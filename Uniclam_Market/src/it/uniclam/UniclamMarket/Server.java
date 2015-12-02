package it.uniclam.UniclamMarket;

import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.DAO.SpesaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JOptionPane;
 
 
/**
 * Classe Server - Socket
 * 
 * @author trovgiov
 *
 */
public class Server {

	public static String INSERT_UTENTE = "req_insert_utente";
	public static String LOGIN_UTENTE="req_login";
	public static String PERSONAL_PAGE="req_Persona_Page";
	public static String CREA_SPESA="req_Creazione_Spesa";
	public static String MASSIMALE="req_massimale";

	public static String SPESA_CREATA="response_spesa_creata";

	public static String HOST = "localhost";
	public static int port = 8888;

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		String operation = null;
 

		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(8888);
		System.out.println("Server in ascolto sulla porta 8888");
		
		while(true){
		Socket s = ss.accept();

		PrintWriter out = new PrintWriter(s.getOutputStream(), true);

		InputStreamReader isr = new InputStreamReader(s.getInputStream());

		BufferedReader in = new BufferedReader(isr);

		boolean closeConnection = false;
		while(!closeConnection){

  
			String r = in.readLine();
			String[] parts = r.split("/");
	
			operation = parts[0]; // Operazione
	
			if (operation.contentEquals(INSERT_UTENTE)) {
	
				String nome = parts[1];
				String cognome = parts[2];
	
				String email = parts[3];
	
				String telefono = parts[4];
				String massimal = parts[5];
	
				double massimale = Double.parseDouble(massimal);
	
				Utente u = new Utente(nome, cognome, email, telefono,
						massimale);
				UtenteDAOImpl.getInstance().insertUtente(u);
				
				//Creo Scheda e attivo
	 double punti_totali=0;
	    
	 
				
	 			Scheda card = new Scheda (punti_totali,massimale);
	
			 
	 			// Attivo la carta con email del cliente
				SchedaDAOImpl.getInstance().activeCard(card, u.getEmail());	
				
				// Genero Pin associato alla carta
			    int a[]=SchedaDAOImpl.getInstance().generatePin(u.getEmail());
				 
				 
			    
				 String response = "OK"+"/"+a[0]+"/"+a[1] ;
	
					out.println(response);
					
					System.out.println(response);
	
					
	  
			}
	
			
			else if(operation.contentEquals(LOGIN_UTENTE)) {
				
 				String nuscheda=parts[1];
				String pino=parts [2];
				
				int numscheda=Integer.parseInt(nuscheda);
				int pin= Integer.parseInt(pino);
				
				boolean result;
				result=UtenteDAOImpl.getInstance().login(numscheda, pin);
				
				if(result){
				
				
					String response="login_Ok\n\n";
				out.println(response);
				
				
	
				}
				else{
					
					String response="login_errato\n\n";
					out.println(response);
				}
				
				
				
				}
			 
			
			else if(operation.contentEquals(CREA_SPESA)){
				
				//Date dataspesa = null;
	 			
				String idsc=parts[1];
				int idscheda= Integer.parseInt(idsc);
				java.util.Date data_spesa_java = new Date();
				java.sql.Date data_spesa = new java.sql.Date(data_spesa_java.getTime());
 	 			
				Spesa c = new Spesa (idscheda,data_spesa);
				
 				int idspesa=SpesaDAOImpl.getInstance().insertSpesa(c);
				
				
				 String response="spesa_creata"+"/"+idspesa;
				out.println(response);
				
				
				
			} 
			
			
			
			else if (operation.equals("logout")){
				closeConnection = true;
				// Altre chiusure necessarie
			}
		}		
		}
		}
 
 
	}


