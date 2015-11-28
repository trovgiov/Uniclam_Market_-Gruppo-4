package it.uniclam.GUI;

import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;
import it.uniclam.mail.EmailUtility;

import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login_GUI extends JFrame {

	private JTextField textNumeroScheda;
	private JPasswordField pin_field;

	public JTextField getTextNumeroScheda() {
		return textNumeroScheda;
	}

	public void setTextNumeroScheda(JTextField textNumeroScheda) {
		this.textNumeroScheda = textNumeroScheda;
	}

	/**
	 * Create the application.
	 */
	public Login_GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// = new JFrame();
		this.setTitle("UNICLAM MARKET -> LOGIN");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setLayout(null);

		JLabel lblAutenticazioneNelSistema = new JLabel(
				"AUTENTICAZIONE NEL SISTEMA");
		lblAutenticazioneNelSistema
		.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutenticazioneNelSistema.setIcon(null);
		lblAutenticazioneNelSistema.setFont(new Font("Lucida Grande",
				Font.BOLD, 15));
		lblAutenticazioneNelSistema.setForeground(Color.WHITE);
		lblAutenticazioneNelSistema.setBounds(90, 6, 341, 36);
		this.getContentPane().add(lblAutenticazioneNelSistema);

		JLabel lblNumeroScheda = new JLabel("Numero Scheda: ");
		lblNumeroScheda.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNumeroScheda.setForeground(Color.WHITE);
		lblNumeroScheda.setBounds(113, 63, 124, 16);
		this.getContentPane().add(lblNumeroScheda);

		textNumeroScheda = new JTextField();
		textNumeroScheda.setBounds(233, 58, 176, 26);
		this.getContentPane().add(textNumeroScheda);
		textNumeroScheda.setColumns(10);

		JLabel lblNewLabel = new JLabel("PIN: ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(191, 109, 40, 16);
		this.getContentPane().add(lblNewLabel);
	 


		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setIcon(null);
		btnLogin.setBackground(Color.GREEN);
		btnLogin.setBounds(297, 153, 84, 36);
		this.getContentPane().add(btnLogin);

		JButton btnRecuperaPin = new JButton("RECUPERA PIN");
		btnRecuperaPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRecuperaPin.setBackground(Color.GREEN);
		btnRecuperaPin.setBounds(171, 153, 104, 36);
		getContentPane().add(btnRecuperaPin);
		
		pin_field = new JPasswordField();
		pin_field.setBounds(233, 103, 176, 26);
		pin_field.setEchoChar('\u25cf'); 
		getContentPane().add(pin_field);
		this.setBackground(new Color(153, 0, 0));
		this.setBounds(100, 100, 535, 229);
 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					String numScheda = textNumeroScheda.getText();

					@SuppressWarnings("deprecation")
					String pino = pin_field.getText();

			int  scheda = Integer.parseInt(numScheda);
					int pin = Integer.parseInt(pino);
					//
					boolean result;
					result = UtenteDAOImpl.getInstance().login(scheda, pin);

					if (result == true) {

						Socket s = new Socket("localhost", 8888);

						BufferedReader in = new BufferedReader(
								new InputStreamReader(s.getInputStream()));
						PrintWriter out = new PrintWriter(s.getOutputStream(),
								true);

						String req = Server.LOGIN_UTENTE + "/" + scheda + "/"
								+ pin;

						out.println(req);

						String line = in.readLine();
						System.out.println(line);
 
						
						if(line.contentEquals("login_Ok")){
							
							Scheda card = new Scheda (scheda,pin);
							
							
							
							
							//GUI
							PersonalPage_GUI personalwindow = new PersonalPage_GUI(card.getIdScheda(),card.getPin());
							personalwindow.setVisible(true);
	 						
 							
							
							
						}
						
						
						
						
						
 						
					 
						

						
					
						
					} else {
						JOptionPane.showMessageDialog(Login_GUI.this,
								"User O Password errate");

					}

				}

				catch (IOException | SQLException ioe) {

					JOptionPane.showMessageDialog(Login_GUI.this,
							"Error in communication with server!", "Error",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});

		btnRecuperaPin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String email_recover=JOptionPane.showInputDialog("Inserisca la mail per il recupero pin");
				try {
					SchedaDAOImpl.getInstance().recovery_pin(email_recover);
				} catch (SQLException | MessagingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
}
