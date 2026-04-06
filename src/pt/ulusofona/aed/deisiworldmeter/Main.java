package pt.ulusofona.aed.deisiworldmeter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao DEISI World Meter");

        File pasta = new File("test-files"); // podes manter esta pasta se ela existir mesmo

        System.out.println("Pasta usada: " + pasta.getAbsolutePath());

        long start = System.currentTimeMillis();
        boolean parseOk = parseFiles(pasta);

        if (!parseOk) {
            System.out.println("Erro na leitura dos ficheiros");
            return;
        }

        System.out.println("Leitura concluída");
        long end = System.currentTimeMillis();
        System.out.println("Ficheiros lidos com sucesso em " + (end - start) + " ms");
        System.out.println();

        System.out.println("TESTAR PAISES");
        System.out.println(paises);

        ArrayList paisesLidos = getObjects(TipoEntidade.PAIS);
        System.out.println("Teste 1: Quantidade de países no ficheiro");
        System.out.println("Total países: " + paisesLidos.size());
        System.out.println();

        System.out.println("TESTAR CIDADES");
        System.out.println(cidades);


        ArrayList cidadesLidas = getObjects(TipoEntidade.CIDADE);
        System.out.println("Teste 2: Quantidade de cidades no ficheiro");
        System.out.println("Total cidades: " + cidadesLidas.size());
        System.out.println();

        System.out.println("Teste 3: Ver primeiros 3 países");
        for (int i = 0; i < 3 && i < paisesLidos.size(); i++) {
            System.out.println(paisesLidos.get(i));
        }
        System.out.println();

        System.out.println("Teste 4: Ver últimos 5 países");
        for (int i = Math.max(0, paisesLidos.size() - 5); i < paisesLidos.size(); i++) {
            System.out.println(paisesLidos.get(i));
        }
        System.out.println();

        System.out.println("Informações sobre a leitura de ficheiros:");
        System.out.println("nome | linhas OK | linhas NOK | primeira linha NOK");

        ArrayList inputsInvalidos = getObjects(TipoEntidade.INPUT_INVALIDO);
        for (int i = 0; i < inputsInvalidos.size(); i++) {
            System.out.println(inputsInvalidos.get(i));
        }
    }
}

