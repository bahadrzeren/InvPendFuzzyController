package org.fuzzy.invpend.intrpr.run;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import org.fuzzy.invpend.intrpr.cont.FuzzyControllerGaussian;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerNormalizedDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerStandardDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerTriangular;
import org.fuzzy.invpend.sim.Simulator;
import org.fuzzy.invpend.sim.ControlSystem;

public class RunPlotMemberships {

	public static NumberFormat formatter1 = new DecimalFormat("#0.00");
	public static NumberFormat formatter4 = new DecimalFormat("#0.0000");

	public static void main(String[] args) {

		ControlSystem[] systemPairs = new ControlSystem[0];

		//	Triangular reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new ControlSystem("Triangular Reference (T)",
																Color.RED,
																new FuzzyControllerTriangular(),
																Simulator.generateNewPendulum());
		//	Gaussian reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new ControlSystem("Gaussian Reference (G)",
																Color.BLUE,
																new FuzzyControllerGaussian(),
																Simulator.generateNewPendulum());
		//	Standard dictionary reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new ControlSystem("Standard Fuzzy Transfer (S)",
																new Color(60, 180, 100),
																new FuzzyControllerStandardDict(),
																Simulator.generateNewPendulum());
		//	Normalized dictionary reference
		systemPairs = Arrays.copyOf(systemPairs, systemPairs.length + 1);
		systemPairs[systemPairs.length - 1] = new ControlSystem("Normalised Standard Fuzzy Transfer (SN)",
																new Color(128, 64, 64),
																new FuzzyControllerNormalizedDict(),
																Simulator.generateNewPendulum());

		systemPairs[1].getCont().plotMembershipFunctions(false);
		systemPairs[1].getCont().plotControlSurface();

////		for (int i = 0; i < systemPairs.length; i++) {
////			for (int ts = -40; ts < 41; ts++) {
////				for (int ds = -8; ds < 9; ds++) {
////					double output = systemPairs[i].cont.getControlInput(ts, ds);
////					System.out.println("Cont" + i +
////										" > Theta: " + formatter1.format(ts) + 
////										", ThetaD: " + formatter1.format(ds) + 
////										" -> Centroid defuzzification: " + formatter4.format(output));
////				}
////			}
////			System.out.println();
////		}
//
//		Simulator.simulate(systemPairs, true);
	}
}
