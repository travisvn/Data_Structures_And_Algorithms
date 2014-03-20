import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Very simple Trie implementation for the Aho-Corasick Algorithm
 * 
 * @author Joshua Alley
 * 
 */
public class Trie {
	public TNode root;
	public HashMap<String, Integer> words;

	public Trie() {
		root = new TNode('\0');
		words = new HashMap<String, Integer>();
	}

	public void addWord(String s) {
		if (!s.matches("[A-Za-z]+"))
			throw new IllegalArgumentException();
		s = s.toLowerCase();
		words.put(s, 0);
		root.addWord(s.toLowerCase());
	}

	public void failFunc() {
		Queue<TNode> queue = new LinkedList<TNode>();
		root.failState = root;
		queue.add(root);
		while (!queue.isEmpty()) {
			TNode t = queue.poll();
			TNode child;
			for (int i = 0; i < 26; i++) {
				child = t.children[i];
				if (child != null)
					queue.add(child);
			}
			if (t == root)
				continue;// root's failState is root.

			//Move to parent's fail function, traverse downward to find longest prefix
			TNode failState = t.parent.failState;
			while (failState.getChild(t.c) == null && failState != root)
				failState = failState.failState;

			t.failState = failState.getChild(t.c); 
			// no suffix
			if (t.failState == null || t.failState == t)
				t.failState = root;
		}
	}

	public void search(String text) {
		failFunc();
		text = text.toLowerCase();
		TNode currState = root;
		TNode n;
		TNode no;
		char p;
		for (int i = 0; i < text.length(); i++) {
			n = currState;
			p = text.charAt(i);
			if (p - 'a' < 0 || p - 'a' > 25) {
				currState = root;
				continue;
			}
				

			while (n.getChild(p) == null && n != root)
				n = n.failState;

			if (n == root) {
				n = n.getChild(p);
				if (n == null)
					n = root;
			} else
				n = n.getChild(p);

		no = n;
		while (no!=root){
			if (no.endWord) {/* "Here's the word!" */
				String s = "";
				TNode temp = no;
				no.count++;
				while (no != root) {
					s = no.c + s;
					no = no.parent;
				}
				int inc = words.get(s) + 1;
				words.put(s, inc);
				no = temp;
			}
			//System.out.println(no.c);
			no = no.failState;
		}
			currState = n;
		}
		System.out.println(words);
	}

}