package nndsa.semestralwork.a.structures;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *
 * @author milan.horak
 * @param <K> key
 * @param <V> vertex
 * @param <E> edge
 */
public class AbstrGraph2<K, V, E> implements IAbstrGraph<K, V, E> {

    private final Hashtable<K, Vertex> vertices = new Hashtable();

    @Override
    public String toString() {
        return "Graph: (" + size() + ") " + vertices;
    }

    @Override
    public void clear() {
        vertices.clear();
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int size() {
        Set<K> keys = vertices.keySet();
        int result = vertices.size();
        for (K vertexKey : keys) {
            result += vertices.get(vertexKey).edges.size();
        }
        return result;
    }

    @Override
    public int verticlesCount() {
        return vertices.size();
    }

    @Override
    public void addVertex(K key, V vertex) throws NullPointerException {
        if (key == null || vertex == null) {
            throw new NullPointerException();
        }
        if (!vertices.containsKey(key)) {
            vertices.put(key, new Vertex(vertex));
        }
    }

    @Override
    public void addEdge(K keyOne, K keyTwo, E edge) throws NullPointerException {
        if (keyOne == null || keyTwo == null || edge == null) {
            throw new NullPointerException();
        }
        if (!vertices.containsKey(keyOne) && !vertices.containsKey(keyTwo)) {
            return;
        }
        Vertex start = vertices.get(keyOne);
        Vertex target = vertices.get(keyTwo);
        start.edges.add(new Edge(start, target, edge));
        target.edges.add(new Edge(target, start, edge));
    }

    @Override
    public V removeVertex(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        if (!vertices.containsKey(key)) {
            return null;
        }
        Set<K> keys = vertices.keySet();
        keys.forEach((otherKey) -> removeEdge(key, otherKey));
        return vertices.remove(key).data;
    }

    @Override
    public E removeEdge(K keyOne, K keyTwo) throws NullPointerException {
        if (keyOne == null || keyTwo == null) {
            throw new NullPointerException();
        }
        if (findEdge(keyOne, keyTwo) == null) {
            return null;
        }
        Vertex start = vertices.get(keyOne);
        Vertex target = vertices.get(keyTwo);
        LinkedList<Edge> startEdges = start.edges;
        LinkedList<Edge> targetEdges = target.edges;

        E result = findEdge(keyOne, keyTwo);

        for (Edge edge : startEdges) {
            if (edge.target.equals(target)) {
                vertices.get(keyOne).edges.remove(edge);
            }
        }
        for (Edge edge : targetEdges) {
            if (edge.target.equals(start)) {
                vertices.get(keyOne).edges.remove(edge);
            }
        }
        return result;
    }

    @Override
    public V findVertex(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        if (!vertices.containsKey(key)) {
            return null;
        }
        return vertices.get(key).data;
    }

    @Override
    public E findEdge(K keyOne, K keyTwo) throws NullPointerException {
        if (keyOne == null || keyTwo == null) {
            throw new NullPointerException();
        }
        LinkedList<Edge> edges = vertices.get(keyOne).edges;
        Vertex target = vertices.get(keyTwo);
        for (Edge edge : edges) {
            if (edge.target.equals(target)) {
                return edge.data;
            }
        }
        return null;
    }

    @Override
    public LinkedList<V> findSuccessorElements(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        LinkedList<Edge> edges = vertices.get(key).edges;
        LinkedList<V> result = new LinkedList<>();
        for (Edge edge : edges) {
            result.add(edge.target.data);
        }
        return result;
    }

    @Override
    public LinkedList<E> findIncidentElements(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        LinkedList<E> result = new LinkedList();
        for (Edge edge : vertices.get(key).edges) {
            result.add(edge.data);
        }
        return result;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            Enumeration<K> keys = vertices.keys();

            @Override
            public boolean hasNext() {
                return keys.hasMoreElements();
            }

            @Override
            public V next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return vertices.get(keys.nextElement()).data;
            }
        };
    }

    private class Vertex {

        private V data;
        private LinkedList<Edge> edges = new LinkedList();

        public Vertex(V vertex) {
            this.data = vertex;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private class Edge {

        private Vertex start;
        private Vertex target;
        private E data;

        public Edge(Vertex start, Vertex target, E data) {
            this.start = start;
            this.target = target;
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
