package org.dynamics.invpend;

/**
 * Class that represents pendulums.
 *
 * @author bahadr
 *
 */
public class InvertedPendulum {

	/*
	 * Pendulum properties.
	 */
	private double mp = 0.1;	//	kg
	private double mc = 1.0;	//	kg
	private double l = 1.0;		//	meter
	private double g = 9.8;		//	meter/sn2
	private double fcp = 0.0;	//	newton
	private double fcc = 0.0;
	/*
	 * Initial state.
	 */
	private double xInit = 0.0;
	private double xdInit = 0.0;
//	private double tInit = -30.0 * Math.PI / 180.0;
//	private double tdInit = 4.0 * Math.PI / 180.0;
//	public static double tInit = 5.0 * Math.PI / 180.0;
//	public static double tdInit = 3.0 * Math.PI / 180.0;
	public static double tInit = 5.0 * Math.PI / 180.0;
	public static double tdInit = 3.0 * Math.PI / 180.0;

	private State activeState = null;

	private State[] stateHistory = null;
	private double[] forceHistory = null;
	private double[] disturbanceHistory = null;

	public InvertedPendulum() {
		this.activeState = new State(xInit, xdInit, tInit, tdInit);
	}

	public void reset(int simulationLength) {
		this.stateHistory = new State[simulationLength];
		this.forceHistory = new double[simulationLength];
		this.disturbanceHistory = new double[simulationLength];

		this.activeState.reset(xInit, xdInit, tInit, tdInit);
	}

	public double getMp() {
		return mp;
	}

	public double getMc() {
		return mc;
	}

	public double getL() {
		return l;
	}

	public double getG() {
		return g;
	}

	public State getS() {
		return activeState;
	}

	private StateDot rungeKutta4Iterate(State s, double f) {

		StateDot res = new StateDot(s.getXd(), 0.0, s.getTd(), 0.0);
/*
 * https://github.com/gordwilling/runge-kutta
 * https://physics.stackexchange.com/questions/35000/elementary-derivation-of-the-motion-equations-for-an-inverted-pendulum-on-a-cart
 * https://betterexplained.com/articles/cross-product/
 * 
 */
		res.setXdd((
					mp * g * s.calculateSinT() * s.calculateCosT()
					+ mp * l * s.calculateSqrTd() * s.calculateSinT()
					+ f
					- fcc * s.getXd()
					+ (s.getTd() * s.calculateCosT() * fcp / l)
					) /
						(mc + mp + mp * s.calculateSqrSinT()));

		res.setTdd((
					- g * (mc + mp + mp) * s.calculateSinT() 
					+ s.calculateCosT() * (l * mp * s.calculateSqrTd() * s.calculateSinT() - f)
					+ fcc * s.getXd() * s.calculateCosT()
					- (1 + (mc + mp) / mp) * (fcp / l) * s.getTd()
					) /
						(l * (mc + mp + mp * s.calculateSqrSinT())));

//		res.setXdd((
//				f
//				+ mp * g * s.sinT() * s.cosT()
//				- mp * l * s.sqrTd() * s.sinT()
////				- fcc * s.getXd()
////				+ (s.getTd() * s.cosT() * fcp / l)
//				) /
//					(mc + mp + mp * s.sqrSinT()));
//
//		res.setTdd((
//				g * (mc + mp + mp) * s.sinT() 
//				- s.cosT() * (l * mp * s.sqrTd() * s.sinT() - f)
////				+ fcc * s.getXd() * s.cosT()
////				- (1 + (mc + mp) / mp) * (fcp / l) * s.getTd()
//				) /
//					(l * (mc + mp + mp * s.sqrSinT())));

/*
 * BECERIKLI
 */
//		res.setTdd((g * s.sinT()
//					- mp * l * s.sqrTd() * s.cosT() * s.sinT() / (mc + mp)
//					+ f * s.cosT() / (mc + mp)) /
//					(l * (4.0 / 3.0 - mp * s.sqrCosT() / (mc + mp))));
//
//		res.setXdd((f 
//					+ mp * l * s.sqrTd() * s.sinT()
//					- mp * l * res.getTdd() * s.cosT()
//					) /
//					(mc + mp));

/*
 * https://www.sciencedirect.com/science/article/pii/S0954181000000078
 */
//		res.setTdd(((mc + mp) * g * s.sinT() - (f + mp * l * s.sqrTd() * s.sinT()) * s.cosT()) /
//				(l * ((mc + mp) * 4.0 / 3.0 - mp * s.sqrCosT())));
//		res.setXdd(((f + mp * l * s.sqrTd() * s.sinT()) * 4.0 / 3.0 - mp * g * s.sinT() * s.cosT()) /
//					((mc + mp) * 4.0 / 3.0 - mp * s.sqrCosT()));

		return res;
	}

	public void move(int timeNdx, double step, double f, double d) {
		this.stateHistory[timeNdx] = this.activeState.getCopy();
		this.forceHistory[timeNdx] = f;
		this.disturbanceHistory[timeNdx] = d;

		StateDot k1 = this.rungeKutta4Iterate(this.activeState, f + d);
		StateDot k2 = this.rungeKutta4Iterate(this.activeState.rungeKutta4Iterate(k1, step / 2.0), f + d);
		StateDot k3 = this.rungeKutta4Iterate(this.activeState.rungeKutta4Iterate(k2, step / 2.0), f + d);
		StateDot k4 = this.rungeKutta4Iterate(this.activeState.rungeKutta4Iterate(k3, step), f + d);
		this.activeState.rungeKutta4merge(k1, k2, k3, k4, step);
	}

	public State[] getStateHistory() {
		return stateHistory;
	}

	public double[] getForceHistory() {
		return forceHistory;
	}

	public double[] getDisturbanceHistory() {
		return disturbanceHistory;
	}

	public String toString() {
		return "Pendulum properties:\r" +
				"mp = " + mp + "kg\r" +
				"mc = " + mc + "kg\r" +
				"l = " + l + "meter\r" +
				"g = " + g + "meter/sn2\r" +
				"fcp = " + fcp + "newton\r" +
				"fcc = " + fcc + "newton\r" +
				"Initial state:\r" +
				"xInit = " + xInit + "\r" +
				"xdInit = " + xdInit + "\r" + 
				"tInit = " + tInit + " rad\r" + 
				"tdInit = " + tdInit + " rad\r" + 
				this.activeState.toString();
	}
}
