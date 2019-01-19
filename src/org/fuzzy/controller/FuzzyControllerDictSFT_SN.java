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

public class FuzzyControllerDictSFT_SN {

	private static int discritisationLevel = 50;

	private Input t = null;
	private Input d = null;
	private Output f = null;
	private T1_Rulebase rulebase = null;

	public FuzzyControllerDictSFT_SN() {
		this.t = new Input("Theta", new Tuple(-40, 40));
		this.d = new Input("ThetaD", new Tuple(-8, 8));
		this.f = new Output("Force", new Tuple(-32, 32));

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		T1MF_Gaussian tNVLMF = new T1MF_Gaussian("Theta none to very little", -39.4, 4.61267);
		T1MF_Gaussian tSSMF = new T1MF_Gaussian("Theta somewhat small",-25.8, 3.85195);
		T1MF_Gaussian tSMF = new T1MF_Gaussian("Theta some", -13.52, 4.07981);
		T1MF_Gaussian tMMF = new T1MF_Gaussian("Theta medium", 0.2, 2.31021);
		T1MF_Gaussian tGAMF = new T1MF_Gaussian("Theta good amount", 11.7334, 3.66743);
		T1MF_Gaussian tVSMF = new T1MF_Gaussian("Theta very sizeable", 25.0001, 4.82549);
		T1MF_Gaussian tHAMF = new T1MF_Gaussian("Theta humongous amount", 39.6002, 5.0274);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		T1MF_Gaussian dTWMF = new T1MF_Gaussian("ThetaD teeny-weeny", -7.76, 1.81289);
		T1MF_Gaussian dSMF = new T1MF_Gaussian("ThetaD some", -2.704, 1.14235);
		T1MF_Gaussian dMMF = new T1MF_Gaussian("ThetaD medium", 0.04, 0.646857);
		T1MF_Gaussian dCAMF = new T1MF_Gaussian("ThetaD considerable amount", 3.56002, 1.16769);
		T1MF_Gaussian dVLMF = new T1MF_Gaussian("ThetaD very large", 7.92003, 1.43036);

		/*
		 * Membership functions of force (Output).
		 */
		T1MF_Gaussian fNVLMF = new T1MF_Gaussian("Force none to very little", -31.52, 2.87011);
		T1MF_Gaussian fLAMF = new T1MF_Gaussian("Force low amount", -24.32, 2.53204);
		T1MF_Gaussian fSSMF = new T1MF_Gaussian("Force somewhat small", -20.64, 2.39667);
		T1MF_Gaussian fMAMF = new T1MF_Gaussian("Force modest amount", -5.28, 2.37762);
		T1MF_Gaussian fMMF = new T1MF_Gaussian("Force medium", 0.16, 1.43746);
		T1MF_Gaussian fGAMF = new T1MF_Gaussian("Force good amount", 9.38669, 2.28196);
		T1MF_Gaussian fSAMF = new T1MF_Gaussian("Force substantial amount", 16.8001, 2.62675);
		T1MF_Gaussian fHAMF = new T1MF_Gaussian("Force high amount", 25.6001, 2.87835);
		T1MF_Gaussian fVHAMF = new T1MF_Gaussian("Force very high amount", 31.3601, 2.2755);

		/*
		 * Theta MF associations.
		 */
		T1_Antecedent tNVL = new T1_Antecedent("None to very little theta", tNVLMF, this.t);
		T1_Antecedent tSS = new T1_Antecedent("Somewhat small theta", tSSMF, this.t);
		T1_Antecedent tS = new T1_Antecedent("Some theta", tSMF, this.t);
		T1_Antecedent tM = new T1_Antecedent("Medium theta", tMMF, this.t);
		T1_Antecedent tGA = new T1_Antecedent("Good amount theta", tGAMF, this.t);
		T1_Antecedent tVS = new T1_Antecedent("Very sizeable theta", tVSMF, this.t);
		T1_Antecedent tHA = new T1_Antecedent("Humongous amount theta", tHAMF, this.t);

		/*
		 * ThetaD MF associations.
		 */
		T1_Antecedent dTW = new T1_Antecedent("Teeny-weeny thetaD", dTWMF, this.d);
		T1_Antecedent dS = new T1_Antecedent("Some thetaD", dSMF, this.d);
		T1_Antecedent dM = new T1_Antecedent("Medium thetaD", dMMF, this.d);
		T1_Antecedent dCA = new T1_Antecedent("Considerable amount thetaD", dCAMF, this.d);
		T1_Antecedent dVL = new T1_Antecedent("Very large thetaD", dVLMF, this.d);

		/*
		 * Force MF associations.
		 */
		T1_Consequent fNVL = new T1_Consequent("None to very little force", fNVLMF, this.f);
		T1_Consequent fLA = new T1_Consequent("Low amount force", fLAMF, this.f);
		T1_Consequent fSS = new T1_Consequent("Somewhat small force", fSSMF, this.f);
		T1_Consequent fMM = new T1_Consequent("Modest amount force", fMAMF, this.f);
		T1_Consequent fM = new T1_Consequent("Medium force", fMMF, this.f);
		T1_Consequent fGA = new T1_Consequent("Good amount force", fGAMF, this.f);
		T1_Consequent fSA = new T1_Consequent("Substantial amount force", fSAMF, this.f);
		T1_Consequent fHA = new T1_Consequent("High amount force", fHAMF, this.f);
		T1_Consequent fVHA = new T1_Consequent("Very high amount force", fVHAMF, this.f);

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
        plotMFs("Theta Membership Functions", new T1MF_Interface[]{tNVLMF, tSSMF, tSMF, tMMF, tGAMF, tVSMF, tHAMF}, this.t.getDomain(), 100); 
        plotMFs("ThetaD Membership Functions", new T1MF_Interface[]{dTWMF, dSMF, dMMF, dCAMF, dVLMF}, this.d.getDomain(), 100);
        plotMFs("Force Membership Functions", new T1MF_Interface[]{fNVLMF, fLAMF, fSSMF, fMAMF, fMMF, fGAMF, fSAMF, fHAMF, fVHAMF}, this.f.getDomain(), 100);
        
       
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
