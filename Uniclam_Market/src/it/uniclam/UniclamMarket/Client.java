package it.uniclam.UniclamMarket;
import it.uniclam.GUI.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Client extends JFrame {
	//private Registrazione_GUI Reg_GUI_Panel;
 

	private JFrame frame;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
 		frame = new JFrame();
		frame.setBounds(100, 100, 526, 347);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.setBounds(88, 159, 117, 29);
		frame.getContentPane().add(btnRegistrati);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(312, 159, 117, 29);
		frame.getContentPane().add(btnLogin);
		
		
		// Action button Registrati
 
		btnRegistrati.addActionListener  (new ActionListener() {
 
 
			@Override
			public void actionPerformed(ActionEvent e) {
				Registrazione_GUI window = new Registrazione_GUI();
				
				//window.setVisible(true);
 				
			}
		});
		
	}
}
