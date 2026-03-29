package pt.ulusofona.aed.deisiworldmeter;


public class Pais {
    int id;
    String alfa2;
    String alfa3;
    String nome;

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2.toUpperCase();    //  LIGA SE AS CIDADES
        this.alfa3 = alfa3.toUpperCase();
        this.nome = nome;

    }

    @Override
    public String toString() {
        return "Pais{" +
                "id=" + id + "| alfa2='" + alfa2  + "| alfa3=" + alfa3  + "| nome=" + nome +
                '}';
    }
}
