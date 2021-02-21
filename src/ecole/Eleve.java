package ecole;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Eleve {

    private final String nom;
    private final String prenom;
    private final String classe;
    private final HashMap<Matiere, List<Double>> listeNote = new HashMap<>();
    private final HashMap<MatiereOptionnel, List<Double>> listeNoteOptions = new HashMap<>();

    private double moyenneGenerale;

    public Eleve(String classe) {
        Faker faker = new Faker();
        this.nom = faker.name().lastName();
        this.prenom = faker.name().firstName();
        this.classe = classe;
    }

    public HashMap<Matiere, List<Double>> getListeNote() {
        return listeNote;
    }

    public void setListeNote(Matiere matiere, List<Double> notes) {
        listeNote.put(matiere, notes);
        Double sommeMoyenne = 0.0;
        for(Map.Entry matiereEntry : listeNote.entrySet()) {
            sommeMoyenne += listeNote.get(matiereEntry.getKey()).get(listeNote.get(matiereEntry.getKey()).size() - 1);
        }
        this.moyenneGenerale = this.classe.equals("SIXIEME") ?  sommeMoyenne/8 : sommeMoyenne/10;
    }

    public void setListeNoteOptions(MatiereOptionnel mat, List<Double> notes) {
        listeNoteOptions.put(mat, notes);
        Double moyenne;

        for(Map.Entry matiereEntry : listeNoteOptions.entrySet()) {
            moyenne = listeNoteOptions.get(matiereEntry.getKey()).get(listeNoteOptions.get(matiereEntry.getKey()).size() - 1);
            this.moyenneGenerale += 0.1 * (10 - moyenne);
        }
    }

    public void afficherEleve(){
        System.out.println("\n" + nom + ' ' + prenom + " en " + classe);
        for(Map.Entry matiereEntry : listeNote.entrySet()) {
            System.out.println(matiereEntry.getKey().toString() + matiereEntry.getValue() + ", ");
        }
        for(Map.Entry matiereEntry : listeNoteOptions.entrySet()) {
            System.out.println(matiereEntry.getKey().toString() + matiereEntry.getValue() + ", ");
        }
        System.out.println("Moyenne générale: " + moyenneGenerale);
    }
}
