package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

public class RunDeOptimization {

	private static Logger logger = null;

	private static final int maxItr = 5000;
	private static final int popSize = 20;

	private static final double cr = 0.5;
	private static final double f = 0.5;

	private static final double centerSearchRange = 0.5;
	private static final double sigmaSearchRange = 1.0;

	static {
		System.setProperty("xyz", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".log");
		logger = LogManager.getLogger(RunDeOptimization.class);
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	public static void main(String[] args) {

		logger.info("Inverted Pendulum Fuzzy Controller Parameters Optimizer");
		logger.info("-------------------------------------------------------");

		DoubleProblem problem = new InvPendFuzzyContParamOpt(maxItr, centerSearchRange, sigmaSearchRange);
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
	    new SolutionListOutput(population).setSeparator("\t").print();

	    logger.info("Total execution time: " + computingTime + "ms");
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

		logger.info("-----------------------------------------------------------------------");

		FuzzyInvPendController.reportSimilarity("DICTIONARY", "MID OPTIMIZED", "FULL OPTIMIZED",
												(FuzzyControllerOpt) controlSystems[0].getCont(),
												(FuzzyControllerOpt) controlSystems[1].getCont(),
												(FuzzyControllerOpt) controlSystems[2].getCont());

		logger.info("RMSE_T(Begin/Mid/Best):" + formatter.format(controlSystems[0].getRmseT()) + "/" +
	    										formatter.format(((InvPendFuzzyContParamOpt) problem).getMidRmseT()) + "/" + 
	    										formatter.format(((InvPendFuzzyContParamOpt) problem).getBestRmseT()));

	    logger.info("JaccardDissimilarity(Begin/Mid/Best):0.0/" +
	    										formatter.format(((InvPendFuzzyContParamOpt) problem).getMidDissimilarity()) + "/" + 
	    										formatter.format(((InvPendFuzzyContParamOpt) problem).getBestDissimilarity()));

		logger.info("-----------------------------------------------------------------------");

	}
}
