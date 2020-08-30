package org.fuzzy.invpend.opt.run;

import org.fuzzy.invpend.cont.FuzzyController;

public class OptOutput {
	private double[] dissimilaritys = null;
	private double[] normDissimilaritys = null;
	private double[] rmseTs = null;
	private double[] normRmseTs = null;
	private double[] objs = null;

	private double midDissimilarity = 0.0;
	private double midRmseT = 0.0;
	private double midObj = 0.0;
	private FuzzyController midController = null;

	private double bestDissimilarity = 0.0;
	private double bestRmseT = 0.0;
	private double bestObj = 0.0;
	private FuzzyController bestController = null;

	public OptOutput(double[] dissimilaritys, double[] normDissimilaritys, double[] rmseTs, double[] normRmseTs, double[] objs, 
						double midDissimilarity, double midRmseT, double midObj, FuzzyController midController,
						double bestDissimilarity, double bestRmseT, double bestObj, FuzzyController bestController) {
		super();
		this.dissimilaritys = dissimilaritys;
		this.normDissimilaritys = normDissimilaritys;
		this.rmseTs = rmseTs;
		this.normRmseTs = normRmseTs;
		this.objs = objs;
		this.midDissimilarity = midDissimilarity;
		this.midRmseT = midRmseT;
		this.midObj = midObj;
		this.midController = midController;
		this.bestDissimilarity = bestDissimilarity;
		this.bestRmseT = bestRmseT;
		this.bestObj = bestObj;
		this.bestController = bestController;
	}

	public double[] getObjs() {
		return objs;
	}

	public double[] getDissimilaritys() {
		return dissimilaritys;
	}

	public double[] getNormDissimilaritys() {
		return normDissimilaritys;
	}

	public double[] getRmseTs() {
		return rmseTs;
	}

	public double[] getNormRmseTs() {
		return normRmseTs;
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
