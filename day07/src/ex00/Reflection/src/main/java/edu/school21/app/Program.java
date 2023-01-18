package edu.school21.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class Program {
	private final static String delimetr = "---------------------";
	private final static String defualtPackage = "edu.school21.classes";
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String className;
		Object object;
		
		printAllClasses();
		System.out.println(delimetr);
		className = printFieldsAndMethods(scanner);
		System.out.println(delimetr);
		object = createObject(scanner, className);
		System.out.println(delimetr);
		updateObject(scanner, object);
		System.out.println(delimetr);
		invokeMethod(scanner, object);
		scanner.close();
	}

	private static void invokeMethod(Scanner scanner, Object object) {
		System.out.println("Enter name of the method for call:");
		try {
			String methodName = scanner.nextLine();
			Method method = null;
			for (Method m : object.getClass().getDeclaredMethods()) {
                if (m.getName().equals(methodName)) {
                    method = m;
                    break;
                }
            }
			Parameter [] parameters = method.getParameters();
			List<Object> inputParams = new ArrayList<>();
			for (Parameter parameter : parameters) {
				System.out.println("Enter " + parameter.getType().getSimpleName() + " value:");
				inputParams.add(scannerGetType(parameter.getType().getSimpleName(), scanner));
				if (!parameter.getType().getSimpleName().toLowerCase().equals("string")) {
					scanner.nextLine();
				}
			}
			Object returnedValue = method.invoke(object, inputParams.toArray());
			if (!method.getReturnType().getSimpleName().equals("void")) {
				System.out.println("Method returned:");
				System.out.println(returnedValue);
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static void updateObject(Scanner scanner, Object object) {
		System.out.println("Enter name of the field for changing:");
		try {
			Field field = object.getClass().getDeclaredField(scanner.nextLine());
			field.setAccessible(true);
			System.out.println("Enter " + field.getType().getSimpleName() + " value:");
			field.set(object, scannerGetType(field.getType().getSimpleName(), scanner));
			if (!field.getType().getSimpleName().toLowerCase().equals("string")) {
				scanner.nextLine();
			}
			System.out.println("Object updated: " + object);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static String printFieldsAndMethods(Scanner scanner) {
		String className;

		System.out.println("Enter class name:");
		className = scanner.nextLine();
		System.out.println(delimetr);
		try {
			Field []fields = Class.forName(defualtPackage + "." + className).getDeclaredFields();
			if (fields.length > 0) {
				System.out.println("fields:");
				for (Field f : fields) {
					System.out.println("\t" + f.getType().getSimpleName() + " " + f.getName());
				}
			}
			Method []methods = Class.forName(defualtPackage + "." + className).getDeclaredMethods();
			if (methods.length > 0) {
				System.out.println("methods:");
				for (Method m : methods) {
					System.out.print("\t" + m.getReturnType().getSimpleName() + " " + m.getName() + "(");
					Class<?> []parameters = m.getParameterTypes();
					if (parameters.length > 0) {
						for (int i = 0; i < parameters.length; i++) {
							System.out.print(parameters[i].getSimpleName());
							if (i < parameters.length - 1) {
								System.out.print(", ");
							}
						}
					}
					System.out.println(")");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return className;
	}

	private static Object createObject(Scanner scanner, String className) {
		System.out.println("Let's create an object.");
		Object object = null;
		try {
			Constructor<?> []constructors = Class.forName(defualtPackage + "." + className).getDeclaredConstructors();
			Field []fields = Class.forName(defualtPackage + "." + className).getDeclaredFields();
			for (Constructor<?> constructor : constructors) {
				Parameter []parameters = constructor.getParameters();
				List<Object> inputParams = new ArrayList<>();
				if (parameters.length > 0) {
					for (int i = 0; i < parameters.length; i++) {
						System.out.println(fields[i].getName() + ":");
						inputParams.add(scannerGetType(parameters[i].getType().getSimpleName(), scanner));
						if (!parameters[i].getType().getSimpleName().toLowerCase().equals("string")) {
							scanner.nextLine();
						}
					}
					object = constructor.newInstance(inputParams.toArray());
					System.out.println("Object created: " + object);
					break ;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}

	private static void printAllClasses() {
		List<Class<?>> classes = getAllClassesFrom(defualtPackage);
		System.out.println("Classes:");
		for (Class<?> class1 : classes) {
			System.out.println("  - " + class1.getSimpleName());
		}
	}

	private static Object scannerGetType(String str, Scanner scanner) {
		switch (str.toLowerCase()) {
			case "string":
				return scanner.nextLine();
			case "int":
			case "integer":
				return scanner.nextInt();
			case "long":
				return scanner.nextLong();
			case "double":
				return scanner.nextDouble();
			case "float":
				return scanner.nextFloat();
			case "char":
			case "character":
				return scanner.next();
			default:
				throw new RuntimeException("Unrecognized type");
		}
	}

	private static List<Class<?>> getAllClassesFrom(String packageName) {
		return new Reflections(packageName, new SubTypesScanner(false))
				.getAllTypes()
				.stream()
				.map(name -> {
					try {
						return Class.forName(name);
					} catch (ClassNotFoundException e) {
						return null;
					}
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
}
