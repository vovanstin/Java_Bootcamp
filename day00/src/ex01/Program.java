import java.util.Scanner;

public class Program {
	
	public static void main(String[] args) {
		Scanner	input = new Scanner(System.in);
		int		number = input.nextInt();
		int 	iter = 1;
		int 	chkNum = 3;
		boolean isPrime;
		
		if (number < 2) {
			System.err.println("Illegal Argument");
			System.exit(-1);
		}
		if (number % 2 == 0) {
			isPrime = number == 2;
		} else {
			while (chkNum * chkNum <= number && number % chkNum != 0) {
				chkNum += 2;
				iter += 1;
			}
			isPrime = chkNum * chkNum > number;
		}
		System.out.print(isPrime);
		System.out.print(" ");
		System.out.println(iter);
		input.close();
	}
}
