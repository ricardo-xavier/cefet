package xavier.ricardo.myfuzzy.tipos;

import java.util.List;

public class Regra {

    private List<AntecedenteConsequente> antecedentes;
    private List<Operador> operadores;
    private AntecedenteConsequente consequente;
    private String exprAntecedente;
    private String exprConsequente;

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

    public String getExprAntecedente() {
        if ((exprAntecedente == null) && (antecedentes != null) && (antecedentes.size() > 0)) {
            StringBuilder expr = new StringBuilder();
            for (int a=0; a<antecedentes.size(); a++) {
                AntecedenteConsequente antecedente = antecedentes.get(a);
                expr.append("SE " + antecedente.getVariavel().getNome() + " é " + antecedente.getTermo().getNome());
                Operador operador = operadores.get(a);
                if (operador != null) {
                    expr.append(operador == Operador.OR ? " OU " : " E ");
                }
            }
            exprAntecedente = expr.toString();
        }
        return exprAntecedente;
    }

    public void setExprAntecedente(String exprAntecedente) {
        this.exprAntecedente = exprAntecedente;
    }

    public String getExprConsequente() {
        if ((exprConsequente == null) && (consequente != null)) {
            exprConsequente = " ENTÃO " + consequente.getVariavel().getNome() + " é " + consequente.getTermo().getNome();
        }
        return exprConsequente;
    }

    public void setExprConsequente(String exprConsequente) {
        this.exprConsequente = exprConsequente;
    }


}
