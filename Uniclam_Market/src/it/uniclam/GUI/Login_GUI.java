package it.uniclam.GUI;

import it.uniclam.Controller.ControllerLogin;
import it.uniclam.Controller.ControllerUtente;
import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.DAO.UtenteDAOImpl;
import it.uniclam.UniclamMarket.Server;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Utente;
import it.uniclam.mail.EmailUtility;

import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login_GUI extends JFrame {

	protected static final int INPUT_MESSAGE = 0;
	private JTextField textNumeroScheda;
	private JPasswordField pin_field;

	public JTextField getTextNumeroScheda() {
		return textNumeroScheda;
	}

	public void setTextNumeroScheda(JTextField textNumeroScheda) {
		this.textNumeroScheda = textNumeroScheda;
	}

	public static BufferedReader in;
	public static PrintWriter out;

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

		JLabel lblNumeroScheda = new JLabel("Numero Scheda: ");
		lblNumeroScheda.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNumeroScheda.setForeground(Color.WHITE);
		lblNumeroScheda.setBounds(229, 173, 124, 16);
		this.getContentPane().add(lblNumeroScheda);

		textNumeroScheda = new JTextField("45");
		textNumeroScheda.setBounds(349, 168, 176, 26);
		this.getContentPane().add(textNumeroScheda);
		textNumeroScheda.setColumns(10);

		JLabel lblNewLabel = new JLabel("PIN: ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(307, 219, 40, 16);
		this.getContentPane().add(lblNewLabel);

		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 14));
		btnLogin.setIcon(null);
		btnLogin.setBackground(Color.GREEN);
		btnLogin.setBounds(428, 278, 97, 46);
		this.getContentPane().add(btnLogin);

		JButton btnRecuperaPin = new JButton("RECUPERA PIN");
		btnRecuperaPin.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		btnRecuperaPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRecuperaPin.setBackground(Color.GREEN);
		btnRecuperaPin.setBounds(257, 298, 115, 26);
		getContentPane().add(btnRecuperaPin);

		pin_field = new JPasswordField("3566");
		pin_field.setBounds(349, 213, 176, 26);
		pin_field.setEchoChar('\u25cf');
		getContentPane().add(pin_field);

		Icon header_login= new ImageIcon("img/login_header.png");
		JLabel header = new JLabel(header_login);
		header.setBounds(6, 6, 543, 136);
		getContentPane().add(header);

		Icon login_lab= new ImageIcon("img/login.png");
		JLabel login = new JLabel(login_lab);
		login.setBounds(16, 154, 183, 136);
		getContentPane().add(login);
		this.setBackground(new Color(153, 0, 0));
		this.setBounds(100, 100, 555, 366);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * ActionListener per il Login
		 */

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String numScheda = textNumeroScheda.getText();

				@SuppressWarnings("deprecation")
				String pino = pin_field.getText();

				// scheda con id scheda e pin
				
				Scheda card=new Scheda(Integer.parseInt(numScheda),Integer.parseInt(pino));
				
				
				
				ControllerLogin.authenticate(card);
				Login_GUI.this.setVisible(false);
			

			}

		});

		btnRecuperaPin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Icon email = new ImageIcon("img/email.png");
				
				 
				String email_recover = (String) JOptionPane.showInputDialog(null, "Inserisca la mail per il recupero pin", "Inserire Email", JOptionPane.INFORMATION_MESSAGE,email, null, null);

				Utente u =new Utente(email_recover);
				
				ControllerLogin.forgot_pin(u);





			}
		});
	}
}
