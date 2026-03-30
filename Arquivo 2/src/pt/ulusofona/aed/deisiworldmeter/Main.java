package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Pais> paises = new ArrayList<>();
    static ArrayList<InputInvalido> inputInvalidos = new ArrayList<>();
    public static ArrayList getObjects(TipoEntidade tipo) {
        if (tipo == TipoEntidade.PAIS){
            return paises;
        }
        if (tipo == TipoEntidade.INPUT_INVALIDO){
            return inputInvalidos;
        }
        return null;
    }


    public static boolean parseFiles(File folder) {
        paises = new ArrayList<>();// evita duplicados
        inputInvalidos = new ArrayList<>();


        InputInvalido infoPaises = new InputInvalido("paises.csv");

        // Leitura do ficheiro paises
        File ficheiroPaises = new File(folder, "paises.csv");
        try {
            Scanner scanner = new Scanner(ficheiroPaises);
            boolean primeiraLinha = true;
            int numeroLinha= 0;

            while (scanner.hasNextLine()) { // se tiver a proxima linha retorna true (hasNextLine)
                String linha = scanner.nextLine();
                numeroLinha ++;

                if (primeiraLinha) { // ignorar cabeçalho
                    primeiraLinha = false;
                    continue;
                }
                String[] partes = linha.split(",");

                if (partes.length != 4) {
                    infoPaises.contalinhasIncorretas(numeroLinha);
                    continue;
                }

                int id = Integer.parseInt(partes[0]);
                String alfa2 = partes[1];
                String alfa3 = partes[2];
                String nome = partes[3];

                Pais pais = new Pais(id, alfa2, alfa3, nome);
                paises.add(pais);
                infoPaises.contalinhascorretas();
            }
            scanner.close();
            inputInvalidos.add(infoPaises);
            return true;

        } catch (FileNotFoundException e) {
            // erro : ficheiro não existe
            return false;
        }

    }

    public static void main(String[] args)  {
        System.out.println("Bem-vindo ao DEISI World Meter");

        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(new File("test-files"));

        if (!parseOk) {
            System.out.println("Erro na leitura dos ficheiros");
            return;
        }
        System.out.println("Leitura concluída");
        long end = System.currentTimeMillis();
        System.out.println("Ficheiros lidos com sucesso em " + (end-start) + "ms" );
        System.out.println();

        System.out.println("TESTAR PAISES");
        System.out.println(paises);
        ArrayList paisesLidos = getObjects(TipoEntidade.PAIS);
        System.out.println("Teste 1: Quantidade paises no ficheiro");
        System.out.println("Total paises: " + paisesLidos.size());
        System.out.println();

        System.out.println("Teste 2: Ver primeiros 3 paises");
        for (int i = 0; i < 3 && i < paisesLidos.size(); i++) { // vê os primeiros 3
            System.out.println(paisesLidos.get(i));
        }
        System.out.println();

        System.out.println("Teste 3: Ver últimos 5 paises do ficheiro");
        for (int i = paisesLidos.size() - 2; i<paisesLidos.size(); i++) { // vê os primeiros 3
            System.out.println(paisesLidos.get(i));
        }
        //TESTE 4 : Teste de erro":
            // Cria uma linha inválida no CSV:
            // é suposto o programa nao falhar, e a linha ser ignorada ex: 123,PT

        System.out.println();
        System.out.println("Informações sobre a leitura de ficheiros:");
        System.out.println("nome | linhas OK | linhas NOK | primeira linha NOK");
        ArrayList inputsInvalidos = getObjects(TipoEntidade.INPUT_INVALIDO);
        System.out.println(inputsInvalidos.get(0));

    }
}
