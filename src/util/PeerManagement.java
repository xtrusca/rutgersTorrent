package util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.BencodingException;

public class PeerManagement {
	
	public final static ByteBuffer KEY_INTERVAL = ByteBuffer.wrap(new byte[]{ 'i', 'n', 't', 'e', 'r','v','a','l' });
	public final static ByteBuffer KEY_MIN_INTERVAL = ByteBuffer.wrap(new byte[] { 'm','i','n',' ', 'i', 'n', 't','e','r','v','a','l' });
	public final static ByteBuffer KEY_PEERS = ByteBuffer.wrap(new byte[]{ 'p', 'e', 'e', 'r', 's'});
	public static ArrayList peerList = null;
				
	public PeerManagement(){
		
	}
	
	public static void createPeerList(byte[] peerByteArray) {
		
		HashMap decodedResponse = null;
		int interval;
		int minInterval;
		
		
		try {
			decodedResponse = (HashMap)Bencoder2.decode(peerByteArray);
			interval = ((Integer)decodedResponse.get(KEY_INTERVAL)).intValue();
			ArrayList listOfPeers = (ArrayList)decodedResponse.get(KEY_PEERS);
			
			for(int i = 0; i<listOfPeers.size(); i++){
				HashMap peers = (HashMap)listOfPeers.get(i);
			}
			      
		} catch (BencodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
