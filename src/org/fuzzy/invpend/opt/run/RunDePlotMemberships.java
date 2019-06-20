package org.fuzzy.invpend.opt.run;

import org.fuzzy.Dictionary;

public class RunDePlotMemberships {

	public static void main(String[] args) {
		Dictionary.defaultCont.plotMembershipFunctions(false);
		Dictionary.defaultCont.plotControlSurface();
	}

}
