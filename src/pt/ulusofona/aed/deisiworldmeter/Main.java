package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeMap;


public class Main {

    // PARTE 1 : Estrutura de Dados
    static ArrayList<Pais> paises = new ArrayList<>();
    static ArrayList<Cidade> cidades = new ArrayList<>();
    static ArrayList<InputInvalido> inputInvalido = new ArrayList<>();
    // PARTE 2 : Estrutura de Dados
    static ArrayList<Populacao> populacoes = new ArrayList<>();


    // PARTE 1 :  GET OBJECTS
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

    // PARTE 1 : PARSE FILES
    public static boolean parseFiles(File folder) {
        paises = new ArrayList<>();// evita duplicados
        cidades = new ArrayList<>();
        populacoes = new ArrayList<>();
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
        paisEPopulacao();

        lerPopulacao(new File(folder, "populacao.csv"));

        return true;
    }
    // PARTE 1 : LER PAISES
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
    // PARTE 1 : LER CIDADES
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


    // PARTE 2 : REMOVER PAISES SEM CIDADES
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
    // PARTE 2 : LER POPULACAO (CORRIGIDO)
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
                    int ano = Integer.parseInt(partes[1]);
                    long popMasculina = (long) Double.parseDouble(partes[2]);
                    long popFeminina = (long) Double.parseDouble(partes[3]);
                    double densidade = Double.parseDouble(partes[4]);

                    Pais paisEncontrado = null;
                    for (Pais pais : paises) {
                        if (pais.id == idPais) {
                            paisEncontrado = pais;
                            break;
                        }
                    }

