package it.unicam.cs.asdl2021.totalproject2;

import java.awt.Label;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementazione dell'algoritmo di Floyd-Warshall per il calcolo di cammini
 * minimi tra tutte le coppie di nodi in un grafo pesato che pu� contenere anche
 * pesi negativi, ma non cicli di peso negativo.
 * 
 * @author Template: Luca Tesei
 *
 * @param <L>
 *                etichette dei nodi del grafo
 */
public class FloydWarshallAllPairsShortestPathComputer<L> {

	private boolean computato=false;
    /*
     * Il grafo su cui opera questo calcolatore.
     */
    private Graph<L> graph;

    /*
     * Matrice dei costi dei cammini minimi. L'elemento in posizione i,j
     * corrisponde al costo di un cammino minimo tra il nodo i e il nodo j, dove
     * i e j sono gli interi associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private double[][] costMatrix;

    /*
     * Matrice dei predecessori. L'elemento in posizione i,j � -1 se non esiste
     * nessun cammino tra i e j oppure corrisponde all'indice di un nodo che
     * precede il nodo j in un qualche cammino minimo da i a j. Si intende che i
     * e j sono gli indici associati ai nodi nel grafo (si richiede quindi che
     * la classe che implementa il grafo supporti le operazioni con indici).
     */
    private int[][] predecessorMatrix;

    /**
     * Crea un calcolatore di cammini minimi fra tutte le coppie di nodi per un
     * grafo orientato e pesato. Non esegue il calcolo, che viene eseguito
     * invocando successivamente il metodo computeShortestPaths().
     * 
     * @param graph
     *                  il grafo su cui opera il calcolatore di cammini minimi
     * @throws NullPointerException
     *                                      se il grafo passato � nullo
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato � vuoto
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non � orientato
     * 
     * @throws IllegalArgumentException
     *                                      se il grafo passato non � pesato,
     *                                      cio� esiste almeno un arco il cui
     *                                      peso � {@code Double.NaN}
     */
    public FloydWarshallAllPairsShortestPathComputer(Graph<L> g) {
    	graph=g;
    	if(g==null)
    		throw new NullPointerException();
    	if(g.isEmpty())
    		throw new IllegalArgumentException();
    	if(!g.isDirected())
    		throw new IllegalArgumentException();
    	for(GraphEdge<L> e: g.getEdges())
    	{
    		if(!e.hasWeight())
    			throw new IllegalArgumentException();
    	}
    	
    	costMatrix = new double[g.size()][g.size()];
    	predecessorMatrix = new int[g.size()][g.size()];
    	
    	for(int i=0;i<g.size();i++)
    	{
    		for(int j=0;j<g.size();j++)
    		{
    			costMatrix[i][j]=Double.MAX_VALUE;
    		}
    	}
    	
    	for(GraphNode<L> n : g.getNodes())
    	{
    		int indice=g.getNodeIndexOf(n.getLabel());
    		costMatrix[indice][indice]=0;
    	}
    	for(GraphEdge e :g.getEdges())
    	{
    		costMatrix[g.getNodeIndexOf((L) e.getNode1().getLabel())][g.getNodeIndexOf((L) e.getNode2().getLabel())]=e.getWeight();
    		predecessorMatrix[g.getNodeIndexOf((L) e.getNode1().getLabel())][g.getNodeIndexOf((L) e.getNode2().getLabel())]=g.getNodeIndexOf((L) e.getNode1().getLabel());
    	}
        // TODO implementare
    }

    /**
     * Esegue il calcolo per la matrice dei costi dei cammini minimi e per la
     * matrice dei predecessori cos� come specificato dall'algoritmo di
     * Floyd-Warshall.
     * 
     * @throws IllegalStateException
     *                                   se il calcolo non pu� essere effettuato
     *                                   per via dei valori dei pesi del grafo,
     *                                   ad esempio se il grafo contiene cicli
     *                                   di peso negativo.
     */
    public void computeShortestPaths() {
        // TODO implementare
    	int totalepeso=0;
    	for(GraphEdge<L> g : graph.getEdges())
    	{
    		totalepeso+=g.getWeight();
    	}
    	if(totalepeso<0)
    		throw new IllegalArgumentException();
    	for(int k=1;k<costMatrix.length;k++)
    	{
    		for(int i=1;i<costMatrix.length;i++)
        	{
        		for(int j=1;j<costMatrix.length;j++)
        		{
        			if(costMatrix[i][j]>costMatrix[i][k]+costMatrix[k][j])
        			{
        				costMatrix[i][j]=costMatrix[i][k]+costMatrix[k][j];
        				predecessorMatrix[i][j]=predecessorMatrix[k][j];
        			}
        		}
        	}
    	}
    	computato=true;
    	
    }

