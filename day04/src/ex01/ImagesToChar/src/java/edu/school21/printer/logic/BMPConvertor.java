package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;

public class BMPConvertor {
	
	public static void printBMPImage(char white, char black, URL path) throws IOException {
		BufferedImage image = ImageIO.read(path);
		for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) {
			for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) {
				int color = image.getRGB(yPixel, xPixel);
				if (color == Color.BLACK.getRGB()) {
					System.out.print(black);
				} else {
					System.out.print(white);
				}
			}
			System.out.println();
		}
	}
}
