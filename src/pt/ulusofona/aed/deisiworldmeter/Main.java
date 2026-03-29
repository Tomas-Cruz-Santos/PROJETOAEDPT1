package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.util.ArrayList;

public class Main {

        public static ArrayList getObjects(TipoEntidade tipo) {
            return null;
        }

        public static boolean parseFiles(File Folder) {
            return  true;
        }

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao DEISI World Meter");

        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(new File("."));

        if (!parseOk) {
            System.out.println("Erro na leitura dos ficheiros");
            return;
        }
        long end = System.currentTimeMillis();



    }
}
