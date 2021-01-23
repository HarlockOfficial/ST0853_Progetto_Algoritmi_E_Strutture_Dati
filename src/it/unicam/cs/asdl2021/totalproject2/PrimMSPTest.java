package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class PrimMSPTest {

    // TODO implementare: fare i test per le eccezioni

    @Test
    final void testFindMSP1() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
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
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, a);
        assertNull(a.getPrevious());
        assertSame(b.getPrevious(), a);
        assertSame(c.getPrevious(), b);
        assertSame(d.getPrevious(), c);
        assertSame(e.getPrevious(), d);
        assertSame(f.getPrevious(), c);
        assertSame(g.getPrevious(), f);
        assertSame(h.getPrevious(), g);
        assertSame(i.getPrevious(), c);
    }

    @Test
    final void testFindMSP2() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<>(a, b, false, 1));
        gr.addEdge(new GraphEdge<>(a, c, false, 5));
        gr.addEdge(new GraphEdge<>(b, d, false, 2));
        gr.addEdge(new GraphEdge<>(b, c, false, 3));
        gr.addEdge(new GraphEdge<>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, a);
        assertNull(a.getPrevious());
        assertSame(b.getPrevious(), a);
        assertSame(c.getPrevious(), b);
        assertSame(d.getPrevious(), b);
    }

    @Test
    final void testFindMSP3() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<>(a, b, false, 1));
        gr.addEdge(new GraphEdge<>(a, c, false, 5));
        gr.addEdge(new GraphEdge<>(b, d, false, 2));
        gr.addEdge(new GraphEdge<>(b, c, false, 3));
        gr.addEdge(new GraphEdge<>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, b);
        assertSame(a.getPrevious(), b);
        assertNull(b.getPrevious());
        assertSame(c.getPrevious(), b);
        assertSame(d.getPrevious(), b);
    }

    @Test
    final void testFindMSP4() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<>(a, b, false, 1));
        gr.addEdge(new GraphEdge<>(a, c, false, 5));
        gr.addEdge(new GraphEdge<>(b, d, false, 2));
        gr.addEdge(new GraphEdge<>(b, c, false, 3));
        gr.addEdge(new GraphEdge<>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, c);
        assertSame(a.getPrevious(), b);
        assertSame(b.getPrevious(), c);
        assertNull(c.getPrevious());
        assertSame(d.getPrevious(), b);
    }

    @Test
    final void testFindMSP5() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<String>();
        GraphNode<String> a = new GraphNode<>("a");
        gr.addNode(a);
        GraphNode<String> b = new GraphNode<>("b");
        gr.addNode(b);
        GraphNode<String> c = new GraphNode<>("c");
        gr.addNode(c);
        GraphNode<String> d = new GraphNode<>("d");
        gr.addNode(d);
        gr.addEdge(new GraphEdge<>(a, b, false, 1));
        gr.addEdge(new GraphEdge<>(a, c, false, 5));
        gr.addEdge(new GraphEdge<>(b, d, false, 2));
        gr.addEdge(new GraphEdge<>(b, c, false, 3));
        gr.addEdge(new GraphEdge<>(c, d, false, 4));
        PrimMSP<String> alg = new PrimMSP<String>();
        alg.computeMSP(gr, d);
        assertSame(a.getPrevious(), b);
        assertSame(b.getPrevious(), d);
        assertSame(c.getPrevious(), b);
        assertNull(d.getPrevious());
    }
}
