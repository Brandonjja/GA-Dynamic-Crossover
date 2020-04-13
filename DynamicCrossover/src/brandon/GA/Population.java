package brandon.GA;

import static brandon.GA.Vars.mutationChance;
import static brandon.GA.Vars.populationSize;
import static brandon.GA.Vars.randomSearch;
import static brandon.GA.Vars.size;
import static brandon.GA.Vars.crossoverChance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
	static List<List<Integer>> hybridPopulation = null;
	
	/**
	 * Initialization function, used to initialize the population array
	 * @return List<List<Ingeter>> The randomly initialized population
	 */
	public static List<List<Integer>> init() {
		hybridPopulation = new ArrayList<>();
		Random r = new Random();

		int num = 0;
		for (int j = 0; j < populationSize; j++) {
			List<Integer> pop = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				num = r.nextInt(size);
				if (pop.contains(num)) {
					--i;
				} else {
					pop.add(num);
				}
			}

			hybridPopulation.add(pop);
		}

		// Copy random chromosome to array for "random search" comparison
		randomSearch = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			randomSearch.add(hybridPopulation.get(0).get(i));
		}
		return hybridPopulation;
	}

	/**
	 * Generation function, used to execute a generation
	 * Mutation and crossover may occur during a generation
	 * 
	 * @param gen Current generation number
	 * @param population The current population array
	 * @return List<List<Integer>> The new population after the generation has completed
	 */
	public static List<List<Integer>> generation(int gen, List<List<Integer>> population) {
		List<Integer> elite = Fitness.findBest(population);
		Random r = new Random();
		for (List<Integer> p : population) {
			if (!p.equals(elite) && r.nextFloat() < crossoverChance) {
				int pool[] = GeneticOperators.selection(population, elite);
				DynamicCrossover.doCrossover(p, population.get(pool[1]));

			}
			if (!p.equals(elite) && r.nextFloat() < mutationChance) {
				GeneticOperators.mutation(p);
			}
		}
		return population;
	}
}
