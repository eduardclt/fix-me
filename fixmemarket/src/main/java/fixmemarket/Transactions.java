package fixmemarket;

import fixmemarket.instruments.*;
import fixmecore.FIXController;
import fixmecore.FIXModel;
import fixmemarket.types.InstrumentObject;
import java.util.*;

public class Transactions
{
	private  List<InstrumentObject> instrument_List;
	private String[] response = {"1", "2"};
	FIXModel model;
	FIXController controller = new FIXController();

	public Transactions(FIXModel model, List<InstrumentObject> instrumentList)
	{
		this.model = model;
		this.instrument_List = instrumentList;
	}

	private String Buy()
	{
		boolean transactionSuccessful = false;
		for(InstrumentObject io : this.instrument_List)
		{
			try{
				int totalPrice = io.getPrice() * Integer.parseInt(model.ORDER_QUANTITY);
				if(io.getName().equalsIgnoreCase(model.INSTRUMENT) && totalPrice <= Integer.parseInt(model.ORDER_PRICE))
				{
					if (Integer.parseInt(model.ORDER_QUANTITY) > io.getQuantity()){
						transactionSuccessful = false;
					}
					else {
						io.setQuantity(io.getQuantity() - Integer.parseInt(model.ORDER_QUANTITY));
						transactionSuccessful = true;
					}
				}
			}
			catch(RuntimeException ex){
				System.out.println("Invalid input");
			}
		}
		if(!transactionSuccessful)
			return response[1];
		return response[0];
	}
	private String Sell()
	{
		boolean transactionSuccessful = false;
		for (InstrumentObject io : this.instrument_List)
		{
			int total_price = io.getPrice() * Integer.parseInt(model.ORDER_QUANTITY);
			if (io.getName().equalsIgnoreCase(model.INSTRUMENT) && total_price >= Integer.parseInt(model.ORDER_PRICE))
			{
				io.setQuantity(io.getQuantity() + Integer.parseInt(model.ORDER_QUANTITY));
				transactionSuccessful = true;
			}
		}
		if (!transactionSuccessful)
			return response[1];
		return response[0];
	}

	public FIXModel ProcTransactions(String res_message)
	{
		String request_Type = null;
		model = controller.readToObject(res_message);

		if(model.REQUEST_TYPE.equalsIgnoreCase("BUY"))
		{
			request_Type = this.Buy();
		}
		else if(model.REQUEST_TYPE.equalsIgnoreCase("SELL"))
		{
			request_Type = this.Sell();
		}

		DisplayMarketData.Display(this.instrument_List);
		this.model.ORDER_STATUS = request_Type;
		return this.model;
	}
}

