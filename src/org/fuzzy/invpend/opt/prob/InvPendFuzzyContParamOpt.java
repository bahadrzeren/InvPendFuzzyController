package org.fuzzy.invpend.opt.prob;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.run.RunDeOptimization;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.SystemPair;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class InvPendFuzzyContParamOpt extends AbstractDoubleProblem {

	private static final long serialVersionUID = 6052688537783762715L;

	public static int numOfVars = 2 * (5 + 3 + 7);

	public InvPendFuzzyContParamOpt(double centerSearchRage,
									double sigmaSearchRage) {
	    setNumberOfVariables(numOfVars);
	    setNumberOfObjectives(1);
	    setName("InvPendFuzzyContParamOpt");

	    List<Double> lowerLimit = new ArrayList<Double>(getNumberOfVariables());
	    List<Double> upperLimit = new ArrayList<Double>(getNumberOfVariables());

	    /*
	     *	THETA 
	     */
	    //	Tiny Center
		lowerLimit.add(Dictionary.dedaultVars.get(0) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(0) + centerSearchRage);
		//	Tiny Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(1) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(1) + sigmaSearchRage);

	    //	Some Center
		lowerLimit.add(Dictionary.dedaultVars.get(2) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(2) + centerSearchRage);
		//	Some Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(3) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(3) + sigmaSearchRage);

	    //	Medium Center
		lowerLimit.add(Dictionary.dedaultVars.get(4) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(4) + centerSearchRage);
		//	Medium Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(5) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(5) + sigmaSearchRage);

	    //	Good amount Center
		lowerLimit.add(Dictionary.dedaultVars.get(6) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(6) + centerSearchRage);
		//	Good amount Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(7) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(7) + sigmaSearchRage);

	    //	Very large Center
		lowerLimit.add(Dictionary.dedaultVars.get(8) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(8) + centerSearchRage);
		//	Very large Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(9) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(9) + sigmaSearchRage);

	    /*
	     *	THETA DELTA
	     */
	    //	Tiny Center
		lowerLimit.add(Dictionary.dedaultVars.get(10) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(10) + centerSearchRage);
		//	Tiny Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(11) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(11) + sigmaSearchRage);

	    //	Medium Center
		lowerLimit.add(Dictionary.dedaultVars.get(12) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(12) + centerSearchRage);
		//	Medium Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(13) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(13) + sigmaSearchRage);

	    //	Very large Center
		lowerLimit.add(Dictionary.dedaultVars.get(14) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(14) + centerSearchRage);
		//	Very large Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(15) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(15) + sigmaSearchRage);

	    /*
	     *	FORCE
	     */
	    //	Tiny Center
		lowerLimit.add(Dictionary.dedaultVars.get(16) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(16) + centerSearchRage);
		//	Tiny Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(17) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(17) + sigmaSearchRage);

	    //	Small Center
		lowerLimit.add(Dictionary.dedaultVars.get(18) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(18) + centerSearchRage);
		//	Small Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(19) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(19) + sigmaSearchRage);

	    //	Some Center
		lowerLimit.add(Dictionary.dedaultVars.get(20) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(20) + centerSearchRage);
		//	Some Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(21) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(21) + sigmaSearchRage);

	    //	Medium Center
		lowerLimit.add(Dictionary.dedaultVars.get(22) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(22) + centerSearchRage);
		//	Medium Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(23) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(23) + sigmaSearchRage);

	    //	Good amount Center
		lowerLimit.add(Dictionary.dedaultVars.get(24) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(24) + centerSearchRage);
		//	Good amount Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(25) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(25) + sigmaSearchRage);

	    //	Large Center
		lowerLimit.add(Dictionary.dedaultVars.get(26) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(26) + centerSearchRage);
		//	Large Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(27) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(27) + sigmaSearchRage);

	    //	Very large Center
		lowerLimit.add(Dictionary.dedaultVars.get(28) - centerSearchRage);
		upperLimit.add(Dictionary.dedaultVars.get(28) + centerSearchRage);
		//	Very large Sigma
		lowerLimit.add(Dictionary.dedaultVars.get(29) - sigmaSearchRage);
		if (lowerLimit.get(lowerLimit.size() - 1) < 0.0) lowerLimit.set(lowerLimit.size() - 1, 0.0);
		upperLimit.add(Dictionary.dedaultVars.get(29) + sigmaSearchRage);

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);

	    /*
	     * Simulator
	     */
	    this.systemPairs = new SystemPair[1];
		systemPairs[0] = new SystemPair();
		systemPairs[0].caption = "Optimized";
		systemPairs[0].color = Color.RED;
		systemPairs[0].pend = Simulator.generateNewPendulum();
	}

	private SystemPair[] systemPairs = null;

	private int itr = 0;

	public List<Double> midVariables = null;
	public double midObj = Integer.MAX_VALUE;
	public double midRmse = Integer.MAX_VALUE;
	public double midDissimilarity = Integer.MAX_VALUE;

	public double bestObj = Integer.MAX_VALUE;
	public double bestRmse = Integer.MAX_VALUE;
	public double bestDissimilarity = Integer.MAX_VALUE;

	public FuzzyControllerOpt midOptFuzzyCont = null;

	@Override
	public synchronized void evaluate(DoubleSolution solution) {
		FuzzyControllerOpt solCont = new FuzzyControllerOpt(solution.getVariables());

		systemPairs[0].rmseT = 0.0;
		systemPairs[0].rmseTd = 0.0;
		systemPairs[0].rmseF = 0.0;
		systemPairs[0].rmseX = 0.0;
		systemPairs[0].rmseXd = 0.0;
		systemPairs[0].cont = solCont;
		Simulator.resetPendulum(systemPairs[0].pend);

		Simulator.simulate(systemPairs, false);

		double jaccardDissimilarity = 1.0 - Dictionary.defaultCont.getAvgJaccardSimilarity(solCont);

//		solution.setObjective(0, systemPairs[0].rmseT);
		solution.setObjective(0, 0.5 * systemPairs[0].rmseT + 0.5 * jaccardDissimilarity);

		if (bestObj > solution.getObjective(0)) {
			bestObj = solution.getObjective(0);
			bestRmse = systemPairs[0].rmseT;
			bestDissimilarity = jaccardDissimilarity;
			if (midOptFuzzyCont == null) {
				ArrayList<Double> hl = ((ArrayList<Double>) solution.getVariables());
				midVariables = (List<Double>) hl.clone();
				if (itr >= RunDeOptimization.maxItr / 2) {
					midOptFuzzyCont = new FuzzyControllerOpt(midVariables);
					midObj = bestObj;
					midRmse = bestRmse;
					midDissimilarity = bestDissimilarity;
				}
			}
		}

		System.out.println((++itr) + " - obj: " + solution.getObjective(0) + "/" + bestObj + " - RMSE: " + systemPairs[0].rmseT + "/" + bestRmse + " - JacSim: "  + jaccardDissimilarity + "/" + bestDissimilarity);
	}
}
