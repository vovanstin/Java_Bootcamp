package edu.school21.sockets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Main {

	@Parameter(names = "--server-port")
	private static int port;
	private static ReadMsg in;
	private static WriteMsg out;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Error! Add argument: java - jar target/socket-server.jar --server-port=8081");
			System.exit(-1);
		}
		JCommander.newBuilder().addObject(new Main()).build().parse(args);
		try (Socket socket = new Socket("localhost", port)) {
			in = new ReadMsg(new BufferedReader(new InputStreamReader(socket.getInputStream())));
			out = new WriteMsg(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			in.start();
			out.start();

			in.join();
			out.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
