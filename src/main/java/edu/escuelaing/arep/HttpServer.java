package edu.escuelaing.arep;

import org.apache.commons.io.FilenameUtils;

import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
/**
 * Servidor HTTP que soporta solicitudes y devuelve archivos
 * 
 * @author Diego Puerto
 */
public class HttpServer {
	
	static ServerSocket serverSocket = null;
	static Socket clientSocket = null;
	
	static PrintWriter out;
	static BufferedReader in;
	
	
  public static void main(String[] args) throws IOException {
	  
	   serverSocket = null;
	   try { 
	      serverSocket = new ServerSocket(getPort());
	   } catch (IOException e) {
	      System.err.println("No es posible escuchar el puerto: 36000.");
	      System.exit(1);
	   }
	   while(true) {
		   
		   try {
		       System.out.println("Listo para recibir ...");
		       clientSocket = serverSocket.accept();
		   } catch (IOException e) {
		       System.err.println("Accept failed.");
		       System.exit(1);
		   }
		   
		   out = new PrintWriter(clientSocket.getOutputStream(), true);
		   in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		   String inputLine, outputLine;
		   
		   StringBuilder stringBuilder = new StringBuilder();
		   
		   Pattern pattern = Pattern.compile("GET /([^\\s]+)");
	       Matcher matcher = null;
		   
		   while ((inputLine = in.readLine()) != null) {
		      System.out.println("Recibi: " + inputLine);
		      stringBuilder.append(inputLine);
		      if (!in.ready()) {
		    	  matcher = pattern.matcher(stringBuilder.toString());
	              if (matcher.find()) {
	                  String req = matcher.group().substring(5);
	                  System.out.println("VALUE: " + req);
	                  returnRequest(req);
	              }
		    	  break; 
		      }
		   }
		  
		    out.close(); 
		    in.close(); 
		    clientSocket.close(); 
		    //serverSocket.close();
	   }
  }
  
  /**
     * Retorna el recurso solicitado en el path 
     *
     * @param req archivo solicitado
     * @throws IOException
     */
  public static void returnRequest(String req) throws IOException {
	  
	  String path = "src/main/resources/";
      String ext = FilenameUtils.getExtension(req);
      System.out.println("Extencion: "+ext);
      
      if (ext.equals("jpg") || ext.equals("png")) {
    	  path+="img/";
      }
      
      System.out.println("Ruta del recurso: "+path+req);
      File file = new File(path+req);
      
      if (file.exists() && !file.isDirectory()) {
	      if (ext.equals("jpg") || ext.equals("png")) {
	    	  	
				FileInputStream fis = new FileInputStream(file);
				byte[] data = new byte[(int) file.length()];
				fis.read(data);
				fis.close();
	                      
	            // Cabeceras con la info de imagen (ya sea png o jpg)
				DataOutputStream binaryOut = new DataOutputStream(clientSocket.getOutputStream());
				binaryOut.writeBytes("HTTP/1.0 200 OK\r\n");
				binaryOut.writeBytes("Content-Type: image/"+ext+"\r\n");
				binaryOut.writeBytes("Content-Length: " + data.length);
				binaryOut.writeBytes("\r\n\r\n");
				binaryOut.write(data);
	
				binaryOut.close();
	    	  
	      } else {
	    	  out.println("HTTP/1.1 200 \r\nContent-Type: text/html\r\n\r\n");
	    	  BufferedReader br = new BufferedReader(new FileReader(file));
	    	  StringBuilder stringBuilder = new StringBuilder();
	          String st;
	          
	          while ((st = br.readLine()) != null) {
	              stringBuilder.append(st);
	          }
	          
	          out.println(stringBuilder.toString());
	          br.close();
	      }
      } else {
    	  out.println("HTTP/1.1 404 \r\n\r\n<html><body><h1>ERROR 404: NOT FOUND</h1></body></html>");
      }
  }
  
  static int getPort() {
      if (System.getenv("PORT") != null) {
          return Integer.parseInt(System.getenv("PORT"));
      }        
      return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
  }
}