package org.fuzzy.invpend.opt.run;

import java.awt.Color;

import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;
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
		systemPairs[0].cont = Dictionary.defaultCont;

		Simulator.simulate(systemPairs, true);	//	plotLen %
		System.out.println("RMSE: " + systemPairs[0].rmseT);
		FuzzyInvPendController.reportSimilarity(Dictionary.defaultCont, (FuzzyControllerOpt) systemPairs[0].cont);
	}
}
