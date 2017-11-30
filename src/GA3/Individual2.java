/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA3;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author chigolumobikile
 */
public class Individual2 {

    private float[] gene;
    private int fitness;

    public Individual2(float[] gene, int fitness) {
        this.gene = gene;
        this.fitness = fitness;
    }

    public Individual2(Individual2 ind) {
        this.gene = ind.getGene();
        this.fitness = ind.getFitness();
    }

    public Individual2() {
    }

    public void createGene(int n) {
        this.gene = new float[n];
        for (int i = 0; i < n; i++) {
            Random rand = new Random();
            this.gene[i] = rand.nextFloat();
        }
    }

    public float[] getGene() {
        return gene;
    }

    public int getFitness() {
        return fitness;
    }

    public void setGene(float[] gene) {
        this.gene = gene;
    }

    public void setGeneIndex(int g, float gene) {
        this.gene[g] = gene;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
     public float getGeneIndex(int g) {
       return this.gene[g];
    }


    @Override
    public String toString() {
        return "Individual2{" + "gene=" + Arrays.toString(gene) + ", fitness=" + fitness + '}';
    }

}
