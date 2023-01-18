package edu.school21.printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.diogonunes.jcolor.Attribute;

@Parameters(separators = "=")
public class ArgParser {

	@Parameter(names = {"--white"})
	private static String WHITE_PIXEL;
	@Parameter(names = {"--black"})
	private static String BLACK_PIXEL;

	public Attribute getColor(String pixel) throws IllegalColorException {
		switch (pixel.toUpperCase()) {
			case "RED":
				return Attribute.RED_BACK();
			case "GREEN":
				return Attribute.GREEN_BACK();
			case "BLUE":
				return Attribute.BLUE_BACK();
			case "BLACK":
				return Attribute.BLACK_BACK();
			case "WHITE":
				return Attribute.WHITE_BACK();
			case "YELLOW":
				return Attribute.YELLOW_BACK();
			case "MAGENTA":
				return Attribute.MAGENTA_BACK();
			case "CYAN":
				return Attribute.CYAN_BACK();
			default:
				throw new IllegalColorException();
		}
	}

	public String getWhitePixel() {
		return (WHITE_PIXEL);
	}

	public String getBlackPixel() {
		return (BLACK_PIXEL);
	}
}