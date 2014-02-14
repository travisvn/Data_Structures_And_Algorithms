import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

/**
 * CS 1332 Fall 2013
 * Binary Search Tree
 * 
 * In this assignment, you will be coding methods to make a functional
 * binary search tree. If you do this right, you will save a lot of time
 * in the next two assignments (since they are just augmenting the BST to 
 * make it efficient). Let's get started!
 * 
 * **************************NOTE************************
 * YOU WILL HAVE TO HANDLE NULL DATA IN THIS ASSIGNMENT!!
 * PLEASE TREAT NULL AS POSITIVE INFINITY!!!!
 * **************************NOTE************************
 * 
 * DO NOT CHANGE ANY OF THE PUBLIC METHOD HEADERS
 * 
 * Please make any extra inner classes, instance fields, and methods private
 */
public class BST<T extends Comparable<T>> {
	
	private Node<T> root;
	private int size;
	
	
	
	private int compare(T a, T b){
		if (a == null && b == null){
			return 0;
		}
		if (a == null){
			return 1;
		}
		if (b == null){
			return -1;
		}
		else {
			return a.compareTo(b);
		}
	}
	
	private Node<T> addHelper(Node<T> curr, T data){
		//System.out.println(data);
		if (curr==null){
			Node<T> newRoot =  new Node<T>(data);
			return newRoot;
		}

		if (compare(data, curr.getData()) < 0) {
			curr.setLeft(addHelper((Node<T>)curr.getLeft(), data));
		}
		if (compare(data, curr.getData()) >= 0){
			curr.setRight(addHelper((Node<T>)curr.getRight(), data));
		}
		return curr;
		
	}
	
	/**
	 * Add data to the binary search tree. Remember to adhere to the BST Invariant:
	 * All data to the left of a node must be smaller and all data to the right of
	 * a node must be larger. Don't forget to update the size. 
	 * 
	 * For this method, you will need to traverse the tree and find the appropriate
	 * location of the data. Depending on the data's value, you will either explore
	 * the right subtree or the left subtree. When you reach a dead end (you have 
	 * reached a null value), simply return a new node with the data that was passed
	 * in.
	 * 
	 * PLEASE TREAT NULL DATA AS POSITIVE INFINITY!!!!
	 * 
	 * @param data A comparable object to be added to the tree.
	 */
	public void add(T data) {
		//TODO Implement this method!
		size++;
		root = addHelper(root, data);	
		
	}	
	/**
	 * Add the contents of the collection to the BST. To do this method, notice that
	 * most every collection in the java collections API implements the iterable 
	 * interface. This means that you can iterate through every element in these 
	 * structures with a for-each loop. Don't forget to update the size.
	 * 
	 * @param collection A collection of data to be added to the tree.
	 */
	public void addAll(Collection<? extends T> c) {
		//TODO Implement this method!
		for (T data : c) {
			add(data);
		}
	}
	
	/**
	 * Remove the data element from the tree. Use the removeChild helper method.
	 *  
	 * PLEASE TREAT NULL DATA AS POSITIVE INFINITY!
	 *  
	 * @param data The data element to be searched for.
	 * @return retData The data that was removed from the tree. Return null if the
	 * data doesn't exist.
	 */
	public T remove(T data) {
		//TODO Implement this method!
		if (!contains(data)){
			return null;
		}
		T returnData = get(data);		
		root = removeChild(root, data);		
		size--;
		return returnData;
	}

	/**
	 * This is the helper method we suggest using, you may code remove in other
	 * ways though.
	 * 
	 * There are three cases you have to deal with:
	 * 1. The node to remove has no children
	 * 2. The node to remove has one child
	 * 2. The node to remove has two children
	 * 
	 * In the first case, return null. In the second case, return the non-null 
	 * child. The third case is where things get interesting. Here, you have two 
	 * you will have to find the predecessor and then copy their data 
	 * into the node you want to remove. You will also have to fix the
	 * predecessor's children if necessary. Don't forget to update the size.
	 *
	 * @param Node<T> node The node to be removed
	 * @param Node<T> The new child node
	 */
	private Node<T> removeChild(Node<T> node, T data) {
		//TODO	
		if (node == null) {
			return null;
		}

		else if (compare(data, node.getData()) < 0){
			node.setLeft(removeChild(node.getLeft(), data));
		}

		else if (compare(data, node.getData())>0){
			node.setRight(removeChild(node.getRight(), data));
		}
		else {
			
			if (node.getLeft() == null) {
				return node.getRight();
			}
			else if (node.getRight() == null) {
				return node.getLeft();
			}
			else{
			//get the inOrder predecessor, replace current node's data
			node.setData(getData(node.getLeft()));
			//delete inOrder predecessor
			node.setLeft(removeChild(node.getLeft(), node.getData()));
			}
		}		
		
		return node;
	}
	
