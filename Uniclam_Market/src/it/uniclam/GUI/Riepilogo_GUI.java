package it.uniclam.GUI;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import it.uniclam.Controller.ControllerSpesa;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;

public class Riepilogo_GUI extends JFrame{

	private int idspesa;
	Socket s;
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

	static Icon ok = new ImageIcon("img/ok.png");
	static Icon confirm = new ImageIcon("img/ok_final.png");
	static Icon no = new ImageIcon("img/no.png");
	static Icon money = new ImageIcon("img/money.png");
	static Icon card = new ImageIcon("img/card.png");
	static Icon scary = new ImageIcon("img/scary.png");
	static Icon happy = new ImageIcon("img/happy.png");

	private void initialize() {
		this.setTitle("Riepilogo della tua spesa");
		this.getContentPane().setBackground(new Color(102, 0, 0));
		this.getContentPane().setLayout(null);

		JButton btnConfermaEPaga = new JButton("CONFERMA e PAGA");
		btnConfermaEPaga.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnConfermaEPaga.setIcon(ok);
		btnConfermaEPaga.setBounds(25, 301, 201, 58);
		this.getContentPane().add(btnConfermaEPaga);

		JButton btnEsci = new JButton("ESCI e ANNULLA");
		btnEsci.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnEsci.setBounds(333, 301, 181, 58);
		btnEsci.setIcon(no);
		this.getContentPane().add(btnEsci);

		JLabel lblImportoTotale = new JLabel("Importo Totale: € ");
		lblImportoTotale.setHorizontalAlignment(SwingConstants.CENTER);
		lblImportoTotale.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblImportoTotale.setIcon(money);
		lblImportoTotale.setForeground(new Color(255, 255, 0));
		lblImportoTotale.setBounds(120, 160, 256, 59);
		this.getContentPane().add(lblImportoTotale);


		JLabel lblNewLabel = new JLabel("Punti Fedeltà accumulati: ");
		lblNewLabel.setForeground(new Color(255, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(card);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(149, 243, 281, 46);
		this.getContentPane().add(lblNewLabel);

		JLabel lblTotale = new JLabel(" ");
		lblTotale.setBounds(315, 362, 61, 16);
		this.getContentPane().add(lblTotale);

		JLabel lblImporto = new JLabel(""+ importo_totale);
		lblImporto.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
		lblImporto.setForeground(new Color(255, 255, 0));
		lblImporto.setBounds(361, 177, 90, 24);
		getContentPane().add(lblImporto);

		int punti_spesa=ControllerSpesa.CalcolaPuntiSpesa(s, idspesa);

		JLabel labelPunti = new JLabel(""+punti_spesa);
		labelPunti.setForeground(Color.YELLOW);
		labelPunti.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
		labelPunti.setBounds(425, 258, 90, 17);
		getContentPane().add(labelPunti);

		Icon header_riepilogue = new ImageIcon("img/riepilogo.png");
		JLabel label = new JLabel(header_riepilogue);
		label.setBounds(6, 6, 527, 134);
		getContentPane().add(label);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 139, 527, 17);
		getContentPane().add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 225, 527, 17);
		getContentPane().add(separator_1);

		double tot=mass_residuo-importo_totale;
		this.setBounds(100, 100, 539, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Pulsante Annulla ed Esci
		btnEsci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int scelta = JOptionPane.showConfirmDialog(Riepilogo_GUI.this, "Sei di voler annullare la spesa e uscire?", "Conferma uscita",
						JOptionPane.YES_NO_OPTION, JOptionPane.YES_OPTION, scary);
				switch (scelta) {
				case JOptionPane.YES_OPTION: {
					try {

						JOptionPane.showMessageDialog(null,
								"Spesa annullata. Non ti è stato addebitato alcun costo.\n" + "Il tuo massimale residuo è ora: " + mass_residuo,
								"Esito Spesa", JOptionPane.INFORMATION_MESSAGE, happy);

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

				ControllerSpesa.updateMassimale_totale(s, idspesa, tot);
				//aggiorno massimale totale
				
				JOptionPane.showMessageDialog(null,
						"Spesa inoltrata correttamente. \n" + "Il tuo massimale residuo è ora: " + mass_residuo + 
						"\nCon questa spesa hai accumulato: "+punti_totali +" punti. \n Grazie per aver scelto Uniclam Market!",
						"Esito Spesa", JOptionPane.INFORMATION_MESSAGE, confirm);
				
				
				/*JOptionPane.showMessageDialog(null,"Punti Spesa Aggiornati "+punti_totali
						+"\nMassimale Residuo e' di : € "+tot);*/
				System.exit(0);
			}
		});


	}
}