package org.fuzzy.invpend.opt.run;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.Dictionary;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.ControlSystem;

public class RunDePlotDefaultSimulation {

//	private static Logger logger = LogManager.getLogger(RunDePlotDefaultSimulation.class);

	public static void main(String[] args) {

		System.setProperty("fuzzyOptLogFileName", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

		/*
	     * Plot outputs
	     */
	    ControlSystem[] controlSystems = new ControlSystem[1];
		controlSystems[0] = new ControlSystem("Interpretable",
												Color.RED,
												Dictionary.defaultCont,
												new InvertedPendulum());

		Simulator.simulate(controlSystems, true, "Sim");	//	plotLen %
		System.out.println("RMSE: " + controlSystems[0].getRmseT());

		FuzzyInvPendController.reportSimilarity("DICTIONARY", "DICTIONARY", Dictionary.defaultCont, (FuzzyControllerOpt) controlSystems[0].getCont());
	}
}
