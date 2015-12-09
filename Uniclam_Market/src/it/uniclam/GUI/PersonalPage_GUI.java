package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import it.uniclam.Controller.Controller_PersonalPage;
import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Utente;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.JTextField;

public class PersonalPage_GUI extends JFrame {

 	private int scheda;
	private int pin;
	private String nome;
	private String cognome;
	private String email;
 	Socket s;
  
	private double mass_res = 0;

	/**
	 * Create the application.
	 */
	public PersonalPage_GUI(int i, int j, Socket s, double mas, String nome,
			String cognome, String email ) {

		this.scheda = i;
		this.pin = j;
		this.s = s;
		this.mass_res = mas;
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
 		initialize();

	}

	// Icone 
	Icon card_icon = new ImageIcon("img/card.png");
 	Icon spesa = new ImageIcon("img/cart.png");
 	Icon settings = new ImageIcon("img/settings.png");
 	Icon delete = new ImageIcon("img/trash.png");
 	Icon exit = new ImageIcon("img/exit.png");
 	Icon pers_icon = new ImageIcon("img/personal_area.png");
 	Icon money_icon = new ImageIcon("img/money.png");
 	Icon scary = new ImageIcon("img/scary.png");
 	Icon sad = new ImageIcon("img/sad.png");
 	Icon email_icon = new ImageIcon("img/email.png");
 	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame = new JFrame();
		this.setBounds(100, 100, 553, 569);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("UNICLAM MARKET - PAGINA PERSONALE");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setLayout(null);

		JLabel lblBenvenutoNellaTua = new JLabel(
				"PAGINA PERSONALE DI");
		lblBenvenutoNellaTua.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenvenutoNellaTua.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 14));
		lblBenvenutoNellaTua.setForeground(new Color(255, 255, 255));
		lblBenvenutoNellaTua.setBounds(61, 133, 299, 17);
		this.getContentPane().add(lblBenvenutoNellaTua);

		
		JButton btnEffettuaSpesa = new JButton("Effettua la spesa");
		btnEffettuaSpesa.setIcon(spesa);
		btnEffettuaSpesa.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnEffettuaSpesa.setBounds(166, 298, 220, 79);
		this.getContentPane().add(btnEffettuaSpesa);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 102, 0));
		separator.setBounds(11, 152, 531, 12);
		this.getContentPane().add(separator);

		JLabel lblIlMassimaleResiduo = new JLabel(
				"Il massimale residuo è di €: ");
		lblIlMassimaleResiduo.setForeground(new Color(255, 204, 153));
		lblIlMassimaleResiduo.setHorizontalAlignment(SwingConstants.CENTER);
		lblIlMassimaleResiduo.setBounds(142, 187, 250, 17);
		this.getContentPane().add(lblIlMassimaleResiduo);

		
		
		
		JButton btnCambiaEmail = new JButton("Cambia la tua email di accesso");
		btnCambiaEmail.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		btnCambiaEmail.setIcon(settings);
		btnCambiaEmail.setBounds(29, 389, 250, 68);
		this.getContentPane().add(btnCambiaEmail);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(11, 374, 531, 12);
		this.getContentPane().add(separator_1);
		
		
		JButton btnCancellati = new JButton("Cancellati \ndal sistema");
		btnCancellati.setFont(new Font("Lucida Grande", Font.BOLD, 11));
		btnCancellati.setIcon(delete);
		btnCancellati.setBounds(316, 389, 210, 68);
		this.getContentPane().add(btnCancellati);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(153, 102, 0));
		separator_2.setBounds(11, 468, 527, 9);
		this.getContentPane().add(separator_2);

		
		JButton btnEsci = new JButton("Esci");
		btnEsci.setIcon(exit);
		btnEsci.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 12));
		btnEsci.setBounds(456, 476, 86, 43);
		this.getContentPane().add(btnEsci);

		JLabel lblVer = new JLabel(
				"Ver. 1.0 - © Tutti i diritti riservati - Developed by Giovanni Trovini ed Enea Marinelli");
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setForeground(SystemColor.scrollbar);
		lblVer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblVer.setBounds(29, 520, 479, 16);
		this.getContentPane().add(lblVer);

		JLabel lblMasRes = new JLabel("");
		lblMasRes.setForeground(Color.WHITE);
		lblMasRes.setBounds(378, 187, 125, 16);
		getContentPane().add(lblMasRes);

		lblMasRes.setText("" + mass_res);

		// Label Utente

		JLabel lblUserEmail = new JLabel("");
		lblUserEmail.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblUserEmail.setForeground(new Color(255, 255, 255));
		lblUserEmail.setBounds(304, 133, 173, 16);
		getContentPane().add(lblUserEmail);
		lblUserEmail.setText("" + nome + " " + cognome);// +" , "+getEmail());
		
		JLabel lblITuoiPunti = new JLabel("I tuoi punti fedeltà sono : ");
		lblITuoiPunti.setHorizontalAlignment(SwingConstants.CENTER);
		lblITuoiPunti.setForeground(new Color(255, 204, 153));
		lblITuoiPunti.setBounds(138, 254, 250, 17);
		getContentPane().add(lblITuoiPunti);
		
		
		int punti=Controller_PersonalPage.Show_punti_scheda(s, scheda);
		JLabel label_ptTotali = new JLabel(""+punti);
		label_ptTotali.setForeground(Color.WHITE);
		label_ptTotali.setBounds(358, 255, 125, 16);
		getContentPane().add(label_ptTotali);
		
		
		JLabel header = new JLabel(pers_icon);
		header.setBounds(27, 11, 490, 110);
		getContentPane().add(header);
		
		
		JLabel money = new JLabel(money_icon);
		money.setBounds(97, 168, 69, 60);
		getContentPane().add(money);
		
		
		JLabel card = new JLabel(card_icon);
		card.setBounds(97, 235, 69, 60);
		getContentPane().add(card);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(new Color(153, 102, 0));
		separator_3.setBounds(11, 228, 531, 12);
		getContentPane().add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(new Color(153, 102, 0));
		separator_4.setBounds(11, 289, 531, 12);
		getContentPane().add(separator_4);

		// Button Effettua Spesa

		btnEffettuaSpesa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Controller_PersonalPage.effettuaspesa(s, scheda, mass_res);
				
			}
		});

		// Pulsante di uscita
		
		btnEsci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(PersonalPage_GUI.this,
						 "Arrivederci!", "Logout", JOptionPane.INFORMATION_MESSAGE, exit);
				System.exit(0);
			}
		});

		// Pulsante Cancellazione utente ok
		btnCancellati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				
				int scelta = JOptionPane.showConfirmDialog(PersonalPage_GUI.this, "Sei sicuro di volerti cancellare dal sistema?", 
						"Eliminazione Account", JOptionPane.YES_NO_OPTION, JOptionPane.YES_OPTION, scary);
				
				switch (scelta) {

				case JOptionPane.YES_OPTION:

					Controller_PersonalPage.CancellaUtente(s, email);
					JOptionPane.showMessageDialog(PersonalPage_GUI.this,
							 "Ci dispiace per la tua scelta! \nSperiamo di rivederti presto!!", "Account Cancellato correttamente", JOptionPane.INFORMATION_MESSAGE, sad);
					
					
				
				case JOptionPane.NO_OPTION:
					break;
				}

			}
		});

		btnCambiaEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String new_mail = (String) JOptionPane.showInputDialog(null, "Inserisca la mail per il recupero pin", "Inserire Email", JOptionPane.INFORMATION_MESSAGE,email_icon, null, null);
				Controller_PersonalPage.ChangeEmail(s, email, new_mail);

			}
		});

	}
}
