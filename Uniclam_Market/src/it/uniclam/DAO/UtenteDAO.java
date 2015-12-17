package it.uniclam.DAO;

import java.sql.SQLException;

import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;

public interface UtenteDAO {

	/**
	 * Crea ed inserisce l'utente nel db
	 * @param u Utente
	 * @throws SQLException
	 */
	public void insertUtente(Utente u) throws SQLException;

	/**
	 * Elimina l'utente dal db
	 * @param u Utente
	 * @throws SQLException
	 */
	public void deleteUtente(Utente u) throws SQLException;

	/**
	 * Consente l'autenticazione. Restituisce true se le credenziali inserite si trovano nel database, altrimenti non consente l'accesso al sistema
	 * @param s Scheda
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean login(Scheda s) throws SQLException;

	/**
	 * Aggiorna la mail dell'utente
	 * @param u Utente
	 * @param new_mail String
	 * @throws SQLException
	 */
	void updateUtente(Utente u, String new_mail) throws SQLException;

}
