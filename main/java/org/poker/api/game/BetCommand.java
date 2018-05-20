/* 
 * Copyright (C) 2018 Santiago Rincon Martinez <rincon.santi@gmail.com>
 * based on work by 2016 David Pérez Cabrera <dperezcabrera@gmail.com>
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
package org.poker.api.game;

import org.poker.api.game.TexasHoldEmUtil.BetCommandType;
import org.util.exceptions.ExceptionUtil;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 * @since 1.0.0
 */
public class BetCommand {

    private final BetCommandType type;
    private long chips;

    public BetCommand(BetCommandType type) {
        this(type, 0);
    }

    public BetCommand(BetCommandType type, long chips) {
        ExceptionUtil.checkNullArgument(type, "type");
        ExceptionUtil.checkMinValueArgument(chips, 0L, "chips");
        this.type = type;
        this.chips = chips;
    }

    public BetCommandType getType() {
        return type;
    }

    public long getChips() {
        return chips;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }

    @Override
    public String toString() {
        return String.join("{class:'BetCommand', type:'", type.toString(), "', chips:", Long.toString(chips), "}");
    }
}
