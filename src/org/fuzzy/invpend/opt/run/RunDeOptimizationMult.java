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
	private static SystemPair[] systemPairWorst = null;

	private static double[] varsMinMid = new double[InvPendFuzzyContParamOpt.numOfVars];
	private static double[] varsAvgMid = new double[InvPendFuzzyContParamOpt.numOfVars];
	private static double[] varsMaxMid = new double[InvPendFuzzyContParamOpt.numOfVars];
	private static double objBestMid = Integer.MAX_VALUE;
	private static double objAvgMid = 0.0;
	private static double objWorstMid = 0.0;
	private static double rmseBestMid = Integer.MAX_VALUE;
	private static double rmseAvgMid = 0.0;
	private static double rmseWorstMid = 0.0;
	private static double dissimilarityBestMid = Integer.MAX_VALUE;
	private static double dissimilarityAvgMid = 0.0;
	private static double dissimilarityWorstMid = 0.0;

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

			systemPairs[i][1] = new SystemPair();
			systemPairs[i][1].caption = "Mid Optimized " + i;
			systemPairs[i][1].color = Color.BLUE;
			systemPairs[i][1].pend = Simulator.generateNewPendulum();
			systemPairs[i][1].cont = ((InvPendFuzzyContParamOpt) problem).midOptFuzzyCont;
			systemPairs[i][1].rmseT = ((InvPendFuzzyContParamOpt) problem).midRmse;
			systemPairs[i][1].variables = ((InvPendFuzzyContParamOpt) problem).midVariables;
			systemPairs[i][1].dissimilarity = ((InvPendFuzzyContParamOpt) problem).midDissimilarity;
			systemPairs[i][1].objective = ((InvPendFuzzyContParamOpt) problem).midObj;

//			systemPairs[i][1].cont.plotMembershipFunctions(true);
//			systemPairs[i][1].cont.plotControlSurface();

			systemPairs[i][2] = new SystemPair();
			systemPairs[i][2].caption = "Optimized " + i;
			systemPairs[i][2].color = Color.GREEN;
			systemPairs[i][2].pend = Simulator.generateNewPendulum();
			systemPairs[i][2].cont = new FuzzyControllerOpt(solution.getVariables());
			systemPairs[i][2].rmseT = ((InvPendFuzzyContParamOpt) problem).bestRmse;
			systemPairs[i][2].variables = solution.getVariables();
			systemPairs[i][2].dissimilarity = ((InvPendFuzzyContParamOpt) problem).bestDissimilarity;
			systemPairs[i][2].objective = ((InvPendFuzzyContParamOpt) problem).bestObj;

