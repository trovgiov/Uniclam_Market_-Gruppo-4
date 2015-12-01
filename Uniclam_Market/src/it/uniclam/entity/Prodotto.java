package it.uniclam.entity;

public class Prodotto {

	
	private String barcode;
	private String nome_prodotto;
	private double costo;
	private int punti ;
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getNome_prodotto() {
		return nome_prodotto;
	}
	public void setNome_prodotto(String nome_prodotto) {
		this.nome_prodotto = nome_prodotto;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public int getPunti() {
		return punti;
	}
	public void setPunti(int punti) {
		this.punti = punti;
	}
 
	
}
