package it.unicam.cs.asdl2021.totalproject2;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class MapAdjacentListUndirectedGraphTest {
    private Graph<Integer> getValidGraph(){
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphNode<Integer>> nodes = graph.getNodes();
        for(int i=0;i<4000;++i){
            boolean result;
            do {
                //noinspection unchecked
                GraphNode<Integer> node1 = (GraphNode<Integer>) nodes.toArray()[generator.nextInt(nodes.size())];
                //noinspection unchecked
                GraphNode<Integer> node2 = (GraphNode<Integer>) nodes.toArray()[generator.nextInt(nodes.size())];
                result = graph.addEdge(new GraphEdge<>(node1, node2, false, generator.nextInt()));
            }while (!result);
        }
        return graph;
    }

    @Test
    final void testNodeCount() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            assertEquals(i, graph.nodeCount());
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        assertEquals(100, graph.nodeCount());
    }

    @Test
    final void testEdgeCount() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphNode<Integer>> nodes = graph.getNodes();
        for(int i=0;i<4000;++i){
            assertEquals(i, graph.edgeCount());
            boolean result;
            do {
                //noinspection unchecked
                GraphNode<Integer> node1 = (GraphNode<Integer>) nodes.toArray()[generator.nextInt(nodes.size())];
                //noinspection unchecked
                GraphNode<Integer> node2 = (GraphNode<Integer>) nodes.toArray()[generator.nextInt(nodes.size())];
                result = graph.addEdge(new GraphEdge<>(node1, node2, false, generator.nextInt()));
            }while (!result);
        }
        assertEquals(4000, graph.edgeCount());
    }

    @Test
    final void testClear() {
        Graph<Integer> graph = getValidGraph();
        assertTrue(graph.nodeCount()>0);
        assertTrue(graph.edgeCount()>0);
        graph.clear();
        assertEquals(0, graph.nodeCount());
        assertEquals(0, graph.edgeCount());
    }

    @Test
    final void testIsDirected() {
        Graph<Integer> graph = getValidGraph();
        assertFalse(graph.isDirected());
    }

    @Test
    final void testGetNodes() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            assertEquals(i, graph.getNodes().size());
            assertEquals(nodeList, graph.getNodes());
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            nodeList.add(node);
            graph.addNode(node);
        }
        assertEquals(100, graph.getNodes().size());
        assertEquals(nodeList, graph.getNodes());
        graph.clear();
        nodeList.clear();
        assertEquals(0, graph.getNodes().size());
        assertEquals(nodeList, graph.getNodes());
    }

    @Test
    final void testAddNode() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.addNode(null));
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        boolean trueTested = false, falseTested = false;
        for(int i=0;i<100;++i){
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt(50));
            if(nodeList.add(node)){
                assertTrue(graph.addNode(node));
                trueTested=true;
            }else{
                assertFalse(graph.addNode(node));
                falseTested=true;
            }
        }
        assertTrue(trueTested && falseTested);
    }

    @Test
    final void testRemoveNode() {
        Graph<Integer> graph = getValidGraph();
        assertThrows(NullPointerException.class, () -> graph.removeNode(null));
        Set<GraphNode<Integer>> nodes = new HashSet<>(graph.getNodes());
        for(GraphNode<Integer> node: nodes){
            assertTrue(graph.removeNode(node));
            assertFalse(graph.removeNode(node));
        }
    }

    @Test
    final void testContainsNode() {
        Graph<Integer> graph = getValidGraph();
        assertThrows(NullPointerException.class, () -> graph.containsNode(null));
        Set<GraphNode<Integer>> nodes = new HashSet<>(graph.getNodes());
        for(GraphNode<Integer> node: nodes){
            assertTrue(graph.containsNode(node));
            graph.removeNode(node);
            assertFalse(graph.containsNode(node));
        }
    }

    @Test
    final void testGetNodeOf() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getNodeOf(null));
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            graph.addNode(node);
            nodeList.add(node);
        }
        for(GraphNode<Integer> node: nodeList){
            assertEquals(node, graph.getNodeOf(node.getLabel()));
        }
        boolean falseTested = false;
        for(int i=0;i<1000;++i){
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            if(!nodeList.contains(node)){
                assertNull(graph.getNodeOf(node.getLabel()));
                falseTested=true;
            }
        }
        assertTrue(falseTested);
    }

    @Test
    final void testGetNodeIndexOf() {
        Graph<Integer> graph = getValidGraph();
        assertThrows(NullPointerException.class, () -> graph.getNodeIndexOf(null));
        assertThrows(UnsupportedOperationException.class, () -> graph.getNodeIndexOf(1));
    }

    @Test
    final void testGetNodeAtIndex() {
        Graph<Integer> graph = getValidGraph();
        assertThrows(UnsupportedOperationException.class, () -> graph.getNodeAtIndex(0));
    }

    @Test
    final void testGetEdge(){
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        Random generator = new Random(123456);
        assertThrows(NullPointerException.class, () ->
                graph.getEdge(null, new GraphNode<>(new Random().nextInt()))
        );
        assertThrows(NullPointerException.class, () ->
                graph.getEdge(new GraphNode<>(new Random().nextInt()), null)
        );
        assertThrows(NullPointerException.class, () -> graph.getEdge(null, null));
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () ->
                graph.getEdge(new GraphNode<>(-1),(GraphNode<Integer>) graph.getNodes().toArray()[0])
        );
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () ->
                graph.getEdge((GraphNode<Integer>) graph.getNodes().toArray()[0], new GraphNode<>(-1))
        );
        assertThrows(IllegalArgumentException.class, () ->
                graph.getEdge(new GraphNode<>(-1), new GraphNode<>(-2))
        );
        assertEquals(graph.edgeCount(), 0);
        for(int i=0;i<graph.nodeCount();++i){
            for(int j=0;j<graph.nodeCount(); ++j){
                //noinspection unchecked
                assertNull(graph.getEdge((GraphNode<Integer>) graph.getNodes().toArray()[i], (GraphNode<Integer>) graph.getNodes().toArray()[j]));
            }
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            //noinspection unchecked
            GraphNode<Integer> node1 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            //noinspection unchecked
            GraphNode<Integer> node2 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, false, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertEquals(edgeList.size(), graph.edgeCount());
        for(GraphEdge<Integer> edge: edgeList){
            assertEquals(edge, graph.getEdge(edge.getNode1(), edge.getNode2()));
        }
    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
        Graph<Integer> graph = getValidGraph();
        assertThrows(UnsupportedOperationException.class, () -> graph.getEdgeAtNodeIndexes(0, 0));
    }

    @Test
    final void testGetAdjacentNodesOf() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getAdjacentNodesOf(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            //noinspection unchecked
            GraphNode<Integer> node1 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            //noinspection unchecked
            GraphNode<Integer> node2 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, false, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacentNodesOf(new GraphNode<>(-1)));
        Set<GraphNode<Integer>> adjacents = new HashSet<>();
        //noinspection unchecked
        GraphNode<Integer> node = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode1().equals(node)){
                adjacents.add(edge.getNode2());
            }else if(edge.getNode2().equals(node)){
                adjacents.add(edge.getNode1());
            }
        }
        assertEquals(graph.getAdjacentNodesOf(node), adjacents);
    }

    @Test
    final void testGetPredecessorNodesOf() {
        Graph<Integer> graph = getValidGraph();
        //noinspection unchecked
        assertThrows(UnsupportedOperationException.class, () -> graph.getPredecessorNodesOf((GraphNode<Integer>) graph.getNodes().toArray()[0]));
    }

    @Test
    final void testGetEdges() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            assertEquals(graph.getEdges(), edgeList);
            //noinspection unchecked
            GraphNode<Integer> node1 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            //noinspection unchecked
            GraphNode<Integer> node2 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, false, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
    }

    @Test
    final void testAddEdge() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.addEdge(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphNode<Integer>> nodes = graph.getNodes();
        for(int i=0;i<4000;++i){
            boolean result;
            do {
                //noinspection unchecked
                GraphNode<Integer> node1 = (GraphNode<Integer>) nodes.toArray()[generator.nextInt(nodes.size())];
                //noinspection unchecked
                GraphNode<Integer> node2 = (GraphNode<Integer>) nodes.toArray()[generator.nextInt(nodes.size())];
                result = graph.addEdge(new GraphEdge<>(node1, node2, false, generator.nextInt()));
                if(result){
                    assertFalse(graph.addEdge(new GraphEdge<>(node1, node2, false, generator.nextInt())));
                }
            }while(!result);
        }
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>((GraphNode<Integer>) graph.getNodes().toArray()[0],
                        (GraphNode<Integer>) graph.getNodes().toArray()[1],
                        true,
                        generator.nextInt())));
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>(new GraphNode<>(-1),
                        (GraphNode<Integer>) graph.getNodes().toArray()[0],
                        false,
                        generator.nextInt())));
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>((GraphNode<Integer>) graph.getNodes().toArray()[0],
                        new GraphNode<>(-1),
                        false,
                        generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>(new GraphNode<>(-1),
                        new GraphNode<>(-2),
                        false,
                        generator.nextInt())));
    }

    @Test
    final void testRemoveEdge() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.removeEdge(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            //noinspection unchecked
            GraphNode<Integer> node1 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            //noinspection unchecked
            GraphNode<Integer> node2 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, false, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(new GraphEdge<>(
                (GraphNode<Integer>) graph.getNodes().toArray()[0],
                new GraphNode<>(-1),
                false,
                generator.nextInt())));
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(new GraphEdge<>(
                new GraphNode<>(-1),
                (GraphNode<Integer>) graph.getNodes().toArray()[0],
                false,
                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(new GraphEdge<>(
                new GraphNode<>(-1),
                new GraphNode<>(-2),
                false,
                generator.nextInt())));
        for(GraphEdge<Integer> edge: edgeList){
            assertTrue(graph.removeEdge(edge));
            assertFalse(graph.removeEdge(edge));
        }
    }

    @Test
    final void testContainsEdge() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.containsEdge(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            //noinspection unchecked
            GraphNode<Integer> node1 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            //noinspection unchecked
            GraphNode<Integer> node2 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, false, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.containsEdge(new GraphEdge<>(
                (GraphNode<Integer>) graph.getNodes().toArray()[0],
                new GraphNode<>(-1),
                false,
                generator.nextInt())));
        //noinspection unchecked
        assertThrows(IllegalArgumentException.class, () -> graph.containsEdge(new GraphEdge<>(
                new GraphNode<>(-1),
                (GraphNode<Integer>) graph.getNodes().toArray()[0],
                false,
                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.containsEdge(new GraphEdge<>(
                new GraphNode<>(-1),
                new GraphNode<>(-2),
                false,
                generator.nextInt())));
        for(GraphEdge<Integer> edge: edgeList){
            assertTrue(graph.containsEdge(edge));
            graph.removeEdge(edge);
            assertFalse(graph.containsEdge(edge));
        }
    }

    @Test
    final void testGetEdgesOf() {
        Graph<Integer> graph = new MapAdjacentListUndirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getEdgesOf(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            //noinspection unchecked
            GraphNode<Integer> node1 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            //noinspection unchecked
            GraphNode<Integer> node2 = (GraphNode<Integer>) graph.getNodes().toArray()[generator.nextInt(graph.nodeCount())];
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, false, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.getEdgesOf(new GraphNode<>(-1)));
        //noinspection unchecked
        GraphNode<Integer> node = (GraphNode<Integer>) graph.getNodes().toArray()[0];
        Set<GraphEdge<Integer>> nodeEdges = new HashSet<>();
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode1().equals(node)){
                nodeEdges.add(edge);
            }else if(edge.getNode2().equals(node)){
                nodeEdges.add(edge);
            }
        }
        assertEquals(graph.getEdgesOf(node), nodeEdges);
    }

    @Test
    final void testGetIngoingEdgesOf() {
        Graph<Integer> graph = getValidGraph();
        //noinspection unchecked
        assertThrows(UnsupportedOperationException.class, () -> graph.getIngoingEdgesOf((GraphNode<Integer>) graph.getNodes().toArray()[0]));
    }

    @Test
    final void testMapAdjacentListUndirectedGraph() {
        //constructor cannot fail
        new MapAdjacentListUndirectedGraph<Integer>();
    }

}
