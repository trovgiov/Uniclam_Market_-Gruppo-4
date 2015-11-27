package it.uniclam.entity;

public class Utente {
 private String nome;
private String cognome;
private String email;
private String telefono;
private double massimale;
public Utente(String nome, String cognome, String email, String telefono,
		double massimale) {
	super();
	 
	this.nome = nome;
	this.cognome = cognome;
	this.email = email;
	this.telefono = telefono;
	this.massimale = massimale;
}
 
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public Utente(String email) {
	super();
	this.email = email;
}

public String getCognome() {
	return cognome;
}
public void setCognome(String cognome) {
	this.cognome = cognome;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getTelefono() {
	return telefono;
}
public void setTelefono(String telefono) {
	this.telefono = telefono;
}
public double getMassimale() {
	return massimale;
}
public void setMassimale(double massimale) {
	this.massimale = massimale;
}
}
