//
//  Debut.java
//  Debut
//
//  Created by TrAsHeUr on 01/07/09.
//  Copyright (c) 2009 Domicile. All rights reserved.
//
import java.util.Scanner;

public class Debut {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Ville v = new Ville();
		Ville v1 = new Ville("marseille", 1236, "france");       
		Ville v2 = new Ville("rio", 321654, "brésil");
		Capitale cap = new Capitale("Paris", 654987, "France", "Sarko");
        
		System.out.println("\n\n"+v1);
		System.out.println(v);
		System.out.println(v2+"\n\n");
		System.out.println(v1.comparer(v2));
		System.out.println("\n"+cap);

	}
	
}
