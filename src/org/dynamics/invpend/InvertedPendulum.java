package org.dynamics.invpend;

import java.util.Arrays;

public class InvertedPendulum {

	private double mp = 0.1;	//	kg
	private double mc = 1.0;	//	kg
	private double l = 2.0;		//	meter
	private double g = 9.8;		//	meter/sn2
	private double fcp = 0.1;	//	newton
	private double fcc = 0.5;

	private State s = null;
	private State[] history = new State[0];

	public InvertedPendulum(double mp, double mc, double l, double g, double fcp, double fcc, State s) {
		super();
		this.mp = mp;
		this.mc = mc;
		this.l = l;
		this.g = g;
		this.fcp = fcp;
		this.fcc = fcc;
		this.s = s;
		this.history = Arrays.copyOf(this.history, this.history.length + 1);
		this.history[this.history.length - 1] = s.getCopy();
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

		StateDot res = new StateDot(s.getV(), 0.0, s.getTd(), 0.0);

		res.setA((
					mp * g * s.cosT() * s.sinT()
					+ mp * l * s.sqrTd() * s.sinT()
					+ f
					+ (s.getTd() * s.cosT() * fcp / l)
					- fcc * s.getV()
					) /
						(mc + mp + mp * s.sqrSinT()));

		res.setTdd((
					s.cosT() * l * mp * s.sqrTd() * s.sinT()
					- g * (mc + mp) * s.sinT() 
					- s.cosT() * f
					+ fcc * s.getV() * s.cosT()
					- (1 + mc / mp) * (fcp / l) * s.getTd()
					) /
						(l * (mc + mp + mp * s.sqrSinT())));

		return res;
	}

	public void move(double step, double f) {
		StateDot k1 = this.iterate(s, f);
		StateDot k2 = this.iterate(s.iterate(k1, step / 2.0), f);
		StateDot k3 = this.iterate(s.iterate(k2, step / 2.0), f);
		StateDot k4 = this.iterate(s.iterate(k3, step), f);
		this.s.rungeKutta4merge(k1, k2, k3, k4, step);
		this.history = Arrays.copyOf(this.history, this.history.length + 1);
		this.history[this.history.length - 1] = s.getCopy();
	}

	public State[] getHistory() {
		return history;
	}

	public String toString() {
		return this.s.toString();
	}
}
