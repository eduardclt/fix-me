package fixmemarket;

import fixmecore.*;
import fixmemarket.instruments.InstrumentList;
import fixmemarket.types.InstrumentObject;

import java.util.List;

public class Reply implements MessageResponse{
    FIXController controller = new FIXController();
	private List<InstrumentObject> instrument_List;

	public void processMessage(String messageGiven, ReadWriteHandler readWriteHandler, Attachment attach) {
		if (messageGiven.startsWith("registerId:")) {
			System.out.println("Market id : " + messageGiven.substring("registerId:".length()));
			Connector.listenToWrite(attach, readWriteHandler);
			return ;
		}
		if (CheckSum.validatecheckSum(messageGiven))
		{
			System.out.println("Checksum okay");
			FIXModel fixModel = controller.readToObject(messageGiven);
			fixModel.ORDER_STATUS = "2";
			String fixMessage = controller.GenerateFixMsgFromModel(fixModel);
			Transactions transactions = new Transactions(fixModel, MainMarket.instrument_List);
			fixModel = transactions.ProcTransactions(fixMessage);
			fixMessage = controller.GenerateFixMsgFromModel(fixModel);
			fixMessage = CheckSum.generatecheckSum(fixMessage);
			if (fixModel.getORDER_STATUS().equals("1")) {
				System.out.println("Order excecuted");
			}
			else {
				System.out.println("Order rejected");
			}
			System.out.println("Market Message :: " + fixMessage);
			Connector.sendStaticMessage(fixMessage, attach, readWriteHandler);
		}
		else {
			System.out.println("Checksum failed");
		}
    }
}
