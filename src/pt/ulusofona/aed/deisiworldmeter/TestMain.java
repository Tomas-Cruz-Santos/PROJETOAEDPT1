package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    @Test
    public void testCountCities() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("COUNT_CITIES 100000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("6", result.result);

        result = Main.execute("COUNT_CITIES 500000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("2", result.result);
    }

    @Test
    public void testGetCitiesByCountry() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_CITIES_BY_COUNTRY 3 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        String[] parts = result.result.split("\n");
        assertEquals("almada", parts[0]);
        assertEquals("lamego", parts[1]);
        assertEquals("canecas", parts[2]);

        result = Main.execute("GET_CITIES_BY_COUNTRY 5 Espanha");
        assertNotNull(result);
        assertFalse(result.success);
    }

    @Test
    public void testInsertCity() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("INSERT_CITY PT Lisboa 01 1000000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Inserido com sucesso", result.result);

        result = Main.execute("COUNT_CITIES 100000");
        assertEquals("7", result.result);

        result = Main.execute("INSERT_CITY XX FakeCity 01 100000");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Pais invalido", result.result);
    }

    @Test
    public void testRemoveCountry() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("REMOVE_COUNTRY Brasil");
        assertNotNull(result);
        assertTrue(result.success);
        assertEquals("Removido com sucesso", result.result);

        result = Main.execute("COUNT_CITIES 100000");
        assertEquals("5", result.result);

        result = Main.execute("REMOVE_COUNTRY Espanha");
        assertNotNull(result);
        assertTrue(result.success);
    }

    @Test
    public void testGetHistory() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_HISTORY 2023 2024 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        String[] lines = result.result.split("\n");
        assertEquals("2023:4836k:5410k", lines[0]);
        assertEquals("2024:4827k:5396k", lines[1]);
    }
    @Test
    public void testResult() {
        Result r1 = new Result(true, null, "teste");
        assertTrue(r1.success);
        assertEquals("teste", r1.result);

        Result r2 = new Result(false, "erro", null);
        assertFalse(r2.success);
        assertEquals("erro", r2.error);
    }

    @Test
    public void testExecuteComandoInvalido() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("COMANDO_INVALIDO");
        assertNotNull(result);
        assertFalse(result.success);
    }

    @Test
    public void testGetTotalPopulation() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("SUM_POPULATIONS Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        assertNotNull(result.result);
    }

    @Test
    public void testGetMissingPopulationHistory() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_MISSING_HISTORY 2020 2024");
        assertNotNull(result);
        assertTrue(result.success);
    }

    @Test
    public void testGetMostPopulousCities() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_MOST_POPULOUS 3");
        assertNotNull(result);
        assertTrue(result.success);
        String[] lines = result.result.split("\n");
        assertEquals(3, lines.length);
    }

    @Test
    public void testGetMostPopulousCitiesByCountry() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_TOP_CITIES_BY_COUNTRY 3 Portugal");
        assertNotNull(result);
        assertTrue(result.success);
        String[] lines = result.result.split("\n");
        assertEquals(3, lines.length);
    }

    @Test
    public void testGetGenderGap() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_COUNTRIES_GENDER_GAP 0");
        assertNotNull(result);
        assertTrue(result.success);
        assertNotNull(result.result);
    }

    @Test
    public void testGetBiggestPopulationIncrease() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_TOP_POPULATION_INCREASE 2020 2024");
        assertNotNull(result);
        assertTrue(result.success);
        assertNotNull(result.result);
    }

    @Test
    public void testGetDuplicateCities() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_DUPLICATE_CITIES 1000");
        assertNotNull(result);
        assertTrue(result.success);
    }
    @Test
    public void testGetCitiesAtDistance() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_CITIES_AT_DISTANCE 27 Portugal");
        assertNotNull(result);
        assertTrue(result.success);

        result = Main.execute("GET_CITIES_AT_DISTANCE 27 Espanha");
        assertNotNull(result);
        assertFalse(result.success);
    }
    @Test
    public void testGetCitiesAtDistanceDifferentCountries() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_CITIES_AT_DISTANCE2 50 Portugal");
        assertNotNull(result);
        assertTrue(result.success);

        result = Main.execute("GET_CITIES_AT_DISTANCE2 50 Espanha");
        assertNotNull(result);
        assertFalse(result.success);
    }
    @Test
    public void testCreativeCommand() {
        assertTrue(Main.parseFiles(new File("test-files")));

        Result result = Main.execute("GET_CITIES_ABOVE_AVERAGE_POPULATION 1");
        assertNotNull(result);
        assertTrue(result.success);
        String[] lines = result.result.split("\n");
        assertEquals(2, lines.length);

        result = Main.execute("GET_CITIES_ABOVE_AVERAGE_POPULATION 3");
        assertNotNull(result);
        assertTrue(result.success);
        lines = result.result.split("\n");
        assertEquals(5, lines.length);
    }
}

