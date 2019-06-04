package org.fuzzy.opt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fuzzy.cont.FuzzyControllerTriangular;
import org.fuzzy.opt.cont.FuzzyControllerOpt;
import org.sim.Simulator;
import org.sim.SystemPair;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class InvPendFuzzyContParamOpt extends AbstractDoubleProblem {

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
		systemPairs[0].caption = "Opt Reference";
		systemPairs[0].color = Color.RED;
		systemPairs[0].pend = Simulator.generateNewPendulum();
	}

	private SystemPair[] systemPairs = null;

	@Override
	public void evaluate(DoubleSolution solution) {
		systemPairs[0].cont = new FuzzyControllerOpt(solution.getVariables());

		int varNdx = 0;
		solution.getVariableValue(varNdx++);

//		solution.setObjective(0, sum);
	}
}
