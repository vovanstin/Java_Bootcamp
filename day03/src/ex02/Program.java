import java.util.Random;

public class Program {

	public static void main(String[] args) throws InterruptedException {
		if (args.length == 2) {
			if (args[0].split("=")[0].equals("--arraySize") && args[1].split("=")[0].equals("--threadsCount")) {
				int sizeArray = Integer.parseInt(args[0].split("=")[1]);
				int countThread = Integer.parseInt(args[1].split("=")[1]);
				if (sizeArray >= 1 && countThread >= 1) {
					Random random = new Random();
					int[] array = new int[sizeArray];
					for (int i = 0; i < sizeArray; i++) {
						array[i] = random.nextInt(2001) - 1000;
					}
					MyRunnable [] runnable = new MyRunnable[countThread];
					int  index = 0;
					for (int i = 0; i < countThread; i++) {
						if (i + 1 == countThread) {
							runnable[i] = new MyRunnable(i + 1, array, index, sizeArray - 1);
							new Thread(runnable[i]).start();
						} else {
							runnable[i] = new MyRunnable(i + 1, array, index, index + sizeArray / countThread - 1);
							new Thread(runnable[i]).start();
							index += sizeArray / countThread;
						}
					}
					int sum = 0;
					for (int i = 0; i < countThread; i++) {
						sum += runnable[i].getSum();
					}
					System.out.println("Threade sum = " + sum);
					sum = 0;
					for (int i = 0; i < sizeArray; i++) {
						sum += array[i];
					}
					System.out.println("Loop sum = " + sum);
				} else {
					System.err.println("Error! Wrong SIZE | COUNT");
				}
			} else {
				System.err.println("Error! Input --arraySize=SIZE --threadsCount=COUNT");
			}
		} else {
			System.err.println("Error! Input --arraySize=SIZE --threadsCount=COUNT");
		}
	}
}
