package it.unicam.cs.asdl2021.totalproject2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * 
 * @author Template: Luca Tesei
 *
 */
class DijkstraShortestPathComputerTest {

    // TODO implementare: inserire i test che controllano le eccezioni

    // TODO implementare: inserire i test che controllano lastSource e
    // isComputed

    @Test
    final void testGetShortestPathTo1() {
        Graph<String> g = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> ns = new GraphNode<>("s");
        g.addNode(ns);
        GraphNode<String> nu = new GraphNode<>("u");
        g.addNode(nu);
        GraphEdge<String> esu = new GraphEdge<>(ns, nu, true, 10.1);
        g.addEdge(esu);
        GraphNode<String> nx = new GraphNode<>("x");
        g.addNode(nx);
        GraphEdge<String> esx = new GraphEdge<>(ns, nx, true, 5.12);
        g.addEdge(esx);
        GraphEdge<String> eux = new GraphEdge<>(nu, nx, true, 2.05);
        g.addEdge(eux);
        GraphEdge<String> exu = new GraphEdge<>(nx, nu, true, 3.04);
        g.addEdge(exu);
        GraphNode<String> ny = new GraphNode<>("y");
        g.addNode(ny);
        GraphEdge<String> exy = new GraphEdge<>(nx, ny, true, 2.0);
        g.addEdge(exy);
        GraphEdge<String> eys = new GraphEdge<>(ny, ns, true, 7.03);

        g.addEdge(eys);
        GraphNode<String> nv = new GraphNode<>("v");
        g.addNode(nv);
        GraphEdge<String> euv = new GraphEdge<>(nu, nv, true, 1.0);
        g.addEdge(euv);
        GraphEdge<String> exv = new GraphEdge<>(nx, nv, true, 9.05);
        g.addEdge(exv);
        GraphEdge<String> eyv = new GraphEdge<>(ny, nv, true, 6.0);
        g.addEdge(eyv);
        GraphEdge<String> evy = new GraphEdge<>(nv, ny, true, 4.07);
        g.addEdge(evy);
        DijkstraShortestPathComputer<String> c = new DijkstraShortestPathComputer<String>(
                g);
        GraphNode<String> nsTest = new GraphNode<>("s");
        c.computeShortestPathsFrom(nsTest);
        List<GraphEdge<String>> pathTest = new ArrayList<>();
        assertEquals(pathTest, c.getShortestPathTo(nsTest));
        GraphNode<String> nuTest = new GraphNode<>("u");
        GraphNode<String> nxTest = new GraphNode<>("x");
        GraphEdge<String> esxTest = new GraphEdge<>(nsTest, nxTest, true,
                5.12);
        pathTest.add(esxTest);
        assertEquals(pathTest, c.getShortestPathTo(nxTest));
        GraphEdge<String> exuTest = new GraphEdge<>(nxTest, nuTest, true,
                3.04);
        pathTest.add(exuTest);
        assertEquals(pathTest, c.getShortestPathTo(nuTest));
        GraphNode<String> nvTest = new GraphNode<>("v");
        GraphEdge<String> euvTest = new GraphEdge<>(nuTest, nvTest, true,
                1.0);
        pathTest.add(euvTest);
        assertEquals(pathTest, c.getShortestPathTo(nvTest));
        pathTest.clear();
        pathTest.add(esxTest);
        GraphNode<String> nyTest = new GraphNode<>("y");
        GraphEdge<String> exyTest = new GraphEdge<>(nxTest, nyTest, true,
                2.0);
        pathTest.add(exyTest);
        assertEquals(pathTest, c.getShortestPathTo(nyTest));
    }

    @Test
    final void testGetShortestPathTo2() {
        Graph<String> g = new AdjacencyMatrixDirectedGraph<>();
        GraphNode<String> ns = new GraphNode<>("s");
        g.addNode(ns);
        GraphNode<String> nu = new GraphNode<>("u");
        g.addNode(nu);
        GraphEdge<String> esu = new GraphEdge<>(ns, nu, true, 10.1);
        g.addEdge(esu);
        GraphNode<String> nx = new GraphNode<>("x");
        g.addNode(nx);
        GraphEdge<String> esx = new GraphEdge<>(ns, nx, true, 5.12);
        g.addEdge(esx);
        GraphEdge<String> eux = new GraphEdge<>(nu, nx, true, 2.05);
        g.addEdge(eux);
        GraphEdge<String> exu = new GraphEdge<>(nx, nu, true, 3.04);
        g.addEdge(exu);
        GraphNode<String> ny = new GraphNode<>("y");
        g.addNode(ny);
        GraphEdge<String> exy = new GraphEdge<>(nx, ny, true, 2.0);
        g.addEdge(exy);
        GraphEdge<String> eys = new GraphEdge<>(ny, ns, true, 7.03);

        g.addEdge(eys);
        GraphNode<String> nv = new GraphNode<>("v");
        g.addNode(nv);
        GraphEdge<String> euv = new GraphEdge<>(nu, nv, true, 1.0);
        g.addEdge(euv);
        GraphEdge<String> exv = new GraphEdge<>(nx, nv, true, 9.05);
        g.addEdge(exv);
        GraphEdge<String> eyv = new GraphEdge<>(ny, nv, true, 6.0);
        g.addEdge(eyv);
        GraphEdge<String> evy = new GraphEdge<>(nv, ny, true, 4.07);
        g.addEdge(evy);
        GraphNode<String> np = new GraphNode<>("p");
        g.addNode(np);
        GraphEdge<String> epv = new GraphEdge<>(np, nv, true, 1.0);
        g.addEdge(epv);
        // p è collegato a v, ma non è raggiungibile da s
        DijkstraShortestPathComputer<String> c = new DijkstraShortestPathComputer<String>(
                g);
        GraphNode<String> nsTest = new GraphNode<>("s");
        c.computeShortestPathsFrom(nsTest);
        GraphNode<String> npTest = new GraphNode<>("p");
        assertNull(c.getShortestPathTo(npTest));
    }
}
