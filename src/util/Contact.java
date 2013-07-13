package util;

import java.net.*;
import java.io.*;

public class Contact {
	
	public static String trackerRequest;
	public static byte[] trackerByteArray;
	
	public static void sendGetRequest(){
	     PrintWriter out = null;
	     InputStream input = null;
	     ByteArrayOutputStream byteResponse = new ByteArrayOutputStream();

		try{
        	
			byte[] recievedBytes = new byte[4096];
        	URL trackerURL = new URL(trackerRequest);
        	input = trackerURL.openStream();
        	int i;
        	
        	while((i = input.read(recievedBytes)) > 0){
        		byteResponse.write(recievedBytes, 0, i);
        	}
        	
        	input.close();
        	
        	trackerByteArray = byteResponse.toByteArray();
        	
		}catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + trackerRequest);
            System.exit(1);
        }catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: " + trackerRequest);
            System.exit(1);
        }
        
        
        	
    }

	
	public static void formGetRequest(int i){
		
		String event = determineEvent(i);
		trackerRequest = Data.announce_url + "?port=" + Data.portNumber + "&info_hash=" + Data.info_hash_string + "&uploaded="
							+ Data.uploaded + "&downloaded=" + Data.downloaded + "&left=" + Data.dataLeft;
		
		if(event.length() > 0){
			trackerRequest += "&event=" + event;
		}
		
		System.out.println(trackerRequest);
	}
	
	public static String determineEvent(int i){
		switch(i){
			case 1: return "STARTED";
			case 2: return "COMPLETED";
			case 3: return "STOPPED";
			default: return "";
		}
	}
	
}
