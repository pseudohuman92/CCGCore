/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.card.properties;

import com.visitor.game.Game;

/**
 *
 * @author pseudo
 */
public interface Damageable {
    
    public abstract int dealDamage(Game game, int damageAmount);
    
}