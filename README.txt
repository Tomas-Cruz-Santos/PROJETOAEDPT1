README.txt
DEISI World Meter - Parte 2
Algoritmia e Estruturas de Dados 2025/26
Universidade Lusófona de Humanidades e Tecnologias

Vídeo: https://www.youtube.com/watch?v=t_C0gxmeO9U

Este projeto foi desenvolvido no âmbito da disciplina de Algoritmia e Estruturas de Dados.
O programa lê ficheiros .csv com dados de países, cidades e populações, carrega-os em
memória usando estruturas de dados eficientes (HashMap, TreeMap, HashSet) e responde
a queries em tempo reduzido, mesmo para ficheiros de grande dimensão.

=======================================================
COMANDOS IMPLEMENTADOS
=======================================================

HELP - Mostra a lista de todos os comandos disponíveis.
COUNT_CITIES <min_population> - Conta cidades com população >= ao valor indicado.
GET_CITIES_BY_COUNTRY <n> <country> - Lista as primeiras n cidades do país indicado.
SUM_POPULATIONS <country1,country2,...> - Calcula a população total de uma lista de países.
GET_HISTORY <year-start> <year-end> <country> - Mostra população masculina e feminina por ano.
GET_MISSING_HISTORY <year-start> <year-end> - Mostra países sem dados num intervalo de anos.
INSERT_CITY <alfa2> <city-name> <region> <population> - Insere uma nova cidade.
REMOVE_COUNTRY <country> - Remove um país e todos os dados associados.
GET_MOST_POPULOUS <n> - Lista as n cidades mais populosas do mundo (uma por país).
GET_TOP_CITIES_BY_COUNTRY <n> <country> - Lista as n cidades mais populosas de um país.
GET_COUNTRIES_GENDER_GAP <min-gap> - Mostra países com diferença percentual de género >= ao valor.
GET_TOP_POPULATION_INCREASE <year-start> <year-end> - Mostra os 5 maiores aumentos de população.
GET_DUPLICATE_CITIES <min_population> - Mostra cidades duplicadas com população >= ao valor.
GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min_population> - Mostra cidades duplicadas em países diferentes.
GET_CITIES_AT_DISTANCE <distance> <country> - Lista pares de cidades do mesmo país à distância indicada.
GET_CITIES_AT_DISTANCE2 <distance> <country> - Lista pares de cidades entre o país indicado e outros países.
GET_CITIES_ABOVE_AVERAGE_POPULATION <min_cities> (EXTRA) - Lista cidades acima da média mundial,
                                                           considerando apenas países com pelo menos min_cities cidades.


=======================================================
UTILIZAÇÃO DE IA GENERATIVA
=======================================================

Ferramenta: Claude / ChatGPT

Prompt: "Estou a implementar um projeto em Java com ficheiros CSV grandes. Estou a usar
ArrayLists para pesquisar países e cidades mas está lento. Quando devo usar HashMap vs
TreeMap? Dá-me um exemplo concreto."

Resposta resumida: HashMap para pesquisa O(1) por chave sem ordem (ex: país por nome/alfa2).
TreeMap para pesquisa O(log n) com chaves ordenadas automaticamente (ex: população por ano).

Como foi utilizado: Substituímos pesquisas lineares em ArrayList por HashMap para localizar
países por nome, alfa2 e id. Usámos TreeMap no GET_TOP_POPULATION_INCREASE para ordenar
dados de população por ano automaticamente.
