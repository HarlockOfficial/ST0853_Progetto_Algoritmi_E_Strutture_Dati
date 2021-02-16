package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * Classe singoletto che implementa l'algoritmo di Kruskal per trovare un
 * Minimum Spanning Tree di un grafo non orientato, pesato e con pesi non
 * negativi.
 *
 * @param <L> etichette dei nodi del grafo
 * @author Template: Luca Tesei
 */
public class KruskalMSP<L> {

    /*
     * Struttura dati per rappresentare gli insiemi disgiunti utilizzata
     * dall'algoritmo di Kruskal.
     */
    private final ArrayList<HashSet<GraphNode<L>>> disjointSets;

    private final EdgesComparator edgesComparator;

    /**
     * Costruisce un calcolatore di un albero di copertura minimo che usa
     * l'algoritmo di Kruskal su un grafo non orientato e pesato.
     */
    public KruskalMSP() {
        this.edgesComparator = new EdgesComparator();
        this.disjointSets = new ArrayList<>();
    }

    /**
     * Utilizza l'algoritmo goloso di Kruskal per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. L'albero restituito non è radicato, quindi è rappresentato
     * semplicemente con un sottoinsieme degli archi del grafo.
     *
     * @param g un grafo non orientato, pesato, con pesi non negativi
     * @return l'insieme degli archi del grafo g che costituiscono l'albero di
     * copertura minimo trovato
     * @throws NullPointerException     se il grafo g è null
     * @throws IllegalArgumentException se il grafo g è orientato, non pesato o
     *                                  con pesi negativi
     */
    public Set<GraphEdge<L>> computeMSP(Graph<L> g) {
        if (g == null)
            throw new NullPointerException(
                    "Calcolo dell'albero minimo di copertura su un grafo nullo");
        // controllo le condizioni sul grafo
        checkGraph(g);
        // creo l'insieme risultato
        Set<GraphEdge<L>> risultato = new HashSet<>();
        // creo gli insiemi disgiunti, uno per ogni nodo
        this.disjointSets.clear();
        for (GraphNode<L> n : g.getNodes()) {
            HashSet<GraphNode<L>> s = new HashSet<>();
            s.add(n);
            this.disjointSets.add(s);
        }
        // Ordino gli archi in senso crescente in una lista in modo da evitare
        // problemi con il comparator che non è compatibile con equals.
        List<GraphEdge<L>> archi = new ArrayList<>(g.getEdges());
        archi.sort(this.edgesComparator);
        for (GraphEdge<L> e : archi) {
            int i = setOf(e.getNode1());
            int j = setOf(e.getNode2());
            if (i != j) {
                risultato.add(e);
                union(i, j);
            }
        }
        return risultato;
    }

    private void union(int i, int j) {
        this.disjointSets.get(i).addAll(this.disjointSets.get(j));
        this.disjointSets.remove(j);
    }

    private int setOf(GraphNode<L> node) {
        for (int i = 0; i < this.disjointSets.size(); i++) {
            if (this.disjointSets.get(i).contains(node))
                return i;
        }
        return -1;
    }

    private void checkGraph(Graph<L> g) {
        if (g.isDirected())
            throw new IllegalArgumentException("Grafo orientato non gestito");
        for (GraphEdge<L> e : g.getEdges()) {
            if (!e.hasWeight())
                throw new IllegalArgumentException(
                        "Il grafo contiene un arco non pesato: "
                                + e.toString());
            if (e.getWeight() < 0)
                throw new IllegalArgumentException(
                        "Il grafo contiene un arco con peso negativo: "
                                + e.toString());
        }

    }

    private class EdgesComparator implements Comparator<GraphEdge<L>> {

        @Override
        public int compare(GraphEdge<L> o1, GraphEdge<L> o2) {
            return Double.compare(o1.getWeight(), o2.getWeight());
        }

    }

}
