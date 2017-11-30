/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleGaExample;

import java.util.Arrays;

/**
 *
 * @author chigolumobikile
 */
public class Rule {
    private int[] condition;
    private int output;

    public Rule(int[] condition, int output) {
        this.condition = condition;
        this.output = output;
    }

    public int[] getCondition() {
        return condition;
    }

    public int getOutput() {
        return output;
    }

    public void setCondition(int[] condition) {
        this.condition = condition;
    }

    public void setConditionGene(int index, int gene) {
        this.condition[index] = gene;
    }

    public void setOutput(int output) {
        this.output = output;
    }
    

    @Override
    public String toString() {
        return "Rule{" + "condition=" + Arrays.toString(condition) + ", output=" + output + '}';
    }  
}

