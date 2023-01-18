package edu.school21.printer.app;

import java.io.IOException;

import edu.school21.printer.logic.BMPConvertor;


public class Program {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 3 || args[0].length() > 1 || args[1].length() > 1)	{
			System.err.println("Incorrect usage!\nUsage: program_name <char for white pixel> <char for black pixel> <full path to image>!");
			System.exit(-1);
		}
		BMPConvertor.printBMPImage(args[0].charAt(0), args[1].charAt(0), args[2]);
	}
}