//			systemPairs[i][2].cont.plotMembershipFunctions(true);
//			systemPairs[i][2].cont.plotControlSurface();

			/*
			 * STATISTICAL VALUES ARE SET ACCORDING TO MID RESULT
			 */
			varsMinMid[0] = systemPairs[i][1].variables.get(0);
			varsAvgMid[0] = systemPairs[i][1].variables.get(0);
			varsMaxMid[0] = systemPairs[i][1].variables.get(0);
			for (int vi = 1; vi < systemPairs[i][1].variables.size(); vi++) {
				if (varsMinMid[vi] > systemPairs[i][1].variables.get(vi))
					varsMinMid[vi] = systemPairs[i][1].variables.get(vi);
				varsAvgMid[vi] += systemPairs[i][1].variables.get(vi);
				if (varsMaxMid[vi] < systemPairs[i][1].variables.get(vi))
					varsMaxMid[vi] = systemPairs[i][1].variables.get(vi);
			}

			if (objBestMid > systemPairs[i][1].objective)
				objBestMid = systemPairs[i][1].objective;
			objAvgMid += systemPairs[i][1].objective;
			if (objWorstMid < systemPairs[i][1].objective)
				objWorstMid = systemPairs[i][1].objective;

			if (rmseBestMid > systemPairs[i][1].rmseT)
				rmseBestMid = systemPairs[i][1].rmseT;
			rmseAvgMid += systemPairs[i][1].rmseT;
			if (rmseWorstMid < systemPairs[i][1].rmseT)
				rmseWorstMid = systemPairs[i][1].rmseT;

			if (dissimilarityBestMid > systemPairs[i][1].dissimilarity)
				dissimilarityBestMid = systemPairs[i][1].dissimilarity;
			dissimilarityAvgMid += systemPairs[i][1].dissimilarity;
			if (dissimilarityWorstMid < systemPairs[i][1].dissimilarity)
				dissimilarityWorstMid = systemPairs[i][1].dissimilarity;

			/*
			 * STATISTICAL VALUES ARE SET ACCORDING TO FINAL RESULT
			 */
			varsMin[0] = systemPairs[i][2].variables.get(0);
			varsAvg[0] = systemPairs[i][2].variables.get(0);
			varsMax[0] = systemPairs[i][2].variables.get(0);
			for (int vi = 1; vi < systemPairs[i][2].variables.size(); vi++) {
				if (varsMin[vi] > systemPairs[i][2].variables.get(vi))
					varsMin[vi] = systemPairs[i][2].variables.get(vi);
				varsAvg[vi] += systemPairs[i][2].variables.get(vi);
				if (varsMax[vi] < systemPairs[i][2].variables.get(vi))
					varsMax[vi] = systemPairs[i][2].variables.get(vi);
			}

			if (objBest > systemPairs[i][2].objective)
				objBest = systemPairs[i][2].objective;
			objAvg += systemPairs[i][2].objective;
			if (objWorst < systemPairs[i][2].objective)
				objWorst = systemPairs[i][2].objective;

			if (rmseBest > systemPairs[i][2].rmseT)
				rmseBest = systemPairs[i][2].rmseT;
			rmseAvg += systemPairs[i][2].rmseT;
			if (rmseWorst < systemPairs[i][2].rmseT)
				rmseWorst = systemPairs[i][2].rmseT;

			if (dissimilarityBest > systemPairs[i][2].dissimilarity)
				dissimilarityBest = systemPairs[i][2].dissimilarity;
			dissimilarityAvg += systemPairs[i][2].dissimilarity;
			if (dissimilarityWorst < systemPairs[i][2].dissimilarity)
				dissimilarityWorst = systemPairs[i][2].dissimilarity;

			/*
			 * SET THE BEST CONFIGURATION
			 */
			if ((systemPairBest == null)
					|| (systemPairBest[2].objective > systemPairs[i][2].objective))
				systemPairBest = systemPairs[i];
			if ((systemPairWorst == null)
					|| (systemPairWorst[2].objective < systemPairs[i][2].objective))
				systemPairWorst = systemPairs[i];
		}

		objAvgMid /= numOfRuns;
		rmseAvgMid /= numOfRuns;
		dissimilarityAvgMid /= numOfRuns;

		objAvg /= numOfRuns;
		rmseAvg /= numOfRuns;
		dissimilarityAvg /= numOfRuns;

		System.out.println("objMinMid\t" + "objAvgMid\t" + "objMaxMid\t" + 
							"rmseMinMid\t" + "rmseAvgMid\t" + "rmseMaxMid\t" + 
							"dissimilarityMinMid\t" + "dissimilarityAvgMid\t" + "dissimilarityMaxMid\t" + 
							"objMin\t" + "objAvg\t" + "objMax\t" + 
							"rmseMin\t" + "rmseAvg\t" + "rmseMax\t" + 
							"dissimilarityMin\t" + "dissimilarityAvg\t" + "dissimilarityMax");

		System.out.println(objBestMid + "\t" + objAvgMid + "\t" + objWorstMid + "\t" +
							rmseBestMid + "\t" + rmseAvgMid + "\t" + rmseWorstMid + "\t" +
							dissimilarityBestMid + "\t" + dissimilarityAvgMid + "\t" + dissimilarityWorstMid + "\t" +
							objBest + "\t" + objAvg + "\t" + objWorst + "\t" +
							rmseBest + "\t" + rmseAvg + "\t" + rmseWorst + "\t" +
							dissimilarityBest + "\t" + dissimilarityAvg + "\t" + dissimilarityWorst);

		System.out.println("Vars\t" +
							"varsAvgMid\t" +
							"\t" +
							"varsAvg\t");

		for (int vi = 0; vi < systemPairBest[1].variables.size(); vi++) {
			varsAvgMid[vi] /= numOfRuns;
			varsAvg[vi] /= numOfRuns;

			System.out.println("Var_" + vi + "\t" +
								varsAvgMid[vi] + "\t" +
								"\t" +
								varsAvg[vi]);
		}

		systemPairBest[0].cont.plotMembershipFunctions(false);
		systemPairBest[0].cont.plotControlSurface();
		systemPairBest[1].cont.plotMembershipFunctions(true);
		systemPairBest[1].cont.plotControlSurface();
		systemPairBest[2].cont.plotMembershipFunctions(true);
		systemPairBest[2].cont.plotControlSurface();

		Simulator.simulate(systemPairBest, true);	//	plotLen %

		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairBest[0].cont, (FuzzyControllerOpt) systemPairBest[1].cont);
		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairBest[0].cont, (FuzzyControllerOpt) systemPairBest[2].cont);

		systemPairWorst[0].cont.plotMembershipFunctions(false);
		systemPairWorst[0].cont.plotControlSurface();
		systemPairWorst[1].cont.plotMembershipFunctions(true);
		systemPairWorst[1].cont.plotControlSurface();
		systemPairWorst[2].cont.plotMembershipFunctions(true);
		systemPairWorst[2].cont.plotControlSurface();

		Simulator.simulate(systemPairWorst, true);	//	plotLen %

		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairWorst[0].cont, (FuzzyControllerOpt) systemPairWorst[1].cont);
		FuzzyInvPendController.reportSimilarity((FuzzyControllerOpt) systemPairWorst[0].cont, (FuzzyControllerOpt) systemPairWorst[2].cont);
	}
}
