import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParseFile {
	
	private List<String> result = new ArrayList<String>();

	ParseFile(Map <short[], String> signatures) throws IOException {
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		short[] keys;
		boolean PROCESSED = true;

		while (!line.equals("42")) {
			for (short[] t: signatures.keySet()) {
				PROCESSED = true;
				keys = readKeys(line, t.length);
				for (int i = 0; i < t.length; i++) {
					if (t[i] != keys[i]) {
						PROCESSED = false;
						break ;
					}
				}
				if (PROCESSED) {
					result.add(signatures.get(t));
					break ;
				}
			}
			if (PROCESSED) {
				System.out.println("PROCESSED");
			} else {
				System.out.println("UNDEFINED");
			}
			line = scanner.nextLine();
		}
		scanner.close();
	}
	
	private short[] readKeys(String path, int len) throws IOException  {
		FileInputStream input = new FileInputStream(path);
		short[] keys = new short[len];

		for (int i = 0; i < keys.length; i++) {
			keys[i] = (short)input.read();
		}
		input.close();
		return (keys);
	}

	public void createResultFile(String fileName) throws IOException {
		FileOutputStream output = new FileOutputStream(fileName);

		for (int i = 0; i < result.size(); i++) {
			if (i != 0) {
				output.write('\n');
			}
			for (char c : result.get(i).toCharArray()) {
				output.write(c);
			}
		}
		output.close();
	}
}
