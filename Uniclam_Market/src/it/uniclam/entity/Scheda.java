package it.uniclam.entity;

public class Scheda {

	private int idScheda;
	private int pin;

	/**
	 * costruttore scheda con idscheda e pin. Viene Usato dopo che l'utente,
	 * effettua il login
	 * 
	 * @param idScheda
	 * @param pin
	 */
	public Scheda(int idScheda, int pin) {
		super();
		this.idScheda = idScheda;
		this.pin = pin;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	/**
	 * Costruttore per creare la scheda
	 * 
	 * @param punti_totali
	 * @param massimale_res
	 */
	public Scheda(double punti_totali, double massimale_res) {
		super();
		this.punti_totali = punti_totali;
		this.massimale_res = massimale_res;
	}

	private double punti_totali;
	private double massimale_res;

	public int getIdScheda() {
		return idScheda;
	}

	public void setIdScheda(int idScheda) {
		this.idScheda = idScheda;
	}

	public double getPunti_totali() {
		return punti_totali;
	}

	public void setPunti_totali(double punti_totali) {
		this.punti_totali = punti_totali;
	}

	public double getMassimale_res() {
		return massimale_res;
	}

	public void setMassimale_res(double massimale_res) {
		this.massimale_res = massimale_res;
	}

}
