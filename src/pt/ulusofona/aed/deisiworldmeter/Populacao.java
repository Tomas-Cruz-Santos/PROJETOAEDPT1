package pt.ulusofona.aed.deisiworldmeter;

public class Populacao {
    int id;
    int ano;
    long populacaoMasculina;
    long populacaoFeminina;
    double densidade;

    public Populacao(int id, int ano, long populacaoMasculina, long populacaoFeminina, double densidade) {
        this.id = id;
        this.ano = ano;
        this.populacaoMasculina = populacaoMasculina;
        this.populacaoFeminina = populacaoFeminina;
        this.densidade = densidade;
    }

    @Override
    public String toString() {
        return id + " | " + ano + " | " + populacaoMasculina + " | " + populacaoFeminina + " | " + densidade;
    }
}