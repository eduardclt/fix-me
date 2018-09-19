package fixmemarket.types;

public class InstrumentObject
{
	protected int id;
	private int order_price;
	private int order_quantity;
	private String instrument_name;
	private static int idCounter = 0;

	public InstrumentObject(int id, String instrumentName, int quantity, int price)
	{
		this.id = id;
		this.instrument_name = instrumentName;
		this.order_price = price;
		this.order_quantity = quantity;
	}

	private int nextId()
	{
		return(++idCounter);
	}

	 public int getId() {
        return id;
    }

    public int getPrice() {
        return order_price;
    }

    public void setPrice(int price) {
        this.order_price = price;
    }

    public int getQuantity() {
        return order_quantity;
    }

    public void setQuantity(int quantity) {
        this.order_quantity = quantity;
    }

    public String getName() {
        return instrument_name;
    }	
};
