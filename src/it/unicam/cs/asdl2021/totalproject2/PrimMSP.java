package it.unicam.cs.asdl2021.totalproject2;

import java.awt.List;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

/**
 * 
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * 
 * L'algoritmo usa una coda di min priorità tra i nodi implementata dalla classe
 * TernaryHeapMinPriorityQueue. I nodi vengono visti come PriorityQueueElement
 * poiché la classe GraphNode<L> implementa questa interfaccia. Si noti che
 * nell'esecuzione dell'algoritmo è necessario utilizzare l'operazione di
 * decreasePriority.
 * 
 * @author Template: Luca Tesei
 * 
 * @param <L>
 *                etichette dei nodi del grafo
 *
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
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @param s
     *              il nodo del grafo g sorgente, cioè da cui parte il calcolo
     *              dell'albero di copertura minimo. Tale nodo sarà la radice
     *              dell'albero di copertura trovato
     * 
     * @throw NullPointerException se il grafo g o il nodo sorgente s sono nulli
     * @throw IllegalArgumentException se il nodo sorgente s non esiste in g
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
    	if(g==null || s==null)
    		throw new NullPointerException();
    	if(!g.containsNode(s))
    		throw new IllegalArgumentException();
    	if (g.isDirected())
            throw new IllegalArgumentException(
                    "Tentativo di applicare l'algoritmo di Prim su un grafo orientato");
        // Determina se tutti gli archi del grafo hanno un peso assegnato e, se
        // sì, positivo o nullo
        Set<GraphEdge<L>> edges = g.getEdges();
        for (GraphEdge<L> e : edges) {
            if (!e.hasWeight())
                throw new IllegalArgumentException(
                        "Tentativo di applicare l'algoritmo di Prim su un grafo con almeno un arco con peso non specificato");
            if (e.getWeight() < 0)
                throw new IllegalArgumentException(
                        "Tentativo di applicare l'algoritmo di Prim su un grafo con almeno un arco con peso negativo");
        }
        
boolean vAppartieneQ=false;
        
        GraphEdge<L> arcoUV=null;
        
        for(GraphNode<L> v:g.getNodes())
        {
        	v.setPriority(Double.MAX_VALUE);
        	v.setPrevious(null);
        }
        s.setPriority(0);
        for(GraphNode<L> nodo:g.getNodes())
        {
        	this.queue.insert(nodo);
        }
		GraphNode<L> u;
        while(this.queue.size()!=0)
        {
        	u=(GraphNode<L>) this.queue.extractMinimum();
        	for(GraphNode<L> v:g.getAdjacentNodesOf(u))
        	{
        		vAppartieneQ=quequeContains(v);
        		
    			for(GraphEdge<L> arco:g.getEdgesOf(u))
    			{
    				//poiché il grafo non è orientato bisogna controllare
    				//ambo i nodi dell'arco per vedere se esso è l'arco che collega
    				//il nodo u con il nodo v
    				if(arco.getNode1().equals(v) || arco.getNode2().equals(v))
    				{
    					arcoUV=arco;
    					break;
    				}
    			}
    			if(vAppartieneQ && arcoUV.getWeight()<v.getPriority())
    			{
    				v.setPrevious(u);
    				v.setPriority(arcoUV.getWeight());
    			}
        		
        	}
        }
    }


private boolean quequeContains(GraphNode<L> a) //controlla se la queue contiene il nodo
{
	 ArrayList<PriorityQueueElement>app = new ArrayList<PriorityQueueElement>();
	 while(!queue.isEmpty())
	 {
		 app.add(queue.extractMinimum());
	 }
	 for(int i=0;i<app.size();i++)
	 {
		 queue.insert(app.get(i));
	 }
	 return app.contains(a);
}
    // TODO implementare: inserire eventuali metodi privati per rendere
    // l'implementazione più modulare

}