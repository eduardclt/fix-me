package fixmecore;

import java.nio.channels.CompletionHandler;
import java.nio.channels.ReadPendingException;

import fixmecore.Attachment;
import java.io.IOException;
import java.nio.charset.Charset;

public class ReadWriteHandler implements CompletionHandler<Integer, Attachment> {

	@Override
	public void completed(Integer result, Attachment attach) {
		if (result == -1) {
			try {
				attach.client.close();
				System.out.format("Stopped   listening to the client %s%n",
						attach.clientAddr);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return;
		}

		//System.out.println("Is read write :: " + attach.isRead + " | port :: " + attach.mainPort);

		if (attach.isRead) {
			attach.buffer.flip();
			int limits = attach.buffer.limit();
			byte bytes[] = new byte[limits];
			attach.buffer.get(bytes, 0, limits);
			Charset cs = Charset.forName("UTF-8");
			String msg = new String(bytes, cs);
			if (attach.response != null) {
				attach.response.processMessage(msg, this, attach);
			}
		} else {
			if (attach.mustRead) {
				//System.out.println("Must read");

				attach.isRead = true;
				attach.buffer.clear();
				try {
					attach.client.read(attach.buffer, attach, this);
				}
				catch (ReadPendingException err) {
					System.out.println("Read Pending Exception :: " + err.getMessage());
				}
			}
		}
	}

	@Override
	public void failed(Throwable e, Attachment attach) {
		//e.printStackTrace();
		if (!attach.isBroker) {
			System.out.println("Market offline");
			attach.mustRead = false;
			if (attach.response != null) {
				attach.response.processMessage("offline", this, attach);
			}
			//Connector.sendStaticMessage("offline", attach, this);
		}
		else {
			System.out.println("Broker offline");
		}
		// System.out.println("Exception attached");
	}

}
