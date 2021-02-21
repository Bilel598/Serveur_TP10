package ecole;

import java.util.*;

public class Ecole {
    private final ArrayList<Classe> listeDesClasse;
    private static Ecole instance;

    public Ecole(){
        this.listeDesClasse = new ArrayList<>();
        for (Annee annee : Annee.values()){
            for(char alphabet = 'A'; alphabet <='F'; alphabet++ ) {
                listeDesClasse.add(new Classe(annee, String.valueOf(alphabet)));
            }
        }
    }

    public void genererNotes(){
        Random rd = new Random();
        for (Classe classe : this.listeDesClasse) {
            for (Eleve eleve : classe.getListeEleve()) {
                ArrayList<Matiere> matieres = new ArrayList<>(Arrays.asList(Matiere.values()));

                if(classe.getAnnee() == Annee.SIXIEME) {
                    matieres.remove(Matiere.LANGUE_VIVANTE);
                    matieres.remove(Matiere.PHYSIQUE);
                }

                for (Matiere matiere : matieres) {
                    List<Double> notes = new ArrayList<>();
                    Double somme = 0.0;
                    int nombreNote = (matiere == Matiere.MUSIQUE || matiere == Matiere.SPORT) ? 2 : 3;
                    for (int i = 0; i < nombreNote; i++){
                        notes.add(Math.round(Math.random() * (21) * 100.0) / 100.0);
                        somme += notes.get(i);
                    }
                    notes.add(Math.round(somme / notes.size() * 100.0) / 100.0);
                    eleve.setListeNote(matiere, notes);
                }
                if (rd.nextBoolean()) {
                   List<Double> notesOptions = new ArrayList<>();
                   Double sommeOptions = 0.0;
                   for(int i=0; i < 3; i++) {
                       notesOptions.add(Math.round(Math.random() * (21) * 100.0) / 100.0);
                       sommeOptions += notesOptions.get(i);
                   }
                    notesOptions.add(Math.round(sommeOptions / notesOptions.size() * 100.0) / 100.0);
                    eleve.setListeNoteOptions(MatiereOptionnel.ANGLAIS_AVANCE, notesOptions);
                }
                if (rd.nextBoolean()) {
                    List<Double> notesOptions = new ArrayList<>();
                    Double sommeOptions = 0.0;
                    for(int i=0; i < 3; i++) {
                        notesOptions.add(Math.round(Math.random() * (21) * 100.0) / 100.0);
                        sommeOptions += notesOptions.get(i);
                    }
                    notesOptions.add(Math.round(sommeOptions / notesOptions.size() * 100.0) / 100.0);
                    eleve.setListeNoteOptions(rd.nextBoolean() ? MatiereOptionnel.GREC : MatiereOptionnel.LATIN, notesOptions);
                }
            }
            classe.getStats();
        }
    }

    public ArrayList<Classe> getListeDesClasse() {
        return listeDesClasse;
    }

    public static Ecole getInstance() {
        if(Ecole.instance == null) {
            Ecole.instance = new Ecole();
        }
        return instance;
    }
}
