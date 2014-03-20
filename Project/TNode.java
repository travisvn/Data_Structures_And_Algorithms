/**
 * Simplistic Node class for Trie
 * 
 * @author Joshua Alley
 * 
 */
public class TNode {

	public boolean endWord = false;
	public char c;
	public TNode[] children;
	public TNode parent;
	public TNode failState;
	public int count;

	/**
	 * Constructor for TNode.
	 * 
	 * @param c the character stored in node
	 */
	public TNode(char c) {
		children = new TNode[26];
		this.c = c;
	}

	/**
	 * Creates a chain of TNodes, each holding a char in  the String
	 * 
	 * @param s the user inputted string.
	 */
	public void addWord(String s) {

		TNode n = getChild(s.charAt(0));
		
		int i = s.charAt(0) - 'a';
			if (children[i] == null) {
				children[i] = new TNode(s.charAt(0));
				children[i].parent = this;
			}
		if (s.length() > 1) {
			children[i].addWord(s.substring(1)); // truncate first character
		} else
			children[i].endWord = true;
	}

	public TNode getChild(char ch) {
		return children[ch - 'a'];
	}

}
