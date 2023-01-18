package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public class BMPConvertor {
	
	public static void printBMPImage(Attribute white, Attribute black, URL path) throws IOException {
		BufferedImage image = ImageIO.read(path);
		for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) {
			for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) {
				int color = image.getRGB(yPixel, xPixel);
				if (color == Color.BLACK.getRGB()) {
					System.out.print(Ansi.colorize(" ", black));
				} else {
					System.out.print(Ansi.colorize(" ", white));
				}
			}
			System.out.println();
		}
	}
}
