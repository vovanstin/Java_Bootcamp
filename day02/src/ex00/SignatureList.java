import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignatureList {
	
	public Map <short[], String> getSignatureList(String path) throws FileNotFoundException {
		Map <short[], String> signatures = new HashMap<short[], String>();
		short[] key;
		Scanner scanner = new Scanner(new FileInputStream(path));
		String line;
		String value;
		
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			key = getKeys(getKeyString(line));
			value = getValueString(line);
			if (key.length > 0 && !value.isEmpty()) {
				signatures.put(key, value);
			}
		}
		scanner.close();
		return (signatures);
	}

	private String getKeyString(String line) {
		return (line.substring(line.indexOf(',') + 2));
	}

	private short[] getKeys(String line) {
		short[] keys = new short[getKeysCount(line)];
		Scanner scanner = new Scanner(line).useRadix(16);
		
		for (int i = 0; i < keys.length; i++) {
			keys[i] = scanner.nextShort();
		}
		scanner.close();
		return (keys);
	}

	private int getKeysCount(String line) {
		int count = 0;
		Scanner scanner = new Scanner(line).useRadix(16);

		while (scanner.hasNextShort()) {
			scanner.nextShort();
			count++;
		}
		scanner.close();
		return (count);
	}

	private String getValueString(String line) {
		return (line.substring(0, line.indexOf(',')));
	}
}
