package fixmebroker;

import fixmecore.Connector;
import fixmecore.FIXModel;
import fixmecore.FIXController;
import fixmecore.CheckSum;
import fixmecore.MessageResponse;

public class MainBroker {

	private FIXController controller;
	private Connector connector;
	private MessageResponse responseHandler;

	public MainBroker(FIXModel model) {
		this.controller = new FIXController();
		connector = new Connector(5000, new Reply());
		if (!connector.connect()) {
			System.out.println("Error connecting to router");
			return ;
		}
		String FIXString = controller.GenerateFixMsgFromModel(model);
		//FIXString = CheckSum.generatecheckSum(FIXString);
		connector.sendMessage(FIXString);
		try {
			Thread.currentThread().join();
		}
		catch (InterruptedException err) {
			System.out.println("Error :: " + err.getMessage());
		}
	}

	public static boolean isNumeric(String strNum) {
		try {
			double d = Double.parseDouble(strNum);
			if (d <= 0){
				throw new NumberFormatException("Arguments should be a positive integer.");
			}
		} catch (NumberFormatException | NullPointerException nfe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		if (args.length != 5) {
			System.out.println("Usage: [java -jar app.jar MARKET_ID REQUEST_TYPE INSTRUMENT QUANTITY ORDER_PRICE]");
		} else {
			if (isNumeric(args[0]) && isNumeric(args[3]) && isNumeric(args[4]) && ( args[1].equalsIgnoreCase("buy") || args[1].equalsIgnoreCase("sell"))) {
				FIXModel model = new FIXModel("400000", args[2], args[3], args[0], args[4], "0", args[1]);
				new MainBroker(model);
			}else{
				System.out.println("Usage: [java -jar app.jar MARKET_ID REQUEST_TYPE INSTRUMENT QUANTITY ORDER_PRICE]");
			}
		}
	}
}
