/*
* LocalServer.java 
*
* Live Server implementation using Java + JavaScript
* This is a simple and light weigh implementation of the live server plugin available in VS Code
* The program runs two servers on port 9000 and 9001, one acts as a simple http server while the other
* serves the purpose of a live server
* The liveserver.js is important as it makes sure that page is refreshed when the file is updated
*
* Author: satanic-devil
* 
*/

import java.io.*;
import java.net.*;
import java.util.*;

public class LocalServer extends BaseServer{
	private final int LISTENING_PORT = 9000;
	private final String LIVE_SERVER_SCRIPT = "<script src=\"liveserver.js\"></script>";
	private LiveServer liveServer = null;

	//Pass an extra argument to see the header details
	public static void main(String args[]){
		new LocalServer( (args.length>0)?true:false );
	}

	
	LocalServer(boolean displayHeaders){
		this.displayHeaders = displayHeaders;
		lastModified = new HashMap<String, Long>();
		serverName = "[ Local Server ]";
		liveServer = new LiveServer(lastModified);
		start(LISTENING_PORT);
	}
	
	protected void exitProcess(){
		//Stop the live server
		liveServer.stopServer();
	}

	protected void setResponseBody() throws Exception {
		FileInputStream fileInputStream = new FileInputStream( file );
		StringBuffer tempResponseBody = new StringBuffer();
		while( ( noOfBytes = fileInputStream.read(buffer)) > 0){
			tempResponseBody.append( new String(buffer, 0, noOfBytes));
		}

		if( fileName.indexOf(".html") != -1 ){
			int pos = tempResponseBody.indexOf("</body>");
			responseBody = 	tempResponseBody.toString().substring(0, pos) +
					LIVE_SERVER_SCRIPT +
					tempResponseBody.toString().substring(pos);
			
		} else 
			responseBody = tempResponseBody.toString();	
		fileInputStream.close();

		//Record the last modified date of the file being served
		lastModified.put(fileName, file.lastModified());
	}
	
}

