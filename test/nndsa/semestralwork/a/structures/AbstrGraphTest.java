package nndsa.semestralwork.a.structures;

import java.util.Iterator;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author milan.horak
 */
public class AbstrGraphTest {

    IAbstrGraph<String, Integer, Integer> graph;

    @Before
    public void setUp() {
        graph = new AbstrGraphHashTable();
        graph.addVertex("a", 1);
        graph.addVertex("b", 2);
        graph.addVertex("c", 3);
        graph.addEdge("a", "b", 100);
        graph.addEdge("a", "c", 200);
    }

    @Test
    public void testClear() {
        graph.clear();
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testIsEmpty() {
        assertFalse(graph.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(graph.size(), 7);
    }

    @Test
    public void testAddVertex() {
        assertNull(graph.findVertex("d"));
        graph.addVertex("d", 4);
        assertNotNull(graph.findVertex("d"));
    }

    @Test
    public void testAddEdge() {
        assertNull(graph.findEdge("b", "c"));
        graph.addEdge("b", "c", 300);
        assertNotNull(graph.findEdge("b", "c"));
        assertNotNull(graph.findEdge("c", "b"));
    }

    @Test
    public void testRemoveVertex() {
        assertNotNull(graph.removeVertex("a"));
        assertNull(graph.removeVertex("a"));
    }

    @Test
    public void testRemoveEdge() {
        assertNotNull(graph.removeEdge("a", "b"));
        assertNull(graph.removeEdge("a", "b"));
        assertNull(graph.removeEdge("b", "a"));
    }

    @Test
    public void testFindVertex() {
        assertTrue(graph.findVertex("a") == 1);
        assertNull(graph.findVertex("d"));
    }

    @Test
    public void testFindEdge() {
        assertTrue(graph.findEdge("a", "b") == 100);
        assertNull(graph.findEdge("b", "c"));
    }

    @Test
    public void testFindSuccessorElements() {
        LinkedList<Integer> shouldBe = new LinkedList<>();
        shouldBe.add(2);
        shouldBe.add(3);
        LinkedList<Integer> result = graph.findSuccessorElements("a");
        assertEquals(shouldBe, result);
    }

    @Test
    public void testFindIncidentElements() {
        LinkedList<Integer> shouldBe = new LinkedList<>();
        shouldBe.add(100);
        shouldBe.add(200);
        LinkedList<Integer> result = graph.findIncidentElements("a");
        assertEquals(shouldBe, result);
    }

    @Test
    public void testIterator() {
        Iterator<Integer> iterator = graph.iterator();
        assertTrue(iterator.hasNext());

        assertTrue(iterator.next() == 2);
        assertTrue(iterator.next() == 1);
        assertTrue(iterator.next() == 3);

        assertFalse(iterator.hasNext());
    }
}
