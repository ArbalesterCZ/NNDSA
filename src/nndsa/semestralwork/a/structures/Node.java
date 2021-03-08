package nndsa.semestralwork.a.structures;

import java.util.Objects;

/**
 *
 * @author milan.horak
 */
public class Node {

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
        return state + " PathCost=" + pathCost + "PathEval=" + pathEval;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.state);
        return hash;
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
        return state.equals(other.state);
    }

    public boolean hasParent() {
        return parent != null;
    }

    public static void resetId() {
        idCounter = 0;
    }

    public int getId() {
        return id;
    }

    public Town getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public Path getAction() {
        return action;
    }

    public int getPathCost() {
        return pathCost;
    }

    public int getPathEval() {
        return pathEval;
    }

    public int getTotalCost() {
        return pathCost + pathEval;
    }
}
