package it.uniclam.UniclamMarket;

 
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.entity.Utente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

 
/**
 * Classe Server - Socket
 * @author trovgiov
 *
 */
public class Server {

	public static String INSERT_AMICI = "req_insert_amici";
	public static String HOST = "localhost";
	public static int port = 8888;
 
 	

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		String operation=null;
	 
	    ServerSocket ss= new ServerSocket(8888);
		
	    System.out.println("Server in ascolto sulla porta 8888");
		Socket s = ss.accept();
		
 
		
		PrintWriter out = new PrintWriter(s.getOutputStream(),true);

	    InputStreamReader isr = new InputStreamReader(s.getInputStream());	
	    
	    BufferedReader in = new BufferedReader(isr);
	    
	    System.out.println(" Collegamento Effettuato");

	    System.out.println("(Server) Eseguo String r=in.readLine() : ");

		 int idUtente = 0;

	    String r=in.readLine();
	    String[] parts = r.split("/");
		operation = parts[0]; // Operazione
	    
		if(operation.contentEquals(INSERT_AMICI)){
		 
			
			String nome = parts[1];
			String cognome = parts[2];
			
			 
			String email = parts[3];

			String telefono = parts[4];
	 		String massimal = parts[5];
 
	 
		
		double massimale=Double.parseDouble(massimal);
		
		
 Utente u=new Utente(idUtente,nome,cognome,email,telefono,massimale);	
 UtenteDAOImpl.getInstance().insertUtente(u);
 
String response="OK";
out.println(response);
 
 		}
	    
	   // String[] parts = r.split("/");
	    
	    
	    
	    System.out.println("\n(Server) Dopo String r=in.readLine() : "+r);

   	  
	   
	   
	}

}
