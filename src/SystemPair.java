import java.awt.Color;

import org.dynamics.invpend.InvertedPendulum;
import org.fuzzy.cont.FuzzyInvPendController;

public class SystemPair {
	public Color color = Color.black;
	public String caption = null;
	public InvertedPendulum pend = null;
	public FuzzyInvPendController cont = null;
}
