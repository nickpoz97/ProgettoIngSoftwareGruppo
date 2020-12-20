package GestioneRuolo;

public class Admin implements Ruolo {
private String ruolo = "";
    @Override
    public String show() {
        return getRuolo();
    }
    public Admin(String ruolo ) {
        this.ruolo=ruolo;
    }

    @Override
    public String getRuolo() {
        return ruolo;
    }
}
