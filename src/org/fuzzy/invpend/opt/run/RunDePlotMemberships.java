package org.fuzzy.invpend.opt.run;

import org.fuzzy.invpend.cont.FuzzyController;
import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.prob.InvPendFuzzyContParamOpt;

public class RunDePlotMemberships {

	public static void main(String[] args) {
		FuzzyController cont = new FuzzyControllerOpt(InvPendFuzzyContParamOpt.defaultVariables);
//		FuzzyController cont = new FuzzyControllerTriangular(null);
		cont.plotMembershipFunctions();
		cont.plotControlSurface();
	}

}
