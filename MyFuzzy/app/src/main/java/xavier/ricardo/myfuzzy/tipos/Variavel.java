package xavier.ricardo.myfuzzy.tipos;

import java.util.ArrayList;
import java.util.List;

public class Variavel {

    private String nome;
    private int inicio;
    private int fim;
    private List<Valor> valores;

    public Variavel(String nome, int inicio, int fim) {
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
        this.valores = new ArrayList<>();
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

    public List<Valor> getValores() {
        return valores;
    }

    public void setValores(List<Valor> valores) {
        this.valores = valores;
    }

}
