package nndsa.semestralwork.a.structures;

import java.util.Hashtable;

/**
 *
 * @author milan.horak
 * @param <K> key
 * @param <V> value
 */
public class Graph<K, V> {

    private final Hashtable<K, Hashtable<K, V>> hashTable = new Hashtable();

    public boolean isEmpty() {
        return hashTable.isEmpty();
    }

    public int size() {
        // TODO
        return hashTable.size();
    }

    public void clear() {
        hashTable.clear();
    }

    public Hashtable<K, V> findVertex(K key) {
        return hashTable.get(key);
    }

    public V findEdge(K vertexOne, K vertexTwo) {
        return hashTable.get(vertexOne).get(vertexTwo);
    }

    public void addVertex(K key) {
        if (!hashTable.containsKey(key)) {
            hashTable.put(key, new Hashtable());
        }
    }

    public void addEdge(K firstVertex, K secondVertex, V value) {
        if (hashTable.containsKey(firstVertex) && hashTable.containsKey(secondVertex)) {
            hashTable.get(firstVertex).put(secondVertex, value);
            hashTable.get(secondVertex).put(firstVertex, value);
        }
    }

    public void removeVertex(K vertex) {
        hashTable.remove(vertex);
        // TODO remove other edges from another vertexs
    }

    public V removeEdge(K firstVertex, K secondVertex) {
        hashTable.get(firstVertex).remove(secondVertex);
        return hashTable.get(secondVertex).remove(firstVertex);
    }
}
