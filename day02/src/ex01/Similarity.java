import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Similarity {

	public double getSimilarity(String path1, String path2, List<String> dictionary) throws IOException {
		Map<String, Integer> vectorA = getVector(path1, dictionary);
		Map<String, Integer> vectorB = getVector(path2, dictionary);
		double numerator = 0;
		double denominatorA = 0;
		double denominatorB = 0;
		
		for (String s : dictionary) {
			numerator += vectorA.get(s) * vectorB.get(s);
			denominatorA += vectorA.get(s) * vectorA.get(s);
			denominatorB += vectorB.get(s) * vectorB.get(s);
		}
		if (denominatorA == 0 || denominatorB == 0) {
			return (0);
		}
		return (numerator/(Math.sqrt(denominatorA) * Math.sqrt(denominatorB)));
	}

	private Map<String, Integer> getVector(String path, List<String> dictionary) throws IOException {
		Map<String, Integer> vector = new HashMap<String, Integer>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
		List<Character> word;
		int c = reader.read();
		String stringWord;
		
		for (String s : dictionary) {
			vector.put(s, 0);
		}
		while (c != -1) {
			word = new ArrayList<Character>();
			while (c != -1 && c != ' ') {
				word.add((char)c);
				c = reader.read();
			}
			stringWord = word.toString().substring(1, 3 * word.size() - 1).replaceAll(", ", ""); 
			vector.replace(stringWord, vector.get(stringWord) + 1);
			if (c != -1) {
				c = reader.read();
			}
		}
		reader.close();
		return (vector);
	}
}
