package it.uniclam.GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

public class Spesa_GUI {

 private JFrame frmUniclamMarket;
 private JTextField textIdProdotto;
 private JTable table;

 /**
 * Launch the application.
 */
 public static void main(String[] args) {
 EventQueue.invokeLater(new Runnable() {
 public void run() {
 try {
 Spesa_GUI window = new Spesa_GUI();
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
 public Spesa_GUI() {
 initialize();
 }

 /**
 * Initialize the contents of the frame.
 */
 private void initialize() {
 frmUniclamMarket = new JFrame();
 frmUniclamMarket.setTitle("UNICLAM MARKET - Effettua la tua spesa");
 frmUniclamMarket.getContentPane().setBackground(new Color(153, 0, 0));
 frmUniclamMarket.setBounds(100, 100, 562, 495);
 frmUniclamMarket.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frmUniclamMarket.getContentPane().setLayout(null);
 
 JTextPane txtpnBenvenutoNelNostro = new JTextPane();
 txtpnBenvenutoNelNostro.setForeground(new Color(255, 255, 255));
 txtpnBenvenutoNelNostro.setBackground(new Color(153, 0, 0));
 txtpnBenvenutoNelNostro.setToolTipText("\\");
 txtpnBenvenutoNelNostro.setEditable(false);
 txtpnBenvenutoNelNostro.setFont(new Font("Lucida Grande", Font.BOLD, 13));
 txtpnBenvenutoNelNostro.setText("Benvenuto nel nostro supermercato!\nPuoi aggiungere i prodotti che vuoi al carrello, utilizzando il lettore di codice a barre prelevato dalla sua base di carica. ");
 txtpnBenvenutoNelNostro.setBounds(46, 6, 475, 54);
 frmUniclamMarket.getContentPane().add(txtpnBenvenutoNelNostro);
 
 JLabel lblProdotto = new JLabel("Prodotto:");
 lblProdotto.setFont(new Font("Lucida Grande", Font.BOLD, 13));
 lblProdotto.setForeground(new Color(255, 255, 255));
 lblProdotto.setHorizontalAlignment(SwingConstants.CENTER);
 lblProdotto.setBounds(48, 86, 72, 16);
 frmUniclamMarket.getContentPane().add(lblProdotto);
 
 textIdProdotto = new JTextField();
 textIdProdotto.setBackground(Color.LIGHT_GRAY);
 textIdProdotto.setBounds(132, 81, 171, 26);
 frmUniclamMarket.getContentPane().add(textIdProdotto);
 textIdProdotto.setColumns(10);
 
 JLabel lblQuantit = new JLabel("Quantità: ");
 lblQuantit.setFont(new Font("Lucida Grande", Font.BOLD, 13));
 lblQuantit.setHorizontalAlignment(SwingConstants.CENTER);
 lblQuantit.setForeground(Color.WHITE);
 lblQuantit.setBounds(360, 86, 72, 16);
 frmUniclamMarket.getContentPane().add(lblQuantit);
 
 JSeparator separator = new JSeparator();
 separator.setBounds(6, 55, 550, 12);
 frmUniclamMarket.getContentPane().add(separator);
 
 JComboBox comboBox = new JComboBox();
 comboBox.setToolTipText("Selezionare la quantità desiderata");
 comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
 comboBox.setMaximumRowCount(10);
 comboBox.setBounds(441, 81, 58, 33);
 frmUniclamMarket.getContentPane().add(comboBox);
 
 table = new JTable();
 table.setBounds(46, 141, 466, 289);
 frmUniclamMarket.getContentPane().add(table);
 }
}