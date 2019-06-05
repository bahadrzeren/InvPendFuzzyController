package org.fuzzy.invpend.cont;

public interface FuzzyController {
	public String getControllerName();
	public double getControlInput(double theta, double thetaD);
	public void plotMembershipFunctions();
	public void plotControlSurface();
}
