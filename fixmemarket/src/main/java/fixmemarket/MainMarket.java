package fixmemarket;

import fixmecore.Connector;
import fixmemarket.instruments.DisplayMarketData;
import fixmemarket.types.*;
import fixmemarket.instruments.InstrumentList;
import java.util.*;
public class MainMarket
{

	static List<InstrumentObject> instrument_List;
	private Connector connector;

	public MainMarket()
    {
		connector = new Connector(5001, new fixmemarket.Reply());
		if (!connector.connect()) {
			System.out.println("Error connecting to router");
			return ;
		}
		connector.sendMessage("Hello");
		instrument_List = InstrumentList.createInstrumentList();
		DisplayMarketData.Display(instrument_List);
		try {
			Thread.currentThread().join();
		}
		catch (InterruptedException err) {
			System.out.println("Unable to join threads");
		}
	}

	public static void main(String[] args)
    {
		new MainMarket();
	}
}
