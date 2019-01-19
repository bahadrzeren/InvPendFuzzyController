package org.diffeq;

public class InvertedPendulum {

	private double mp = 0.1;	//	kg
	private double mc = 1.0;	//	kg
	private double l = 2.0;		//	meter
	private double g = 9.8;		//	meter/sn2

	private State s = null;

	public InvertedPendulum(double mp, double mc, double l, double g, State s) {
		super();
		this.mp = mp;
		this.mc = mc;
		this.l = l;
		this.g = g;
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

		StateDot res = new StateDot(s.getV(), 0.0, s.getTd(), 0.0);

		res.setA((
					f + 
					mp * g * s.cosT() * s.sinT() - 
					mp * l * s.sqrTd() * s.sinT()
					) /
						(mc + mp * s.sqrSinT()));

		res.setTdd((
					g * (mc + mp) * s.sinT() - 
					s.cosT() * (l * mp * s.sqrTd() * s.sinT() - f)
					) /
						(l * (mp * s.sqrSinT() + mc)));

		return res;
	}

	public void move(double step, double f) {
		StateDot k1 = this.iterate(s, f);
		StateDot k2 = this.iterate(s.iterate(k1, step / 2.0), f);
		StateDot k3 = this.iterate(s.iterate(k2, step / 2.0), f);
		StateDot k4 = this.iterate(s.iterate(k3, step), f);
		this.s.rungeKutta4merge(k1, k2, k3, k4, step);
	}

	public String toString() {
		return "x: " + s.getX() +
				", t: " + s.getT() + 
				", v: " + s.getV() +
				", td: " + s.getTd();
	}
}
