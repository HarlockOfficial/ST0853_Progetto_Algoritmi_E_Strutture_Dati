package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class KruskalMSPTest {

    // TODO implementare: aggiungere i test che controllano le eccezioni

    // TODO implementare: aggiungere altri test che controllano il risultato

    @Test
    final void testComputeMSP() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<>();
        GraphNode<String> a = new GraphNode<>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<>("d");
        gr.addNode(d);
        GraphNode<String> e = new GraphNode<>("e");
        gr.addNode(e);
        GraphNode<String> f = new GraphNode<>("f");
        gr.addNode(f);
        GraphNode<String> g = new GraphNode<>("g");
        gr.addNode(g);
        GraphNode<String> h = new GraphNode<>("h");
        gr.addNode(h);
        GraphNode<String> i = new GraphNode<>("i");
        gr.addNode(i);
        gr.addEdge(new GraphEdge<>(a, b, false, 4));
        gr.addEdge(new GraphEdge<>(a, h, false, 8.5));
        gr.addEdge(new GraphEdge<>(b, h, false, 11));
        gr.addEdge(new GraphEdge<>(b, c, false, 8));
        gr.addEdge(new GraphEdge<>(c, i, false, 2));
        gr.addEdge(new GraphEdge<>(c, d, false, 7));
        gr.addEdge(new GraphEdge<>(c, f, false, 4));
        gr.addEdge(new GraphEdge<>(d, f, false, 14));
        gr.addEdge(new GraphEdge<>(d, e, false, 9));
        gr.addEdge(new GraphEdge<>(e, f, false, 10));
        gr.addEdge(new GraphEdge<>(f, g, false, 2));
        gr.addEdge(new GraphEdge<>(g, i, false, 6));
        gr.addEdge(new GraphEdge<>(g, h, false, 1));
        gr.addEdge(new GraphEdge<>(h, i, false, 7));
        KruskalMSP<String> alg = new KruskalMSP<String>();
        Set<GraphEdge<String>> result = new HashSet<>();
        result.add(new GraphEdge<>(a, b, false, 4));
        result.add(new GraphEdge<>(b, c, false, 8));
        result.add(new GraphEdge<>(c, i, false, 2));
        result.add(new GraphEdge<>(c, d, false, 7));
        result.add(new GraphEdge<>(c, f, false, 4));
        result.add(new GraphEdge<>(d, e, false, 9));
        result.add(new GraphEdge<>(f, g, false, 2));
        result.add(new GraphEdge<>(g, h, false, 1));
        assertEquals(result, alg.computeMSP(gr));
    }

}
