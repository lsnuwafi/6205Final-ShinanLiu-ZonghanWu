package life.base;

import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.util.Factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class HelloWorld {

    static Phenotype<IntegerGene, Integer> best = null;


    private static void update(final EvolutionResult<IntegerGene, Integer> result){

            best = result.getBestPhenotype();
            System.out.println(result.getGeneration() + ": " + result.getPopulation().size());
            System.out.println("The best is : " + best.getFitness() );
    }


    private static String decode(Genotype<IntegerGene> gt){
        StringBuilder sb = new StringBuilder();
        int[] gti = gt.get(0).as(IntegerChromosome.class).toArray();

        for (int i = 0, j = 1; i < gti.length ; i+=2, j+=2){
            sb.append(gti[i]).append(" ").append(gti[j]).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private static int eval(final Genotype<IntegerGene> gt){

        long gameGeneration = 0L;
        Grid grid = new Grid(gameGeneration);
        Map<Game, Long> generations = new HashMap<>();

        String pattern = decode(gt);
        grid.add(Group.create(gameGeneration, pattern));
        BiConsumer<Long, Grid> gridMonitor = (l, g) ->System.out.print("");
        BiConsumer<Long, Group> groupMonitor = (l, g) -> System.out.print("");;
        Game game = new Game(gameGeneration, grid, null, groupMonitor);
        while (!game.terminated()) {
            generations.put(game, game.generation);
            game.render();
            game = game.generation(gridMonitor);
        }

        return (int)game.generation ;

    }

    public static int genRandomGeneLegnth() {
        int num1 = 6, num2 = 500;
        int s = num1 + (int) (Math.random() * (num2 - num1));
        if (s % 2 == 0) {
            return s;
        }  else return s+1;
    }

    public static String[] run() {


        int geneLength = genRandomGeneLegnth();
        System.out.println("Random length of the chromosome is"+geneLength);
        final Factory<Genotype<IntegerGene>> gtf = Genotype.of(IntegerChromosome.of(0,100,geneLength));

        final EvolutionStreamable<IntegerGene, Integer> engine = Engine.builder(HelloWorld::eval, gtf).populationSize(200)
                .alterers(new Mutator<>(0.02))
                .build()
                .limit(() -> Limits.byFitnessThreshold(100));

        final Phenotype ph = engine.stream()
                .limit(10000)
                .peek(HelloWorld::update)
                .collect(EvolutionResult.toBestPhenotype());

        System.out.println("Best pheonoType fitness value is" + ph.getFitness());
        Genotype<IntegerGene> gt = ph.getGenotype();
        System.out.println(decode(gt));
        String uiPattern = decode(gt);
        String[] uiData = {String.valueOf(geneLength), uiPattern};
        return uiData;

    }
//    public static void main(String[] args) {
//
//
//        int geneLength = genRandomGeneLegnth();
//        System.out.println("Random length of the chromosome is"+geneLength);
//       final Factory<Genotype<IntegerGene>> gtf = Genotype.of(IntegerChromosome.of(0,100,geneLength));
//
//       final EvolutionStreamable<IntegerGene, Integer> engine = Engine.builder(HelloWorld::eval, gtf).populationSize(200)
//                .alterers(new Mutator<>(0.02))
//                .build()
//                .limit(() -> Limits.byFitnessThreshold(100));
//
//       final Phenotype ph = engine.stream()
//                        .limit(10000)
//                        .peek(HelloWorld::update)
//                        .collect(EvolutionResult.toBestPhenotype());
//
//        System.out.println("Best pheonoType fitness value is" + ph.getFitness());
//        Genotype<IntegerGene> gt = ph.getGenotype();
//        System.out.println(decode(gt));
//        String uiPattern = decode(gt);
//
//    }
}