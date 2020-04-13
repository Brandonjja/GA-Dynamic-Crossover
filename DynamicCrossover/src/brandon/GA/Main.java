package brandon.GA;

import static brandon.GA.Population.generation;
import static brandon.GA.Vars.coTimes;
import static brandon.GA.Vars.coc_hybrid;
import static brandon.GA.Vars.coc_order;
import static brandon.GA.Vars.coc_singlePoint;
import static brandon.GA.Vars.coc_twoPoint;
import static brandon.GA.Vars.coc_uniform;
import static brandon.GA.Vars.currentGeneration;
import static brandon.GA.Vars.hybridTimes;
import static brandon.GA.Vars.maxGenerations;
import static brandon.GA.Vars.mutTimes;
import static brandon.GA.Vars.orderTimes;
import static brandon.GA.Vars.populationSize;
import static brandon.GA.Vars.runs;
import static brandon.GA.Vars.singlePTimes;
import static brandon.GA.Vars.twoPTimes;
import static brandon.GA.Vars.uniformTimes;

import java.util.ArrayList;
import java.util.List;

/**
 * Genetic Algorithm - TSP implementation
 * GA using "dynamic crossover", using 4 different CO types
 * 
 * @author Brandon Anthony
 */

public class Main {
	
	static List<Integer> hy = new ArrayList<>();
	static int lowestHy = Integer.MAX_VALUE;
	
	private static void getStats() {
		double sum = 0;
		for (int i = 0; i < hy.size(); i++) {
			sum += hy.get(i);
		}
		
		System.out.println("Average Fitness across all GA runs: " + String.format("%.2f", (sum / hy.size())));
		System.out.println("Lowest (best) overall fitness across all GA runs: " + lowestHy);
	}

	/** Resets all required values to their respective default values for a new evolution **/
	private static void resetDefaults() {
		Population.init();
		currentGeneration = 1;
		coTimes = 0;
		mutTimes = 0;
		orderTimes = 0;
		uniformTimes = 0;
		singlePTimes = 0;
		twoPTimes = 0;
		hybridTimes = 0;
		coc_order = 0.5;
		coc_uniform = 0.5;
		coc_singlePoint = 0.5;
		coc_twoPoint = 0.5;
		coc_hybrid = 0.5;
	}

	public static void main(String[] args) {
		
		long overallStart = System.currentTimeMillis();
		for (int i = 0; i < runs; i++) {
			System.out.println("(" + (i + 1) + ") Running new GA on " + maxGenerations + " generations with a population size of " + populationSize);
			resetDefaults();
			long start = System.currentTimeMillis();
			while (currentGeneration <= maxGenerations) {
				
				Population.hybridPopulation = generation(currentGeneration, Population.hybridPopulation);
				++currentGeneration;
			}
			// Prints extra information about the program
			System.out.println("\nTime to Execute Generation: " + ((double) (System.currentTimeMillis() - start) / 1000) + "s");
			System.out.println("Total Crossovers: " + coTimes + "\nTotal Mutations: " + mutTimes + "\nPopulation Size: "
					+ populationSize + "\nGenerations: " + maxGenerations);
			DynamicCrossover.dynCrossoverStats();
		}
		
		System.out.println("\nTime to Execute Overall GA: " + ((double) (System.currentTimeMillis() - overallStart) / 1000) + "s\n");
		getStats();

	}
}
