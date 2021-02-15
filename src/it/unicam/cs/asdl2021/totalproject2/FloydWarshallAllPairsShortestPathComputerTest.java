package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class FloydWarshallAllPairsShortestPathComputerTest {

    // TODO implementare tutti i test non ancora implementati

    @Test
    final void testFloydWarshallAllPairsShortestPathComputer() {
        Graph<String> tmp = new MapAdjacentListUndirectedGraph<>();
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false));
        assertThrows(NullPointerException.class,()->new FloydWarshallAllPairsShortestPathComputer<String>(null));//eccezzione perch� � null
        assertThrows(IllegalArgumentException.class,()->new FloydWarshallAllPairsShortestPathComputer<String>(tmp));//eccezzione perch� � orientato
        assertThrows(IllegalArgumentException.class,()->new FloydWarshallAllPairsShortestPathComputer<String>(tmp1));//eccezzione per mancanza di peso

    }

    @Test
    final void testComputeShortestPaths() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, -1));
        tmp1.addEdge(new GraphEdge<String>(g, h, false, -1));
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        assertThrows(IllegalArgumentException.class,()->app.computeShortestPaths());//eccezzione ciclo con peso negativo

        
    }

    @Test
    final void testIsComputed() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, 1));
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        assertFalse(app.isComputed());
        app.computeShortestPaths();
        assertTrue(app.isComputed());
    }

    @Test
    final void testGetGraph() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("a");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("b");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, 1));
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        assertTrue(tmp1.equals(app.getGraph()));
    }

    @Test
    final void testGetShortestPath() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("1");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("2");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, 2));
        tmp1.addEdge(new GraphEdge<String>(g, h, false, 3));
        GraphNode<String> p = new GraphNode<String>("4");
        tmp1.addNode(p);
        tmp1.addEdge(new GraphEdge<String>(g, p, false, 5));
        tmp1.addEdge(new GraphEdge<String>(h, p, false, 4));
        GraphNode<String> d = new GraphNode<String>("3");
        tmp1.addNode(d);
        GraphEdge< String > appEdge1 = new GraphEdge<>(p, d, false,2);
        tmp1.addEdge(appEdge1);
        GraphEdge< String > appEdge2 =new GraphEdge<String>(d , h, false, 1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        app.computeShortestPaths();
        List<GraphEdge<String>> getShortestPath = new ArrayList<GraphEdge<String>>();
        getShortestPath.add(appEdge1);
        getShortestPath.add(appEdge2);
        assertTrue(getShortestPath.equals(app.getShortestPath(p, h)));
        getShortestPath.add(new GraphEdge<String>(d , h, false, 3));
        assertFalse(getShortestPath.equals(app.getShortestPath(p, h)));


    }

    @Test
    final void testGetShortestPathCost() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("1");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("2");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, 2));
        tmp1.addEdge(new GraphEdge<String>(g, h, false, 3));
        GraphNode<String> p = new GraphNode<String>("4");
        tmp1.addNode(p);
        tmp1.addEdge(new GraphEdge<String>(g, p, false, 5));
        tmp1.addEdge(new GraphEdge<String>(h, p, false, 4));
        GraphNode<String> d = new GraphNode<String>("3");
        tmp1.addNode(d);
        GraphEdge< String > appEdge1 = new GraphEdge<>(p, d, false,2);
        tmp1.addEdge(appEdge1);
        GraphEdge< String > appEdge2 =new GraphEdge<String>(d , h, false, 1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        app.computeShortestPaths();
        List<GraphEdge<String>> getShortestPath = new ArrayList<GraphEdge<String>>();
        getShortestPath.add(appEdge1);
        getShortestPath.add(appEdge2);
        assertTrue(app.getShortestPathCost(p, h)==3);
    }

    @Test
    final void testPrintPath() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    final void testGetCostMatrix() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("1");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("2");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, 2));
        tmp1.addEdge(new GraphEdge<String>(g, h, false, 3));
        GraphNode<String> p = new GraphNode<String>("4");
        tmp1.addNode(p);
        tmp1.addEdge(new GraphEdge<String>(g, p, false, 5));
        tmp1.addEdge(new GraphEdge<String>(h, p, false, 4));
        GraphNode<String> d = new GraphNode<String>("3");
        tmp1.addNode(d);
        GraphEdge< String > appEdge1 = new GraphEdge<>(p, d, false,2);
        tmp1.addEdge(appEdge1);
        GraphEdge< String > appEdge2 =new GraphEdge<String>(d , h, false, 1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        app.computeShortestPaths();
        List<GraphEdge<String>> getShortestPath = new ArrayList<GraphEdge<String>>();
        getShortestPath.add(appEdge1);
        getShortestPath.add(appEdge2);
        assertTrue(app.getCostMatrix()[3][3]==0);
        assertTrue(app.getCostMatrix()[1][0]==2);

    }

    @Test
    final void testGetPredecessorMatrix() {
        Graph<String> tmp1= new AdjacencyMatrixDirectedGraph<String>();
        GraphNode<String> g = new GraphNode<String>("1");
        tmp1.addNode(g);
        GraphNode<String> h = new GraphNode<String>("2");
        tmp1.addNode(h);
        tmp1.addEdge(new GraphEdge<String>(h, g, false, 2));
        tmp1.addEdge(new GraphEdge<String>(g, h, false, 3));
        GraphNode<String> p = new GraphNode<String>("4");
        tmp1.addNode(p);
        tmp1.addEdge(new GraphEdge<String>(g, p, false, 5));
        tmp1.addEdge(new GraphEdge<String>(h, p, false, 4));
        GraphNode<String> d = new GraphNode<String>("3");
        tmp1.addNode(d);
        GraphEdge< String > appEdge1 = new GraphEdge<>(p, d, false,2);
        tmp1.addEdge(appEdge1);
        GraphEdge< String > appEdge2 =new GraphEdge<String>(d , h, false, 1);
        tmp1.addEdge(appEdge2);
        FloydWarshallAllPairsShortestPathComputer<String> app = new FloydWarshallAllPairsShortestPathComputer<String>(tmp1);
        app.computeShortestPaths();
        List<GraphEdge<String>> getShortestPath = new ArrayList<GraphEdge<String>>();
        getShortestPath.add(appEdge1);
        getShortestPath.add(appEdge2);
        assertTrue(app.getPredecessorMatrix()[0][2]==-1);

    }

}
