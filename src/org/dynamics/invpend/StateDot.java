package org.dynamics.invpend;

public class StateDot {
	private double v = 0.0;
	private double a = 0.0;
	private double td = 0.0;
	private double tdd = 0.0;

	public StateDot(double v, double a, double td, double tdd) {
		this.v = v;
		this.a = a;
		this.td = td;
		this.tdd = tdd;
	}

	public double getV() {
		return v;
	}
	public void setV(double v) {
		this.v = v;
	}
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getTd() {
		return td;
	}
	public void setTd(double td) {
		this.td = td;
	}
	public double getTdd() {
		return tdd;
	}
	public void setTdd(double tdd) {
		this.tdd = tdd;
	}
}
