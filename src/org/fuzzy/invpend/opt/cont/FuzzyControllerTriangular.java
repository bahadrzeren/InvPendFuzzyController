package org.fuzzy.invpend.opt.cont;

import java.util.List;

import type1.sets.T1MF_Triangular;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerTriangular extends FuzzyInvPendController {

	public FuzzyControllerTriangular(List<Double> variables) {
		super("Triangular Reference Fuzzy Controller", variables);
	}

	@Override
	protected void initialize(List<Double> variables) {
		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		this.tMfNMin = new T1MF_Triangular("Theta negative big", 0, 0, 2.5);
		this.tMfN1 = new T1MF_Triangular("Theta negative", 0, 2.5, 5);
		this.tMf0 = new T1MF_Triangular("Theta zero", 3, 5, 7);
		this.tMfP1 = new T1MF_Triangular("Theta positive", 5, 7.5, 10);
		this.tMfPMax = new T1MF_Triangular("Theta positive big", 7.5, 10, 10);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		this.dMfNMin = new T1MF_Triangular("ThetaD negative", 0, 0, 4);
		this.dMf0 = new T1MF_Triangular("ThetaD zero", 2, 5, 8);
		this.dMfPMax = new T1MF_Triangular("ThetaD positive", 6, 10, 10);

		/*
		 * Membership functions of force (Output).
		 */
		this.fMfNMin = new T1MF_Triangular("Force negative very big", 0, 0, 1.5);
		this.fMfN2 = new T1MF_Triangular("Force negative big", 0, 1.5, 3);
		this.fMfN1 = new T1MF_Triangular("Force negative", 1.5, 3, 4.5);
		this.fMf0 = new T1MF_Triangular("Force zero", 3.5, 5, 6.5);
		this.fMfP1 = new T1MF_Triangular("Force positive", 5.5, 7, 8.5);
		this.fMfP2 = new T1MF_Triangular("Force positive big", 7, 8.5, 10);
		this.fMfPMax = new T1MF_Triangular("Force positive very big", 8.5, 10, 10);

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
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tINMin, dI0}, fON1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tINMin, dIPMax}, fO0));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIN1, dINMin}, fON2));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIN1, dI0}, fON1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIN1, dIPMax}, fO0));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tI0, dINMin}, fON1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tI0, dI0}, fO0));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tI0, dIPMax}, fOP1));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIP1, dINMin}, fO0));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIP1, dI0}, fOP1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIP1, dIPMax}, fOP2));

		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIPMax, dINMin}, fO0));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIPMax, dI0}, fOP1));
		this.rulebase.addRule(new T1_Rule(new T1_Antecedent[]{tIPMax, dIPMax}, fOPMax));

		this.f.setDiscretisationLevel(discritisationLevel);
	}
}
