package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.Dictionary;
import org.fuzzy.invpend.cont.FuzzyController;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;
import org.fuzzy.invpend.opt.prob.InvPendFuzzyContParamOpt;
import org.fuzzy.invpend.sim.ControlSystem;
import org.fuzzy.invpend.sim.Simulator;
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

public class RunDeOptimizationMult {

	private static Logger logger = null;

	private static final int maxItr = 2000;
	private static final int popSize = 20;

	private static final double cr = 0.5;
	private static final double f = 0.5;

	private static final double centerSearchRange = 0.5;
	private static final double sigmaSearchRange = 1.0;

	private static final int numOfRuns = 30;

	private static List<OptOutput> optOutputs = new ArrayList<OptOutput>();

	static {
		System.setProperty("xyz", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".log");
		logger = LogManager.getLogger(RunDeOptimizationMult.class);
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	public static void main(String[] args) throws InterruptedException {
		for (int i = 1; i <= numOfRuns; i++) {
			logger.info("----------------------------------------------------------");
			logger.info(i + "# Inverted Pendulum Fuzzy Controller Parameters Optimizer");
			logger.info("----------------------------------------------------------");
			logger.info("----------------------------------------------------------");

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

		    logger.info(i + "# Total execution time: " + computingTime + "ms");
		    logger.info(i + "# Fitness: " + solution.getObjective(0)) ;

		    evaluator.shutdown();

		    OptOutput output = new OptOutput(((InvPendFuzzyContParamOpt) problem).getMidDissimilarity(),
											    ((InvPendFuzzyContParamOpt) problem).getMidRmseT(),
											    ((InvPendFuzzyContParamOpt) problem).getMidObj(),
											    ((InvPendFuzzyContParamOpt) problem).getMidOptFuzzyCont(),
									
											    ((InvPendFuzzyContParamOpt) problem).getBestDissimilarity(),
											    ((InvPendFuzzyContParamOpt) problem).getBestRmseT(),
											    ((InvPendFuzzyContParamOpt) problem).getBestObj(),
											    new FuzzyControllerOpt(solution.getVariables()));

		    optOutputs.add(output);
		}
		report();
	}

	public static void report() throws InterruptedException {
	    ControlSystem[] controlSystems = new ControlSystem[3];
		controlSystems[0] = new ControlSystem("Dictionary",
											Color.RED,
											Dictionary.defaultCont,
											new InvertedPendulum());

		controlSystems[0].getCont().plotMembershipFunctions("dict", false);
		controlSystems[0].getCont().plotControlSurface("dict");

//		FuzzyController midsBest = optOutputs.stream().max(new Comparator<OptOutput>() {
//										@Override
//										public int compare(OptOutput o1, OptOutput o2) {
//											if (o1.getMidObj() < o2.getMidObj()) return 1;
//											else
//												if (o1.getMidObj() > o2.getMidObj()) return -1;
//												else
//													return 0;
//										}
//									}).get().getMidController();


		OptOutput bestOutput = optOutputs.stream().max(new Comparator<OptOutput>() {
										@Override
										public int compare(OptOutput o1, OptOutput o2) {
											if (o1.getBestObj() < o2.getBestObj()) return 1;
											else
												if (o1.getBestObj() > o2.getBestObj()) return -1;
												else
													return 0;
										}
									}).get();
		FuzzyController bestsBest = bestOutput.getBestController();
		FuzzyController bestsMid = bestOutput.getMidController();


//		List<Double> midsAvgVars = new ArrayList<Double>();
//		for (int i = 0; i < 30; i++) {
//			final int fi = i;
//			midsAvgVars.add(optOutputs.stream().mapToDouble(o -> o.getMidController().getVariables().get(fi)).average().getAsDouble());
//		}
//
//		List<Double> bestsAvgVars = new ArrayList<Double>();
//		for (int i = 0; i < 30; i++) {
//			final int fi = i;
//			bestsAvgVars.add(optOutputs.stream().mapToDouble(o -> o.getBestController().getVariables().get(fi)).average().getAsDouble());
//		}
//
//		FuzzyController midsAvg = new FuzzyControllerOpt(midsAvgVars);
//		FuzzyController bestsAvg = new FuzzyControllerOpt(bestsAvgVars);

		/*
		 * COMPARISON REPORT USING SIMULATION (BESTS)
		 */

		logger.info("-----------------------------------------------------------------------");
		logger.info("COMPARISON REPORT USING SIMULATION (BESTS)");
		logger.info("-----------------------------------------------------------------------");

//		controlSystems[1] = new ControlSystem("Mid Best Optimized",
//												Color.BLUE,
//												midsBest,
//												new InvertedPendulum());
//
//		controlSystems[1].getCont().plotMembershipFunctions("mid best", true);
//		controlSystems[1].getCont().plotControlSurface("mid best");

		controlSystems[1] = new ControlSystem("Final Mid Optimized",
											Color.BLUE,
											bestsMid,
											new InvertedPendulum());

		controlSystems[1].getCont().plotMembershipFunctions("final mid", true);
		controlSystems[1].getCont().plotControlSurface("final mid");

		controlSystems[2] = new ControlSystem("Final Best Optimized",
											Color.GREEN,
											bestsBest,
											new InvertedPendulum());

		controlSystems[2].getCont().plotMembershipFunctions("final best", true);
		controlSystems[2].getCont().plotControlSurface("final best");

		Simulator.simulate(controlSystems, true, "Sim+Opt+FinalMid+FinalBest");	//	plotLen %

		logger.info("-----------------------------------------------------------------------");

		FuzzyInvPendController.reportSimilarity("DICTIONARY", "FINAL MID OPTIMIZED", "FINAL BEST OPTIMIZED",
												(FuzzyControllerOpt) controlSystems[0].getCont(),
												(FuzzyControllerOpt) controlSystems[1].getCont(),
												(FuzzyControllerOpt) controlSystems[2].getCont());

		logger.info("RMSE_T(Dict/FinalMid/FinalBest):" + formatter.format(controlSystems[0].getRmseT()) + "/" +
	    										formatter.format(controlSystems[1].getRmseT()) + "/" +
	    										formatter.format(controlSystems[2].getRmseT()));

	    logger.info("JaccardDissimilarity(Dict/FinalMid/FinalBest):0.0/" +
												formatter.format(1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity((FuzzyInvPendController) controlSystems[1].getCont())) + "/" +
												formatter.format(1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity((FuzzyInvPendController) controlSystems[2].getCont())));

		logger.info("-----------------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------------");

		Thread.sleep(500);

//		/*
//		 * COMPARISON REPORT USING SIMULATION (AVG)
//		 */
//
//		logger.info("-----------------------------------------------------------------------");
//		logger.info("COMPARISON REPORT USING SIMULATION (AVG)");
//		logger.info("-----------------------------------------------------------------------");
//
//		controlSystems[1] = new ControlSystem("Mid Avg Optimized",
//												Color.BLUE,
//												midsAvg,
//												new InvertedPendulum());
//
//		controlSystems[1].getCont().plotMembershipFunctions("mid avg", true);
//		controlSystems[1].getCont().plotControlSurface("mid avg");
//
//		controlSystems[2] = new ControlSystem("Final Avg Optimized",
//											Color.GREEN,
//											bestsAvg,
//											new InvertedPendulum());
//
//		controlSystems[2].getCont().plotMembershipFunctions("final avg", true);
//		controlSystems[2].getCont().plotControlSurface("final avg");
//
//		Simulator.simulate(controlSystems, true, "Sim+Opt+MidAvg+FinalAvg");	//	plotLen %
//
//		logger.info("-----------------------------------------------------------------------");
//
//		FuzzyInvPendController.reportSimilarity("DICTIONARY", "MID AVG OPTIMIZED", "FULL AVG OPTIMIZED",
//												(FuzzyControllerOpt) controlSystems[0].getCont(),
//												(FuzzyControllerOpt) controlSystems[1].getCont(),
//												(FuzzyControllerOpt) controlSystems[2].getCont());
//
//		logger.info("RMSE_T(Dict/MidAvg/FinalAvg):" + formatter.format(controlSystems[0].getRmseT()) + "/" +
//	    										formatter.format(controlSystems[1].getRmseT()) + "/" + 
//	    										formatter.format(controlSystems[2].getRmseT()));
//
//	    logger.info("JaccardDissimilarity(Dict/MidAvg/FinalAvg):0.0/" +
//												formatter.format(1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity((FuzzyInvPendController) controlSystems[1].getCont())) + "/" + 
//												formatter.format(1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity((FuzzyInvPendController) controlSystems[2].getCont())));
//
//		logger.info("-----------------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------------");


	}
}
