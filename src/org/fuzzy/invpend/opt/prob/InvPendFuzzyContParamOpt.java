package org.fuzzy.invpend.opt.prob;

import java.awt.Color;
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

	private int maxItr = 2000;
	public static int numOfVars = 2 * (5 + 3 + 7);

	public InvPendFuzzyContParamOpt(int maxItr,
									double centerSearchRage,
									double sigmaSearchRage) {
		this.maxItr = maxItr;
	    setNumberOfVariables(numOfVars);
	    setNumberOfObjectives(1);
	    setName("InvPendFuzzyContParamOpt");

	    List<Double> lowerLimit = new ArrayList<Double>(getNumberOfVariables());
	    List<Double> upperLimit = new ArrayList<Double>(getNumberOfVariables());

	    /*
	     *	THETA 
	     */
	    for (int i = 0; i < numOfVars / 2; i++) {
			lowerLimit.add(Dictionary.defaultVars.get(2 * i) - centerSearchRage);
			upperLimit.add(Dictionary.defaultVars.get(2 * i) + centerSearchRage);
			lowerLimit.add(Dictionary.defaultVars.get(2 * i + 1) - sigmaSearchRage);
			if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
			upperLimit.add(Dictionary.defaultVars.get(2 * i + 1) + sigmaSearchRage);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);

	    /*
	     * Simulator
	     */
	    this.controlSystems = new ControlSystem[1];
		controlSystems[0] = new ControlSystem("Optimized",
											Color.RED,
											null,
											new InvertedPendulum());
	}

	private ControlSystem[] controlSystems = null;

	private int itr = 0;

	private double midObj = Integer.MAX_VALUE;
	private double midRmseT = Integer.MAX_VALUE;
	private double midDissimilarity = Integer.MAX_VALUE;

	private double bestObj = Integer.MAX_VALUE;
	private double bestRmseT = Integer.MAX_VALUE;
	private double bestDissimilarity = Integer.MAX_VALUE;

	public double getMidObj() {
		return midObj;
	}

	public double getMidRmseT() {
		return midRmseT;
	}

	public double getMidDissimilarity() {
		return midDissimilarity;
	}

	public double getBestObj() {
		return bestObj;
	}

	public double getBestRmseT() {
		return bestRmseT;
	}

	public double getBestDissimilarity() {
		return bestDissimilarity;
	}

	//	private List<Double> midVariables = null;
	private FuzzyControllerOpt midOptFuzzyCont = null;

	public FuzzyControllerOpt getMidOptFuzzyCont() {
		return midOptFuzzyCont;
	}

	@Override
	public synchronized void evaluate(DoubleSolution solution) {
		FuzzyControllerOpt solCont = new FuzzyControllerOpt(solution.getVariables());

		controlSystems[0].setCont(solCont);

		Simulator.simulate(controlSystems, false, null);

		controlSystems[0].setCont(null);

		double jaccardDissimilarity = 1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity(solCont);

//		solution.setObjective(0, systemPairs[0].rmseT);
		solution.setObjective(0, 0.5 * controlSystems[0].getRmseT() + 0.5 * jaccardDissimilarity);

		if (bestObj > solution.getObjective(0)) {
			bestObj = solution.getObjective(0);
			bestRmseT = controlSystems[0].getRmseT();
			bestDissimilarity = jaccardDissimilarity;
			if (itr < this.maxItr / 2) {
//				ArrayList<Double> hl = ((ArrayList<Double>) solution.getVariables());
//				midVariables = (ArrayList<Double>) hl.clone();
				midOptFuzzyCont = solCont;	//	new FuzzyControllerOpt(midVariables);
				midObj = bestObj;
				midRmseT = bestRmseT;
				midDissimilarity = bestDissimilarity;
			}
		}

		logger.info((++itr) + " - obj: " + solution.getObjective(0) + "/" + bestObj + 
								" - RMSET: " + controlSystems[0].getRmseT() + "/" + bestRmseT + 
								" - JacDissim: "  + jaccardDissimilarity + "/" + bestDissimilarity);
	}
}
