package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementazione dell'algoritmo di Bellman-Ford per il calcolo di cammini
 * minimi a sorgente singola in un grafo pesato che può contenere anche pesi
 * negativi, ma non cicli di peso negativo.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class BellmanFordShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {
    private final Graph<L> graph;
    private boolean solved;
    private GraphNode<L> lastSource;

    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * orientato e pesato.
     *
     * @param graph il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException     se il grafo passato è nullo
     * @throws IllegalArgumentException se il grafo passato è vuoto
     * @throws IllegalArgumentException se il grafo passato non è diretto
     * @throws IllegalArgumentException se il grafo passato non è pesato,
     *                                  cioè esiste almeno un arco il cui
     *                                  peso è {@code Double.NaN}.
     */
    public BellmanFordShortestPathComputer(Graph<L> graph) {
        if (graph == null) {
            throw new NullPointerException("Passed parameter must be not null");
        }
        if (graph.isEmpty() || !graph.isDirected() || graph.getEdges().isEmpty()) {
            throw new IllegalArgumentException("Invalid Graph passed");
        }
        for (GraphEdge<L> edge : graph.getEdges()) {
            if (!edge.hasWeight()) {
                throw new IllegalArgumentException("Invalid Graph passed");
            }
        }
        this.graph = graph;
        solved = false;
        lastSource = null;
    }

    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        if (sourceNode == null) {
            throw new NullPointerException("Passed parameter must be not null");
        }
        if (!graph.getNodes().contains(sourceNode)) {
            throw new IllegalArgumentException("Invalid Node passed");
        }
        // I decided to use only the floating point distance
        for (GraphNode<L> node : graph.getNodes()) {
            node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
            node.setPrevious(null);
            if (sourceNode.equals(node)) {
                node.setFloatingPointDistance(0.0);
            }
        }
        for (int i = 0; i < graph.getNodes().size(); ++i) {
            for (GraphEdge<L> edge : graph.getEdges()) {
                GraphNode<L> source = edge.getNode1();
                GraphNode<L> dest = edge.getNode2();
                if (dest.getFloatingPointDistance() > source.getFloatingPointDistance() + edge.getWeight()) {
                    dest.setFloatingPointDistance(source.getFloatingPointDistance() + edge.getWeight());
                    dest.setPrevious(source);
                }
            }
        }
        for (GraphEdge<L> edge : graph.getEdges()) {
            GraphNode<L> source = edge.getNode1();
            GraphNode<L> dest = edge.getNode2();
            if (dest.getFloatingPointDistance() > source.getFloatingPointDistance() + edge.getWeight()) {
                throw new IllegalStateException("Graph contains negative-weight cycle");
            }
        }

        lastSource = sourceNode;
        solved = true;
    }

    @Override
    public boolean isComputed() {
        return solved;
    }

    @Override
    public GraphNode<L> getLastSource() {
        if (lastSource == null) {
            throw new IllegalStateException("Must solve at least once before calling this function");
        }
        return lastSource;
    }

    @Override
    public Graph<L> getGraph() {
        return graph;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        if (targetNode == null) {
            throw new NullPointerException("Passed parameter must be not null");
        }
        if (!graph.getNodes().contains(targetNode)) {
            throw new IllegalArgumentException("Invalid Node passed");
        }
        if (lastSource == null) {
            throw new IllegalStateException("Must solve at least once before calling this function");
        }
        List<GraphEdge<L>> shortestPath = new ArrayList<>();
        GraphNode<L> lastNode = null;
        for (GraphNode<L> node : graph.getNodes()) {
            if (targetNode.equals(node)) {
                lastNode = node;
                break;
            }
        }
        //unnecessary assert, checked in if in line 121, done to avoid warning in line 138
        assert (lastNode != null);
        while (true) {
            GraphNode<L> prev = lastNode.getPrevious();
            for (GraphEdge<L> edge : graph.getIngoingEdgesOf(lastNode)) {
                if (edge.getNode1().equals(prev)) {
                    shortestPath.add(edge);
                    break;
                }
            }
            if (prev.equals(lastSource)) {
                break;
            }
            lastNode = prev;
        }

        Collections.reverse(shortestPath);
        return shortestPath;
    }
}
