package edu.school21.sockets.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Instant;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepositoryImpl;
import edu.school21.sockets.services.UsersService;

public class ClientSocket extends Thread {
	
	private Socket socket;
	private UsersService usersService;
	private MessageRepositoryImpl messageRepository;
	private BufferedReader in;
	private BufferedWriter out;
	private User user;
	private boolean isOnline = false;

	public ClientSocket(Socket socket, UsersService usersService, MessageRepositoryImpl messageRepository) {
		this.socket = socket;
		this.usersService = usersService;
		this.messageRepository = messageRepository;
		this.start();
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			sendMessage("Hello from Server!");
			String input;
			while (true) {
				input = in.readLine();
				if (input.equals("signUp")) {
					signUp();
				} else if (input.equals("signIn")) {
					if (signIn()) {
						break ;
					}
				} else if (input.equals("Exit")) {
					break ;
				} else {
					sendMessage("Use commands: signUp, signIn, Exit");
				}
			}
			while (true && !input.equals("Exit")) {
				input = in.readLine();
				if (input.equals("Exit")) {
					break ;
				}
				messageRepository.save(new Message(null, user, input, Timestamp.from(Instant.now())));
				for (ClientSocket client : Server.getList()) {
					if (client.getSocket().isConnected()) {
						if (client.isOnline) {
							client.sendMessage(user.getUsername() + ": " + input);
						}
					} else {
                        Server.getList().remove(client);
                    }
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean signIn() {
		try {
			String username;
			String password;
			sendMessage("Enter username:");
			username = in.readLine();
			sendMessage("Enter password:");
			password = in.readLine();
			user = usersService.signIn(username, password);
			sendMessage("Start messaging!");
			isOnline = true;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			sendMessage(e.getMessage());
		}
		return false;
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

	public Socket getSocket() {
		return socket;
	}

}
