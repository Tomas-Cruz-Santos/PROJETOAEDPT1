package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class TestMain {

    @Test
    public void toStringPaisIdMenor700() {
        Pais pais = new Pais(620, "pt", "prt", "Portugal");

        String atual = pais.toString();
        String esperado = "Portugal | 620 | PT | PRT";

        assertEquals(esperado, atual);
    }

    @Test
    public void toStringPaisIdMaior700() {
        Pais pais = new Pais(701, "wk", "wka", "Wakanda");

        for (int i = 0; i < 5; i++) {
            pais.adicionarIndicador();
        }

        String atual = pais.toString();
        String esperado = "Wakanda | 701 | WK | WKA | 5";

        assertEquals(esperado, atual);
    }

    @Test
    public void toStringCidade() {
        Cidade cidade = new Cidade("ad", "andorra la vella", "07", 20430, 42.5, 1.5166667);

        String atual = cidade.toString();
        String esperado = "andorra la vella | AD | 07 | 20430 | (42.5,1.5166667)";

        assertEquals(esperado, atual);
    }

    @Test
    public void parseFilesSemErros() {
        boolean atual = Main.parseFiles(new File("test-files"));

        assertTrue(atual);

        ArrayList paises = Main.getObjects(TipoEntidade.PAIS);
        ArrayList cidades = Main.getObjects(TipoEntidade.CIDADE);

        assertNotNull(paises);
        assertNotNull(cidades);
        assertTrue(paises.size() > 0);
        assertTrue(cidades.size() > 0);
    }

    @Test
    public void getObjectsInputInvalido() {
        boolean resultado = Main.parseFiles(new File("test-files"));

        assertTrue(resultado);

        ArrayList lista = Main.getObjects(TipoEntidade.INPUT_INVALIDO);

        assertNotNull(lista);
        assertEquals(3, lista.size());
    }
}