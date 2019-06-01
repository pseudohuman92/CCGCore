/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.set1;

import com.visitor.card.types.Spell;
import com.visitor.card.types.Item;
import com.visitor.game.Game;
import com.visitor.helpers.Hashmap;
import com.visitor.helpers.Predicates;
import static com.visitor.protocol.Types.Knowledge.RED;
import java.util.UUID;

/**
 *
 * @author pseudo
 */
public class Extraction extends Spell {

    UUID target; 
    
    public Extraction(String owner) {
        super("Extraction", 1, new Hashmap(RED, 2), 
            "Additional Cost \n" +
            "  Return an item you control to your hand. \n" +
            "Deal 4 damage.", owner);
    }
    
    @Override
    public boolean canPlay (Game game){
        return super.canPlay(game) && game.hasInstancesIn(controller, Item.class, "play", 1);
    }
    
    @Override
    public void play (Game game){
        target = game.selectFromZone(controller, "play", Predicates::isItem, 1, false).get(0);
        game.getCard(target).returnToHand(game);
        super.play(game);
    }  
    
    @Override
    public void resolveEffect (Game game){
        target = game.selectDamageTargets(controller, 1, false).get(0);
        game.dealDamage(id, target, 4);
    }    
}