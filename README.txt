LINK DO VÍDEO
[coloca aqui o link do YouTube depois de gravares]

COMANDO CRIATIVO
Nome: GET_CITIES_ABOVE_AVERAGE_POPULATION <min_cities>
Descrição: Retorna as cidades cuja população está acima da média de população
das cidades, considerando apenas países que tenham pelo menos min_cities cidades.
O parâmetro min_cities afeta a média calculada e os países considerados,
alterando os resultados.

Exemplo (com ficheiros gigantes):
GET_CITIES_ABOVE_AVERAGE_POPULATION 5
london:United Kingdom
shanghai:China
...

GET_CITIES_ABOVE_AVERAGE_POPULATION 50
shanghai:China
...

UTILIZAÇÃO DE IA GENERATIVA
Ferramenta: Claude Sonnet 4.6 (claude.ai)

Prompt utilizado:
"Da me o case GET_CITIES_ABOVE_AVERAGE_POPULATION para o execute() em Java.
O comando recebe um inteiro min_cities e retorna as cidades com população acima
da média, considerando apenas países com pelo menos min_cities cidades."

Resposta da IA:
[A IA gerou o código do case completo com HashMap para contar cidades por país,
cálculo da média e filtragem das cidades acima da média]

Como foi usada:
O código gerado foi usado diretamente no case do execute() com pequenas
adaptações para seguir o estilo do projeto. A estrutura de HashMaps sugerida
pela IA foi mantida por ser eficiente.