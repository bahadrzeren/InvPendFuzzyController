package org.fuzzy.invpend.opt.run;

public class BestOptOutput {
	private double objCoefRmseT = 0.0;
	private double objCoefDissim = 0.0;
	private OptOutput optOutput = null;

	public BestOptOutput(double objCoefRmseT, double objCoefDissim, OptOutput optOutput) {
		this.objCoefRmseT = objCoefRmseT;
		this.objCoefDissim = objCoefDissim;
		this.optOutput = optOutput;
	}

	public double getObjCoefRmseT() {
		return objCoefRmseT;
	}

	public double getObjCoefDissim() {
		return objCoefDissim;
	}

	public OptOutput getOptOutput() {
		return optOutput;
	}
}
