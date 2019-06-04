package org.sim;
import java.awt.Color;

import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.cont.FuzzyInvPendController;

public class SystemPair {
	public Color color = Color.black;
	public String caption = null;
	public InvertedPendulum pend = null;
	public FuzzyInvPendController cont = null;

	public double rmseT = 0.0;
	public double rmseTd = 0.0;
	public double rmseF = 0.0;
	public double rmseX = 0.0;
	public double rmseXd = 0.0;
}
