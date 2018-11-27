
/*
 * This file is public domain.
 *
 * SWIRLDS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF 
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED 
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. SWIRLDS SHALL NOT BE LIABLE FOR 
 * ANY DAMAGES SUFFERED AS A RESULT OF USING, MODIFYING OR 
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

import java.nio.charset.StandardCharsets;

import com.swirlds.platform.Browser;
import com.swirlds.platform.Console;
import com.swirlds.platform.Platform;
import com.swirlds.platform.SwirldMain;
import com.swirlds.platform.SwirldState;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.math.BigInteger;
import java.security.MessageDigest;
//import java.util.HashMap;
//import java.security.NoSuchAlgorithmException;
//import java.util.Map;


/**
 * This HelloSwirld creates a single transaction, consisting of the string "Hello Swirld", and then goes
 * into a busy loop (checking once a second) to see when the state gets the transaction. When it does, it
 * prints it, too.
 */
public class HelloSwirldDemoMain implements SwirldMain {
	/** the platform running this app */
	public Platform platform;
	/** ID number for this member */
	public long selfId;
	/** a console window for text output */
	public Console console;
	/** sleep this many milliseconds after each sync */
	public final int sleepPeriod = 100;
	
	public static String s="";
	
	public static String str="";
	
	public static String arr;
	
//	public static HashMap<String, String> mp= new HashMap<String, String>();
//	
//	public static HashMap<String, String> revmp= new HashMap<String, String>();
	
	public static String hashtext="";

	/**
	 * This is just for debugging: it allows the app to run in Eclipse. If the config.txt exists and lists a
	 * particular SwirldMain class as the one to run, then it can run in Eclipse (with the green triangle
	 * icon).
	 * 
	 * @param args
	 *            these are not used
	 */
	
		
	public static void main(String[] args) {		
		Browser.main(args);
	}

	// ///////////////////////////////////////////////////////////////////

	@Override
	public void preEvent() {
	}

	@Override
	public void init(Platform platform, long id) {
		this.platform = platform;
		this.selfId = id;
		this.console = platform.createConsole(true); // create the window, make it visible
		platform.setAbout("Hello Swirld v. 1.0\n"); // set the browser's "about" box
		platform.setSleepAfterSync(sleepPeriod);
	}


	
	@Override
	public void run() {
		
//		HashMap<String, String> mp= new HashMap<String, String>();
		
		String myName = platform.getState().getAddressBookCopy()
				.getAddress(selfId).getSelfName();

		console.out.println("This is a modified program. Hello Swirld from " + myName);

		// create a transaction. For this example app,
		// we will define each transactions to simply
		// be a string in UTF-8 encoding.
		
		JFrame f = new JFrame(myName);
	    f.setSize(300, 300);
	    f.setLocation(300,200);
	    final JTextArea textArea = new JTextArea(10, 40);
	    f.getContentPane().add(BorderLayout.CENTER, textArea);
	    final JButton button = new JButton("Submit");
	    f.getContentPane().add(BorderLayout.SOUTH, button);
	    button.addActionListener(new ActionListener() {
 
	        public void actionPerformed(ActionEvent e) {
	            s=textArea.getText();	            
	            textArea.setText("");
	                 
	            
	            //create a hash of the string
	            try { 
	                MessageDigest md = MessageDigest.getInstance("SHA-256"); 

	                byte[] messageDigest = md.digest(s.getBytes()); 

	                BigInteger no = new BigInteger(1, messageDigest); 
 
	                hashtext = no.toString(16); 	      
	                while (hashtext.length() < 32) { 
	                    hashtext = "0" + hashtext; 
	                } 
	                textArea.setText("HASH- "+hashtext);
	                
	            } 
	      
	            // For specifying wrong message digest algorithms 
	            catch (Exception exce) { 
	                System.out.println("Exception thrown"
	                                   + " for incorrect algorithm: " + e); 
	            } 
            
	            //checking if input string already exists
	            String sarr= arr.substring(1, arr.length() - 1);
	            String[] values = sarr.split(", ");
	            int flag=0;
	            for(int j=0; j<values.length; j++) {
	            	String[] val = values[j].split(": "); 
	            	if(val[0].equals(s)) {
	            		flag=1;
	            		break;
	            	}
	            	else {
	            		flag=0;
	            	}
	            }
	            if(flag==1)
	            	textArea.setText("Your idea already exists");
	            else {
	            	s=s +": " + hashtext;
	            //if doesn't exist, add the string into the network
	            byte[] transaction = s.getBytes(StandardCharsets.UTF_8);
	            platform.createTransaction(transaction);
	            }
	        }
	    });
	    
	    final JButton button1 = new JButton("Search");
	    f.getContentPane().add(BorderLayout.EAST, button1);
	    button1.addActionListener(new ActionListener() {
   
	        public void actionPerformed(ActionEvent e) {
	            str=textArea.getText();
	           
	        if(str.length() == 64) {
	        	String starr = arr.substring(1, arr.length() - 1);
	        	String[] v = starr.split(", ");
	        	int f=0;
	        	 String t = ""; 
	        	for(int k=0; k<v.length; k++) {
	        		String[] va = v[k].split(": ");
	        		if(va[1].equals(str)) {	        			
	        			f=1;
	        			t=va[0];
	        			break;
	        		}
	        		else {
	        			f=0;
	        		}
	        	}
	        	if(f==1) {
	        		textArea.setText("IDEA- " + t);
	        	}
	        	else {
	        		textArea.setText("Sorry, we could not find your idea");
	        	}
	        }
	        else {  
	            
	            String sarr= arr.substring(1, arr.length() - 1);
	            String[] values = sarr.split(", ");
	            int flag=0;
	            String temp= "";
	            for(int j=0; j<values.length; j++) {
	            	String[] val = values[j].split(": ");
	            	if(val[0].equals(str)) {
	            		flag=1;
	            		temp=val[1];
	            		break;
	            	}
	            	else {
	            		flag=0;
	            	}
	            }
	            if(flag==1) {
	            	textArea.setText("Hash- " + temp);
	            	
	            }
	            else
	            	textArea.setText("Sorry, we could not find your idea");
	        }
	        }
	    });
	    
	      
	    
	    f.setVisible(true);

		// Send the transaction to the Platform, which will then
		// forward it to the State object.
		// The Platform will also send the transaction to
		// all the other members of the community during syncs with them.
		// The community as a whole will decide the order of the transactions

		String lastReceived = "";
		
		while (true) {
			HelloSwirldDemoState state = (HelloSwirldDemoState) platform
					.getState();
			String received = state.getReceived();

			if (!lastReceived.equals(received)) {
				lastReceived = received;
				arr = received;
				String rec = received.substring(1, received.length() - 1);
				String[] sa = rec.split(", ");
				console.out.print("Recieved: [");
				for(int i=0; i<sa.length; i++) {
					String[] sar = sa[i].split(": ");
					console.out.print(sar[0] + ", "); // print all received transactions			
				}
				console.out.println("]");
			}
			try {
				Thread.sleep(sleepPeriod);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public SwirldState newState() {
		return new HelloSwirldDemoState();
	}
}