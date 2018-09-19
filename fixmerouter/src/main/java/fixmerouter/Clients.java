package fixmerouter;

import fixmecore.Attachment;
import java.util.List;
import java.util.ArrayList;

public class Clients {
	private static List<Attachment>	attachedClients = new ArrayList<Attachment>();

	public static void addClient(Attachment attach) {
		System.out.println("New client added :: " + attach.id);
		attachedClients.add(attach);
	}

	public static Attachment findMarket(String marketId) {
		for (Attachment tempAttach : attachedClients) {
			if (tempAttach.id.equals(marketId) && !tempAttach.isBroker) {
				return (tempAttach);
			}
		}
		return (null);
	}

	public static Attachment findBroker(String brokerId) {
		for (Attachment tempAttach : attachedClients) {
			if (tempAttach.id.equals(brokerId) && tempAttach.isBroker) {
				return (tempAttach);
			}
		}
		return (null);
	}
}
