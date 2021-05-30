/*
* LiveServer.java
* LiveServer class
* This server will check if the requested files are updated and send appropriate response to the fetch call made in js
* Author: satanic-devil
*
*/
import java.io.*;
import java.net.*;
import java.util.*;


public class LiveServer extends BaseServer implements Runnable{
	private final int LISTENING_PORT = 9001;
	Thread thread = null;
	
	LiveServer(HashMap<String, Long> lastModified){
		this.lastModified = lastModified;
		serverName = "[ Live Server ]";
		thread = new Thread(this, "Live Server");
		thread.start();
	}


	public void run(){
		start(LISTENING_PORT);
	}	

	public void stopServer(){
		try{
			display("Stopping Live Server....");
			thread.stop();
		}catch(Exception e){}		
	}

	protected void exitProcess(){}	//No task to be performed here

	protected void setResponseBody() throws Exception{
		if( lastModified.get(fileName) == file.lastModified() )
			responseBody = "{\"isModified\":\"false\"}";
		else 
			responseBody = "{\"isModified\":\"true\"}";
	}
}