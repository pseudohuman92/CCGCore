/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.visitor.sets.set1;

import com.visitor.card.types.Unit;
import com.visitor.helpers.Hashmap;

import java.io.Serializable;

/**
 *
 * @author pseudo
 */
public class Dog extends Unit implements Serializable {

    public Dog(String owner) {
        super("Dog", 
                0, new Hashmap(), 
                "", 
                2, 4,
                owner);
    }
}
