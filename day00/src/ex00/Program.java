public class Program {

	public static void main(String[] args) {
		int	number;
		int	sum;

		number = 479598;
		sum = 0;
		sum += number / 100000;
        number %= 100000;
        sum += number / 10000;
        number %= 10000;
        sum += number / 1000;
        number %= 1000;
        sum += number / 100;
        number %= 100;
        sum += number / 10;
        number %= 10;
        sum += number;
		System.out.println(sum);
	}
}