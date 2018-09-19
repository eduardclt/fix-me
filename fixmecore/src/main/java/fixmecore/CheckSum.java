package fixmecore;

public class CheckSum {
	private static String separator = "|";

	public static String generatecheckSum(String message) {
		String checksum = "CHECKSUM=";
		String checkValue = "";

		checkValue = Encrypt.encrypt(message);
		return (message + separator + checksum + checkValue);
	}

	public static boolean validatecheckSum(String message)  {
		if (message.contains("|")) {
			String originalMessage = message.substring(0, message.lastIndexOf("|"));
			String checkVerify = message.substring(message.lastIndexOf("=") + 1);
			//System.out.println("Original Message : " + originalMessage);
			String sumCode = Encrypt.encrypt(originalMessage);
			return (checkVerify.equals(sumCode));
		}
		return (false);
	}
}

