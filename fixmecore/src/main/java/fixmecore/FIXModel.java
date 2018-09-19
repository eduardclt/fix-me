package fixmecore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class FIXModel {

    public String SENDER_ID;
    public String INSTRUMENT;
    public String ORDER_QUANTITY;
    public String MARKET_ID;
    public String ORDER_PRICE;
    public String ORDER_STATUS;
    public String REQUEST_TYPE;

    public String getSENDER_ID() {
        return SENDER_ID;
    }

    public void setSENDER_ID(String id) {
        this.SENDER_ID = id;
    }

    public String getINSTRUMENT() {
        return INSTRUMENT;
    }

    public String getORDER_QUANTITY() {
        return ORDER_QUANTITY;
    }

    public String getMARKET_ID() {
        return MARKET_ID;
    }

    public String getORDER_PRICE() {
        return ORDER_PRICE;
    }

    public String getORDER_STATUS() {
        return ORDER_STATUS;
    }

    public String getREQUEST_TYPE() {
        return REQUEST_TYPE;
    }





    public FIXModel(String sender_id, String instrument, String order_quantity, String market_id,
                    String order_price, String order_status, String request_type){
        SENDER_ID = sender_id;
        INSTRUMENT = instrument;
        ORDER_QUANTITY = order_quantity;
        MARKET_ID = market_id;
        ORDER_PRICE = order_price;
        ORDER_STATUS = order_status;
        REQUEST_TYPE = request_type;
    }
}
