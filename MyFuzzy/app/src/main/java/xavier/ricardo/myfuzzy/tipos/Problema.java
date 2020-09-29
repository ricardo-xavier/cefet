package xavier.ricardo.myfuzzy.tipos;

import java.util.List;

public class Problema {

    private String nome;
    private List<Variavel> variaveis;
    private List<Regra> regras;
    private double resultado;

    public Problema(String nome, List<Variavel> variaveis, List<Regra> regras) {
        this.nome = nome;
        this.variaveis = variaveis;
        this.regras = regras;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Variavel> getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(List<Variavel> variaveis) {
        this.variaveis = variaveis;
    }

    public List<Regra> getRegras() {
        return regras;
    }

    public void setRegras(List<Regra> regras) {
        this.regras = regras;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

}
