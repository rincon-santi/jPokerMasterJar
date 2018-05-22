
/* 
 * Copyright (C) 2018 Santiago Rincon Martinez <rincon.santi@gmail.com>
 * Based on work by 2016 David Pérez Cabrera <dperezcabrera@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.poker.sample.strategies;

import java.util.List;
import java.util.Map;
import org.poker.api.core.Card;
import org.poker.api.game.BetCommand;
import org.poker.api.game.GameInfo;
import org.poker.api.game.IStrategy;
import org.poker.api.game.PlayerInfo;
import org.poker.api.game.TexasHoldEmUtil;
import org.poker.api.game.TexasHoldEmUtil.BetCommandType;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class LaidBackExpertBot implements IStrategy{
    
    private final static int [][] SMALLBLINDTABLE={
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 19, 19},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 15, 13, 12},
        {20, 20, 20, 20, 20, 20, 20, 20, 17, 15, 12, 10,  9},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 11, 10,  9,  6},
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 15,  7,  5,  0},
        {20, 16, 13, 12, 17, 20, 20, 20, 20, 20, 11,  0,  0},
        {20, 15, 10,  8,  9, 11, 18, 20, 20, 20, 14,  0,  0},
        {20, 14,  9,  6,  5,  5,  8, 14, 20, 20, 20, 10,  0},
        {20, 13,  8,  6,  0,  0,  0,  0,  0, 20, 20, 14,  0},
        {20, 12,  7,  5,  0,  0,  0,  0,  0,  0, 20, 11,  0},
        {20, 11,  7,  5,  0,  0,  0,  0,  0,  0,  0, 20,  0},
        {20, 11,  7,  0,  0,  0,  0,  0,  0,  0,  0,  0, 14}
    };
    
    private final static int [][] BIGBLINDTABLE={
        {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20},
        {20, 20, 20, 20, 20, 20, 18, 15, 14, 13, 12, 11, 11},
        {20, 20, 20, 20, 20, 16, 13, 11,  9,  9,  8,  7,  7},
        {20, 20, 19, 20, 18, 13, 11,  9,  7,  6,  6,  6,  5},
        {20, 20, 15, 13, 20, 12,  9,  7,  6,  5,  5,  5,  0},
        {20, 17, 12,  9,  8, 20,  8,  7,  6,  5,  0,  0,  0},
        {20, 14,  9,  7,  6,  6, 20,  6,  5,  5,  0,  0,  0},
        {20, 13,  8,  6,  5,  5,  5, 20,  5,  5,  0,  0,  0},
        {20, 11,  7,  5,  0,  0,  0,  0, 20,  5,  0,  0,  0},
        {20, 10,  6,  5,  0,  0,  0,  0,  0, 20,  0,  0,  0},
        {18,  9,  6,  0,  0,  0,  0,  0,  0,  0, 20,  0,  0},
        {16,  8,  6,  0,  0,  0,  0,  0,  0,  0,  0, 20,  0},
        {15,  8,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0, 14}
    };

    private String name;
    private boolean smallBlind;
    //Aquí puedes crear más variables privadas
    
    public LaidBackExpertBot(){
        //Este es el constructor de la estrategia de juego
        //aquí deberías dar valores a las correspondientes
        //variables privadas, o, como mínimo, al nombre
        name="laid-back_expert";
    }
   

    @Override
    public BetCommand getCommand(GameInfo<PlayerInfo> state) {
        //Esta es la función más importante, en ella debes especificar
        //qué comando devolverá tu estrategia (tu jugador) según el estado
        //de la partida. Para ello tienes a tu disposición los métodos:
        //
        //  state.getCommunityCards() que devuelve la lista de las cartas
        //comunes a todos los jugadores (las del centro de la mesa), en formato
        //List<Card>, donde Card es una clase que a su vez tiene métodos
        //getSuit() para obtener el palo (en el enumerado SPADE, HEART, DIAMOND
        //CLUB; con ese orden), getRank() para obtener el número (en el
        //enumerado TWO, TRHEE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK,
        //QUEEN, KING, ACE; con ese orden) hashCode() para obtener un int a modo
        //de identificador único. También cuenta con un método toString() y un
        //método equals().
        //
        //  state.getDealer() que devuelve la posición del jugador que hace las
        //veces de dealer en la mano actual, como entero.
        //
        //  state.getPlayer(int i) que devuelve el jugador en la posición i.
        //Este jugador es una clase PlayerInfo, que cuenta por su parte con
        //funciones isActive() que devolverá false si está fold, all-in, o bien
        //out de la partida, y true en caso contrario; getName() que devuelve el
        //nombre del jugador como String; getChips(), que devuelve un long que
        //marca el número de fichas que le quedan al jugador, getBet() marcará
        //la ultima apuesta del jugador como un objeto de la clase BetCommand,
        //de la que hablaremos más tarde; getCards() devuelve las cartas que el
        //jugador tiene en la mano como array de tipo Card - se aconseja usar
        //esta función con responsabilidad, recuérdese que en una partida real
        //no se puede mirar las cartas del rival -, también se pueden consultar
        //las cartas de la mano por posición usando el método getCard(int i), la
        //clase también cuenta con su respectivo método toString(), con una
        //funcion getState, que devuelve el estado del jugador como PlayerState
        //(READY, OUT, FOLD, CHECK, CALL, RISE o ALL_IN), y con una funcion
        //getErrors() que devuelve un entero para representar el número de veces
        //que el jugador ha devuelto una apuesta errónea.
        //
        //  La clase BetCommand modula las apuestas, y cuenta con los métodos
        //getType(), que devuelve el tipo de apuesta como objeto del enumerado
        //BetCommandType (ERROR, TIMEOUT, FOLD, CHECK, CALL, RISE, ALL_IN), y
        //getChips() que devuelve un long con el número de chips apostado.
        //También cuenta con su respectivo toString().
        //
        //  state.getPlayers() devuelve el conjunto de jugadores de la partida
        //como una List<> de objetos tipo PlayerInfo.
        //
        //  state.getNumPlayers() devuelve un entero correspondiente al número
        //de jugadores de la partida.
        //
        //  state.getPlayerTurn() devuelve la posición del jugador cuyo turno
        //está en curso, en formato entero.
        //
        //  state.getRound() devuelve la ronda de la partida en la que se está,
        //en formato entero.
        //
        //  state.getGameState() devuelve el actual estadío de la partida 
        //representado mediante un objeto perteneciente al enumerado GameState
        //(PRE_FLOP, FLOP, TURN, RIVER, SHOWDOWN, END).
        //
        //  state.getSettings() devuelve un objeto Settings, que representa la
        //configuración de la partida. Se puede consultar esta configuración
        //mediante las funciones getMaxErrors(), que devuelve el máximo de
        //errores permitido antes de expulsar a un jugador de la partida, en
        //formato entero; getMaxPlayers(), que devuelve el máximo número de
        //jugadores permitido; getTime(), que devuelve el tiempo que se permite
        //tardar en decidir una apuesta cuando llega el turno de un jugador, en
        //formato long; getPlayerChip(), que devuelve la cantidad inicial de
        //chips de los jugadores, en formato long; getSmallBlind() y
        //getBigBlind() devuelven la ciega pequeña y grande respectivamente, en
        //formato long; getMaxRounds(), devuelve en formato entero el número
        //máximo de manos de duración de una partida, y 
        //getRounds4IncrementBlind(), que devuelve en formato entero cada qué
        //número de rondas se incrementa la ciega.
        //
        //
        //Utilizando la información sobre la partida que puedes obtener con los
        //métodos y funciones ya comentados, se estima información suficiente
        //para elaborar un algoritmo efectivo de juego.
        Card [] myCards=state.getPlayer(state.getPlayerTurn()).getCards();
        long myChips=state.getPlayer(state.getPlayerTurn()).getChips();
        boolean suited=false;
        if (myCards[0].getSuit().equals(myCards[1].getSuit())) suited=true;
        int [] enteros={
            toInt(myCards[0]),
            toInt(myCards[1])
        };
        if (suited) if (enteros[0]>enteros[1]){
            int aux=enteros[0];
            enteros[0]=enteros[1];
            enteros[1]=aux;
        } else if (enteros [0]<enteros[1]){
            int aux=enteros[0];
            enteros[0]=enteros[1];
            enteros[1]=aux;
        }
        int maxAp=0;
        if (smallBlind)
            maxAp=SMALLBLINDTABLE[enteros[0]][enteros[1]];
        else
            maxAp=BIGBLINDTABLE[enteros[0]][enteros[1]];
        boolean goAllIn=false;
        if (myChips <= (state.getSettings().getBigBlind()*maxAp)) goAllIn=true;
        if (smallBlind && goAllIn) return new BetCommand(BetCommandType.ALL_IN);
        else if (!goAllIn) return new BetCommand(BetCommandType.FOLD);
        else if (state.getPlayer((state.getPlayerTurn()+1)%2).isActive()) return new BetCommand(BetCommandType.ALL_IN);
        else return new BetCommand(BetCommandType.CALL);
    }

    private int toInt(Card card){
        switch (card.getRank()){
            case ACE:
                return 0;
            case KING:
                return 1;
            case QUEEN:
                return 2;
            case JACK:
                return 3;
            case TEN:
                return 4;
            case NINE:
                return 5;
            case EIGHT:
                return 6;
            case SEVEN:
                return 7;
            case SIX:
                return 8;
            case FIVE:
                return 9;
            case FOUR:
                return 10;
            case TRHEE:
                return 11;
            case TWO:
                return 12;
            default:
                return -1;
        }
    }

    @Override
    public void initHand(GameInfo<PlayerInfo> state) {
        //Este método, opcional, te permite modificar las variables privadas que
        //hayas decidido utilizar, o preparar tu estrategia, al principio de
        //cada mano, que será cuando se ejecute. En cuanto a los datos sobre la
        //partida, el objeto state de la clase IMaskedGameInfo es similar al que
        //ya utilizamos en la función getCommand().
        for (int i=0; i<state.getPlayers().size(); i++) if (name.equals(state.getPlayer(i).getName())){
            if (state.getPlayerTurn()==i) smallBlind=true;
            else smallBlind=false;
        }
    }
    
    @Override
    public void endHand(GameInfo<PlayerInfo> state) {
        //Este método, opcional, te permite modificar las variables privadas que
        //hayas decidido utilizar, o preparar tu estrategia, al final de
        //cada mano, que será cuando se ejecute. En cuanto a los datos sobre la
        //partida, el objeto state de la clase IMaskedGameInfo es similar al que
        //ya utilizamos en la función getCommand().
    }
    
    @Override
    public void endGame(Map<String, Double> scores) {
        //Este método, opcional, te permite realizar las acciones que consideres
        //al final de la partida, que será cuando se ejecute. En cuanto a los
        //datos facilitados a este fin, scores es una tabla que une cada jugador
        //(en formato String) con su respectivo índice de victoria, entre 0 y 1,
        //teniendo en cuenta que el bote se repartirá proporcionalmente a éste
        //índice. El índice de victoria tiene formato Double.
    }

    @Override
    public void check(List<Card> communityCards) {
        //Este método, opcional, te permite modificar las variables privadas que
        //hayas decidido utilizar, o preparar tu estrategia, justo antes de que
        //se compruebe cuál es la mano ganadora en una ronda, que será cuando se
        //ejecute. En cuanto a los datos recibidos a este fin, se trata de un
        //tipo List<Card> que almacena las cartas comunes (las del centro de la
        //mesa).
    }

    @Override
    public void onPlayerCommand(String player, BetCommand betCommand) {
        //Este método, opcional, te permite modificar las variables privadas que
        //hayas decidido utilizar, o preparar tu estrategia, cuando un jugador
        //envíe una jugada, que será cuando se ejecute. Los argumentos que
        //recibe este método son un String con el nombre del jugador y un objeto
        //tipo BetCommand (explicado en la función getCommand), representando la
        //jugada realizada.
    }
    
    
    @Override
    public String getName(){
        //Es necesario el que haya un getter que facilite el nombre del jugador
        return name;
    }
}
