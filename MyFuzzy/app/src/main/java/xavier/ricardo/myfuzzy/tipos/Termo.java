package xavier.ricardo.myfuzzy.tipos;

public class Termo {

    private String nome;
    private Integer a;
    private Integer b;
    private Integer c;
    private Integer d;
    private double pertinencia;

    public Termo(String nome, Integer a, Integer b, Integer c) {
        this(nome, a, b, c, null);
    }

    public Termo(String nome, Integer a, Integer b, Integer c, Integer d) {
        this.nome = nome;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public String toString() {
        return "Termo{" +
                "nome='" + nome + '\'' +
                ", a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }

    public Integer getD() {
        return d;
    }

    public void setD(Integer d) {
        this.d = d;
    }

    public double getPertinencia() {
        return pertinencia;
    }

    public void setPertinencia(double pertinencia) {
        this.pertinencia = pertinencia;
    }
}
