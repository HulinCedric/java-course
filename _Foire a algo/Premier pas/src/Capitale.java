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
	 *Constructeur par dfaut
	 */
	public Capitale(){
		//Ce mot cl appelle le constructeur de la classe mre.  
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
		return super.toString() + "\n \t ==>>" + president + " est son prsident";
	}
	
	/**
	 * @return le nom du prsident
	 */
	public String getPresident() {
		return president;
	}
	
	/**
	 * Dfinit le nom du prsident
	 * @param president
	 */
	public void setPresident(String president) {
		this.president = president;
	}
}
