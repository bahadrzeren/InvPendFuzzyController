package org.fuzzy.invpend.sim;

import java.awt.Color;
import java.util.List;

import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.invpend.cont.FuzzyController;

/**
 * Instances of this class represents a full simulation set.
 * Has a pendulum, controller, color, caption and other statistical error values. 
 *
 * @author bahadr
 *
 */
public class ControlSystem {
	private String caption = null;
	private Color color = Color.red;
	private FuzzyController cont = null;
	private InvertedPendulum pend = null;

	private double rmseT = 0.0;
	private double rmseTd = 0.0;
	private double rmseF = 0.0;
	private double rmseX = 0.0;
	private double rmseXd = 0.0;

	private List<Double> variables = null;
	private double objective = 0.0;
	private double dissimilarity = 0.0;

	public ControlSystem(String caption,
							Color color,
							FuzzyController cont,
							InvertedPendulum pend) {
		this.caption = caption;
		this.color = color;
		this.cont = cont;
		this.pend = pend;
	}

	public double getRmseT() {
		return rmseT;
	}

	public void setRmseT(double rmseT) {
		this.rmseT = rmseT;
	}

	public double getRmseTd() {
		return rmseTd;
	}

	public void setRmseTd(double rmseTd) {
		this.rmseTd = rmseTd;
	}

	public double getRmseF() {
		return rmseF;
	}

	public void setRmseF(double rmseF) {
		this.rmseF = rmseF;
	}

	public double getRmseX() {
		return rmseX;
	}

	public void setRmseX(double rmseX) {
		this.rmseX = rmseX;
	}

	public double getRmseXd() {
		return rmseXd;
	}

	public void setRmseXd(double rmseXd) {
		this.rmseXd = rmseXd;
	}

	public double getObjective() {
		return objective;
	}

	public void setObjective(double objective) {
		this.objective = objective;
	}

	public double getDissimilarity() {
		return dissimilarity;
	}

	public void setDissimilarity(double dissimilarity) {
		this.dissimilarity = dissimilarity;
	}

	public String getCaption() {
		return caption;
	}

	public Color getColor() {
		return color;
	}

	public FuzzyController getCont() {
		return cont;
	}

	public InvertedPendulum getPend() {
		return pend;
	}

	public void proceed(double time,
						double appStart,
						double appEnd,
						double disturbance,
						double step) {
		double force = 0.0;
		force = this.cont.calculateControlInput(this.pend.getS().getT(), this.pend.getS().getTd());

		force = Math.floor(force * 10000.0) / 10000.0;
//		State prevState = this.pend.getS().getCopy();
		if ((time >= appStart)
				&& (time < appEnd)) {
			this.pend.move(step, force, disturbance);
//			System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState +
//					"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + " + disturbance + " -> " + this.pend);

		} else {
			this.pend.move(step, force, 0.0);
//			System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState + 
//					"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + 0.0 -> " + this.pend);
		}
	}
}
