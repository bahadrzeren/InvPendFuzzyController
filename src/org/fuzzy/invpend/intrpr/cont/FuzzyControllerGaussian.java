package org.fuzzy.invpend.intrpr.cont;

import generic.Input;
import generic.Output;
import generic.Tuple;
import type1.sets.T1MF_Gaussian;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerGaussian extends FuzzyInvPendController {

	public FuzzyControllerGaussian() {
		super("Gaussian Reference Fuzzy Controller");
	}

	@Override
	protected void initialize() {
		this.t = new Input("Theta", new Tuple(-40, 40));
		this.d = new Input("ThetaD", new Tuple(-8, 8));
		this.f = new Output("Force", new Tuple(-32, 32));

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		this.tNVBMF = new T1MF_Gaussian("Theta negative very big", -40, 5);
		this.tNBMF = new T1MF_Gaussian("Theta negative big",-25, 5);
		this.tNMF = new T1MF_Gaussian("Theta negative", -10, 3.33);
		this.tZMF = new T1MF_Gaussian("Theta zero", 0, 1.67);
		this.tPMF = new T1MF_Gaussian("Theta positive", 10, 3.33);
		this.tPBMF = new T1MF_Gaussian("Theta positive big", 25, 5);
		this.tPVBMF = new T1MF_Gaussian("Theta positive very big", 40, 5);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		this.dNBMF = new T1MF_Gaussian("ThetaD negative big", -8, 1.67);
		this.dNMF = new T1MF_Gaussian("ThetaD negative", -3, 1);
		this.dZMF = new T1MF_Gaussian("ThetaD zero", 0, 0.33);
		this.dPMF = new T1MF_Gaussian("ThetaD positive", 3, 1);
		this.dPBMF = new T1MF_Gaussian("ThetaD positive big", 8, 1.67);

		/*
		 * Membership functions of force (Output).
		 */
		this.fNVVBMF = new T1MF_Gaussian("Force negative very very big", -32, 2.67);
		this.fNVBMF = new T1MF_Gaussian("Force negative very big", -24, 2.67);
		this.fNBMF = new T1MF_Gaussian("Force negative big", -16, 2.67);
		this.fNMF = new T1MF_Gaussian("Force negative", -8, 2.67);
		this.fZMF = new T1MF_Gaussian("Force zero", 0, 1.33);
		this.fPMF = new T1MF_Gaussian("Force positive", 8, 2.67);
		this.fPBMF = new T1MF_Gaussian("Force positive big", 16, 2.67);
		this.fPVBMF = new T1MF_Gaussian("Force positive very big", 24, 2.67);
		this.fPVVBMF = new T1MF_Gaussian("Force positive very very big", 32, 2.67);

		/*
		 * Theta MF associations.
		 */
		T1_Antecedent tNVB = new T1_Antecedent("Negative very big theta", tNVBMF, this.t);
		T1_Antecedent tNB = new T1_Antecedent("Negative big theta", tNBMF, this.t);
		T1_Antecedent tN = new T1_Antecedent("Negative theta", tNMF, this.t);
		T1_Antecedent tZ = new T1_Antecedent("Zero theta", tZMF, this.t);
		T1_Antecedent tP = new T1_Antecedent("Positive theta", tPMF, this.t);
		T1_Antecedent tPB = new T1_Antecedent("Positive big theta", tPBMF, this.t);
		T1_Antecedent tPVB = new T1_Antecedent("Positive very big theta", tPVBMF, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dNB = new T1_Antecedent("Negative big thetaD", dNBMF, this.d);
		T1_Antecedent dN = new T1_Antecedent("Negative thetaD", dNMF, this.d);
		T1_Antecedent dZ = new T1_Antecedent("Zero thetaD", dZMF, this.d);
		T1_Antecedent dP = new T1_Antecedent("Positive thetaD", dPMF, this.d);
		T1_Antecedent dPB = new T1_Antecedent("Positive big thetaD", dPBMF, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fNVVB = new T1_Consequent("Negative very very big force", fNVVBMF, this.f);
		T1_Consequent fNVB = new T1_Consequent("Negative very big force", fNVBMF, this.f);
		T1_Consequent fNB = new T1_Consequent("Negative big force", fNBMF, this.f);
		T1_Consequent fN = new T1_Consequent("Negative force", fNMF, this.f);
		T1_Consequent fZ = new T1_Consequent("Zero force", fZMF, this.f);
		T1_Consequent fP = new T1_Consequent("Positive force", fPMF, this.f);
		T1_Consequent fPB = new T1_Consequent("Positive big force", fPBMF, this.f);
		T1_Consequent fPVB = new T1_Consequent("Positive very big force", fPVBMF, this.f);
		T1_Consequent fPVVB = new T1_Consequent("Positive very very big force", fPVVBMF, this.f);

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

		this.f.setDiscretisationLevel(discritisationLevel);
	}
}
