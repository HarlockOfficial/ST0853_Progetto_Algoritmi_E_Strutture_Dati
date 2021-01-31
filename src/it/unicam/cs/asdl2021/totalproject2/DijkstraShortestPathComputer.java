package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gli oggetti di questa classe sono calcolatori di cammini minimi con sorgente
 * singola su un certo grafo orientato e pesato dato. Il grafo su cui lavorare
 * deve essere passato quando l'oggetto calcolatore viene costruito e non può
 * contenere archi con pesi negativi. Il calcolatore implementa il classico
 * algoritmo di Dijkstra per i cammini minimi con sorgente singola utilizzando
 * una coda con priorità che estrae l'elemento con priorità minima e aggiorna le
 * priorità con l'operazione decreasePriority in tempo logaritmico (coda
 * realizzata con uno heap binario). In questo caso il tempo di esecuzione
 * dell'algoritmo di Dijkstra è {@code O(n log m)} dove {@code n} è il numero di
 * nodi del grafo e {@code m} è il numero di archi.
 * 
 * @author Template: Luca Tesei
 *
 * @param <L>
 *                il tipo delle etichette dei nodi del grafo
 */
public class DijkstraShortestPathComputer<L>
        implements SingleSourceShortestPathComputer<L> {
    private final Graph<L> graph;
    private boolean solved;
    private GraphNode<L> lastSource;

    /**
     * Crea un calcolatore di cammini minimi a sorgente singola per un grafo
     * diretto e pesato privo di pesi negativi.
     * 
     * @param graph
     *                  il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException
     *                                      se il grafo passato è nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato è vuoto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è orientato
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non è pesato,
     *                                      cioè esiste almeno un arco il cui
     *                                      peso è {@code Double.NaN}
     * @throws IllegalArgumentException
     *                                      se il grafo passato contiene almeno
     *                                      un peso negativo
     */
    public DijkstraShortestPathComputer(Graph<L> graph) {
        if(graph==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(graph.isEmpty() || !graph.isDirected() || graph.getEdges().isEmpty()){
            throw new IllegalArgumentException("Invalid Graph passed");
        }
        for(GraphEdge<L> edge: graph.getEdges()){
            if(!edge.hasWeight() || edge.getWeight()<0){
                throw new IllegalArgumentException("Invalid Graph passed");
            }
        }
        this.graph=graph;
        solved=false;
        lastSource=null;
    }

    @Override
    public void computeShortestPathsFrom(GraphNode<L> sourceNode) {
        if(sourceNode==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!graph.getNodes().contains(sourceNode)){
            throw new IllegalArgumentException("Invalid Node passed");
        }
        BinaryHeapMinPriorityQueue queue = new BinaryHeapMinPriorityQueue();
        for(GraphNode<L> node: graph.getNodes()){
            if(sourceNode.equals(node)){
                node.setFloatingPointDistance(0.0);
                queue.insert(node);
            }else{
                node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
            }
        }
        while(!queue.isEmpty()){
            //noinspection unchecked
            GraphNode<L> current = (GraphNode<L>) queue.extractMinimum();
            for(GraphEdge<L> edge: graph.getEdgesOf(current)){
                double distance = current.getFloatingPointDistance()+edge.getWeight();
                if(distance<edge.getNode2().getFloatingPointDistance()){
                    edge.getNode2().setFloatingPointDistance(distance);
                    edge.getNode2().setPrevious(current);
                    queue.insert(edge.getNode2());
                }
            }
        }
        lastSource=sourceNode;
        solved=true;
    }

    @Override
    public boolean isComputed() {
        return solved;
    }

    @Override
    public GraphNode<L> getLastSource() {
        if(lastSource==null){
            throw new IllegalStateException("Must solve at least once before calling this function");
        }
        return lastSource;
    }

    @Override
    public Graph<L> getGraph() {
        return graph;
    }

    @Override
    public List<GraphEdge<L>> getShortestPathTo(GraphNode<L> targetNode) {
        if(targetNode==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!graph.getNodes().contains(targetNode)){
            throw new IllegalArgumentException("Invalid Node passed");
        }
        if(lastSource==null){
            throw new IllegalStateException("Must solve at least once before calling this function");
        }
        List<GraphEdge<L>> shortestPath = new ArrayList<>();
        GraphNode<L> lastNode = null;
        if(targetNode.equals(lastSource)){
            return shortestPath;
        }
        for(GraphNode<L> node: graph.getNodes()){
            if(targetNode.equals(node)){
                lastNode = node;
                break;
            }
        }
        //unnecessary assert, checked in if in line 126, done to avoid warning in line 143
        assert(lastNode!=null);
        while (true){
            GraphNode<L> prev = lastNode.getPrevious();
            if(prev==null){
                break;
            }
            for(GraphEdge<L> edge: graph.getIngoingEdgesOf(lastNode)){
                if(edge.getNode1().equals(prev)) {
                    shortestPath.add(edge);
                    break;
                }
            }
            if(prev.equals(lastSource)){
                break;
            }
            lastNode=prev;
        }
        if(!shortestPath.isEmpty()) {
            Collections.reverse(shortestPath);
            return shortestPath;
        }
        return null;
    }
}
