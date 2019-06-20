package org.fuzzy.invpend.cont;

public interface FuzzyController {
	public String getControllerName();
	public double getControlInput(double theta, double thetaD);
	public void plotMembershipFunctions(boolean updateNames);
	public void plotControlSurface();
}
