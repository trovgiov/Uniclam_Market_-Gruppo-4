package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import it.uniclam.DAO.SchedaDAOImpl;
import it.uniclam.entity.Utente;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;

public class PersonalPage_GUI extends JFrame{

	private JFrame frame;
   private int scheda;
   private int pin;
	
   double mass_res=0;
 	/**
	 * Create the application.
	 */
	public PersonalPage_GUI(int i, int j) {
		
		this.scheda=i;
		this.pin=j;
		initialize();
		
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
		//frame = new JFrame();
		this.setBounds(100, 100, 500, 351);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setTitle("UNICLAM MARKET - PAGINA PERSONALE");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setLayout(null);
		
		JLabel lblBenvenutoNellaTua = new JLabel("BENVENUTO NELLA TUA PAGINA PERSONALE");
		lblBenvenutoNellaTua.setHorizontalAlignment(SwingConstants.CENTER);
		lblBenvenutoNellaTua.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 14));
		lblBenvenutoNellaTua.setForeground(new Color(255, 255, 255));
		lblBenvenutoNellaTua.setBounds(16, 6, 455, 17);
		this.getContentPane().add(lblBenvenutoNellaTua);
		
		JButton btnEffettuaSpesa = new JButton("Effettua la spesa");
		btnEffettuaSpesa.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnEffettuaSpesa.setBounds(61, 92, 165, 38);
		this.getContentPane().add(btnEffettuaSpesa);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(153, 102, 0));
		separator.setBounds(26, 23, 445, 12);
		this.getContentPane().add(separator);
		
		JLabel lblIlMassimaleResiduo = new JLabel("Il massimale residuo è di €: ");
		lblIlMassimaleResiduo.setForeground(new Color(255, 204, 153));
		lblIlMassimaleResiduo.setHorizontalAlignment(SwingConstants.CENTER);
		lblIlMassimaleResiduo.setBounds(68, 60, 250, 17);
		this.getContentPane().add(lblIlMassimaleResiduo);
		
		JButton btnCambiaEmail = new JButton("Cambia la tua email di accesso");
		btnCambiaEmail.setBounds(25, 199, 238, 29);
		this.getContentPane().add(btnCambiaEmail);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBounds(26, 158, 445, 12);
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
		lblAreaDatiPersonali.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		lblAreaDatiPersonali.setBackground(new Color(0, 0, 0));
		lblAreaDatiPersonali.setBounds(26, 168, 125, 16);
		this.getContentPane().add(lblAreaDatiPersonali);
		
		JButton btnCancellati = new JButton("Cancellati dal sistema");
		btnCancellati.setBounds(294, 198, 165, 29);
		this.getContentPane().add(btnCancellati);
		
		JButton btnModificaMassimale = new JButton("Modifica il massimale");
		btnModificaMassimale.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnModificaMassimale.setBounds(294, 92, 165, 38);
		this.getContentPane().add(btnModificaMassimale);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(new Color(153, 102, 0));
		separator_2.setBounds(26, 254, 442, 12);
		this.getContentPane().add(separator_2);
		
		JButton btnEsci = new JButton("Esci");
		btnEsci.setBounds(410, 264, 61, 29);
		this.getContentPane().add(btnEsci);
		
		JLabel lblVer = new JLabel("Ver. 1.0 - © Tutti i diritti riservati - Developed by Giovanni Trovini ed Enea Marinelli");
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setForeground(SystemColor.scrollbar);
		lblVer.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblVer.setBounds(6, 308, 479, 16);
		this.getContentPane().add(lblVer);
		
		JLabel lblMasRes = new JLabel("");
		lblMasRes.setForeground(Color.WHITE);
		lblMasRes.setBounds(308, 60, 125, 16);
		getContentPane().add(lblMasRes);
		try {
			mass_res=SchedaDAOImpl.getInstance().checkMassimale(getScheda());
			lblMasRes.setText(""+mass_res);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnEffettuaSpesa.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				JOptionPane.showMessageDialog(null, "Scheda : "+getScheda()+"\n Pin : "+getPin());
				
			}
		});
		
		
		
		
		
		//Pulsante di uscita
		btnEsci.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(PersonalPage_GUI.this, "Arrivederci!");
				System.exit(0);
			}
		});
	
	}
}
