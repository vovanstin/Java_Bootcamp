import java.nio.file.Files;
import java.nio.file.Paths;

public class Program {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Error! Input --current-folder=DIR");
			System.exit(-1);
		}
		if (Files.exists(Paths.get(args[0].split("=")[1])) && Files.isDirectory(Paths.get(args[0].split("=")[1]))) {
			FileManager fm = new FileManager(Paths.get(args[0].split("=")[1]));
			fm.startCatchCommands();
		} else {
			System.err.println("Error! Input real directory");
			System.exit(-1);
		}
	}
}
