package org.dynamics.invpend;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class State {
	private double x = 0.0;
	private double v = 0.0;
	private double t = 0.0;
	private double td = 0.0;

	public State(double x, double v, double t, double td) {
		super();
		this.x = x;
		this.v = v;
		this.t = t;
		this.td = td;
	}

	public State getCopy() {
		return new State(this.x, this.v, this.t, this.td);
	}

	public double sinT() {
		return Math.sin(this.t);
	}

	public double cosT() {
		return Math.cos(this.t);
	}

	public double sqrTd() {
		return this.td * this.td;
	}

	public double sqrSinT() {
		return Math.sin(this.t) * Math.sin(this.t);
	}

	public State iterate(StateDot sd, double c) {
		return new State(this.x + sd.getV() * c,
							this.v + sd.getA() * c,
							this.t + sd.getTd() * c,
							this.td + sd.getTdd() * c);
	}

	public void rungeKutta4merge(StateDot k1,
									StateDot k2,
									StateDot k3,
									StateDot k4,
									double step) {
		this.x += (k1.getV() + k2.getV() * 2.0 + k3.getV() * 2.0 + k4.getV()) * (step / 6.0); 
		this.v += (k1.getA() + k2.getA() * 2.0 + k3.getA() * 2.0 + k4.getA()) * (step / 6.0);
		this.t += (k1.getTd() + k2.getTd() * 2.0 + k3.getTd() * 2.0 + k4.getTd()) * (step / 6.0);
		this.td += (k1.getTdd() + k2.getTdd() * 2.0 + k3.getTdd() * 2.0 + k4.getTdd()) * (step / 6.0);
	}

	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getV() {
		return v;
	}
	public void setV(double v) {
		this.v = v;
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

	private NumberFormat formatter = new DecimalFormat("#0.0000");

	public String toString() {
		return "x: " + this.formatter.format(x) +
				", t: " + this.formatter.format(t) + 
				", v: " + this.formatter.format(v) +
				", td: " + this.formatter.format(td);
	}
}
