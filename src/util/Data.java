package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import exceptions.BencodingException;

public class Data {
	
	public static TorrentInfo torrentInfo = null;
	public static byte[] info_hash = new byte[20];
	public static String info_hash_string = "";
	public static int portNumber = 0;
	public static String announce_url;
	public static int downloaded = 0;
	public static int uploaded = 0; 
	public static int dataLeft = 0;
	public static ArrayList peerList = null;
	
	
	public Data(){
	
	}
	
	public Data(String torrentFileName, String saveFileName) throws BencodingException, IOException{
		
		createInfoFile(torrentFileName, saveFileName);
		createInfoHash();
		info_hash_string = convertToHex(info_hash);
		announce_url = torrentInfo.announce_url.toString();
		portNumber = torrentInfo.announce_url.getPort();
		Contact.formGetRequest(0);
		Contact.sendGetRequest();
		PeerManagement.createPeerList(Contact.trackerByteArray);
		peerList = PeerManagement.peerList;

	}
	
	@SuppressWarnings("resource")
	public static TorrentInfo createInfoFile(String torrentFileName, String saveFileName) throws BencodingException, IOException{
		
		File file = new File(torrentFileName);
		    long fileSize = -1;
		    byte[] fileBytes = null;
		    InputStream file_stream = null;
		
		    fileSize = file.length();
		    fileBytes = new byte[(int)fileSize];
		        
		    try {        
		      file_stream = new FileInputStream(file);
		      
		      if(!file.exists()){
		        System.err.println("Error:The File" + torrentFileName + " does not exist. Please reload the program with the correct filename");
		      }
		      file_stream.read(fileBytes);
		      
		      for(int i = 0; i<fileBytes.length; i++){
		        System.out.print((char)fileBytes[i]);
		      }
		      System.out.println();
		      
		    } catch (FileNotFoundException e) {
		      System.out.println("File Not Found.");
		      e.printStackTrace();
		    } catch (IOException e1){
		      System.out.println("Error, cannot read the file.");
		      e1.printStackTrace();
		    }
		    file_stream.close();
		    torrentInfo = new TorrentInfo(fileBytes);
		    return torrentInfo;
		  }
	
	public static void createInfoHash(){
		torrentInfo.info_hash.get(info_hash, 0, info_hash.length);
		torrentInfo.info_hash.rewind();
	}
	
	public static String convertToHex(byte[] bytes){
		
		char[] hexValues = { '0', '1', '2', '3', '4', '5', '6','7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuffer convertedValue = new StringBuffer();
		
		int i = 0;
		while(i<bytes.length){
			convertedValue.append('%').append(hexValues[(bytes[i] >> 4 & 0x0F)]).append(hexValues[(bytes[i] & 0x0F)]);
			i++;
		}
		System.out.println(convertedValue.toString());
		return convertedValue.toString();
	}
	
	public static void createPeerList(byte[] trackerResponse){
		
		ByteBuffer interval = ByteBuffer.wrap(new byte[] { 'i', 'n', 't', 'e', 'r','v','a','l' });
		 
		try {
			 for(int i = 0; i<trackerResponse.length; i++){
			        System.out.print((char)trackerResponse[i]);
			      }
			      System.out.println();
			Object decodedResponse = Bencoder2.decode(trackerResponse);
			
		} catch (BencodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

