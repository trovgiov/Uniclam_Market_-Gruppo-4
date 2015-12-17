package it.uniclam.DAO;

import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;

import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
/**
 * Interfaccia SchedaDAO
 * @author GiovanniTrovini
 *
 */
public interface SchedaDAO {


	/**
	 * Si occupa di attivare la tessera fedelta' e inserire i dati nel database.
	 * @param s Scheda
	 * @param u Utente
	 * @throws SQLException
	 */
	public void activeCard(Scheda s, Utente u) throws SQLException;


	/**
	 * Generare id scheda e pin. Il pin a 4 cifre viene generato casualmente con la funzione random.
	 * @param u Utente
	 * @return pin Restituisce il pin univoco appartenente all'utente che ha effettuato la registrazione
	 * @throws SQLException
	 */
	public int[] generatePin(Utente u) throws SQLException;


	/**
	 * Si occupa di controllare e restituire il massimale. Il controllo avviene secondo le caratteristiche progettuali.
	 * Se il mese, in cui l'utente accede al sistema, corrisponde al mese dell'ultima spesa effetuata, il sistema restituisce il massimale residuo.
	 * Se il mese, in cui l'utente accede al sistema, corrisponde a un mese diverso dall'ultima spesa, il sistema resetta il massimale residuo a quello totale.
	 * @param card Scheda
	 * @return massimale Massimale utente
	 * @throws SQLException
	 */
	public double checkMassimale(Scheda card) throws SQLException;

	/**
	 * Acquisisce le informazioni personali dell'utente  che ha effettuato il login 
	 * @param card Scheda
	 * @return u Utente
	 * @throws SQLException
	 */
	public Utente checkUser(Scheda card) throws SQLException; 

	
	/**
	 * Seleziona , tramite query, i punti totali a partire dall'id scheda.
	 * @param card Scheda
	 * @throws SQLException
	 */
	public void show_points(Scheda card) throws SQLException;
	
	
	/**
	 * Eseguo l'update del massimale residuo e lo inserisco nel db
	 * @param s Scheda
	 * @throws SQLException
	 */
	public void UpdateMassimaleResiduo(Scheda s) throws SQLException;




	/**
	 * Funzione aggiuntiva al progetto.
	 * Consente di recuperare i dati di accesso al sistema, tramite l'invio di una mail all'indirizzo specificato in fase di registrazione.
	 * @param u Utente
	 * @throws SQLException
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public  void recovery_pin(Utente u) throws SQLException, AddressException,
	MessagingException;



}
