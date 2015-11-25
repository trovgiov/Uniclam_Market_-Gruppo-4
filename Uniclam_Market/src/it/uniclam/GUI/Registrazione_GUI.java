 package it.uniclam.GUI;

import it.uniclam.UniclamMarket.Client;
import it.uniclam.UniclamMarket.Server;
 


import java.awt.EventQueue;

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

	//private JFrame this;
	private JTextField txtNome;
	private JTextField textCognome;
	private JTextField textEmail;
	private JTextField textTelefono;
	private JTextField textMassimale;



	/**
	 * Create the application.
	 */
	public Registrazione_GUI( ) {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//this = new JFrame();
		this.setFont(new Font("Adobe Heiti Std", Font.BOLD, 12));
		this.setTitle("UNICLAM MARKET");
		this.getContentPane().setBackground(new Color(204, 255, 255));
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
		lblNome.setBounds(99, 60, 61, 16);
  		this.getContentPane().add(lblNome);
 		this.getContentPane().add(lblNome);
 		
 		this.getContentPane().add(lblNome);
		
 		JLabel lblCognome = new JLabel("COGNOME:");
		lblCognome.setBounds(73, 104, 77, 16);
		this.getContentPane().add(lblCognome);
		
		textCognome = new JTextField();
		textCognome.setBounds(151, 99, 206, 26);
		this.getContentPane().add(textCognome);
		textCognome.setColumns(10);
		
		JLabel lblEmail = new JLabel("EMAIL:");
		lblEmail.setBounds(101, 148, 51, 16);
		this.getContentPane().add(lblEmail);
		
		textEmail = new JTextField();
		textEmail.setBounds(151, 143, 206, 26);
		this.getContentPane().add(textEmail);
		textEmail.setColumns(10);
		
		JLabel lblTelfono = new JLabel("TELEFONO:");
		lblTelfono.setBounds(73, 191, 77, 16);
		this.getContentPane().add(lblTelfono);
		
		textTelefono = new JTextField();
		textTelefono.setBounds(151, 187, 206, 26);
		this.getContentPane().add(textTelefono);
		textTelefono.setColumns(10);
		
		JLabel lblImpostareIlMassimale = new JLabel("Impostare il massimale di spesa mensile:");
		lblImpostareIlMassimale.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblImpostareIlMassimale.setBounds(90, 249, 298, 16);
		this.getContentPane().add(lblImpostareIlMassimale);
		
		textMassimale = new JTextField();
 		textMassimale.setBounds(179, 277, 130, 26);
		this.getContentPane().add(textMassimale);
		textMassimale.setColumns(10);
 
		
		JLabel lblNewLabel = new JLabel("Registrazione al Sistema");
		lblNewLabel.setBounds(163, 17, 158, 26);
  		this.getContentPane().add(lblNewLabel);
		
		JButton btnRegistrati = new JButton("REGISTRATI");
		btnRegistrati.setBounds(339, 324, 117, 29);
		this.getContentPane().add(btnRegistrati);
		setVisible(true);
 		this.getContentPane().add(lblNewLabel);
 		
 		
 
		
 		btnRegistrati.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					Socket s = new Socket("localhost", 8888);
					
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					
				 	 String nome= txtNome.getText();
				 	 String cognome=textCognome.getText();
				 	 String email=textEmail.getText();
				 	 String telefono=textTelefono.getText();
				 	 String massimale=textMassimale.getText();
				 	 
				 	 
					String req=Server.INSERT_AMICI+"/"+nome+"/"+cognome+"/"+email+"/"+telefono+"/"+massimale;
					 
 
					
					out.println(req);
					
					System.out.println("Inviato: " + req);
					
					String line = in.readLine();
					System.out.println(line);
					/*
					if(line.contentEquals("OK")){
						JOptionPane.showMessageDialog(Registrazione_GUI.this, "I dati da lei Inseriti sono : \n"+
					      "Nome : "+txtNome.getText()+
							"\n"+	"Cognome : "+textCognome.getText()+
								"\n"+"Email : "+textEmail.getText()+
								"\n"+"Telefono : "+textTelefono.getText()+
								"\n"+"Massimale : "+textMassimale.getText());

					}
				
				 else {
						JOptionPane.showMessageDialog(Registrazione_GUI.this, "Error in communication with server!", "Error", JOptionPane.ERROR_MESSAGE);

					}
					*/
					s.close();
				} catch (IOException ioe){
					JOptionPane.showMessageDialog(Registrazione_GUI.this, "Error in communication with server!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
 }
