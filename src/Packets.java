
/**
 * Alle pakcets die de client kan bovenkrijgen en gaat deze dan bewerken
 * @author pim
 *
 */
public class Packets {

	private Client client;
	private GameFrame frame;
	
	public Packets(Client client, GameFrame frame) {
		setClient(client);
		setFrame(frame);
	}
	
	public void handlePackets(PacketEnum packet, String packetString) {

		String splitted[] = packetString.split("::"+packet.name());
		
		if (splitted.length < 1) {
			return;
		}
		
		switch (packet) {
		case PLAYERCOMMANDCONSOLE:
			getFrame().appendTextToAreaCommands(splitted[1]);
			break;
			
		case SETPLAYERNAME:
        	String splittedTwo[] = packetString.split("-");
        	getClient().setPlayerName(splittedTwo[1]);
        	getFrame().appendTextToAreaCommands(splitted[1].replaceAll("-", " "));
			break;
			
		case UNKNOWN:
			getFrame().appendTextToArea(Misc.capitalizeJustFirst(packetString));
			break;
		case CURRENTLOCATION:
			
			break;
			
		case SOUND:
			getFrame().playMusicNew(splitted[1].trim());
			break;
			
		case BG:
			getFrame().playMusicRepeat(splitted[1].trim());
			break;
		
		}
	}
	

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public GameFrame getFrame() {
		return frame;
	}


	public void setFrame(GameFrame frame) {
		this.frame = frame;
	}
}
