package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Login_GUI {

	private JFrame frmUniclamMarket;
	private JTextField textNumeroScheda;
	private JTextField textField;


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
		frmUniclamMarket = new JFrame();
		frmUniclamMarket.setTitle("UNICLAM MARKET -> LOGIN");
		frmUniclamMarket.getContentPane().setBackground(new Color(102, 0, 0));
		frmUniclamMarket.getContentPane().setLayout(null);
		
		JLabel lblAutenticazioneNelSistema = new JLabel("AUTENTICAZIONE NEL SISTEMA");
		lblAutenticazioneNelSistema.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutenticazioneNelSistema.setIcon(new ImageIcon(Login_GUI.class.getResource("/com/sun/javafx/scene/control/skin/caspian/dialog-information.png")));
		lblAutenticazioneNelSistema.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblAutenticazioneNelSistema.setForeground(Color.WHITE);
		lblAutenticazioneNelSistema.setBounds(90, 6, 341, 36);
		frmUniclamMarket.getContentPane().add(lblAutenticazioneNelSistema);
		
		JLabel lblNumeroScheda = new JLabel("Numero Scheda: ");
		lblNumeroScheda.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNumeroScheda.setForeground(Color.WHITE);
		lblNumeroScheda.setBounds(113, 63, 124, 16);
		frmUniclamMarket.getContentPane().add(lblNumeroScheda);
		
		textNumeroScheda = new JTextField();
		textNumeroScheda.setBounds(233, 58, 176, 26);
		frmUniclamMarket.getContentPane().add(textNumeroScheda);
		textNumeroScheda.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("PIN: ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(191, 109, 40, 16);
		frmUniclamMarket.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(233, 104, 95, 26);
		frmUniclamMarket.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setIcon(new ImageIcon(Login_GUI.class.getResource("/com/sun/javafx/scene/control/skin/caspian/fxvk-capslock-button.png")));
		btnLogin.setBackground(Color.GREEN);
		btnLogin.setBounds(418, 149, 84, 36);
		frmUniclamMarket.getContentPane().add(btnLogin);
		frmUniclamMarket.setBackground(new Color(153, 0, 0));
		frmUniclamMarket.setBounds(100, 100, 535, 229);
		frmUniclamMarket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
