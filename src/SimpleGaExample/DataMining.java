/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimpleGaExample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author chigolumobikile
 */
public class DataMining {

    /**
     * @param args the command line arguments
     */
    static int numR = 10;
    static int conL = 5;
    static int dataNum = 32;
    static int geneSize = (conL + 1) * numR;
    static int pop = 100;
    static int generationNum = 200;
    static PrintWriter pw;
    static double crossRate = 0.6;
    static double m_rate = 0.01;

    public static void main(String[] args) throws FileNotFoundException {
        pw = new PrintWriter(new File("test1.csv"));

        // READ IN DATA FILE
        Scanner scan = new Scanner(DataMining.class.getResourceAsStream("data1.txt"));
        Data[] database = new Data[dataNum];

        int aa = 0;
        while (scan.hasNextLine()) {
            String[] input = scan.nextLine().split(" ");
            String[] splitString = input[0].split("");
            int[] var = new int[conL];

            for (int i = 0; i < splitString.length; i++) {
                //parse and store each value into int[] to be returned
                var[i] = Integer.parseInt(splitString[i]);
            }
            Data d1 = new Data(var, Integer.parseInt(input[1]));
            database[aa] = d1;
            aa++;
        }

        //INITIALIZE POPULATION
        Individual[] population = new Individual[pop];
        Individual[] offspring = new Individual[pop];
        for (int i = 0; i < pop; i++) {
            population[i] = new Individual();
            population[i].createGene(geneSize, conL);
            int tempFitness1 = fitnessFunction(population[i], database);
            population[i].setFitness(tempFitness1);
        }

        //---------------------Start GA----------------------------
        for (int i = 0; i < generationNum; i++) {
            System.out.println("Generation" + (i + 1));

            //SELECTION
            for (int j = 0; j < pop; j++) {
                offspring[j] = new Individual();
                Random ran = new Random();
                int parent1 = ran.nextInt(pop);
                int parent2 = ran.nextInt(pop);

                int p1 = population[parent1].getFitness();
                int p2 = population[parent2].getFitness();

                if (p1 > p2) {
                    offspring[j] = new Individual(population[parent1]);
                } else {
                    offspring[j] = new Individual(population[parent2]);
                }
            }

            //CROSSOVER
            Random ran = new Random();
            double crossPoint;
            for (int index = 0; index < pop; index += 2) {
                int cross = ran.nextInt(geneSize);
                crossPoint = ran.nextDouble();
                if (crossPoint < crossRate) {
                    int[] temp1 = offspring[index].getGene();
                    int[] temp2 = offspring[index + 1].getGene();
                    int[] firstChild = new int[geneSize];
                    int[] secondChild = new int[geneSize];

                    for (int j = 0; j < geneSize; j++) {
                        if (j < cross) {
                            firstChild[j] = temp2[j];
                            secondChild[j] = temp1[j];
                        } else {
                            firstChild[j] = temp1[j];
                            secondChild[j] = temp2[j];
                        }
                    }
                    offspring[index] = new Individual();
                    offspring[index + 1] = new Individual();
                    offspring[index].setGene(firstChild);
                    offspring[index + 1].setGene(secondChild);
                }
            }//end crossover loop

            //MUTATION
            //System.out.println("\nMutation\n");
            Random r = new Random();
            //int leastFittest = 0;
            for (int y = 0; y < pop; y++) {
                for (int j = 0; j < geneSize; j++) {
                    double mutation = r.nextDouble();
                    if (mutation < m_rate) {
                        if (((j + 1) % (conL + 1)) == 0) {
                            offspring[y].getGene()[j] = r.nextInt(2);
                        } else {
                            offspring[y].getGene()[j] = r.nextInt(3);
                        }
                    }
                }
            }//end mutation loop

            //find the best individual in population
            int fittest = 0;
            for (int w = 0; w < pop; w++) {
                if (population[w].getFitness() > population[fittest].getFitness()) {
                    fittest = w;
                }
            }
            Individual tempBest = new Individual(population[fittest]);

            //copy offspring to population
             for (int w = 0; w < pop; w++) {
                for (int g = 0; g < geneSize; g++) {
                    population[w].setGeneIndex(g, offspring[w].getGeneIndex(g));
                }
                population[w].setFitness(offspring[w].getFitness());
            }

            //find the worst individual in population
            int worst = 0;
            for (int w = 0; w < pop; w++) {
                if (population[w].getFitness() < population[worst].getFitness()) {
                    worst = w;
                }
            }
            //set worst to best
            if (population[worst].getFitness() < tempBest.getFitness()) {
                for(int gen = 0;gen < geneSize;gen++){
                    population[worst].setGeneIndex(gen, tempBest.getFitness());
                }
                population[worst].setFitness(tempBest.getFitness());
            }

            //Calculate best fitness and average fitness
            int totalFit = 0, meanFit = 0, bestFit = 0;
            for (int t = 0; t < pop; t++) {
                int tempFitness1 = fitnessFunction(population[t], database);
                population[t].setFitness(tempFitness1);
                totalFit += tempFitness1;
                if (bestFit < tempFitness1) {
                    bestFit = tempFitness1;
                }
            }
            meanFit = (totalFit / pop);

            System.out.println("totalFit = " + totalFit);
            System.out.println("meanFit = " + meanFit);
            System.out.println("bestFit = " + bestFit + "\n");

            try {
                writeToCSV(population, i, bestFit, meanFit);
            } catch (FileNotFoundException fe) {
                System.out.println("File not found!!!");
            }
        }//end - generation loop
        pw.close();
    }

    public static int fitnessFunction(Individual ind, Data[] database) {
        int fitness = 0;
        Rule[] rulebase = getRulebase(ind);

        for (int i = 0; i < dataNum; i++) {
            for (int j = 0; j < numR; j++) {
                if (matches(rulebase[j].getCondition(), database[i].getVariable())) {
                    if (rulebase[j].getOutput() == database[i].getCategory()) {
                        fitness++;
                    }
                    break;
                    //j = numR;
                }
            }
        }
        return fitness;
    }

    public static Rule[] getRulebase(Individual ind) {
        int k = 0;
        Rule[] rulebase = new Rule[numR];
        for (int i = 0; i < numR; i++) {
            rulebase[i] = new Rule(new int[conL], 0);
            for (int j = 0; j < conL; j++) {
                rulebase[i].setConditionGene(j, ind.getGene()[k++]);
            }
            rulebase[i].setOutput(ind.getGene()[k++]);
        }
        return rulebase;
    }

    public static boolean matches(int[] cond, int[] var) {

        boolean matches = true;
        for (int i = 0; i < cond.length; i++) {
            if (cond[i] != var[i]) {
                if (cond[i] != 2) {
                    matches = false;
                }
            }
        }
        return matches;
    }

    public static void writeToCSV(Individual[] population, int genNum, int bestFitness, int averageFitness) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        sb.append(genNum);
        sb.append(',');
        sb.append(bestFitness);
        sb.append(',');
        sb.append(averageFitness);
        sb.append('\n');

        pw.write(sb.toString());
        //System.out.println("done!");
    }
}
