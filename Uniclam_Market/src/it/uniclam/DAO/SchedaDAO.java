package it.uniclam.DAO;

import it.uniclam.entity.Scheda;


import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface SchedaDAO {

	public void activeCard (Scheda s , String email_Utente) throws SQLException;
 	public int[] generatePin(String m) throws SQLException;
 	public void recovery_pin(String email) throws SQLException, AddressException, MessagingException;
 	public double checkMassimale(int idscheda) throws SQLException;
	String checkUtente(int idscheda) throws SQLException;
 
 }
