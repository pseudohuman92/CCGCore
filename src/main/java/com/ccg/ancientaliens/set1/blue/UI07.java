/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ccg.ancientaliens.set1.blue;

import com.ccg.ancientaliens.card.types.Activation;
import com.ccg.ancientaliens.card.types.Item;
import com.ccg.ancientaliens.game.Game;
import static com.ccg.ancientaliens.protocol.Types.Knowledge.BLUE;
import com.ccg.ancientaliens.helpers.Hashmap;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author pseudo
 */
public class UI07 extends Item {
    
    int x;
    
    public UI07(String owner) {
        super("UA07", 1, new Hashmap(BLUE, 2), 
        "X, Sacrifice ~: \n" +
        "  Look at opponent's deck. \n" +
        "  Choose a card that costs X or less from it. \n" +
        "  Transform chosen card into junk.", owner);
    }

    @Override
    public boolean canActivate(Game game) {
        return true;
    }

    @Override
    public void activate(Game game) {
        x = game.selectX(controller, game.getPlayer(controller).energy);
        game.destroy(id);
        game.addToStack(new Activation(controller, 
            "  Look at opponent's deck. \n" +
            "  Choose a card that costs X or less from it. \n" +
            "  Transform chosen card into junk.",
        (g, cx) -> {
            ArrayList <UUID> canSelected = new ArrayList<>();
            g.getZone(g.getOpponentName(controller), "deck").forEach(c -> {
                if (c.cost <= x){
                    canSelected.add(c.id);
                }
            });
            ArrayList<UUID> s = g.selectFromListUpTo(controller, g.getZone(g.getOpponentName(controller), "deck"), canSelected, 1);
            if (!s.isEmpty()){
                game.transformToJunk(s.get(0));
            }
        }));
    }
    
}
