package ecole;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Classe {
    private final String nomClasse;
    private final Annee annee;
    private final ArrayList<Eleve> listeEleve;
    private final HashMap<Matiere, List<Double>> listeNoteClasse = new HashMap<>();


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


    public HashMap<Matiere, List<Double>> getListeNoteClasse() {

        for(Eleve eleve : listeEleve) {
            eleve.getListeNote().forEach( (matiere, note) -> listeNoteClasse.merge(matiere, note, (v1, v2) -> Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toList())));
        }
        return listeNoteClasse;
    }


    public Annee getAnnee() {
        return annee;
    }


    public ArrayList<Eleve> getListeEleve() {
        return listeEleve;
    }


    @Override
    public String toString() {
        return nomClasse + '\'' + ", annee=" + annee + "\n";
    }
}
