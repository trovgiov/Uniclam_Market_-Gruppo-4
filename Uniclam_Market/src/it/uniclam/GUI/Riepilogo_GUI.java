package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import it.uniclam.Controller.ControllerSpesa;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;

public class Riepilogo_GUI extends JFrame{

	private JTextField textBarcode;
	private int idspesa;
	private JTextField textQuantità;
	Socket s;
	private JTable table;
	private double mass_residuo;
	double importo_totale;
	
	
	public Riepilogo_GUI(int idSpesa, Socket s, double mass_res, double importo_finale2) {
		
		this.idspesa = idSpesa;
		this.s = s;
		this.mass_residuo = mass_res;
		this.importo_totale=importo_finale2;
		initialize();
	}

	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setTitle("Riepilogo della tua spesa");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setLayout(null);

		JLabel lblRiepilogoDellaTua = new JLabel("Riepilogo della tua spesa");
		lblRiepilogoDellaTua.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblRiepilogoDellaTua.setForeground(new Color(255, 255, 255));
		lblRiepilogoDellaTua.setHorizontalAlignment(SwingConstants.CENTER);
		lblRiepilogoDellaTua.setBounds(88, 6, 344, 16);
		this.getContentPane().add(lblRiepilogoDellaTua);

		JButton btnConfermaEPaga = new JButton("CONFERMA e PAGA");
		btnConfermaEPaga.setBounds(28, 431, 151, 49);
		this.getContentPane().add(btnConfermaEPaga);

		JButton btnEsci = new JButton("ESCI e ANNULLA");
		btnEsci.setBounds(345, 431, 151, 49);
		this.getContentPane().add(btnEsci);

		JLabel lblImportoTotale = new JLabel("Importo Totale: € " + importo_totale);
		lblImportoTotale.setFont(new Font("Lucida Grande", Font.ITALIC, 15));
		lblImportoTotale.setForeground(new Color(255, 255, 102));
		lblImportoTotale.setBounds(199, 361, 188, 16);
		this.getContentPane().add(lblImportoTotale);

		JLabel lblNewLabel = new JLabel("Punti Fedeltà accumulati: ");
		lblNewLabel.setForeground(new Color(255, 255, 153));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 15));
		lblNewLabel.setBounds(118, 389, 211, 16);
		this.getContentPane().add(lblNewLabel);

		JLabel lblTotale = new JLabel(" ");
		lblTotale.setBounds(315, 362, 61, 16);
		this.getContentPane().add(lblTotale);
		this.setBounds(100, 100, 523, 527);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Pulsante Annulla ed Esci
				btnEsci.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {

						int scelta = JOptionPane.showConfirmDialog(Riepilogo_GUI.this,
								"Sei di voler annullare la spesa e uscire?", "",
								JOptionPane.YES_NO_OPTION);
						switch (scelta) {
						case JOptionPane.YES_OPTION: {
							try {
								JOptionPane.showMessageDialog(null, "Spesa annullata. Non ti è stato addebitato alcun costo.\n" + "Il tuo massimale residuo è ora: " + mass_residuo, 
										"Esito Spesa", DISPOSE_ON_CLOSE, null);
								ControllerSpesa.cancellaSpesa(s, idspesa);
								System.exit(0);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
							break;

						case JOptionPane.NO_OPTION:
							break;

						}
					}
				});


}
}
