//
//  Capitale.java
//  Debut
//
//  Created by TrAsHeUr on 01/07/09.
//  Copyright 2009 Domicile. All rights reserved.
//

public class Capitale extends Ville {
	
	private String president;
	
	/**
	 *Constructeur par défaut
	 */
	public Capitale(){
		//Ce mot clé appelle le constructeur de la classe mère.  
		super();
		president = "aucun";
	}
	
	/**
	 * Constructeur d'initialisation de capitale
	 */
	public Capitale(String nom, int hab, String pays, String president){
		super(nom, hab, pays);
		this.president = president;
	}
	
	/**
	 * Retourne la description de la capitale
	 * @return description captiale
	 */
	public String toString(){
		return super.toString() + "\n \t ==>>" + president + " est son président";
	}
	
	/**
	 * @return le nom du président
	 */
	public String getPresident() {
		return president;
	}
	
	/**
	 * Définit le nom du président
	 * @param president
	 */
	public void setPresident(String president) {
		this.president = president;
	}
}
