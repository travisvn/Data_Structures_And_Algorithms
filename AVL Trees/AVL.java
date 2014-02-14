import java.util.Collection;
import java.util.List;


/**
 * CS 1332 Fall 2013
 * AVL Tree
 * 
 * In this class, you will program an AVL Tree (Adelson Veskii-Landis Tree).
 * This is like a better version of a binary search tree in that it 
 * tries to fill out every level of the tree as much as possible. It
 * accomplishes this by keeping track of each node's height and balance
 * factor. As you recurse back up from operations that modify the tree
 * (like add or remove), you will update the height and balance factor
 * of the current node, and perform a rotation on the current node if 
 * necessary. Keeping this in mind, let's get started!
 * 
 * **************************NOTE*************************************
 * please please please  treat null as positive infinity!!!!!!!!
 * PLEASE TREAT NULL AS POSITIVE INFINITY!!!!
 * *************************NOTE**************************************
 * 
 * I STRONLY RECOMMEND THAT YOU IMPLEMENT THIS DATA STRUCTURE RECURSIVELY!
 * 
 * Please make any new internal classes, instance data, and methods private!!
 * 
 * DO NOT CHANGE ANY OF THE PUBLIC METHOD HEADERS
 */
public class AVL<T extends Comparable<T>> {
	
	private AVLNode<T> root;
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
	
	private AVLNode<T> addHelper(AVLNode<T> curr, T data){
		
		if (curr==null){
			AVLNode<T> newRoot =  new AVLNode<T>(data);
			updateHeightAndBF(newRoot);
			return newRoot;
		}

		if (compare(data, curr.getData()) < 0) {
			
			
			curr.setLeft(addHelper((AVLNode<T>)curr.getLeft(), data));
			updateHeightAndBF(curr);
			
			
		}
		if (compare(data, curr.getData()) >= 0){
			
			
			curr.setRight(addHelper((AVLNode<T>)curr.getRight(), data));			
			updateHeightAndBF(curr);
			
			
		}
		return rotate(curr);
		
		
	}
	
	/**
	 * I promise you, this is just like the add() method you coded
	 * in the BST part of the homework! You will start off at the
	 * root and find the proper place to add the data. As you 
	 * recurse back up the tree, you will have to update the
	 * heights and balance factors of each node that you visited
	 * while reaching the proper place to add your data. Immediately
	 * before you return out of each recursive step, you should update
	 * the height and balance factor of the current node and then
	 * call rotate on the current node. You will then return the node
	 * that comes from the rotate(). This way, the re-balanced subtrees
	 * will properly be added back to the whole tree. Also, don't forget
	 * to update the size of the tree as a whole.
	 * 
	 * PLEASE TREAT NULL AS POSITIVE INFINITY!!!!
	 * 
	 * @param data The data do be added to the tree.
	 */
	public void add(T data) {
		//TODO Implement this method!
			size++;
			root = addHelper(root, data);
	}
	
	
	/**
	 * This is a pretty simple method. All you need to do is to get
	 * every element in the collection that is passed in into the tree.
	 * 
	 * Try to think about how you can combine a for-each loop and your
	 * add method to accomplish this.
	 * 
	 * @param c A collection of elements to be added to the tree.
	 */
	public void addAll(Collection<? extends T> c){
		//TODO Implement this method!
		for (T data : c){
			add(data);
		}
	}
	
	/**
	 * All right, now for the remove method. Just like in the vanilla BST, you
	 * will have to traverse to find the data the user is trying to remove. 
	 * 
	 * You will have three cases:
	 * 
	 * 1. Node to remove has zero children.
	 * 2. Node to remove has one child.
	 * 3. Node to remove has two children.
	 * 
	 * For the first case, you simply return null up the tree. For the second case,
	 * you return the non-null child up the tree. 
	 * 
	 * Just as in add, you'll have to updateHeightAndBF() as well as rotate() just before
	 * you return out of each recursive step.
	 * 
	 * FOR THE THIRD CASE USE THE PREDECESSOR OR YOU WILL LOSE POINTS
	 * 
	 * @param data The data to search in the tree.
	 * @return The data that was removed from the tree.
	 */
	public T remove(T data) {
		//TODO Implement this method!
		if (!contains(data)){
			return null;
		}
		T returnData = get(data);
		
		root = removeChild(root, data);
		updateHeightAndBF(root);
		
		size--;
		return returnData;
	}
	
