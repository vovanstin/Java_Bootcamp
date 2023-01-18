package edu.school21.sockets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class WriteMsg extends Thread {
	
	private BufferedWriter writer;

	public WriteMsg(BufferedWriter writer) {
		this.writer = writer;
	}

	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String message = scanner.nextLine();
			if (message.equals("Exit")) {
				sendMessage(message);
				break ;
			}
			sendMessage(message);
		}
		scanner.close();
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void sendMessage(String message) {
		try {
			writer.write(message + "\n");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
