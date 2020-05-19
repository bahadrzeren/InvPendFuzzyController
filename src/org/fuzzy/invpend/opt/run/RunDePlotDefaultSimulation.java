package org.fuzzy.invpend.opt.run;

import java.awt.Color;

import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.ControlSystem;

public class RunDePlotDefaultSimulation {

	public static void main(String[] args) {
	    /*
	     * Plot outputs
	     */
	    ControlSystem[] controlSystems = new ControlSystem[1];
		controlSystems[0] = new ControlSystem("Interpretable",
												Color.RED,
												Dictionary.defaultCont,
												Simulator.generateNewPendulum());

		Simulator.simulate(controlSystems, true);	//	plotLen %
		System.out.println("RMSE: " + controlSystems[0].getRmseT());

		FuzzyInvPendController.reportSimilarity(Dictionary.defaultCont, (FuzzyControllerOpt) controlSystems[0].getCont());
	}
}
