public class Program {
	static Object lock = new Object();
	static int flag = 1;
	public static void main(String[] args) {
		if (args.length == 1) {
			if (args[0].split("=")[0].equals("--count")) {
				if (Integer.parseInt(args[0].split("=")[1]) >= 1) {
					Thread egg = new Thread(() -> {
						try {
							printMessage("Egg", Integer.parseInt(args[0].split("=")[1]));
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.exit(-1);
						}
					});
					Thread hen = new Thread(() -> {
						try {
							printMessage("Hen", Integer.parseInt(args[0].split("=")[1]));
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.exit(-1);
						}
					});
					egg.start();
					hen.start();
				} else {
					System.err.println("Error! Wrong SIZE");
				}
			} else {
				System.err.println("Error! Input --count=SIZE");
			}
		} else {
			System.err.println("Error! Input --count=SIZE");
		}
	}

	public static void printMessage(String mes, int count) throws InterruptedException {
		for (int i = 0; i < count; i++) {
			synchronized(lock) {
				if (mes.equals("Egg")) {
					while (flag < 1) {
						lock.wait();
					}
					flag = 0;
				} else {
					while (flag == 1) {
						lock.wait();
					}
					flag = 1;
				}
				System.out.println(mes);
				lock.notify();
			}
		}
	}
}
