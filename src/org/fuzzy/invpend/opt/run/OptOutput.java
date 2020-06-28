package org.fuzzy.invpend.opt.run;

import org.fuzzy.invpend.cont.FuzzyController;

public class OptOutput {
	private double midDissimilarity = 0.0;
	private double midRmseT = 0.0;
	private double midObj = 0.0;
	private FuzzyController midController = null;

	private double bestDissimilarity = 0.0;
	private double bestRmseT = 0.0;
	private double bestObj = 0.0;
	private FuzzyController bestController = null;

	public OptOutput(double midDissimilarity, double midRmseT, double midObj, FuzzyController midController,
						double bestDissimilarity, double bestRmseT, double bestObj, FuzzyController bestController) {
		super();
		this.midDissimilarity = midDissimilarity;
		this.midRmseT = midRmseT;
		this.midObj = midObj;
		this.midController = midController;
		this.bestDissimilarity = bestDissimilarity;
		this.bestRmseT = bestRmseT;
		this.bestObj = bestObj;
		this.bestController = bestController;
	}

	public double getMidObj() {
		return midObj;
	}

	public double getMidDissimilarity() {
		return midDissimilarity;
	}

	public double getMidRmseT() {
		return midRmseT;
	}

	public FuzzyController getMidController() {
		return midController;
	}

	public double getBestDissimilarity() {
		return bestDissimilarity;
	}

	public double getBestRmseT() {
		return bestRmseT;
	}

	public double getBestObj() {
		return bestObj;
	}

	public FuzzyController getBestController() {
		return bestController;
	}
}
