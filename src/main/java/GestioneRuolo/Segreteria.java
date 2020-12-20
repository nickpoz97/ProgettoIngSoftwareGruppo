package GestioneRuolo;

public class Segreteria implements Ruolo {
    private String ruolo ="";

    public String show() {
        return getRuolo();
    }
    public Segreteria(String ruolo) {this.ruolo=ruolo;}


    public String getRuolo() {
        return ruolo;
    }
}


