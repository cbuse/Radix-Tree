



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Opens a file for reading and provides a stream of words resulting from its parsing.
 */
public final class FileParser {

	String filePath;
	BufferedReader reader;
	Queue<String> wordBuffer;

	public FileParser(String filePath) {
		this.filePath = filePath;
		this.reader = null;
		this.wordBuffer = new LinkedList<String>();
	}

	/**
	 * Opens the file for reading.
	 * <p>
	 * This operation must be performed before calling any other methods on this object.
	 */
	public void open() {
		if (reader != null) {
			throw new IllegalStateException("File already opened for reading");
		}
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Closes the file opened for reading.
	 * <p>
	 * Should be called after finishing reading.
	 */
	public void close() {
		wordBuffer.clear();
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * Parses the underlying file and returns the next word.
	 *
	 * @return a word as a <code>String<code>, or <code>null<code> if the end of the file has been
	 * reached.
	 */
	public String getNextWord() {

		if (!wordBuffer.isEmpty()) {
			return wordBuffer.poll();
		}

		// refill word buffer
		String[] nextWords = new String[0];
		while (nextWords.length == 0) {
			nextWords = parseNextLine();
			if (nextWords == null) {
				return null;
			}
		}
		wordBuffer.addAll(Arrays.asList(nextWords));
		return wordBuffer.poll();
	}

	private String[] parseNextLine() {
		String line;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (line == null) {
			return null;
		}
		return line.toLowerCase().split("[^a-zA-Z']+");
	}
}



