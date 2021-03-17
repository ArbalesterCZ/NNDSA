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
public class AbstrGraphHashTable<K, V, E> implements IAbstrGraph<K, V, E> {

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
        if (findEdge(keyOne, keyTwo) != null) {
            return;
        }
        vertices.get(keyOne).edges.put(keyTwo, edge);
        vertices.get(keyTwo).edges.put(keyOne, edge);
    }

    @Override
    public V removeVertex(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        if (!vertices.containsKey(key)) {
            return null;
        }
        Vertex vertex = vertices.get(key);
        LinkedList<K> keys = new LinkedList();
        for (K k : vertex.edges.keySet()) {
            keys.add(k);
        }
        for (K otherKey : keys) {
            removeEdge(key, otherKey);
        }
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
        vertices.get(keyOne).edges.remove(keyTwo);
        return vertices.get(keyTwo).edges.remove(keyOne);
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
        return vertices.get(keyOne).edges.get(keyTwo);
    }

    @Override
    public LinkedList<V> findSuccessorElements(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        LinkedList<V> result = new LinkedList();
        for (K vertexKey : vertices.get(key).edges.keySet()) {
            result.add(vertices.get(vertexKey).data);
        }
        return result;
    }

    @Override
    public LinkedList<E> findIncidentElements(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        LinkedList<E> result = new LinkedList();
        for (K keyTwo : vertices.get(key).edges.keySet()) {
            result.add(vertices.get(key).edges.get(keyTwo));
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
        private Hashtable<K, E> edges = new Hashtable();

        public Vertex(V vertex) {
            this.data = vertex;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
}
