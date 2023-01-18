import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dictionary {
	private List<String> dictionary = new ArrayList<String>();

	Dictionary(String path1, String path2) throws IOException {
		readDictionaryFromFile(path1);
		readDictionaryFromFile(path2);
		createDictionaryFile();
	}

	private void readDictionaryFromFile(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		List<Character> word;
		int c = reader.read();
		String stringWord;

		while (c != -1) {
			word = new ArrayList<Character>();
			while (c != -1 && c != ' ') {
				word.add((char)c);
				c = reader.read();
			}
			stringWord = word.toString().substring(1, 3 * word.size() - 1).replaceAll(", ", ""); 
			if (!dictionary.contains(stringWord)) {
				dictionary.add(stringWord);
			}
			if (c != -1) {
				c = reader.read();
			}
		}
		dictionary.sort(null);
		reader.close();
	}

	public void createDictionaryFile() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"));

		for (String s : dictionary) {
			writer.write(s);
			if (dictionary.indexOf(s) != dictionary.size() - 1) {
				writer.write(", ");
			}
		}
		writer.close();
	}

	public List<String> getDictionary() {
		return (this.dictionary);
	}
}
