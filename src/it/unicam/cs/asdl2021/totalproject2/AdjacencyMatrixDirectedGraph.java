package it.unicam.cs.asdl2021.totalproject2;

import java.util.*;

/**
 * Classe che implementa un grafo orientato tramite matrice di adiacenza. Non
 * sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * I nodi sono indicizzati da 0 a nodeCoount() - 1 seguendo l'ordine del loro
 * inserimento (0 è l'indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 * 
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 * 
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco orientato e contiene un oggetto della classe
 * GraphEdge<L> se lo sono. Tale oggetto rappresenta l'arco.
 * 
 * Questa classe non supporta la cancellazione di nodi, ma supporta la
 * cancellazione di archi e tutti i metodi che usano indici, utilizzando
 * l'indice assegnato a ogni nodo in fase di inserimento.
 * 
 * @author Template: Luca Tesei
 *
 */
public class AdjacencyMatrixDirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    // Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
    // matrice di adiacenza
    protected Map<GraphNode<L>, Integer> nodesIndex;

    // Matrice di adiacenza, gli elementi sono null o oggetti della classe
    // GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
    // dimensione gradualmente ad ogni inserimento di un nuovo nodo.
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;

    /*
     * NOTA: per tutti i metodi che ritornano un set utilizzare la classe
     * HashSet<E> per creare l'insieme risultato. Questo garantisce un buon
     * funzionamento dei test JUnit che controllano l'uguaglianza tra insiemi
     */

    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new ArrayList<>();
        this.nodesIndex = new HashMap<>();
    }

    @Override
    public int nodeCount() {
        return nodesIndex.size();
    }

    @Override
    public int edgeCount() {
        int count = 0;
        for(ArrayList<GraphEdge<L>> tmp: matrix){
            for(GraphEdge<L> elem:tmp){
                if(elem!=null){
                    count+=1;
                }
            }
        }
        return count;
    }

    @Override
    public void clear() {
        this.matrix = new ArrayList<>();
        this.nodesIndex = new HashMap<>();
    }

    @Override
    public boolean isDirected() {
        // Questa classe implementa un grafo orientato
        return true;
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return nodesIndex.keySet();
    }

    @Override
    public boolean addNode(GraphNode<L> node) {
        if(node==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(containsNode(node)){
            return false;
        }
        nodesIndex.put(node, nodesIndex.size());
        for(ArrayList<GraphEdge<L>> arr:matrix){
            arr.add(null);
        }
        ArrayList<GraphEdge<L>> tmp = new ArrayList<>();
        for(int i =0;i<=matrix.size();++i){
            tmp.add(null);
        }
        matrix.add(tmp);
        return true;
    }

    @Override
    public boolean removeNode(GraphNode<L> node) {
        throw new UnsupportedOperationException("Remove di nodi non supportata");
    }

    @Override
    public boolean containsNode(GraphNode<L> node) {
        if(node==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        return nodesIndex.containsKey(node);
    }

    @Override
    public GraphNode<L> getNodeOf(L label) {
        if(label==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        for(GraphNode<L> tmp: nodesIndex.keySet()){
            if(tmp.getLabel().equals(label)){
                return tmp;
            }
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(L label) {
        if(label==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        for(GraphNode<L> tmp: nodesIndex.keySet()){
            if(tmp.getLabel().equals(label)){
                return nodesIndex.get(tmp);
            }
        }
        throw new IllegalArgumentException("Argument not present in graph");
    }

    @Override
    public GraphNode<L> getNodeAtIndex(int index) {
        if(index<0 || index>=nodesIndex.size()){
            throw new IndexOutOfBoundsException("Cannot obtain value at index "+index+" graph length: "+
                    nodesIndex.size());
        }
        for(GraphNode<L> tmp: nodesIndex.keySet()){
            if(nodesIndex.get(tmp)==index){
                return tmp;
            }
        }
        // is impossible
        throw new UnsupportedOperationException("Impossible exception, value is returned by for");
    }


    //TODO check maybe is wrong: matrix.get(nodesIndex.get(node))
    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        if(node==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(node)){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        HashSet<GraphNode<L>> out = new HashSet<>();
        for(GraphEdge<L> elem: matrix.get(nodesIndex.get(node))){
            if(elem!=null){
                out.add(elem.getNode2());
            }
        }
        return out;
    }

    //TODO check maybe is wrong: matrix.get(nodesIndex.get(node))
    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        if(node==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(node)){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        HashSet<GraphNode<L>> out = new HashSet<>();
        for(ArrayList<GraphEdge<L>> arr: matrix){
            if(arr.get(nodesIndex.get(node))!=null){
                out.add(arr.get(nodesIndex.get(node)).getNode1());
            }
        }
        return out;
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        HashSet<GraphEdge<L>> out = new HashSet<>();
        for(ArrayList<GraphEdge<L>> arr: matrix){
            for(GraphEdge<L> elem: arr){
                if(elem!=null){
                    out.add(elem);
                }
            }
        }
        return out;
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        if(edge==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2())){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        if(edge.isDirected()!=isDirected()){
            throw new IllegalArgumentException("Passed parameter must be directed or undirected, " +
                    "just like the graph");
        }
        if(matrix.get(nodesIndex.get(edge.getNode1())).get(nodesIndex.get(edge.getNode2()))!=null){
            return false;
        }
        matrix.get(nodesIndex.get(edge.getNode1())).set(nodesIndex.get(edge.getNode2()), edge);
        return true;
    }

    @Override
    public boolean removeEdge(GraphEdge<L> edge) {
        if(edge==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2())){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        if(matrix.get(nodesIndex.get(edge.getNode1())).get(nodesIndex.get(edge.getNode2()))==null){
            return false;
        }
        matrix.get(nodesIndex.get(edge.getNode1())).set(nodesIndex.get(edge.getNode2()), null);
        return true;
    }

    @Override
    public boolean containsEdge(GraphEdge<L> edge) {
        if(edge==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2())){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        return edge.equals(matrix.get(nodesIndex.get(edge.getNode1())).get(nodesIndex.get(edge.getNode2())));
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
        if(node==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(node)){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        HashSet<GraphEdge<L>> out=new HashSet<>();
        for(GraphEdge<L> elem: matrix.get(nodesIndex.get(node))){
            if(elem!=null){
                out.add(elem);
            }
        }
        return out;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        if(node==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(node)){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        HashSet<GraphEdge<L>> out = new HashSet<>();
        for(ArrayList<GraphEdge<L>> arr: matrix){
            if(arr.get(nodesIndex.get(node))!=null){
                out.add(arr.get(nodesIndex.get(node)));
            }
        }
        return out;
    }

    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        if(node1==null || node2==null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        if(!nodesIndex.containsKey(node1) || !nodesIndex.containsKey(node2)){
            throw new IllegalArgumentException("Passed parameter must be part of the graph");
        }
        return getEdgeAtNodeIndexes(nodesIndex.get(node1), nodesIndex.get(node2));
    }

    @Override
    public GraphEdge<L> getEdgeAtNodeIndexes(int i, int j) {
        if(i<0 || i>=nodesIndex.size() || j<0 || j>nodesIndex.size()){
            throw new IndexOutOfBoundsException("Cannot obtain value at index "+i+" or at index "+j+
                    " graph length: "+nodesIndex.size());
        }
        return matrix.get(i).get(j);
    }
}
