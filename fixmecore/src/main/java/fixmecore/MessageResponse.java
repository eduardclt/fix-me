package fixmecore;

public interface MessageResponse {
	 void processMessage(String message, ReadWriteHandler readWriteHandler, Attachment attach);
}
