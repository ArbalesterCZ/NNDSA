package nndsa.semestralwork.a.structures;

import java.util.Hashtable;
import java.util.Set;

/**
 *
 * @author milan.horak
 * @param <K> key
 * @param <V> vertex
 * @param <E> edge
 */
public class AbstrGraph<K, V, E> {

    private final Hashtable<K, Hashtable<K, E>> edges = new Hashtable();
    private final Hashtable<K, V> vertices = new Hashtable();

    @Override
    public String toString() {
        return "Graph: (" + size() + ") " + edges;
    }

    public void clear() {
        edges.clear();
        vertices.clear();
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public int size() {
        Set<K> keys = edges.keySet();
        int result = keys.size();
        for (K key : keys) {
            result += edges.get(key).size();
        }
        return result;
    }

    public void addVertex(K key, V vertex) {
        if (!edges.containsKey(key)) {
            edges.put(key, new Hashtable());
            vertices.put(key, vertex);
        }
    }

    public void addEdge(K firstVertex, K secondVertex, E edge) {
        if (edges.containsKey(firstVertex) && edges.containsKey(secondVertex)) {
            edges.get(firstVertex).put(secondVertex, edge);
            edges.get(secondVertex).put(firstVertex, edge);
        }
    }

    public V removeVertex(K key) {
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

    public E removeEdge(K keyOne, K keyTwo) {
        edges.get(keyOne).remove(keyTwo);
        return edges.get(keyTwo).remove(keyOne);
    }

    public V findVertex(K key) {
        return vertices.get(key);
    }

    public E findEdge(K keyOne, K keyTwo) {
        return edges.get(keyOne).get(keyTwo);
    }
}
