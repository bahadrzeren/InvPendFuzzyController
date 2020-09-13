package org.fuzzy.invpend.opt.prob;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.ControlSystem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class InvPendFuzzyContParamOpt extends AbstractDoubleProblem {

	private static Logger logger = LogManager.getLogger(InvPendFuzzyContParamOpt.class);

	private static final long serialVersionUID = 6052688537783762715L;

	private int maxItr = 0;
//	private static int numOfVars = 2 * (5 + 3 + 7);

	private double objCoefRmseT = 0.5;
	private double objCoefDissim = 0.5;

//	2020-09-13 07:04:31 INFO  RunDeOptimizationMult:213 - Max/Min costs so far:
//	2020-09-13 07:04:31 INFO  RunDeOptimizationMult:214 - minRmseT = 4.941936629717052
//	2020-09-13 07:04:31 INFO  RunDeOptimizationMult:215 - minDissimilarity = 0.002704859026779527
//	2020-09-13 07:04:31 INFO  RunDeOptimizationMult:216 - maxRmseT = 30.159535726920165
//	2020-09-13 07:04:31 INFO  RunDeOptimizationMult:217 - maxDissimilarity = 0.475356475609351
//
//	2020-09-13 12:09:43 INFO  RunDeOptimizationMult:221 - minRmseT = 5.62416750593947
//	2020-09-13 12:09:43 INFO  RunDeOptimizationMult:222 - minDissimilarity = 0.0011740059851901297
//	2020-09-13 12:09:43 INFO  RunDeOptimizationMult:223 - maxRmseT = 29.910931952816796
//	2020-09-13 12:09:43 INFO  RunDeOptimizationMult:224 - maxDissimilarity = 0.40457212339451487
//
//	2020-09-13 13:04:06 INFO  RunDeOptimizationMult:221 - minRmseT = 5.449252717536699
//	2020-09-13 13:04:06 INFO  RunDeOptimizationMult:222 - minDissimilarity = 0.0011740059851901297
//	2020-09-13 13:04:06 INFO  RunDeOptimizationMult:223 - maxRmseT = 33.49791787742926
//	2020-09-13 13:04:06 INFO  RunDeOptimizationMult:224 - maxDissimilarity = 0.40970153693223355
//
//
//
//	2020-09-13 07:04:31 INFO  RunDeOptimizationMult:213 - Max/Min costs so far:
//	2020-09-13 07:06:40 INFO  RunDeOptimizationMult:214 - minRmseT = 0.7246634436187402
//	2020-09-13 07:06:40 INFO  RunDeOptimizationMult:215 - minDissimilarity = 0.008130564278186592
//	2020-09-13 07:06:40 INFO  RunDeOptimizationMult:216 - maxRmseT = 32.87187242165629
//	2020-09-13 07:06:40 INFO  RunDeOptimizationMult:217 - maxDissimilarity = 0.4950770095545515
//
//	2020-09-13 12:09:47 INFO  RunDeOptimizationMult:221 - minRmseT = 0.8875118205424842
//	2020-09-13 12:09:47 INFO  RunDeOptimizationMult:222 - minDissimilarity = 0.0010499318176603323
//	2020-09-13 12:09:47 INFO  RunDeOptimizationMult:223 - maxRmseT = 32.717263326461115
//	2020-09-13 12:09:47 INFO  RunDeOptimizationMult:224 - maxDissimilarity = 0.4073763505121716
//
//	2020-09-13 13:04:11 INFO  RunDeOptimizationMult:221 - minRmseT = 0.8812448959139009
//	2020-09-13 13:04:11 INFO  RunDeOptimizationMult:222 - minDissimilarity = 0.0010499318176603323
//	2020-09-13 13:04:11 INFO  RunDeOptimizationMult:223 - maxRmseT = 32.717263326461115
//	2020-09-13 13:04:11 INFO  RunDeOptimizationMult:224 - maxDissimilarity = 0.41421704805954696

	private double normMinRmseT = 0.65;
	private double normMinDissimilarity = 0.0008;
	private double normMaxRmseT = 39.0;
	private double normMaxDissimilarity = 0.55;
	private double normRangeRmseT = normMaxRmseT - normMinRmseT;
	private double normRangeDissimilarity = normMaxDissimilarity - normMinDissimilarity;

	public void init(int maxItr,
						double centerSearchRage,
						double sigmaSearchRage,
						double objCoefRmseT,
						double objCoefDissim) {
		this.maxItr = maxItr;
	    setNumberOfVariables(30);
	    setNumberOfObjectives(1);
	    setName("InvPendFuzzyContParamOpt");

	    List<Double> lowerLimit = new ArrayList<Double>(getNumberOfVariables());
	    List<Double> upperLimit = new ArrayList<Double>(getNumberOfVariables());

	    /*
	     *	THETA 
	     */
	    for (int i = 0; i < 30 / 2; i++) {
			lowerLimit.add(Dictionary.defaultVars.get(2 * i) - centerSearchRage);
			upperLimit.add(Dictionary.defaultVars.get(2 * i) + centerSearchRage);
			lowerLimit.add(Dictionary.defaultVars.get(2 * i + 1) - sigmaSearchRage);
			if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
			upperLimit.add(Dictionary.defaultVars.get(2 * i + 1) + sigmaSearchRage);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);

	    this.objCoefRmseT = objCoefRmseT;
	    this.objCoefDissim = objCoefDissim;

	    /*
	     * Simulator
	     */
	    this.controlSystems = new ControlSystem[1];
		controlSystems[0] = new ControlSystem("Optimized",
											Color.RED,
											null,
											new InvertedPendulum());

		this.objs = new double[maxItr];
		this.rmseTs = new double[maxItr];
		this.dissimilaritys = new double[maxItr];
		this.normRmseTs = new double[maxItr];
		this.normDissimilaritys = new double[maxItr];
	}

	public InvPendFuzzyContParamOpt(int maxItr,
			double centerSearchRage,
			double sigmaSearchRage,
			double objCoefRmseT,
			double objCoefDissim) {
		this.init(maxItr, centerSearchRage, sigmaSearchRage, objCoefRmseT, objCoefDissim);
	}

	public InvPendFuzzyContParamOpt(int maxItr,
									double centerSearchRage,
									double sigmaSearchRage,
									double objCoefRmseT,
									double objCoefDissim,
									double normMinRmseT,
									double normMinDissimilarity,
									double normMaxRmseT,
									double normMaxDissimilarity) {
		this.init(maxItr, centerSearchRage, sigmaSearchRage, objCoefRmseT, objCoefDissim);
	    this.normMinRmseT = normMinRmseT;
	    this.normMinDissimilarity = normMinDissimilarity;
	    this.normMaxRmseT = normMaxRmseT;
	    this.normMaxDissimilarity = normMaxDissimilarity;
	}

	private ControlSystem[] controlSystems = null;

	private int itr = 0;

	private double minRmseT = Integer.MAX_VALUE;
	private double minDissimilarity = Integer.MAX_VALUE;
	private double maxRmseT = 0.0;
	private double maxDissimilarity = 0.0;

	private double[] objs = null;
	private double[] rmseTs = null;
	private double[] dissimilaritys = null;
	private double[] normRmseTs = null;
	private double[] normDissimilaritys = null;

	private double midObj = Integer.MAX_VALUE;
	private double midRmseT = Integer.MAX_VALUE;
	private double midNormRmseT = Integer.MAX_VALUE;
	private double midDissimilarity = Integer.MAX_VALUE;
	private double midNormDissimilarity = Integer.MAX_VALUE;

	private double bestObj = Integer.MAX_VALUE;
	private double bestRmseT = Integer.MAX_VALUE;
	private double bestNormRmseT = Integer.MAX_VALUE;
	private double bestDissimilarity = Integer.MAX_VALUE;
	private double bestNormDissimilarity = Integer.MAX_VALUE;

	public double[] getObjs() {
		return this.objs;
	}

	public double[] getRmseTs() {
		return this.rmseTs;
	}

	public double[] getDissimilaritys() {
		return this.dissimilaritys;
	}

	public double[] getNormRmseTs() {
		return this.normRmseTs;
	}

	public double[] getNormDissimilaritys() {
		return this.normDissimilaritys;
	}

	public double getMinRmseT() {
		return minRmseT;
	}

	public double getMinDissimilarity() {
		return minDissimilarity;
	}

	public double getMaxRmseT() {
		return maxRmseT;
	}

	public double getMaxDissimilarity() {
		return maxDissimilarity;
	}

	public double getMidObj() {
		return midObj;
	}

	public double getMidRmseT() {
		return midRmseT;
	}

	public double getMidNormRmseT() {
		return midNormRmseT;
	}

	public double getMidDissimilarity() {
		return midDissimilarity;
	}

	public double getMidNormDissimilarity() {
		return midNormDissimilarity;
	}

	public double getBestObj() {
		return bestObj;
	}

	public double getBestRmseT() {
		return bestRmseT;
	}

	public double getBestNormRmseT() {
		return bestNormRmseT;
	}

	public double getBestDissimilarity() {
		return bestDissimilarity;
	}

	public double getBestNormDissimilarity() {
		return bestNormDissimilarity;
	}

	//	private List<Double> midVariables = null;
	private FuzzyControllerOpt midOptFuzzyCont = null;

	public FuzzyControllerOpt getMidOptFuzzyCont() {
		return midOptFuzzyCont;
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	@Override
	public synchronized void evaluate(DoubleSolution solution) {
		FuzzyControllerOpt solCont = new FuzzyControllerOpt(solution.getVariables());

		controlSystems[0].setCont(solCont);

		Simulator.simulate(controlSystems, false, null);

		controlSystems[0].setCont(null);

		double rmseT = controlSystems[0].getRmseT();
		double dissimilarity = 1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity(solCont);
		double normRmseT = (rmseT - normMinRmseT) / normRangeRmseT;
		double normDissimilarity = (dissimilarity - normMinDissimilarity) / normRangeDissimilarity;

//		solution.setObjective(0, systemPairs[0].rmseT);
//		solution.setObjective(0, 0.9 * rmseT + 0.1 * dissimilarity);
//		solution.setObjective(0, objCoefRmseT * rmseT + objCoefDissim * dissimilarity);
		solution.setObjective(0, objCoefRmseT * normRmseT + objCoefDissim * normDissimilarity);

		if (minRmseT > rmseT)
			minRmseT = rmseT;
		if (minDissimilarity > dissimilarity)
			minDissimilarity = dissimilarity;

		if (maxRmseT < rmseT)
			maxRmseT = rmseT;
		if (maxDissimilarity < dissimilarity)
			maxDissimilarity = dissimilarity;

		if (bestObj > solution.getObjective(0)) {
			bestObj = solution.getObjective(0);
			bestRmseT = rmseT;
			bestDissimilarity = dissimilarity;
			bestNormRmseT = (bestRmseT - normMinRmseT) / normRangeRmseT;
			bestNormDissimilarity = (bestDissimilarity - normMinDissimilarity) / normRangeDissimilarity;;

			if (itr < this.maxItr / 2) {
//				ArrayList<Double> hl = ((ArrayList<Double>) solution.getVariables());
//				midVariables = (ArrayList<Double>) hl.clone();
				midOptFuzzyCont = solCont;	//	new FuzzyControllerOpt(midVariables);
				midObj = bestObj;
				midRmseT = bestRmseT;
				midNormRmseT = bestNormRmseT;
				midDissimilarity = bestDissimilarity;
				midNormDissimilarity = bestNormDissimilarity;
			}
		}

		this.objs[itr] = bestObj;
		this.rmseTs[itr] = bestRmseT;
		this.normRmseTs[itr] = bestNormRmseT;
		this.dissimilaritys[itr] = bestDissimilarity;
		this.normDissimilaritys[itr] = bestNormDissimilarity;

		logger.info((++itr) + " - obj: " + formatter.format(solution.getObjective(0)) + "/" + formatter.format(bestObj) + 
								" - RMSET: " + formatter.format(rmseT) + "/" + formatter.format(bestRmseT) + 
								" - NormRMSET: " + formatter.format(normRmseT) + "/" + formatter.format(bestNormRmseT) + 
								" - JacDissim: "  + formatter.format(dissimilarity) + "/" + formatter.format(bestDissimilarity) +
								" - NormJacDissim: "  + formatter.format(normDissimilarity) + "/" + formatter.format(bestNormDissimilarity)
								);
	}
}
