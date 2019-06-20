package org.fuzzy;

import java.util.ArrayList;
import java.util.List;

import org.fuzzy.invpend.opt.cont.FuzzyControllerOpt;
import org.fuzzy.invpend.opt.cont.FuzzyInvPendController;

import similarity.JaccardSimilarityEngine;
import type1.sets.T1MF_Gaussian;

public class Dictionary {

	public static List<T1MF_Gaussian> defaultMFs = null;
	public static List<Double> dedaultVars = new ArrayList<Double>();
	public static FuzzyControllerOpt defaultCont = null;

	static {
	    /*
	     *	THETA
	     */
	    //	Tiny Center
		dedaultVars.add(0.15);
		//	Tiny Sigma
		dedaultVars.add(2.00546);

	    //	Some Center
		dedaultVars.add(3.31);
		//	Some Sigma
		dedaultVars.add(1.93929);

	    //	Medium Center
		dedaultVars.add(5.025);
		//	Medium Sigma
		dedaultVars.add(1.09813);

	    //	Good amount Center
		dedaultVars.add(6.46667);
		//	Good amount Sigma
		dedaultVars.add(1.74327);

	    //	Very large Center
		dedaultVars.add(9.95002);
		//	Very large Sigma
		dedaultVars.add(2.42824);

	    /*
	     *	THETA DELTA
	     */
	    //	Tiny Center
		dedaultVars.add(0.15);
		//	Tiny Sigma
		dedaultVars.add(2.00546);

	    //	Medium Center
		dedaultVars.add(5.025);
		//	Medium Sigma
		dedaultVars.add(1.09813);

	    //	Very large Center
		dedaultVars.add(9.95002);
		//	Very large Sigma
		dedaultVars.add(2.42824);

	    /*
	     *	FORCE
	     */
	    //	Tiny Center
		dedaultVars.add(0.15);
		//	Tiny Sigma
		dedaultVars.add(2.00546);

	    //	Small Center
		dedaultVars.add(1.7);
		//	Small Sigma
		dedaultVars.add(1.80021);

	    //	Some Center
		dedaultVars.add(3.31);
		//	Some Sigma
		dedaultVars.add(1.93929);

	    //	Medium Center
		dedaultVars.add(5.025);
		//	Medium Sigma
		dedaultVars.add(1.09813);

	    //	Good amount Center
		dedaultVars.add(6.46667);
		//	Good amount Sigma
		dedaultVars.add(1.74327);

	    //	Large Center
		dedaultVars.add(8.35001);
		//	Large Sigma
		dedaultVars.add(1.49622);

	    //	Very large Center
		dedaultVars.add(9.95002);
		//	Very large Sigma
		dedaultVars.add(2.42824);

		defaultCont = new FuzzyControllerOpt(dedaultVars);

		defaultMFs = new ArrayList<T1MF_Gaussian>();
		defaultMFs.add(new T1MF_Gaussian("None to very little", 0.075, 2.19258));
		defaultMFs.add(new T1MF_Gaussian("Tiny", 0.15, 2.00546));
		defaultMFs.add(new T1MF_Gaussian("A smidgen", 0.15, 2.0583));
		defaultMFs.add(new T1MF_Gaussian("Teeny-weeny", 0.15, 3.07764));
		defaultMFs.add(new T1MF_Gaussian("Very small", 0.2, 1.6623));
		defaultMFs.add(new T1MF_Gaussian("Very little", 0.35, 1.79764));
		defaultMFs.add(new T1MF_Gaussian("Little", 0.85, 2.31483));
		defaultMFs.add(new T1MF_Gaussian("A bit", 1.0, 2.324));
		defaultMFs.add(new T1MF_Gaussian("Low amount", 1.2, 1.93432));
		defaultMFs.add(new T1MF_Gaussian("Small", 1.7, 1.80021));
		defaultMFs.add(new T1MF_Gaussian("Somewhat small", 1.775, 1.83098));
		defaultMFs.add(new T1MF_Gaussian("Some", 3.31, 1.93929));
		defaultMFs.add(new T1MF_Gaussian("Quite a bit", 4.175, 1.80752));
		defaultMFs.add(new T1MF_Gaussian("Modest amount", 4.175, 1.81635));
		defaultMFs.add(new T1MF_Gaussian("Some to moderate", 4.7, 1.49706));
		defaultMFs.add(new T1MF_Gaussian("Medium", 5.025, 1.09813));
		defaultMFs.add(new T1MF_Gaussian("Moderate amount", 5.125, 1.3798));
		defaultMFs.add(new T1MF_Gaussian("Fair amount", 5.5, 1.62723));
		defaultMFs.add(new T1MF_Gaussian("Sizeable", 6.00001, 1.95962));
		defaultMFs.add(new T1MF_Gaussian("Good amount", 6.46667, 1.74327));
		defaultMFs.add(new T1MF_Gaussian("Considerable amount", 7.22501, 1.98231));
		defaultMFs.add(new T1MF_Gaussian("Substantial amount", 7.62501, 2.00667));
		defaultMFs.add(new T1MF_Gaussian("Very sizeable", 8.12501, 2.29374));
		defaultMFs.add(new T1MF_Gaussian("A lot", 8.30001, 1.95823));
		defaultMFs.add(new T1MF_Gaussian("Large", 8.35001, 1.49622));
		defaultMFs.add(new T1MF_Gaussian("High amount", 9.00002, 2.19888));
		defaultMFs.add(new T1MF_Gaussian("Huge amount", 9.85002, 2.24351));
		defaultMFs.add(new T1MF_Gaussian("Maximum amount", 9.90002, 0.907837));
		defaultMFs.add(new T1MF_Gaussian("Very high amount", 9.90002, 1.73834));
		defaultMFs.add(new T1MF_Gaussian("Extreme amount", 9.95002, 2.05745));
		defaultMFs.add(new T1MF_Gaussian("Humongous amount", 9.95002, 2.38972));
		defaultMFs.add(new T1MF_Gaussian("Very large", 9.95002, 2.42824));
	}

	private static JaccardSimilarityEngine jse = new JaccardSimilarityEngine(); 

	public static String getTheMostSimilarOnesName(T1MF_Gaussian func) {
		double bestSimilarity = 0.0;
		String bestFunctionName = null;

		for (int i = 0; i < defaultMFs.size(); i++) {
			double similarity = jse.getSimilarity(func, defaultMFs.get(i), FuzzyInvPendController.discritisationLevel); 
			if (bestSimilarity < similarity) {
				bestSimilarity = similarity;
				bestFunctionName = defaultMFs.get(i).getName();
			}
		}

		return bestFunctionName;
	}
}
