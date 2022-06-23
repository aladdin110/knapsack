package com.example.fuck;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome {

    private final int length;
    private final ArrayList<Byte> genes;
    private final KP kp;

    public Chromosome(KP kp, boolean newChromosome) {
        this.kp = kp;
        this.length = kp.getLength();
        genes = new ArrayList<>();
        if (newChromosome) {
            generateRandomGenes();
        } else {
            for (int i=0; i<length; i++) {
                this.genes.add(i, (byte) 0);
            }
        }
    }

    private void generateRandomGenes() {
        final Random random = new Random();
        byte randGene;
        for (int i=0; i<length; i++) {
            randGene = (byte) (Math.abs(random.nextInt())%2);
            this.genes.add(i, randGene);
        }
    }

    public ArrayList<Byte> getGenes() {
        return genes;
    }

    public byte getSingleGene (int index) {
        return this.genes.get(index);
    }

    public void setSingleGene (int index, byte gene) {
        this.genes.set(index, gene);
    }

    public double getValue() {
        double value = 0;
        for (int i=0; i<length; i++) {
            value += kp.getItems().get(i).getValue()*genes.get(i);
        }

        return value;
    }

    public double getWeight () {
        double weight = 0;
        for (int i=0; i<length; i++) {
            weight += kp.getItems().get(i).getWeight()*genes.get(i);
        }

        return weight;
    }
    public double getFitness() {
        double weight = getWeight();
        double value = getValue();
        if (weight > kp.getCapacity()) {
            return 0;
        } else {
            return value;
        }
    }

}
