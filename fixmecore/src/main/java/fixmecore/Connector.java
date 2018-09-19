package fixmecore;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadPendingException;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.nio.ByteBuffer;

public class Connector {
	private int port;
	private Attachment attach;
	private ReadWriteHandler readwriteHandler;
	private MessageResponse callingClass;
	
	private void attachClientObject(AsynchronousSocketChannel channel) {
		this.attach = new Attachment();
		this.attach.client = channel;
		this.attach.buffer = ByteBuffer.allocate(2048);
		this.attach.isRead = false;
		this.attach.response = this.callingClass;
		this.attach.mainPort = this.port;
		this.attach.isBroker = this.port == 5000;
		this.attach.mustRead = true;
	}

	public Connector(int port) {
		this.callingClass = null;
		this.port = port;
	}

	public Connector(int port, MessageResponse callingClass) {
		this.port = port;
		this.callingClass = callingClass;
	}

	public boolean connect() {
		try {
			AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
			InetSocketAddress client = new InetSocketAddress("localhost", this.port);
			Future<Void> result = channel.connect(client);
			result.get();
			attachClientObject(channel);
			this.readwriteHandler = new ReadWriteHandler();
			System.out.println("Connected");
			return (true);
		}
		catch (Exception err) {
			System.out.println("Error connecting to port ::" + this.port);
		}
		return (false);
	}

	public void	sendMessage(String message) {
		byte[] data = "register".getBytes();
		this.attach.buffer.clear();
		this.attach.buffer.rewind();
		this.attach.buffer.put(data);
		this.attach.buffer.flip();
		this.attach.isRead = false;
		this.attach.mainPort = this.port;
		this.attach.mustRead = true;
		this.attach.tempString = message;
		this.attach.client.write(this.attach.buffer, this.attach, this.readwriteHandler);
	}

	public static void sendStaticMessage(String message, Attachment staticAttach, ReadWriteHandler readWriteHandler) {
		byte[] data = message.getBytes();
		staticAttach.buffer.clear();
		staticAttach.buffer.rewind();
		staticAttach.buffer.put(data);
		staticAttach.buffer.flip();
		staticAttach.isRead = false;
		staticAttach.client.write(staticAttach.buffer, staticAttach, readWriteHandler);
	}

	public static void listenToWrite(Attachment staticAttachment, ReadWriteHandler readWriteHandler) {
		staticAttachment.isRead = true;
		staticAttachment.buffer.clear();
		staticAttachment.client.read(staticAttachment.buffer, staticAttachment, readWriteHandler);
	}
}
