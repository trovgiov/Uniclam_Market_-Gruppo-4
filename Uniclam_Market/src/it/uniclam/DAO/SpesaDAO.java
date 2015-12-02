package it.uniclam.DAO;


import it.uniclam.entity.Spesa;

import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public interface SpesaDAO {

	
 
	int insertSpesa( Spesa c) throws SQLException;
    public boolean addProducts(String barcode,int idscheda,int quantita) throws SQLException;
	public DefaultTableModel getData(int idspesa) throws SQLException;
}
