import java.util.Scanner;

public class Program {
	
	public static void printGraph(char[] letters, int[] countLetters, int strLen) {
		int[] graph = new int[10];

		graph[0] = 10;
		for (int i = 1; i < graph.length && i < strLen; i++) {
			graph[i] = countLetters[i] * 10 / countLetters[0];
		}
		for (int i = 11; i >= 0; i--) {
			for (int j = 0; j < graph.length && j < strLen; j++) {
				if (graph[j] + 1 == i) {
					System.out.printf("%3d ", countLetters[j]);
				} else if (i == 0) { 
					System.out.printf("%3c ", letters[j]);
				} else if (graph[j] >= i) {
					System.out.printf("%3c ", '#');
				} else {
					break ;
				}
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		Scanner	scanner = new Scanner(System.in);
		String	str = scanner.nextLine();
		char[]	initArray = str.toCharArray();
		char[]	letters = new char[999];
		int[]	countLetters = new int[999];
		int		strLen = 0;
		int		j = 0;
		int		tempInt;
		char	tempChar;


		for (int i = 0; i < initArray.length; i++) {
			for (j = 0; j < strLen; j++) {
				if (initArray[i] == letters[j]) {
					break ;
				}
			}
			if (j == strLen) {
				letters[j] = initArray[i];
				countLetters[j] = 1;
				strLen += 1;
			} else {
				countLetters[j] += 1;
			}
		}
		for (int i = strLen - 1; i > 0; i--) {
			for (j = 0; j < i; j++) {
				if ((countLetters[j] < countLetters[j + 1])
						|| (countLetters[j] == countLetters[j + 1]
						&& letters[j] > letters[j + 1])) {
					tempInt = countLetters[j];
					countLetters[j] = countLetters[j + 1];
					countLetters[j + 1] = tempInt;
					tempChar = letters[j];
					letters[j] = letters[j + 1];
					letters[j + 1] = tempChar;
				}
			}
		}
		printGraph(letters, countLetters, strLen);
		scanner.close();
	}
}
