package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Implementazione dell'algoritmo di Floyd-Warshall per il calcolo di cammini
 * minimi tra tutte le coppie di nodi in un grafo pesato che può contenere anche
 * pesi negativi, ma non cicli di peso negativo.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class FloydWarshallAllPairsShortestPathComputer<L> {

    /*
     * Il grafo su cui opera questo calcolatore.
     */
    private final Graph<L> graph;
    /*
     * Matrice dei costi dei cammini minimi. L'elemento in posizione i,j
     * corrisponde al costo di un cammino minimo tra il nodo i e il nodo j, dove
     * i e j sono gli interi associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private final double[][] costMatrix;
    /*
     * Matrice dei predecessori. L'elemento in posizione i,j è -1 se non esiste
     * nessun cammino tra i e j oppure corrisponde all'indice di un nodo che
     * precede il nodo j in un qualche cammino minimo da i a j. Si intende che i
     * e j sono gli indici associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private final int[][] predecessorMatrix;
    private boolean computato = false;

    /**
     * Crea un calcolatore di cammini minimi fra tutte le coppie di nodi per un
     * grafo orientato e pesato. Non esegue il calcolo, che viene eseguito
     * invocando successivamente il metodo computeShortestPaths().
     *
     * @param graph il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException     se il grafo passato è nullo
     * @throws IllegalArgumentException se il grafo passato è vuoto
     * @throws IllegalArgumentException se il grafo passato non è orientato
     * @throws IllegalArgumentException se il grafo passato non è pesato,
     *                                  cioè esiste almeno un arco il cui
     *                                  peso è {@code Double.NaN}
     */
    public FloydWarshallAllPairsShortestPathComputer(Graph<L> graph) {
        if (graph == null) {
            throw new NullPointerException();
        }
        if (graph.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (!graph.isDirected()) {
            throw new IllegalArgumentException();
        }
        for (GraphEdge<L> e : graph.getEdges()) {
            if (!e.hasWeight()) {
                throw new IllegalArgumentException();
            }
        }

        this.graph = graph;
        costMatrix = new double[graph.nodeCount()][graph.nodeCount()];
        predecessorMatrix = new int[graph.nodeCount()][graph.nodeCount()];

        for (int i = 0; i < graph.nodeCount(); i++) {
            for (int j = 0; j < graph.nodeCount(); j++) {
                costMatrix[i][j] = Double.MAX_VALUE;
                predecessorMatrix[i][j] = -1;
            }
        }

        for (GraphNode<L> n : graph.getNodes()) {
            int indice = graph.getNodeIndexOf(n.getLabel());
            costMatrix[indice][indice] = 0;
        }
        for (GraphEdge<L> e : graph.getEdges()) {
            costMatrix[graph.getNodeIndexOf(e.getNode1().getLabel())][graph.getNodeIndexOf(e.getNode2().getLabel())] = e.getWeight();
            predecessorMatrix[graph.getNodeIndexOf(e.getNode1().getLabel())][graph.getNodeIndexOf(e.getNode2().getLabel())] = graph.getNodeIndexOf(e.getNode1().getLabel());
        }
    }

    /**
     * Esegue il calcolo per la matrice dei costi dei cammini minimi e per la
     * matrice dei predecessori così come specificato dall'algoritmo di
     * Floyd-Warshall.
     *
     * @throws IllegalStateException se il calcolo non può essere effettuato
     *                               per via dei valori dei pesi del grafo,
     *                               ad esempio se il grafo contiene cicli
     *                               di peso negativo.
     */
    public void computeShortestPaths() {
        int totalepeso = 0;
        for (GraphEdge<L> g : graph.getEdges()) {
            totalepeso += g.getWeight();
        }
        if (totalepeso < 0)
            throw new IllegalArgumentException();
        for (int k = 1; k < costMatrix.length; k++) {
            for (int i = 1; i < costMatrix.length; i++) {
                for (int j = 1; j < costMatrix.length; j++) {
                    if (costMatrix[i][j] > costMatrix[i][k] + costMatrix[k][j]) {
                        costMatrix[i][j] = costMatrix[i][k] + costMatrix[k][j];
                        predecessorMatrix[i][j] = predecessorMatrix[k][j];
                    }
                }
            }
        }
        computato = true;
    }

    /**
     * Determina se è stata invocatala procedura di calcolo dei cammini minimi.
     *
     * @return true se i cammini minimi sono stati calcolati, false altrimenti
     */
    public boolean isComputed() {
        return computato;
    }

    /**
     * Restituisce il grafo su cui opera questo calcolatore.
     *
     * @return il grafo su cui opera questo calcolatore
     */
    public Graph<L> getGraph() {
        return this.graph;
    }

    /**
     * Restituisce una lista di archi da un nodo sorgente a un nodo target. Tale
     * lista corrisponde a un cammino minimo tra i due nodi nel grafo gestito da
     * questo calcolatore.
     *
     * @param sourceNode il nodo di partenza del cammino minimo da
     *                   restituire
     * @param targetNode il nodo di arrivo del cammino minimo da restituire
     * @return la lista di archi corrispondente al cammino minimo; la lista è
     * vuota se il nodo sorgente è il nodo target. Viene restituito
     * {@code null} se il nodo target non è raggiungibile dal nodo
     * sorgente
     * @throws NullPointerException     se almeno uno dei nodi passati è
     *                                  nullo
     * @throws IllegalArgumentException se almeno uno dei nodi passati non
     *                                  esiste
     * @throws IllegalStateException    se non è stato eseguito il calcolo
     *                                  dei cammini minimi
     */
    public List<GraphEdge<L>> getShortestPath(GraphNode<L> sourceNode,
                                              GraphNode<L> targetNode) {
        if (sourceNode == null || targetNode == null)
            throw new NullPointerException();
        if (!graph.containsNode(targetNode) || !graph.containsNode(sourceNode))
            throw new IllegalArgumentException();
        if (!isComputed())
            throw new IllegalStateException();

        List<GraphNode<L>> orderedNodes = new ArrayList<>();
        int indice1 = graph.getNodeIndexOf(sourceNode.getLabel());
        int indice2 = graph.getNodeIndexOf(targetNode.getLabel());
        while (indice1 != indice2) {
            GraphNode<L> node = graph.getNodeAtIndex(indice2);
            orderedNodes.add(node);
            indice2 = predecessorMatrix[indice1][indice2];
            if(indice2<0){
                return null;
            }
        }
        orderedNodes.add(graph.getNodeAtIndex(indice1));
        Collections.reverse(orderedNodes);
        List<GraphEdge<L>> toReturn = new ArrayList<>();
        for(int i = 0;i<orderedNodes.size()-1;++i){
            GraphEdge<L> edge = graph.getEdge(orderedNodes.get(i), orderedNodes.get(i+1));
            if(edge == null){
                return null;
            }
            toReturn.add(edge);
        }
        return toReturn;
    }

    /**
     * Restituisce il costo di un cammino minimo da un nodo sorgente a un nodo
     * target.
     *
     * @param sourceNode il nodo di partenza del cammino minimo
     * @param targetNode il nodo di arrivo del cammino minimo
     * @return il coso di un cammino minimo tra il nodo sorgente e il nodo
     * target. Viene restituito {@code Double.POSITIVE_INFINITY} se il
     * nodo target non è raggiungibile dal nodo sorgente, mentre viene
     * restituito zero se il nodo sorgente è il nodo target.
     * @throws NullPointerException     se almeno uno dei nodi passati è
     *                                  nullo
     * @throws IllegalArgumentException se almeno uno dei nodi passati non
     *                                  esiste
     * @throws IllegalStateException    se non è stato eseguito il calcolo
     *                                  dei cammini minimi
     */
    public double getShortestPathCost(GraphNode<L> sourceNode,
                                      GraphNode<L> targetNode) {
        if (sourceNode == null || targetNode == null)
            throw new NullPointerException();
        if (!graph.containsNode(targetNode) || !graph.containsNode(sourceNode))
            throw new IllegalArgumentException();
        if (!isComputed())
            throw new IllegalStateException();

        return costMatrix[graph.getNodeIndexOf(sourceNode.getLabel())][graph.getNodeIndexOf(targetNode.getLabel())];
    }

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     *
     * @param path un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException se il cammino passato è nullo
     */
    public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Richiesta di stampare un path nullo");
        if (path.isEmpty())
            return "[ ]";
        // Costruisco la stringa
        StringBuilder s = new StringBuilder();
        s.append("[ ").append(path.get(0).getNode1().toString());
        for (GraphEdge<L> lGraphEdge : path)
            s.append(" -- ").append(lGraphEdge.getWeight()).append(" --> ").append(lGraphEdge.getNode2().toString());
        s.append(" ]");
        return s.toString();
    }

    /**
     * @return the costMatrix
     */
    public double[][] getCostMatrix() {
        return costMatrix;
    }

    /**
     * @return the predecessorMatrix
     */
    public int[][] getPredecessorMatrix() {
        return predecessorMatrix;
    }
}
