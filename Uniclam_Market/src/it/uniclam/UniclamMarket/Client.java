package it.uniclam.UniclamMarket;

import it.uniclam.GUI.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

 
@SuppressWarnings("serial")
public class Client extends JFrame {
	// private Registrazione_GUI Reg_GUI_Panel;

	private JFrame frmUniclamMarket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					window.frmUniclamMarket.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creo l'applicazione.
	 */
	public Client() {
		initialize();

	}

	
	/**
	 * Inizializzo il contenuto del frame
	 */
	private void initialize() {
		frmUniclamMarket = new JFrame();
		frmUniclamMarket.getContentPane().setBackground(new Color(102, 0, 0));
		frmUniclamMarket.setTitle("UNICLAM MARKET");
		frmUniclamMarket.setBounds(100, 100, 494, 261);
		frmUniclamMarket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUniclamMarket.getContentPane().setLayout(null);

		Icon registration = new ImageIcon("img/registration.png");
		JButton btnRegistrati = new JButton("Registrati al sistema");
		btnRegistrati.setIcon(registration);
		btnRegistrati.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		btnRegistrati.setBounds(23, 162, 196, 54);
		frmUniclamMarket.getContentPane().add(btnRegistrati);

		Icon login = new ImageIcon("img/login1.png");
		JButton btnLogin = new JButton("Entra nel sistema");
		btnLogin.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 13));
		btnLogin.setIcon(login);
		btnLogin.setBounds(291, 162, 183, 54);
		frmUniclamMarket.getContentPane().add(btnLogin);
		
		//Welcome icon
		Icon welcome = new ImageIcon("img/welcome.png");
		
		JLabel label = new JLabel(welcome);
		label.setBounds(17, 13, 465, 129);
		frmUniclamMarket.getContentPane().add(label);

		// Action button Registrati

		btnRegistrati.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				Registrazione_GUI window = new Registrazione_GUI();

				// window.setVisible(true);

			}
		});

		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Login_GUI loginwindows = new Login_GUI();
				loginwindows.setVisible(true);
				frmUniclamMarket.setVisible(false);
			}
		});

	}
}
