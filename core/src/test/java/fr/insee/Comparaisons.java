package fr.insee;

import org.junit.jupiter.api.Test;

public class Comparaisons {

	@Test
	void test() {
		// Strings and Boxed types should be compared using "equals()"
		String chaine1 = new String("toto");
		String chaine2 = new String("toto");
		// compare les objets avec la définition de leur méthode equals
		// pour les String, comparaison lettre à lettre
		boolean compar1 = chaine1.equals(chaine2);
		System.out.println("comparaison avec equals : " + compar1); // true
		// compare les références des objets
		boolean compar2 = chaine1 == chaine2;
		System.out.println("comparaison avec == : " + compar2); // false

		// pour que la comparaison == donne true,
		// il faut que les 2 objets fassent référence à la même instance
		String chaine3 = "toto";
		String chaine4 = chaine3;
		boolean compar3 = chaine3 == chaine4;
		System.out.println("comparaison même objet avec == : " + compar3); // true
	}

}
