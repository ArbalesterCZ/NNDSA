package nndsa.semestralwork.a.structures;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

/**
 *
 * @author milan.horak
 * @param <K> key
 * @param <V> vertex
 * @param <E> edge
 */
public class AbstrGraph<K, V, E> implements IAbstrGraph<K, V, E> {

    private final Hashtable<K, Hashtable<K, E>> edges = new Hashtable();
    private final Hashtable<K, V> vertices = new Hashtable();

    @Override
    public String toString() {
        return "Graph: (" + size() + ") " + edges;
    }

    @Override
    public void clear() {
        edges.clear();
        vertices.clear();
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    @Override
    public int size() {
        Set<K> keys = edges.keySet();
        int result = keys.size();
        for (K key : keys) {
            result += edges.get(key).size();
        }
        return result;
    }

    @Override
    public void addVertex(K key, V vertex) throws NullPointerException {
        if (key == null || vertex == null) {
            throw new NullPointerException();
        }
        if (!edges.containsKey(key)) {
            edges.put(key, new Hashtable());
            vertices.put(key, vertex);
        }
    }

    @Override
    public void addEdge(K firstVertex, K secondVertex, E edge) throws NullPointerException {
        if (firstVertex == null || secondVertex == null || edge == null) {
            throw new NullPointerException();
        }
        if (!edges.containsKey(firstVertex) && !edges.containsKey(secondVertex)) {
            return;
        }
        edges.get(firstVertex).put(secondVertex, edge);
        edges.get(secondVertex).put(firstVertex, edge);
    }

    @Override
    public V removeVertex(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        if (!vertices.containsKey(key)) {
            return null;
        }
        Set<K> keys = edges.keySet();
        for (K otherKey : keys) {
            removeEdge(key, otherKey);
        }
        edges.remove(key);
        return vertices.remove(key);
    }

    @Override
    public E removeEdge(K keyOne, K keyTwo) throws NullPointerException {
        if (keyOne == null || keyTwo == null) {
            throw new NullPointerException();
        }
        edges.get(keyOne).remove(keyTwo);
        return edges.get(keyTwo).remove(keyOne);
    }

    @Override
    public V findVertex(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        return vertices.get(key);
    }

    @Override
    public E findEdge(K keyOne, K keyTwo) throws NullPointerException {
        if (keyOne == null || keyTwo == null) {
            throw new NullPointerException();
        }
        if (!edges.containsKey(keyOne)) {
            return null;
        }
        return edges.get(keyOne).get(keyTwo);
    }

    @Override
    public LinkedList<V> findSuccessorElements(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        Set<K> keys = edges.get(key).keySet();
        LinkedList<V> result = new LinkedList<>();
        for (K edgeKey : keys) {
            result.add(vertices.get(edgeKey));
        }
        return result;
    }

    @Override
    public LinkedList<E> findIncidentElements(K key) throws NullPointerException {
        if (key == null) {
            throw new NullPointerException();
        }
        Set<K> keys = edges.get(key).keySet();
        LinkedList<E> result = new LinkedList<>();
        for (K edgeKey : keys) {
            result.add(edges.get(key).get(edgeKey));
        }
        return result;
    }
}
