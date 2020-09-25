package xavier.ricardo.myfuzzy.tipos;

public class AntecedenteConsequente {

    private Variavel variavel;
    private Termo valor;

    public AntecedenteConsequente(Variavel variavel, Termo valor) {
        this.variavel = variavel;
        this.valor = valor;
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

    public Termo getValor() {
        return valor;
    }

    public void setValor(Termo valor) {
        this.valor = valor;
    }
}
