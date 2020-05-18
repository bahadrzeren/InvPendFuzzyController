package org.dynamics.invpend;

/**
 * First derivative of the State class.
 * 
 * @author bahadr
 *
 */

public class StateDot {
	private double xd = 0.0;
	private double xdd = 0.0;
	private double td = 0.0;
	private double tdd = 0.0;

	public StateDot(double xd, double xdd, double td, double tdd) {
		this.xd = xd;
		this.xdd = xdd;
		this.td = td;
		this.tdd = tdd;
	}

	public double getXd() {
		return xd;
	}
	public void setXd(double xd) {
		this.xd = xd;
	}
	public double geXdd() {
		return xdd;
	}
	public void setXdd(double xdd) {
		this.xdd = xdd;
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
