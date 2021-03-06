package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

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
import org.math.plot.Plot2DPanel;
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

	private static final int maxItr = 5000;
	private static final int popSize = 20;

	private static final double cr = 0.5;
	private static final double f = 0.5;

	private static final double centerSearchRange = 0.5;
	private static final double sigmaSearchRange = 1.0;

	private static final int numOfCoefSetups = 100;
	private static final int numOfRuns = 30;

	private static double[] itrs = null;

	private static double minRmseT = Integer.MAX_VALUE;
	private static double minDissimilarity = Integer.MAX_VALUE;
	private static double maxRmseT = 0.0;
	private static double maxDissimilarity = 0.0;

	private static List<BestOptOutput> bestOptOutputs = new ArrayList<BestOptOutput>();

	static {
		System.setProperty("xyz", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".log");
		logger = LogManager.getLogger(RunDeOptimizationMult.class);
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	public static void main(String[] args) throws InterruptedException {

		double normMinRmseT = 0.0;
		double normMinDissimilarity = 0.0;
		double normMaxRmseT = 0.0;
		double normMaxDissimilarity = 0.0;

		if (args.length > 1) {
			InvertedPendulum.tInit = Double.parseDouble(args[0]) * Math.PI / 180.0;
			InvertedPendulum.tdInit = Double.parseDouble(args[1]) * Math.PI / 180.0;

			normMinRmseT = Double.parseDouble(args[2]);
			normMinDissimilarity = Double.parseDouble(args[3]);
			normMaxRmseT = Double.parseDouble(args[4]);
			normMaxDissimilarity = Double.parseDouble(args[5]);
		}
		logger.info("tInit = " + InvertedPendulum.tInit + " rad");
		logger.info("tdInit = " + InvertedPendulum.tdInit + " rad");

		itrs = new double[maxItr];
		for (int i = 0; i < itrs.length; i++)
			itrs[i] = i;

		Random r = new Random();
		BestOptOutputComparator comp = new BestOptOutputComparator();

		for (int a = 1; a <= numOfCoefSetups; a++) {

			double objCoefRmseT = Math.floor(r.nextDouble() * 10000.0) / 10000.0;
			double objCoefDissim = Math.floor((1.0 - objCoefRmseT) * 10000.0) / 10000.0;

//if (a == 1) {
//	objCoefRmseT = 0.01;
//} else
//	if (a == 2) {
//		objCoefRmseT = 0.1;
//	} else
//		if (a == 3) {
//			objCoefRmseT = 0.5;
//		} else
//			if (a == 4) {
//				objCoefRmseT = 0.9;
//			} else
//				if (a == 5) {
//					objCoefRmseT = 0.99;
//				}
//objCoefDissim = 1.0 - objCoefRmseT;

			List<OptOutput> optOutputs = new ArrayList<OptOutput>();

			logger.info("----------------------------------------------------------");
			logger.info("----------------------------------------------------------");
			logger.info(a + " => objCoefRmseT: " + objCoefRmseT + ", objCoefDissim: " + objCoefDissim);
			logger.info("----------------------------------------------------------");
			logger.info("----------------------------------------------------------");

			for (int i = 1; i <= numOfRuns; i++) {
				logger.info("----------------------------------------------------------");
				logger.info("objCoefRmseT: " + objCoefRmseT + ", objCoefDissim: " + objCoefDissim);
				logger.info(a + "th setup, " + i + "# Inverted Pendulum Fuzzy Controller Parameters Optimizer");
				logger.info("----------------------------------------------------------");
				logger.info("----------------------------------------------------------");

				DoubleProblem problem = new InvPendFuzzyContParamOpt(maxItr,
																		centerSearchRange, sigmaSearchRange,
																		objCoefRmseT, objCoefDissim,
																		normMinRmseT, normMinDissimilarity, normMaxRmseT, normMaxDissimilarity);

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

			    logger.info(a + "th setup, " + i + "# Total execution time: " + computingTime + "ms");
			    logger.info(a + "th setup, " + i + "# Fitness: " + solution.getObjective(0)) ;

			    evaluator.shutdown();

				if (minRmseT > ((InvPendFuzzyContParamOpt) problem).getMinRmseT())
					minRmseT = ((InvPendFuzzyContParamOpt) problem).getMinRmseT();
				if (minDissimilarity > ((InvPendFuzzyContParamOpt) problem).getMinDissimilarity())
					minDissimilarity = ((InvPendFuzzyContParamOpt) problem).getMinDissimilarity();
				
				if (maxRmseT < ((InvPendFuzzyContParamOpt) problem).getMaxRmseT())
					maxRmseT = ((InvPendFuzzyContParamOpt) problem).getMaxRmseT();
				if (maxDissimilarity < ((InvPendFuzzyContParamOpt) problem).getMaxDissimilarity())
					maxDissimilarity = ((InvPendFuzzyContParamOpt) problem).getMaxDissimilarity();

			    OptOutput output = new OptOutput(((InvPendFuzzyContParamOpt) problem).getRmseTs(),
												    ((InvPendFuzzyContParamOpt) problem).getNormRmseTs(),
												    ((InvPendFuzzyContParamOpt) problem).getDissimilaritys(),
										    		((InvPendFuzzyContParamOpt) problem).getNormDissimilaritys(),
												    ((InvPendFuzzyContParamOpt) problem).getObjs(),
			    									((InvPendFuzzyContParamOpt) problem).getMidRmseT(),
			    									((InvPendFuzzyContParamOpt) problem).getMidNormRmseT(),
			    									((InvPendFuzzyContParamOpt) problem).getMidDissimilarity(),
			    									((InvPendFuzzyContParamOpt) problem).getMidNormDissimilarity(),
												    ((InvPendFuzzyContParamOpt) problem).getMidObj(),
												    ((InvPendFuzzyContParamOpt) problem).getMidOptFuzzyCont(),
												    ((InvPendFuzzyContParamOpt) problem).getBestRmseT(),
												    ((InvPendFuzzyContParamOpt) problem).getBestNormRmseT(),
												    ((InvPendFuzzyContParamOpt) problem).getBestDissimilarity(),
												    ((InvPendFuzzyContParamOpt) problem).getBestNormDissimilarity(),
												    ((InvPendFuzzyContParamOpt) problem).getBestObj(),
												    new FuzzyControllerOpt(solution.getVariables()));

			    optOutputs.add(output);
			}

			OptOutput bestOutput = report(objCoefRmseT + "-" + objCoefDissim, optOutputs);

			bestOptOutputs.add(new BestOptOutput(objCoefRmseT, objCoefDissim, bestOutput));

			logger.info("Best outputs so far:");

			bestOptOutputs.stream().sorted(comp).forEach(bestOptOutput -> {
				logger.info("[rmsetCoeff: " + formatter.format(bestOptOutput.getObjCoefRmseT()) + ", dissimCoeff: " + formatter.format(bestOptOutput.getObjCoefDissim()) + "] -> Obj: " +
							formatter.format(bestOptOutput.getOptOutput().getBestObj()) + ", Norm[" +
							formatter.format(bestOptOutput.getOptOutput().getBestNormRmseT()) + ", " +
							formatter.format(bestOptOutput.getOptOutput().getBestNormDissimilarity()) + "], Plain[" +
							formatter.format(bestOptOutput.getOptOutput().getBestRmseT()) + ", " +
							formatter.format(bestOptOutput.getOptOutput().getBestDissimilarity()) + "]");
			});

			logger.info("Best variables so far:");

			bestOptOutputs.stream().sorted(comp).forEach(bestOptOutput -> {
				logger.info("[rmsetCoeff: " + formatter.format(bestOptOutput.getObjCoefRmseT()) + ", dissimCoeff: " + formatter.format(bestOptOutput.getObjCoefDissim()) + "] -> " +
							Arrays.toString(bestOptOutput.getOptOutput().getBestController().getVariables().stream().map(x -> formatter.format(x)).toArray()) + "]");
			});

			logger.info("Max/Min costs so far:");
			logger.info("minRmseT = " + minRmseT);
			logger.info("minDissimilarity = " + minDissimilarity);
			logger.info("maxRmseT = " + maxRmseT);
			logger.info("maxDissimilarity = " + maxDissimilarity);

			logger.info("End of " + a + "th setup.");
		}
		
		InvertedPendulum ip = new InvertedPendulum();
		logger.info(ip.toString());
	}

	public static OptOutput report(String filePrefix, List<OptOutput> optOutputs) throws InterruptedException {
	    ControlSystem[] controlSystems = new ControlSystem[3];
		controlSystems[0] = new ControlSystem("Dictionary",
											Color.RED,
											Dictionary.defaultCont,
											new InvertedPendulum());

//		controlSystems[0].getCont().plotMembershipFunctions("dict", false);
//		controlSystems[0].getCont().plotControlSurface("dict");

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

		/*
		 * COMPARISON REPORT USING SIMULATION (BESTS)
		 */

		logger.info("-----------------------------------------------------------------------");
		logger.info("COMPARISON REPORT USING SIMULATION (BESTS)");
		logger.info("-----------------------------------------------------------------------");

		controlSystems[1] = new ControlSystem("Final Mid Optimized",
											Color.BLUE,
											bestsMid,
											new InvertedPendulum());

//		controlSystems[1].getCont().plotMembershipFunctions("final mid", true);
//		controlSystems[1].getCont().plotControlSurface("final mid");

		controlSystems[2] = new ControlSystem("Final Best Optimized",
											Color.GREEN,
											bestsBest,
											new InvertedPendulum());

//		controlSystems[2].getCont().plotMembershipFunctions("final best", true);
//		controlSystems[2].getCont().plotControlSurface("final best");

		plotConvergence(filePrefix + "_FinalBestConv", "Convergence", itrs, bestOutput.getObjs(), bestOutput.getRmseTs(), bestOutput.getDissimilaritys());
		plotConvergence(filePrefix + "_FinalBestNormConv", "NormConvergence", itrs, bestOutput.getObjs(), bestOutput.getNormRmseTs(), bestOutput.getNormDissimilaritys());

		Simulator.simulate(controlSystems, true, filePrefix + "_Sim+Opt+FinalMid+FinalBest");	//	plotLen %

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

		return bestOutput;
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

	private static Font legendFont = new Font("Tahoma", 1, 12);
	private static Font axisFont = new Font("Tahoma", 1, 12);
	private static Font axisLightFont = new Font("Tahoma", 1, 10);
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	private static void plotConvergence(String fileName, String title, double[] iters, double[] objs, double[] rmseTs, double[] dissimilaritys) {

		double minV = Integer.MAX_VALUE;
		double maxV = Integer.MIN_VALUE;

        for (int i = 0; i < maxItr; i++) {
        	if (objs[i] > maxV) maxV = objs[i];
        	if (objs[i] < minV) minV = objs[i];
        }

        for (int i = 0; i < maxItr; i++) {
        	if (rmseTs[i] > maxV) maxV = rmseTs[i];
        	if (rmseTs[i] < minV) minV = rmseTs[i];
        }

        for (int i = 0; i < maxItr; i++) {
        	if (dissimilaritys[i] > maxV) maxV = dissimilaritys[i];
        	if (dissimilaritys[i] < minV) minV = dissimilaritys[i];
        }

        if (minV > 0) minV *= 0.9; else minV *= 1.1;
        if (maxV < 0) maxV *= 0.9; else maxV *= 1.1;

        Container plot = new Plot2DPanel();

        ((Plot2DPanel)plot).setFont(legendFont);
        ((Plot2DPanel)plot).getAxis(0).setLabelText("Itr");
        ((Plot2DPanel)plot).getAxis(0).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(0).setLightLabelFont(axisLightFont);
        ((Plot2DPanel)plot).getAxis(1).setLabelText("Convergence (Cost)");
        ((Plot2DPanel)plot).getAxis(1).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(1).setLightLabelFont(axisLightFont);
        // define the legend position
        ((Plot2DPanel)plot).addLegend("SOUTH");            

        // add a line plot to the PlotPanel
        ((Plot2DPanel)plot).addLinePlot("Obj", Color.RED, iters, objs);
        ((Plot2DPanel)plot).setFixedBounds(1, minV, maxV);
        ((Plot2DPanel)plot).setFixedBounds(0, 0, maxItr);
        // add a line plot to the PlotPanel
        ((Plot2DPanel)plot).addLinePlot("RmseT", Color.BLUE, iters, rmseTs);
        ((Plot2DPanel)plot).setFixedBounds(1, minV, maxV);
        ((Plot2DPanel)plot).setFixedBounds(0, 0, maxItr);
        // add a line plot to the PlotPanel
        ((Plot2DPanel)plot).addLinePlot("Dissimilarity", Color.GREEN, iters, dissimilaritys);
        ((Plot2DPanel)plot).setFixedBounds(1, minV, maxV);
        ((Plot2DPanel)plot).setFixedBounds(0, 0, maxItr);


        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setContentPane(plot);
        frame.setVisible(true);

        try {
        	Thread.sleep(50);
        	((Plot2DPanel)plot).toGraphicFile(new File(LocalDateTime.now().format(dateTimeFormatter) + "_" + fileName + "_convergence.png"));
        	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}

}
