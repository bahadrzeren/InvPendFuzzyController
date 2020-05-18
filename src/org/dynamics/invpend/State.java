package org.dynamics.invpend;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Stores state of a pendulum at a particular iteration and generates new states for next iterations using Runge-Kutta.
 * 
 * @author bahadr
 *
 */

public class State {
	private double x = 0.0;
	private double xd = 0.0;
	private double t = 0.0;
	private double td = 0.0;

	public State(double x, double xd, double t, double td) {
		super();
		this.x = x;
		this.xd = xd;
		this.t = t;
		this.td = td;
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getXd() {
		return xd;
	}
	public void setXd(double xd) {
		this.xd = xd;
	}
	public double getT() {
		return t;
	}
	public void setT(double t) {
		this.t = t;
	}
	public double getTd() {
		return td;
	}
	public void setTd(double td) {
		this.td = td;
	}

	public void reset(double x, double xd, double t, double td) {
		this.x = x;
		this.xd = xd;
		this.t = t;
		this.td = td;
	}

	public State getCopy() {
		return new State(this.x, this.xd, this.t, this.td);
	}

	public double calculateSinT() {
		return Math.sin(this.t);
	}

	public double calculateCosT() {
		return Math.cos(this.t);
	}

	public double calculateSqrTd() {
		return this.td * this.td;
	}

	public double calculateSqrSinT() {
		return Math.sin(this.t) * Math.sin(this.t);
	}

	public double calculateSqrCosT() {
		return Math.cos(this.t) * Math.cos(this.t);
	}

	public State rungeKutta4Iterate(StateDot sd, double c) {
		return new State(this.x + sd.getXd() * c,
							this.xd + sd.geXdd() * c,
							this.t + sd.getTd() * c,
							this.td + sd.getTdd() * c);
	}

	public void rungeKutta4merge(StateDot k1,
									StateDot k2,
									StateDot k3,
									StateDot k4,
									double step) {
		this.x += (k1.getXd() + k2.getXd() * 2.0 + k3.getXd() * 2.0 + k4.getXd()) * (step / 6.0); 
		this.xd += (k1.geXdd() + k2.geXdd() * 2.0 + k3.geXdd() * 2.0 + k4.geXdd()) * (step / 6.0);
		this.t += (k1.getTd() + k2.getTd() * 2.0 + k3.getTd() * 2.0 + k4.getTd()) * (step / 6.0);
		this.td += (k1.getTdd() + k2.getTdd() * 2.0 + k3.getTdd() * 2.0 + k4.getTdd()) * (step / 6.0);
	}

	private NumberFormat formatter = new DecimalFormat("#0.0000");

	public String toString() {
		return "x: " + this.formatter.format(x) +
				", t: " + this.formatter.format(t * 180.0 / Math.PI) + 
				", xd: " + this.formatter.format(xd) +
				", td: " + this.formatter.format(td * 180.0 / Math.PI);
	}
}
