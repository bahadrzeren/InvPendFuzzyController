package org.fuzzy.invpend.sim;
import java.awt.Color;

import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.invpend.cont.FuzzyController;

public class SystemPair {
	public Color color = Color.red;
	public String caption = null;
	public InvertedPendulum pend = null;
	public FuzzyController cont = null;

	public double rmseT = 0.0;
	public double rmseTd = 0.0;
	public double rmseF = 0.0;
	public double rmseX = 0.0;
	public double rmseXd = 0.0;

	public double objective = 0.0;
	public double dissimilarity = 0.0;
}
