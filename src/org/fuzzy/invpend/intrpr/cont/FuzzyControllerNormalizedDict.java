package org.fuzzy.invpend.intrpr.cont;

import generic.Input;
import generic.Output;
import generic.Tuple;
import type1.sets.T1MF_Gaussian;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerNormalizedDict extends FuzzyInvPendController {

	public FuzzyControllerNormalizedDict() {
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
		T1_Antecedent tNVB = new T1_Antecedent("Very small theta", tNVBMF, this.t);
		T1_Antecedent tNB = new T1_Antecedent("Very little theta", tNBMF, this.t);
		T1_Antecedent tN = new T1_Antecedent("Some to moderate theta", tNMF, this.t);
		T1_Antecedent tZ = new T1_Antecedent("Medium theta", tZMF, this.t);
		T1_Antecedent tP = new T1_Antecedent("Moderate amount theta", tPMF, this.t);
		T1_Antecedent tPB = new T1_Antecedent("Large theta", tPBMF, this.t);
		T1_Antecedent tPVB = new T1_Antecedent("Maximum amount theta", tPVBMF, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dNB = new T1_Antecedent("Very small thetaD", dNBMF, this.d);
		T1_Antecedent dN = new T1_Antecedent("Somewhat small thetaD", dNMF, this.d);
		T1_Antecedent dZ = new T1_Antecedent("Medium thetaD", dZMF, this.d);
		T1_Antecedent dP = new T1_Antecedent("Good amount thetaD", dPMF, this.d);
		T1_Antecedent dPB = new T1_Antecedent("Maximum amount thetaD", dPBMF, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fNVVB = new T1_Consequent("Very small force", fNVVBMF, this.f);
		T1_Consequent fNVB = new T1_Consequent("Very small force", fNVBMF, this.f);
		T1_Consequent fNB = new T1_Consequent("Small force", fNBMF, this.f);
		T1_Consequent fN = new T1_Consequent("Some to moderate force", fNMF, this.f);
		T1_Consequent fZ = new T1_Consequent("Medium force", fZMF, this.f);
		T1_Consequent fP = new T1_Consequent("Moderate amount force", fPMF, this.f);
		T1_Consequent fPB = new T1_Consequent("Little force", fPBMF, this.f);
		T1_Consequent fPVB = new T1_Consequent("Very high amount force", fPVBMF, this.f);
		T1_Consequent fPVVB = new T1_Consequent("Maximum amount force", fPVVBMF, this.f);

		/*
		 * Rulebase.
		 */
		this.rulebase = new T1_Rulebase(35);
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPVB, dPB}, fPVVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPVB, dP}, fPVVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPVB, dZ}, fPVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPVB, dN}, fPB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPVB, dNB}, fP));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPB, dPB}, fPVVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPB, dP}, fPVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPB, dZ}, fPB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPB, dN}, fP));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tPB, dNB}, fZ));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tP, dPB}, fPVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tP, dP}, fPB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tP, dZ}, fP));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tP, dN}, fZ));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tP, dNB}, fN));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tZ, dPB}, fPB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tZ, dP}, fP));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tZ, dZ}, fZ));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tZ, dN}, fN));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tZ, dNB}, fNB));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tN, dPB}, fP));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tN, dP}, fZ));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tN, dZ}, fN));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tN, dN}, fNB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tN, dNB}, fNVB));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNB, dPB}, fZ));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNB, dP}, fN));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNB, dZ}, fNB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNB, dN}, fNVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNB, dNB}, fNVVB));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVB, dPB}, fN));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVB, dP}, fNB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVB, dZ}, fNVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVB, dN}, fNVVB));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tNVB, dNB}, fNVVB));

		this.f.setDiscretisationLevel(discritisationLevel);	}
}
