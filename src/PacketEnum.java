import java.util.regex.Pattern;

/**
 * Alle packets die de client kan binnenkrijgen
 * @author pim
 *
 */
public enum PacketEnum {

	SETPLAYERNAME,
	PLAYERCOMMANDCONSOLE,
	UNKNOWN,
	CURRENTLOCATION,
	SOUND,
	BG;
	
	/**
	 * Returned the packet mbv een string
	 * @param packetName
	 * @return
	 */
	public static PacketEnum getPacket(String packetName) {
		if (packetName != null) {
			for (PacketEnum packet : values()) {
				//Contains the string
				if (Pattern.compile(Pattern.quote(packet.name()), Pattern.CASE_INSENSITIVE).matcher(packetName).find()) {
					return packet;
				}
			}
		}
		return null;
	}
	
	/**
	 * Is het een packet?
	 * @param packetName
	 * @return
	 */
	public static boolean isPacket(String packetName) {
		if (packetName != null) {
			for (PacketEnum packet : values()) {
				if (packet.name().contains(packetName)) {
					return true;
				}
			}
		}
		return false;
	}
}
