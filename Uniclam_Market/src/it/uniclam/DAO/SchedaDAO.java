package it.uniclam.DAO;

import it.uniclam.entity.Scheda;

import java.sql.SQLException;

public interface SchedaDAO {

	public void activeCard (Scheda s , String email_Utente) throws SQLException;
 	public int[] generatePin(String m) throws SQLException;
 }
