package it.uniclam.UniclamMarket;

import it.uniclam.GUI.*;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

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
	 * Create the application.
	 */
	public Client() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUniclamMarket = new JFrame();
		frmUniclamMarket.getContentPane().setBackground(new Color(102, 0, 0));
		frmUniclamMarket.setTitle("UNICLAM MARKET");
		frmUniclamMarket.setBounds(100, 100, 526, 169);
		frmUniclamMarket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUniclamMarket.getContentPane().setLayout(null);

		JButton btnRegistrati = new JButton("Registrati al sistema");
		btnRegistrati.setBackground(new Color(102, 255, 0));
		btnRegistrati.setBounds(55, 81, 168, 41);
		frmUniclamMarket.getContentPane().add(btnRegistrati);

		JButton btnLogin = new JButton("Entra nel sistema");
		btnLogin.setBounds(305, 81, 162, 41);
		frmUniclamMarket.getContentPane().add(btnLogin);

		JLabel lblNewLabel = new JLabel("BENVENUTI!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		lblNewLabel.setIcon(null);
		lblNewLabel.setBounds(187, 6, 155, 55);
		frmUniclamMarket.getContentPane().add(lblNewLabel);

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
			}
		});

	}
}
