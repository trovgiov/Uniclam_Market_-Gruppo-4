package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Spesa_GUI extends JFrame{

  private JTextField textIdProdotto;
 private JTable table;
private int idspesa;


 /**
 * Create the application.
 */
 public Spesa_GUI(int id) {
 this.idspesa=id;
	 
	 initialize();
 }

 /**
 * Initialize the contents of the frame.
 */
 private void initialize() {
  this.setTitle("UNICLAM MARKET - Effettua la tua spesa");
 this.getContentPane().setBackground(new Color(153, 0, 0));
 this.setBounds(100, 100, 780, 556);
 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 this.getContentPane().setLayout(null);
 
 JTextPane txtpnBenvenutoNelNostro = new JTextPane();
 txtpnBenvenutoNelNostro.setForeground(new Color(255, 255, 255));
 txtpnBenvenutoNelNostro.setBackground(new Color(153, 0, 0));
 txtpnBenvenutoNelNostro.setToolTipText("\\");
 txtpnBenvenutoNelNostro.setEditable(false);
 txtpnBenvenutoNelNostro.setFont(new Font("Lucida Grande", Font.BOLD, 13));
 txtpnBenvenutoNelNostro.setText("Benvenuto nel nostro supermercato!\nPuoi aggiungere i prodotti che vuoi al carrello, utilizzando il lettore di codice a barre prelevato dalla sua base di carica. ");
 txtpnBenvenutoNelNostro.setBounds(46, 6, 728, 48);
 this.getContentPane().add(txtpnBenvenutoNelNostro);
 
 JLabel lblProdotto = new JLabel("Prodotto:");
 lblProdotto.setFont(new Font("Lucida Grande", Font.BOLD, 13));
 lblProdotto.setForeground(new Color(255, 255, 255));
 lblProdotto.setHorizontalAlignment(SwingConstants.CENTER);
 lblProdotto.setBounds(48, 86, 72, 16);
 this.getContentPane().add(lblProdotto);
 
 textIdProdotto = new JTextField();
 textIdProdotto.setBackground(Color.LIGHT_GRAY);
 textIdProdotto.setBounds(132, 79, 186, 28);
 this.getContentPane().add(textIdProdotto);
 textIdProdotto.setColumns(10);
 
 JLabel lblQuantit = new JLabel("Quantità: ");
 lblQuantit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
 lblQuantit.setHorizontalAlignment(SwingConstants.CENTER);
 lblQuantit.setForeground(Color.WHITE);
 lblQuantit.setBounds(374, 86, 72, 16);
 this.getContentPane().add(lblQuantit);
 
 JSeparator separator = new JSeparator();
 separator.setBounds(6, 55, 768, 12);
 this.getContentPane().add(separator);
 
 JComboBox comboBox = new JComboBox();
 comboBox.setToolTipText("Selezionare la quantità desiderata");
 comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
 comboBox.setMaximumRowCount(10);
 comboBox.setBounds(458, 79, 58, 33);
 this.getContentPane().add(comboBox);
 
 table = new JTable();
 table.setBounds(45, 147, 681, 337);
 this.getContentPane().add(table);
 
 JButton btnAggiungiAlCarrello = new JButton("Aggiungi al Carrello");
 btnAggiungiAlCarrello.setBounds(551, 83, 186, 24);
 getContentPane().add(btnAggiungiAlCarrello);
 
 JButton btnEliminaProdotto = new JButton("Elimina Prodotto");
 btnEliminaProdotto.addActionListener(new ActionListener() {
 	public void actionPerformed(ActionEvent e) {
 	}
 });
 btnEliminaProdotto.setBounds(551, 107, 186, 28);
 getContentPane().add(btnEliminaProdotto);
 
 JLabel lblIdSpesa = new JLabel("id Spesa : ");
 lblIdSpesa.setForeground(Color.WHITE);
 lblIdSpesa.setBounds(58, 119, 74, 16);
 getContentPane().add(lblIdSpesa);
 
 JLabel lblsetIDspesa = new JLabel("");
 lblsetIDspesa.setText(""+getIdspesa());
 lblsetIDspesa.setBounds(142, 119, 61, 16);
 getContentPane().add(lblsetIDspesa);
 }

public int getIdspesa() {
	return idspesa;
}

public void setIdspesa(int idspesa) {
	this.idspesa = idspesa;
}
}