# Monopoly

## Preparação
 - Compile com o comando make / make run
 - Cadastre de 2 até 6 jogadores
 - Todos rolam 2 dados e quem tiver maior soma começa
 - Os próximo jogadores são escolhidos pela ordem de cadastro

## Objetivo
 - Levar os adversários a falência, sendo então eliminados

## Visão Geral
 - Os jogadores rolam os dados e saltam o número de casas por eles indicado
 - O tabuleiro é composto por cinco tipos de casas: Terreno, Companhia, Receita, Prisão e Sorte/Azar
 ### Terrenos
 - Terrenos são dividos em grupos de três cores: Vermelho, Verde e Azul. Ao cair em uma casa desse tipo, o jogador pode comprar seu título de posse. Caso tenha todos os terrenos de um mesmo grupo, ele pode fazer construções nos terrenos, aumentando assim o preço do Aluguel
 - Quando um jogador parar em um terreno que já for de outro, deve então pagar o valor do Aluguel para o respectivo
 ### Companhias
 - Os jogadores também podem adquirir o título de posse das companhias
 - Quando um jogador parar em uma companhia que já for de outro, deverá pagar a ele a multiplição do resultado da soma dos dados pelo valor da companhia 
 ### Receita
 - Ao passar na casa inicial, o jogador pagará imposto para o estado em relação a quantidade de propriedades obtidas do tabuleiro

| Quantidade de Móveis | Nível Fácil (10%) | Nível Médio (15%) | Nível Difícil (20%) |
| :-------------------: | :---------------: | :---------------: | :------------------: |
| 1                     | 10                | 15                | 20                  |
| 2                     | 20                | 30                | 40                  |
| 3                     | 30                | 45                | 60                  |
| 4                     | 40                | 60                | 80                  |
| 5                     | 50                | 75                | 100                 |
| 6                     | 60                | 90                | 120                 |
| 7                     | 70                | 105               | 140                 |
| 8                     | 80                | 120               | 160                 |
| 9                     | 90                | 135               | 180                 |
| 10                    | 100               | 150               | 200                 |
| 11                    | 110               | 165               | 220                 |
| 12                    | 120               | 180               | 240                 |
| 13                    | 130               | 195               | 260                 |
| 14                    | 140               | 210               | 280                 |
| 15                    | 150               | 225               | 300                 |

 ### Prisão
 - Ao entra na prisão, terá três formas de sair:
 1. Pagando para o estado
 2. Jogar dados e obter uma dupla
 3. Após de 3 rodadas (caso não consegiu nenhuma formação de duplas nos dados entre as 3 rodadas)
 ### Sorte/Azar
 - O jogo seleciona aleatoriamente uma carta do monte. A carte pode tanto premiar quanto punir o jogador da vez.

## Sobrevivência
 - Está em dívida? O banco pode ajudar! 
 - Na falta de dinheiro, o jogador deve começar vendendo as suas propriedades nos terrenos para o banco por 50% do valor da construção
 - Em seguida, pode vender seus títulos de posse para o banco pelo valor original pago. É possível hipotecar os títulos, de forma que o banco paga 50% por eles e o jogador não recebe mais seus Aluguéis, mas pode o recuperar também por 50% do valor do título

## Falência
 - O jogador é eliminado caso não tenha mais recursos para lidar com suas dídivas. Gerencie bem suas finanças!