package pt.ulusofona.aed.deisiworldmeter;


public class Pais {
    int id;
    String alfa2;
    String alfa3;
    String nome;
    int numeroIndicadores;

    public void adicionarIndicador() {
        numeroIndicadores++;
    }

    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2.toUpperCase();    //  LIGA SE AS CIDADES
        this.alfa3 = alfa3.toUpperCase();
        this.nome = nome;
        this.numeroIndicadores = 0;

    }

    @Override
    public String toString() {
        if (id>700){
            return nome + " | " + id + " | " + alfa2 + " | " + alfa3+ " | " + numeroIndicadores;
        }else{
            return nome + " | " + id + " | " + alfa2 + " | " + alfa3;
        }
    }
}
