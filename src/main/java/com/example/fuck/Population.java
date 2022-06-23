package com.example.fuck;

import java.util.ArrayList;

public class Population {

    KP kp;
    private final int size;
    private final ArrayList<Chromosome> chromosomes;
    Population(KP kp, int size, boolean newPopulation) {
        this.kp = kp;
        this.size = size;
        this.chromosomes = new ArrayList<>();
        if (newPopulation) {
            generateRandomPopulation();
        }
    }

    private void generateRandomPopulation() {
        for (int i=0; i<size; i++) {
            this.chromosomes.add(new Chromosome(kp, true));
        }
    }

    public Chromosome getFittest() {
        Chromosome fittestChromosome = chromosomes.get(0);
        for (int i=1; i<size; i++) {
            if (fittestChromosome.getFitness() < chromosomes.get(i).getFitness()) {
                fittestChromosome = chromosomes.get(i);
            }
        }
        return fittestChromosome;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }
    public void addChromosome(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    public Chromosome getChromosome(int index) {
        return this.chromosomes.get(index);
    }
}
