/*
* BaseServer.java
* BaseServer abstract class which acts as the base class for LocalServer and LiveServer class
*
* Author: satanic-devil
*/

import java.io.*;
import java.net.*;
import java.util.*;

abstract class BaseServer{
	protected String serverName = "";
	protected String fileName = null;
	private StringBuffer requestHeader, responseHeader;
	protected String responseBody;
	protected File file;
	protected boolean displayHeaders = false;

	private final int BUFFER_SIZE = 8192;
	protected byte buffer[] = new byte[ BUFFER_SIZE ];
	protected int noOfBytes;
	private boolean isRequestInvalid = false;
	private int port;

	private Socket socket = null;
	private ServerSocket serverSocket = null;
	private InputStream socketInputStream = null;
	private OutputStream socketOutputStream = null;	
	
	protected HashMap<String, Long> lastModified;


	//Abstract methods
	abstract protected void setResponseBody() throws Exception;
	abstract protected void exitProcess();

	//Implemented methods
	protected void start(int port){
		this.port = port;
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			display( "SERVER STARTED "+serverName+"\non http://localhost:" + port + "\n\n");
		while(true){
			try{			
				socket = serverSocket.accept();
			
				display("Connection Request " + serverName + " : " + socket.getRemoteSocketAddress());

				getRequestHeader();
			
				if( displayHeaders) display( requestHeader.toString());

				if( !isRequestInvalid )
					sendResponse();
				isRequestInvalid = false;
				closeConnection();
			}catch(Exception e){
				display("Error : " + e.getMessage());
			}	
			
		}
		}catch(Exception e){
			System.out.println("Server Socket Error : "  + e.getMessage());
			e.printStackTrace();
			exitProcess();
		}
	}

	protected void display(String msg){
		System.out.println(msg);
	}

	protected void getRequestHeader() throws Exception{
		requestHeader = new StringBuffer();
		socketInputStream = socket.getInputStream();
		while( (noOfBytes = socketInputStream.read(buffer)) > 0 ){
			requestHeader.append(new String(buffer, 0, noOfBytes));
			if( requestHeader.indexOf("\r\n\r\n") != -1 ) break;
		}
		
		if( requestHeader.toString().length() != 0 ) getRequestedFileName();
		else {
			display("Invalid Request Header");
			isRequestInvalid = true;
		}
	
	}

	protected void getRequestedFileName(){
		fileName = requestHeader.toString().split("\\s")[1];
		if( fileName.length() == 1 ) 
			fileName = "index.html";
		else
			fileName = fileName.substring(1);
	}
	

	protected void sendErrorResponse() throws Exception{
		socketOutputStream.write("HTTP/1.1 404 OK\nConnection:close\r\n\r\n".getBytes());
	}

	private void sendResponse() throws Exception{
		socketOutputStream = socket.getOutputStream();
		file = new File(fileName);
		if( file.exists() )
			sendResponseBody();
		else
			sendErrorResponse();
	}

	private void sendResponseBody() throws Exception{
		
		setResponseBody();
		sendResponseHeader( responseBody.length() );
		socketOutputStream.write( responseBody.getBytes() );
	}

	//Make sure the end of the header is terminated by two \r\n\r\n and nothing else
	private void sendResponseHeader(long size) throws Exception{
		String contentType = "text/css";
		if( fileName.indexOf(".js") != -1 ) contentType = "application/javascript";
		if( fileName.indexOf(".html") != -1 ) contentType = "text/html";
		String header = "HTTP/1.1 200 OK\nContent-Type: "+ contentType +"\nAccess-Control-Allow-Origin: *\nContent-Length: "+ size +"\r\n\r\n";
		socketOutputStream.write(header.getBytes());
	}

	protected void closeConnection(){
		try{
			socketOutputStream.flush();
			socketOutputStream.close();
			socketInputStream.close();
			socket.close();
		}catch( Exception e ){
			display("Error while closing the connection: " + e.getMessage() );
		}
	}
}