package ecole;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Classe {
    private String nomClasse;
    private Annee annee;
    private ArrayList<Eleve> listeEleve;
    private HashMap<Matiere, List<Double>> listeNoteClasse = new HashMap<>();


    public Classe(Annee annee, String nomClasse){
        this.nomClasse = annee + nomClasse;
        this.annee = annee;
        this.listeEleve = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Eleve eleve = new Eleve(this.nomClasse);
            listeEleve.add(eleve);
        }

    }

    public void getStats(){
        getListeNoteClasse();
        System.out.println("Pour la classe de " + nomClasse + ": ");
        listeNoteClasse.forEach((key, value) -> {
            DoubleSummaryStatistics stats = listeNoteClasse.get(key).stream().mapToDouble((x) -> x).summaryStatistics();
            System.out.println(key.toString() + ": " + " Minimum: " + stats.getMin() + "; Valeur moyenne: " + stats.getAverage() + "; Maximum" + stats.getMax() + "; Nombre total de notes: " + stats.getCount());

        });
    }

    public Double getMoyenneMatiere(Matiere matiere) {
        DoubleSummaryStatistics stats = listeNoteClasse.get(matiere).stream().mapToDouble((x) -> x).summaryStatistics();
        return stats.getAverage();
    }

    public HashMap<Matiere, List<Double>> getListeNoteClasse() {

        for(Eleve eleve : listeEleve) {
            eleve.getListeNote().forEach( (matiere, note) -> {
                listeNoteClasse.merge(matiere, note, (v1, v2) -> Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toList()));
            });
        }
        return listeNoteClasse;
    }

    public void setListeNoteClasse(HashMap<Matiere, List<Double>> listeNoteClasse) {
        this.listeNoteClasse = listeNoteClasse;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public ArrayList<Eleve> getListeEleve() {
        return listeEleve;
    }

    public void setListeEleve(ArrayList<Eleve> listeEleve) {
        this.listeEleve = listeEleve;
    }

    @Override
    public String toString() {
        return nomClasse + '\'' + ", annee=" + annee + "\n";
    }
}
