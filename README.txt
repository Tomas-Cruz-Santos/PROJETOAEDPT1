README.txt
DEISI World Meter - Parte 2
Algoritmia e Estruturas de Dados 2025/26
Universidade Lusófona de Humanidades e Tecnologias

Vídeo - https://www.youtube.com/watch?v=t_C0gxmeO9U


Este projeto foi desenvolvido no âmbito da disciplina de Algoritmia e Estruturas de Dados.
Trata-se de um sistema de consulta de dados mundiais sobre países, cidades e populações.

O programa lê ficheiros .csv com dados de países, cidades e populações históricas,
carrega-os em memória usando estruturas de dados eficientes (HashMap, TreeMap, HashSet)
e responde a queries em tempo reduzido, mesmo para ficheiros de grande dimensão.

=======================================================
COMANDOS IMPLEMENTADOS
=======================================================

HELP
  Mostra a lista de todos os comandos disponíveis.

COUNT_CITIES <min_population>
  Conta o número de cidades com população maior ou igual ao valor indicado.

GET_CITIES_BY_COUNTRY <num-results> <country-name>
  Lista as primeiras n cidades do país indicado, pela ordem em que aparecem no ficheiro.

SUM_POPULATIONS <country1,country2,...>
  Calcula a população total (masculina + feminina em 2024) de uma lista de países separados por vírgula.

GET_HISTORY <year-start> <year-end> <country-name>
  Mostra a população masculina e feminina de um país para cada ano num intervalo de anos.

GET_MISSING_HISTORY <year-start> <year-end>
  Mostra os países que não têm dados populacionais para algum ano dentro do intervalo indicado.

INSERT_CITY <alfa2> <city-name> <region> <population>
  Insere uma nova cidade na estrutura de dados, associada ao país com o código alfa2 indicado.

REMOVE_COUNTRY <country-name>
  Remove um país e todas as suas cidades e dados de população associados.

GET_MOST_POPULOUS <num-results>
  Lista as n cidades mais populosas do mundo (uma por país), por ordem decrescente de população.

GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>
  Lista as n cidades mais populosas de um país (mínimo 10000 habitantes), ordenadas por população.

GET_COUNTRIES_GENDER_GAP <min-gender-gap>
  Mostra os países cuja diferença percentual entre população masculina e feminina (em 2024)
  excede o valor indicado.

GET_TOP_POPULATION_INCREASE <year-start> <year-end>
  Mostra os 5 maiores aumentos de população (em percentagem) dentro do intervalo de anos indicado.

GET_DUPLICATE_CITIES <min_population>
  Mostra cidades com nome duplicado (excluindo a primeira ocorrência) com população >= ao valor indicado.

GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>
  Mostra cidades com o mesmo nome em países diferentes, com população >= ao valor indicado.

GET_CITIES_AT_DISTANCE <distance> <country-name>
  Lista pares de cidades do mesmo país que se encontram aproximadamente à distância indicada (em km).

GET_CITIES_AT_DISTANCE2 <distance> <country-name>
  Lista pares de cidades — uma do país indicado e outra de qualquer outro país —
  que se encontram aproximadamente à distância indicada (em km).

GET_CITIES_ABOVE_AVERAGE_POPULATION <min_cities> (EXTRA)
  Comando criativo: lista as cidades com população acima da média mundial, considerando apenas
  países com pelo menos min_cities cidades no ficheiro.

=======================================================
UTILIZAÇÃO DE IA GENERATIVA
=======================================================

Ferramenta/Modelo utilizado: Claude e ChatGPT

Prompt utilizado:
"Estou a implementar um projeto em Java que lê dados de países, cidades e populações
a partir de ficheiros CSV e precisa de responder a queries de forma eficiente. Estou
a usar ArrayLists para pesquisar países e cidades mas está muito lento para ficheiros
grandes. Podes explicar quando devo usar HashMap vs TreeMap e dar-me um exemplo
concreto aplicado a pesquisar países por nome ou id?"

Resposta obtida:
"Boa pergunta! Aqui está quando usar cada um:

HashMap: Usa quando só precisas de pesquisa rápida por uma chave e não te importas
com a ordem. Tem complexidade O(1) em média para get/put. Perfeito para procurar
um país por nome ou código alfa2.

Exemplo:
HashMap<String, Pais> mapaAlfa2 = new HashMap<>();
for (Pais pais : paises) {
    mapaAlfa2.put(pais.alfa2.toUpperCase(), pais);
}
Pais resultado = mapaAlfa2.get("PT"); // O(1) em vez de O(n)

TreeMap: Usa quando precisas que as chaves fiquem ordenadas automaticamente.
Tem complexidade O(log n) para get/put mas mantém as entradas por ordem natural.
Perfeito para dados de população indexados por ano, onde queres iterar os anos
por ordem.

Exemplo:
TreeMap<Integer, Populacao> popPorAno = new TreeMap<>();
for (Populacao pop : populacoes) {
    popPorAno.put(pop.ano, pop);
}
// popPorAno.values() devolve as populações ordenadas por ano automaticamente"

Como e onde foi utilizado:
A solução foi aplicada em vários comandos do projeto. Substituímos as pesquisas lineares
em ArrayList por HashMap para localizar países por nome, alfa2 e id, reduzindo a
complexidade de O(n) para O(1). Usámos TreeMap no comando GET_TOP_POPULATION_INCREASE
para armazenar os dados de população ordenados por ano, o que simplificou a lógica
de comparação entre anos consecutivos.
