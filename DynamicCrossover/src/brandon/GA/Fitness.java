package brandon.GA;

import java.util.List;

import static brandon.GA.Vars.populationSize;
import static brandon.GA.Vars.size;
import static brandon.GA.Vars.matrix;

public class Fitness {
	/**
	 * Do fitness evaluation on chromosomes. (Hamiltonian cycle of each array in
	 * population)
	 * 
	 * @param arr The chromosome to evaluate the fitness of
	 * @return int The fitness a given individual
	 */
	static int fitnessEval(List<Integer> arr) {
		int fitness = 0;

		for (int i = 0; i < size; i++) {
			if (i == size - 1) {
				fitness += matrix[arr.get(i)][arr.get(0)];
			} else {
				fitness += matrix[arr.get(i)][arr.get(i + 1)];
			}
		}
		return fitness;
	}

	/**
	 * Returns an array containing the fitness values of the whole population. Index
	 * corresponds to index of each chromosome in the population array
	 * 
	 * @param population The entire population
	 * @return int[]
	 */
	static int[] populationFitness(List<List<Integer>> population) {
		int fitnessValues[] = new int[populationSize];
		for (int i = 0; i < populationSize; i++) {
			fitnessValues[i] = fitnessEval(population.get(i));
		}
		return fitnessValues;
	}

	/**
	 * Finds and returns the chromosome with the best fitness
	 * (Best fitness = lowest hamiltonian cycle value)
	 * 
	 * @param population The entire population
	 * @return List<Integer> The chromosome with the best fitness in the population
	 */
	static List<Integer> findBest(List<List<Integer>> population) {
		int bestFitness = Integer.MAX_VALUE, currentFitness = bestFitness;
		List<Integer> currentElite = null;
		for (List<Integer> arr : population) {
			currentFitness = fitnessEval(arr);
			if (currentFitness < bestFitness) {
				bestFitness = currentFitness;
				currentElite = arr;
			}
		}
		return currentElite;
	}

}
