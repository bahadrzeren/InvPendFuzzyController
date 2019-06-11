package org.fuzzy.invpend.intrpr.run;

import java.awt.Color;
import java.util.Arrays;

import org.fuzzy.invpend.intrpr.cont.FuzzyControllerGaussian;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerNormalizedDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerStandardDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerTriangular;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.SystemPair;

public class RunInterpretability {
	public static void main(String[] args) {

		SystemPair[] systemPairs = new SystemPair[0];

		//	Triangular reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new SystemPair();
		systemPairs[systemPairs.length - 1].caption = "Triangular Reference (T)";
		systemPairs[systemPairs.length - 1].color = Color.RED;
		systemPairs[systemPairs.length - 1].cont = new FuzzyControllerTriangular();
		systemPairs[systemPairs.length - 1].pend = Simulator.generateNewPendulum();
		//	Gaussian reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new SystemPair();
		systemPairs[systemPairs.length - 1].color = Color.BLUE;
		systemPairs[systemPairs.length - 1].caption = "Gaussian Reference (G)";
		systemPairs[systemPairs.length - 1].cont = new FuzzyControllerGaussian();
		systemPairs[systemPairs.length - 1].pend = Simulator.generateNewPendulum();
		//	Standard dictionary reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new SystemPair();
		systemPairs[systemPairs.length - 1].color = new Color(60, 180, 100);
		systemPairs[systemPairs.length - 1].caption = "Standard Fuzzy Transfer (S)";
		systemPairs[systemPairs.length - 1].cont = new FuzzyControllerStandardDict();
		systemPairs[systemPairs.length - 1].pend = Simulator.generateNewPendulum();
		//	Normalized dictionary reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new SystemPair();
		systemPairs[systemPairs.length - 1].color = new Color(128, 64, 64);
		systemPairs[systemPairs.length - 1].caption = "Normalised Standard Fuzzy Transfer (SN)";
		systemPairs[systemPairs.length - 1].cont = new FuzzyControllerNormalizedDict();
		systemPairs[systemPairs.length - 1].pend = Simulator.generateNewPendulum();

		Simulator.simulate(systemPairs, 40);	//	plotLen %
	}
}
