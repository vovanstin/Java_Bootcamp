import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.*;

public class FileManager {
	private Path path;

	FileManager (Path path) {
		this.path = path;
	}
	
	public void startCatchCommands() {
		Scanner scanner = new Scanner(System.in);
		String command = scanner.nextLine().trim();

		while (!command.equals("exit")) {
			if (command.length() != 0) {
				if (command.split(" ")[0].equals("ls")) {
					if (command.split(" ").length == 1) {
						ls();
					} else {
						System.err.println("ls: Wrong amount of arguments");
					}
				} else if (command.split(" ")[0].equals("cd")) {
					if (command.split(" ").length == 2) {
						cd(path.resolve(Paths.get(command.split(" ")[1])));
					} else {
						System.err.println("cd: Wrong amount of arguments");
					}
				} else if (command.split(" ")[0].equals("mv")) {
					if (command.split(" ").length == 3) {
						mv(path.resolve(Paths.get(command.split(" ")[1])), path.resolve(Paths.get(command.split(" ")[2])));
					} else {
						System.err.println("mv: Wrong amount of arguments");
					}
				} else {
					System.err.println("Command \'" + command.split(" ")[0] + "\' not found");
				}
			}
			command = scanner.nextLine().trim();
		}
		scanner.close();
	}

	private void ls() {
		for (File f : new File(path.toString()).listFiles()) {
			System.out.println(f.getName() + " " + f.length() / 1024 + " KB");
		}
	}

	private void cd(Path newPath) {
		if (!Files.exists(newPath) || !Files.isDirectory(newPath)) {
			System.err.println("cd: no such file or directory: " + newPath.toString());
		} else {
			path = newPath.normalize();
			System.out.println(path.toString());
		}
	}

	private void mv(Path obj, Path path) {
		if (!Files.exists(obj)) {
			System.err.println("mv: " + obj.getFileName() + ": No such file or directory");
		}
		if (Files.isDirectory(path)) {
			path = Paths.get(path.toString() + "/" + obj.getFileName());
		}
		try {
			Files.move(obj, path, REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
