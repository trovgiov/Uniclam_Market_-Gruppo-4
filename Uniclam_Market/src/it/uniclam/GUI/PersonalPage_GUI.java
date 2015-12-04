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

	private JFrame frame;
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
	public PersonalPage_GUI(int i, int j,Socket s,double mas,String nome,String cognome,String email) {

		this.scheda = i;
		this.pin = j;
		this.s=s;
		this.mass_res=mas;
		this.nome=nome;
		this.cognome=cognome;
		this.email=email;
		initialize();

	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public double getMass_res() {
		return mass_res;
	}

	public void setMass_res(double mass_res) {
		this.mass_res = mass_res;
	}

	public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	}

	public int getScheda() {
		return scheda;
	}

	public void setScheda(int scheda) {
		this.scheda = scheda;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// frame = new JFrame();
		this.setBounds(100, 100, 539, 351);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setTitle("UNICLAM MARKET - PAGINA PERSONALE");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setLayout(null);

		JLabel lblBenvenutoNellaTua = new JLabel(
				"BENVENUTO NELLA TUA PAGINA PERSONALE,");
		lblBenvenutoNellaTua.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenvenutoNellaTua.setFont(new Font("Lucida Grande", Font.BOLD
				| Font.ITALIC, 14));
		lblBenvenutoNellaTua.setForeground(new Color(255, 255, 255));
		lblBenvenutoNellaTua.setBounds(-3, 6, 392, 17);
		this.getContentPane().add(lblBenvenutoNellaTua);

		JButton btnEffettuaSpesa = new JButton("Effettua la spesa");
		btnEffettuaSpesa.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnEffettuaSpesa.setBounds(40, 89, 165, 38);
		this.getContentPane().add(btnEffettuaSpesa);

		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 102, 0));
		separator.setBounds(40, 23, 445, 12);
		this.getContentPane().add(separator);

		JLabel lblIlMassimaleResiduo = new JLabel(
				"Il massimale residuo è di €: ");
		lblIlMassimaleResiduo.setForeground(new Color(255, 204, 153));
		lblIlMassimaleResiduo.setHorizontalAlignment(SwingConstants.CENTER);
		lblIlMassimaleResiduo.setBounds(85, 64, 250, 17);
		this.getContentPane().add(lblIlMassimaleResiduo);

		JButton btnCambiaEmail = new JButton("Cambia la tua email di accesso");
		btnCambiaEmail.setBounds(40, 199, 238, 38);
		this.getContentPane().add(btnCambiaEmail);

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(40, 159, 445, 12);
		this.getContentPane().add(separator_1);

		JLabel lblAreaSpesa = new JLabel("Area spesa");
		lblAreaSpesa.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		lblAreaSpesa.setVerticalAlignment(SwingConstants.TOP);
		lblAreaSpesa.setHorizontalAlignment(SwingConstants.CENTER);
		lblAreaSpesa.setForeground(new Color(204, 204, 204));
		lblAreaSpesa.setBounds(26, 35, 68, 17);
		this.getContentPane().add(lblAreaSpesa);

		JLabel lblAreaDatiPersonali = new JLabel("Area dati personali");
		lblAreaDatiPersonali.setForeground(new Color(204, 204, 204));
		lblAreaDatiPersonali
		.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		lblAreaDatiPersonali.setBackground(new Color(0, 0, 0));
		lblAreaDatiPersonali.setBounds(26, 168, 125, 16);
		this.getContentPane().add(lblAreaDatiPersonali);

		JButton btnCancellati = new JButton("Cancellati dal sistema");
		btnCancellati.setBounds(320, 199, 165, 38);
		this.getContentPane().add(btnCancellati);

		JButton btnStoricoSpese = new JButton("Storico Spese");
		btnStoricoSpese.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStoricoSpese.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnStoricoSpese.setBounds(320, 92, 165, 38);
		this.getContentPane().add(btnStoricoSpese);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(153, 102, 0));
		separator_2.setBounds(43, 254, 442, 12);
		this.getContentPane().add(separator_2);

		JButton btnEsci = new JButton("Esci");
		btnEsci.setBounds(424, 267, 61, 38);
		this.getContentPane().add(btnEsci);

		JLabel lblVer = new JLabel(
				"Ver. 1.0 - © Tutti i diritti riservati - Developed by Giovanni Trovini ed Enea Marinelli");
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setForeground(SystemColor.scrollbar);
		lblVer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblVer.setBounds(26, 307, 479, 16);
		this.getContentPane().add(lblVer);

		JLabel lblMasRes = new JLabel("");
		lblMasRes.setForeground(Color.WHITE);
		lblMasRes.setBounds(325, 64, 125, 16);
		getContentPane().add(lblMasRes);




		lblMasRes.setText(""+mass_res);


		// Label Utente

		JLabel lblUserEmail = new JLabel("");
		lblUserEmail.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblUserEmail.setForeground(new Color(255, 255, 255));
		lblUserEmail.setBounds(360, 7, 173, 16);
		getContentPane().add(lblUserEmail);
		lblUserEmail.setText(""+nome+ " "+cognome);//+" , "+getEmail());

		// Button Effettua Spesa

		btnEffettuaSpesa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub


				Controller_PersonalPage.effettuaspesa(s, getScheda());
			}
		});

		// Pulsante di uscita
		btnEsci.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
 				JOptionPane.showMessageDialog(PersonalPage_GUI.this,
						"Arrivederci!");
				System.exit(0);
			}
		});

		// Pulsante Cancellazione utente ok
		btnCancellati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int scelta = JOptionPane.showConfirmDialog(
						PersonalPage_GUI.this,
						"Sei sicuro di volerti cancellare dal sistema?",
						"Eliminazione Account", JOptionPane.YES_NO_OPTION);
				switch (scelta) {
			
				case JOptionPane.YES_OPTION:
					 
					Controller_PersonalPage.CancellaUtente(s, email);
					JOptionPane
					.showMessageDialog(null,
							"Ci dispiace per la tua scelta e speriamo di rivederti presto!");
				case JOptionPane.NO_OPTION:
					break;
				}

			}
		});

		
		
		
		
		btnCambiaEmail.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 
				String new_mail = JOptionPane
						.showInputDialog(("Inserisci la nuova mail "));
		Controller_PersonalPage.ChangeEmail(s, email, new_mail);
				

			}
		});

	}
}
