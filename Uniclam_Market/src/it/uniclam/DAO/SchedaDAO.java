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

	 
	
	public void activeCard(Scheda s, Utente u) throws SQLException;

	public int[] generatePin(Utente u) throws SQLException;


	public double checkMassimale(Scheda card) throws SQLException;

 	public Utente checkUser(Scheda card) throws SQLException; 

	public double show_points(Scheda card) throws SQLException;
	public void UpdateMassimaleResiduo(Scheda s) throws SQLException;




	public  void recovery_pin(Utente u) throws SQLException, AddressException,
	MessagingException;



}
