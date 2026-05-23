package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
// 9/9

public class Main {
    static ArrayList<Pais> paises = new ArrayList<>();
    static ArrayList<Cidade> cidades = new ArrayList<>();
    static ArrayList<InputInvalido> inputInvalido = new ArrayList<>();

    public static ArrayList getObjects(TipoEntidade tipo) {
        if (tipo == TipoEntidade.PAIS) {
            return paises;
        }
        if (tipo == TipoEntidade.CIDADE) {
            return cidades;
        }
        if (tipo == TipoEntidade.INPUT_INVALIDO) {
            return inputInvalido;
        }
        return null;
    }

    public static boolean parseFiles(File folder) {
        paises = new ArrayList<>();// evita duplicados
        cidades = new ArrayList<>();
        inputInvalido = new ArrayList<>();

        String[] Ficheiros = {"paises.csv", "cidades.csv", "populacao.csv"};   // leitura de ficheiros

        for (String nome : Ficheiros) {
            File ficheiro = new File(folder, nome);

            if (!ficheiro.exists() || ficheiro.isDirectory()) {
                System.out.println("Erro: O ficheiro " + nome + " não existe ou não é um ficheiro válido.");
                return false;
            }
        }
        lerPaises(new File(folder, "paises.csv"));
        lerCidades(new File(folder, "cidades.csv"));

        removerPaisSemCidade();

        lerPopulacao(new File(folder, "populacao.csv"));

        return true;
    }

    static boolean lerPaises(File ficheiroPaises) {
        Scanner scanner = null;
        InputInvalido inputInvalidoPaises = new InputInvalido(ficheiroPaises.getName());
        boolean primeiraLinha = true; // Ignora a primeira linha (cabeçalho)
        int numeroDaLinha = 0; // Contador para o número da linha

        try {
            scanner = new Scanner(ficheiroPaises);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            numeroDaLinha++;

            if (primeiraLinha) {
                primeiraLinha = false;
                continue;
            }

            String[] partes = linha.split(",");
            if (partes.length == 4) {
                try {
                    int id = Integer.parseInt(partes[0]);
                    String alfa2 = partes[1];
                    String alfa3 = partes[2];
                    String nome = partes[3];

                    // Verifica se o ID já existe na lista de países (paises repetidos)
                    boolean idIgual = false;
                    for (Pais paisExistente : paises) {
                        if (paisExistente.id == id) {
                            idIgual = true;
                            break;
                        }
                    }

                    if (!idIgual) {
                        if (id > 0 && alfa2.length() == 2 && alfa3.length() == 3 && !nome.isEmpty()) {
                            Pais pais = new Pais(id, alfa2, alfa3, nome);
                            paises.add(pais);
                            inputInvalidoPaises.contalinhascorretas();
                        } else {
                            inputInvalidoPaises.contalinhasIncorretas(numeroDaLinha);
                        }
                    } else {
                        inputInvalidoPaises.contalinhasIncorretas(numeroDaLinha);
                    }

                } catch (NumberFormatException e) {
                    inputInvalidoPaises.contalinhasIncorretas(numeroDaLinha);
                }
            } else {
                inputInvalidoPaises.contalinhasIncorretas(numeroDaLinha);
            }
        }
        scanner.close();
        inputInvalido.add(inputInvalidoPaises);
        return true;
    }

    static boolean lerCidades(File ficheiroCidades) {
        Scanner scanner = null;
        InputInvalido inputInvalidoCidades = new InputInvalido(ficheiroCidades.getName());
        boolean primeiraLinha = true;
        int numeroDaLinha = 0;

        try {
            scanner = new Scanner(ficheiroCidades);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            numeroDaLinha++;

            if (primeiraLinha) {
                primeiraLinha = false;
                continue;
            }

            String[] partes = linha.split(",");

            if (partes.length == 6) {
                try {
                    String alfa2 = partes[0];
                    String nomeCidade = partes[1];
                    String regiao = (partes[2]);
                    double populacao = Double.parseDouble(partes[3]);
                    double latitude = Double.parseDouble(partes[4]);
                    double longitude = Double.parseDouble(partes[5]);

                    if (alfa2.length() == 2 && !regiao.isEmpty() && populacao > 0) {
                        boolean paisEncontrado = false;

                        for (Pais pais : paises) {
                            if (pais.alfa2.equalsIgnoreCase(alfa2)) {
                                paisEncontrado = true;
                                break;
                            }
                        }

                        if (paisEncontrado) {
                            Cidade cidadeEnc = new Cidade(alfa2, nomeCidade, regiao, populacao, latitude, longitude);
                            cidades.add(cidadeEnc);
                            inputInvalidoCidades.contalinhascorretas();
                        } else {
                            inputInvalidoCidades.contalinhasIncorretas(numeroDaLinha);
                        }
                    } else {
                        inputInvalidoCidades.contalinhasIncorretas(numeroDaLinha);
                    }
                } catch (NumberFormatException e) {
                    inputInvalidoCidades.contalinhasIncorretas(numeroDaLinha);
                }
            } else {
                inputInvalidoCidades.contalinhasIncorretas(numeroDaLinha);
            }
        }

        scanner.close();
        inputInvalido.add(inputInvalidoCidades);
        return true;
    }

