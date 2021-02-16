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
     * Comparatore tra archi in base al peso.
     */
    private final EdgesComparator edgesComparator;

    /*
     * Struttura dati per rappresentare gli insiemi disgiunti utilizzata
     * dall'algoritmo di Kruskal.
     */
    private final ArrayList<HashSet<GraphNode<L>>> disjointSets;

    public KruskalMSP() {
        this.edgesComparator = new EdgesComparator();
        this.disjointSets = new ArrayList<>();
    }

    /**
     * Utilizza l'algoritmo goloso di Kruskal per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. L'albero restituito non � radicato, quindi � rappresentato
     * semplicemente con un sottoinsieme degli archi del grafo.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @return l'insieme degli archi del grafo g che costituiscono l'albero di
     *         copertura minimo trovato
     * @throws NullPointerException se il grafo g � null
     * @throws IllegalArgumentException se il grafo g � orientato, non pesato o
     *        con pesi negativi
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
        // problemi con il comparator che non � compatibile con equals.
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

    /*
     * Unisce gli insiemi all'indice i e all'indice j mettendo il risultato
     * nell'indice i ed eliminando l'insieme all'indice j
     */
    private void union(int i, int j) {
        this.disjointSets.get(i).addAll(this.disjointSets.get(j));
        this.disjointSets.remove(j);
    }

    /*
     * Cerca l'indice dell'insieme che contiene il nodo
     */
    private int setOf(GraphNode<L> node) {
        for (int i = 0; i < this.disjointSets.size(); i++) {
            if (this.disjointSets.get(i).contains(node))
                return i;
        }
        return -1;
    }

    /*
     * Controlla le tre propriet� richieste al grafo e, se non soddisfatte,
     * lancia le relative eccezioni
     */
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

    /*
     * Classe comparatore che ordina gli archi in base al peso. Attenzione: non
     * compatibile con equals di GraphEdge!
     */
    private class EdgesComparator implements Comparator<GraphEdge<L>> {

        @Override
        public int compare(GraphEdge<L> o1, GraphEdge<L> o2) {
            return Double.compare(o1.getWeight(), o2.getWeight());
        }
    }
}
