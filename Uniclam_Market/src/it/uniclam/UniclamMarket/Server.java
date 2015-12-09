package it.uniclam.UniclamMarket;

import it.uniclam.DAO.SchedaDAO;
import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.DAO.SpesaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.entity.Carrello;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

/**
 * Classe Server - Socket
 * 
 * @author trovgiov
 *
 */
public class Server {
	public static   String UPDATE_MASSIMALE_RESIDUO = "req_massimale_residuo_aggiornato";

	public  static   String POINTS_UPDATED = "RES_points_updated";
	public static  String UPDATE_POINTS = "req_update_points";
	public static  String ADD_POINTS = "RES_add_points";
	public static  String GET_POINTS = "req_get_points";
	public static   String POINTS_SHOWED = "RES_points_showed";
	public static   String SHOW_POINTS = "res_show_points";
	public static   String MASSIMALE_INSERITO = "RES_ massimaleInserito";
	public static   String UPDATE_MASSIMALE = "req_update_max";
	public static  String SPESA_INVIATA ="RES_spesa_mostrata";
	public static  String SHOW_SPESA = "req_show_spesa";
	public static  String CALCOLAIMPORTO = "req_calcolo_importo";
	public static  String ELIMINAUTENTE = "req_elimina_utente";
	public static  String CANCELLA_SPESA = "req_cancella_spesa";
	public static String INSERT_UTENTE = "req_insert_utente";
	public static String LOGIN_UTENTE = "req_login";
	public static String PERSONAL_PAGE = "req_Persona_Page";
	public static String CREA_SPESA = "req_Creazione_Spesa";
	public static String MASSIMALE = "req_massimale";
	public static String INSERT_PRODUCTS = "req_inserimentoProdotti";
	public static String DELETE_PRODUCTS = "req_delete_Prodotti";
	public static String UPDATE_PRODUCTS = "req_update_Prodotti";
	public static String CHANGE_EMAIL = "req_change_email";
	public static String RECOVERY_PIN="req_recovery_pin";

	public static String UTENTE_ELIMINATO = "RES_utenteEliminato";
	public static String EMAIL_CHANGED = "RES_email_changed";
	public static String SPESA_CANCELLATA = "RES_spesa_cancellata";
	public static String PIN_RECOVERED = "RES_pin_recovered";
	public static String SPESA_CREATA = "RES_spesa_creata";
	public static   String MASSIMALE_RESIDUO_UPDATED="RES_massimale_residuo_updated";

	public static String HOST = "localhost";
	public static int port = 8888;

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		String operation = null;

		@SuppressWarnings("resource")
		ServerSocket ss = new ServerSocket(8888);
		System.out.println("Server in ascolto sulla porta 8888");

