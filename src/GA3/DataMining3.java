/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GA3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author chigolumobikile
 */
public class DataMining3 {

    /**
     * @param args the command line arguments
     */
    static int numR = 10;
    static int conL = 12;
    static int dataLength = 6;
    static int dataNum = 2000;
    static int geneSize = (conL + 1) * numR;
    static int pop = 400;
    static int generationNum = 1000;
    static PrintWriter pw;
    static double crossRate = 0.0;
    static double m_rate = 0.01;

    static int[][] mA = new int[5][generationNum];
    static int[][] bA = new int[5][generationNum];

    static int totalFit = 0;
    static int meanFit = 0;
    static int bestFit = 0;
    static int[] meanFitArray = new int[generationNum];
    static int[] bestFitArray = new int[generationNum];
    static int[] totalFitArray = new int[generationNum];
    static PrintWriter printOut;

    public static void main(String[] args) throws FileNotFoundException {
        pw = new PrintWriter(new File("test3.csv"));
        // READ IN DATA FILE
        Scanner scan = new Scanner(DataMining3.class.getResourceAsStream("data3.txt"));
        Data3[] database = new Data3[dataNum];
        int aa = 0;
        while (scan.hasNextLine()) {
            String[] input = scan.nextLine().split(" ");
            float[] var = new float[dataLength];
            int category = 0;
            for (int i = 0; i < input.length; i++) {
                if (i == (input.length - 1)) {
                    category = Integer.parseInt(input[i]);
                } else {
                    var[i] = Float.parseFloat(input[i]);
                }
            }
            Data3 d1 = new Data3(var, category);

            database[aa] = d1;
            //System.out.println(database[aa].toString());
            aa++;
        }

        for (int q = 0; q < 5; q++) {

            //INITIALIZE POPULATION
            Individual2[] population = new Individual2[pop];
            Individual2[] offspring = new Individual2[pop];

            for (int i = 0; i < pop; i++) {
                //System.out.println(Arrays.toString(gene1));
                Individual2 ind = new Individual2();
                ind.createGene(geneSize);
                fitnessFunction(ind, database);
                population[i] = ind;
            }

            System.out.println("\n");
            //System.exit(0);

            //---------------------Start GA----------------------------
            for (int i = 0; i < generationNum; i++) {
                System.out.println("Generation" + (i + 1));
                //SELECTION
                //System.out.println("Selection\n");

                for (int j = 0; j < pop; j++) {
                    offspring[j] = new Individual2();
                    Random ran = new Random();
                    int parent1 = ran.nextInt(pop);
                    int parent2 = ran.nextInt(pop);

                    int p1 = population[parent1].getFitness();
                    int p2 = population[parent2].getFitness();

                    if (p1 > p2) {
                        offspring[j] = new Individual2(population[parent1]);
                    } else {
                        offspring[j] = new Individual2(population[parent2]);
                    }
                }

                //CROSSOVER
                //System.out.println("\n\n Crossover\n\n");
                //make a crossover point
                Random ran = new Random();
                int crossPoint;
                //System.out.println(crossPoint);
                for (int index = 0; index < pop; index += 2) {
                    crossPoint = ran.nextInt(geneSize);
                    if (crossPoint < crossRate) {
                        float[] temp1 = offspring[index].getGene();
                        float[] temp2 = offspring[index + 1].getGene();
                        float[] firstChild = new float[geneSize];
                        float[] secondChild = new float[geneSize];

                        for (int j = 0; j < geneSize; j++) {
                            if (j < crossPoint) {
                                firstChild[j] = temp1[j];
                                secondChild[j] = temp2[j];
                            } else {
                                firstChild[j] = temp2[j];
                                secondChild[j] = temp1[j];
                            }
                        }
                        offspring[index] = new Individual2();
                        offspring[index + 1] = new Individual2();
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
                            int randomInt = r.nextInt(2);
                            float randomFloat = r.nextFloat();
                            if (randomInt == 0) {
                                float plus = (offspring[y].getGene()[j] + randomFloat);
                                offspring[y].setGeneIndex(j, plus);
                            } else {
                                float minus = (offspring[y].getGene()[j] - randomFloat);

                                offspring[y].setGeneIndex(j, minus);
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
                Individual2 tempBest = new Individual2(population[fittest]);

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
                    for (int gen = 0; gen < geneSize; gen++) {
                        population[worst].setGeneIndex(gen, tempBest.getFitness());
                    }
                    population[worst].setFitness(tempBest.getFitness());
                }

                //Calculate best fitness and average fitness
                int totalFit = 0, meanFit = 0, bestFit = 0;
                for (int t = 0; t < pop; t++) {
                    int tempFitness1 = fitnessFunction(population[t], database);
                    population[t].setFitness(tempFitness1);
                    population[t].setFitness(tempFitness1);
                    totalFit += tempFitness1;
                    if (bestFit < tempFitness1) {
                        bestFit = tempFitness1;
                    }
                }
                meanFit = (totalFit / pop);

//                System.out.println("totalFit = " + totalFit);
//                System.out.println("meanFit = " + meanFit);
//                System.out.println("bestFit = " + bestFit + "\n");
                
        meanFitArray[i] = meanFit;
        bestFitArray[i] = bestFit;
        totalFitArray[i] = totalFit;

            }//end - generation loop

            System.out.println("NEXT RUN" + q);

            for (int y = 0; y < meanFitArray.length; y++) {
                //mA[q][y] = new int[10][G];
                mA[q][y] = meanFitArray[y];
                bA[q][y] = bestFitArray[y];
            }

        }//end loop - Q
//         float tempMean = 0;
//        float tempBest = 0;
//        for (int y = 0; y < 5; y++) {
//            tempMean += mA[y][(generationNum - 1)];
//            tempBest += bA[y][(generationNum - 1)];
//        }
//        tempMean = (tempMean / 5);
//        tempBest = (tempBest / 5);
//
//        System.out.println("mean: " + tempMean);
//        System.out.println("best: " + tempBest);

        //printAllFitness();
        writeToCSV2();

    }//end main ------------------------------------------

    public static int fitnessFunction(Individual2 ind, Data3[] database) {
        int fitness = 0;
        Rule2[] rulebase = getRulebase(ind);

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
        //ind.setFitness(fitness);
        //System.out.println(fitness);
        return fitness;
    }

    public static Rule2[] getRulebase(Individual2 ind) {
        int k = 0;
        Rule2[] rulebase = new Rule2[numR];
        for (int i = 0; i < numR; i++) {
            rulebase[i] = new Rule2(new float[conL], 0);
            for (int j = 0; j < conL; j++) {
                rulebase[i].setConditionGene2(j, ind.getGene()[k++]);
            }
            rulebase[i].setOutput(Math.round(ind.getGene()[k++]));
        }
        return rulebase;
    }

    public static boolean matches(float[] cond, float[] var) {

        boolean matches = true;
        for (int i = 0; i < cond.length; i += 2) {
            if (var[i / 2] > cond[i] && var[i / 2] < cond[i + 1]) {
                matches = false;
            }
        }
        return matches;
    }
    
     public static void writeToCSV2() throws FileNotFoundException {

        printOut = new PrintWriter(new File("4000.csv")); //printing
        StringBuilder csv = new StringBuilder();//printing

        csv.append("MEAN");
        csv.append(',');
        csv.append("=AVERAGE(B203:K203)");
        csv.append('\n');

        csv.append("BEST");
        csv.append(',');
        csv.append("=AVERAGE(B404:K404)");
        csv.append('\n');

        csv.append("Generation");
        csv.append(',');
        csv.append("Mean Fitness");
        csv.append('\n');

        for (int i = 0; i < generationNum; i++) {
            csv.append(i + 1);
            csv.append(',');
            csv.append(mA[0][i]);
            csv.append(',');
            csv.append(mA[1][i]);
            csv.append(',');
            csv.append(mA[2][i]);
            csv.append(',');
            csv.append(mA[3][i]);
            csv.append(',');
            csv.append(mA[4][i]);
            csv.append('\n');
        }

        csv.append("Generation");
        csv.append(',');
        csv.append("Best Fitness");
        csv.append('\n');

        for (int i = 0; i < generationNum; i++) {
            csv.append(i + 1);
            csv.append(',');
            csv.append(bA[0][i]);
            csv.append(',');
            csv.append(bA[1][i]);
            csv.append(',');
            csv.append(bA[2][i]);
            csv.append(',');
            csv.append(bA[3][i]);
            csv.append(',');
            csv.append(bA[4][i]);
            csv.append('\n');
        }
        printOut.write(csv.toString());
        printOut.close();

    }//end - writeToCSV - writeToCSV - writeToCSV - writeToCSV - writeToCSV

}
