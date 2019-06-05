package org.fuzzy.invpend.opt.cont;

import java.util.List;

import type1.sets.T1MF_Gaussian;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerOpt extends FuzzyInvPendController {

	public FuzzyControllerOpt(List<Double> variables) {
		super("Gaussian Dictionary Interpered Normalized Fuzzy Controller", variables);
	}

	@Override
	protected void initialize(List<Double> variables) {

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		this.tMfNMin = new T1MF_Gaussian("Theta Tiny", variables.get(0), variables.get(1));
		this.tMfN1 = new T1MF_Gaussian("Theta Some", variables.get(2), variables.get(3));
		this.tMf0 = new T1MF_Gaussian("Theta Medium", variables.get(4), variables.get(5));
		this.tMfP1 = new T1MF_Gaussian("Theta Good amount", variables.get(6), variables.get(7));
//		this.tMfP1 = new T1MF_Gaussian("Theta Considerable amount", variables.get(6), variables.get(7));
		this.tMfPMax = new T1MF_Gaussian("Theta Very large", variables.get(8), variables.get(9));

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		this.dMfNMin = new T1MF_Gaussian("ThetaD Tiny", variables.get(10), variables.get(11));
		this.dMf0 = new T1MF_Gaussian("ThetaD Medium", variables.get(12), variables.get(13));
		this.dMfPMax = new T1MF_Gaussian("ThetaD Very large", variables.get(14), variables.get(15));

		/*
		 * Membership functions of force (Output).
		 */
		this.fMfNMin = new T1MF_Gaussian("Force Tiny", variables.get(16), variables.get(17));
		this.fMfN2 = new T1MF_Gaussian("Force Small", variables.get(18), variables.get(19));
		this.fMfN1 = new T1MF_Gaussian("Force Some", variables.get(20), variables.get(21));
		this.fMf0 = new T1MF_Gaussian("Force Medium", variables.get(22), variables.get(23));
		this.fMfP1 = new T1MF_Gaussian("Force Good amount", variables.get(24), variables.get(25));
//		this.fMfP1 = new T1MF_Gaussian("Theta Considerable amount", variables.get(24), variables.get(25));
		this.fMfP2 = new T1MF_Gaussian("Force Large", variables.get(26), variables.get(27));
		this.fMfPMax = new T1MF_Gaussian("Force Very large", variables.get(28), variables.get(29));

//		/*
//		 * Membership functions of Theta angle (Input 1).
//		 */
//		this.tMfNMin = new T1MF_Gaussian("Theta Tiny", 0.15, 2.00546);
//		this.tMfN1 = new T1MF_Gaussian("Theta Some", 3.31, 1.93929);
//		this.tMf0 = new T1MF_Gaussian("Theta Medium", 5.025, 1.09813);
//		this.tMfP1 = new T1MF_Gaussian("Theta Good amount", 6.46667, 1.74327);
////		this.tMfP1 = new T1MF_Gaussian("Theta Considerable amount", 7.22501, 1.98231);
//		this.tMfPMax = new T1MF_Gaussian("Theta Very large", 9.95002, 2.42824);
//
//		/*
//		 * Membership functions of change in theta angle (Input 2).
//		 */
//		this.dMfNMin = new T1MF_Gaussian("ThetaD Tiny", 0.15, 2.00546);
//		this.dMf0 = new T1MF_Gaussian("ThetaD Medium", 5.025, 1.09813);
//		this.dMfPMax = new T1MF_Gaussian("ThetaD Very large", 9.95002, 2.42824);
//
//		/*
//		 * Membership functions of force (Output).
//		 */
//		this.fMfNMin = new T1MF_Gaussian("Force Tiny", 0.15, 2.00546);
//		this.fMfN2 = new T1MF_Gaussian("Force Small", 1.7, 1.80021);
//		this.fMfN1 = new T1MF_Gaussian("Force Some", 3.31, 1.93929);
//		this.fMf0 = new T1MF_Gaussian("Force Medium", 5.025, 1.09813);
//		this.fMfP1 = new T1MF_Gaussian("Force Good amount", 6.46667, 1.74327);
////		this.fMfP1 = new T1MF_Gaussian("Theta Considerable amount", 7.22501, 1.98231);
//		this.fMfP2 = new T1MF_Gaussian("Force Large", 8.35001, 1.49622);
//		this.fMfPMax = new T1MF_Gaussian("Force Very large", 9.95002, 2.42824);

		/*
		 * Theta MF associations.
		 */
		T1_Antecedent tINMin = new T1_Antecedent("Tiny Theta", tMfNMin, this.t);
		T1_Antecedent tIN1 = new T1_Antecedent("Some Theta", tMfN1, this.t);
		T1_Antecedent tI0 = new T1_Antecedent("Medium Theta", tMf0, this.t);
		T1_Antecedent tIP1 = new T1_Antecedent("Good amount Theta", tMfP1, this.t);
		T1_Antecedent tIPMax = new T1_Antecedent("Very large Theta", tMfPMax, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dINMin = new T1_Antecedent("Tiny ThetaD", dMfNMin, this.d);
		T1_Antecedent dI0 = new T1_Antecedent("Medium ThetaD", dMf0, this.d);
		T1_Antecedent dIPMax = new T1_Antecedent("Very large ThetaD", dMfPMax, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fONMin = new T1_Consequent("Tiny Force", fMfNMin, this.f);
		T1_Consequent fON2 = new T1_Consequent("Small Force", fMfN2, this.f);
		T1_Consequent fON1 = new T1_Consequent("Some Force", fMfN1, this.f);
		T1_Consequent fO0 = new T1_Consequent("Medium Force", fMf0, this.f);
		T1_Consequent fOP1 = new T1_Consequent("Good amount Force", fMfP1, this.f);
		T1_Consequent fOP2 = new T1_Consequent("Large Force", fMfP2, this.f);
		T1_Consequent fOPMax = new T1_Consequent("Very large Force", fMfPMax, this.f);

		/*
		 * Rulebase.
		 */
		this.rulebase = new T1_Rulebase(15);
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tINMin, dINMin}, fONMin));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tINMin, dI0}, fON2));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tINMin, dIPMax}, fON2));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIN1, dINMin}, fONMin));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIN1, dI0}, fON2));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIN1, dIPMax}, fON1));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tI0, dINMin}, fON1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tI0, dI0}, fO0));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tI0, dIPMax}, fOP1));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIP1, dINMin}, fOP1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIP1, dI0}, fOP2));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIP1, dIPMax}, fOPMax));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIPMax, dINMin}, fOP2));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIPMax, dI0}, fOP2));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIPMax, dIPMax}, fOPMax));

		this.f.setDiscretisationLevel(discritisationLevel);
	}
}
