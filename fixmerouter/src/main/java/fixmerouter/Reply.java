package fixmerouter;

import fixmecore.Attachment;
import fixmecore.FIXController;
import fixmecore.ReadWriteHandler;
import fixmecore.FIXModel;
import fixmecore.MessageResponse;
import fixmecore.Connector;
import fixmecore.CheckSum;

public class Reply implements MessageResponse {

	private Attachment attach;
	private FIXController controller;

	public Reply(Attachment attach) {
		this.attach = attach;
	}

	private void sendToMarket(String message, ReadWriteHandler readWriteHandler, Attachment staticAttach) {
		FIXModel	model;
		String		responseMessage;
		Attachment	marketAttachment;

		controller = new FIXController();
		if ((model = controller.readToObject(message)) != null) {
			if ((marketAttachment = Clients.findMarket(model.MARKET_ID)) != null) {
				System.out.println("market found");
				marketAttachment.mustRead = true;
				marketAttachment.tempString = message;
				Connector.sendStaticMessage(message, marketAttachment, readWriteHandler);
			}
			else {
				System.out.println("market not found");
				Connector.sendStaticMessage(message.toLowerCase(), staticAttach, readWriteHandler);
			}
		}
		else {
			Connector.sendStaticMessage("SENDER_ID=1|ORDER_TYPE=1|ORDER_QUANTITY=1|MARKET_ID=1|ORDER_PRICE=1|ORDER_STATUS=2|REQUEST_TYPE=1|CHECKSUM=aa27d768bedf4790644899b5fa034b11",
				staticAttach, readWriteHandler);
		}
	}

	private void sendToBroker(String message, ReadWriteHandler readWriteHandler, Attachment staticAttach) {
		FIXModel	model;
		String		responseMessage;
		Attachment	marketAttachment;

		controller = new FIXController();
		if ((model = controller.readToObject(message)) != null) {
			if ((marketAttachment = Clients.findBroker(model.SENDER_ID)) != null) { 
				System.out.println("Broker found");
				marketAttachment.mustRead = false;
				Connector.sendStaticMessage(message, marketAttachment, readWriteHandler);
			}
			else {
				System.out.println("Broker not found");
				marketAttachment.mustRead = false;
				Connector.sendStaticMessage(message.toLowerCase(), staticAttach, readWriteHandler);
			}
		}
		else {
			Connector.sendStaticMessage("SENDER_ID=1|ORDER_TYPE=1|ORDER_QUANTITY=1|MARKET_ID=1|ORDER_PRICE=1|ORDER_STATUS=2|REQUEST_TYPE=1|CHECKSUM=aa27d768bedf4790644899b5fa034b11",
				staticAttach, readWriteHandler);
		}
	}

	public void processMessage(String message, ReadWriteHandler readWriteHandler, Attachment staticAttach) {
		if (message.equals("register")) {
			if (staticAttach.isBroker) {
				System.out.println("Broker Message :: " + message);
				String sendString = "registerId:" + staticAttach.id;
				staticAttach.mustRead = true;
				staticAttach.isRead = false;
				Connector.sendStaticMessage(sendString, staticAttach, readWriteHandler);
			}
			else {
				System.out.println("Market Message :: " + message);
				String sendString = "registerId:" + staticAttach.id;
				staticAttach.isRead = false;
				Connector.sendStaticMessage(sendString, staticAttach, readWriteHandler);
			}
			return ;
		}
		if (message.equals("offline")) {
			System.out.println("Send to broker offline status");
			sendToBroker(staticAttach.tempString, readWriteHandler, staticAttach);
			return ;
		}
		if (CheckSum.validatecheckSum(message)) {
			if (staticAttach.isBroker) {
				System.out.println("Broker Message :: " + message);
				sendToMarket(message, readWriteHandler, staticAttach);
			}
			else {
				System.out.println("Market Message :: " + message);
				sendToBroker(message, readWriteHandler, staticAttach);
			}
		} else {
			Connector.sendStaticMessage("SENDER_ID=1|ORDER_TYPE=1|ORDER_QUANTITY=1|MARKET_ID=1|ORDER_PRICE=1|ORDER_STATUS=2|REQUEST_TYPE=1|CHECKSUM=aa27d768bedf4790644899b5fa034b11",
				staticAttach, readWriteHandler);
		}
	}
}
