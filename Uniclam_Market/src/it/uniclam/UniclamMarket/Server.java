package it.uniclam.UniclamMarket;

import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.DAO.SpesaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.GUI.Login_GUI;
import it.uniclam.entity.Carrello;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;



/**
 * Classe Server - Socket
 * 
 * @author Giovanni Trovini
 *
 */
public class Server {

	// Configurazione socket
	public static String HOST = "localhost";
	public static int port = 8888;

	public static String ERROR="Error";
	public static String LOGIN_ERROR="Login Error";

	// Definizione delle richieste
	public static String INSERT_USER = "req_insert_user";
	public static String LOGIN_USER = "req_login";
	public static String CREATE_SHOPPING="req_create_shopping";
	public static String DELETE_USER = "req_delete_user";
	public static String SHOW_POINTS = "req_show_points";
	public static String INSERT_PRODUCT = "req_inserimento_prodotto";
	public static String DELETE_PRODUCT = "req_delete_prodotto";
	public static String UPDATE_PRODUCT = "req_update_prodotto";
	public static String DELETE_SHOP="req_delete_shop";
	public static   String UPDATE_MASSIMALE_RESIDUO = "req_massimale_residuo_aggiornato";
	public static  String LOGOUT = "req_logout";

	public static  String UPDATE_POINTS = "req_update_points";
	public static  String ADD_POINTS = "RES_add_points";
	public static  String GET_POINTS = "req_get_points";
	public static String MASSIMALE_INSERITO = "RES_ massimaleInserito";
	public static String SHOW_MASSIMALE = "req_show_max";
	public static String SHOW_SPESA = "req_show_spesa";
	public static String PERSONAL_PAGE = "req_Persona_Page";
	public static String CREA_SPESA = "req_Creazione_Spesa";
	public static String MASSIMALE = "req_massimale";
	public static String CHANGE_EMAIL = "req_change_email";
	public static String RECOVERY_PIN="req_recovery_pin";




	//Definizione delle risposte
	public static String USER_INSERTED="RES_user_inserted";
	public static String USER_LOGGED="RES_user_logged";
	public static String SHOPPING_CREATED="RES_shopping_created";
	public static String USER_DELETED = "RES_user_deleted";
	public static String POINTS_SHOWED = "RES_points_showed";
	public static String PRODUCT_INSERTED = "RES_product_inserted";
	public static String PRODUCT_NOT_INSERTED = "RES_product_not_inserted";
	public static String PRODUCT_DELETED = "RES_product_deleted";
	public static String PRODUCT_NOT_DELETED = "RES_product_not_deleted";
	public static String PRODUCT_UPDATED = "RES_update_prodotto";
	public static String PRODUCT_NOT_UPDATED = "RES_non_update_prodotto";
	public static String POINTS_ADDED ="RES_points_added";
	public static String SHOP_DELETED="RES_shop_deleted";
	public static String POINTS_UPDATED = "RES_points_updated";
	public static String EMAIL_CHANGED = "RES_email_changed";
	public static String SPESA_CANCELLATA = "RES_spesa_cancellata";
	public static String PIN_RECOVERED = "RES_pin_recovered";
	public static String SPESA_CREATA = "RES_spesa_creata";
	public static String MASSIMALE_RESIDUO_UPDATED="RES_massimale_residuo_updated";
	public static String LOGOUT_OK="RES_logout_ok";


	public static int PUNTI_TOTALI=0;

