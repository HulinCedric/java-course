//
// Annee 2006_2007 - Module Java - Fichiers de donnees / Data.java
//
// Services de gestion - Classe Data - Version 1.0.0
//
// Version 1.0.0	: version initiale
//
/**
 *
 * La classe Data fournit deux services destinés à simplifier et à 
 * uniformiser la gestion des fichiers de donnees (objets Java).
 *
 * Les services fournis sont :
 *
 * load    	: charger un fichier de donnees depuis le repertoire courant,
 * store	: enregistrer un fichier de donnees dans le repertoire courant.
 *
 * @author Alain Thuaire - Universite de Nice/IUT - Departement informatique
**/

import java.util.*;
import java.io.*;

abstract public class Data {
/**
 *
 * La methode store enregistre dans un fichier du repertoire courant l'objet
 * fourni en premier parametre. Le fichier resultant est cree avec l'extension
 * .data. Le nom du fichier est forme automatiquement de la facon suivante : 
 * p2-p3.data, où p2 et p3 designent les deux derniers parametres effectifs. 
 * 
**/
   public static boolean store (Object data, 
                                String name  ,
                                String version) {                          	
   String origine;
   String nomFichier;      
   FileOutputStream f= null;
   ObjectOutputStream out= null;
   
      // Controler l'existence de la donnee
      //
      if (data == null) return false;
      
      // Construire le nom du fichier de donnees
      //
      nomFichier= name + "-" + version + ".data";
      
      // Construire un fichier logique et le fichier physique associe
      //
      try {f= new FileOutputStream(nomFichier);}
      catch (Exception e) {return false;}
      
      // Construire un flux de sortie base sur le fichier logique
      //
      try {out= new ObjectOutputStream(f);}
      catch (Exception e) {return false;}
      
      // Serialiser l'objet dans le flux de sortie
      //
      try{out.writeObject(data);}
      catch (Exception e) {return false;}
      return true;
   }
   
   /**
 *
 * La methode load charge le contenu d'un fichier de donnees depuis le 
 * repertoire courant. L'objet resultant est la valeur de retour. Le fichier 
 * origine possede obligatoirement l'extension .data. Le nom du fichier 
 * est forme automatiquement de la facon suivante : p1-p2.data, ou p1 et p2 
 * designent les deux parametres effectifs. 
 * 
**/
   public static Object load (String  name   ,    
                              String  version) {
   String origine;
   String nomFichier;      
   FileInputStream f= null;
   ObjectInputStream in= null;
   Object resultat;
   
      // Construire le nom du fichier source de la configuration
      //
      nomFichier= name + "-" + version + ".data";
      
      // Construire un fichier logique correspondant
      //
      try {f= new FileInputStream(nomFichier);}
      catch (Exception e) {return null;}
      
      // Construire un flux d'entree base sur le fichier logique
      //
      try {in= new ObjectInputStream(f);}
      catch (Exception e) {return null;}
      
      // Acquerir et deserialiser le flux d'entree
      //
      try{resultat=in.readObject();}
      catch (Exception e) {return null;}
          
      return resultat;
   }
}
