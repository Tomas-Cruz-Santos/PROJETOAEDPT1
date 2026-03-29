package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList getObjects(TipoEntidade tipo) {
        return null;
    }

    public static boolean parseFiles(File Folder) {

        // leitura do ficheiro paises
        File ficheiroPaises = new File(Folder, "paises.csv");
        try {
            Scanner scanner = new Scanner(ficheiroPaises);
            boolean primeiraLinha = true;

            while (scanner.hasNextLine()) { // se tiver a proxima linha retorna true (hasNextLine)
                String linha = scanner.nextLine();

                if (primeiraLinha) { // ignorar cabeçalho
                    primeiraLinha = false;
                    continue;
                }
                System.out.println(linha);
            }
            scanner.close();
            return true;

        } catch (FileNotFoundException e) {
            // erro : ficheiro não existe
            return false;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Bem-vindo ao DEISI World Meter");

        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(new File("test-files"));

        if (!parseOk) {
            System.out.println("Erro na leitura dos ficheiros");
            return;
        }
        System.out.println("Leitura concluída");
        long end = System.currentTimeMillis();
    }
}
