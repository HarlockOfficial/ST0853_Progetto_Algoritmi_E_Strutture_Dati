package it.unicam.cs.asdl2021.totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Template: Luca Tesei
 */
class FloydWarshallAllPairsShortestPathComputerTest {

    // TODO implementare tutti i test non ancora implementati

    @Test
    final void testFloydWarshallAllPairsShortestPathComputer() {
        Graph<String> tmp = new MapAdjacentListUndirectedGraph<>();
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<>(h, g, true));
        assertThrows(NullPointerException.class, () -> new FloydWarshallAllPairsShortestPathComputer<String>(null));//eccezzione perch� � null
        assertThrows(IllegalArgumentException.class, () -> new FloydWarshallAllPairsShortestPathComputer<>(tmp));//eccezzione perch� � orientato
        assertThrows(IllegalArgumentException.class, () -> new FloydWarshallAllPairsShortestPathComputer<>(tmp1));//eccezzione per mancanza di peso

    }

    @Test
    final void testComputeShortestPaths() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<>(h, g, true, -1));
        tmp1.addEdge(new GraphEdge<>(g, h, true, -1));
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        //noinspection Convert2MethodRef
        assertThrows(IllegalArgumentException.class, () -> app.computeShortestPaths());//eccezzione ciclo con peso negativo


    }

    @Test
    final void testIsComputed() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 1));
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        assertFalse(app.isComputed());
        app.computeShortestPaths();
        assertTrue(app.isComputed());
    }

    @Test
    final void testGetGraph() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 1));
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        assertEquals(app.getGraph(), tmp1);
    }

    @Test
    final void testGetShortestPath() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("1");
        GraphNode<String> h = new GraphNode<>("2");
        GraphNode<String> d = new GraphNode<>("3");
        GraphNode<String> p = new GraphNode<>("4");
        tmp1.addNode(g);
        tmp1.addNode(h);
        tmp1.addNode(d);
        tmp1.addNode(p);
        GraphEdge<String> appEdge1 = new GraphEdge<>(p, d, true, 2);
        GraphEdge<String> appEdge2 = new GraphEdge<>(d, h, true, 1);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 2));
        tmp1.addEdge(new GraphEdge<>(g, h, true, 3));
        tmp1.addEdge(new GraphEdge<>(g, p, true, 5));
        tmp1.addEdge(new GraphEdge<>(h, p, true, 4));
        tmp1.addEdge(appEdge1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        app.computeShortestPaths();
        List<GraphEdge<String>> getShortestPath = new ArrayList<>();
        getShortestPath.add(appEdge1);
        getShortestPath.add(appEdge2);
        assertEquals(app.getShortestPath(p, h), getShortestPath);
        getShortestPath.add(new GraphEdge<>(d, h, true, 3));
        assertNotEquals(app.getShortestPath(p, h), getShortestPath);
    }

    @Test
    final void testGetShortestPathCost() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("1");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<>("2");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 2));
        tmp1.addEdge(new GraphEdge<>(g, h, true, 3));
        GraphNode<String> p = new GraphNode<>("4");
        tmp1.addNode(p);
        tmp1.addEdge(new GraphEdge<>(g, p, true, 5));
        tmp1.addEdge(new GraphEdge<>(h, p, true, 4));
        GraphNode<String> d = new GraphNode<>("3");
        tmp1.addNode(d);
        GraphEdge<String> appEdge1 = new GraphEdge<>(p, d, true, 2);
        tmp1.addEdge(appEdge1);
        GraphEdge<String> appEdge2 = new GraphEdge<>(d, h, true, 1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        app.computeShortestPaths();
        assertEquals(app.getShortestPathCost(p, h), 3);
    }

    @Test
    final void testPrintPath() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("1");
        GraphNode<String> h = new GraphNode<>("2");
        GraphNode<String> d = new GraphNode<>("3");
        GraphNode<String> p = new GraphNode<>("4");
        tmp1.addNode(g);
        tmp1.addNode(h);
        tmp1.addNode(d);
        tmp1.addNode(p);
        GraphEdge<String> appEdge1 = new GraphEdge<>(p, d, true, 2);
        GraphEdge<String> appEdge2 = new GraphEdge<>(d, h, true, 1);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 2));
        tmp1.addEdge(new GraphEdge<>(g, h, true, 3));
        tmp1.addEdge(new GraphEdge<>(g, p, true, 5));
        tmp1.addEdge(new GraphEdge<>(h, p, true, 4));
        tmp1.addEdge(appEdge1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        app.computeShortestPaths();
        List<GraphEdge<String>> getShortestPath = new ArrayList<>();
        getShortestPath.add(appEdge1);
        getShortestPath.add(appEdge2);
        assertEquals(app.printPath(app.getShortestPath(p, h)), app.printPath(getShortestPath));
    }

    @Test
    final void testGetCostMatrix() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("1");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<>("2");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 2));
        tmp1.addEdge(new GraphEdge<>(g, h, true, 3));
        GraphNode<String> p = new GraphNode<>("4");
        tmp1.addNode(p);
        tmp1.addEdge(new GraphEdge<>(g, p, true, 5));
        tmp1.addEdge(new GraphEdge<>(h, p, true, 4));
        GraphNode<String> d = new GraphNode<>("3");
        tmp1.addNode(d);
        GraphEdge<String> appEdge1 = new GraphEdge<>(p, d, true, 2);
        tmp1.addEdge(appEdge1);
        GraphEdge<String> appEdge2 = new GraphEdge<>(d, h, true, 1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        app.computeShortestPaths();
        assertEquals(app.getCostMatrix()[3][3], 0);
        assertEquals(app.getCostMatrix()[1][0], 2);

    }

    @Test
    final void testGetPredecessorMatrix() {
        Graph<String> tmp1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> g = new GraphNode<>("1");
        GraphNode<String> h = new GraphNode<>("2");
        GraphNode<String> d = new GraphNode<>("3");
        GraphNode<String> p = new GraphNode<>("4");
        tmp1.addNode(g);
        tmp1.addNode(h);
        tmp1.addNode(d);
        tmp1.addNode(p);
        GraphEdge<String> appEdge1 = new GraphEdge<>(p, d, true, 2);
        GraphEdge<String> appEdge2 = new GraphEdge<>(d, h, true, 1);
        tmp1.addEdge(new GraphEdge<>(h, g, true, 2));
        tmp1.addEdge(new GraphEdge<>(g, h, true, 3));
        tmp1.addEdge(new GraphEdge<>(g, p, true, 5));
        tmp1.addEdge(new GraphEdge<>(h, p, true, 4));
        tmp1.addEdge(appEdge1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<>(tmp1);
        app.computeShortestPaths();
        assertEquals(-1, app.getPredecessorMatrix()[0][2]);
    }

}
