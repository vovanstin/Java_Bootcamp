package edu.school21.sockets.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import edu.school21.sockets.repositories.MessageRepositoryImpl;
import edu.school21.sockets.services.UsersService;

@Component
@Parameters(separators = "=")
public class Server {

	@Parameter(names = "--port")
	private int port;

	private UsersService usersService;
	private static LinkedList<ClientSocket> list;
	private MessageRepositoryImpl messageRepository;

	public Server(UsersService usersService, MessageRepositoryImpl messageRepository) {
		this.usersService = usersService;
		list = new LinkedList<>();
		this.messageRepository = messageRepository;
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			while (true) {
				list.add(new ClientSocket(serverSocket.accept(), usersService, messageRepository));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static LinkedList<ClientSocket> getList() {
		return list;
	}
	
}
