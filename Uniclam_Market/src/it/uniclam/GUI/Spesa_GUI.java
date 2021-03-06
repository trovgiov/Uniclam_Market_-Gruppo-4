package it.uniclam.GUI;


import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;





import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.SystemColor;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;





import it.uniclam.Controller.ControllerLogin;
import it.uniclam.Controller.ControllerSpesa;
import it.uniclam.entity.Carrello;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

/**
 * Interfaccia grafica Spesa
 * @author GiovanniTrovini Enea Marinelli
 *
 */
@SuppressWarnings("serial")
public class Spesa_GUI extends JFrame {

	private JTextField textBarcode;
	private JTextField textQuantità;
	Socket s;
	private JTable table;
	double importo_finale;

	private Spesa shop;
	private Scheda card;
	private Utente u;
	/**
	 * Create the application.
	 */




	public static BufferedReader in;

	public Spesa_GUI(Socket s, Spesa shop,Scheda card,Utente u) throws HeadlessException {
		super();
		this.s = s;
		this.shop = shop;
		this.card=card;
		this.u=u;

		initialize();
	}

	public static PrintWriter out;

	/**
	 * Initialize the contents of the frame.
	 */

	Icon error = new ImageIcon ("img/error.png");
	Icon scary = new ImageIcon("img/scary.png");
	private void initialize() {
		this.setTitle("UNICLAM MARKET - Effettua la tua spesa");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.setBounds(100, 100, 851, 910);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JLabel lblProdotto = new JLabel("Prodotto:");
		lblProdotto.setBounds(166, 196, 72, 16);
		lblProdotto.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblProdotto.setForeground(new Color(255, 255, 255));
		lblProdotto.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(lblProdotto);

		textBarcode = new JTextField();
		textBarcode.setBounds(264, 190, 186, 28);
		textBarcode.setBackground(Color.LIGHT_GRAY);
		this.getContentPane().add(textBarcode);
		textBarcode.setColumns(10);

		JLabel lblQuantit = new JLabel("Quantità: ");
		lblQuantit.setBounds(510, 196, 72, 16);
		lblQuantit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblQuantit.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuantit.setForeground(Color.WHITE);
		this.getContentPane().add(lblQuantit);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 131, 840, 12);
		this.getContentPane().add(separator);

		Icon add_cart = new ImageIcon("img/add_cart.png");
		JButton btnAggiungiAlCarrello = new JButton("Aggiungi al Carrello");
		btnAggiungiAlCarrello.setIcon(add_cart);
		btnAggiungiAlCarrello.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		btnAggiungiAlCarrello.setBounds(34, 246, 217, 58);
		getContentPane().add(btnAggiungiAlCarrello);

