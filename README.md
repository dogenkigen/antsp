# antsp

**Antsp** is object-oriented multithreading implementation of few most popular Ant Colony Optimization algorithms. 

It requires TSPLIB files parser which can be found here https://github.com/dogenkigen/tsplib-parser.

To use **antsp** just run code like this:
 
```java
Tsp tsp = TspLibParser.parseTsp("path_to_tsp_file");
AcoConfig config = AcoConfigFactory.createDefaultAntSystemConfig(100);
Solution solution = SolutionFactory.createAntSystemSolution(tsp, config);
```

This will create solution object using Ant System algorithm with colony of 100 artificial ants. `SolutionFacory` 
supports 
also Elitist Ant System, 
Rank-Based Ant System and MAX-MIN Ant System algorithms.

For creating custom configuration use `AcoConfigBulder` like this:

```java
AcoConfig config = new AcoConfigBuilder()
                .withMaxStagnationCount(10)
                .withAntsCount(100)
                .withHeuristicImportance(3)
                .withPheromoneImportance(1)
                .withPheromoneEvaporationFactor(0.5)
                .withNearestNeighbourFactor(15)
                .withWithLocalSearch(true)
                .build();
```
or if you want overwrite just one parameter in default config:

```java
AcoConfig config = AcoConfigFactory.createAcoConfigBuilderWithDefaults(numberOfAnts)
                .withPheromoneEvaporationFactor(0.5)
                .build()
```
