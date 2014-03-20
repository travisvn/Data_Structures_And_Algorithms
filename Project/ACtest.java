import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class ACtest {

	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentTest() {
		Trie mt = new Trie();
		mt.addWord("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalArgumentTest2() {
		Trie mt2 = new Trie();
		mt2.addWord("1");		
	}
	
	@Test
	public void failFunctionTest(){
		Trie fail = new Trie();
		fail.addWord("he");
		fail.addWord("she");
		fail.addWord("his");
		fail.addWord("hers");
		fail.failFunc();
		assertEquals(fail.root, fail.root.failState);
		//root children failState = root
		TNode root = fail.root;
		TNode h = fail.root.children['h'-'a'];
		TNode s = fail.root.children['s'-'a'];
		assertEquals(h.failState, root);
		assertEquals(s.failState, root);
		//test second-depth failState
		TNode he = h.children['e'-'a'];
		TNode hi = h.children['i'-'a'];
		TNode sh =s.children['h'-'a'];
		assertEquals(he.failState, root);
		assertEquals(hi.failState, root);
		assertEquals(sh.failState, h);
		//test third-depth failStates
		TNode her = he.children['r'-'a'];
		TNode his = hi.children['s'-'a'];
		TNode she = sh.children['e'-'a'];
		assertEquals(her.failState, root);
		assertEquals(his.failState, s);
		assertEquals(she.failState, h.children['e'-'a']);
		//test final depth
		TNode hers = her.children['s'-'a'];
		assertEquals(hers.failState, s);
	}
	
	@Test
	public void fullTextTest(){
		Trie abc = new Trie();
		abc.addWord("a");
		abc.addWord("b");
		abc.addWord("c");
		abc.addWord("ab");
		abc.addWord("ac");
		abc.addWord("ba");
		abc.addWord("bc");
		abc.addWord("ca");
		abc.addWord("cb");
		abc.addWord("abc");
		abc.search("abc");
		assertEquals((Integer)1, abc.words.get("a"));
		assertEquals((Integer)1, abc.words.get("b"));
		assertEquals((Integer)1, abc.words.get("c"));
		assertEquals((Integer)1, abc.words.get("ab"));
		assertEquals((Integer)0, abc.words.get("ac"));
		assertEquals((Integer)0, abc.words.get("ba"));
		assertEquals((Integer)1, abc.words.get("bc"));
		assertEquals((Integer)0, abc.words.get("ca"));
		assertEquals((Integer)0, abc.words.get("cb"));
		assertEquals((Integer)1, abc.words.get("abc"));
		
	}
	
	@Test
	public void searchTextFile() throws IOException{
		Trie custom = new Trie();
		custom.addWord("Hello");
		custom.addWord("this");
		custom.addWord("idk");
		custom.addWord("i");
		custom.search(TestClass.readFile(new File("src/file")));
		assertEquals((Integer)3, custom.words.get("i"));
	}

}
