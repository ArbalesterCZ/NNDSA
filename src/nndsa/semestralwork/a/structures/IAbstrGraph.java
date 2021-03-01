package nndsa.semestralwork.a.structures;

import java.util.LinkedList;

/**
 *
 * @author milan.horak
 */
interface IAbstrGraph<K, V, E> {

    public void clear();

    public boolean isEmpty();

    public int size();

    public void addVertex(K key, V vertex) throws NullPointerException;

    public void addEdge(K firstVertex, K secondVertex, E edge) throws NullPointerException;

    public V removeVertex(K key) throws NullPointerException;

    public E removeEdge(K keyOne, K keyTwo) throws NullPointerException;

    public V findVertex(K key) throws NullPointerException;

    public E findEdge(K keyOne, K keyTwo) throws NullPointerException;

    public LinkedList<V> findSuccessorElements(K key) throws NullPointerException;

    public LinkedList<E> findIncidentElements(K key) throws NullPointerException;
}
