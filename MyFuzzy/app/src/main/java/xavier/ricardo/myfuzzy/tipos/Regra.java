package xavier.ricardo.myfuzzy.tipos;

import java.util.List;

import xavier.ricardo.myfuzzy.MainActivity;
import xavier.ricardo.myfuzzy.R;

public class Regra {

    private List<AntecedenteConsequente> antecedentes;
    private List<Operador> operadores;
    private AntecedenteConsequente consequente;
    private String exprAntecedente;
    private String exprConsequente;
    private int id;

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
                String _se = MainActivity.getStringResources().getString(R.string.se).toUpperCase() + " ";
                String _eh = " " + MainActivity.getStringResources().getString(R.string.eh).toUpperCase() + " ";
                String _ou = " " + MainActivity.getStringResources().getString(R.string.ou).toUpperCase() + " ";
                String _e  = " " + MainActivity.getStringResources().getString(R.string.e).toUpperCase() + " ";
                expr.append(_se + antecedente.getVariavel().getNome() + _eh + antecedente.getTermo().getNome());
                Operador operador = operadores.get(a);
                if (operador != null) {
                    expr.append(operador == Operador.OR ? _ou : _e);
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
            String _entao = MainActivity.getStringResources().getString(R.string.entao).toUpperCase() + " ";
            String _eh = " " + MainActivity.getStringResources().getString(R.string.eh).toUpperCase() + " ";
            exprConsequente = _entao + consequente.getVariavel().getNome() + _eh + consequente.getTermo().getNome();
        }
        return exprConsequente;
    }

    public void setExprConsequente(String exprConsequente) {
        this.exprConsequente = exprConsequente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
