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
import it.uniclam.entity.Scheda;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
		btnConfermaEPaga.setBounds(74, 154, 151, 49);
		this.getContentPane().add(btnConfermaEPaga);

		JButton btnEsci = new JButton("ESCI e ANNULLA");
		btnEsci.setBounds(315, 154, 151, 49);
		this.getContentPane().add(btnEsci);

		JLabel lblImportoTotale = new JLabel("Importo Totale: € ");
		lblImportoTotale.setFont(new Font("Lucida Grande", Font.ITALIC, 15));
		lblImportoTotale.setForeground(new Color(255, 255, 102));
		lblImportoTotale.setBounds(188, 50, 188, 16);
		this.getContentPane().add(lblImportoTotale);


		JLabel lblNewLabel = new JLabel("Punti Fedeltà accumulati: ");
		lblNewLabel.setForeground(new Color(255, 255, 153));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 15));
		lblNewLabel.setBounds(107, 78, 211, 16);
		this.getContentPane().add(lblNewLabel);

		JLabel lblTotale = new JLabel(" ");
		lblTotale.setBounds(315, 362, 61, 16);
		this.getContentPane().add(lblTotale);

		JLabel lblImporto = new JLabel(""+ importo_totale);
		lblImporto.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		lblImporto.setForeground(new Color(255, 255, 0));
		lblImporto.setBounds(331, 50, 90, 17);
		getContentPane().add(lblImporto);

		int punti_spesa=ControllerSpesa.CalcolaPuntiSpesa(s, idspesa);

		JLabel labelPunti = new JLabel(""+punti_spesa);
		labelPunti.setForeground(Color.YELLOW);
		labelPunti.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		labelPunti.setBounds(315, 78, 90, 17);
		getContentPane().add(labelPunti);

		double tot=mass_residuo-importo_totale;
		this.setBounds(100, 100, 523, 270);
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
		
		btnConfermaEPaga.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
 
			int punti_totali=ControllerSpesa.CalcoloPuntiTotali(s, idspesa, punti_spesa);
			int idscheda;
 
			ControllerSpesa.updateMassimale_totale(s, idspesa, tot);
			//aggiorno massimale totale
			JOptionPane.showMessageDialog(null,"Punti Spesa Aggiornati "+punti_totali
					+"\nMassimale Residuo e' di : € "+tot);
			System.exit(0);
			}
		});


	}
}