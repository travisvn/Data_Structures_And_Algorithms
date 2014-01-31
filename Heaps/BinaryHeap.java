import java.util.Comparator;

/**
 * This is an implementation of a heap that is backed by an data.
 * 
 * This implementation will accept a comparator object that can be used to
 * define an ordering of the items contained in this heap, other than the
 * objects' default compareTo method (if they are comparable). This is useful if
 * you wanted to sort strings by their length rather than their lexicographic
 * ordering. That's just one example.
 * 
 * Null should be treated as positive infinity if no comparator is provided. If
 * a comparator is provided, you should let it handle nulls, which means it
 * could possibly throw a NullPointerException, which in this case would be
 * fine.
 * 
 * If a comparator is provided that should always be what you use to compare
 * objects. If no comparator is provided you may assume the objects are
 * Comparable and cast them to type Comparable<T> for comparisons. If they
 * happen to not be Comparable you do not need to handle anything, and you can
 * just let your cast throw a ClassCastException.
 * 
 * This is a minimum heap, so the smallest item should always be at the root.
 * 
 * @param <T>
 *            The type of objects in this heap
 */
public class BinaryHeap<T> implements Heap<T> {

	/**
	 * The comparator that should be used to order the elements in this heap
	 */
	private Comparator<T> comp;

	/**
	 * The backing data of this heap
	 */
	private T[] data;

	/**
	 * The number of elements that have been added to this heap, this is NOT the
	 * same as data.length
	 */
	private int size;

	/**
	 * Default constructor, this should initialize data to a default size (11 is
	 * normally a good choice)
	 * 
	 * This assumes that the generic objects are Comparable, you will need to
	 * cast them when comparing since there are no bounds on the generic
	 * parameter
	 */
	public BinaryHeap() {
		// TODO Implement this.
		data = (T[])new Comparable[11];
		size=0;
	}

	/**
	 * Constructor that accepts a comparator to use with this heap. Also
	 * initializes data to a default size.
	 * 
	 * When a comparator is provided it should be preferred over the objects'
	 * compareTo method
	 * 
	 * If the comparator given is null you should attempt to cast the objects to
	 * Comparable as if a comparator were not given
	 * 
	 * @param comp
	 */
	public BinaryHeap(Comparator<T> comp) {
		// TODO Implement this.
		this();
		this.comp = comp;	
		
	}

	@Override
	public void add(T item) {
    	if ((size+1)>=data.length){
			resize();
		}
		data[size+1]=item;
		size++;
		bubbleUp();
	}

	@Override
	public boolean isEmpty() {
		// TODO Implement this.
		return size==0;
	}

	@Override
	public T peek() {
		// TODO Implement this.
		return data[1];
	}

	@Override
	public T remove() {
		if (size==0) return null;
		T removed = peek();
		data[1] = data[size];
		data[size]=null;
		size--;
		bubbleDown();
		return removed;
	}

	@Override
	public int size() {
		// TODO Implement this.
		return size;
	}
		
	
	//resizes the data if full.
    private T[] resize() {
		int newSize = data.length*2 + 1;
		int i, j=0;
		T[] newData = (T[])new Comparable[newSize];
		for (i=0; i<data.length; i++){
			newData[i]= data[i];
		}
		data = newData;
        return data;
    }
	//private method that bubbles added value up the data
    private void bubbleUp() {
        int index = this.size;
        if (comp==null){
        while (hasParent(index)
                && (((Comparable) parent(index)).compareTo(data[index]) > 0)) {

            swap(index, index/2);
            index = index/2;
        }
        }
        else{
            while (hasParent(index)
                    && comp.compare(parent(index), data[index]) > 0) {

                swap(index, index/2);
                index = index/2;
            }
        }
    }
	//private method that bubbles the new root (bubbles down the current root)
    private void bubbleDown() {
        int index = 1;
        
        if (comp==null){
        while (hasLeftChild(index)) {
            int smallerChild = index*2;

            if (hasRightChild(index)
                && ((Comparable) data[index*2]).compareTo(data[(index*2+1)]) > 0) {
                smallerChild = (index*2+1);
            } 
            
            if (((Comparable) data[index]).compareTo(data[smallerChild]) > 0) {
                swap(index, smallerChild);
            } else {
                break;
            }
            index = smallerChild;
        }
        }
        else{
            while (hasLeftChild(index)) {

                int smallerChild = index*2;

                if (hasRightChild(index)
                    && comp.compare(data[index*2], data[(index*2+1)]) > 0) {
                    smallerChild = (index*2+1);
                } 
                
                if (comp.compare(data[index], data[smallerChild]) > 0) {
                    swap(index, smallerChild);
                } else {

                    break;
                }
                

                index = smallerChild;
            }
        }
    }
	
	
	
	//check if parent exists
    private boolean hasParent(int i) {
        return i > 1;
    } 
    //check if leftChild exists
    private boolean hasLeftChild(int i) {
        return i*2 <= size;
    }
    
    //check if right child exists
    private boolean hasRightChild(int i) {
        return (i*2+1) <= size;
    }
    
    //returns parent
    private T parent(int i) {
        return data[i/2];
    }
	
	//swaps parent/child
	private void swap(int index, int index2){
		T temp = data[index];
        data[index] = data[index2];
        data[index2] = temp;       
	}
	
}