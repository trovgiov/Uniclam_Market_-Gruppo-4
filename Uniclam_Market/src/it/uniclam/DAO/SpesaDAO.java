package it.uniclam.DAO;


import it.uniclam.entity.Spesa;

import java.sql.SQLException;

public interface SpesaDAO {

	
 
	int insertSpesa( Spesa c) throws SQLException;

	
}
