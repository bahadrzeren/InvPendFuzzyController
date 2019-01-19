package org.fuzzy.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;

import generic.Input;
import generic.Output;
import generic.Tuple;
import tools.JMathPlotter;
import type1.sets.T1MF_Gaussian;
import type1.sets.T1MF_Interface;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerDictSFT {

	private static int discritisationLevel = 50;

	private Input t = null;
	private Input d = null;
	private Output f = null;
	private T1_Rulebase rulebase = null;

	public FuzzyControllerDictSFT() {
		this.t = new Input("Theta", new Tuple(-40, 40));
		this.d = new Input("ThetaD", new Tuple(-8, 8));
		this.f = new Output("Force", new Tuple(-32, 32));

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		T1MF_Gaussian tVSMF = new T1MF_Gaussian("Theta very small", -38.4, 13.2984);
		T1MF_Gaussian tVLMF = new T1MF_Gaussian("Theta very little",-37.2, 14.3811);
		T1MF_Gaussian tSMMF = new T1MF_Gaussian("Theta some to moderate", -2.4, 11.9765);
		T1MF_Gaussian tMMF = new T1MF_Gaussian("Theta medium", 0.2, 8.78504);
		T1MF_Gaussian tMAMF = new T1MF_Gaussian("Theta moderate amount", 1, 11.0384);
		T1MF_Gaussian tLMF = new T1MF_Gaussian("Theta large", 26.8001, 11.9698);
		T1MF_Gaussian tMAAMF = new T1MF_Gaussian("Theta maximum amount", 39.2002, 7.2627);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		T1MF_Gaussian dVSMF = new T1MF_Gaussian("ThetaD very small", -7.68, 2.65968);
		T1MF_Gaussian dSSMF = new T1MF_Gaussian("ThetaD somewhat small", -5.16, 2.92957);
		T1MF_Gaussian dMMF = new T1MF_Gaussian("ThetaD medium", 0.04, 1.75701);
		T1MF_Gaussian dGAMF = new T1MF_Gaussian("ThetaD good amount", 2.34667, 2.78923);
		T1MF_Gaussian dMAMF = new T1MF_Gaussian("ThetaD maximum amount", 7.84003, 1.45254);

		/*
		 * Membership functions of force (Output).
		 */
		T1MF_Gaussian fVVSMF = new T1MF_Gaussian("Force very very small", -30.72, 10.6387);
		T1MF_Gaussian fVSMF = new T1MF_Gaussian("Force very small", -30.72, 10.6387);
		T1MF_Gaussian fSMF = new T1MF_Gaussian("Force small", -21.12, 11.5213);
		T1MF_Gaussian fSMMF = new T1MF_Gaussian("Force some to moderate", -1.92, 9.58118);
		T1MF_Gaussian fMMF = new T1MF_Gaussian("Force medium", 0.16, 7.02803);
		T1MF_Gaussian fMAMF = new T1MF_Gaussian("Force moderate amount", 0.8, 8.83072);
		T1MF_Gaussian fLMF = new T1MF_Gaussian("Force little", 21.4401, 9.57581);
		T1MF_Gaussian fVHAMF = new T1MF_Gaussian("Force very high amount", 31.3601, 11.1254);
		T1MF_Gaussian fMAAMF = new T1MF_Gaussian("Force maximum amount", 31.3601, 5.81016);

		/*
		 * Theta MF associations.
		 */
		T1_Antecedent tVS = new T1_Antecedent("Very small theta", tVSMF, this.t);
		T1_Antecedent tVL = new T1_Antecedent("Very little theta", tVLMF, this.t);
		T1_Antecedent tSM = new T1_Antecedent("Some to moderate theta", tSMMF, this.t);
		T1_Antecedent tM = new T1_Antecedent("Medium theta", tMMF, this.t);
		T1_Antecedent tMA = new T1_Antecedent("Moderate amount theta", tMAMF, this.t);
		T1_Antecedent tL = new T1_Antecedent("Large theta", tLMF, this.t);
		T1_Antecedent tMAA = new T1_Antecedent("Maximum amount theta", tMAAMF, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dVS = new T1_Antecedent("Very small thetaD", dVSMF, this.d);
		T1_Antecedent dSS = new T1_Antecedent("Somewhat small thetaD", dSSMF, this.d);
		T1_Antecedent dMM = new T1_Antecedent("Medium thetaD", dMMF, this.d);
		T1_Antecedent dGA = new T1_Antecedent("Good amount thetaD", dGAMF, this.d);
		T1_Antecedent dMA = new T1_Antecedent("Maximum amount thetaD", dMAMF, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fVVS = new T1_Consequent("Very very small force", fVVSMF, this.f);
		T1_Consequent fVS = new T1_Consequent("Very small force", fVSMF, this.f);
		T1_Consequent fS = new T1_Consequent("Small force", fSMF, this.f);
		T1_Consequent fSM = new T1_Consequent("Some to moderate force", fSMMF, this.f);
		T1_Consequent fM = new T1_Consequent("Medium force", fMMF, this.f);
		T1_Consequent fMA = new T1_Consequent("Moderate amount force", fMAMF, this.f);
		T1_Consequent fL = new T1_Consequent("Little force", fLMF, this.f);
		T1_Consequent fVHA = new T1_Consequent("Very high amount force", fVHAMF, this.f);
		T1_Consequent fMAA = new T1_Consequent("Maximum amount force", fMAAMF, this.f);

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

		double[] aggregatedSet = new double[discritisationLevel]; 

		for (int ts = -40; ts < 41; ts++) {
			for (int ds = -8; ds < 9; ds++) {
				getForce(ts, ds);
				HashMap<Output, double[]> outputSets = getForce(ts, ds);
				Iterator<Output> itr = outputSets.keySet().iterator();
				while (itr.hasNext()) {
					Output output = itr.next();
					double[] outputSet = outputSets.get(output);
					for (int i = 0; i < discritisationLevel; i++) {						
						if (aggregatedSet[i] < outputSet[i])
							aggregatedSet[i] = outputSet[i];
					}
				}
			}
		}

		System.out.println("\n" + this.rulebase);

        JMathPlotter plotter = new JMathPlotter(12,12,12);
        Tuple xTuple = new Tuple();
        xTuple.setLeft(0);
        xTuple.setRight(discritisationLevel);
        Tuple yTuple = new Tuple();
        yTuple.setLeft(0);
        yTuple.setRight(1);
        plotter.plotInputOutput("Aggregated output sets", aggregatedSet, xTuple, yTuple);
        plotter.show();

		//plot some sets, discretizing each input into 100 steps.
        plotMFs("Theta Membership Functions", new T1MF_Interface[]{tVSMF, tVLMF, tSMMF, tMMF, tMAMF, tLMF, tMAAMF}, this.t.getDomain(), 100); 
        plotMFs("ThetaD Membership Functions", new T1MF_Interface[]{dVSMF, dSSMF, dMMF, dGAMF, dMAMF}, this.d.getDomain(), 100);
        plotMFs("Force Membership Functions", new T1MF_Interface[]{fVVSMF, fVSMF, fSMF, fSMMF, fMMF, fMAMF, fLMF, fVHAMF, fMAAMF}, this.f.getDomain(), 100);
        
       
//        //plot control surface
//        //do either height defuzzification (false) or centroid d. (true)
        plotControlSurface(true, 80, 16);
	}

	private HashMap<Output, double[]> getForce(double theta, double thetaD) {
		NumberFormat formatter = new DecimalFormat("#0.00");
		this.t.setInput(theta);
		this.d.setInput(thetaD);
		System.out.println("Theta: " + this.t.getInput() + 
							", ThetaD: " + this.d.getInput() + 
							", Centroid defuzzification: " + formatter.format(rulebase.evaluate(1).get(this.f)));
		return rulebase.getOutputSetBuffers();
	}

    private void plotMFs(String name, T1MF_Interface[] sets, Tuple xAxisRange, int discretizationLevel)
    {
        JMathPlotter plotter = new JMathPlotter(12,12,12);
        for (int i=0;i<sets.length;i++) {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, xAxisRange, new Tuple(0.0,1.0), false);
        }
        plotter.show(name);
    }

    private void plotControlSurface(boolean useCentroidDefuzzification, int input1Discs, int input2Discs)
    {
        double output;
        double[] x = new double[(int) Math.floor(this.t.getDomain().getSize()) + 1];
        double[] y = new double[(int) Math.floor(this.d.getDomain().getSize()) + 1];
        double[][] z = new double[y.length][x.length];

        //first, get the values
        int min = (int) Math.floor(this.t.getDomain().getLeft());
        int len = (int) Math.floor(this.t.getDomain().getSize() + 1);
        for(int currentX = 0; currentX < len; currentX++) {
            x[currentX] = min + currentX;        
        }
        min = (int) Math.floor(this.d.getDomain().getLeft());
        len = (int) Math.floor(this.d.getDomain().getSize() + 1);
        for(int currentY = 0; currentY < len; currentY++) {
            y[currentY] = min + currentY;
        }
        
        for(int currentX=0; currentX < x.length; currentX++) {
            this.t.setInput(x[currentX]);
            for(int currentY=0; currentY < y.length; currentY++) {
                this.d.setInput(y[currentY]);
                if(useCentroidDefuzzification)
                    output = rulebase.evaluate(1).get(this.f);
                else
                    output = rulebase.evaluate(0).get(this.f);
                z[currentY][currentX] = output;
            }    
        }

        //now do the plotting
        JMathPlotter plotter = new JMathPlotter(12, 12, 12);
        plotter.plotControlSurface("Control Surface", new String[]{this.t.getName(), this.d.getName(), this.f.getName()}, x, y, z, new Tuple(-32.0, 32.0), true);   
        plotter.show("Type-1 Fuzzy Logic System Control Surface for Inv. Pend. Example");
    }
}
