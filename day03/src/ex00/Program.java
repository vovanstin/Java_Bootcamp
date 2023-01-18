public class Program {
	public static void main(String[] args) throws InterruptedException {
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
					egg.join();
					hen.join();
					printMessage("Human", Integer.parseInt(args[0].split("=")[1]));
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
			Thread.sleep(1);
			System.out.println(mes);
		}
	}
}