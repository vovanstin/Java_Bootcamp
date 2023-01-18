package edu.school21.processor;

import java.io.FileWriter;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.google.auto.service.AutoService;

import edu.school21.annotation.HtmlForm;
import edu.school21.annotation.HtmlInput;

@SupportedAnnotationTypes("edu.school21.annotation.HtmlForm")
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
		for (Element annotatedElement : annotatedElements) {
			HtmlForm htmlForm = annotatedElement.getAnnotation(HtmlForm.class);
			try (FileWriter fileWriter = new FileWriter("target/classes/" + htmlForm.fileName())) {
				fileWriter.write(
						String.format("<form action = \"https://localhost%s\" method = \"%s\">", htmlForm.action(), htmlForm.method()));
				for (Element e : annotatedElement.getEnclosedElements()) {
					HtmlInput htmlInput = e.getAnnotation(HtmlInput.class);
					if (htmlInput != null) {
						fileWriter.write(String.format("\t<input type = \"%s\" name = \"%s\" placeholder = \"%s\">",
								htmlInput.type(), htmlInput.name(), htmlInput.placeholder()));
					}
				}
				fileWriter.write("\t<input type = \"submit\" value = \"Send\">");
				fileWriter.write("</form>");
				fileWriter.flush();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}
}
