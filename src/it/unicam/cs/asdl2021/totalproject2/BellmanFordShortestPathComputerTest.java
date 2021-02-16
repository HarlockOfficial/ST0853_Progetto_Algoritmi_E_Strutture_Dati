package it.unicam.cs.asdl2021.totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class BellmanFordShortestPathComputerTest {
    @Test
    final void testBellmanFordShortestPathComputer() {
        //noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> new BellmanFordShortestPathComputer<Integer>(null));

        Graph<Integer> undirectedGraph = new MapAdjacentListUndirectedGraph<>();
        GraphNode<Integer> undirectedGraphNode = new GraphNode<>(1);
        undirectedGraph.addNode(undirectedGraphNode);
        undirectedGraph.addEdge(new GraphEdge<>(undirectedGraphNode, undirectedGraphNode, false, 12));
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<>(undirectedGraph));

        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<>(graph));
        GraphNode<Integer> node = new GraphNode<>(1);
        graph.addNode(node);
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<>(graph));
        graph.addEdge(new GraphEdge<>(node, node, true));
        assertThrows(IllegalArgumentException.class, () -> new BellmanFordShortestPathComputer<>(graph));
        graph.removeEdge(new GraphEdge<>(node, node, true));
        graph.addEdge(new GraphEdge<>(node, node, true, 12));
        assertEquals(graph, new BellmanFordShortestPathComputer<>(graph).getGraph());
    }

    @Test
    final void testComputeShortestPathsFrom() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<Integer> node1 = new GraphNode<>(1);
        GraphNode<Integer> node2 = new GraphNode<>(2);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(new GraphEdge<>(node1, node2, true, 9));
        SingleSourceShortestPathComputer<Integer> computer = new BellmanFordShortestPathComputer<>(graph);
        assertThrows(NullPointerException.class, () -> computer.computeShortestPathsFrom(null));
        assertThrows(IllegalArgumentException.class, () -> computer.computeShortestPathsFrom(new GraphNode<>(3)));
        GraphEdge<Integer> edge = new GraphEdge<>(node2, node1, true, -10);
        graph.addEdge(edge);
        assertThrows(IllegalStateException.class, () -> computer.computeShortestPathsFrom(node1));
        graph.removeEdge(edge);
        edge = new GraphEdge<>(node2, node1, true, 2);
        graph.addEdge(edge);
        assertFalse(computer.isComputed());
        computer.computeShortestPathsFrom(node1);
        assertTrue(computer.isComputed());
    }

    @Test
    final void testIsComputed() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<Integer> node1 = new GraphNode<>(1);
        GraphNode<Integer> node2 = new GraphNode<>(2);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(new GraphEdge<>(node1, node2, true, 9));
        graph.addEdge(new GraphEdge<>(node2, node1, true, 2));
        SingleSourceShortestPathComputer<Integer> computer = new BellmanFordShortestPathComputer<>(graph);
        assertFalse(computer.isComputed());
        computer.computeShortestPathsFrom(node1);
        assertTrue(computer.isComputed());
    }

    @Test
    final void testGetLastSource() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<Integer> node1 = new GraphNode<>(1);
        GraphNode<Integer> node2 = new GraphNode<>(2);
        graph.addNode(node1);
        graph.addNode(node2);
        graph.addEdge(new GraphEdge<>(node1, node2, true, 9));
        graph.addEdge(new GraphEdge<>(node2, node1, true, 2));
        SingleSourceShortestPathComputer<Integer> computer = new BellmanFordShortestPathComputer<>(graph);
        assertFalse(computer.isComputed());
        //noinspection Convert2MethodRef
        assertThrows(IllegalStateException.class, () -> computer.getLastSource());
        computer.computeShortestPathsFrom(node2);
        assertTrue(computer.isComputed());
        assertEquals(node2, computer.getLastSource());
    }

    @Test
    final void testGetGraph() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        for (int i = 0; i < 100; ++i) {
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        for (int i = 0; i < 4000; ++i) {
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            graph.addEdge(new GraphEdge<>(node1, node2, true, generator.nextInt()));
        }
        //assuming that constructor works
        SingleSourceShortestPathComputer<Integer> computer = new BellmanFordShortestPathComputer<>(graph);
        assertEquals(computer.getGraph(), graph);
    }

    @Test
    final void testGetShortestPathTo() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<Integer> node1 = new GraphNode<>(1);
        GraphNode<Integer> node2 = new GraphNode<>(2);
        graph.addNode(node1);
        graph.addNode(node2);
        GraphEdge<Integer> edge1 = new GraphEdge<>(node1, node2, true, 9);
        graph.addEdge(edge1);
        graph.addEdge(new GraphEdge<>(node2, node1, true, 2));
        SingleSourceShortestPathComputer<Integer> computer = new BellmanFordShortestPathComputer<>(graph);
        assertThrows(IllegalStateException.class, () -> computer.getShortestPathTo(node1));
        computer.computeShortestPathsFrom(node1);
        assertThrows(NullPointerException.class, () -> computer.getShortestPathTo(null));
        assertThrows(IllegalArgumentException.class, () -> computer.getShortestPathTo(new GraphNode<>(3)));
        List<GraphEdge<Integer>> shortestPath = computer.getShortestPathTo(node2);
        List<GraphEdge<Integer>> edges = new ArrayList<>();
        edges.add(edge1);
        assertEquals(edges, shortestPath);
    }

}
