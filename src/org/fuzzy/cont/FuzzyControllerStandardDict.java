package org.fuzzy.cont;

import generic.Input;
import generic.Output;
import generic.Tuple;
import type1.sets.T1MF_Gaussian;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerStandardDict extends FuzzyInvPendController {

	public FuzzyControllerStandardDict() {
		super("Gaussian Dictionary Interpered Fuzzy Controller");
	}

	@Override
	protected void initialize() {
		this.t = new Input("Theta", new Tuple(-40, 40));
		this.d = new Input("ThetaD", new Tuple(-8, 8));
		this.f = new Output("Force", new Tuple(-32, 32));

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		this.tNVBMF = new T1MF_Gaussian("Theta very small", -38.4, 13.2984);
		this.tNBMF = new T1MF_Gaussian("Theta very little",-37.2, 14.3811);
		this.tNMF = new T1MF_Gaussian("Theta some to moderate", -2.4, 11.9765);
		this.tZMF = new T1MF_Gaussian("Theta medium", 0.2, 8.78504);
		this.tPMF = new T1MF_Gaussian("Theta moderate amount", 1, 11.0384);
		this.tPBMF = new T1MF_Gaussian("Theta large", 26.8001, 11.9698);
		this.tPVBMF = new T1MF_Gaussian("Theta maximum amount", 39.2002, 7.2627);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		this.dNBMF = new T1MF_Gaussian("ThetaD very small", -7.68, 2.65968);
		this.dNMF = new T1MF_Gaussian("ThetaD somewhat small", -5.16, 2.92957);
		this.dZMF = new T1MF_Gaussian("ThetaD medium", 0.04, 1.75701);
		this.dPMF = new T1MF_Gaussian("ThetaD good amount", 2.34667, 2.78923);
		this.dPBMF = new T1MF_Gaussian("ThetaD maximum amount", 7.84003, 1.45254);

		/*
		 * Membership functions of force (Output).
		 */
		this.fNVVBMF = new T1MF_Gaussian("Force very very small", -30.72, 10.6387);
		this.fNVBMF = new T1MF_Gaussian("Force very small", -30.72, 10.6387);
		this.fNBMF = new T1MF_Gaussian("Force small", -21.12, 11.5213);
		this.fNMF = new T1MF_Gaussian("Force some to moderate", -1.92, 9.58118);
		this.fZMF = new T1MF_Gaussian("Force medium", 0.16, 7.02803);
		this.fPMF = new T1MF_Gaussian("Force moderate amount", 0.8, 8.83072);
		this.fPBMF = new T1MF_Gaussian("Force little", 21.4401, 9.57581);
		this.fPVBMF = new T1MF_Gaussian("Force very high amount", 31.3601, 11.1254);
		this.fPVVBMF = new T1MF_Gaussian("Force maximum amount", 31.3601, 5.81016);

		/*
		 * Theta MF associations.
		 */
		T1_Antecedent tVS = new T1_Antecedent("Very small theta", tNVBMF, this.t);
		T1_Antecedent tVL = new T1_Antecedent("Very little theta", tNBMF, this.t);
		T1_Antecedent tSM = new T1_Antecedent("Some to moderate theta", tNMF, this.t);
		T1_Antecedent tM = new T1_Antecedent("Medium theta", tZMF, this.t);
		T1_Antecedent tMA = new T1_Antecedent("Moderate amount theta", tPMF, this.t);
		T1_Antecedent tL = new T1_Antecedent("Large theta", tPBMF, this.t);
		T1_Antecedent tMAA = new T1_Antecedent("Maximum amount theta", tPVBMF, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dVS = new T1_Antecedent("Very small thetaD", dNBMF, this.d);
		T1_Antecedent dSS = new T1_Antecedent("Somewhat small thetaD", dNMF, this.d);
		T1_Antecedent dMM = new T1_Antecedent("Medium thetaD", dZMF, this.d);
		T1_Antecedent dGA = new T1_Antecedent("Good amount thetaD", dPMF, this.d);
		T1_Antecedent dMA = new T1_Antecedent("Maximum amount thetaD", dPBMF, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fVVS = new T1_Consequent("Very very small force", fNVVBMF, this.f);
		T1_Consequent fVS = new T1_Consequent("Very small force", fNVBMF, this.f);
		T1_Consequent fS = new T1_Consequent("Small force", fNBMF, this.f);
		T1_Consequent fSM = new T1_Consequent("Some to moderate force", fNMF, this.f);
		T1_Consequent fM = new T1_Consequent("Medium force", fZMF, this.f);
		T1_Consequent fMA = new T1_Consequent("Moderate amount force", fPMF, this.f);
		T1_Consequent fL = new T1_Consequent("Little force", fPBMF, this.f);
		T1_Consequent fVHA = new T1_Consequent("Very high amount force", fPVBMF, this.f);
		T1_Consequent fMAA = new T1_Consequent("Maximum amount force", fPVVBMF, this.f);

		/*
		 * Rulebase.
		 */
		this.rulebase = new T1_Rulebase(35);
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMAA, dMA}, fMAA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMAA, dGA}, fMAA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMAA, dMM}, fVHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMAA, dSS}, fL));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMAA, dVS}, fMA));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tL, dMA}, fMAA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tL, dGA}, fVHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tL, dMM}, fL));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tL, dSS}, fMA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tL, dVS}, fM));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMA, dMA}, fVHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMA, dGA}, fL));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMA, dMM}, fMA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMA, dSS}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tMA, dVS}, fSM));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dMA}, fL));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dGA}, fMA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dMM}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dSS}, fSM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dVS}, fS));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSM, dMA}, fMA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSM, dGA}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSM, dMM}, fSM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSM, dSS}, fS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSM, dVS}, fVS));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVL, dMA}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVL, dGA}, fSM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVL, dMM}, fS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVL, dSS}, fVS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVL, dVS}, fVVS));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dMA}, fSM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dGA}, fS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dMM}, fVS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dSS}, fVVS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dVS}, fVVS));

		this.f.setDiscretisationLevel(discritisationLevel);
	}
}
