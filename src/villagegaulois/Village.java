package villagegaulois;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}
	
	public static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			for (int i=0; i<nbEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			for (int i=0; i<etals.length; i++) {
				if (!etals[i].isEtalOccupe()) return i;
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit) {
			Etal[] temp = new Etal[etals.length];
			int len = 0;
			for (int i=0; i<temp.length; i++) {
				Etal etal = etals[i];
				if (etal.isEtalOccupe() && etal.contientProduit(produit)) {
					temp[i] = etal;
					len++;
				}
			}
//			System.arraycopy(temp, 0, temp, 0, len);
//			System.out.println(temp.length);
			return temp;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			for (int i=0; i<etals.length; i++) {
				Etal etal = etals[i];
				if (etal.getVendeur()==gaulois) return etal;
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalLibre = 0;
			for (Etal etal : etals) {
				if (etal.isEtalOccupe()) chaine.append(etal.afficherEtal());
				else nbEtalLibre++;
			}
			chaine.append("Il reste " + nbEtalLibre + " étals non utilisés dans le marché.");
			return chaine.toString();
		}
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		
		int noEtalLibre = marche.trouverEtalLibre();
		if (noEtalLibre==-1) {
			chaine.append("Il n'y a plus d'étal libre.\n");
			return chaine.toString();
		} else {
			marche.utiliserEtal(noEtalLibre, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (noEtalLibre+1) + ".\n");
			return chaine.toString();
		}
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		
		if (etalsAvecProduit[0]==null) chaine.append("\nIl n'y a pas de vendeur qui propose des " + produit + "\n");
		else {
			chaine.append("\nLes vendeurs qui proposent des " + produit +" sont :\n");
			for (Etal etal : etalsAvecProduit) {
				if (etal!=null) chaine.append("- " + etal.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marché du village \"" + nom + "\" possède plusieurs étals :\n" + marche.afficherMarche());
		return chaine.toString();
	}
	
	
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}