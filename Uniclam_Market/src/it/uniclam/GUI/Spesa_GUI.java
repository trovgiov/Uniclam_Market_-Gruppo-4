package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import it.uniclam.Controller.ControllerSpesa;
import it.uniclam.Controller.Controller_PersonalPage;
import it.uniclam.DAO.SpesaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.JTableOperation;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

import javax.swing.JScrollBar;

public class Spesa_GUI extends JFrame {

	private JTextField textBarcode;
	private int idspesa;
	private JTextField textQuantità;
	Socket s;
	private JTable table;
	private double mass_residuo;
	double importo_finale;

	/**
	 * Create the application.
	 */
	public Spesa_GUI(int id, Socket s, double mass_res) {
		this.idspesa = id;
		this.s = s;
		this.mass_residuo = mass_res;
		
		initialize();
	}

	public static BufferedReader in;
	public static PrintWriter out;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setTitle("UNICLAM MARKET - Effettua la tua spesa");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.setBounds(100, 100, 852, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JTextPane txtpnBenvenutoNelNostro = new JTextPane();
		txtpnBenvenutoNelNostro.setBounds(34, 6, 812, 48);
		txtpnBenvenutoNelNostro.setForeground(new Color(255, 255, 255));
		txtpnBenvenutoNelNostro.setBackground(new Color(102, 0, 0));
		txtpnBenvenutoNelNostro.setToolTipText("\\");
		txtpnBenvenutoNelNostro.setEditable(false);
		txtpnBenvenutoNelNostro
				.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		txtpnBenvenutoNelNostro
				.setText("Benvenuto nel nostro supermercato!\nPuoi aggiungere i prodotti che vuoi al carrello, utilizzando il lettore di codice a barre prelevato dalla sua base di carica. ");
		this.getContentPane().add(txtpnBenvenutoNelNostro);

		JLabel lblProdotto = new JLabel("Prodotto:");
		lblProdotto.setBounds(88, 86, 72, 16);
		lblProdotto.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblProdotto.setForeground(new Color(255, 255, 255));
		lblProdotto.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblProdotto);

		textBarcode = new JTextField();
		textBarcode.setBounds(186, 80, 186, 28);
		textBarcode.setBackground(Color.LIGHT_GRAY);
		this.getContentPane().add(textBarcode);
		textBarcode.setColumns(10);

