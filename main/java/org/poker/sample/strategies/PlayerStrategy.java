
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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.poker.api.core.Card;
import org.poker.api.game.BetCommand;
import org.poker.api.game.GameInfo;
import org.poker.api.game.IStrategy;
import org.poker.api.game.PlayerInfo;
import org.poker.api.game.TexasHoldEmUtil;
import org.poker.api.game.TexasHoldEmUtil.BetCommandType;
import org.poker.gui.ImageManager;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public class PlayerStrategy implements IStrategy{

    private String name;
    //Aquí puedes crear más variables privadas
    
    public PlayerStrategy(){
        name="Player";
    }
   

    @Override
    public BetCommand getCommand(GameInfo<PlayerInfo> state) {
        boolean done=false;
        String [] options ={
            "Fold",
            "Call",
            "Raise",
            "All In"
        };
        Icon icon= new ImageIcon(ImageManager.INSTANCE.getImage(ImageManager.IMAGES_PATH+"poker-chip.png"));
        String finalOpt= (String) JOptionPane.showInputDialog(null, "Select your command", "Command", JOptionPane.DEFAULT_OPTION, icon, options, options[0]);
        while (!done) switch (finalOpt){
            case "Fold":
                done=true;
                return new BetCommand(BetCommandType.FOLD);
            case "Call":
                done=true;
                return new BetCommand(BetCommandType.CALL);
            case "Raise":
                try {
                done=true;
                long chips = Long.parseLong(JOptionPane.showInputDialog(null, "Number of chips", "Number Of Chips", JOptionPane.DEFAULT_OPTION), 10);
                return new BetCommand(BetCommandType.RAISE, chips);
                }catch(Exception e){
                    done=false;
                    JOptionPane.showMessageDialog(null, "Please introduce a valid number", "Error", JOptionPane.ERROR_MESSAGE); 
                }
            case "All In":
                done=true;
                return new BetCommand(BetCommandType.ALL_IN);
            default:
                return null;
        }
        return null;
    }

    @Override
    public String getName(){
        return name;
    }
}
