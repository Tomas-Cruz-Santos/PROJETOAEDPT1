package pt.ulusofona.aed.deisiworldmeter;

public class InputInvalido {
    String nomeFicheiro;
    int linhasCorretas;
    int linhasIncorretas;
    int primeiraLinhaIncorreta;


    public InputInvalido(String file) {
        this.nomeFicheiro = file;
        this.linhasCorretas = 0;
        this.linhasIncorretas = 0;
        this.primeiraLinhaIncorreta = -1;
    }

    public void contalinhascorretas() {
        linhasCorretas++;
    }

    public void contalinhasIncorretas(int nlinha) {
        if (linhasIncorretas == 0) {
            primeiraLinhaIncorreta = nlinha;
        }
        linhasIncorretas++;
    }

    @Override
    public String toString() {
        return nomeFicheiro + " | " + linhasCorretas + " | " + linhasIncorretas + " | " + (primeiraLinhaIncorreta != -1 ? primeiraLinhaIncorreta : "-1");
    }
}

