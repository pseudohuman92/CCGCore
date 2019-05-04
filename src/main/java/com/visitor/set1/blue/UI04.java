/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.visitor.set1.blue;

import com.visitor.card.types.Activation;
import com.visitor.card.types.Item;
import com.visitor.game.Game;
import com.visitor.helpers.Arraylist;
import static com.visitor.protocol.Types.Knowledge.BLUE;
import com.visitor.set1.additional.AI02;
import com.visitor.helpers.Hashmap;


/**
 *
 * @author pseudo
 */
public class UI04 extends Item {
    
    public UI04 (String owner){
        super("UI04", 1, new Hashmap(BLUE, 1), 
                "1, Discard 1: Transform ~ into AI02.", owner);
        subtypes.add("Kit");
    }

    @Override
    public boolean canActivate(Game game) {
        return game.hasCardsIn(controller, "hand", 1)
                && game.hasEnergy(controller, 1);
    }
    
    @Override
    public void activate(Game game) {
        game.discard(controller, 1);
        game.spendEnergy(controller, 1);
        game.addToStack(new Activation(controller, "Transform ~ into AI02.",
            (x) -> { 
                if(game.isIn(controller, id, "play"))
                    game.replaceWith(this, new AI02(this));
        }, new Arraylist<>(id)));
    }
}