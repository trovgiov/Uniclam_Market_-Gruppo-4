package it.uniclam.DAO;

import java.sql.SQLException;

import it.uniclam.entity.Utente;

public interface UtenteDAO {

	
	public void insertUtente(Utente u) throws SQLException;
 	public void deleteUtente (String email);
	void updateUtente(Utente u, String mail) throws SQLException;
    public boolean login(int id , int pin) throws SQLException;
  }
 