		JLabel lbl_importofinale = new JLabel("");
		lbl_importofinale.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 15));
		lbl_importofinale.setForeground(new Color(255, 255, 51));
		lbl_importofinale.setBounds(475, 669, 93, 16);
		getContentPane().add(lbl_importofinale);


		Icon delete_prod = new ImageIcon("img/delete_prod.png");
		JButton btnEliminaProdotto = new JButton("Elimina Prodotto");
		btnEliminaProdotto.setIcon(delete_prod);
		btnEliminaProdotto.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		btnEliminaProdotto.setBounds(603, 246, 217, 58);
		getContentPane().add(btnEliminaProdotto);



		JLabel lblIdSpesa = new JLabel(
				"La sua spesa è registrata con il codice : ");
		lblIdSpesa.setBounds(6, 147, 269, 16);
		lblIdSpesa.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblIdSpesa.setHorizontalAlignment(SwingConstants.CENTER);
		lblIdSpesa.setForeground(Color.WHITE);
		getContentPane().add(lblIdSpesa);

		JLabel lblsetIDspesa = new JLabel("");
		lblsetIDspesa.setBounds(287, 147, 61, 16);
		lblsetIDspesa.setForeground(new Color(255, 255, 255));
		lblsetIDspesa.setText("" + shop.getIdspesa());
		getContentPane().add(lblsetIDspesa);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 706, 840, 12);
		getContentPane().add(separator_1);

		Icon next = new ImageIcon("img/next.png");
		JButton btnAvanti = new JButton("Avanti");
		btnAvanti.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnAvanti.setIcon(next);
		btnAvanti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAvanti.setBounds(420, 724, 166, 58);
		getContentPane().add(btnAvanti);

		Icon exit = new ImageIcon("img/exit_ico.png");
		JButton btnEsci = new JButton("Annulla Spesa");
		btnEsci.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnEsci.setIcon(exit);
		btnEsci.setBounds(221, 725, 166, 58);
		getContentPane().add(btnEsci);

		JLabel lblVer = new JLabel("Ver. 1.0 - © Tutti i diritti riservati - Developed by Giovanni Trovini ed Enea Marinelli");
		lblVer.setBounds(206, 798, 450, 16);
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setForeground(SystemColor.scrollbar);
		lblVer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		this.getContentPane().add(lblVer);

		textQuantità = new JTextField("1");
		textQuantità.setBounds(594, 190, 72, 28);
		getContentPane().add(textQuantità);
		textQuantità.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 326, 789, 293);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("IMPORTO FINALE : €");
		lblNewLabel.setForeground(new Color(255, 255, 51));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC,
				15));
		lblNewLabel.setBounds(306, 669, 186, 16);
		getContentPane().add(lblNewLabel);

		Icon plus = new ImageIcon("img/quantity.png");
		JButton btnModificaQuantit = new JButton("Modifica quantità");
		btnModificaQuantit.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		btnModificaQuantit.setIcon(plus);
		btnModificaQuantit.setBounds(318, 246, 217, 58);
		getContentPane().add(btnModificaQuantit);

		JLabel lblMassimaleResiduo = new JLabel("MASSIMALE RESIDUO : €");
		lblMassimaleResiduo.setForeground(new Color(255, 255, 0));
		lblMassimaleResiduo.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 15));
		lblMassimaleResiduo.setBounds(294, 639, 196, 16);
		getContentPane().add(lblMassimaleResiduo);

		JLabel lbl_masRes = new JLabel("" + card.getMassimale_res());
		lbl_masRes.setForeground(new Color(255, 255, 51));
		lbl_masRes.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC,
				15));
		lbl_masRes.setBounds(500, 638, 93, 16);
		getContentPane().add(lbl_masRes);

		Icon header_spesa = new ImageIcon ("img/header_spesa.png");
		JLabel label = new JLabel(header_spesa);
		label.setBounds(6, 6, 840, 138);
		getContentPane().add(label);

		Icon products = new ImageIcon ("img/barcode.png");
		JLabel label_1 = new JLabel(products);
		label_1.setBounds(102, 176, 61, 58);
		getContentPane().add(label_1);


		//Pulsante che consente di inserire il prodotto nel carrello e aggiornare l'importo totale

		btnAggiungiAlCarrello.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String barcode = textBarcode.getText();
				String quantita = textQuantità.getText();

				//Creo Carrello con barcode e quantita
				Carrello c=new Carrello(barcode, Integer.parseInt(quantita));


				ControllerSpesa.AddProduct(s,
						c,shop , table);
				lbl_importofinale.setText("" + shop.getImporto_tot());

			}
		});


		btnEliminaProdotto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String barcode = textBarcode.getText();

				Carrello c=new Carrello (barcode);
				ControllerSpesa.DeleteProduct(s, c,
						shop, table);

				lbl_importofinale.setText("" + shop.getImporto_tot());
			}
		});
		btnModificaQuantit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String barcode = textBarcode.getText();
				String quantita = textQuantità.getText();

				Carrello c=new Carrello(barcode,Integer.parseInt(quantita));

				ControllerSpesa.UpdateProduct(s,c, shop, table);
				lbl_importofinale.setText("" + shop.getImporto_tot());

			}

		});

		//Pulsante Avanti
		btnAvanti.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {



				if(shop.getImporto_tot()<=card.getMassimale_res()){

					Spesa_GUI.this.setVisible(false);
					Riepilogo_GUI windows=new Riepilogo_GUI(s,card,shop,u);
					windows.setVisible(true);
 
 
				}

				else {

					JOptionPane.showMessageDialog(null,
							"ATTENZIONE! Il tuo massimale non ti permette di effettuare una spesa del genere! "
									+ "\nRimuovi qualche prodotto dal carrello",
									"Errore Massimale", JOptionPane.INFORMATION_MESSAGE, error);

				}




			}});

		// Pulsante Annulla ed Esci
		btnEsci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int scelta = JOptionPane.showConfirmDialog(Spesa_GUI.this, "Sei di voler annullare la spesa e tornare nella tua area personale?", "Conferma uscita",
						JOptionPane.YES_NO_OPTION, JOptionPane.YES_OPTION, scary);

				switch (scelta) {
				case JOptionPane.YES_OPTION: {
					try {


						ControllerSpesa.cancellaSpesa(s,shop);
						Spesa_GUI.this.setVisible(false);
						PersonalPage_GUI window=new PersonalPage_GUI(s, card, u);
						window.setVisible(true);
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


}