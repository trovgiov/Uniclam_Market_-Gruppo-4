package it.uniclam.entity;

import java.util.Date;

/**
 * Entity Spesa
 * @author GiovanniTrovini
 *
 */
public class Spesa {

	private int idspesa;
	private double importo_tot;
	private Date data_spesa;
	private int punti_spesa;
	private int quantita;
	private int idscheda;

	 
	public int getIdscheda() {
		return idscheda;
	}

	/**
	 * Costruttore per la creazione della spesa
	 * @param data_spesa int
	 * @param idscheda int
	 */

	public Spesa(int idscheda, Date data_spesa) {
		super();
		this.idscheda = idscheda;

		this.data_spesa = data_spesa;
	}

	public void setIdscheda(int idscheda) {
		this.idscheda = idscheda;
	}

	public Spesa(Date data_spesa) {
		super();
		this.data_spesa = data_spesa;
	}

	public Spesa(int idspesa) {
		this.idspesa=idspesa;
	}

	public Spesa() {
		// TODO Auto-generated constructor stub
	}

	public int getIdspesa() {
		return idspesa;
	}

	public void setIdspesa(int idspesa) {
		this.idspesa = idspesa;
	}

	public double getImporto_tot() {
		return importo_tot;
	}

	public void setImporto_tot(double importo_tot) {
		this.importo_tot = importo_tot;
	}

	public Date getData_spesa() {
		return data_spesa;
	}

	public void setData_spesa(Date data_spesa) {
		this.data_spesa = data_spesa;
	}

	public int getPunti_spesa() {
		return punti_spesa;
	}

	public void setPunti_spesa(int punti_spesa) {
		this.punti_spesa = punti_spesa;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

}
