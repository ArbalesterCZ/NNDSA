package nndsa.semestralwork.a.structures;

import java.util.Objects;

/**
 *
 * @author milan.horak
 */
public class Node implements Comparable<Node> {

    private static int idCounter = 0;

    public final int id = ++idCounter;
    public final Town state;
    public final Node parent;
    public final Path action;
    public final int pathCost;
    public final int pathEval;

    public Node(Town state, Node parent, Path action, int pathCost, int pathEval) {
        this.state = new Town(state);
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
        this.pathEval = pathEval;
    }

    @Override
    public String toString() {
        return state + " PathCost: [" + pathCost + "] PathEval: [" + pathEval + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, action);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        return state.equals(other.state) && action.equals(other.action);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public static void resetId() {
        idCounter = 0;
    }

    public int getTotalCost() {
        return pathCost + pathEval;
    }

    @Override
    public int compareTo(Node other) {
        return getTotalCost() - other.getTotalCost();
    }
}
