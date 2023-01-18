import java.util.Scanner;

public class Program {

	public static void sendError() {
		System.err.println("Illegal Argument");
		System.exit(-1);
	}
	
	public static void printStat(long array, int weekCounter) {
		int		i = 1;
		long	j;

		while (weekCounter > 0) {
			System.out.print("Week " + i + " ");
			j = array % 10;
			array /= 10;
			while (j > 0) {
				System.out.print("=");
				j -= 1;
			}
			System.out.println(">");
			i += 1;
			weekCounter -= 1;
		}
	}

	public static long pow(int weekCounter) {
		long pow = 1;

		while (weekCounter > 0) {
			pow *= 10;
			weekCounter -= 1;
		}
		return (pow);
	}

	public static int findMin(Scanner scanner) {
		int i = 4;
		int	min;
		int	num;
	
		if (!scanner.hasNextInt()) {
			sendError();
		}
		min = scanner.nextInt();
		if (min < 1 || min > 9) {
			sendError();
		}
		while (i > 0) {
			if (!scanner.hasNextInt()) {
				sendError();
			}
			num = scanner.nextInt();
			if (num < 1 || num > 9) {
				sendError();
			}
			if (num < min) {
				min = num;
			}
			i -= 1;
		}
		return (min);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int		weekCounter = 1;
		String	line = "";
		long	array = 0;

		if (scanner.hasNextInt()) {
			if (scanner.nextInt() == 42) {
				System.exit(0);
			} else {
				sendError();
			}
		}
		while (weekCounter != 19) {
			line = scanner.next();
			if (!line.equals("Week")) {
				if (line.equals("42")) {
					break ;
				}
				sendError();
			} else {
				if (scanner.nextInt() != weekCounter) {
					sendError();
				}
			}
			array += pow(weekCounter - 1) * findMin(scanner);
			weekCounter += 1;
		}
		if (line.equals("42") || weekCounter == 19) {
			printStat(array, weekCounter - 1);
		} else {
			sendError();
		}
		scanner.close();
	}
}
