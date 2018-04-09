

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Utilities;

import jaco.mp3.player.MP3Player;

public class GameFrame {
	
	GameFrame(Client client) {
		setClient(client);
	}
	
	/**
	 * Alle data voor de gameframe
	 */
	private Client client;
	private JFrame mainFrame = new JFrame(Global.GAME_NAME);
	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollpane = new JScrollPane(getTextArea());
	private JTextArea textArea2 = new JTextArea();
	private JScrollPane scrollpane2 = new JScrollPane(getTextArea2());
	private JTextArea textArea3 = new JTextArea();
	private JScrollPane scrollpane3 = new JScrollPane(getTextArea3());
	private JTextArea textArea4 = new JTextArea();
	private JScrollPane scrollpane4 = new JScrollPane(getTextArea4());
	
	/**
	 * Een plaatje laadden
	 * @param f
	 * @param path1
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void loadImage(JFrame f, String path1, int x, int y, int width, int height) {
		try {
        BufferedImage image;
		image = ImageIO.read(getClass().getResource(path1+".png"));
        JLabel label = new JLabel(new ImageIcon(image));
        label.setBounds(x, y, width, height);
        f.getContentPane().add(label);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Een alert versturen naar de client
	 * @param text
	 */
	public void alert(String text) {
		JOptionPane.showMessageDialog(getMainFrame(), text);
	}
	
	/**
	 * Send text naar een textarea
	 * @param text
	 */
	public void appendTextToArea(String text) {
		getTextArea().append("\n"+text);
	}	
	
	/**
	 * Send text naar het commands textarea
	 * @param text
	 */
	public void appendTextToAreaCommands(String text) {
		getTextArea4().append("\n"+text);
	}
	
	
	/**
	 * Send text naar het help text area
	 * @param text
	 */
	public void appendTextToAreaHelp(String text) {
		getTextArea3().append("\n"+text);
	}
	
