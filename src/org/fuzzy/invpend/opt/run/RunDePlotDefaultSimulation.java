package org.fuzzy.invpend.opt.run;

import java.awt.Color;

import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.prob.InvPendFuzzyContParamOpt;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.SystemPair;

public class RunDePlotDefaultSimulation {

	public static void main(String[] args) {
	    /*
	     * Plot outputs
	     */
	    SystemPair[] systemPairs = new SystemPair[1];
		systemPairs[0] = new SystemPair();
		systemPairs[0].caption = "Interpretable";
		systemPairs[0].color = Color.RED;
		systemPairs[0].pend = Simulator.generateNewPendulum();
		systemPairs[0].cont = new FuzzyControllerOpt(InvPendFuzzyContParamOpt.defaultVariables);
//		systemPairs[0].cont = new FuzzyControllerTriangular(null);

		Simulator.simulate(systemPairs, true);	//	plotLen %
	}

}
