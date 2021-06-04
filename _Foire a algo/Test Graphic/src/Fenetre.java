//
//  Fenetre.java
//  Test Graphic
//
//  Created by TrAsHeUr on 05/07/09.
//  Copyright 2009 Domicile. All rights reserved.
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fenetre extends JFrame{
	
	private Panneau pan = new Panneau();

	public Fenetre(){
		
		//Définit un titre pour votre fenêtre
		//
		this.setTitle("Ma premiere fenetre java");
		
		//Définit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
		//
		this.setSize(100, 150);
		
		//Nous allons maintenant dire à notre objet de se positionner au centre
		//
		this.setLocationRelativeTo(null);
		
		//Ferme-toi lorsqu'on clique sur "Fermer" !
		//
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Empêcher le redimensionnement de la fenêtre
		//
		//this.setResizable(false);
		
		// Faire que votre fenêtre soit toujours au premier plan
		//
		//this.setAlwaysOnTop(true);
		
		// Retirer les contours et les boutons de contrôles
		//
		//this.setUndecorated(true);
		
		
		
		/*//Instanciation d'un objet JPanel
		//
		JPanel pan = new JPanel();
		
		//Définition de sa couleur de fond
		//
		pan.setBackground(Color.GREEN);        
		
		//On prévient notre JFrame que ce sera notre JPanel qui sera son contentPane
		//
		this.setContentPane(pan);*/
		
		
		this.setContentPane(pan);

		
		
		
		// Rendre visible la fenetre
		//
		this.setVisible(true);
		
		go();

	}
	
	private void go(){
		
		for(int i = -50; i < pan.getWidth(); i++)
		{
			int x = pan.getPosX(), y = pan.getPosY();
			x++;
			y++;
			pan.setPosX(x);
			pan.setPosY(y);
			pan.repaint();  
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}       
}
