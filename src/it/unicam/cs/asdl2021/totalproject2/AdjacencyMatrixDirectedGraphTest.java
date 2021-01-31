package it.unicam.cs.asdl2021.totalproject2;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class AdjacencyMatrixDirectedGraphTest {
    @Test
    final void testNodeCount() {
        //assuming that constructor works
        AdjacencyMatrixDirectedGraph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            assertEquals(i, graph.nodeCount());
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        assertEquals(100, graph.nodeCount());
    }

    @Test
    final void testEdgeCount() {
        // assuming that constructor works
        AdjacencyMatrixDirectedGraph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        for(int i=0;i<4000;++i){
            assertEquals(i, graph.edgeCount());
            boolean result;
            do {
                // assuming that getNodeAtIndex works
                GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
                GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
                // assuming that addEdge works
                result = graph.addEdge(new GraphEdge<>(node1, node2, true, generator.nextInt()));
            }while (!result);
        }
        assertEquals(4000, graph.edgeCount());
    }

    @Test
    final void testClear() {
        // assuming that constructor works
        AdjacencyMatrixDirectedGraph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        for(int i=0;i<4000;++i){
            boolean result;
            do {
                // assuming that getNodeAtIndex works
                GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
                GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
                // assuming that addEdge works
                result = graph.addEdge(new GraphEdge<>(node1, node2, true, generator.nextInt()));
            }while (!result);
        }
        assertTrue(graph.nodeCount()>0);
        assertTrue(graph.edgeCount()>0);
        graph.clear();
        assertEquals(graph.edgeCount(), 0);
        assertEquals(graph.nodeCount(), 0);
    }

    @Test
    final void testIsDirected() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertTrue(graph.isDirected());
    }

    @Test
    final void testGetNodes() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            assertEquals(graph.getNodes().size(), i);
            assertEquals(graph.getNodes(), nodeList);
            // assuming that addNode works
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            nodeList.add(node);
            graph.addNode(node);
        }
        assertEquals(graph.getNodes().size(), 100);
        assertEquals(graph.getNodes(), nodeList);
        graph.clear();
        nodeList.clear();
        assertEquals(graph.getNodes().size(), 0);
        assertEquals(graph.getNodes(), nodeList);
    }

    @Test
    final void testAddNode() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
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
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(UnsupportedOperationException.class, () -> graph.removeNode(null));
        // no further checks are needed, since the function is unsupported
    }

    @Test
    final void testContainsNode() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.containsNode(null));
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            // assuming that addNode works
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            graph.addNode(node);
            nodeList.add(node);
        }
        for(GraphNode<Integer> node: nodeList){
            assertTrue(graph.containsNode(node));
        }
        boolean falseTested = false;
        for(int i=0;i<1000;++i){
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            if(!nodeList.contains(node)){
                assertFalse(graph.containsNode(node));
                falseTested=true;
            }
        }
        assertTrue(falseTested);
    }

    @Test
    final void testGetNodeOf() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getNodeOf(null));
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            // assuming that addNode works
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            graph.addNode(node);
            nodeList.add(node);
        }
        for(GraphNode<Integer> node: nodeList){
            assertEquals(graph.getNodeOf(node.getLabel()), node);
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
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getNodeIndexOf(null));
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            // assuming that addNode works
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            graph.addNode(node);
            nodeList.add(node);
        }
        for(GraphNode<Integer> node: nodeList){
            int index = graph.getNodeIndexOf(node.getLabel());
            assertTrue(index>=0 && index<nodeList.size());
            //assuming that getNodeAtIndexWorks
            assertEquals(graph.getNodeAtIndex(index), node);
        }
        boolean falseTested = false;
        for(int i=0;i<1000;++i){
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            if(!nodeList.contains(node)){
                assertThrows(IllegalArgumentException.class, () -> graph.getNodeIndexOf(node.getLabel()));
                falseTested=true;
            }
        }
        assertTrue(falseTested);
    }

    @Test
    final void testGetNodeAtIndex() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getNodeIndexOf(null));
        Random generator = new Random(123456);
        Set<GraphNode<Integer>> nodeList = new HashSet<>();
        for(int i=0;i<100;++i){
            // assuming that addNode works
            GraphNode<Integer> node = new GraphNode<>(generator.nextInt());
            graph.addNode(node);
            nodeList.add(node);
        }
        for(GraphNode<Integer> node: nodeList){
            //assuming that getNodeIndexOf
            int index = graph.getNodeIndexOf(node.getLabel());
            assertEquals(graph.getNodeAtIndex(index), node);
        }
        boolean testedIf = false, testedElse=false;
        for(int i=0;i<1000;++i){
            int index = generator.nextInt();
            if(nodeList.size()<=index){
                assertThrows(IndexOutOfBoundsException.class, () -> graph.getNodeAtIndex(index));
                testedIf=true;
            }else{
                assertThrows(IndexOutOfBoundsException.class, () -> graph.getNodeAtIndex(-index));
                testedElse=true;
            }
        }
        assertTrue(testedIf && testedElse);
    }

    @Test
    final void testGetEdge() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        assertThrows(NullPointerException.class, () ->
                graph.getEdge(null, new GraphNode<>(new Random().nextInt()))
        );
        assertThrows(NullPointerException.class, () ->
                graph.getEdge(new GraphNode<>(new Random().nextInt()), null)
        );
        assertThrows(NullPointerException.class, () -> graph.getEdge(null, null));
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }

        assertThrows(IllegalArgumentException.class, () ->
                graph.getEdge(new GraphNode<>(-1),graph.getNodeAtIndex(0))
        );
        assertThrows(IllegalArgumentException.class, () ->
                graph.getEdge(graph.getNodeAtIndex(0), new GraphNode<>(-1))
        );
        assertThrows(IllegalArgumentException.class, () ->
                graph.getEdge(new GraphNode<>(-1), new GraphNode<>(-2))
        );
        assertEquals(graph.edgeCount(), 0);
        for(int i=0;i<graph.nodeCount();++i){
            for(int j=0;j<graph.nodeCount(); ++j){
                // assuming that getNodeAtIndex works
                assertNull(graph.getEdge(graph.getNodeAtIndex(i), graph.getNodeAtIndex(j)));
            }
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertEquals(graph.edgeCount(), edgeList.size());
        for(GraphEdge<Integer> edge: edgeList){
            assertEquals(edge, graph.getEdge(edge.getNode1(), edge.getNode2()));
        }
    }

    @Test
    final void testGetEdgeAtNodeIndexes() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        assertEquals(graph.edgeCount(), 0);
        for(int i=0;i<graph.nodeCount();++i){
            for(int j=0;j<graph.nodeCount(); ++j){
                // assuming that getNodeAtIndex works
                assertNull(graph.getEdgeAtNodeIndexes(i, j));
            }
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertEquals(graph.edgeCount(), edgeList.size());
        for(GraphEdge<Integer> edge: edgeList){
            assertEquals(edge, graph.getEdgeAtNodeIndexes(
                    graph.getNodeIndexOf(edge.getNode1().getLabel()),
                    graph.getNodeIndexOf(edge.getNode2().getLabel())
            ));
        }
        for(int i=1;i<graph.nodeCount()*2;++i){
            for(int j=1;j<graph.nodeCount()*2;++j) {
                int finalI = i;
                int finalJ = j;
                if (i < graph.nodeCount()) {
                    if(j<graph.nodeCount()) {
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(finalI, -finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, -finalJ));
                    }else{
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(finalI, finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, -finalJ));
                    }
                } else {
                    if(j<graph.nodeCount()) {
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(finalI, -finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, finalJ));
                    }else{
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(finalI, finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(finalI, -finalJ));
                        assertThrows(IndexOutOfBoundsException.class, () -> graph.getEdgeAtNodeIndexes(-finalI, -finalJ));
                    }
                }
            }
        }
    }

    @Test
    final void testGetAdjacentNodesOf() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getAdjacentNodesOf(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.getAdjacentNodesOf(new GraphNode<>(-1)));
        // finding adiacent nodes
        Set<GraphNode<Integer>> adjacents = new HashSet<>();
        GraphNode<Integer> node = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode1().equals(node)){
                adjacents.add(edge.getNode2());
            }
        }
        assertEquals(graph.getAdjacentNodesOf(node), adjacents);
    }

    @Test
    final void testGetPredecessorNodesOf() {
        //assuming that constructor works
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getPredecessorNodesOf(null));
        assertThrows(IllegalArgumentException.class, () -> graph.getPredecessorNodesOf(new GraphNode<>(-1)));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        // finding adiacent nodes
        Set<GraphNode<Integer>> adjacents = new HashSet<>();
        GraphNode<Integer> node = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode2().equals(node)){
                adjacents.add(edge.getNode1());
            }
        }
        assertEquals(graph.getPredecessorNodesOf(node), adjacents);
    }

    @Test
    final void testGetEdges() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            assertEquals(graph.getEdges(), edgeList);
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
    }

    @Test
    final void testAddEdge() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.addEdge(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        List<GraphEdge<Integer>> edgesList = new ArrayList<>();
        for(int i=0;i<4000;++i){
            GraphEdge<Integer> edge;
            do {
                // assuming that getNodeAtIndex works
                GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
                GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
                // assuming that addEdge works
                edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            }while (edgesList.contains(edge));
            edgesList.add(edge);
            assertTrue(graph.addEdge(edge));
            assertFalse(graph.addEdge(edge));
        }
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>(graph.getNodeAtIndex(0),
                                graph.getNodeAtIndex(1),
                                false,
                                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>(new GraphNode<>(-1),
                                graph.getNodeAtIndex(0),
                                true,
                                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>(graph.getNodeAtIndex(0),
                                new GraphNode<>(-1),
                                true,
                                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.addEdge(
                new GraphEdge<>(new GraphNode<>(-1),
                                new GraphNode<>(-2),
                                true,
                                generator.nextInt())));
    }

    @Test
    final void testRemoveEdge() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.removeEdge(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(new GraphEdge<>(
                                graph.getNodeAtIndex(0),
                                new GraphNode<>(-1),
                                true,
                                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(new GraphEdge<>(
                                new GraphNode<>(-1),
                                graph.getNodeAtIndex(0),
                                true,
                                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.removeEdge(new GraphEdge<>(
                                new GraphNode<>(-1),
                                new GraphNode<>(-2),
                                true,
                                generator.nextInt())));
        for(GraphEdge<Integer> edge: edgeList){
            assertTrue(graph.removeEdge(edge));
            assertFalse(graph.removeEdge(edge));
        }
    }

    @Test
    final void testContainsEdge() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.containsEdge(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.containsEdge(new GraphEdge<>(
                graph.getNodeAtIndex(0),
                new GraphNode<>(-1),
                true,
                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.containsEdge(new GraphEdge<>(
                new GraphNode<>(-1),
                graph.getNodeAtIndex(0),
                true,
                generator.nextInt())));
        assertThrows(IllegalArgumentException.class, () -> graph.containsEdge(new GraphEdge<>(
                new GraphNode<>(-1),
                new GraphNode<>(-2),
                true,
                generator.nextInt())));
        for(GraphEdge<Integer> edge: edgeList){
            assertTrue(graph.containsEdge(edge));
            graph.removeEdge(edge);
            assertFalse(graph.containsEdge(edge));
        }
    }

    @Test
    final void testGetEdgesOf() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getEdgesOf(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.getEdgesOf(new GraphNode<>(-1)));
        GraphNode<Integer> node = graph.getNodeAtIndex(0);
        Set<GraphEdge<Integer>> nodeEdges = new HashSet<>();
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode1().equals(node)){
                nodeEdges.add(edge);
            }
        }
        assertEquals(graph.getEdgesOf(node), nodeEdges);
    }

    @Test
    final void testGetIngoingEdgesOf() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getIngoingEdgesOf(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.getIngoingEdgesOf(new GraphNode<>(-1)));
        GraphNode<Integer> node = graph.getNodeAtIndex(0);
        Set<GraphEdge<Integer>> nodeEdges = new HashSet<>();
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode2().equals(node)){
                nodeEdges.add(edge);
            }
        }
        assertEquals(graph.getIngoingEdgesOf(node), nodeEdges);
    }

    @Test
    final void testAdjacencyMatrixDirectedGraph() {
        // this function cannot fail
        // constructor doesn't give any errors
        // and parent class Graph uses the default constructor
        new AdjacencyMatrixDirectedGraph<>();
    }

    @Test
    final void testSize() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertEquals(graph.size(), 0);
        GraphNode<Integer> node = new GraphNode<>(0);
        graph.addNode(node);
        assertEquals(graph.size(), 1);
        graph.addEdge(new GraphEdge<>(node, node, true, 12));
        assertEquals(graph.size(), 2);
        graph.removeEdge(new GraphEdge<>(node, node, true, 13));
        assertEquals(graph.size(), 1);
        graph.clear();
        assertEquals(graph.size(), 0);
    }

    @Test
    final void testIsEmpty() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertTrue(graph.isEmpty());
        graph.addNode(new GraphNode<>(0));
        assertFalse(graph.isEmpty());
        graph.clear();
        assertTrue(graph.isEmpty());
    }

    @Test
    final void testGetDegreeOf() {
        Graph<Integer> graph = new AdjacencyMatrixDirectedGraph<>();
        assertThrows(NullPointerException.class, () -> graph.getDegreeOf(null));
        Random generator = new Random(123456);
        for(int i=0;i<100;++i){
            // assuming that addNode works
            graph.addNode(new GraphNode<>(generator.nextInt()));
        }
        Set<GraphEdge<Integer>> edgeList = new HashSet<>();
        for(int i=0;i<4000;++i){
            // assuming that getNodeAtIndex works
            GraphNode<Integer> node1 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            GraphNode<Integer> node2 = graph.getNodeAtIndex(generator.nextInt(graph.nodeCount()));
            // assuming that addEdge works
            GraphEdge<Integer> edge = new GraphEdge<>(node1, node2, true, generator.nextInt());
            edgeList.add(edge);
            graph.addEdge(edge);
        }
        assertThrows(IllegalArgumentException.class, () -> graph.getDegreeOf(new GraphNode<>(-1)));
        GraphNode<Integer> node = graph.getNodeAtIndex(0);
        int count = 0;
        for(GraphEdge<Integer> edge: edgeList){
            if(edge.getNode1().equals(node) || edge.getNode2().equals(node)){
                ++count;
            }
        }
        assertEquals(graph.getDegreeOf(graph.getNodeAtIndex(0)), count);
    }

}
