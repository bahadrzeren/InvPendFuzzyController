package org.fuzzy.invpend.intrpr.run;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerGaussian;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerNormalizedDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerStandardDict;
import org.fuzzy.invpend.intrpr.cont.FuzzyControllerTriangular;
import org.fuzzy.invpend.sim.ControlSystem;

public class RunPlotMemberships {

	public static NumberFormat formatter1 = new DecimalFormat("#0.00");
	public static NumberFormat formatter4 = new DecimalFormat("#0.0000");

	public static void main(String[] args) {

		ControlSystem[] controlSystems = new ControlSystem[0];

		//	Triangular reference
		controlSystems = Arrays.copyOf(controlSystems, controlSystems.length + 1);
		controlSystems[controlSystems.length - 1] = new ControlSystem("Triangular Reference (T)",
																Color.RED,
																new FuzzyControllerTriangular(),
																new InvertedPendulum());
		//	Gaussian reference
		controlSystems = Arrays.copyOf(controlSystems, controlSystems.length + 1);
		controlSystems[controlSystems.length - 1] = new ControlSystem("Gaussian Reference (G)",
																Color.BLUE,
																new FuzzyControllerGaussian(),
																new InvertedPendulum());
		//	Standard dictionary reference
		controlSystems = Arrays.copyOf(controlSystems, controlSystems.length + 1);
		controlSystems[controlSystems.length - 1] = new ControlSystem("Standard Fuzzy Transfer (S)",
																new Color(60, 180, 100),
																new FuzzyControllerStandardDict(),
																new InvertedPendulum());
		//	Normalized dictionary reference
		controlSystems = Arrays.copyOf(controlSystems, controlSystems.length + 1);
		controlSystems[controlSystems.length - 1] = new ControlSystem("Normalised Standard Fuzzy Transfer (SN)",
																new Color(128, 64, 64),
																new FuzzyControllerNormalizedDict(),
																new InvertedPendulum());

		controlSystems[1].getCont().plotMembershipFunctions("", false);
		controlSystems[1].getCont().plotControlSurface("");

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
