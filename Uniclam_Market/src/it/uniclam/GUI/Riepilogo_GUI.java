package it.uniclam.GUI;

import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import it.uniclam.Controller.ControllerSpesa;
import it.uniclam.entity.Scheda;
import it.uniclam.entity.Spesa;
import it.uniclam.entity.Utente;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSeparator;

/**
 * Interfaccia grafica di riepilogo
 * @author Giovanni Trovini - Enea Marinelli
 *
 */
@SuppressWarnings("serial")
public class Riepilogo_GUI extends JFrame{

	Socket s;
	private Spesa shop;
	private Scheda card;
	private Utente u;

	public Riepilogo_GUI(Socket s,Scheda card,Spesa shop,Utente u) {


		this.s=s;
		this.card=card;
		this.shop=shop;
		this.u=u;

		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */

	static Icon ok = new ImageIcon("img/ok.png");
	static Icon confirm = new ImageIcon("img/ok_final.png");
	static Icon no = new ImageIcon("img/no.png");
	static Icon money = new ImageIcon("img/money.png");
	static Icon card_ico = new ImageIcon("img/card.png");
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

		JLabel lblNewLabel = new JLabel("Punti Fedeltà Spesa: ");
		lblNewLabel.setForeground(new Color(255, 255, 0));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(card_ico);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setBounds(120, 243, 281, 46);
		this.getContentPane().add(lblNewLabel);

		JLabel lblTotale = new JLabel(" ");
		lblTotale.setBounds(315, 362, 61, 16);
		this.getContentPane().add(lblTotale);

		JLabel lblImporto = new JLabel(""+ shop.getImporto_tot());
		lblImporto.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
		lblImporto.setForeground(new Color(255, 255, 0));
		lblImporto.setBounds(361, 177, 90, 24);
		getContentPane().add(lblImporto);


		ControllerSpesa.CalcolaPuntiSpesa(s, shop);

		JLabel labelPunti = new JLabel(""+shop.getPunti_spesa());
		labelPunti.setForeground(Color.YELLOW);
		labelPunti.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
		labelPunti.setBounds(385, 258, 90, 17);
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

		// Calcolo il massimale residuo - importo spesa
		double tot=card.getMassimale_res()-shop.getImporto_tot();
		//aggiorno 	campo massimale residuo
		card.setMassimale_res(tot);
		this.setBounds(100, 100, 539, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Pulsante Annulla ed Esci
		btnEsci.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int scelta = JOptionPane.showConfirmDialog(Riepilogo_GUI.this, "Sei di voler annullare la spesa e tornare nella tua area personale ?", "Conferma uscita",
						JOptionPane.YES_NO_OPTION, JOptionPane.YES_OPTION, scary);
				switch (scelta) {
				case JOptionPane.YES_OPTION: {
					try {
						ControllerSpesa.cancellaSpesa(s, shop);

						JOptionPane.showMessageDialog(null,
								"Spesa annullata. Non ti è stato addebitato alcun costo.\n" + "Il tuo massimale residuo è ora: " + card.getMassimale_res(),
								"Esito Spesa", JOptionPane.INFORMATION_MESSAGE, happy);

						Riepilogo_GUI.this.setVisible(false);
						PersonalPage_GUI window=new PersonalPage_GUI(s, card, u);
						window.setVisible(true);
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

				//Aggiorno i punti totali
				ControllerSpesa.CalcoloPuntiTotali(s,shop,card);

				ControllerSpesa.updateMassimale_residuo(s, shop, card,u);
				//aggiorno massimale totale

				JOptionPane.showMessageDialog(null,
						"Spesa inoltrata correttamente." + "\nIl tuo massimale residuo è ora: € " + card.getMassimale_res() + 
						"\nCon questa spesa hai in totale "+card.getPunti_totali() +" punti. \nGrazie per aver scelto Uniclam Market!",
						"Esito Spesa", JOptionPane.INFORMATION_MESSAGE, confirm);

				Riepilogo_GUI.this.setVisible(false);

			}
		});


	}
}