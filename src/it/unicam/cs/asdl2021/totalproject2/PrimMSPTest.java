package it.unicam.cs.asdl2021.totalproject2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class PrimMSPTest {

    @Test
    final void testFindMSP1() {
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
        PrimMSP<String> alg = new PrimMSP<>();
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
        Graph<String> gr = new MapAdjacentListUndirectedGraph<>();
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
        PrimMSP<String> alg = new PrimMSP<>();
        alg.computeMSP(gr, a);
        assertNull(a.getPrevious());
        assertSame(b.getPrevious(), a);
        assertSame(c.getPrevious(), b);
        assertSame(d.getPrevious(), b);
    }

    @Test
    final void testFindMSP3() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<>();
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
        PrimMSP<String> alg = new PrimMSP<>();
        alg.computeMSP(gr, b);
        assertSame(a.getPrevious(), b);
        assertNull(b.getPrevious());
        assertSame(c.getPrevious(), b);
        assertSame(d.getPrevious(), b);
    }

    @Test
    final void testFindMSP4() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<>();
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
        PrimMSP<String> alg = new PrimMSP<>();
        alg.computeMSP(gr, c);
        assertSame(a.getPrevious(), b);
        assertSame(b.getPrevious(), c);
        assertNull(c.getPrevious());
        assertSame(d.getPrevious(), b);
    }

    @Test
    final void testFindMSP5() {
        Graph<String> gr = new MapAdjacentListUndirectedGraph<>();
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
        PrimMSP<String> alg = new PrimMSP<>();
        alg.computeMSP(gr, d);
        assertSame(a.getPrevious(), b);
        assertSame(b.getPrevious(), d);
        assertSame(c.getPrevious(), b);
        assertNull(d.getPrevious());
        assertThrows(NullPointerException.class,()-> alg.computeMSP(null,null));//eccezzione perch� � null
        assertThrows(NullPointerException.class,()-> alg.computeMSP(null,c ));//eccezzione perch� � null
        assertThrows(NullPointerException.class,()-> alg.computeMSP(gr,null));//eccezzione perch� � null
        assertThrows(IllegalArgumentException.class,()-> alg.computeMSP(gr, new GraphNode<>("pi")));//il nuovo nodo non esiste in gr
        Graph<String> gr1 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> e = new GraphNode<>("a");
        gr1.addNode(e);
        GraphNode<String> f = new GraphNode<>("b");
        gr1.addNode(f);
        GraphNode<String> g = new GraphNode<>("2a");
        gr.addNode(g);
        GraphNode<String> h = new GraphNode<>("b");
        gr1.addNode(h);
        gr1.addEdge(new GraphEdge<>(e, f, true, 1));
        assertThrows(IllegalArgumentException.class,()-> alg.computeMSP(gr1,e));//eccezzione il grafo è orientato
        gr.addEdge(new GraphEdge<>(g, h, false, -1));
        assertThrows(IllegalArgumentException.class,()-> alg.computeMSP(gr,g));//eccezzione un arco ha peso negativo
        Graph<String> gr2 = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> e2 = new GraphNode<String>("a");
        gr2.addNode(e2);
        GraphNode<String> f2 = new GraphNode<String>("b");
        gr2.addNode(f2);
        GraphNode<String> g2 = new GraphNode<String>("2a");
        gr2.addNode(g2);
        GraphNode<String> h2 = new GraphNode<String>("b");
        gr2.addNode(h2);
        gr2.addEdge(new GraphEdge<String>(e, f, false));
        assertThrows(IllegalArgumentException.class,()-> alg.computeMSP(gr2,g2));//eccezzione un arco senza peso
    }
}