		while (true) {
			Socket s = ss.accept();

			PrintWriter out = new PrintWriter(s.getOutputStream(), true);

			InputStreamReader isr = new InputStreamReader(s.getInputStream());

			BufferedReader in = new BufferedReader(isr);

			boolean closeConnection = false;
			while (!closeConnection) {

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

					// Creo Scheda e attivo
					double punti_totali = 0;

					Scheda card = new Scheda(punti_totali, massimale);

					// Attivo la carta con email del cliente
					SchedaDAOImpl.getInstance().activeCard(card, u.getEmail());

					// Genero Pin associato alla carta
					int a[] = SchedaDAOImpl.getInstance().generatePin(
							u.getEmail());

					String response = "OK" + "/" + a[0] + "/" + a[1];

					out.println(response);

					System.out.println(response);

				}

				else if (operation.contentEquals(LOGIN_UTENTE)) {

					String nuscheda = parts[1];
					String pino = parts[2];

					int numscheda = Integer.parseInt(nuscheda);
					int pin = Integer.parseInt(pino);

					boolean result;
					result = UtenteDAOImpl.getInstance().login(numscheda, pin);

					if (result) {

						Double mas_res = SchedaDAOImpl.getInstance()
								.checkMassimale(numscheda);
						Utente a = SchedaDAOImpl.getInstance().checkUser(
								numscheda);

						String response = "login_Ok" + "/" + mas_res + "/"
								+ a.getNome() + "/" + a.getCognome() + "/"
								+ a.getEmail();

						out.println(response);

					} else {

						String response = "login_errato\n\n";
						out.println(response);
					}

				}
				else if(operation.contentEquals(RECOVERY_PIN)){


					//String email=parts[1];

					SchedaDAOImpl.getInstance().recovery_pin(parts[1]);

					String response=Server.PIN_RECOVERED;
					out.println(response);






				}

				else if (operation.contentEquals(CREA_SPESA)) {

					// Date dataspesa = null;

					String idsc = parts[1];
					int idscheda = Integer.parseInt(idsc);
					java.util.Date data_spesa_java = new Date();
					java.sql.Date data_spesa = new java.sql.Date(
							data_spesa_java.getTime());

					Spesa c = new Spesa(idscheda, data_spesa);

					int idspesa = SpesaDAOImpl.getInstance().insertSpesa(c);

					String response = "spesa_creata" + "/" + idspesa;
					out.println(response);

				}

				else if (operation.contentEquals(INSERT_PRODUCTS)) {

					String barcode = parts[1];
					int idspesa = Integer.parseInt(parts[2]);
					int quantita = Integer.parseInt(parts[3]);

					if (SpesaDAOImpl.getInstance().addProducts(barcode,
							idspesa, quantita)) {

						double importo = SpesaDAOImpl.getInstance()
								.calcoloImporto(idspesa);

						String response = "prodotto inserito" + "/" + importo;
						out.println(response);

					} else {
						String response = "prodotto non inserito";
						out.println(response);
					}

				}

				else if (operation.contentEquals(DELETE_PRODUCTS)) {

					String barcode = parts[1];
					int idspesa = Integer.parseInt(parts[2]);

					if (SpesaDAOImpl.getInstance().deleteProduct(barcode,
							idspesa)) {

						double importo = SpesaDAOImpl.getInstance()
								.calcoloImporto(idspesa);

						String response = "prodotto eliminato" + "/" + importo;
						out.println(response);

					}

					else {
						String response = "prodotto non eliminato";
						out.println(response);
					}

				}

				else if (operation.contentEquals(UPDATE_PRODUCTS)) {

					String barcode = parts[1];
					int quantita = Integer.parseInt(parts[2]);
					int idspesa = Integer.parseInt(parts[3]);

					if (SpesaDAOImpl.getInstance().updateProduct(barcode,
							quantita, idspesa)) {

						double importo = SpesaDAOImpl.getInstance()
								.calcoloImporto(idspesa);
						String response = "prodotto aggiornato" + "/" + importo;
						out.println(response);

					} else {
						String response = "prodotto non aggiornato";
						out.println(response);

					}

				}

				else if (operation.contentEquals(ELIMINAUTENTE)) {

					UtenteDAOImpl.getInstance().deleteUtente(parts[1]);
					String response = Server.UTENTE_ELIMINATO;
					out.println(response);

				}

				else if (operation.contentEquals(Server.CHANGE_EMAIL)) {

					// email parts 1 // new email parts[2]

					UtenteDAOImpl.getInstance()
					.updateUtente(parts[1], parts[2]);

					String response = Server.EMAIL_CHANGED;
					out.println(response);

				}

				else if (operation.contentEquals(CANCELLA_SPESA)) {

					int idSpesa = Integer.parseInt(parts[1]);

					SpesaDAOImpl.getInstance().cancellaSpesa(idSpesa);

					String response = Server.SPESA_CANCELLATA;
					out.println(response);

				}
				else if(operation.contentEquals(UPDATE_MASSIMALE)){
					
 					double upd_mas=Double.parseDouble(parts[1]);
 					int idspesa=Integer.parseInt(parts[2]);
 					
 					int idscheda=SpesaDAOImpl.getInstance().getIdScheda(idspesa);
 					
 					
 					if(SpesaDAOImpl.getInstance().updateMassimale(upd_mas, idscheda)){
 						
 						String response=Server.MASSIMALE_INSERITO;
 						out.println(response);
 					}
 					else{
 						String response="Error";
 						out.println(response);
 						
 					}
 					
 					
				}
			 
				else if(operation.contentEquals(SHOW_POINTS)){
					int idscheda=Integer.parseInt(parts[1]);
					
					int punti=SchedaDAOImpl.getInstance().show_points(idscheda);
					
					String response=Server.POINTS_SHOWED+"/"+punti;
					out.println(response);
				}
				
				else if(operation.contentEquals(GET_POINTS)){
					
					int idspesa=Integer.parseInt(parts[1]);
					
					int punti_spesa=SpesaDAOImpl.getInstance().CalcolaPuntiSpesa(idspesa);
					
					String response=Server.ADD_POINTS+"/"+punti_spesa;
					out.println(response);
					
				}
				
				else if(operation.contentEquals(UPDATE_POINTS)){
					int idspesa=Integer.parseInt(parts[1]);
 					int idscheda=SpesaDAOImpl.getInstance().getIdScheda(idspesa);
 					int punti=Integer.parseInt(parts[2]);

					int punti_spesa=SpesaDAOImpl.getInstance().AggiornaPuntiSpesa(idscheda,punti);
                    

					String response=Server.POINTS_UPDATED+"/"+punti_spesa;
					out.println(response);
				}
 
				//	SpesaDAOImpl.getInstance().AggiornaMassimaleResiduo(idscheda)
					
	else if(operation.contentEquals(Server.UPDATE_MASSIMALE_RESIDUO)){
					
					int idspesa=Integer.parseInt(parts[1]);
 					int idscheda=SpesaDAOImpl.getInstance().getIdScheda(idspesa);
					
 					double upd_massres=Double.parseDouble(parts[2]);
 					
					Scheda card =new Scheda (idscheda,upd_massres);
 					
 					SchedaDAOImpl.getInstance().UpdateMassimaleResiduo(card);
 						
 						String response=Server.MASSIMALE_RESIDUO_UPDATED;
 						out.println(response);
 				
 					
				}

				else if (operation.equals("logout")) {
					closeConnection = true;
					// Altre chiusure necessarie
				}
				
				

			}
		}
	}

}
