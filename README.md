# batalha-naval-UDP

Na presente versão, para rodar o jogo é necessário rodar o src/application/server/Server e duas vezes src/application/game/Player (player1 e player2)<br>
Para tanto, pode-se utilizar uma IDE e executar via console ou executar no terminal do seu Sistema Operacional.<br>
<br>
Exemplo:<br>
<img src=”https://github.com/marceloalvescl/batalha-naval-UDP/blob/main/readme-images/Em-Execucao.png”>
![](https://github.com/marceloalvescl/batalha-naval-UDP/blob/main/readme-images/Em-Execucao.png)

Ressalto que a implementação da lógica de comunicação via UDP (src/application/server/Server e o método sendPosition em src/application/game/Player), foram baseados na implementação presente no seguinte link:https://www.kelvinsantiago.com.br/criar-datagram-socket-em-udp-e-socket-tcp-em-java/
