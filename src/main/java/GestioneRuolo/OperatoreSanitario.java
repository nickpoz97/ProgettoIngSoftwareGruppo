package GestioneRuolo;

public class OperatoreSanitario implements Ruolo{
   private String ruolo ="";
    @Override
    public String show() {
        return getRuolo();
    }
    public OperatoreSanitario(String ruolo) {this.ruolo=ruolo;}

    @Override
    public String getRuolo() {
        return ruolo;
    }
}
