
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class Client {

	/**
	 * De playedId bij het opstarten van de client
	 */
	private int playerId = -1;
	
	/**
	 * De playernaam bij het opstarten van de client
	 */
	private String playerName;
	
	/**
	 * De GUI van de client
	 */
	private GameFrame frame;
	
	/**
	 * De data dat binnen komt in de client
	 */
	private BufferedReader in;
	
	/**
	 * De data dat de client verstuurt
	 */
	private PrintWriter out;
	
	/**
	 * De packets (stukjes data)
	 */
	private Packets packets;

	/**
	 * Laadt de truststore voor de ssl connectie
	 * @param trustStream
	 * @throws Exception
	 */
	private static void setSSLFactories(InputStream trustStream) throws Exception {    

	  // Now get trustStore
	  KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());    

	  // if your store is password protected then declare it (it can be null however)
	  //char[] trustPassword = password.toCharArray();

	  // load the stream to your store
	  trustStore.load(trustStream, null);

	  // initialize a trust manager factory with the trusted store
	  TrustManagerFactory trustFactory = 
	  TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());    
	  trustFactory.init(trustStore);

	  // get the trust managers from the factory
	  TrustManager[] trustManagers = trustFactory.getTrustManagers();

	  // initialize an ssl context to use these managers and set as default
	  SSLContext sslContext = SSLContext.getInstance("SSL");
	  sslContext.init(null, trustManagers, null);
	  SSLContext.setDefault(sslContext);    
	}
    /**
     * Connect met de server en dan de main loop
     * @throws Exception 
     */
    private void run() {
    	try {
			InputStream truststoreInput = Thread.currentThread().getContextClassLoader()
			    .getResourceAsStream(Global.TRUSSTORE_FILE_NAME);
			setSSLFactories(truststoreInput);
			
	        String serverAddress = Global.SERVER_IP;
	        SSLSocketFactory socketFactory = 
	                (SSLSocketFactory)SSLSocketFactory.getDefault();
	        Socket socket = socketFactory.createSocket(serverAddress, 9001);
	        setIn(new BufferedReader(new InputStreamReader(
	                socket.getInputStream())));
	        setOut(new PrintWriter(socket.getOutputStream(), true));
	
	        while (true) {
	            String line = in.readLine();
	           
	            System.out.println("Data received from server: "+line);
	            	
	        	PacketEnum packet = PacketEnum.getPacket(line);
	
	        	if (line != null) {
		        	if (packet != null) {
		        		getPackets().handlePackets(packet, line);
		        	} else {
		        		getPackets().handlePackets(PacketEnum.UNKNOWN, line);
		        	}
	        	} else {
	        		getFrame().alert("Please restart the client or download the latest version, a packet went wrong");
	        	}
	        }
    	} catch(Exception e) {
    		getFrame().alert("Could not connect to server, please restart your client");
    	}
    }
    
    /**
     * Verzendt bepaalde data naar de server
     * @param text
     */
    public void sendStringText(String text) {
    	if (text.contains("empty")) {
    		getFrame().getTextArea().setText("");
    		getFrame().getTextArea2().setText("");
    	} else {
			System.out.println("Send data to server: "+text);
	    	getOut().println(text);
    	}
    }

    /**
     * Runt de client
     */
    public static void main(String[] args) throws Exception {
        Client client = new Client();
    	GameFrame frame = new GameFrame(client);
        client.setFrame(frame);
        frame.initializeFrame();
        client.setPackets(new Packets(client, frame));
        client.run();
    }
    
	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public GameFrame getFrame() {
		return frame;
	}

	public void setFrame(GameFrame frame) {
		this.frame = frame;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}
	public Packets getPackets() {
		return packets;
	}
	public void setPackets(Packets packets) {
		this.packets = packets;
	}
}
