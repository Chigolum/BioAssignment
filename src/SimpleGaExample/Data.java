/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleGaExample;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author chigolumobikile
 */
public class Data {
    private int[] variable;
    private int category;

    public Data(int[] variable, int category) {
        this.variable = variable;
        this.category = category;
        
    }

    public void setVariable(int[] variable) {
        this.variable = variable;
    }

    public void setCategory(int category) {
        this.category = category;
    }
    
    

    public int[] getVariable() {
        return variable;
    }

    public int getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return "Data{" + "variable=" + Arrays.toString(variable) + ", category=" + category + '}';
    }
}
