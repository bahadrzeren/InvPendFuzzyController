package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamics.invpend.InvertedPendulum;
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

	private static Logger logger = null;

	public static final int maxItr = 5000;
	private static final int popSize = 20;

	private static final double cr = 0.5;
	private static final double f = 0.5;

	private static final double centerSearchRange = 0.5;
	private static final double sigmaSearchRange = 1.0;

	static {
		System.setProperty("xyz", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".log");
		logger = LogManager.getLogger(RunDeOptimization.class);
	}

	public static void main(String[] args) {

		logger.info("Inverted Pendulum Fuzzy Controller Parameters Optimizer");
		logger.info("-------------------------------------------------------");
		logger.info("-------------------------------------------------------");
		logger.info("-------------------------------------------------------");

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

	    logger.info("Total execution time: " + computingTime + "ms");
	    logger.info("Objectives values have been written to file FUN.tsv");
	    logger.info("Variables values have been written to file VAR.tsv");
	    logger.info("Fitness: " + solution.getObjective(0)) ;

	    evaluator.shutdown();

	    /*
	     * Plot outputs
	     */
	    ControlSystem[] controlSystems = new ControlSystem[3];
		controlSystems[0] = new ControlSystem("Dictionary",
											Color.RED,
											Dictionary.defaultCont,
											new InvertedPendulum());

		controlSystems[0].getCont().plotMembershipFunctions("dict", false);
		controlSystems[0].getCont().plotControlSurface("dict");

//		systemPairs[0].cont.plotMembershipFunctions();
//		systemPairs[0].cont.plotControlSurface();

		controlSystems[1] = new ControlSystem("Mid Optimized",
												Color.BLUE,
												((InvPendFuzzyContParamOpt) problem).getMidOptFuzzyCont(),
												new InvertedPendulum());

		controlSystems[1].getCont().plotMembershipFunctions("mid", true);
		controlSystems[1].getCont().plotControlSurface("mid");

		controlSystems[2] = new ControlSystem("Optimized",
											Color.GREEN,
											new FuzzyControllerOpt(solution.getVariables()),
											new InvertedPendulum());

		controlSystems[2].getCont().plotMembershipFunctions("best", true);
		controlSystems[2].getCont().plotControlSurface("best");

		Simulator.simulate(controlSystems, true, "Sim+Opt");	//	plotLen %

	    logger.info("RMSE_T(Begin/Mid/Best): " + controlSystems[0].getRmseT() + "/" +
													((InvPendFuzzyContParamOpt) problem).getMidRmseT() + "/" + 
													((InvPendFuzzyContParamOpt) problem).getBestRmseT());

	    logger.info("RMSE_T(Begin/Mid/Best): " + controlSystems[0].getRmseT() + "/" +
	    											controlSystems[1].getRmseT() + "/" + 
	    											controlSystems[2].getRmseT());

	    logger.info("JaccardDissimilarity(Begin/Mid/Best): 0.0/" +
													((InvPendFuzzyContParamOpt) problem).getMidDissimilarity() + "/" + 
													((InvPendFuzzyContParamOpt) problem).getBestDissimilarity());

	    logger.info("JaccardDissimilarity(Begin/Mid/Best): 0.0/" +
	    											(1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity((FuzzyControllerOpt) controlSystems[1].getCont())) + "/" + 
	    											(1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity((FuzzyControllerOpt) controlSystems[2].getCont())));

		FuzzyInvPendController.reportSimilarity("DICTIONARY", "FULL OPTIMIZED", (FuzzyControllerOpt) controlSystems[0].getCont(), (FuzzyControllerOpt) controlSystems[1].getCont());
		FuzzyInvPendController.reportSimilarity("DICTIONARY", "MID OPTIMIZED", (FuzzyControllerOpt) controlSystems[0].getCont(), (FuzzyControllerOpt) controlSystems[2].getCont());
		FuzzyInvPendController.reportSimilarity("MID OPTIMIZED", "FULL OPTIMIZED", (FuzzyControllerOpt) controlSystems[1].getCont(), (FuzzyControllerOpt) controlSystems[2].getCont());
	}

}
