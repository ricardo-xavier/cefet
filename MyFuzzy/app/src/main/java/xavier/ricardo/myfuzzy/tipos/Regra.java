package xavier.ricardo.myfuzzy.tipos;

import java.util.List;

public class Regra {

    private List<AntecedenteConsequente> antecedentes;
    private List<Operador> operadores;
    private AntecedenteConsequente consequente;

    public Regra(List<AntecedenteConsequente> antecedentes, List<Operador> operadores, AntecedenteConsequente consequente) {
        this.antecedentes = antecedentes;
        this.operadores = operadores;
        this.consequente = consequente;
    }

    public List<AntecedenteConsequente> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(List<AntecedenteConsequente> antecedentes) {
        this.antecedentes = antecedentes;
    }

    public List<Operador> getOperadores() {
        return operadores;
    }

    public void setOperadores(List<Operador> operadores) {
        this.operadores = operadores;
    }

    public AntecedenteConsequente getConsequente() {
        return consequente;
    }

    public void setConsequente(AntecedenteConsequente consequente) {
        this.consequente = consequente;
    }
}
