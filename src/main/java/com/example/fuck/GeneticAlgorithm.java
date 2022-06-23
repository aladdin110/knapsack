package com.example.fuck;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {

    KP kp;
    private int populationSize;
    private final int chromosomeLength;
    //size of the population chosen for tournament selection
    private int tournamentSize;
    // crossover rate
    private final double uniformRate;
    // mutation rate
    private final double mutationRate;
    private int maxIterations;

    private final boolean elitism;
    private double objValue;
    private ArrayList<Item> solution = new ArrayList<>();

    public GeneticAlgorithm(KP kp, int populationSize, int tournamentSize, double uniformRate, double mutationRate, int maxIterations, boolean elitism) {
        this.kp = kp;
        chromosomeLength = kp.getLength();
        this.populationSize = populationSize;
        this.tournamentSize = tournamentSize;
        this.uniformRate = uniformRate;
        this.mutationRate = mutationRate;
        this.maxIterations = maxIterations;
        this.elitism = elitism;

    }

    public GeneticAlgorithm(KP kp) {
        this.kp = kp;
        chromosomeLength = kp.getLength();
        if (chromosomeLength <= 100) {
            populationSize = 100;
            maxIterations = 50;
            tournamentSize = 5;
        } else if (chromosomeLength <= 200){
            populationSize = 250;
            maxIterations = 100;
            tournamentSize = 20;
        } else if (chromosomeLength <= 300) {
            populationSize = 300;
            maxIterations = 125;
            tournamentSize = 25;
        } else if (chromosomeLength <= 500) {
            populationSize = 400;
            maxIterations = 120;
            tournamentSize = 30;
        } else if (chromosomeLength <= 800) {
            populationSize = 1000;
            maxIterations = 300;
            tournamentSize = 30;
        } else if (chromosomeLength <= 100000) {
            populationSize = 10000;
            maxIterations = 1000;
            tournamentSize = 50;
        }


        uniformRate = .5;
        mutationRate = .025;
        elitism = true;
    }

    public void runAlgorithm() {
        int generationCount = 1;
        Chromosome fittestChromosome;
        // initialise the population randomly
        Population pop = initializePopulation();
        do {
            // replace the current population with the evolved population
            pop = evolve_population(pop);
            // increment the generation count
            generationCount++;
            // get the best solution so far
            fittestChromosome = pop.getFittest();
            //System.out.println("generation: " + generationCount);
        } while(generationCount <= maxIterations);
        objValue = fittestChromosome.getFitness();
        ArrayList<Byte> solutionGenes = fittestChromosome.getGenes();
        for (int i=0; i<chromosomeLength; i++) {
            if(solutionGenes.get(i) == 1) {
                solution.add(kp.getItem(i));
            }
        }

    }

    private Population initializePopulation() {
        return new Population(kp, populationSize, true);
    }

    private Population evolve_population(Population pop) {
        int elitismOffset;
        // initializing an empty population
        Population newPop = new Population(kp, populationSize, false);
        if (elitism) {
            newPop.addChromosome(pop.getFittest());
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        for (int i=elitismOffset; i<populationSize; i++) {
            List<Chromosome> parents = selectParents(pop);
            Chromosome child = crossover(parents);
            // populating newPop with children
            newPop.addChromosome(child);
        }

        for (int i=elitismOffset; i<populationSize;i++) {
            mutate(newPop.getChromosome(i));
        }

        return newPop;
    }

    private List<Chromosome> selectParents(Population pop) {
        // for selecting parents, we use tournament selection
        List<Chromosome> parents = new ArrayList<>();
        parents.add(tournamentSelection(pop));
        parents.add(tournamentSelection(pop));
        return parents;
    }

    private Chromosome crossover(List<Chromosome> parents) {
        Chromosome child = new Chromosome(kp, false);
        for (int i=0; i<chromosomeLength; i++) {
            if (Math.random() < uniformRate) {
                child.setSingleGene(i, parents.get(0).getSingleGene(i));
            } else {
                child.setSingleGene(i, parents.get(1).getSingleGene(i));
            }
        }
        return child;
    }

    private Chromosome tournamentSelection(Population pop) {
        // choose k individuals (chromosomes) from the population at random
        final Random random = new Random();
        Population tournament = new Population(kp, tournamentSize, false);
        for (int i=0; i<tournamentSize; i++) {
            int randomInd = Math.abs(random.nextInt()) % populationSize;
            tournament.addChromosome(pop.getChromosome(randomInd));
        }

        // chose the fittest individual from the tournament
        return tournament.getFittest();
    }


    private void mutate(Chromosome chromosome) {
        for (int i=0; i<chromosomeLength; i++) {
            if (Math.random()<mutationRate) {
                if (chromosome.getSingleGene(i) == 0) {
                    chromosome.setSingleGene(i, (byte) 1);
                } else {
                    chromosome.setSingleGene(i, (byte) 0);
                }
            }
        }
    }

    public double getObjValue() {
        return objValue;
    }

    public ArrayList<Item> getSolution(){
        return solution;
    }
}
