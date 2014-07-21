


/**
 * This execution entry point class handles the parsing, indexing and lookup of words in a text.
 */
public class Index {

	/**
	 * Execution entry point.
	 *
	 * @param args two strings representing the name of the file that contains the text to index,
	 * and the name of the file containing words to lookup in the text.
	 */
	public static void main(String[] args) {
		Integer index = -1;
		
		if (args.length != 2) {
			System.err.println("Usage: java -cp <classpath> Index <text file> <word file>");
			System.exit(1);
		}		
		
		FileParser textFile = new FileParser(args[0]);
		textFile.open();
		
		RadixTree trie = new RadixTree();
		String word;
				
		while ((word = textFile.getNextWord()) != null) {
			index ++;
			//concatenez indexul din text la valoare ca sa pastrez pozitia
			String word1 = word + "*"+index.toString();
			
			trie.insert(word1);
		}
		textFile.close();
		
		
		
		FileParser prefixFile = new FileParser(args[1]);
		prefixFile.open();
		
		while ((word = prefixFile.getNextWord()) != null) {
			//System.out.print(word + " ");
			trie.getIndexes(word);	
			
		}
		//trie.display();
		
		

	}		
}		


