/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structs;

/**
 *
 * @author Matt
 */
public enum Level {
    INTRO("Intro"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced");
    
    private final String text;
    private Level(String displayValue){
        text = displayValue;
    }
    
    @Override
    public String toString(){
        return text;
    }
}