	/*
	 * Gets the data from the rightmost node in the subtree
	 * 
	 * @param Node<T> t the node to the immediate left of the node to be deleted
	 * @return T the data in the node
	 */
	private T getData(Node<T> t){
		while(t.getRight() != null) {
			t = t.getRight();
		}
		return t.getData();
	}
	
	
	
	private T getHelper(Node<T> current, T oldData) {

		if (current == null) {
			return null;
		}
		
		if (compare(current.getData(), oldData) < 0){
			return getHelper(current.getRight(), oldData);
		}
		if (compare(current.getData(), oldData) > 0){
			return getHelper(current.getLeft(), oldData);
		}
		return current.getData();		
	}
	
	
	/**
	 * Get the data from the tree.
	 * 
	 * This method simply returns the data that was stored in the tree.
	 * 
	 * TREAT NULL DATA AS POSITIVE INFINITY!
	 * 
	 * @param data The datum to search for in the tree.
	 * @return The data that was found in the tree. Return null if the data doesn't
	 * exist.
	 */
	public T get(T data) {
		//TODO Implement this method!		
		if(contains(data)){
			return getHelper(root, data);
		}
		return null;
	}
	
	/**
	 * See if the tree contains the data.
	 * 
	 * TREAT NULL DATA AS POSITIVE INFINITY!
	 * 
	 * @param data The data to search for in the tree.
	 * @return Return true if the data is in the tree, false otherwise.
	 */
	public boolean contains(T data) {
		//TODO Implement this method!
		return containsHelper(root, data);
	}
	
	private boolean containsHelper(Node<T> current, T data){
		
		if (current == null) {
			return false;
		}
		
		if (compare(current.getData(), data) < 0){
			return containsHelper(current.getRight(), data);
		}
		if (compare(current.getData(), data) > 0){
			return containsHelper(current.getLeft(), data);
		}
		return true;
		
	}
	
	
	private void preOrderHelper(Node<T> current, ArrayList<T> newList) {
		if (current == null) {
			return;
		}
		newList.add(current.getData());
		preOrderHelper(current.getLeft(), newList);
		preOrderHelper(current.getRight(), newList);
		
	}
	/**
	 * Linearize the tree using the pre-order traversal.
	 * 
	 * @return A list that contains every element in pre-order.
	 */
	public List<T> preOrder() {
		//TODO Implement this method!
		ArrayList<T> preList = new ArrayList<T>();
		preOrderHelper(root, preList);		
		return preList;
	}
	
	
	private void inOrderHelper(Node<T> current, ArrayList<T> newList) {
		if (current == null) {
			return;
		}
		inOrderHelper(current.getLeft(), newList);
		newList.add(current.getData());
		inOrderHelper(current.getRight(), newList);
		
	}
	
	/**
	 * Linearize the tree using the in-order traversal.
	 * 
	 * @return A list that contains every element in-order.
	 */
	public List<T> inOrder() {
		//TODO Implement this method!
		ArrayList<T> inList = new ArrayList<T>();
		inOrderHelper(root, inList);		
		return inList;
	}
	
	
	private void postOrderHelper(Node<T> current, ArrayList<T> newList) {
		if (current == null) {
			return;
		}
		postOrderHelper(current.getLeft(), newList);
		postOrderHelper(current.getRight(), newList);
		newList.add(current.getData());		
	}
	
	/**
	 * Linearize the tree using the post-order traversal.
	 * 
	 * @return A list that contains every element in post-order.
	 */
	public List<T> postOrder() {
		//TODO Implement this method!
		ArrayList<T> postList = new ArrayList<T>();
		postOrderHelper(root, postList);
		return postList;
	}
	
	
	/**
	 * Test to see if the tree is empty.
	 * 
	 * @return Return true if the tree is empty, false otherwise.
	 */
	public boolean isEmpty() {
		//TODO Implement this method!
		return size==0;
	}
	
	/**
	 * 
	 * @return Return the number of elements in the tree.
	 */
	public int size() {
		//TODO Implement this method!
		return size;
	}
	
	/**
	 * Clear the tree. (ie. set root to null and size to 0)
	 */
	public void clear() {
		//TODO Implement this method!
		root = null;
		size = 0;
	}
	
	/**
	 * Clear the existing tree, and rebuilds a unique binary search tree 
	 * with the pre-order and post-order traversals that are passed in.
	 * Draw a tree out on paper and generate the appropriate traversals.
	 * See if you can manipulate these lists to generate the same tree.
	 * 
	 * TL;DR - at the end of this method, the tree better have the same
	 * pre-order and post-order as what was passed in.
	 * 
	 * @param preOrder A list containing the data in a pre-order linearization.
	 * @param postOrder A list containing the data in a post-order linearization.
	 */
	public void reconstruct(List<? extends T> preOrder, List<? extends T> postOrder) {
		//TODO Implement this method!
		root = null;
		size = 0;
		addAll(preOrder);
	}
}
