package edu.school21.sockets.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.beust.jcommander.JCommander;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;

public class Main {
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Error! Add argument: java - jar target/socket-server.jar --port=8081");
			System.exit(-1);
		}
		ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
		Server server = context.getBean(Server.class);
		JCommander.newBuilder().addObject(server).build().parse(args);
		server.start();
	}
}
