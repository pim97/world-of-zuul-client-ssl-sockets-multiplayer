/**
 * Representations for all the valid command words for the game
 * along with a string in a particular language.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public enum CommandWord
{
    // A value for each command word along with its
    // corresponding user interface string.
	GO("Type go [direction] to go to your next room"),
	QUIT("If you're done, type quit to quit the game"),
	HELP("If you need help, type help"),
	UNKNOWN("Returns when the command is not a valid command"),
	SETNAME("Will set your player name, type setname [namehere] to set your name"),
	ROOM("Will display room information about your room"),
	LOOK("Will display room information about your room"),
	TALK("Talk to a npc in the room, use talk [npc_here], including the underscore"),
	PICKUP("Pickup an item in the room, use pickup [item_here] including the underscore"),
	PAK("Pickup an item in the room, use pickup [item_here] including the underscore"),
	DROPITEM("Will drop an item from your inventory"),
	LOS("Will drop an item from your inventory"),
	MYITEMS("Will display all items in your inventory"),
	USEITEM("Will let you use the item you've typed in"),
	ATTACKNPC("Will let you attack the NPC"),
	WEARITEM("Will let you wear an item, wearitem [itemName_amount]"),
	UNEQUIP("Will let you unequip an item unequip [itemName_amount]"),
	BACK("Will set you one room back"),
	EAT("Will let you regenerate your health"),
	PREV("Will let you do your previous command"),
	EET("Will let you regenerate your health"),
	TIMELEFT("Will display the minutes that are left to complete the date"),
	EMPTY("Let you empty your text field area"),
	PLAYER("Will display information about you"),
	T("Will let you talk to your other team mates"),
	SEND("Will send data to the client"),
	SERVERPICKUP("[CHEAT] Will pickup the item you specified"),
	SERVERROOM("[CHEAT] Will let you cheat to room you specified");
    
	public static CommandWord getCommand(String command) {
		for (CommandWord comm : values()) {
			if (comm.name().equalsIgnoreCase(command)) {
				return comm;
			}
		}
		return null;
	}
	
	private String instruction;
	
	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
    
    /**
     * Initialise with the corresponding command string.
     * @param commandString The command string.
     */
    CommandWord(String instruction)
    {
    	setInstruction(instruction);
    }
    
}
