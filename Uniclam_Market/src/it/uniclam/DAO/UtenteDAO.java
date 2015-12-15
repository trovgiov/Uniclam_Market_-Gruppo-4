package it.uniclam.DAO;

import java.sql.SQLException;

import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;

public interface UtenteDAO {

	public void insertUtente(Utente u) throws SQLException;

	public void deleteUtente(Utente u) throws SQLException;

	public boolean login(Scheda s) throws SQLException;

	void updateUtente(Utente u, String new_mail) throws SQLException;

}
