package edu.school21.printer.app;

import java.io.IOException;
import com.beust.jcommander.JCommander;
import edu.school21.printer.logic.ArgParser;
import edu.school21.printer.logic.BMPConvertor;

public class Program {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 2 || !args[0].split("=")[0].equals("--white") || !args[1].split("=")[0].equals("--black"))	{
			System.err.println("Incorrect usage!\nUsage: program_name --white=<char for white pixel> --black=<char for black pixel>!");
			System.err.println("You can use!\nColors: Black, Red, Green, Yellow, Blue, Magenta, Cyan, White");
			System.exit(-1);
		}
		ArgParser argParser = new ArgParser();
		JCommander.newBuilder().addObject(argParser).build().parse(args);
		BMPConvertor.printBMPImage(argParser.getColor(argParser.getWhitePixel()), argParser.getColor(argParser.getBlackPixel()), Program.class.getResource("/resources/it.bmp"));
	}
}