		JLabel lblQuantit = new JLabel("Quantità: ");
		lblQuantit.setBounds(471, 86, 72, 16);
		lblQuantit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblQuantit.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantit.setForeground(Color.WHITE);
		this.getContentPane().add(lblQuantit);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 55, 840, 12);
		this.getContentPane().add(separator);

		JButton btnAggiungiAlCarrello = new JButton("Aggiungi al Carrello");
		btnAggiungiAlCarrello.setBounds(48, 156, 186, 24);
		getContentPane().add(btnAggiungiAlCarrello);

		JLabel lbl_importofinale = new JLabel("");
		lbl_importofinale.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 15));
		lbl_importofinale.setForeground(new Color(255, 255, 51));
		lbl_importofinale.setBounds(552, 530, 93, 16);
		getContentPane().add(lbl_importofinale);

		btnAggiungiAlCarrello.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String barcode = textBarcode.getText();
				String quantita = textQuantità.getText();

				// table.setVisible(true);

				importo_finale = ControllerSpesa.AddProduct(s, barcode,
						quantita, idspesa, table);
				lbl_importofinale.setText("" + importo_finale);

			}
		});

		JButton btnEliminaProdotto = new JButton("Elimina Prodotto");
		btnEliminaProdotto.setBounds(619, 154, 186, 28);
		getContentPane().add(btnEliminaProdotto);

		btnEliminaProdotto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String barcode = textBarcode.getText();

				importo_finale = ControllerSpesa.DeleteProduct(s, barcode,
						idspesa, table);

				lbl_importofinale.setText("" + importo_finale);
			}
		});

		JLabel lblIdSpesa = new JLabel(
				"La sua spesa è registrata con il codice : ");
		lblIdSpesa.setBounds(88, 128, 269, 16);
		lblIdSpesa.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblIdSpesa.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdSpesa.setForeground(Color.WHITE);
		getContentPane().add(lblIdSpesa);

		JLabel lblsetIDspesa = new JLabel("");
		lblsetIDspesa.setBounds(369, 128, 61, 16);
		lblsetIDspesa.setForeground(new Color(255, 255, 255));
		lblsetIDspesa.setText("" + getIdspesa());
		getContentPane().add(lblsetIDspesa);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 558, 840, 12);
		getContentPane().add(separator_1);

		JButton btnAvanti = new JButton("Avanti >");
		btnAvanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAvanti.setBounds(384, 582, 166, 47);
		getContentPane().add(btnAvanti);

		JButton btnEsci = new JButton("Annulla ed esci");
		btnEsci.setBounds(206, 582, 166, 47);
		getContentPane().add(btnEsci);

		JLabel lblVer = new JLabel(
				"Ver. 1.0 - © Tutti i diritti riservati - Developed by Giovanni Trovini ed Enea Marinelli");
		lblVer.setBounds(174, 663, 450, 16);
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setForeground(SystemColor.scrollbar);
		lblVer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		this.getContentPane().add(lblVer);

		textQuantità = new JTextField("1");
		textQuantità.setBounds(555, 80, 72, 28);
		getContentPane().add(textQuantità);
		textQuantità.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(30, 193, 789, 293);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("IMPORTO FINALE :       €");
		lblNewLabel.setForeground(new Color(255, 255, 51));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC,
				15));
		lblNewLabel.setBounds(340, 531, 186, 16);
		getContentPane().add(lblNewLabel);

		JButton btnModificaQuantit = new JButton("Modifica quantità");
		btnModificaQuantit.setBounds(346, 154, 186, 28);
		getContentPane().add(btnModificaQuantit);

		JLabel lblMassimaleResiduo = new JLabel("MASSIMALE RESIDUO : €");
		lblMassimaleResiduo.setForeground(new Color(255, 255, 0));
		lblMassimaleResiduo.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 15));
		lblMassimaleResiduo.setBounds(313, 503, 196, 16);
		getContentPane().add(lblMassimaleResiduo);

		JLabel lbl_masRes = new JLabel("" + mass_residuo);
		lbl_masRes.setForeground(new Color(255, 255, 51));
		lbl_masRes.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC,
				15));
		lbl_masRes.setBounds(519, 502, 93, 16);
		getContentPane().add(lbl_masRes);

		btnModificaQuantit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String barcode = textBarcode.getText();
				String quantita = textQuantità.getText();

				importo_finale = ControllerSpesa.UpdateProduct(s, barcode,
						quantita, idspesa, table);
				lbl_importofinale.setText("" + importo_finale);

			}

		});

		//Pulsante Avanti
		btnAvanti.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			 
 				
				
				if(importo_finale<=mass_residuo){
					Riepilogo_GUI windows=new Riepilogo_GUI(idspesa, s, mass_residuo, importo_finale);
                    windows.setVisible(true);
			
			 
					
				}
			
				else {
					JOptionPane.showMessageDialog(null, "Errore massimale");
				}
				
				
				
		 
			}});

		// Pulsante Annulla ed Esci
		btnEsci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int scelta = JOptionPane.showConfirmDialog(Spesa_GUI.this,
						"Sei di voler annullare la spesa e uscire?", "",
						JOptionPane.YES_NO_OPTION);
				switch (scelta) {
				case JOptionPane.YES_OPTION: {
					try {
						JOptionPane.showMessageDialog(null,
								"Il suo massimale residuo e' " + mass_residuo);

						ControllerSpesa.cancellaSpesa(s, idspesa);
						// PersonalPage_GUI = new Per
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
					break;

				case JOptionPane.NO_OPTION:
					break;

				}
			}
		});
		

	}

	public int getIdspesa() {
		return idspesa;
	}

	public void setIdspesa(int idspesa) {
		this.idspesa = idspesa;
	}
}