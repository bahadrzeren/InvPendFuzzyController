package org.fuzzy.invpend.sim;

import java.awt.Color;

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

//	public static NumberFormat formatter1 = new DecimalFormat("#0.00");
//	public static NumberFormat formatter4 = new DecimalFormat("#0.0000");

	private String caption = null;
	private Color color = Color.red;
	private FuzzyController cont = null;
	private InvertedPendulum pend = null;

	private double rmseT = 0.0;
	private double rmseTd = 0.0;
	private double rmseF = 0.0;
	private double rmseX = 0.0;
	private double rmseXd = 0.0;

//	private List<Double> variables = null;
//	private double objective = 0.0;
//	private double dissimilarity = 0.0;

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

//	public double getObjective() {
//		return objective;
//	}
//
//	public void setObjective(double objective) {
//		this.objective = objective;
//	}
//
//	public double getDissimilarity() {
//		return dissimilarity;
//	}
//
//	public void setDissimilarity(double dissimilarity) {
//		this.dissimilarity = dissimilarity;
//	}

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

	public void setCont(FuzzyController controller) {
		this.cont = controller;
	}

	public void runSimulation(double[] times,
								double appStart,
								double appEnd,
								double disturbance,
								double step) {
		this.rmseT = 0.0;
		this.rmseTd = 0.0;
		this.rmseX = 0.0;
		this.rmseXd = 0.0;
		this.rmseF = 0.0;

		this.pend.reset(times.length);

		for (int i = 0; i < times.length; i++) {
			if ((times[i] >= appStart)
					&& (times[i] < appEnd)) {
				this.proceedToNextStep(i, step, disturbance);
			} else {
				this.proceedToNextStep(i, step, 0.0);
			}
		}

    	this.rmseT = Math.sqrt(this.rmseT / times.length);
    	this.rmseTd = Math.sqrt(this.rmseTd / times.length);
    	this.rmseX = Math.sqrt(this.rmseX / times.length);
    	this.rmseXd = Math.sqrt(this.rmseXd / times.length);
    	this.rmseF = Math.sqrt(this.rmseF / times.length);
	}

	private void proceedToNextStep(int timeNdx,
									double step,
									double disturbance) {

		double force = this.cont.calculateControlInput(this.pend.getS().getT(), this.pend.getS().getTd());

		force = Math.floor(force * 10000.0) / 10000.0;

		this.pend.move(timeNdx, step, force, disturbance);

		this.rmseT += Math.pow(this.pend.getStateHistory()[timeNdx].getT() * 180.0 / Math.PI, 2);
		this.rmseTd += Math.pow(this.pend.getStateHistory()[timeNdx].getTd() * 180.0 / Math.PI, 2);
		this.rmseX += Math.pow(this.pend.getStateHistory()[timeNdx].getX(), 2);
		this.rmseXd += Math.pow(this.pend.getStateHistory()[timeNdx].getXd(), 2);
		this.rmseF += Math.pow(this.pend.getForceHistory()[timeNdx], 2);
	}
}
