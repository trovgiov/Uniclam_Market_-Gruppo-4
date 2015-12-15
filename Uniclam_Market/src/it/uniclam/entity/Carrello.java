package it.uniclam.entity;

public class Carrello {

	private String barcode;
	private String nome_prodotto;
	private int quantita;
	private double prezzo_finale;
	public String getNome_prodotto() {
		return nome_prodotto;
	}
	public void setNome_prodotto(String nome_prodotto) {
		this.nome_prodotto = nome_prodotto;
	}
	
	
	/**
	 * Costruttore personalizzato per le operazioni di CRUD prodotto
	 * @param barcode
	 * @param quantita
	 */
	public Carrello(String barcode, int quantita) {
		super();
		this.barcode = barcode;
		this.quantita = quantita;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getQuantita() {
		return quantita;
	}
	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}
	public double getPrezzo_finale() {
		return prezzo_finale;
	}
	public void setPrezzo_finale(double prezzo_finale) {
		this.prezzo_finale = prezzo_finale;
	}
	public Carrello(String nome_prodotto, int quantita, double prezzo_finale) {
		super();
		this.nome_prodotto = nome_prodotto;
		this.quantita = quantita;
		this.prezzo_finale = prezzo_finale;
	}
	/**
	 * Costruttore personalizzato per eliminazione prodotto dalla lista spesa
	 * @param barcode barcode prodotto
	 */
	public Carrello(String barcode) {
		this.barcode=barcode;
		// TODO Auto-generated constructor stub
	}
	public Carrello() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "\n\tnome_prodotto: " + nome_prodotto
				+ "\n\tquantita: " + quantita + "\n\tprezzo_finale: "
				+ prezzo_finale + "\n";
	}
}
