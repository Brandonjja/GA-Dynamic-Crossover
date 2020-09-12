# Genetic Algorithm using Custom "Dynamic Crossover"
Genetic Algorithm implementation for Travelling Salesman Problem, using a custom "dynamic crossover" technique.

## Genetic Algorithm
"_In computer science and operations research, a genetic algorithm (GA) is a metaheuristic inspired by the process of natural selection that belongs to the larger class of evolutionary algorithms (EA). Genetic algorithms are commonly used to generate high-quality solutions to optimization and search problems by relying on biologically inspired operators such as mutation, crossover and selection._"
https://en.wikipedia.org/wiki/Genetic_algorithm

## Dynamic Crossover
Rather than having one crossover technique used throughout each generation, several were implemented in this algorithm. To better
utilize the advantages of each type of crossover implemented, every generation the GA would decide which to use, then assign a fitness value based on
the overall fitness of the individuals in the generation. Crossover techniques which were yeilding more desirable generations were
assigned a higher fitness, which had a higher chance of being used in subsequent generations.

To keep the GA stochastic, every crossover technique had a chance of being chosen during each generation. A lower and upper bound is enforced when modifying
the fitness of each crossover so no one technique dominates.

Types of crossover used:
- Order Crossover
- Uniform Crossover
- Single Point Crossover
- Two Point Crossover
