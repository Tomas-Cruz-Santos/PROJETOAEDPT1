package pt.ulusofona.aed.deisiworldmeter;

public class Cidade {
    String alfa2;   // LIGA SE AOS PAISES
    String cidade;
    int regiao;
    double populacao;
    double latitude;
    double longitude;

    public Cidade(String alfa2, String cidade, int regiao, Double populacao, Double latitude, Double longitude) {
        this.alfa2 = alfa2.toUpperCase();
        this.cidade = cidade;
        this.regiao = regiao;
        this.populacao = populacao;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return cidade + " | " + alfa2 + " | " + regiao + " | " +
                (int) populacao + " | (" + latitude + "," + longitude + ")";
    }
}