	public static void main(String[] args) throws Throwable {

		//dichiaro gli oggetti che andrò ad usare nelle varie operazioni
		Spesa shop = new Spesa() ;
		Carrello carrello=new Carrello();
		Scheda card=new Scheda();
		Utente u= new Utente();


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

				// Riceve  la richiesta dal client chee viene divisa in più stringhe, divise dal carattere "/"


				String r = in.readLine();
				String[] parts = r.split("/");


				// parts[0] è la stringa che definisce l'operazione da eseguire

				operation = parts[0];  


				//operazione per la regisrrazione dell'utente

				if (operation.contentEquals(INSERT_USER)) {

					String massimal = parts[5];
					double massimale = Double.parseDouble(massimal);

					// Costruttore Utente(nome,cognome,email,telefono,massimale)

					u.setNome(parts[1]);
					u.setCognome(parts[2]);
					u.setEmail(parts[3]);
					u.setTelefono(parts[4]);
					u.setMassimale(massimale);



					UtenteDAOImpl.getInstance().insertUtente(u);


					// Creo l'oggetto scheda, assegnando i punti_totali a 0 


					//Scheda card = new Scheda(PUNTI_TOTALI, massimale);
					card.setPunti_totali(PUNTI_TOTALI);
					card.setMassimale_res(massimale);

					// Attivo la carta con email del cliente
					SchedaDAOImpl.getInstance().activeCard(card, u);

					// Genero Pin,che verrà  associato alla carta e memorizzo i dati in un array di interi

					int a[] = SchedaDAOImpl.getInstance().generatePin(
							u);

					// mando la risposta al client, con id scheda(a[0]) e pin(a[1])
					String response =  Server.USER_INSERTED +"/"+a[0]+"/"+a[1]+"\n";

					out.println(response);
					s.close();

					closeConnection=true;
				}

				else if (operation.contentEquals(LOGIN_USER)) {


					//assegno i valori di idscheda e pin a Scheda

					card.setIdScheda(Integer.parseInt(parts[1]));
					card.setPin(Integer.parseInt(parts[2]));

					boolean result;

					// metodo che restituisce true se il login è andato a buon fine
					result = UtenteDAOImpl.getInstance().login(card);


					// se login è avvenuto con successo
					if (result) {


						Double mas_res = SchedaDAOImpl.getInstance()
								.checkMassimale(card);

						Utente a = SchedaDAOImpl.getInstance().checkUser(
								card);

						String response = USER_LOGGED + "/" + mas_res + "/"
								+ a.getNome() + "/" + a.getCognome() + "/"
								+ a.getEmail()+"\n";

						out.println(response);

					} else {


						out.println(Server.LOGIN_ERROR);
					}



				}
				else if(operation.contentEquals(RECOVERY_PIN)){


					//String email=parts[1];
					u.setEmail(parts[1]);
					SchedaDAOImpl.getInstance().recovery_pin(u);

					String response=Server.PIN_RECOVERED+"\n\n";
					out.println(response);






				}

				else if (operation.contentEquals(CREATE_SHOPPING)) {

					// Date dataspesa = null;


					java.util.Date data_spesa_java = new Date();
					java.sql.Date data_spesa = new java.sql.Date(
							data_spesa_java.getTime());

					// assegno l'id spesa e la data spesa a shop (Spesa)
					shop.setIdscheda(Integer.parseInt(parts[1]));
					shop.setData_spesa(data_spesa);


					// Genero un id_spesa random e lo invio al Client,per mostrarlo all'utente
					int idspesa = SpesaDAOImpl.getInstance().insertSpesa(shop);

					shop.setIdspesa(idspesa);

					// invio la risposta al client
					String response =Server.SHOPPING_CREATED + "/" + shop.getIdspesa()+"\n\n";
					out.println(response);

				}

				else if (operation.contentEquals(INSERT_PRODUCT)) {

					int quantita = Integer.parseInt(parts[2]);

					// assegno il barcode e la quantità al carrello
					carrello.setBarcode(parts[1]);
					carrello.setQuantita(quantita);

					//assegno l'id spesa alla spesa

					// Se il metodo restituisce true, allora calcolo importo
					if (SpesaDAOImpl.getInstance().addProducts(carrello,
							shop)) {


						SpesaDAOImpl.getInstance()
						.calcoloImporto(shop);


						String response = Server.PRODUCT_INSERTED + "/" + shop.getImporto_tot();
						out.println(response);

					} else {
						String response = Server.PRODUCT_NOT_INSERTED+"\n";
						out.println(response);
					}

				}

				else if (operation.contentEquals(DELETE_PRODUCT)) {


					//assegno al carrello, il barcode del prodotto da eliminare
					carrello.setBarcode(parts[1]);


					//
					if (SpesaDAOImpl.getInstance().deleteProduct(carrello,
							shop)) {


						SpesaDAOImpl.getInstance()
						.calcoloImporto(shop);

						String response = Server.PRODUCT_DELETED + "/" +shop.getImporto_tot();
						out.println(response);

					}

					else {
						String response = Server.PRODUCT_NOT_DELETED+"\n";
						out.println(response);
					}

				}

				else if (operation.contentEquals(UPDATE_PRODUCT)) {


					int quantita = Integer.parseInt(parts[2]);

					//assegno il barcode e la quantita del prodotto al carrello

					carrello.setBarcode(parts[1]);

					carrello.setQuantita(quantita);

					// se la query è andata a buon fine, aggiorno importo
					if (SpesaDAOImpl.getInstance().updateProduct(carrello,shop)) {

						SpesaDAOImpl.getInstance()
						.calcoloImporto(shop);

						String response = Server.PRODUCT_UPDATED + "/" + shop.getImporto_tot()+"\n";  
						out.println(response);

					} else {
						String response = Server.PRODUCT_NOT_UPDATED+"\n";
						out.println(response);

					}

				}

				else if (operation.contentEquals(DELETE_USER)) {

					// Costruttore Utente con il valore email
					u.setEmail(parts[1]);
					//Metodo che esegue la query che elimina l'utente dal sistema 
					UtenteDAOImpl.getInstance().deleteUtente(u);

					//Invio la risposta al client
					String response = Server.USER_DELETED+"\n";
					out.println(response);

				}

				else if (operation.contentEquals(Server.CHANGE_EMAIL)) {

					// email parts 1 // new email parts[2]
					u.setEmail(parts[1]);
					//Metodo che esegue la query di update email 
					UtenteDAOImpl.getInstance()
					.updateUtente(u, parts[2]);

					String response = Server.EMAIL_CHANGED+"\n";
					//Invio la risposta al Client
					out.println(response);

				}

				else if (operation.contentEquals(DELETE_SHOP)) {


					//Cancello la spesa
					SpesaDAOImpl.getInstance().cancellaSpesa(shop);

					String response = Server.SHOP_DELETED;
					out.println(response);

				}




				else if(operation.contentEquals(SHOW_POINTS)){

					card.setIdScheda(Integer.parseInt(parts[1]));

					//Metodo che esegue la query per mostrare i punti.
					//Restituisce i punti totali da inviare al server
					SchedaDAOImpl.getInstance().show_points(card);

					// Preparo la risposta da inviare al server
					String response=Server.POINTS_SHOWED+"/"+card.getPunti_totali()+"\n";
					//Invio la risposta
					out.println(response);
				}

				else if(operation.contentEquals(GET_POINTS)){


					//Calcolo punti Spesa
					SpesaDAOImpl.getInstance().CalcolaPuntiSpesa(shop);
					//invio i punti al server
					String response=Server.POINTS_ADDED+"/"+shop.getPunti_spesa()+"\n";
					out.println(response);

				}

				else if(operation.contentEquals(UPDATE_POINTS)){

					card.setMassimale_res(Double.parseDouble(parts[1]));
					//Aggiorno i punti spesa
					SpesaDAOImpl.getInstance().AggiornaPuntiScheda(card,shop);


					String response=Server.POINTS_UPDATED+"/"+card.getPunti_totali()+"\n";
					out.println(response);
				}



				else if(operation.contentEquals(Server.UPDATE_MASSIMALE_RESIDUO)){


					double upd_massres=Double.parseDouble(parts[1]);
					// assegno alla scheda, il valore che proviene dalla richiesta
					card.setMassimale_res(upd_massres);

					//Aggiorno il massimale residuo
					SchedaDAOImpl.getInstance().UpdateMassimaleResiduo(card);

					String response=Server.MASSIMALE_RESIDUO_UPDATED+"/"+card.getMassimale_res();
					out.println(response);


				}

				else if (operation.equals(Server.LOGOUT)) {

					Login_GUI.in.close();
					Login_GUI.out.close();
					s.close();



					closeConnection = true;


				}



			}
		}
	}

}
