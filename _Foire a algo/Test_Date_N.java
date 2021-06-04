/** Annee 2008_2009 - Module Java
*
* Class Date - Module de tests unitaires (cas nominaux)
* 
* @author Hulin Cedric
* @version	1.0.0
**/


import java.util.Date;

public class Test_Date_N 
{
	public static void main (String[] args) 
	{
        /**
         * Ce qui est déprécié a été remplacé a 
         * partir de la JDK 1.1 par Calendar ou GregorianCalendar
         */
		Tests.Begin("Class Date", "1.0.0");

			Tests.Design("Constructeur", 3);
	         
				Tests.Case("Constructeur par defaut");
				{	
					/**
					 * Date du jour
					 * format de sorti
					 * Sun Oct 11 13:55:49 CEST 2009
					 */
					Date m = new Date();
	         	
					Tests.Unit(new Date(), m);
				}
	         
				Tests.Case("Constructeur normal (année, mois, jour)");
				{  	
					/**
					 * Déprécié depuis la JDK 1.1
					 * @param année  (année + 1900)
					 * @param mois
					 * @param jour
					 */
					Date m1= new Date(2009-1900, 11, 01);
					Tests.Unit(new Date(2009-1900, 11, 01), m1);
				}
				
				Tests.Case("Constructeur normal (année, mois, jour, heure, minute)");
				{
					/**
					 * Déprécié depuis la JDK 1.1
					 * @param année  (année + 1900)
					 * @param mois
					 * @param jour
					 * @param heure
					 * @param minute
					 */
					Date m2= new Date(2009-1900, 11, 01, 23, 02);
					Tests.Unit(new Date(2009-1900, 11, 01, 23, 02), m2);
				}
				
				Tests.Case("Constructeur normal (année, mois, jour, heure, minute, seconde)");
				{
					/**
					 * Déprécié depuis la JDK 1.1
					 * @param année  (année + 1900)
					 * @param mois
					 * @param jour
					 * @param heure
					 * @param minute
					 * @param seconde
					 */
					Date m3= new Date(2009-1900, 11, 01, 23, 02, 12);
					Tests.Unit(new Date(2009-1900, 11, 01, 23, 02, 12), m3);
				}
				
				Tests.Case("Constructeur normal (milliseconde)");
				{
					/**
					 * Date calculer par rapport au miliseconde
					 * écoulé depuis le 1er Janvier 1970
					 * @param milliseconde (1 Janvier 1970 + milliseconde)
					 */
					Date m4 = new Date(System.currentTimeMillis());
					Tests.Unit(new Date(System.currentTimeMillis()), m4);
				}
				
				Tests.Case("Constructeur normal (String)");
				{
					/**
					 * Déprécié depuis la JDK 1.1
					 * @param date (complete ou incomplete) format String (anglais)
					 */
					Date m5= new Date("01 January 1988 11:34:55");
					Tests.Unit(new Date("01 January 1988 11:34:55"), m5);
				}
				
			Tests.Design("Methode de Accession", 3);
			
				Tests.Case("Methode getTime");
				{
					Date m = new Date();
					Long l= new Long(m.getTime());
					
					Tests.Unit(l, new Long(m.getTime()));
				}
			
			Tests.Design("Methode de Modification", 3);

				Tests.Case("Methode setTime");
				{
					Date m = new Date(100000);
					Long l= new Long(System.currentTimeMillis());
									
					Tests.Unit(m.toString(), m.toString());
					
					m.setTime(l.longValue());

					Tests.Unit(m.toString(), m.toString());
				}
				
			Tests.Design("Methode de Comparaison", 3);

				Tests.Case("Methode de after");
				{
					Date m1= new Date();
					Date m2= new Date(1000000);
					
					/**
					 * after
					 * vrai si la date est apres la date donnee en parametre
					 */
					Tests.Unit(new Boolean(true), new Boolean(m1.after(m2)));
				}
				
				Tests.Case("Methode de before");
				{
					Date m1= new Date();
					Date m2= new Date(1000000);
					
					/**
					 * before
					 * vrai si la date est avant la date donnee en parametre
					 */
					Tests.Unit(new Boolean(false), new Boolean(m1.before(m2)));
				}
				
				Tests.Case("Methode de compareTo");
				{
					Date m1= new Date();
					Date m2= new Date(1000000);
					
					/**
					 * compareTo
					 * si la date est apres la date donnee en parametre = 1
					 * si la date est avant la date donnee en parametre = -1
					 * si la date est la meme = 0
					 */
					Tests.Unit(new Integer(1), new Integer(m1.compareTo(m2)));
					Tests.Unit(new Integer(-1), new Integer(m2.compareTo(m1)));
					Tests.Unit(new Integer(0), new Integer(m1.compareTo(m1)));
				}
				
			Tests.Design("Methode herite de Object", 3);

				Tests.Case("Methode equals");
				{
					Date m1= new Date();
					Date m2= new Date();
					Date m3= new Date(1000000);
					
					Tests.Unit(new Boolean(true), new Boolean(m1.equals(m2)));
					Tests.Unit(new Boolean(false), new Boolean(m1.equals(m3)));
				}
				
				Tests.Case("Methode clone");
				{
					Date m1= new Date();
					Date m2= (Date)m1.clone();
					
					Tests.Unit(new Boolean(true), new Boolean(m1.equals(m2)));
				}
				
				Tests.Case("Methode hashCode");
				{
					Date m1= new Date();
					
					Tests.Unit(new Integer(m1.hashCode()), new Integer(m1.hashCode()));
				}
				
				Tests.Case("Methode toString");
				{
					Date m1= new Date();
					
					Tests.Unit(m1.toString(), m1.toString());
				}
				
				/**
				 * Autres Methode d'Accession deprecie
				 * 
				 * int getDate();
				 * int getDay();
				 * int getHours();
				 * int getMinutes();
				 * int getMonth();
				 * int getSeconds();
				 * int getTimezoneOffset();
				 * int getYear();
				 * 
				 * Autres Methode de Modification deprecie
				 * 
				 * void setDate(int);
				 * void setHours(int);
				 * void setMinutes(int);
				 * void setMonth(int);
				 * void setSeconds(int);
				 * void setYear(int);
				 * 
				 * Autre Methode d'affichage deprecie
				 * 
				 * 	String toGMTString();
				 * 
				 * Autres Methode deprecie
				 * 
				 * static long parse(String);
				 * static long UTC(int year, int month, int date, int hrs, int min, int sec);
				 */

		Tests.End();
	}
}
