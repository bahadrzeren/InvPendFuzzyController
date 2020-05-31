package org.fuzzy.invpend.opt.run.solution;

public class MembershipParams {
	private String functionName = null;
	private double jaccardDissimilarity = 0.0;
	private double mean = 0.0;
	private double spread = 0.0;

	public MembershipParams(String functionName, double jaccardDissimilarity, double mean, double spread) {
		this.functionName = functionName;
		this.jaccardDissimilarity = jaccardDissimilarity;
		this.mean = mean;
		this.spread = spread;
	}

	public String getFunctionName() {
		return functionName;
	}

	public double getJaccardDissimilarity() {
		return jaccardDissimilarity;
	}

	public double getMean() {
		return mean;
	}

	public double getSpread() {
		return spread;
	}

}