                    if (paisEncontrado != null) {
                        populacoes.add(new Populacao(idPais, ano, popMasculina, popFeminina, densidade));
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
            } else {
                inputInvalidoPopulacao.contalinhasIncorretas(numeroDaLinha);
            }
        }

        scanner.close();
        inputInvalido.add(inputInvalidoPopulacao);
        return true;
    }

    static void paisEPopulacao() {
        ArrayList<Populacao> populacaoFormatada = new ArrayList<>();
        HashSet<Integer> paisesComPopulacao = new HashSet<>();
        InputInvalido segundoInvalido = inputInvalido.get(0);

        int numeroLinha = 2;
        int linhasIncorretas = 0;

        for (Pais pais : paises) {
            paisesComPopulacao.add(pais.id);
        }

        for (Populacao populacao : populacoes) {
            if(paisesComPopulacao.contains(populacao.id)) {
                populacaoFormatada.add(populacao);
            } else {
                linhasIncorretas++;
                segundoInvalido.contalinhasIncorretas(numeroLinha);
                segundoInvalido.linhasCorretas--;
            }
            numeroLinha++;
        }
        populacoes = populacaoFormatada;
    }

    // PARTE 2 : COMANDO HELP
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
    // PARTE 2 : EXECUTE
    public static Result execute(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {

            case "HELP":
                return new Result(true, null, comandoHelp());

            case "COUNT_CITIES":
                int minPop = Integer.parseInt(parts[1]);
                int count = 0;
                for (Cidade cidade : cidades) {
                    if (cidade.populacao >= minPop) {
                        count++;
                    }
                }
                return new Result(true, null, String.valueOf(count));

            case "GET_CITIES_BY_COUNTRY":
                int numResults = Integer.parseInt(parts[1]);
                String nomePaisGCBC = command.substring(parts[0].length() + parts[1].length() + 2);

                HashMap<String, Pais> mapaNomePais = new HashMap<>();
                for (Pais pais : paises) {
                    mapaNomePais.put(pais.nome.toLowerCase(), pais);
                }

                Pais paisGCBC = mapaNomePais.get(nomePaisGCBC.toLowerCase());
                if (paisGCBC == null) {
                    return new Result(false, "Pais invalido: " + nomePaisGCBC, null);
                }

                StringBuilder sb = new StringBuilder();
                int contador = 0;
                for (Cidade cidade : cidades) {
                    if (cidade.alfa2.equalsIgnoreCase(paisGCBC.alfa2)) {
                        sb.append(cidade.cidade).append("\n");
                        contador++;
                        if (contador == numResults) {
                            break;
                        }
                    }
                }
                return new Result(true, null, sb.toString());

            case "SUM_POPULATIONS":
                String listaPaises = command.substring(parts[0].length() + 1);
                String[] paisesLista = listaPaises.split(",");
                long totalPop = 0;

                HashMap<String, Pais> mapaSumPop = new HashMap<>();
                for (Pais pais : paises) {
                    mapaSumPop.put(pais.nome.toLowerCase().trim(), pais);
                }

                HashMap<Integer, Populacao> mapaPop2024 = new HashMap<>();
                for (Populacao pop : populacoes) {
                    if (pop.ano == 2024) {
                        mapaPop2024.put(pop.id, pop);
                    }
                }

                for (String nomePais2 : paisesLista) {
                    Pais paisTotal = mapaSumPop.get(nomePais2.toLowerCase().trim());

                    // País inválido deve ser ignorado, não deve dar success=false
                    if (paisTotal == null) {
                        return new Result(false, "Pais invalido: " + nomePais2.trim(), null);
                    }

                    Populacao pop2024 = mapaPop2024.get(paisTotal.id);
                    if (pop2024 != null) {
                        totalPop += pop2024.populacaoMasculina + pop2024.populacaoFeminina;
                    }
                }

                return new Result(true, null, String.valueOf(totalPop));

            case "GET_HISTORY":
                int anoInicio = Integer.parseInt(parts[1]);
                int anoFim = Integer.parseInt(parts[2]);
                String nomePaisHistory = command.substring(parts[0].length() + parts[1].length() + parts[2].length() + 3);

                HashMap<String, Pais> mapaHistory = new HashMap<>();
                for (Pais pais : paises) {
                    mapaHistory.put(pais.nome.toLowerCase(), pais);
                }

                Pais paisHistory = mapaHistory.get(nomePaisHistory.toLowerCase());
                if (paisHistory == null) {
                    return new Result(false, "Pais invalido: " + nomePaisHistory, null);
                }

                HashMap<Integer, Populacao> mapaAnosPop = new HashMap<>();
                for (Populacao pop : populacoes) {
                    if (pop.id == paisHistory.id) {
                        mapaAnosPop.put(pop.ano, pop);
                    }
                }

                StringBuilder sbHistory = new StringBuilder();
                for (int ano = anoInicio; ano <= anoFim; ano++) {
                    Populacao pop = mapaAnosPop.get(ano);
                    if (pop != null) {
                        sbHistory.append(pop.ano).append(":")
                                .append(pop.populacaoMasculina / 1000).append("k:")
                                .append(pop.populacaoFeminina / 1000).append("k\n");
                    }
                }

                if (sbHistory.length() == 0) {
                    return new Result(true, null, "Sem resultados\n");
                }
                return new Result(true, null, sbHistory.toString());

            case "GET_MISSING_HISTORY":
                int anoInicioMissing = Integer.parseInt(parts[1]);
                int anoFimMissing = Integer.parseInt(parts[2]);

                // HashMap idPais -> Set de anos que tem
                HashMap<Integer, HashSet<Integer>> mapaAnosExistentes = new HashMap<>();
                for (Populacao pop : populacoes) {
                    mapaAnosExistentes.computeIfAbsent(pop.id, k -> new HashSet<>()).add(pop.ano);
                }

                StringBuilder sbMissing = new StringBuilder();
                for (Pais pais : paises) {
                    HashSet<Integer> anosDoP = mapaAnosExistentes.get(pais.id);
                    boolean faltaAlgum = false;
                    for (int ano = anoInicioMissing; ano <= anoFimMissing; ano++) {
                        if (anosDoP == null || !anosDoP.contains(ano)) {
                            faltaAlgum = true;
                            break;
                        }
                    }
                    if (faltaAlgum) {
                        sbMissing.append(pais.alfa2.toLowerCase()).append(":").append(pais.nome).append("\n");
                    }
                }

                if (sbMissing.length() == 0) {
                    return new Result(true, null, "Sem resultados\n");
                }
                return new Result(true, null, sbMissing.toString());


            case "INSERT_CITY":
                String alfa2Insert = parts[1];
                String regiaoInsert = parts[parts.length - 2];
                double popInsert = Double.parseDouble(parts[parts.length - 1]);
                String nomeInsert = command.substring(
                        parts[0].length() + parts[1].length() + 2,
                        command.length() - parts[parts.length - 1].length() - parts[parts.length - 2].length() - 2
                );

                // verificar se o país existe  ← ESTA PARTE ESTAVA A FALTAR!
                HashMap<String, Pais> mapaInsert = new HashMap<>();
                for (Pais pais : paises) {
                    mapaInsert.put(pais.alfa2.toUpperCase(), pais);
                }

                if (mapaInsert.get(alfa2Insert.toUpperCase()) == null) {
                    return new Result(false, "Pais invalido", null);
                }

                cidades.add(new Cidade(alfa2Insert, nomeInsert, regiaoInsert, popInsert, 0.0, 0.0));
                return new Result(true, null, "Inserido com sucesso");

            case "REMOVE_COUNTRY":
                String nomeRemover = command.substring(parts[0].length() + 1);

                // HashMap nome -> Pais
                HashMap<String, Pais> mapaRemover = new HashMap<>();
                for (Pais pais : paises) {
                    mapaRemover.put(pais.nome.toLowerCase(), pais);
                }

                Pais paisRemover = mapaRemover.get(nomeRemover.toLowerCase());
                if (paisRemover == null) {
                    return new Result(false, "Pais invalido", null);
                }

                paises.remove(paisRemover);

                cidades.removeIf(cidade -> cidade.alfa2.equalsIgnoreCase(paisRemover.alfa2));
                populacoes.removeIf(pop -> pop.id == paisRemover.id);

                return new Result(true, null, "Removido com sucesso");


            case "GET_MOST_POPULOUS":
                int numResultsMP = Integer.parseInt(parts[1]);

                // HashMap alfa2 -> cidade mais populosa
                HashMap<String, Cidade> mapaMaisPopulosa = new HashMap<>();
                for (Cidade cidade : cidades) {
                    Cidade atual = mapaMaisPopulosa.get(cidade.alfa2.toUpperCase());
                    if (atual == null || cidade.populacao > atual.populacao) {
                        mapaMaisPopulosa.put(cidade.alfa2.toUpperCase(), cidade);
                    }
                }

                // HashMap alfa2 -> Pais
                HashMap<String, Pais> mapaAlfa2Pais = new HashMap<>();
                for (Pais pais : paises) {
                    mapaAlfa2Pais.put(pais.alfa2.toUpperCase(), pais);
                }

                ArrayList<Cidade> maisPopulosas = new ArrayList<>(mapaMaisPopulosa.values());

                // ordenar por população decrescente
                for (int i = 0; i < maisPopulosas.size() - 1; i++) {
                    for (int j = i + 1; j < maisPopulosas.size(); j++) {
                        if (maisPopulosas.get(j).populacao > maisPopulosas.get(i).populacao) {
                            Cidade temp = maisPopulosas.get(i);
                            maisPopulosas.set(i, maisPopulosas.get(j));
                            maisPopulosas.set(j, temp);
                        }
                    }
                }

                int limiteMP = numResultsMP == -1 ? maisPopulosas.size() : Math.min(numResultsMP, maisPopulosas.size());
                StringBuilder sbMP = new StringBuilder();
                for (int i = 0; i < limiteMP; i++) {
                    Cidade c = maisPopulosas.get(i);
                    Pais paisC = mapaAlfa2Pais.get(c.alfa2.toUpperCase());
                    String nomePaisC = paisC != null ? paisC.nome : c.alfa2;
                    sbMP.append(nomePaisC).append(":").append(c.cidade).append(":").append((long) c.populacao).append("\n");
                }
                return new Result(true, null, sbMP.toString());


            case "GET_TOP_CITIES_BY_COUNTRY":
                int numResultsTop = Integer.parseInt(parts[1]);
                String nomePaisTop = parts[2];

                ArrayList<Cidade> cidadesDoPais = new ArrayList<>();
                for (Pais pais : paises) {
                    if (pais.nome.equals(nomePaisTop)) {          // usa a variável local
                        for (Cidade cidade : cidades) {
                            if (cidade.alfa2.equals(pais.alfa2) && cidade.populacao >= 10000) {
                                cidadesDoPais.add(cidade);
                            }
                        }
                        break;
                    }
                }

                cidadesDoPais.sort((c1, c2) -> {
                    int comparar = Integer.compare((int) c2.populacao / 1000, (int) c1.populacao / 1000);
                    if (comparar != 0) {
                        return comparar;
                    }
                    return c1.cidade.compareTo(c2.cidade);
                });

                if (numResultsTop != -1) {  // quando -1 le desde o inicio
                    int numReais = Math.min(numResultsTop, cidadesDoPais.size());
                    cidadesDoPais = new ArrayList<>(cidadesDoPais.subList(0, numReais));
                }

                StringBuilder resultTop = new StringBuilder();
                for (Cidade cidade : cidadesDoPais) {
                    resultTop.append(cidade.cidade)   // ordem alfsbetica caso tenha o mesmo valor
                            .append(":")
                            .append((int) cidade.populacao / 1000)
                            .append("K\n");
                }
                return new Result(true, null, resultTop.toString());


            case "GET_COUNTRIES_GENDER_GAP":
                int minGap = Integer.parseInt(parts[1]);

                // HashMap idPais -> pop2024
                HashMap<Integer, Populacao> mapaGap = new HashMap<>();
                for (Populacao pop : populacoes) {
                    if (pop.ano == 2024) {
                        mapaGap.put(pop.id, pop);
                    }
                }

                StringBuilder sbGap = new StringBuilder();
                for (Pais pais : paises) {
                    Populacao pop2024 = mapaGap.get(pais.id);
                    if (pop2024 == null) {
                        continue;
                    }
                    double masc = pop2024.populacaoMasculina;
                    double fem = pop2024.populacaoFeminina;
                    double gap = Math.abs(masc - fem) / (masc + fem) * 100;
                    if (gap >= minGap) {
                        double gapTruncado = Math.round(gap * 100.0) / 100.0;
                        sbGap.append(pais.nome).append(":")
                                .append(String.format(java.util.Locale.US, "%.2f", gapTruncado)).append("\n");
                    }
                }

                if (sbGap.length() == 0) {
                    return new Result(true, null, "Sem resultados");
                }
                return new Result(true, null, sbGap.toString());


            case "GET_TOP_POPULATION_INCREASE":
                int anoInicioIncrease = Integer.parseInt(parts[1]);
                int anoFimIncrease = Integer.parseInt(parts[2]);

                HashMap<Integer, TreeMap<Integer, Populacao>> mapaPopsIncrease = new HashMap<>();
                for (Populacao pop : populacoes) {
                    if (pop.ano >= anoInicioIncrease && pop.ano <= anoFimIncrease) {
                        mapaPopsIncrease.computeIfAbsent(pop.id, k -> new TreeMap<>()).put(pop.ano, pop);
                    }
                }

                HashMap<Integer, Pais> mapaIdPais = new HashMap<>();
                for (Pais pais : paises) {
                    mapaIdPais.put(pais.id, pais);
                }

                ArrayList<double[]> aumentos = new ArrayList<>();
                ArrayList<String> nomesAumentos = new ArrayList<>();

                for (int idPais : mapaPopsIncrease.keySet()) {
                    TreeMap<Integer, Populacao> popsOrdenadas = mapaPopsIncrease.get(idPais);
                    Pais paisIncrease = mapaIdPais.get(idPais);
                    if (paisIncrease == null) {
                        continue;
                    }

                    ArrayList<Populacao> lista = new ArrayList<>(popsOrdenadas.values());

                    for (int i = 0; i < lista.size(); i++) {
                        for (int j = i + 1; j < lista.size(); j++) {
                            Populacao popAnterior = lista.get(i);
                            Populacao popAtual = lista.get(j);
                            double totalAnterior = popAnterior.populacaoMasculina + popAnterior.populacaoFeminina;
                            double totalAtual = popAtual.populacaoMasculina + popAtual.populacaoFeminina;
                            double aumento = totalAtual - totalAnterior;
                            if (aumento > 0) {
                                double percentagem = aumento / totalAtual * 100;
                                double percentagemFinal = Math.round(percentagem * 100.0) / 100.0;
                                aumentos.add(new double[]{percentagemFinal});
                                nomesAumentos.add(paisIncrease.nome + ":" + popAnterior.ano + "-" + popAtual.ano);
                            }
                        }
                    }
                }

                for (int i = 0; i < aumentos.size() - 1; i++) {
                    for (int j = i + 1; j < aumentos.size(); j++) {
                        if (aumentos.get(j)[0] > aumentos.get(i)[0]) {
                            double[] tempD = aumentos.get(i);
                            aumentos.set(i, aumentos.get(j));
                            aumentos.set(j, tempD);
                            String tempS = nomesAumentos.get(i);
                            nomesAumentos.set(i, nomesAumentos.get(j));
                            nomesAumentos.set(j, tempS);
                        }
                    }
                }

                StringBuilder sbIncrease = new StringBuilder();
                int limiteIncrease = Math.min(5, aumentos.size());
                for (int i = 0; i < limiteIncrease; i++) {
                    sbIncrease.append(nomesAumentos.get(i)).append(":")
                            .append(String.format(java.util.Locale.US, "%.2f", aumentos.get(i)[0]))
                            .append("%\n");
                }

                if (sbIncrease.length() == 0) {
                    return new Result(true, null, "Sem resultados");
                }
                return new Result(true, null, sbIncrease.toString());


            case "GET_DUPLICATE_CITIES":
                int minPopDup = Integer.parseInt(parts[1]);

                // contar ocorrências de cada no
                HashMap<String, Integer> contagemNomes = new HashMap<>();
                for (Cidade cidade : cidades) {
                    if (cidade.populacao >= minPopDup) {
                        contagemNomes.put(cidade.cidade, contagemNomes.getOrDefault(cidade.cidade, 0) + 1);
                    }
                }

                // HashMap alfa2 -> nome do país
                HashMap<String, String> mapaAlfa2Nome = new HashMap<>();
                for (Pais pais : paises) {
                    mapaAlfa2Nome.put(pais.alfa2.toUpperCase(), pais.nome);
                }

                HashSet<String> originaisVistos = new HashSet<>();
                StringBuilder sbDup = new StringBuilder();

                for (Cidade cidade : cidades) {
                    if (cidade.populacao >= minPopDup && contagemNomes.getOrDefault(cidade.cidade, 0) > 1) {
                        if (originaisVistos.contains(cidade.cidade)) {
                            String nomePaisDup = mapaAlfa2Nome.getOrDefault(cidade.alfa2.toUpperCase(), cidade.alfa2);
                            sbDup.append(cidade.cidade).append(" (").append(nomePaisDup).append(",").append(cidade.regiao).append(")\n");
                        } else {
                            originaisVistos.add(cidade.cidade);
                        }
                    }
                }

                if (sbDup.length() == 0) {
                    return new Result(true, null, "Sem resultados\n");
                }
                return new Result(true, null, sbDup.toString());

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
        } while (line != null && !line.equals("QUIT"));
    }
}

