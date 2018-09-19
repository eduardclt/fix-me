package fixmecore;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class Attachment {
	public String							id;
	public AsynchronousServerSocketChannel	server;
	public AsynchronousSocketChannel		client;
	public ByteBuffer						buffer;
	public SocketAddress					clientAddr;
	public InetSocketAddress				serverAddr;
	public MessageResponse					response;
	public int								mainPort;
	public boolean							isBroker;
	public boolean							isRead;	
	public boolean							mustRead;
	public String							tempString;
}
