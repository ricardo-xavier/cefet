package xavier.ricardo.myfuzzy.tipos;

public class AntecedenteConsequente {

    private Variavel variavel;
    private Termo termo;

    public AntecedenteConsequente(Variavel variavel, Termo termo) {
        this.variavel = variavel;
        this.termo = termo;
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

    public Termo getTermo() {
        return termo;
    }

    public void setTermo(Termo valor) {
        this.termo = termo;
    }
}
