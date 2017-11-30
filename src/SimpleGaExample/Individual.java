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
public class Individual {

    private int[] gene;
    private int fitness = 0;

    public Individual(Individual i) {
        this.fitness = i.getFitness();
        this.gene = i.getGene();
    }

    public Individual(int fitness, int[] gene) {
        this.fitness = fitness;
        this.gene = gene;
    }

    public Individual() {
    }

    public void setGene(int[] gene) {
        this.gene = gene;
    }

    public void setGeneIndex(int g,int gene) {
        this.gene[g] = gene;
    }

    public void createGene(int n, int conL) {
        this.gene = new int[n];
        for (int i = 0; i < n; i++) {
            Random rand = new Random();
            //int r = rand.nextInt(2);
            //this.gene[i] = r;
            if (((i + 1) % (conL+1)) == 0) {
                this.gene[i] = rand.nextInt(2);
            } else {
                this.gene[i] = rand.nextInt(3);
            }
        }
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getFitness() {
        return this.fitness;
    }

    public int[] getGene() {
        return gene;
    }
    
     public int getGeneIndex(int g) {
       return this.gene[g];
    }

    
    @Override
    public String toString() {
        return "Individual{" + "gene=" + Arrays.toString(this.gene) + ", fitness=" + this.fitness + '}';
    }

}
