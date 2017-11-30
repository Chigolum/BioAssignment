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
public class Rule2 {

    private float[] condition;
    private int output;


    public Rule2(float[] condition, int output) {
        this.condition = condition;
        this.output = output;
    }

    public float[] getCondition() {
        return condition;
    }

    public float getOutput() {
        return output;
    }

    public void setCondition(float[] condition) {
        this.condition = condition;
    }
    public void setConditionGene2(int index, float gene) {
        this.condition[index] = gene;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "Rule2{" + "condition=" + condition + ", output=" + output + '}';
    }

    
}
