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

	/**
	 * Create the application.
	 */
	public Spesa_GUI(int id, Socket s) {
		this.idspesa = id;
		this.s = s;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setTitle("UNICLAM MARKET - Effettua la tua spesa");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.setBounds(100, 100, 780, 612);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JTextPane txtpnBenvenutoNelNostro = new JTextPane();
		txtpnBenvenutoNelNostro.setBounds(34, 6, 728, 48);
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
		lblProdotto.setBounds(48, 86, 72, 16);
		lblProdotto.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblProdotto.setForeground(new Color(255, 255, 255));
		lblProdotto.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblProdotto);

		textBarcode = new JTextField();
		textBarcode.setBounds(132, 79, 186, 28);
		textBarcode.setBackground(Color.LIGHT_GRAY);
		this.getContentPane().add(textBarcode);
		textBarcode.setColumns(10);

		JLabel lblQuantit = new JLabel("Quantità: ");
		lblQuantit.setBounds(374, 86, 72, 16);
		lblQuantit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblQuantit.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantit.setForeground(Color.WHITE);
		this.getContentPane().add(lblQuantit);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 55, 768, 12);
		this.getContentPane().add(separator);

		JButton btnAggiungiAlCarrello = new JButton("Aggiungi al Carrello");
		btnAggiungiAlCarrello.setBounds(551, 83, 186, 24);
		getContentPane().add(btnAggiungiAlCarrello);

		JLabel lbl_importofinale = new JLabel("");
		lbl_importofinale.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 15));
		lbl_importofinale.setForeground(new Color(255, 255, 51));
		lbl_importofinale.setBounds(458, 462, 61, 16);
		getContentPane().add(lbl_importofinale);

		btnAggiungiAlCarrello.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String barcode = textBarcode.getText();
				String quantita = textQuantità.getText();

				// table.setVisible(true);

				try {
					Login_GUI.in = new BufferedReader(new InputStreamReader(s
							.getInputStream()));
					Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

					String response = Server.INSERT_PRODUCTS + "/" + barcode
							+ "/" + idspesa + "/" + quantita;

					Login_GUI.out.println(response);

					String line = Login_GUI.in.readLine();
					String part[] = line.split("/");

					if (part[0].contentEquals("prodotto inserito")) {

						try {
							DefaultTableModel dm = new JTableOperation()
							.getData(idspesa);

							// dm=SpesaDAOImpl.getInstance().getData(idspesa);

							table.setModel(dm);

							lbl_importofinale.setText(part[1]);

						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					else {
						JOptionPane.showMessageDialog(Spesa_GUI.this,
								"prodotto non inserito");

					}

				} catch (IOException ioe) {

					JOptionPane.showMessageDialog(Spesa_GUI.this,
							"Error in communication with server!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		JButton btnEliminaProdotto = new JButton("Elimina Prodotto");
		btnEliminaProdotto.setBounds(551, 107, 186, 28);
		btnEliminaProdotto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String barcode = textBarcode.getText();
				try {
					Login_GUI.in = new BufferedReader(new InputStreamReader(s
							.getInputStream()));
					Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

					String response = Server.DELETE_PRODUCTS + "/" + barcode
							+ "/" + idspesa;

					Login_GUI.out.println(response);

					String line = Login_GUI.in.readLine();

					if (line.contentEquals("prodotto eliminato")) {

						try {
							DefaultTableModel dm = new JTableOperation()
							.getData(idspesa);

							table.setModel(dm);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					else {
						JOptionPane.showMessageDialog(Spesa_GUI.this,
								"prodotto non eliminato");

					}

				} catch (IOException ioe) {

					JOptionPane.showMessageDialog(Spesa_GUI.this,
							"Error in communication with server!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});
		getContentPane().add(btnEliminaProdotto);

		JLabel lblIdSpesa = new JLabel(
				"La sua spesa è registrata con il codice : ");
		lblIdSpesa.setBounds(49, 114, 269, 16);
		lblIdSpesa.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblIdSpesa.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdSpesa.setForeground(Color.WHITE);
		getContentPane().add(lblIdSpesa);

		JLabel lblsetIDspesa = new JLabel("");
		lblsetIDspesa.setBounds(336, 114, 61, 16);
		lblsetIDspesa.setForeground(new Color(255, 255, 255));
		lblsetIDspesa.setText("" + getIdspesa());
		getContentPane().add(lblsetIDspesa);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 490, 756, 12);
		getContentPane().add(separator_1);

		JButton btnConferma = new JButton("Conferma e paga");
		btnConferma.setBounds(152, 508, 166, 47);
		getContentPane().add(btnConferma);

		JButton btnEsci = new JButton("Annulla ed esci");
		btnEsci.setBounds(458, 508, 166, 47);
		getContentPane().add(btnEsci);

		JLabel lblVer = new JLabel(
				"Ver. 1.0 - © Tutti i diritti riservati - Developed by Giovanni Trovini ed Enea Marinelli");
		lblVer.setBounds(152, 568, 450, 16);
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setForeground(SystemColor.scrollbar);
		lblVer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		this.getContentPane().add(lblVer);

		textQuantità = new JTextField();
		textQuantità.setBounds(447, 80, 72, 28);
		getContentPane().add(textQuantità);
		textQuantità.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 147, 691, 292);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("IMPORTO FINALE : €");
		lblNewLabel.setForeground(new Color(255, 255, 51));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC,
				15));
		lblNewLabel.setBounds(269, 461, 177, 16);
		getContentPane().add(lblNewLabel);

		// Pulsante Annulla ed Esci
		btnEsci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int scelta = JOptionPane.showConfirmDialog(Spesa_GUI.this,
						"Sei di voler annullare la spesa e uscire?", "",
						JOptionPane.YES_NO_OPTION);
				switch (scelta) {
				case JOptionPane.YES_OPTION:
					System.exit(0);
					;

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