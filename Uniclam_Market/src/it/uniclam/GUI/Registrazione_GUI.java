package it.uniclam.GUI;

import it.uniclam.UniclamMarket.Client;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.mail.EmailUtility;

import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.SystemColor;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Registrazione_GUI extends JFrame {

	// private JFrame this;
	private JTextField txtNome;
	private JTextField textCognome;
	private JTextField textEmail;
	private JTextField textTelefono;
	private JTextField textMassimale;

	/**
	 * Create the application.
	 */
	public Registrazione_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// this = new JFrame();
		this.setFont(new Font("Adobe Heiti Std", Font.BOLD, 12));
		this.setTitle("UNICLAM MARKET");
		this.getContentPane().setBackground(new Color(153, 0, 0));
		this.getContentPane().setForeground(Color.GRAY);
		this.setBounds(100, 100, 478, 395);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		txtNome = new JTextField();
		txtNome.setForeground(Color.BLACK);
		txtNome.setBounds(151, 55, 206, 26);
		this.getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNome = new JLabel("NOME:");
		lblNome.setForeground(Color.WHITE);
		lblNome.setBounds(99, 60, 61, 16);
		this.getContentPane().add(lblNome);
		this.getContentPane().add(lblNome);

		this.getContentPane().add(lblNome);

		JLabel lblCognome = new JLabel("COGNOME:");
		lblCognome.setForeground(Color.WHITE);
		lblCognome.setBounds(73, 104, 77, 16);
		this.getContentPane().add(lblCognome);

		textCognome = new JTextField();
		textCognome.setBounds(151, 99, 206, 26);
		this.getContentPane().add(textCognome);
		textCognome.setColumns(10);

		JLabel lblEmail = new JLabel("EMAIL:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(101, 148, 51, 16);
		this.getContentPane().add(lblEmail);

		textEmail = new JTextField();
		textEmail.setBounds(151, 143, 206, 26);
		this.getContentPane().add(textEmail);
		textEmail.setColumns(10);

		JLabel lblTelfono = new JLabel("TELEFONO:");
		lblTelfono.setForeground(Color.WHITE);
		lblTelfono.setBounds(73, 191, 77, 16);
		this.getContentPane().add(lblTelfono);

		textTelefono = new JTextField();
		textTelefono.setBounds(151, 187, 206, 26);
		this.getContentPane().add(textTelefono);
		textTelefono.setColumns(10);

		JLabel lblImpostareIlMassimale = new JLabel(
				"Impostare il massimale di spesa mensile: €");
		lblImpostareIlMassimale.setForeground(Color.WHITE);
		lblImpostareIlMassimale.setFont(new Font("Lucida Grande", Font.PLAIN,
				15));
		lblImpostareIlMassimale.setBounds(90, 249, 315, 16);
		this.getContentPane().add(lblImpostareIlMassimale);

		textMassimale = new JTextField();
		textMassimale.setBackground(Color.LIGHT_GRAY);
		textMassimale.setBounds(187, 277, 130, 26);
		this.getContentPane().add(textMassimale);
		textMassimale.setColumns(10);

		JLabel lblNewLabel = new JLabel("Registrazione al Sistema");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(156, 17, 200, 26);
		this.getContentPane().add(lblNewLabel);

		JButton btnRegistrati = new JButton("REGISTRATI");
		btnRegistrati.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		btnRegistrati.setBounds(335, 310, 123, 43);
		this.getContentPane().add(btnRegistrati);
		setVisible(true);
		this.getContentPane().add(lblNewLabel);

		btnRegistrati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {

					String nome = txtNome.getText();
					String cognome = textCognome.getText();
					String email = textEmail.getText();

					String telefono = textTelefono.getText();
					String massimale = textMassimale.getText();

					String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

					// Controllo se i campi sono stati riempiti correttamente
					if (email.matches(emailPattern) && nome.length() != 0
							&& cognome.length() != 0 && telefono.length() != 0
							&& massimale.length() != 0) {

						Socket s = new Socket("localhost", 8888);

						BufferedReader in = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						PrintWriter out = new PrintWriter(s.getOutputStream(),
								true);

						String req = Server.INSERT_UTENTE + "/" + nome + "/"
								+ cognome + "/" + email + "/" + telefono + "/"
								+ massimale;

						out.println(req);
						System.out.println("Email valida");
						System.out.println("Inviato: " + req);

						String line = in.readLine();
						String parts[] = line.split("/");
						String message = parts[0];
						String pin = parts[2];
						String cardnumber = parts[1];
						System.out.println(line);

						if (message.contentEquals("OK")) {
							JOptionPane
									.showMessageDialog(
											Registrazione_GUI.this,
											"Complimenti. La sua carta è stata attivata."
													+ "\nA breve riceverà una mail con il numero carta ed il pin, necessario per l'accesso."
													+ "\nUniclam Market");

							/*
							 * JOptionPane.showMessageDialog(null,
							 * "Dati per l'autenticazione \n" +
							 * "Numero Carta : " + cardnumber + "\n" + "Pin : "
							 * + pin + "\n");
							 */
							

					 
							String subject = "Registrazione Sistema Uniclam Market";

							String message2 = "Benvenuto nel nostro sistema, gentile "
									+ nome
									+ " "
									+ cognome
									+ " \n\n"
									+ "Le comunichiamo che la sua carta fedelta' e' stata attivata !!!"
									+ " \n\n"
									+ "Riepilogo Dati: "
									+ " \n"
									+ "Dati anagrafici : "
									+ nome
									+ " "
									+ cognome
									+ "\n"
									+ "telefono : "
									+ telefono
									+ "\n"
									+ "email : "
									+ email
									+ "\n\n"
									+ "Ecco i suoi dati di accesso da conservare per l'accesso al sistema "
									+ "\n"
									+ "Numero Carta :  "
									+ cardnumber
									+ "\nPin: "
									+ pin
									+ ".\n\n"
									+ "Saluti - Uniclam Market ";

							EmailUtility.sendEmail(EmailUtility.HOST, EmailUtility.PASSWORD, EmailUtility.USER,
									EmailUtility.PASSWORD, email, subject, message2);

						}

					} else {
						JOptionPane.showMessageDialog(Registrazione_GUI.this,
								"Errore inserimento campi");
					}

				} catch (IOException ioe) {

					JOptionPane.showMessageDialog(Registrazione_GUI.this,
							"Error in communication with server!", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (AddressException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