    /**
     * Determina se � stata invocatala procedura di calcolo dei cammini minimi.
     * 
     * @return true se i cammini minimi sono stati calcolati, false altrimenti
     */
    public boolean isComputed() {
        // TODO implementare
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
     * @param sourceNode
     *                       il nodo di partenza del cammino minimo da
     *                       restituire
     * @param targetNode
     *                       il nodo di arrivo del cammino minimo da restituire
     * @return la lista di archi corrispondente al cammino minimo; la lista �
     *         vuota se il nodo sorgente � il nodo target. Viene restituito
     *         {@code null} se il nodo target non � raggiungibile dal nodo
     *         sorgente
     * 
     * @throws NullPointerException
     *                                      se almeno uno dei nodi passati �
     *                                      nullo
     * 
     * @throws IllegalArgumentException
     *                                      se almeno uno dei nodi passati non
     *                                      esiste
     * 
     * @throws IllegalStateException
     *                                      se non � stato eseguito il calcolo
     *                                      dei cammini minimi
     * 
     * 
     */
    public List<GraphEdge<L>> getShortestPath(GraphNode<L> sourceNode,
            GraphNode<L> targetNode) {
    	if(sourceNode==null||targetNode==null)
    		throw new NullPointerException();
    	if(!graph.containsNode(targetNode)||!graph.containsNode(sourceNode))
    		throw new IllegalArgumentException();
    	if(!isComputed())
    		throw new IllegalArgumentException();
        // TODO implementare
    	
    	List<GraphEdge<L>> toReturn = new ArrayList<GraphEdge<L>>();
    	int indice1 = graph.getNodeIndexOf(targetNode.getLabel());
    	int indice2 = graph.getNodeIndexOf(sourceNode.getLabel());
    	if(predecessorMatrix[indice1][indice2]!=-1)
    	{
    		return toReturn;
    	}
    	else
    	{
    		while(indice1!=indice2)
    		{
    			indice1=predecessorMatrix[indice1][indice2];
    			toReturn.add(graph.getEdgeAtNodeIndexes(indice1, indice2));
    		}
    	}
        return toReturn;
    }

    /**
     * Restituisce il costo di un cammino minimo da un nodo sorgente a un nodo
     * target.
     * 
     * @param sourceNode
     *                       il nodo di partenza del cammino minimo
     * @param targetNode
     *                       il nodo di arrivo del cammino minimo
     * @return il coso di un cammino minimo tra il nodo sorgente e il nodo
     *         target. Viene restituito {@code Double.POSITIVE_INFINITY} se il
     *         nodo target non � raggiungibile dal nodo sorgente, mentre viene
     *         restituito zero se il nodo sorgente � il nodo target.
     * 
     * @throws NullPointerException
     *                                      se almeno uno dei nodi passati �
     *                                      nullo
     * 
     * @throws IllegalArgumentException
     *                                      se almeno uno dei nodi passati non
     *                                      esiste
     * 
     * @throws IllegalStateException
     *                                      se non � stato eseguito il calcolo
     *                                      dei cammini minimi
     * 
     * 
     */
    public double getShortestPathCost(GraphNode<L> sourceNode,
            GraphNode<L> targetNode) {
        // TODO implementare
        return costMatrix[graph.getNodeIndexOf(sourceNode.getLabel())][graph.getNodeIndexOf(targetNode.getLabel())];
    }

    /**
     * Genera una stringa di descrizione di un path riportando i nodi
     * attraversati e i pesi degli archi. Nel caso di cammino vuoto genera solo
     * la stringa {@code "[ ]"}.
     * 
     * @param path
     *                 un cammino minimo
     * @return una stringa di descrizione del cammino minimo
     * @throws NullPointerException
     *                                  se il cammino passato � nullo
     */
    public String printPath(List<GraphEdge<L>> path) {
        if (path == null)
            throw new NullPointerException(
                    "Richiesta di stampare un path nullo");
        if (path.isEmpty())
            return "[ ]";
        // Costruisco la stringa
        StringBuffer s = new StringBuffer();
        s.append("[ " + path.get(0).getNode1().toString());
        for (int i = 0; i < path.size(); i++)
            s.append(" -- " + path.get(i).getWeight() + " --> "
                    + path.get(i).getNode2().toString());
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
    
    

    // TODO inserire eventuali metodi privati per fini di implementazione
}
