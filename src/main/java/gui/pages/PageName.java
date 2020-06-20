/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.pages;

/**
 *
 * @author Matt
 */
public enum PageName {
    OUTPUT("output"),
    INDIV_ACC("give drive access"),
    PARSE_FORM("parse certification form");
    
    private final String value;
    
    private PageName(String displayValue){
        value = displayValue;
    }
    
    public final String getDisplayValue(){
        return value;
    }
}
