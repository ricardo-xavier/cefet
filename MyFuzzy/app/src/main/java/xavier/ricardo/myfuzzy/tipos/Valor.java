package xavier.ricardo.myfuzzy.tipos;

public class Valor {

    private String nome;
    private int inicio;
    private int maximo;
    private int fim;

    public Valor(String nome, int inicio, int maximo, int fim) {
        this.nome = nome;
        this.inicio = inicio;
        this.maximo = maximo;
        this.fim = fim;
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

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getFim() {
        return fim;
    }

    public void setFim(int fim) {
        this.fim = fim;
    }


}
