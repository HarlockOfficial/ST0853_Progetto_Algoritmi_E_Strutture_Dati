package it.unicam.cs.asdl2021.totalproject2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementazione di una coda con priorità tramite heap binario. Gli oggetti
 * inseriti in coda implementano l'interface PriorityQueueElement che permette
 * di gestire la priorità e una handle dell'elemento. La handle è fondamentale
 * per realizzare in tempo logaritmico l'operazione di decreasePriority che,
 * senza la handle, dovrebbe cercare l'elemento all'interno dello heap e poi
 * aggiornare la sua posizione. Nel caso di heap binario rappresentato con una
 * ArrayList la handle è semplicemente l'indice dove si trova l'elemento
 * nell'ArrayList. Tale campo naturalmente va tenuto aggiornato se l'elemento
 * viene spostato in un'altra posizione.
 * 
 * @author Template: Luca Tesei
 *
 * @param <E>
 *                il tipo degli elementi che vengono inseriti in coda.
 *
 */
public class BinaryHeapMinPriorityQueue {
    /*
     * ArrayList per la rappresentazione dello heap. Vengono usate tutte le
     * posizioni (la radice dello heap è quindi in posizione 0).
     */
    private final ArrayList<PriorityQueueElement> heap;

    /**
     * Crea una coda con priorità vuota.
     * 
     */
    public BinaryHeapMinPriorityQueue() {
        heap = new ArrayList<>();
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the heap. The handle of the element will also be set
     * accordingly.
     * 
     * @param element
     *                    the new element to add
     * @throws NullPointerException
     *                                  if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        if(element == null){
            throw new NullPointerException("Passed parameter must be not null");
        }
        element.setHandle(heap.size());
        heap.add(element);
        heapifyUp(heap.size()-1);
    }

    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the heap.
     * 
     * @return the current minimum element of this min-priority queue
     * 
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {
        if(heap.isEmpty()){
            throw new NoSuchElementException("Heap is empty, cannot obrain first element");
        }
        return heap.get(0);
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * ternary heap will be updated accordingly.
     * 
     * @return the current minimum element
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        if(heap.isEmpty()){
            throw new NoSuchElementException("Heap is empty, cannot obrain first element");
        }
        PriorityQueueElement out = heap.get(0);
        PriorityQueueElement last = heap.remove(heap.size() - 1);
        if(heap.size()>0) {
            last.setHandle(0);
            heap.set(0, last);
            heapifyDown(0);
        }
        return out;
    }

    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     * 
     * @param element
     *                        the element whose priority will be decreased, it
     *                        must currently be inside this min-priority queue
     * @param newPriority
     *                        the new priority to assign to the element
     * 
     * @throws NoSuchElementException
     *                                      if the element is not currently
     *                                      present in this min-priority queue
     * @throws IllegalArgumentException
     *                                      if the specified newPriority is not
     *                                      strictly less than the current
     *                                      priority of the element
     */
    public void decreasePriority(PriorityQueueElement element,
            double newPriority) {
        if(element==null || element.getHandle()<0 || element.getHandle()>=heap.size()){
            throw new NoSuchElementException("Element not present in Heap");
        }
        if(newPriority>=heap.get(element.getHandle()).getPriority()){
            throw new IllegalArgumentException("Passed priority is not valid");
        }
        heap.get(element.getHandle()).setPriority(newPriority);
        heapifyUp(element.getHandle());
    }

    /**
     * Determines if this priority queue is empty.
     * 
     * @return true if this priority queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Return the current size of this queue.
     * 
     * @return the number of elements currently in this queue.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        this.heap.clear();
    }

    /*
     * done in logaritmic time, because node is just scanned against parent,
     * so worst case scenario is tree height
     */
    private void heapifyUp(int startPosition) {
        int parentIndex = getParentNode(startPosition);
        PriorityQueueElement parent = heap.get(parentIndex);
        PriorityQueueElement element = heap.get(startPosition);
        while(parent.getPriority()>element.getPriority()){
            heap.set(element.getHandle(), parent);
            heap.set(parent.getHandle(), element);

            parent.setHandle(element.getHandle());
            element.setHandle(parentIndex);

            parentIndex = getParentNode(parentIndex);
            if(parentIndex<0){
                //root reached
                break;
            }
            parent = heap.get(parentIndex);
        }
    }
    /*
     * useless to maintain startPosition, since is used only once in the code
     * with the actual value 0, but it could be used in other implementations with
     * different start position
     * Function done in logaritmic time since the node is scanned always against the
     * smallest priority child, then the max number of checks is the height of the tree
     */
    private void heapifyDown(@SuppressWarnings("SameParameterValue") int startPosition) {
        int childIndex = getBestChildIndex(startPosition);
        if(childIndex==-1){
            return;
        }
        PriorityQueueElement child = heap.get(childIndex);
        PriorityQueueElement element = heap.get(startPosition);
        while (child.getPriority()<element.getPriority()){
            heap.set(element.getHandle(), child);
            heap.set(child.getHandle(), element);

            child.setHandle(element.getHandle());
            element.setHandle(childIndex);

            childIndex = getBestChildIndex(element.getHandle());
            if(childIndex==-1){
                break;
            }
            child = heap.get(childIndex);
        }
    }
    private int getParentNode(int childIndex){
        return (childIndex-1)/2;
    }
    private int getChildNode(int parentIndex, Position pos){
        return 2*parentIndex+(pos.value?1:2);
    }
    private int getBestChildIndex(int parentIndex){
        int leftChildIndex = getChildNode(parentIndex, Position.LEFT);
        int rightChildIndex = getChildNode(parentIndex, Position.RIGHT);
        if(leftChildIndex>0 && leftChildIndex<heap.size()){
            if(rightChildIndex>0 && rightChildIndex<heap.size()){
                return heap.get(leftChildIndex).getPriority()<heap.get(rightChildIndex).getPriority()?
                        leftChildIndex:rightChildIndex;
            }
            return leftChildIndex;
        }
        if(rightChildIndex>0 && rightChildIndex<heap.size()) {
            return rightChildIndex;
        }
        return -1;
    }
    private enum Position{
        LEFT(true), RIGHT(false);
        public final boolean value;
        private Position(boolean value){
            this.value=value;
        }
    }
}