	/**
	 * Een key listener, dus als je op een knopje klikt, dan moeten er bepaalde
	 * dingen worden gerunned
	 */
	public void textAreaListener() {
		getTextArea2().addKeyListener(new KeyListener() {
	
		    @Override
		    public void keyTyped(KeyEvent arg0) {
	
		    }
	
		    @Override
		    public void keyReleased(KeyEvent arg0) {
		        // TODO Auto-generated method stub
	
		    }
	
		    @Override
		    public void keyPressed(KeyEvent arg0) {
		    	//alert("POS: "+MouseInfo.getPointerInfo().getLocation());
		    	
		    	if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			    	try {
			    		 
			    	int end = getTextArea2().getDocument().getLength();
			    	int start = Utilities.getRowStart(getTextArea2(), end);
	
			    	while (start == end)
			    	{
			    	    end--;
			    	    start = Utilities.getRowStart(getTextArea2(), end);
			    	}
	
			    	String text = getTextArea2().getText(start, end - start);

			    	getClient().sendStringText(text);
			    
			    	
			    	} catch (BadLocationException exp) {
			    		exp.printStackTrace();
			    	}
		    	}
		    }
		});
	}
	
	/**
	 * De playername van de speler met input
	 * @return
	 */
	private String getPlayerName() {
        return JOptionPane.showInputDialog(
            getMainFrame(),
            "Please type the name of your warrior",
            "Welcome to the "+Global.GAME_NAME,
            JOptionPane.QUESTION_MESSAGE);
    }
	
	/**
	 * Alle commands die worden gedisplayed
	 */
	public void showCommands() {
		getTextArea3().append("== Available commands ==\n");
		for (CommandWord word : CommandWord.values()) {
			getTextArea3().append("["+word.name().toLowerCase()+"] - "+ word.getInstruction()+"\n");
		}
	}
	
	/**
	 * De MP3Player
	 */
	private MP3Player repeat = new MP3Player();
	
	/**
	 * Muziek afspelen met een repeat
	 * @param file
	 */
	public void playMusicRepeat(String file) {
		repeat.addToPlayList(new File(file+".mp3"));
		repeat.setRepeat(true);
		repeat.play();
	}
	
	/**
	 * Muziek afspelen zonder repeat
	 * @param file
	 */
	public void playMusicNew(String file) {
		MP3Player noRepeat = new MP3Player();
		noRepeat.addToPlayList(new File(file+".mp3"));
		noRepeat.play();
	}
	
	/**
	 * De GUI intialiseren
	 */
	public void initializeFrame() {
		Thread th1 = new Thread() {
			public void run() {
				//new MP3Player(new File("background.mp3")).play();
				loadImage(getMainFrame(), "map", 510, 190, 494, 213);
				showCommands();
				textAreaListener();
				getTextArea().setBackground(Color.black);
				getTextArea().setBounds(5, 5, 500, 400);
				getTextArea().setForeground(Color.white);
				getTextArea().setToolTipText("This is data you receive from the server");
				getTextArea().setEditable(false);
				DefaultCaret caret = (DefaultCaret)getTextArea().getCaret();
				caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

				getTextArea2().setText("Type commands here");
				getTextArea2().setBackground(Color.black);
				getTextArea2().setForeground(Color.white);
				getTextArea2().setBounds(510, 5, 495, 400);
				getTextArea2().setFont(textArea.getFont().deriveFont(30f)); // will only change size to 12pt
				getTextArea2().setToolTipText("Please type any commands here to start");
				DefaultCaret caret2 = (DefaultCaret)getTextArea2().getCaret();
				caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				
				getTextArea3().setBackground(Color.black);
				getTextArea3().setBounds(5, 5, 500, 400);
				getTextArea3().setForeground(Color.white);
				getTextArea3().setToolTipText("This is your information to work on");
				getTextArea3().setEditable(false);
				DefaultCaret caret3 = (DefaultCaret)getTextArea3().getCaret();
				caret3.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				
				getTextArea4().setBackground(Color.black);
				getTextArea4().setBounds(5, 410, 1500, 140);
				getTextArea4().setForeground(Color.white);
				getTextArea4().setToolTipText("These are all the command people are typing");
				getTextArea4().setEditable(false);
				DefaultCaret caret4 = (DefaultCaret)getTextArea4().getCaret();
				caret4.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
				
				getScrollpane().setBounds(new Rectangle(5, 5, 500, 400));
				getScrollpane().setBackground(Color.white);
				getScrollpane2().setBounds(new Rectangle(510, 5, 495, 180));
				getScrollpane2().setBackground(Color.white);
				getScrollpane3().setBounds(new Rectangle(1010, 5, 495, 400));
				getScrollpane3().setBackground(Color.white);
				getScrollpane4().setBounds(new Rectangle(5, 410, 1500, 140));
				getScrollpane4().setBackground(Color.white);
				
				getMainFrame().add(getScrollpane());
				getMainFrame().add(getScrollpane2());
				getMainFrame().add(getScrollpane3());
				getMainFrame().add(getScrollpane4());
				
				getMainFrame().setSize(Global.FRAME_SIZE_X, Global.FRAME_SIZE_Y);
				getMainFrame().setLayout(null);
				getMainFrame().setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				getMainFrame().setVisible(true);
				getMainFrame().setResizable(false);
				
				appendTextToAreaCommands("Here will be all the commands people have typed");
				//getTextArea2().append("Type in here, `help` - commands");
				getTextArea().append("Please use the command `help` to view the command. `look` will get you started out\n"
						+ "\nYou've been captured by a targaryen in Game of Thrones.\nYou seek revenge, you must escape within 30 minutes otherwise you will fail.");
				//setplayer
				
				if (getClient().getPlayerId() == -1) {
					requestUsername();
				}
				String playerName = getPlayerName();
				
				if (playerName == null || playerName.isEmpty()) {
					playerName = Integer.toString((int)(Math.random() * 100000));
				}
				getClient().setPlayerName(playerName);
				getClient().sendStringText("::USERNAMEREQUESTSEND "+getClient().getPlayerId());
				getClient().sendStringText("::SETNAME "+getClient().getPlayerId()+" "+playerName);
				
				alert("Welcome there adventurer!\nYou've entered the world of warriors\n\nUnfurtunately you've been captured by a dark demon called Cesar\n\nYou must escape\nType 'look' and 'help' to start the game");
				
				
			}
		};
		th1.start();
		
	}
	
	/**
	 * De usernaam genereren en setten
	 */
	public void requestUsername() {
		int random = (int) (Math.random() * 1000000);
		getClient().setPlayerId(random);
	}
	
	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public JScrollPane getScrollpane() {
		return scrollpane;
	}

	public void setScrollpane(JScrollPane scrollpane) {
		this.scrollpane = scrollpane;
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}

	public JTextArea getTextArea2() {
		return textArea2;
	}

	public void setTextArea2(JTextArea textArea2) {
		this.textArea2 = textArea2;
	}

	public JScrollPane getScrollpane2() {
		return scrollpane2;
	}

	public void setScrollpane2(JScrollPane scrollpane2) {
		this.scrollpane2 = scrollpane2;
	}

	public JScrollPane getScrollpane3() {
		return scrollpane3;
	}

	public void setScrollpane3(JScrollPane scrollpane3) {
		this.scrollpane3 = scrollpane3;
	}

	public JTextArea getTextArea3() {
		return textArea3;
	}

	public void setTextArea3(JTextArea textArea3) {
		this.textArea3 = textArea3;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public JTextArea getTextArea4() {
		return textArea4;
	}

	public void setTextArea4(JTextArea textArea4) {
		this.textArea4 = textArea4;
	}

	public JScrollPane getScrollpane4() {
		return scrollpane4;
	}

	public void setScrollpane4(JScrollPane scrollpane4) {
		this.scrollpane4 = scrollpane4;
	}


}
