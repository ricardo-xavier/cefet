package xavier.ricardo.myfuzzy.tipos;

import java.util.ArrayList;
import java.util.List;

public class Variavel {

    private String nome;
    private int inicio;
    private int fim;
    private List<Termo> termos;
    private int crisp;

    public Variavel(String nome, int inicio, int fim) {
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
        this.termos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFim() {
        return fim;
    }

    public void setFim(int fim) {
        this.fim = fim;
    }

    public List<Termo> getTermos() {
        return termos;
    }

    public void setTermos(List<Termo> valores) {
        this.termos = termos;
    }

    public int getCrisp() {
        return crisp;
    }

    public void setCrisp(int crisp) {
        this.crisp = crisp;
    }
}
