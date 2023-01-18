package edu.school21.sockets.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Component;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import edu.school21.sockets.services.UsersService;

@Component
@Parameters(separators = "=")
public class Server {

	@Parameter(names = "--port")
	private int port;

	private UsersService usersService;
	private BufferedReader in;
	private BufferedWriter out;

	public Server(UsersService usersService) {
		this.usersService = usersService;
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			Socket sockets = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(sockets.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sockets.getOutputStream()));
			sendMessage("Hello from Server!");
			while (true) {
				String input = in.readLine();
				if (input.equals("signUp")) {
					signUp();
					break ;
				}
				sendMessage("Use command: signUp");
			}
			sockets.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void signUp() {
		try {
			String username;
			String password;
			sendMessage("Enter username:");
			username = in.readLine();
			sendMessage("Enter password:");
			password = in.readLine();
			usersService.signUp(username, password);
			sendMessage("Successful!");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			sendMessage(e.getMessage());
		}
	}

	private void sendMessage(String message) {
		try {
			out.write(message + "\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
