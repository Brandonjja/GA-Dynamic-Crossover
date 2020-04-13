package brandon.GA;

import static brandon.GA.Vars.coc_order;
import static brandon.GA.Vars.coc_singlePoint;
import static brandon.GA.Vars.coc_twoPoint;
import static brandon.GA.Vars.coc_uniform;
import static brandon.GA.Vars.orderTimes;
import static brandon.GA.Vars.singlePTimes;
import static brandon.GA.Vars.twoPTimes;
import static brandon.GA.Vars.uniformTimes;
import static brandon.GA.Vars.incrementChance;
import static brandon.GA.Vars.decrementChance;
import static brandon.GA.Vars.upperBound;
import static brandon.GA.Vars.lowerBound;
import static brandon.GA.Vars.coTimes;
import static brandon.GA.Vars.randomSearch;
import static brandon.GA.GeneticOperators.orderCrossover;
import static brandon.GA.GeneticOperators.singlePointCrossover;
import static brandon.GA.GeneticOperators.twoPointCrossover;
import static brandon.GA.GeneticOperators.uniformCrossover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DynamicCrossover {
	/**
	 * Calls the correct crossover function using a pseudorandom dice roll
	 * 
	 * @param p1 First parent
	 * @param p2 Second parent
	 */
	public static void doCrossover(List<Integer> p1, List<Integer> p2) {
		coTimes++;
		Random r = new Random();
		
		int oldFitness = Fitness.fitnessEval(p1);
		
		List<Integer> cOvers = new ArrayList<>();
		
		int order = (int) (coc_order * 100);
		int uniform = (int) (coc_uniform * 100);
		int single = (int) (coc_singlePoint * 100);
		int two = (int) (coc_twoPoint * 100);
		
		for (int i = 0; i < order; i++) {
			cOvers.add(0);
		}
		for (int i = 0; i < uniform; i++) {
			cOvers.add(1);
		}
		for (int i = 0; i < single; i++) {
			cOvers.add(2);
		}
		for (int i = 0; i < two; i++) {
			cOvers.add(3);
		}
		
		int randomIndex = r.nextInt(cOvers.size());
		int coType = cOvers.get(randomIndex);
		
		switch (coType) {
		case 0:
			orderCrossover(p1, p2);
			break;
		case 1:
			uniformCrossover(p1, p2);
			break;
		case 2:
			singlePointCrossover(p1, p2);
			break;
		case 3:
			twoPointCrossover(p1, p2);
			break;
		default:
			break;
		}
		evalCrossovers(oldFitness, p1, coType);
	}

	/**
	 * Evaluate each type of crossover and increase the chance of crossover for the
	 * crossover performing the best.
	 * Gives an extra increase if the current best was previously the worst
	 * 
	 * @param oldFitness The fitness of the parent before crossover
	 * @param child The resulting offspring from crossover
	 * @param coType The type of crossover that was performed
	 * coTypes: 0 = Order, 1 = Uniform, 2 = Single Point, 3 = Two Point
	 */
	public static void evalCrossovers(int oldFitness, List<Integer> child, int coType) {
		int newFitness = Fitness.fitnessEval(child);
		
		List<Double> co = new ArrayList<>();
		co.add(coc_order);
		co.add(coc_uniform);
		co.add(coc_singlePoint);
		co.add(coc_twoPoint);
		double min = Collections.min(co);
		
		if (oldFitness > newFitness) {
			switch (coType) {
			case 0:
				if (coc_order == min) {
					coc_order += 0.02;
				}
				coc_order += incrementChance;
				coc_uniform -= decrementChance;
				coc_singlePoint -= decrementChance;
				coc_twoPoint -= decrementChance;
				break;
			case 1:
				if (coc_uniform == min) {
					coc_uniform += 0.02;
				}
				coc_uniform += incrementChance;
				coc_order -= decrementChance;
				coc_singlePoint -= decrementChance;
				coc_twoPoint -= decrementChance;
				break;
			case 2:
				if (coc_singlePoint == min) {
					coc_singlePoint += 0.02;
				}
				coc_singlePoint += incrementChance;
				coc_uniform -= decrementChance;
				coc_order -= decrementChance;
				coc_twoPoint -= decrementChance;
				break;
			case 3:
				if (coc_twoPoint == min) {
					coc_twoPoint += 0.02;
				}
				coc_twoPoint += incrementChance;
				coc_uniform -= decrementChance;
				coc_singlePoint -= decrementChance;
				coc_order -= decrementChance;
				break;
			default:
				break;
			}
		}
		checkBounds();
	}
	
	/**
	 * Checks the percentage bounds after updating the "chance of crossover"
	 */
	private static void checkBounds() {
		if (coc_order <= lowerBound) {
			coc_order = lowerBound;
		} else if (coc_order >= upperBound) {
			coc_order = upperBound;
		}
		if (coc_uniform <= lowerBound) {
			coc_uniform = lowerBound;
		} else if (coc_uniform >= upperBound) {
			coc_uniform = upperBound;
		}
		if (coc_singlePoint <= lowerBound) {
			coc_singlePoint = lowerBound;
		} else if (coc_singlePoint >= upperBound) {
			coc_singlePoint = upperBound;
		}
		if (coc_twoPoint <= lowerBound) {
			coc_twoPoint = lowerBound;
		} else if (coc_twoPoint >= upperBound) {
			coc_twoPoint = upperBound;
		}
	}
	
	/**
	 * @param pop The entire population
	 * @return String A string containing the hamiltonian cycle of the best chromosome
	 */
	private static String getElite(List<List<Integer>> pop) {
		List<Integer> elite = Fitness.findBest(pop);
		String s = "";
		for (Integer i : elite) {
			s += " " + String.valueOf(i);
		}
		return s;
	}
	
	/**
	 * Prints some statistics relating to each crossover
	 */
	public static void dynCrossoverStats() {
		int hybridPopFitness = Fitness.fitnessEval(Fitness.findBest(Population.hybridPopulation));
		
		System.out.println("");
		System.out.println("Final Cross over Chances (COC):");
		System.out.println("Order COC:\t" + String.format("%.2f", coc_order) + "%, CO Times: " + orderTimes);
		System.out.println("Uniform COC:\t" + String.format("%.2f", coc_uniform) + "%, CO Times: " + uniformTimes);
		System.out.println("Single COC:\t" + String.format("%.2f", coc_singlePoint) + "%, CO Times: " + singlePTimes);
		System.out.println("Two Pnt COC:\t" + String.format("%.2f", coc_twoPoint) + "%, CO Times: " + twoPTimes);
		System.out.println("Random Search Fitness: " + Fitness.fitnessEval(randomSearch));
		
		Main.hy.add(hybridPopFitness);
		
		if (hybridPopFitness < Main.lowestHy) {
			Main.lowestHy = hybridPopFitness;
		}
		
		System.out.println("Dynamic Crossover Fitness: " + hybridPopFitness);
		System.out.println("Tour Found: " + getElite(Population.hybridPopulation));
		
		System.out.println("__________________________________________\n");
	}
	
}
