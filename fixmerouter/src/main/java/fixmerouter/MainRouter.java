package fixmerouter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import fixmecore.Attachment;
import fixmecore.MessageResponse;

public class MainRouter {

	Clients _clients = new Clients();

	public static void RegisterServer(int port) {
		try {
			AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
			InetSocketAddress socketAddress = new InetSocketAddress("localhost", port);
			server.bind(socketAddress);
			Attachment attach = new Attachment();
			attach.server = server;
			attach.response = new Reply(attach);
			attach.serverAddr = socketAddress;
			server.accept(attach, new ConnectionHandler());
			System.out.println("Server Listening at port :: " + port);
		}
		catch (Exception err) {
			System.out.println("Error listening to port " + port + " :: " + err.getMessage());
		}
	}
	
    public static void main(String[] args) {
		RegisterServer(5000);
		RegisterServer(5001);
		try {
			Thread.currentThread().join();
		}
		catch (InterruptedException err) {
			System.out.println("Error joining threads :: " + err.getMessage());
		}
	}
}
