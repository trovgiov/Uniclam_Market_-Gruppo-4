package it.uniclam.DAO;

 
import it.uniclam.entity.Carrello;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public interface SpesaDAO {
 
 
	int insertSpesa(Spesa c) throws SQLException;
 
	public boolean addProducts(Carrello c, Spesa s)
			throws SQLException;

 
	public DefaultTableModel getData(int idspesa) throws SQLException;

	public boolean deleteProduct(Carrello c, Spesa shop)
			throws SQLException;

	public void calcoloImporto(Spesa shop) throws SQLException;

	boolean updateProduct(Carrello carrello,Spesa shop)
			throws SQLException;

	public boolean cancellaSpesa(Spesa shop) throws SQLException;
	
	
 
 
	boolean show_Massimale(Scheda card) throws SQLException;

	int getIdScheda(int idspesa) throws SQLException;

	public   void CalcolaPuntiSpesa(Spesa shop) throws SQLException;

	public void AggiornaPuntiScheda(Scheda card,Spesa shop) throws SQLException;
}
