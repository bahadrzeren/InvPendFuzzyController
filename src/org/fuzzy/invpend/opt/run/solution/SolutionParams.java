package org.fuzzy.invpend.opt.run.solution;

public class SolutionParams {

	private double objective = 0.0;
	private double jaccardDissimilarity = 0.0;
	private double rmseT = 0.0;
	private double rmseTd = 0.0;
	private double rmseF = 0.0;
	private double rmseX = 0.0;
	private double rmseXd = 0.0;

	private ControllerParams midContParams = null;
	private ControllerParams bestContParams = null;

	public SolutionParams(double objective, double jaccardDissimilarity, double rmseT, double rmseTd, double rmseF,
			double rmseX, double rmseXd, ControllerParams midContParams, ControllerParams bestContParams) {
		this.objective = objective;
		this.jaccardDissimilarity = jaccardDissimilarity;
		this.rmseT = rmseT;
		this.rmseTd = rmseTd;
		this.rmseF = rmseF;
		this.rmseX = rmseX;
		this.rmseXd = rmseXd;
		this.midContParams = midContParams;
		this.bestContParams = bestContParams;
	}

	public double getObjective() {
		return objective;
	}

	public double getJaccardDissimilarity() {
		return jaccardDissimilarity;
	}

	public double getRmseT() {
		return rmseT;
	}

	public double getRmseTd() {
		return rmseTd;
	}

	public double getRmseF() {
		return rmseF;
	}

	public double getRmseX() {
		return rmseX;
	}

	public double getRmseXd() {
		return rmseXd;
	}

	public ControllerParams getMidContParams() {
		return midContParams;
	}

	public ControllerParams getBestContParams() {
		return bestContParams;
	}
}
