package fixmemarket.instruments;

import fixmemarket.types.*;
import java.util.*;

public class InstrumentList
{
	public static List<InstrumentObject> instrument_List;
	private static InstrumentObject instrument;

	public static List<InstrumentObject> createInstrumentList()
	{
		try{
			instrument_List = new ArrayList<>();
			String[] instrument_names = {"BTC", "LTC", "GNOSIS","RIPPLE", "ETHEREUM"};
			int[] order_prices = {200, 150, 170, 185, 100};
			int[] order_quantity = {80, 15, 80, 20, 60};

			for(int i = 0; i <  5; i++)
			{
				instrument = new InstrumentObject(i, instrument_names[i], order_quantity[i], order_prices[i]);
				instrument_List.add(instrument);
			}
		}
		catch(RuntimeException ex){
			System.out.println("Invalid input");
		}
		return instrument_List;

	}
}
