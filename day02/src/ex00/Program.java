import java.io.IOException;
import java.util.Map;

public class Program {

	public static void main(String[] args) throws IOException {
		SignatureList signatureList = new SignatureList();
		Map <short[], String> signatures = signatureList.getSignatureList("signatures.txt");
		ParseFile parse = new ParseFile(signatures);
		parse.createResultFile("result.txt");
	}
}
