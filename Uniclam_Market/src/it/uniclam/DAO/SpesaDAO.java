package it.uniclam.DAO;

 
import it.uniclam.entity.Carrello;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;

import java.sql.SQLException;
 
import javax.swing.table.DefaultTableModel;

public interface SpesaDAO {
 
 
	/**
	 * Crea e inseriscela spesa nel database,generando un id spesa con la funzione random
	 * @param c Spesa
	 * @return idspesa id della spesa 
	 * @throws SQLException
	 */
	int insertSpesa(Spesa c) throws SQLException;
 
	
	/**
	  Aggiunge i  prodotti nel carrello, tramite una query di insert nel database.
	 * Restituisce una variabile boolean. Se return true allora il prodotto è stato correttamente inserito
	 * @param c Carrello
	 * @param s Spesa
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean addProducts(Carrello c, Spesa s)
			throws SQLException;

 /**
  * Interfacciamento con la JTable. Esegue una select di tutte le informazioni necessarie sui prodotti, che saranno fornite all'utente
  * @param idspesa id della spesa
  * @return dm DefaultTableModel
  * @throws SQLException
  */
	public DefaultTableModel getData(int idspesa) throws SQLException;

	
	/**
	 * Eliminazione Prodotto dalla lista spesa e aggiornamento dell'importo finale.
	 * @param c Carrello
	 * @param shop Spesa
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean deleteProduct(Carrello c, Spesa shop)
			throws SQLException;

	
	/**
	 * Calcolo dell'importo finale della spesa.
	 * Il calcolo viene eseguito direttamente nella query.
	 * @param shop Spesa
	 * @throws SQLException
	 */
	public void calcoloImporto(Spesa shop) throws SQLException;

	
	/**
	 * Consente di aggiornare la quantità del prodotto da acquistare.
	 * @param carrello Carrello
	 * @param shop Spesa
	 * @return boolean
	 * @throws SQLException
	 */
	boolean updateProduct(Carrello carrello,Spesa shop)
			throws SQLException;

	
	/**
	 * Elimina la spesa dal db 
	 * @param shop Spesa
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean cancellaSpesa(Spesa shop) throws SQLException;
	
	
 
 /**
  * Mostra il massimale residuo dell'utente
  * @param card
  * @return boolean
  * @throws SQLException
  */
	boolean show_Massimale(Scheda card) throws SQLException;

 
	/**
	 * Calcolo punti tessera fedeltà per la singola spesa  
	 * Il calcolo punti viene eseguito dentro la query : SUM (quantita*punti prodotto) as punti.
	 * Il result della query, viene assegnato alla spesa con il metodo setPunti_spesa
	 * @param shop Spesa 
	 * @throws SQLException
	 */
	public   void CalcolaPuntiSpesa(Spesa shop) throws SQLException;

	/**
	 * Aggiorna i punti della scheda sommando i punti precendenti con quelli dell'ultima spesa
	 * @param card Scheda
	 * @param shop Spesa 
	 * @throws SQLException
	 */
	public void AggiornaPuntiScheda(Scheda card,Spesa shop) throws SQLException;
}
