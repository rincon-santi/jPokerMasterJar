/* 
 * Copyright (C) 2018 Santiago Rincon Martinez <rincon.santi@gmail.com>
 * Based on work of 2016 David Pérez Cabrera <dperezcabrera@gmail.com>
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
package org.poker.main;

import org.poker.sample.strategies.RandomStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.poker.api.game.IGameController;
import org.poker.api.game.IStrategy;
import org.poker.api.game.Settings;
import org.poker.engine.controller.GameController;
import org.poker.gui.TexasHoldEmView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Santiago Rincon Martinez <rincon.santi@gmail.com>
 */
public final class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    public static final int VISUAL_GAME=1;
    public static final int NO_VISUAL_GAME=2;

    private MainController() {
    }

    public static Map<String, Double> run(IStrategy pl1, IStrategy pl2, int mode, int iterations, long BB, long initialChips) throws Exception {
        IStrategy strategyMain = pl1;
        TexasHoldEmView texasHoldEmView = new TexasHoldEmView(strategyMain);
        if (mode==VISUAL_GAME) texasHoldEmView.setVisible(true);
        strategyMain = texasHoldEmView.getStrategy();
        List<IStrategy> strategies = new ArrayList<>();
        strategies.add(strategyMain);
        strategies.add(pl2);
        Collections.shuffle(strategies);
        Map<String,Double> scores=new HashMap<String, Double>();
        scores.put(pl1.getName(), new Double(0));
        scores.put(pl2.getName(), new Double(0));
        for (int i=0; i<iterations; i++) {
            Settings settings = new Settings();
            settings.setMaxErrors(3);
            settings.setMaxPlayers(2);
            settings.setMaxRounds(1000);
            settings.setTime(1000000000);
            settings.setPlayerChip(BB*initialChips);
            settings.setRounds4IncrementBlind(20);
            settings.setSmallBlind(BB / 2);
            IGameController controller = new GameController();
            controller.setSettings(settings);
            for (IStrategy strategy : strategies) {
                controller.addStrategy(strategy);
            }
            controller.start();
            controller.waitFinish();
            controller.waitFinish();
            Double scorepl1=scores.get(pl1.getName())+controller.getScores().get(pl1.getName());
            Double scorepl2=scores.get(pl2.getName())+controller.getScores().get(pl2.getName());
            scores=new HashMap();
            scores.put(pl1.getName(), scorepl1);
            scores.put(pl2.getName(), scorepl2);
            if (mode==VISUAL_GAME) Thread.sleep(3000);
        }
        texasHoldEmView.dispose();
        return scores;
    }
}
