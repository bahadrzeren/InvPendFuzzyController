package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;
import org.fuzzy.invpend.opt.prob.InvPendFuzzyContParamOpt;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.ControlSystem;
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

public class RunDeOptimization {

	public static final int maxItr = 5000;
	private static final int popSize = 100;

	private static final double cr = 0.5;
	private static final double f = 0.5;

	private static final double centerSearchRange = 0.3;
	private static final double sigmaSearchRange = 0.6;

	public static void main(String[] args) {
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
									        .setVarFileOutputContext(new DefaultFileOutputContext("VAR.tsv"))
									        .setFunFileOutputContext(new DefaultFileOutputContext("FUN.tsv"))
									        .print();

	    System.out.println("Total execution time: " + computingTime + "ms");
	    System.out.println("Objectives values have been written to file FUN.tsv");
	    System.out.println("Variables values have been written to file VAR.tsv");

	    System.out.println("Fitness: " + solution.getObjective(0)) ;

	    evaluator.shutdown();

	    /*
	     * Plot outputs
	     */
	    ControlSystem[] systemPairs = new ControlSystem[3];
		systemPairs[0] = new ControlSystem("Dictionary",
											Color.RED,
											Dictionary.defaultCont,
											Simulator.generateNewPendulum());

		systemPairs[0].getCont().plotMembershipFunctions(false);
		systemPairs[0].getCont().plotControlSurface();

//		systemPairs[0].cont.plotMembershipFunctions();
//		systemPairs[0].cont.plotControlSurface();

		systemPairs[1] = new ControlSystem("Mid Optimized",
											Color.BLUE,
											((InvPendFuzzyContParamOpt) problem).midOptFuzzyCont,
											Simulator.generateNewPendulum());

		systemPairs[1].getCont().plotMembershipFunctions(true);
		systemPairs[1].getCont().plotControlSurface();

		systemPairs[2] = new ControlSystem("Optimized",
											Color.GREEN,
											new FuzzyControllerOpt(solution.getVariables()),
											Simulator.generateNewPendulum());

		systemPairs[2].getCont().plotMembershipFunctions(true);
		systemPairs[2].getCont().plotControlSurface();

		Simulator.simulate(systemPairs, true);	//	plotLen %

		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairs[0].getCont(), (FuzzyControllerOpt) systemPairs[1].getCont());
		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairs[0].getCont(), (FuzzyControllerOpt) systemPairs[2].getCont());
		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairs[1].getCont(), (FuzzyControllerOpt) systemPairs[2].getCont());
	}

}
