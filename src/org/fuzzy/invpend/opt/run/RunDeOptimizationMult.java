package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;
import org.fuzzy.invpend.opt.prob.InvPendFuzzyContParamOpt;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.SystemPair;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.singleobjective.differentialevolution.DifferentialEvolutionBuilder;
import org.uma.jmetal.operator.impl.crossover.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.impl.selection.DifferentialEvolutionSelection;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

public class RunDeOptimizationMult {

	public static final int maxItr = 5000;
	private static final int popSize = 100;

	private static final double cr = 0.5;
	private static final double f = 0.5;

	private static final double centerSearchRange = 0.3;
	private static final double sigmaSearchRange = 0.6;

	private static final int numOfRuns = 30;

	private static SystemPair[][] systemPairs = new SystemPair[numOfRuns][3];

	private static SystemPair[] systemPairBest = null;

	private static double[] varsMin = new double[InvPendFuzzyContParamOpt.numOfVars];
	private static double[] varsAvg = new double[InvPendFuzzyContParamOpt.numOfVars];
	private static double[] varsMax = new double[InvPendFuzzyContParamOpt.numOfVars];

	private static double objBest = Integer.MAX_VALUE;
	private static double objAvg = 0.0;
	private static double objWorst = 0.0;

	private static double rmseBest = Integer.MAX_VALUE;
	private static double rmseAvg = 0.0;
	private static double rmseWorst = 0.0;

	private static double dissimilarityBest = Integer.MAX_VALUE;
	private static double dissimilarityAvg = 0.0;
	private static double dissimilarityWorst = 0.0;

	public static void main(String[] args) {
		for (int i = 0; i < numOfRuns; i++) {
		    DoubleProblem problem = new InvPendFuzzyContParamOpt(centerSearchRange, sigmaSearchRange);
		    DifferentialEvolutionSelection selection = new DifferentialEvolutionSelection();
		    DifferentialEvolutionCrossover crossover = new DifferentialEvolutionCrossover(cr, f, "rand/1/bin");
		    SolutionListEvaluator<DoubleSolution> evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();

		    Algorithm<DoubleSolution> algorithm = new DifferentialEvolutionBuilder(problem).setCrossover(crossover)
																				            .setSelection(selection)
																				            .setSolutionListEvaluator(evaluator)
																				            .setMaxEvaluations(maxItr)
																				            .setPopulationSize(popSize)
																				            .build();

		    AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm).execute();

		    DoubleSolution solution = algorithm.getResult();
		    long computingTime = algorithmRunner.getComputingTime();

		    List<DoubleSolution> population = new ArrayList<DoubleSolution>(1);
		    population.add(solution);
		    new SolutionListOutput(population).setSeparator("\t")
										        .setVarFileOutputContext(new DefaultFileOutputContext("VAR_" + i + ".tsv"))
										        .setFunFileOutputContext(new DefaultFileOutputContext("FUN_" + i + ".tsv"))
										        .print();

		    System.out.println("Total execution time_" + i + ": " + computingTime + "ms");
		    System.out.println("Objectives values have been written to file FUN_" + i + ".tsv");
		    System.out.println("Variables values have been written to file VAR_" + i + ".tsv");

		    System.out.println("Fitness_" + i + ": " + solution.getObjective(0)) ;

		    evaluator.shutdown();

		    /*
		     * Plot outputs
		     */
			systemPairs[i][0] = new SystemPair();
			systemPairs[i][0].caption = "Dictionary";
			systemPairs[i][0].color = Color.RED;
			systemPairs[i][0].pend = Simulator.generateNewPendulum();
			systemPairs[i][0].cont = Dictionary.defaultCont;

//			systemPairs[i][0].cont.plotMembershipFunctions(false);
//			systemPairs[i][0].cont.plotControlSurface();

//			systemPairs[i][0].cont.plotMembershipFunctions();
//			systemPairs[i][0].cont.plotControlSurface();

			systemPairs[i][1] = new SystemPair();
			systemPairs[i][1].caption = "Mid Optimized " + i;
			systemPairs[i][1].color = Color.BLUE;
			systemPairs[i][1].pend = Simulator.generateNewPendulum();
			systemPairs[i][1].cont = ((InvPendFuzzyContParamOpt) problem).midOptFuzzyCont;

//			systemPairs[i][1].cont.plotMembershipFunctions(true);
//			systemPairs[i][1].cont.plotControlSurface();

			systemPairs[i][2] = new SystemPair();
			systemPairs[i][2].caption = "Optimized " + i;
			systemPairs[i][2].color = Color.GREEN;
			systemPairs[i][2].pend = Simulator.generateNewPendulum();
			systemPairs[i][2].cont = new FuzzyControllerOpt(solution.getVariables());

//			systemPairs[i][2].cont.plotMembershipFunctions(true);
//			systemPairs[i][2].cont.plotControlSurface();
		}

		for (int i = 0; i < numOfRuns; i++) {
			SystemPair spMid = systemPairs[i][1];
			SystemPair spBest = systemPairs[i][1];
		}
		


		Simulator.simulate(systemPairs[i], true);	//	plotLen %

		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairs[i][0].cont, (FuzzyControllerOpt) systemPairs[i][1].cont);
		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairs[i][0].cont, (FuzzyControllerOpt) systemPairs[i][2].cont);

	}
}
