/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA3;

import java.util.Arrays;

/**
 *
 * @author chigolumobikile
 */
public class Data3 {

    private float[] variable;
    private int category;

    public Data3(float[] variable, int category) {
        this.variable = variable;
        this.category = category;
    }


    public float[] getVariable() {
        return variable;
    }

    public void setVariable(float[] variable) {
        this.variable = variable;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "variable=" + Arrays.toString(variable)+ ", category=" + category + "\n";
    }

}
