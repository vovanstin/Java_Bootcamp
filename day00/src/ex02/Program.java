import java.util.Scanner;

public class Program {

	public static int getSum(int number) {
		int	sum = 0;

		while (number != 0) {
			sum += number % 10;
			number /= 10;
		}
		return (sum);
	}
	
	public static boolean isPrime(int number) {
		int 	chkNum = 3;
		
		if (number % 2 == 0) {
			return (number == 2);
		} else {
			while (chkNum * chkNum <= number && number % chkNum != 0) {
				chkNum += 2;
			}
			return (chkNum * chkNum > number);
		}
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int		number;
		int		requestCount = 0;

		number = input.nextInt();
		while (number != 42) {
			if (number < 2) {
				System.err.println("Illegal Argument");
				System.exit(-1);
			}
			if (isPrime(getSum(number)))
				requestCount += 1;
			number = input.nextInt();
		}
		System.out.print("Count of coffee-request - ");
		System.out.println(requestCount);
		input.close();
	}
}