    static void removerPaisSemCidade() {
        ArrayList<Pais> paisesFormatados = new ArrayList<>();
        HashSet<String> paisesComCidades = new HashSet<>();
        InputInvalido primeiroInputInvalido = inputInvalido.get(0);

        int linhasIncorretas = 0;
        int primeiraLinha = 1;
        boolean primeiraLinhaErrada = true;

        for (Cidade cidade : cidades) {
            paisesComCidades.add(cidade.alfa2);
        }

        for (Pais pais : paises) {
            if (primeiraLinhaErrada) {
                primeiraLinha++;
            }
            if (paisesComCidades.contains(pais.alfa2)) {
                paisesFormatados.add(pais);

            } else {
                linhasIncorretas++;
                primeiraLinhaErrada = false;
            }
        }

        paises = paisesFormatados;

        primeiroInputInvalido.linhasCorretas -= linhasIncorretas;
        primeiroInputInvalido.linhasIncorretas += linhasIncorretas;
        primeiroInputInvalido.primeiraLinhaIncorreta = primeiraLinha;
    }


    static boolean lerPopulacao(File ficheiroPopulacao) {
        Scanner scanner = null;
        InputInvalido inputInvalidoPopulacao = new InputInvalido(ficheiroPopulacao.getName());
        boolean primeiraLinha = true;
        int numeroDaLinha = 0;

        try {
            scanner = new Scanner(ficheiroPopulacao);
        } catch (FileNotFoundException e) {
            return false;
        }

        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            numeroDaLinha++;

            if (primeiraLinha) {
                primeiraLinha = false;
                continue;
            }

            String[] partes = linha.split(",");

            if (partes.length == 5) {
                try {
                    int idPais = Integer.parseInt(partes[0]);

                    // valida os outros campos sem guardar
                    Integer.parseInt(partes[1]);
                    Double.parseDouble(partes[2]);
                    Double.parseDouble(partes[3]);
                    Double.parseDouble(partes[4]);

                    Pais paisEncontrado = null;

                    for (Pais pais : paises) {
                        if (pais.id == idPais) {
                            paisEncontrado = pais;
                            break;
                        }
                    }

                    if (paisEncontrado != null) {
                        inputInvalidoPopulacao.contalinhascorretas();

                        if (paisEncontrado.id > 700) {
                            paisEncontrado.adicionarIndicador();
                        }
                    } else {
                        inputInvalidoPopulacao.contalinhasIncorretas(numeroDaLinha);
                    }

                } catch (NumberFormatException e) {
                    inputInvalidoPopulacao.contalinhasIncorretas(numeroDaLinha);
                }
            }
        }

        scanner.close();
        inputInvalido.add(inputInvalidoPopulacao);
        return true;
    }


    public static String comandoHelp() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("-------------------------\n");
        resultado.append("Commands available:\n");
        resultado.append("COUNT_CITIES <min_population>\n");
        resultado.append("GET_CITIES_BY_COUNTRY <num-results> <country-name>\n");
        resultado.append("SUM_POPULATIONS <countries-list>\n");
        resultado.append("GET_HISTORY <year-start> <year-end> <country-name>\n");
        resultado.append("GET_MISSING_HISTORY <year-start> <year-end>\n");
        resultado.append("GET_MOST_POPULOUS <num-results>\n");
        resultado.append("GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>\n");
        resultado.append("GET_DUPLICATE_CITIES <min_population>\n");
        resultado.append("GET_COUNTRIES_GENDER_GAP <min-gender-gap>\n");
        resultado.append("GET_TOP_POPULATION_INCREASE <year-start> <year-end>\n");
        resultado.append("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>\n");
        resultado.append("GET_CITIES_AT_DISTANCE <distance> <country-name>\n");
        resultado.append("GET_CITIES_AT_DISTANCE2 <distance> <country-name>\n");
        resultado.append("GET_CITIES_WHITIN_RADIUS <radius> <central-city>\n");
        resultado.append("INSERT_CITY <alfa2> <city-name> <region> <population>\n");
        resultado.append("REMOVE_COUNTRY <country-name>\n");
        resultado.append("HELP\n");
        resultado.append("QUIT\n");
        resultado.append("-------------------------\n");
        return resultado.toString();
    }

    public static Result execute(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {                 // FAZER COMANDOS

            case "HELP":
                return new Result(true, null, comandoHelp());

            default:
                return new Result(false, "Comando invalido", null);
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to DEISI World Meter");

        long start = System.currentTimeMillis();
        File pasta = new File("test-files");
        boolean parseOk = parseFiles(pasta);
        if (!parseOk) {
            System.out.println("Error loading files");
            return;
        }
        long end = System.currentTimeMillis();

        System.out.println("Loaded files in " + (end - start) + " ms\n");


        ArrayList<Object> resultados;

        // Teste para INPUT_INVALIDO
        System.out.println("Resultados para INPUT_INVALIDO:");
        resultados = getObjects(TipoEntidade.INPUT_INVALIDO);
        for (Object obj : resultados) {
            System.out.println(obj);
        }

        Result result = execute("HELP");
        System.out.println(result.result);

        Scanner in = new Scanner(System.in);

        String line;

        do {
            System.out.print("> ");
            line = in.nextLine(); // Read input at the start of the loop

            if (line != null && !line.equals("QUIT")) {
                start = System.currentTimeMillis();
                result = execute(line);
                end = System.currentTimeMillis();

                if (!result.success) {
                    System.out.println("Error " + result.error);
                } else {
                    System.out.println(result.result);
                    System.out.println("(took " + (end - start) + " ms)");
                }
            }
        }
        while (line != null && !line.equals("QUIT"));
    }
}

