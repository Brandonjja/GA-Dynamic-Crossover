package brandon.GA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static brandon.GA.Vars.populationSize;
import static brandon.GA.Vars.size;
import static brandon.GA.Vars.mutTimes;
import static brandon.GA.Vars.orderTimes;
import static brandon.GA.Vars.uniformTimes;
import static brandon.GA.Vars.singlePTimes;
import static brandon.GA.Vars.twoPTimes;

public class GeneticOperators {
	/**
	 * Randomly selects parent for crossover
	 * 
	 * @param population The entire population to choose a mate from
	 * @param elite The chromosome with the highest fitness
	 * @return int[] A pair of two parents choosen from selection
	 */
	public static int[] selection(List<List<Integer>> population, List<Integer> elite) {
		Random r = new Random();

		int p1 = r.nextInt(populationSize), p2 = 0;
		// Choose 2 random parents, which are not the elite
		while (p1 == p2 || population.get(p1).equals(elite) || population.get(p2).equals(elite)) {
			if (population.get(p1).equals(elite)) {
				p1 = r.nextInt(populationSize);
			}
			p2 = r.nextInt(populationSize);
		}

		int arr[] = { p1, p2 };
		return arr;
	}
	
	/**
	 * Mutation type: Swap Mutation
	 * @param p The chromosome being mutated
	 */
	public static void mutation(List<Integer> p) {
		mutTimes++;

		Random r = new Random();
		int index = r.nextInt(size);
		int index2 = r.nextInt(size);

		while (index == index2) {
			index2 = r.nextInt(size);
		}

		int tmp = p.get(index);
		p.set(index, p.get(index2));
		p.set(index2, tmp);
	}
	
	/**
	 * Crossover type: Order Crossover
	 * 
	 * @param p1 The first parent chromosome
	 * @param p2 The second parent chromosome
	 */
	public static void orderCrossover(List<Integer> p1, List<Integer> p2) {
		orderTimes++;

		Random r = new Random();
		int point = r.nextInt((size / 2));
		int point2 = r.nextInt(size - 1);

		while (point2 - 1 < point) {
			++point2;
		}

		List<Integer> c1 = new ArrayList<>();

		for (int i = point; i < point2; i++) {
			c1.add(p1.get(i));
		}

		int ins = 0; // index to insert into
		for (int i = 0; i < point; i++) {
			// If child does not contain element in parent index 1, add it
			if (!c1.contains(p2.get(i))) {
				c1.add(ins, p2.get(i));
				++ins;
			}
		}

		for (int i = point; i < size; i++) {
			if (!c1.contains(p2.get(i))) {
				c1.add(p2.get(i));
			}
		}

		for (int i = 0; i < c1.size(); i++) {
			p1.set(i, c1.get(i));
		}
	}
	
	/**
	 * Crossover type: Uniform Crossover
	 * 
	 * @param p1 The first parent chromosome
	 * @param p2 The second parent chromosome
	 */
	public static void uniformCrossover(List<Integer> p1, List<Integer> p2) {
		uniformTimes++;
		List<Integer> c1 = new ArrayList<>();

		Random r = new Random();

		// Flip a coin for bitflip
		for (Integer i : p1) {
			if (r.nextInt(10) <= 7) { // higher number preserves adjacency more
				if (!c1.contains(i)) {
					c1.add(i);
				}
			} else {
				for (Integer j : p2) { // If coin failed, enter next acceptable gene from p2
					if (!c1.contains(j)) {
						c1.add(j);
						break;
					}
				}
			}
		}
		
		// Fill in the rest of the child genes with genes from p1
		// (p1 is the dominant parent)
		for (Integer i : p1) {
			if (!c1.contains(i)) {
				c1.add(i);
			}
		}

		for (int i = 0; i < c1.size(); i++) {
			p1.set(i, c1.get(i));
		}
	}
	
	/**
	 * Crossover type: Single Point Crossover
	 * 
	 * @param p1 The first parent chromosome
	 * @param p2 The second parent chromosome
	 */
	public static void singlePointCrossover(List<Integer> p1, List<Integer> p2) {
		singlePTimes++;
		List<Integer> c1 = new ArrayList<>();
		Random r = new Random();

		int coPoint = r.nextInt(p1.size());
		for (int i = 0; i <= coPoint; i++) {
			c1.add(p1.get(i));
		}
		for (Integer i : p2) {
			if (!c1.contains(i)) {
				c1.add(i);
			}
		}

		for (int i = 0; i < c1.size(); i++) {
			p1.set(i, c1.get(i));
		}
	}
	
	/**
	 * Crossover type: Two Point Crossover
	 * 
	 * @param p1 The first parent chromosome
	 * @param p2 The second parent chromosome
	 */
	public static void twoPointCrossover(List<Integer> p1, List<Integer> p2) {
		twoPTimes++;
		List<Integer> c1 = new ArrayList<>();
		List<Integer> secondHalf = new ArrayList<>();
		Random r = new Random();

		int coPoint = r.nextInt(p1.size() / 2);
		int coPoint2;
		do {
			coPoint2 = r.nextInt(p1.size());
		} while (coPoint2 <= coPoint);

		// Add the first section to the child
		for (int i = 0; i <= coPoint; i++) {
			c1.add(p1.get(i));
		}

		for (int i = coPoint2; i < p1.size(); i++) {
			secondHalf.add(p1.get(i));
		}

		// Add the middle section to the child
		for (Integer i : p2) {
			if (!c1.contains(i) && !secondHalf.contains(i)) {
				c1.add(i);
			}
		}

		// Add the third and final section to the child
		for (Integer i : secondHalf) {
			c1.add(i);
		}

		for (int i = 0; i < c1.size(); i++) {
			p1.set(i, c1.get(i));
		}
	}
	
}
