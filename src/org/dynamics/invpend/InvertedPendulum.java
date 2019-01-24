package org.dynamics.invpend;

import java.util.Arrays;

public class InvertedPendulum {

	private double mp = 0.1;	//	kg
	private double mc = 1.0;	//	kg
	private double l = 2.0;		//	meter
	private double g = 9.8;		//	meter/sn2
	private double fcp = 0.0;	//	newton
	private double fcc = 0.0;

	private State s = null;

	private State[] stateHistory = new State[0];
	private double[] forceHistory = new double[0];
	private double[] disturbanceHistory = new double[0];

	public InvertedPendulum(double mp, double mc, double l, double g, double fcp, double fcc, State s) {
		super();
		this.mp = mp;
		this.mc = mc;
		this.l = l;
		this.g = g;
		this.fcp = fcp;
		this.fcc = fcc;
		this.s = s;
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
		return s;
	}

	private StateDot iterate(State s, double f) {

		StateDot res = new StateDot(s.getXd(), 0.0, s.getTd(), 0.0);
//	https://github.com/gordwilling/runge-kutta
		res.setXdd((
					mp * g * s.cosT() * s.sinT()
					+ mp * l * s.sqrTd() * s.sinT()
					+ f
					+ (s.getTd() * s.cosT() * fcp / l)
					- fcc * s.getXd()
					) /
						(mc + mp + mp * s.sqrSinT()));

		res.setTdd((
					s.cosT() * l * mp * s.sqrTd() * s.sinT()
					- g * (mc + mp + mp) * s.sinT() 
					- s.cosT() * f
					+ fcc * s.getXd() * s.cosT()
					- (1 + (mc + mp) / mp) * (fcp / l) * s.getTd()
					) /
						(l * (mc + mp + mp * s.sqrSinT())));

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
//		res.setXdd(((f + mp * l * s.sqrTd() * s.sinT()) * 4.0 / 3.0 - mp * g * s.sinT() * s.cosT()) /
//					((mc + mp) * 4.0 / 3.0 - mp * s.sqrCosT()));
//		res.setTdd(((mc + mp) * g * s.sinT() - (f + mp * l * s.sqrTd() * s.sinT()) * s.cosT()) /
//						(l * ((mc + mp) * 4.0 / 3.0 - mp * s.sqrCosT())));

		return res;
	}

	public void move(double step, double f, double d) {
		this.stateHistory = Arrays.copyOf(this.stateHistory, this.stateHistory.length + 1);
		this.stateHistory[this.stateHistory.length - 1] = s.getCopy();
		this.forceHistory = Arrays.copyOf(this.forceHistory, this.forceHistory.length + 1);
		this.forceHistory[this.forceHistory.length - 1] = f;
		this.disturbanceHistory = Arrays.copyOf(this.disturbanceHistory, this.disturbanceHistory.length + 1);
		this.disturbanceHistory[this.disturbanceHistory.length - 1] = d;

		StateDot k1 = this.iterate(s, f + d);
		StateDot k2 = this.iterate(s.iterate(k1, step / 2.0), f + d);
		StateDot k3 = this.iterate(s.iterate(k2, step / 2.0), f + d);
		StateDot k4 = this.iterate(s.iterate(k3, step), f + d);
		this.s.rungeKutta4merge(k1, k2, k3, k4, step);
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
		return this.s.toString();
	}
}
