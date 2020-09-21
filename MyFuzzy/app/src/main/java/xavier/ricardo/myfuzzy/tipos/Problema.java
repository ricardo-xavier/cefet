package xavier.ricardo.myfuzzy.tipos;

import java.util.List;

public class Problema {

    private String nome;
    private List<Variavel> variaveis;

    public Problema(String nome, List<Variavel> variaveis) {
        this.nome = nome;
        this.variaveis = variaveis;
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



}
