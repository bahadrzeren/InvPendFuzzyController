package org.fuzzy.invpend.opt.run;

import org.fuzzy.invpend.cont.FuzzyController;

public class OptOutput {
	private double[] rmseTs = null;
	private double[] normRmseTs = null;
	private double[] dissimilaritys = null;
	private double[] normDissimilaritys = null;
	private double[] objs = null;

	private double midRmseT = 0.0;
	private double midNormRmseT = 0.0;
	private double midDissimilarity = 0.0;
	private double midNormDissimilarity = 0.0;
	private double midObj = 0.0;
	private FuzzyController midController = null;

	private double bestRmseT = 0.0;
	private double bestNormRmseT = 0.0;
	private double bestDissimilarity = 0.0;
	private double bestNormDissimilarity = 0.0;
	private double bestObj = 0.0;
	private FuzzyController bestController = null;

	public OptOutput(double[] rmseTs, double[] normRmseTs, double[] dissimilaritys, double[] normDissimilaritys, double[] objs, 
						double midRmseT, double midNormRmseT, double midDissimilarity, double midNormDissimilarity, double midObj, FuzzyController midController,
						double bestRmseT, double bestNormRmseT, double bestDissimilarity, double bestNormDissimilarity, double bestObj, FuzzyController bestController) {
		super();
		this.rmseTs = rmseTs;
		this.normRmseTs = normRmseTs;
		this.dissimilaritys = dissimilaritys;
		this.normDissimilaritys = normDissimilaritys;
		this.objs = objs;
		this.midRmseT = midRmseT;
		this.midNormRmseT = midNormRmseT;
		this.midDissimilarity = midDissimilarity;
		this.midNormDissimilarity = midNormDissimilarity;
		this.midObj = midObj;
		this.midController = midController;
		this.bestRmseT = bestRmseT;
		this.bestNormRmseT = bestNormRmseT;
		this.bestDissimilarity = bestDissimilarity;
		this.bestNormDissimilarity = bestNormDissimilarity;
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

	public double getMidNormRmseT() {
		return midNormRmseT;
	}

	public double getMidNormDissimilarity() {
		return midNormDissimilarity;
	}

	public double getBestNormRmseT() {
		return bestNormRmseT;
	}

	public double getBestNormDissimilarity() {
		return bestNormDissimilarity;
	}

	public FuzzyController getBestController() {
		return bestController;
	}
}
