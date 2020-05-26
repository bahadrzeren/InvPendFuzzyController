package org.fuzzy.invpend.cont;

public interface FuzzyController {
	public String getControllerName();
	public double calculateControlInput(double theta, double thetaD);
	public void plotMembershipFunctions(String filePrefix, boolean updateNames);
	public void plotControlSurface(String filePrefix);
}
