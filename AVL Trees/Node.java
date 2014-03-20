
/**
 * Don't modify this class
 */
public class Node<T> {
		
		private T data;
		private Node<T> right;
		private Node<T> left;
		
		public Node(T data) {
			this.data = data;
			this.right = null;
			this.left = null;
		}
		
		public T getData() {
			return data;
		}
		
		public void setData(T data) {
			this.data = data;
		}
		
		public Node<T> getRight() {
			return right;
		}
		
		public void setRight(Node<T> right) {
			this.right = right;
		}
		
		public Node<T> getLeft() {
			return left;
		}
		
		public void setLeft(Node<T> left) {
			this.left = left;
		}
	}