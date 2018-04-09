
public class Misc {

	/**
	 * De eerst letter als hoofdletter gebruiken
	 * @param str
	 * @return
	 */
	public static String capitalizeJustFirst(String str) {
		str = str.toLowerCase();
		if (str.length() > 1) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str.toUpperCase();
		}
		return str;
	}
}