	private AVLNode<T> removeChild(AVLNode<T> node, T data) {
		//TODO
		if (node == null) {
			return null;
		}

		else if (compare(data, node.getData()) < 0){
			node.setLeft(removeChild(node.getLeft(), data));
			updateHeightAndBF(node);
		}

		else if (compare(data, node.getData())>0){
			node.setRight(removeChild(node.getRight(), data));
			updateHeightAndBF(node);
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
			updateHeightAndBF(node);
			}
		}		
		
		return rotate(node);
	}
	
	/*
	 * Gets the data from the rightmost node in the subtree
	 * 
	 * @param Node<T> t the node to the immediate left of the node to be deleted
	 * @return T the data in the node
	 */
	private T getData(AVLNode<T> t){
		while(t.getRight() != null) {
			t = t.getRight();
		}
		return t.getData();
	}
	
	/**
	 * This method should be pretty simple, all you have to do is recurse
	 * to the left or to the right and see if the tree contains the data.
	 * 
	 * @param data The data to search for in the tree.
	 * @return The boolean flag that indicates if the data was found in the tree or not.
	 */
	public boolean contains(T data) {
		//TODO Implement this method!
		return containsHelper(root, data);	
	}
	
	
	private boolean containsHelper(AVLNode<T> current, T data){
		
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
	
	
	
	/**
	 * Again, simply recurse through the tree and find the data that is passed in.
	 * 
	 * @param data The data to fetch from the tree.
	 * @return The data that the user wants from the tree. Return null if not found.
	 */
	public T get(T data) {
		//TODO Implement this method!
		if(contains(data)){
			return getHelper(root, data);
		}
		return null;
	}
	
	private T getHelper(AVLNode<T> current, T oldData) {
		//T newData;
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
	 * Test to see if the tree is empty.
	 * 
	 * @return A boolean flag that is true if the tree is empty.
	 */
	public boolean isEmpty(){
		//TODO Implement this method!
		return size==0;
	}
	
	/**
	 * Return the number of data in the tree.
	 * 
	 * @return The number of data in the tree.
	 */
	public int size() {
		//TODO Implement this method!
		return size;
	}
	
	/**
	 * Reset the tree to its original state. Get rid of every element in the tree.
	 */
	public void clear() {
		//TODO Implement this method!
		size=0;
		root = null;
	}
	
	// The below methods are all private, so we will not be directly grading them,
	// however we strongly recommend you not change them, and make use of them.
	
	
	/**
	 * Use this method to update the height and balance factor for a node.
	 * 
	 * @param node The node whose height and balance factor need to be updated.
	 */
	private void updateHeightAndBF(AVLNode<T> node) {
		//TODO Implement this method!
		if(node.getLeft() == null && node.getRight() == null){
			node.setBF(0);
			node.setHeight(0);
		}
		else if (node.getLeft() == null){
			node.setBF(-1 - node.getRight().getHeight());
			node.setHeight(node.getRight().getHeight()+1);
		}
		else if (node.getRight() == null){
			node.setBF(node.getLeft().getHeight() + 1);
			node.setHeight(node.getLeft().getHeight()+1);
		}
		else{
			node.setBF(node.getLeft().getHeight() - node.getRight().getHeight());
			if (node.getLeft().getHeight() > node.getRight().getHeight()){
				node.setHeight(node.getLeft().getHeight()+1);
			}
			else {
				node.setHeight(node.getRight().getHeight()+1);
			}
		}
	}
	
	/**
	 * In this method, you will check the balance factor of the node that is passed in and
	 * decide whether or not to perform a rotation. If you need to perform a rotation, simply
	 * call the rotation and return the new root of the balanced subtree. If there is no need
	 * for a rotation, simply return the node that was passed in.
	 * 
	 * @param node - a potentially unbalanced node
	 * @return The new root of the balanced subtree.
	 */
	private AVLNode<T> rotate(AVLNode<T> node) {
		//TODO Implement this method!
		if (node == null){
			return node;
		}
		if (node.getBf() > 1){
			if (node.getLeft().getBf() >= 0){
				node = rightRotate(node);
			}
			else {
				node = leftRightRotate(node);

			}
		}
		else if (node.getBf() < -1 ) {
			if (node.getRight().getBf() <= 0){
				node = leftRotate(node);

			}
			else{
				node = rightLeftRotate(node);

			}
		}		
		return node;
	}
	
	/**
	 * In this method, you will perform a left rotation. Remember, you perform a 
	 * LEFT rotation when the sub-tree is RIGHT heavy. This moves more nodes over to
	 * the LEFT side of the node that is passed in so that the height differences
	 * between the LEFT and RIGHT subtrees differ by at most one.
	 * 
	 * HINT: DO NOT FORGET TO RE-CALCULATE THE HEIGHT OF THE NODES
	 * WHOSE CHILDREN HAVE CHANGED! YES, THIS DOES MAKE A DIFFERENCE!
	 * 
	 * @param node - the current root of the subtree to rotate.
	 * @return The new root of the subtree
	 */
	private AVLNode<T> leftRotate(AVLNode<T> node) {
		//TODO Implement this method!
		AVLNode<T> newRoot = node.getRight();
		node.setRight(newRoot.getLeft());
		newRoot.setLeft(node);	
		
		updateHeightAndBF(node);	
		updateHeightAndBF(newRoot);
		return newRoot;
	}
	
	/**
	 * In this method, you will perform a right rotation. Remember, you perform a
	 * RIGHT rotation when the sub-tree is LEFT heavy. THis moves more nodes over to
	 * the RIGHT side of the node that is passed in so that the height differences
	 * between the LEFT and RIGHT subtrees differ by at most one.
	 * 
	 * HINT: DO NOT FORGET TO RE-CALCULATE THE HEIGHT OF THE NODES
	 * WHOSE CHILDREN HAVE CHANGED! YES, THIS DOES MAKE A DIFFERENCE!
	 * 
	 * @param node - The current root of the subtree to rotate.
	 * @return The new root of the rotated subtree.
	 */
	private AVLNode<T> rightRotate(AVLNode<T> node) {
		//TODO Implement this method!
		AVLNode<T> newRoot = node.getLeft();
		node.setLeft(newRoot.getRight());
		newRoot.setRight(node);
		
		updateHeightAndBF(node);		
		updateHeightAndBF(newRoot);
		return newRoot;
	}
	
	/**
	 * In this method, you will perform a left-right rotation. You can simply use
	 * the left and right rotation methods on the node and the node's child. Remember
	 * that you must perform the rotation on the node's child first, otherwise you will
	 * end up with a mangled tree (sad face). After rotating the child, remember to link up
	 * the new root of the that first rotation with the node that was passed in.
	 * 
	 * The whole point of heterogeneous rotations is to transform the node's 
	 * subtree into one of the cases handled by the left and right rotations.
	 * 
	 * @param node
	 * @return The new root of the subtree.
	 */
	private AVLNode<T> leftRightRotate(AVLNode<T> node) {
		//TODO Implement this method!
		node.setLeft(leftRotate(node.getLeft()));
		return rightRotate(node);
	}
	
	/**
	 * In this method, you will perform a right-left rotation. You can simply use your
	 * right and left rotation methods on the node and the node's child. Remember
	 * that you must perform the rotation on the node's child first, otherwise
	 * you will end up with a mangled tree (super sad face). After rotating the node's child,
	 * remember to link up the new root of that first rotation with the node that was
	 * passed in.
	 * 
	 * Again, the whole point of the heterogeneous rotations is to first transform the
	 * node's subtree into one of the cases handled by the left and right rotations.
	 * 
	 * @param node
	 * @return The new root of the subtree.
	 */
	private AVLNode<T> rightLeftRotate(AVLNode<T> node) {
		//TODO Implement this method!
		node.setRight(rightRotate(node.getRight()));
		return leftRotate(node);
	}
}
