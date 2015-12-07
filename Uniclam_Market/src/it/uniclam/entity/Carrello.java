package it.uniclam.entity;

public class Carrello {

	
	private String nome_prodotto;
	private int quantita;
	private double prezzo_finale;
	public String getNome_prodotto() {
		return nome_prodotto;
	}
	public void setNome_prodotto(String nome_prodotto) {
		this.nome_prodotto = nome_prodotto;
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
	@Override
	public String toString() {
		return "\n\tnome_prodotto: " + nome_prodotto
				+ "\n\tquantita: " + quantita + "\n\tprezzo_finale: "
				+ prezzo_finale + "\n";
	}
	
	
}
