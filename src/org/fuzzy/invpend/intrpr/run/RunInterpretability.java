package org.fuzzy.invpend.intrpr.run;

import java.awt.Color;

import org.fuzzy.invpend.intrpr.cont.FuzzyControllerGaussian;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerNormalizedDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerStandardDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerTriangular;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.ControlSystem;

public class RunInterpretability {
	public static void main(String[] args) {

		ControlSystem[] controlSystems = new ControlSystem[4];

		//	Triangular reference
		controlSystems[0] = new ControlSystem("Triangular Reference (T)",
												Color.RED,
												new FuzzyControllerTriangular(),
												Simulator.generateNewPendulum());
		//	Gaussian reference
		controlSystems[1] = new ControlSystem("Gaussian Reference (G)",
												Color.BLUE,
												new FuzzyControllerGaussian(),
												Simulator.generateNewPendulum());
		//	Standard dictionary reference
		controlSystems[2] = new ControlSystem("Standard Fuzzy Transfer (S)",
												new Color(60, 180, 100),
												new FuzzyControllerStandardDict(),
												Simulator.generateNewPendulum());
		//	Normalized dictionary reference
		controlSystems[3] = new ControlSystem("Normalised Standard Fuzzy Transfer (SN)",
												new Color(128, 64, 64),
												new FuzzyControllerNormalizedDict(),
												Simulator.generateNewPendulum());

		Simulator.simulate(controlSystems, true);	//	plotLen %
	}
}
