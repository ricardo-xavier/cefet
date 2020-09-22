package xavier.ricardo.myfuzzy.tipos;

public class AntecedenteConsequente {

    private Variavel variavel;
    private Valor valor;

    public AntecedenteConsequente(Variavel variavel, Valor valor) {
        this.variavel = variavel;
        this.valor = valor;
    }

    public Variavel getVariavel() {
        return variavel;
    }

    public void setVariavel(Variavel variavel) {
        this.variavel = variavel;
    }

    public Valor getValor() {
        return valor;
    }

    public void setValor(Valor valor) {
        this.valor = valor;
    }
}
