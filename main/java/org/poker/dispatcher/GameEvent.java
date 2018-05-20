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
package org.poker.dispatcher;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 * @since 1.0.0
 * 
 * @param <E>
 */
public class GameEvent<E extends Enum> {

    private E type;
    private String source;
    private Object payload;

    public GameEvent(E type, String source) {
        this(type, source, null);
    }

    public GameEvent(E type, String source, Object payload) {
        this.source = source;
        this.type = type;
        this.payload = payload;
    }

    public String getSource() {
        return source;
    }

    public E getType() {
        return type;
    }

    public Object getPayload() {
        return payload;
    }
}
