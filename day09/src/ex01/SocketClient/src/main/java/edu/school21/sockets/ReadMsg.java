package edu.school21.sockets;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadMsg extends Thread {
	
	private BufferedReader reader;

	public ReadMsg(BufferedReader reader) {
		this.reader = reader;
	}

	@Override
	public void run() {
		while (true) {
			String message;
			try {
				message = reader.readLine();
				System.out.println(message);
			} catch (Exception  e) {
				try {
					System.out.println("You have left the chat.");
					reader.close();
					System.exit(0);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}


}
