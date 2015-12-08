package it.uniclam.GUI;

import it.uniclam.Controller.ControllerUtente;
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
import javax.swing.Icon;
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
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setForeground(Color.GRAY);
		this.setBounds(100, 100, 478, 425);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		txtNome = new JTextField();
		txtNome.setForeground(Color.BLACK);
		txtNome.setBounds(154, 119, 206, 26);
		this.getContentPane().add(txtNome);
		txtNome.setColumns(10);

		JLabel lblNome = new JLabel("NOME:");
		lblNome.setForeground(Color.WHITE);
		lblNome.setBounds(102, 124, 61, 16);
		this.getContentPane().add(lblNome);
		this.getContentPane().add(lblNome);

		this.getContentPane().add(lblNome);

		JLabel lblCognome = new JLabel("COGNOME:");
		lblCognome.setForeground(Color.WHITE);
		lblCognome.setBounds(74, 170, 77, 16);
		this.getContentPane().add(lblCognome);

		textCognome = new JTextField();
		textCognome.setBounds(152, 165, 206, 26);
		this.getContentPane().add(textCognome);
		textCognome.setColumns(10);

		JLabel lblEmail = new JLabel("EMAIL:");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setBounds(102, 212, 51, 16);
		this.getContentPane().add(lblEmail);

		textEmail = new JTextField();
		textEmail.setBounds(152, 207, 206, 26);
		this.getContentPane().add(textEmail);
		textEmail.setColumns(10);

		JLabel lblTelfono = new JLabel("TELEFONO:");
		lblTelfono.setForeground(Color.WHITE);
		lblTelfono.setBounds(74, 253, 77, 16);
		this.getContentPane().add(lblTelfono);

		textTelefono = new JTextField();
		textTelefono.setBounds(152, 249, 206, 26);
		this.getContentPane().add(textTelefono);
		textTelefono.setColumns(10);

		JLabel lblImpostareIlMassimale = new JLabel(
				"Impostare il massimale di spesa mensile: €");
		lblImpostareIlMassimale.setForeground(Color.WHITE);
		lblImpostareIlMassimale.setFont(new Font("Lucida Grande", Font.PLAIN,
				15));
		lblImpostareIlMassimale.setBounds(87, 287, 315, 16);
		this.getContentPane().add(lblImpostareIlMassimale);

		textMassimale = new JTextField();
		textMassimale.setBackground(Color.LIGHT_GRAY);
		textMassimale.setBounds(185, 313, 130, 26);
		this.getContentPane().add(textMassimale);
		textMassimale.setColumns(10);

		JButton btnRegistrati = new JButton("REGISTRATI");
		btnRegistrati.setFont(new Font("Lucida Grande",
				Font.BOLD | Font.ITALIC, 13));
		btnRegistrati.setBounds(340, 341, 123, 43);
		this.getContentPane().add(btnRegistrati);

		Icon header = new ImageIcon("img/reg_sistema.png");
		JLabel header1 = new JLabel(header);
		header1.setBounds(19, 8, 444, 104);
		getContentPane().add(header1);

		Icon personalicon = new ImageIcon("img/personal.png");
		JLabel personal = new JLabel(personalicon);
		personal.setBounds(19, 318, 86, 66);
		getContentPane().add(personal);
		setVisible(true);

		btnRegistrati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				String nome = txtNome.getText();
				String cognome = textCognome.getText();
				String email = textEmail.getText();

				String telefono = textTelefono.getText();
				String massimale = textMassimale.getText();

				String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

				// Passo alla funzione di Controller

				ControllerUtente.registrazioneUtente(nome, cognome, email,
						massimale, telefono, emailPattern);


			}
		});
	}
}
