package it.uniclam.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

import it.uniclam.GUI.Login_GUI;
import it.uniclam.GUI.Spesa_GUI;
import it.uniclam.UniclamMarket.Server;

public class ControllerSpesa {

	public static void cancellaSpesa(Socket s,int idSpesa) throws IOException{

		Login_GUI.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		Login_GUI.out = new PrintWriter(s.getOutputStream(), true);

		String req = Server.CANCELLA_SPESA + "/" + idSpesa;
		Login_GUI.out.println(req);

		String line = Login_GUI.in.readLine();

		if(line.contentEquals(Server.SPESA_CANCELLATA)){

			JOptionPane.showMessageDialog(null, "Spesa Cancellata");

		}
		else{
			JOptionPane.showMessageDialog(null,"Attenzione! Spesa non cancellata!");

		}
	}






}


