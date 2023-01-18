package edu.school21.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Main {

	@Parameter(names = "--server-port")
	private static int port;
	private static BufferedReader in;
	private static BufferedWriter out;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Error! Add argument: java - jar target/socket-server.jar --server-port=8081");
			System.exit(-1);
		}
		JCommander.newBuilder().addObject(new Main()).build().parse(args);
		try (Socket socket = new Socket("localhost", port)) {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println(in.readLine());
			Scanner scanner = new Scanner(System.in);
			while (true) {
				String message = scanner.nextLine();
				sendMessage(message);
				message = in.readLine();
				System.out.println(message);
				if (message.equals("Enter username:")) {
					break ;
				}
			}
			sendMessage(scanner.nextLine());
			System.out.println(in.readLine());
			sendMessage(scanner.nextLine());
			System.out.println(in.readLine());
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void sendMessage(String message) {
		try {
			out.write(message + "\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
