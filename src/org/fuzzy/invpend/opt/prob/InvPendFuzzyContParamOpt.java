package org.fuzzy.invpend.opt.prob;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.SystemPair;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class InvPendFuzzyContParamOpt extends AbstractDoubleProblem {

	private static final long serialVersionUID = 6052688537783762715L;

	public static List<Double> defaultVariables = new ArrayList<Double>();

	static {
	    /*
	     *	THETA
	     */
	    //	Tiny Center
		defaultVariables.add(0.15);
		//	Tiny Sigma
		defaultVariables.add(2.00546);

	    //	Some Center
		defaultVariables.add(3.31);
		//	Some Sigma
		defaultVariables.add(1.93929);

	    //	Medium Center
		defaultVariables.add(5.025);
		//	Medium Sigma
		defaultVariables.add(1.09813);

	    //	Good amount Center
		defaultVariables.add(6.46667);
		//	Good amount Sigma
		defaultVariables.add(1.74327);

	    //	Very large Center
		defaultVariables.add(9.95002);
		//	Very large Sigma
		defaultVariables.add(2.42824);

	    /*
	     *	THETA DELTA
	     */
	    //	Tiny Center
		defaultVariables.add(0.15);
		//	Tiny Sigma
		defaultVariables.add(2.00546);

	    //	Medium Center
		defaultVariables.add(5.025);
		//	Medium Sigma
		defaultVariables.add(1.09813);

	    //	Very large Center
		defaultVariables.add(9.95002);
		//	Very large Sigma
		defaultVariables.add(2.42824);

	    /*
	     *	FORCE
	     */
	    //	Tiny Center
		defaultVariables.add(0.15);
		//	Tiny Sigma
		defaultVariables.add(2.00546);

	    //	Small Center
		defaultVariables.add(1.7);
		//	Small Sigma
		defaultVariables.add(1.80021);

	    //	Some Center
		defaultVariables.add(3.31);
		//	Some Sigma
		defaultVariables.add(1.93929);

	    //	Medium Center
		defaultVariables.add(5.025);
		//	Medium Sigma
		defaultVariables.add(1.09813);

	    //	Good amount Center
		defaultVariables.add(6.46667);
		//	Good amount Sigma
		defaultVariables.add(1.74327);

	    //	Large Center
		defaultVariables.add(8.35001);
		//	Large Sigma
		defaultVariables.add(1.49622);

	    //	Very large Center
		defaultVariables.add(9.95002);
		//	Very large Sigma
		defaultVariables.add(2.42824);
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	private static int reportSimilarity(String mfName, int varNdx, double centerRange, double sigmaRange, List<Double> optimizedVariables, List<Double> cSim, List<Double> sSim) {

		int res = varNdx;

		double similarity = 1.0 / (1.0 + Math.exp(- Math.abs(optimizedVariables.get(res) - defaultVariables.get(res)) / centerRange));
		cSim.add(similarity);
		System.out.println(mfName + " Center#" + formatter.format(defaultVariables.get(res)) + "#" + formatter.format(optimizedVariables.get(res)) + "#sigmoid(|Cb - Ca|/Crange) = " + formatter.format(similarity));
		res++;

		similarity = 1.0 / (1.0 + Math.exp(- Math.abs(optimizedVariables.get(res) - defaultVariables.get(res)) / sigmaRange));
		sSim.add(similarity);
		System.out.println(mfName + " Sigma#" + formatter.format(defaultVariables.get(res)) + "#" + formatter.format(optimizedVariables.get(res)) + "#sigmoid(|Sb - Sa|/Srange) = " + formatter.format(similarity));
		res++;

		return res;
	}

	public static void calculateSimilarity(List<Double> optimizedVariables, double centerRange, double sigmaRange, double centerW, double sigmaW) {

		int varNdx = 0;
		List<Double> cSim = new ArrayList<Double>();
		List<Double> sSim = new ArrayList<Double>();

		System.out.println("#INTERPRETABLE#OPTIMIZED#SIMILARITY");

		/*
		 * THETA
		 */
		varNdx = reportSimilarity("Tiny", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Some", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Medium", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Good amount", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Very large", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);

	    /*
	     *	THETA DELTA
	     */
		varNdx = reportSimilarity("Tiny", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Medium", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Very large", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);

	    /*
	     *	FORCE
	     */
		varNdx = reportSimilarity("Tiny", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Small", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Some", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Medium", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Good amount", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Large", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);
		varNdx = reportSimilarity("Very large", varNdx, centerRange, sigmaRange, optimizedVariables, cSim, sSim);

		double simCmin = Integer.MAX_VALUE;
		double simCmax = Integer.MIN_VALUE;
		double simCtot = 0.0;
		double simCavg = 0.0;
		double simSmin = Integer.MAX_VALUE;
		double simSmax = Integer.MIN_VALUE;
		double simStot = 0.0;
		double simSavg = 0.0;

		for (int i = 0; i < varNdx / 2; i++) {
			if (simCmin > cSim.get(i)) simCmin = cSim.get(i);
			if (simCmax < cSim.get(i)) simCmax = cSim.get(i);
			simCtot += cSim.get(i);
			if (simSmin > sSim.get(i)) simSmin = sSim.get(i);
			if (simSmax < sSim.get(i)) simSmax = sSim.get(i);
			simStot += sSim.get(i);
		}
		simCavg = 2.0 * simCtot / varNdx;
		simSavg = 2.0 * simStot / varNdx;

		System.out.println("Center similarity (max/avg/min/tot): ###" + formatter.format(simCmax) + "/" + formatter.format(simCavg) + "/" + formatter.format(simCmin) + "/" + formatter.format(simCtot));
		System.out.println("Sigma similarity (max/avg/min/tot): ###" + formatter.format(simSmax) + "/" + formatter.format(simSavg) + "/" + formatter.format(simSmin) + "/" + formatter.format(simStot));
		System.out.println("Weighted similarity (max/avg/min/tot): ###" + formatter.format(simCmax * centerW + simSmax * sigmaW) + "/" +
																		formatter.format(simCavg * centerW + simSavg * sigmaW) + "/" +
																		formatter.format(simCmin * centerW + simSmin * sigmaW) + "/" +
																		formatter.format(simCtot * centerW + simStot * sigmaW));
	}

	public InvPendFuzzyContParamOpt(double centerSearchRage,
									double sigmaSearchRage) {
	    setNumberOfVariables(2 * (5 + 3 + 7));
	    setNumberOfObjectives(1);
	    setName("InvPendFuzzyContParamOpt");

	    List<Double> lowerLimit = new ArrayList<Double>(getNumberOfVariables());
	    List<Double> upperLimit = new ArrayList<Double>(getNumberOfVariables());

	    /*
	     *	THETA 
	     */
	    //	Tiny Center
		lowerLimit.add(0.15 - centerSearchRage);
		upperLimit.add(0.15 + centerSearchRage);
		//	Tiny Sigma
		lowerLimit.add(2.00546 - sigmaSearchRage);
		upperLimit.add(2.00546 + sigmaSearchRage);

	    //	Some Center
		lowerLimit.add(3.31 - centerSearchRage);
		upperLimit.add(3.31 + centerSearchRage);
		//	Some Sigma
		lowerLimit.add(1.93929 - sigmaSearchRage);
		upperLimit.add(1.93929 + sigmaSearchRage);

	    //	Medium Center
		lowerLimit.add(5.025 - centerSearchRage);
		upperLimit.add(5.025 + centerSearchRage);
		//	Medium Sigma
		lowerLimit.add(1.09813 - sigmaSearchRage);
		upperLimit.add(1.09813 + sigmaSearchRage);

	    //	Good amount Center
		lowerLimit.add(6.46667 - centerSearchRage);
		upperLimit.add(6.46667 + centerSearchRage);
		//	Good amount Sigma
		lowerLimit.add(1.74327 - sigmaSearchRage);
		upperLimit.add(1.74327 + sigmaSearchRage);

	    //	Very large Center
		lowerLimit.add(9.95002 - centerSearchRage);
		upperLimit.add(9.95002 + centerSearchRage);
		//	Very large Sigma
		lowerLimit.add(2.42824 - sigmaSearchRage);
		upperLimit.add(2.42824 + sigmaSearchRage);

	    /*
	     *	THETA DELTA
	     */
	    //	Tiny Center
		lowerLimit.add(0.15 - centerSearchRage);
		upperLimit.add(0.15 + centerSearchRage);
		//	Tiny Sigma
		lowerLimit.add(2.00546 - sigmaSearchRage);
		upperLimit.add(2.00546 + sigmaSearchRage);

	    //	Medium Center
		lowerLimit.add(5.025 - centerSearchRage);
		upperLimit.add(5.025 + centerSearchRage);
		//	Medium Sigma
		lowerLimit.add(1.09813 - sigmaSearchRage);
		upperLimit.add(1.09813 + sigmaSearchRage);

	    //	Very large Center
		lowerLimit.add(9.95002 - centerSearchRage);
		upperLimit.add(9.95002 + centerSearchRage);
		//	Very large Sigma
		lowerLimit.add(2.42824 - sigmaSearchRage);
		upperLimit.add(2.42824 + sigmaSearchRage);


	    /*
	     *	FORCE
	     */
	    //	Tiny Center
		lowerLimit.add(0.15 - centerSearchRage);
		upperLimit.add(0.15 + centerSearchRage);
		//	Tiny Sigma
		lowerLimit.add(2.00546 - sigmaSearchRage);
		upperLimit.add(2.00546 + sigmaSearchRage);

	    //	Small Center
		lowerLimit.add(1.7 - centerSearchRage);
		upperLimit.add(1.7 + centerSearchRage);
		//	Small Sigma
		lowerLimit.add(1.80021 - sigmaSearchRage);
		upperLimit.add(1.80021 + sigmaSearchRage);

	    //	Some Center
		lowerLimit.add(3.31 - centerSearchRage);
		upperLimit.add(3.31 + centerSearchRage);
		//	Some Sigma
		lowerLimit.add(1.93929 - sigmaSearchRage);
		upperLimit.add(1.93929 + sigmaSearchRage);

	    //	Medium Center
		lowerLimit.add(5.025 - centerSearchRage);
		upperLimit.add(5.025 + centerSearchRage);
		//	Medium Sigma
		lowerLimit.add(1.09813 - sigmaSearchRage);
		upperLimit.add(1.09813 + sigmaSearchRage);

	    //	Good amount Center
		lowerLimit.add(6.46667 - centerSearchRage);
		upperLimit.add(6.46667 + centerSearchRage);
		//	Good amount Sigma
		lowerLimit.add(1.74327 - sigmaSearchRage);
		upperLimit.add(1.74327 + sigmaSearchRage);

	    //	Large Center
		lowerLimit.add(8.35001 - centerSearchRage);
		upperLimit.add(8.35001 + centerSearchRage);
		//	Large Sigma
		lowerLimit.add(1.49622 - sigmaSearchRage);
		upperLimit.add(1.49622 + sigmaSearchRage);

	    //	Very large Center
		lowerLimit.add(9.95002 - centerSearchRage);
		upperLimit.add(9.95002 + centerSearchRage);
		//	Very large Sigma
		lowerLimit.add(2.42824 - sigmaSearchRage);
		upperLimit.add(2.42824 + sigmaSearchRage);

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

	@Override
	public synchronized void evaluate(DoubleSolution solution) {
		systemPairs[0].rmseT = 0.0;
		systemPairs[0].rmseTd = 0.0;
		systemPairs[0].rmseF = 0.0;
		systemPairs[0].rmseX = 0.0;
		systemPairs[0].rmseXd = 0.0;
		systemPairs[0].cont = new FuzzyControllerOpt(solution.getVariables());
		Simulator.resetPendulum(systemPairs[0].pend);

		Simulator.simulate(systemPairs, false);

		solution.setObjective(0, systemPairs[0].rmseT);

		System.out.println((++itr) + " - " + systemPairs[0].rmseT);
	}
}
