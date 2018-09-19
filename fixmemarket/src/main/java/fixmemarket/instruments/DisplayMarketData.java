package fixmemarket.instruments;

import fixmemarket.types.*;
import java.util.*;

public class DisplayMarketData
{
	public static void Display(List<InstrumentObject> instrument_List)
	{
		for(InstrumentObject io : instrument_List)
		{
			System.out.println("Id. " + io.getId() + " " + io.getName().toUpperCase() + " Quantity = " + io.getQuantity() + " Price = " + io.getPrice());
		}
	}
}
