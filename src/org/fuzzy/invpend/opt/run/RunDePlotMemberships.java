package org.fuzzy.invpend.opt.run;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.fuzzy.Dictionary;

public class RunDePlotMemberships {

//	private static Logger logger = LogManager.getLogger(RunDePlotMemberships.class);

	public static void main(String[] args) {

		System.setProperty("fuzzyOptLogFileName", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));

		Dictionary.defaultCont.plotMembershipFunctions("Fuzzy", false);
		Dictionary.defaultCont.plotControlSurface("Fuzzy");
	}

}
