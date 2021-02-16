package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * <p>
 * L'algoritmo usa una coda di min priorità tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poiché la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell'esecuzione dell'algoritmo è necessario utilizzare l'operazione di
 * decreasePriority.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class PrimMSP<L> {

    /*
     * Coda di priorità che va usata dall'algoritmo. La variabile istanza è
     * protected solo per scopi di testing JUnit.
     */
    protected BinaryHeapMinPriorityQueue queue;

    /**
     * Crea un nuovo algoritmo e inizializza la coda di priorità con una coda
     * vuota.
     */
    public PrimMSP() {
        this.queue = new BinaryHeapMinPriorityQueue();
    }

    /**
     * Utilizza l'algoritmo goloso di Prim per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non negativi.
     * Dopo l'esecuzione del metodo nei nodi del grafo il campo previous deve
     * contenere un puntatore a un nodo in accordo all'albero di copertura
     * minimo calcolato, la cui radice è il nodo sorgente passato.
     *
     * @param g un grafo non orientato, pesato, con pesi non negativi
     * @param s il nodo del grafo g sorgente, cioè da cui parte il calcolo
     *          dell'albero di copertura minimo. Tale nodo sarà la radice
     *          dell'albero di copertura trovato
     * @throws NullPointerException     se il grafo g o il nodo sorgente s sono nulli
     * @throws IllegalArgumentException se il nodo sorgente s non esiste in g
     * @throws IllegalArgumentException se il grafo g è orientato, non pesato o
     *                                  con pesi negativi
     */
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
        if (g == null || s == null)
            throw new NullPointerException();
        if (!g.containsNode(s) || g.isDirected())
            throw new IllegalArgumentException();
        for (GraphEdge<L> e : g.getEdges()) {
            if (!e.hasWeight())
                throw new IllegalArgumentException();
            if (e.getWeight() < 0)
                throw new IllegalArgumentException();
        }

        for (GraphNode<L> n : g.getNodes()) //mette tutte le priorità a infinito e i precedenti a null
        {
            n.setPriority(Double.POSITIVE_INFINITY);
            n.setPrevious(null);
        }
        s.setPriority(0);

        for (GraphNode<L> node : g.getNodes())//inserisco tutti i nodi alla queue
            queue.insert(node);

        GraphNode<L> appNode;
        while (queue.size() != 0) //ripete finche ci sono elementi nella size
        {
            //noinspection unchecked
            appNode = (GraphNode<L>) queue.extractMinimum(); //estrae il nodo con priorita minore
            for (GraphNode<L> n : g.getAdjacentNodesOf(appNode)) {
                GraphEdge<L> appArco = null;

                for (GraphEdge<L> edge : g.getEdgesOf(appNode)) {
                    if (edge.getNode1().equals(n) || edge.getNode2().equals(n)) //trova l'arco che collega il minimo estratto con il suo adiacente
                        appArco = edge;
                }

                if (quequeContains(appNode) && appArco != null && appArco.getWeight() < n.getPriority()) {
                    n.setPrevious(appNode);
                    n.setPriority(appArco.getWeight());
                }
            }
        }
    }

    private boolean quequeContains(GraphNode<L> a) //controlla se la queue contiene il nodo
    {
        ArrayList<PriorityQueueElement> elements = new ArrayList<>();
        try {
            while (true) {
                if (a.equals(queue.minimum())) {
                    for (PriorityQueueElement element : elements) {
                        queue.insert(element);
                    }
                    return true;
                }
                elements.add(queue.extractMinimum());
            }
        } catch (NoSuchElementException e) {
            for (PriorityQueueElement element : elements) {
                queue.insert(element);
            }
            return false;
        }
        /*
        for (PriorityQueueElement n : queue.getTernaryHeap()) {
            if (n.equals(a))
                return true;
        }
        return false;
        */
    }

}