package edu.school21.printer;

import java.time.LocalDateTime;

import edu.school21.renderer.Renderer;

public class PrinterWithDateTimeImpl implements Printer {
	
	private Renderer renderer;
	
	public PrinterWithDateTimeImpl(Renderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void print(String message) {
		renderer.render(LocalDateTime.now() + " " + message);
	}
}
