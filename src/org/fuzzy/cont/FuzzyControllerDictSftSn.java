package org.fuzzy.cont;

import generic.Input;
import generic.Output;
import generic.Tuple;
import type1.sets.T1MF_Gaussian;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerDictSftSn extends FuzzyInvPendController {

	public FuzzyControllerDictSftSn() {
		super("Gaussian Dictionary Interpered Normalized Fuzzy Controller");
	}

	@Override
	protected void initialize() {
		this.t = new Input("Theta", new Tuple(-40, 40));
		this.d = new Input("ThetaD", new Tuple(-8, 8));
		this.f = new Output("Force", new Tuple(-32, 32));

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		this.tNVBMF = new T1MF_Gaussian("Theta none to very little", -39.4, 4.61267);
		this.tNBMF = new T1MF_Gaussian("Theta somewhat small",-25.8, 3.85195);
		this.tNMF = new T1MF_Gaussian("Theta some", -13.52, 4.07981);
		this.tZMF = new T1MF_Gaussian("Theta medium", 0.2, 2.31021);
		this.tPMF = new T1MF_Gaussian("Theta good amount", 11.7334, 3.66743);
		this.tPBMF = new T1MF_Gaussian("Theta very sizeable", 25.0001, 4.82549);
		this.tPVBMF = new T1MF_Gaussian("Theta humongous amount", 39.6002, 5.0274);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		this.dNBMF = new T1MF_Gaussian("ThetaD teeny-weeny", -7.76, 1.81289);
		this.dNMF = new T1MF_Gaussian("ThetaD some", -2.704, 1.14235);
		this.dZMF = new T1MF_Gaussian("ThetaD medium", 0.04, 0.646857);
		this.dPMF = new T1MF_Gaussian("ThetaD considerable amount", 3.56002, 1.16769);
		this.dPBMF = new T1MF_Gaussian("ThetaD very large", 7.92003, 1.43036);

		/*
		 * Membership functions of force (Output).
		 */
		this.fNVVBMF = new T1MF_Gaussian("Force none to very little", -31.52, 2.87011);
		this.fNVBMF = new T1MF_Gaussian("Force low amount", -24.32, 2.53204);
		this.fNBMF = new T1MF_Gaussian("Force somewhat small", -20.64, 2.39667);
		this.fNMF = new T1MF_Gaussian("Force modest amount", -5.28, 2.37762);
		this.fZMF = new T1MF_Gaussian("Force medium", 0.16, 1.43746);
		this.fPMF = new T1MF_Gaussian("Force good amount", 9.38669, 2.28196);
		this.fPBMF = new T1MF_Gaussian("Force substantial amount", 16.8001, 2.62675);
		this.fPVBMF = new T1MF_Gaussian("Force high amount", 25.6001, 2.87835);
		this.fPVVBMF = new T1MF_Gaussian("Force very high amount", 31.3601, 2.2755);

		/*
		 * Theta MF associations.
		 */
		T1_Antecedent tNVL = new T1_Antecedent("None to very little theta", tNVBMF, this.t);
		T1_Antecedent tSS = new T1_Antecedent("Somewhat small theta", tNBMF, this.t);
		T1_Antecedent tS = new T1_Antecedent("Some theta", tNMF, this.t);
		T1_Antecedent tM = new T1_Antecedent("Medium theta", tZMF, this.t);
		T1_Antecedent tGA = new T1_Antecedent("Good amount theta", tPMF, this.t);
		T1_Antecedent tVS = new T1_Antecedent("Very sizeable theta", tPBMF, this.t);
		T1_Antecedent tHA = new T1_Antecedent("Humongous amount theta", tPVBMF, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dTW = new T1_Antecedent("Teeny-weeny thetaD", dNBMF, this.d);
		T1_Antecedent dS = new T1_Antecedent("Some thetaD", dNMF, this.d);
		T1_Antecedent dM = new T1_Antecedent("Medium thetaD", dZMF, this.d);
		T1_Antecedent dCA = new T1_Antecedent("Considerable amount thetaD", dPMF, this.d);
		T1_Antecedent dVL = new T1_Antecedent("Very large thetaD", dPBMF, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fNVL = new T1_Consequent("None to very little force", fNVVBMF, this.f);
		T1_Consequent fLA = new T1_Consequent("Low amount force", fNVBMF, this.f);
		T1_Consequent fSS = new T1_Consequent("Somewhat small force", fNBMF, this.f);
		T1_Consequent fMM = new T1_Consequent("Modest amount force", fNMF, this.f);
		T1_Consequent fM = new T1_Consequent("Medium force", fZMF, this.f);
		T1_Consequent fGA = new T1_Consequent("Good amount force", fPMF, this.f);
		T1_Consequent fSA = new T1_Consequent("Substantial amount force", fPBMF, this.f);
		T1_Consequent fHA = new T1_Consequent("High amount force", fPVBMF, this.f);
		T1_Consequent fVHA = new T1_Consequent("Very high amount force", fPVVBMF, this.f);

		/*
		 * Rulebase.
		 */
		this.rulebase = new T1_Rulebase(35);
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tHA, dVL}, fVHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tHA, dCA}, fVHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tHA, dM}, fHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tHA, dS}, fSA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tHA, dTW}, fGA));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dVL}, fVHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dCA}, fHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dM}, fSA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dS}, fGA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tVS, dTW}, fM));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tGA, dVL}, fHA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tGA, dCA}, fSA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tGA, dM}, fGA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tGA, dS}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tGA, dTW}, fMM));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dVL}, fSA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dCA}, fGA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dM}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dS}, fMM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tM, dTW}, fSS));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tS, dVL}, fGA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tS, dCA}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tS, dM}, fMM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tS, dS}, fSS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tS, dTW}, fLA));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSS, dVL}, fM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSS, dCA}, fMM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSS, dM}, fSS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSS, dS}, fLA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tSS, dTW}, fNVL));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVL, dVL}, fMM));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVL, dCA}, fSS));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVL, dM}, fLA));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVL, dS}, fNVL));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVL, dTW}, fNVL));

		this.f.setDiscretisationLevel(discritisationLevel);
	}
}
