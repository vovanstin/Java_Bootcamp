import java.io.IOException;
import java.util.List;

public class Program {
	
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Wrong amount of arguments");
		} else {
			Dictionary dict = new Dictionary(args[0], args[1]);
			List<String> dictionary = dict.getDictionary();
			Similarity similarity = new Similarity();
			System.out.printf("Similarity = %.2f\n", similarity.getSimilarity(args[0], args[1], dictionary));
		}
	}
}
