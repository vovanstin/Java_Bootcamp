public class MyRunnable implements Runnable{
	private int sum = 0;
	private int id;
	private int indexStart;
	private int indexEnd;
	private int[] array;

	MyRunnable(int id, int[] array, int indexStart, int indexEnd) {
		this.id = id;
		this.indexStart = indexStart;
		this.indexEnd = indexEnd;
		this.array = array;
	}

	@Override
	public void run() {
		for (int i = indexStart; i <= indexEnd; i++) {
			sum += array[i];
		}
	}

	public int getSum() {
		System.out.printf("Thread %s: from %d to %d sum is %d\n", id, indexStart, indexEnd, sum);
		return (sum);
	}
}
