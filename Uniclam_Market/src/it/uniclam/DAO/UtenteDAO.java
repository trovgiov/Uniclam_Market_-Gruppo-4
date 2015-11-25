package it.uniclam.DAO;

import java.sql.SQLException;

import it.uniclam.entity.Utente;

public interface UtenteDAO {

	
	public void insertUtente(Utente u) throws SQLException;
	public void generatePin(String string) throws SQLException;
}
