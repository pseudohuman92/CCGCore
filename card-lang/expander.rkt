#lang br
(define-macro (bf-module-begin PARSE-TREE)
  #'(#%module-begin
     PARSE-TREE))
(provide (rename-out [bf-module-begin #%module-begin]))

(define-macro (card-list CARD ...)
  #'(list CARD ...))

(define-macro (card _ ARG ...)
  #'(process-card ARG ...))

(define (process-card
              name-arg
              type-arg
              [subtypes empty]
              [cost-arg #f]
              [knowledge-cost #f]
              [attack-arg #f]
              [health-arg #f]
              [card-text ""]
              [keywords empty]
              [ability empty]
              [set-arg "noset"])
  
print (string-replace
          (string-replace
           (string-replace template
                           "<class-name>" name-arg)
           "<type>" type-arg)
          "<cost>" cost-arg))



(define-macro (name ARG) #''ARG)
(define-macro (type ARG) #''ARG)
(define-macro (cost ARG) #''ARG)
(define-macro (attack ARG) #''ARG)
(define-macro (health ARG) #''ARG)

(provide card card-list process-card name type cost attack health)

(define template "package com.visitor.sets.<set>;
import com.visitor.*;
import com.visitor.card.properties.Combat.CombatAbility.*;
import com.visitor.protocol.Types.Knowledge.*

/**
 * Generated by card-lang
 */
public class <class-name> extends <type> {

	public <class-name> (Game game, String owner) {
		super(game, \"<card-name>\",
				<cost>,
                                new CounterMap(YELLOW, 3),
				<card-text>,
				<attack>, <block>,
				owner, <keywords>);
	}
}")