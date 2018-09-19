package fixmecore;

public class FIXController {

	public  String GenerateFixMsgFromModel(fixmecore.FIXModel model) {

		String _message = "SENDER_ID=" + model.SENDER_ID
			+ "|ORDER_TYPE=" + model.INSTRUMENT
			+ "|ORDER_QUANTITY=" + model.ORDER_QUANTITY
			+ "|MARKET_ID=" + model.MARKET_ID
			+ "|ORDER_PRICE=" + model.ORDER_PRICE
			+ "|ORDER_STATUS=" + model.ORDER_STATUS
			+ "|REQUEST_TYPE=" + model.REQUEST_TYPE;

		return _message;
	}

	public fixmecore.FIXModel readToObject(String line) {
		try {
			String SenderID = ((line.split("\\|")[0]).split("=")[1]);
			String Instrument = ((line.split("\\|")[1]).split("=")[1]);
			String OrderQuantity = ((line.split("\\|")[2]).split("=")[1]);
			String MarketID = ((line.split("\\|")[3]).split("=")[1]);
			String OrderPrice = ((line.split("\\|")[4]).split("=")[1]);
			String OrderStatus = ((line.split("\\|")[5]).split("=")[1]);
			String RequestType = ((line.split("\\|")[6]).split("=")[1]);
			return (new FIXModel(SenderID, Instrument, OrderQuantity, MarketID, OrderPrice, OrderStatus, RequestType));
		}
		catch (ArrayIndexOutOfBoundsException err) {
			System.out.println("Array out of bounds :: " + line);
		}
		return (null);
	}
}
