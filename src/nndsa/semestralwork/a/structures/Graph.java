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

    @Override
    public String toString() {
        return "Graph: (" + size() + ") " + hashTable;
    }

    public void clear() {
        hashTable.clear();
    }

    public boolean isEmpty() {
        return hashTable.isEmpty();
    }

    public int size() {
        int size = 0;
        for (K key : hashTable.keySet()) {
            size += hashTable.get(key).size() + 1;
        }
        return size;
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
        for (K key : hashTable.get(vertex).keySet()) {
            removeEdge(vertex, key);
        }
        hashTable.remove(vertex);
    }

    public void removeEdge(K firstVertex, K secondVertex) {
        hashTable.get(firstVertex).remove(secondVertex);
        hashTable.get(secondVertex).remove(firstVertex);
    }

    public Hashtable<K, V> findVertex(K key) {
        return hashTable.get(key);
    }

    public V findEdge(K vertexOne, K vertexTwo) {
        return hashTable.get(vertexOne).get(vertexTwo);
    }
}
