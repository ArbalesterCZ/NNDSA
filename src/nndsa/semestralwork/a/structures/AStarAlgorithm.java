package nndsa.semestralwork.a.structures;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AStarAlgorithm {

    private final List<Node> STATE_LIST_SAVE = new LinkedList();
    private final PriorityQueue<Node> STATE_LIST_FIFO = new PriorityQueue();

    private Town target;

    IAbstrGraph<Integer, Town, Path> graph;

    public AStarAlgorithm(IAbstrGraph graph) {
        this.graph = graph;
    }

    public List<Node> findSolution(Town start, Town target) {
        this.target = target;
        STATE_LIST_FIFO.clear();
        STATE_LIST_SAVE.clear();
        Town initialTown = graph.findVertex(start.hashCode());
        if (initialTown == null) {
            return null;
        }

        STATE_LIST_FIFO.add(new Node(initialTown, null, null, 0, evalFunction(initialTown)));
        final LinkedList<Node> SOLUTION_LIST = new LinkedList();

        while (!STATE_LIST_FIFO.isEmpty()) {
            Node expandedNode = STATE_LIST_FIFO.remove();
            STATE_LIST_SAVE.add(expandedNode);

            if (expandedNode.state.equals(target)) {
                Node pathNode = expandedNode;
                while (pathNode != null) {
                    SOLUTION_LIST.add(pathNode);
                    pathNode = pathNode.parent;
                }
                Collections.sort(SOLUTION_LIST, (Node node1, Node node2) -> node1.id - node2.id);
                LinkedList<Node> result = new LinkedList();
                for (Node node : SOLUTION_LIST) {
                    result.add(node);
                }
                return result;
            }
            expanse(expandedNode);
        }
        return SOLUTION_LIST;
    }

    private void expanse(Node expandedNode) {
        LinkedList<Town> newTowns = graph.findSuccessorElements(expandedNode.state.hashCode());
        for (Town newTown : newTowns) {
            Path action = graph.findEdge(newTown.hashCode(), expandedNode.state.hashCode());
            if (isStateSaved(newTown, action, STATE_LIST_SAVE) || isStateSaved(newTown, action, STATE_LIST_FIFO)) {
                continue;
            }
            int shiftCost = action.length;
            Node newNode = new Node(newTown, expandedNode, action, expandedNode.pathCost + shiftCost, evalFunction(newTown));
            STATE_LIST_FIFO.add(newNode);
        }
    }

    private boolean isStateSaved(Town town, Path action, PriorityQueue<Node> states) {
        for (Node node : states) {
            if (node.state.equals(town) && action.equals(node.action)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStateSaved(Town town, Path action, List<Node> list) {
        for (Node node : list) {
            if (node.state.equals(town) && action.equals(node.action)) {
                return true;
            }
        }
        return false;
    }

    private int evalFunction(Town town) {
        return (int) (town.getCoordinate().distance(target.getCoordinate()));
    }
}
