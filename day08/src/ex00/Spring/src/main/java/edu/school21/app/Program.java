package edu.school21.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithPrefixImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererErrImpl;

public class Program {
	
	public static void main(String[] args) {
		{
			PreProcessor preProcessor = new PreProcessorToUpperImpl();
			Renderer renderer = new RendererErrImpl(preProcessor);
			PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
			printer.setPrefix("Prefix");
			printer.print("Hello!");
		}
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
			Printer printer = context.getBean("printerWithPrefix", Printer.class);
			printer.print("Hello!");
		}
	}
}
