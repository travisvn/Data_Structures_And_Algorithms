import java.io.*;

public class TestClass {
	
	public static void main(String[] args) {
		
		Trie t = new Trie();
		/*t.addWord("his");
		t.addWord("her");    //Add words of your choice
		t.addWord("he");
		t.addWord("she");
		t.search("hello his her he she");*/
		
		//file source
		File f = new File("src/file");
		try {
			t.search(TestClass.readFile(f));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public static String readFile(File f) throws IOException {
		BufferedReader read = new BufferedReader(new FileReader(f));
		String s = "";
		try {
			while (read.ready()) {
				s += read.readLine();
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return s;
	}
}