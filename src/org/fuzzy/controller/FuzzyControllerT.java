package org.fuzzy.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

import generic.Input;
import generic.Output;
import generic.Tuple;
import tools.JMathPlotter;
import type1.sets.T1MF_Interface;
import type1.sets.T1MF_Triangular;
import type1.system.T1_Antecedent;
import type1.system.T1_Consequent;
import type1.system.T1_Rule;
import type1.system.T1_Rulebase;

public class FuzzyControllerT {

	private static int discritisationLevel = 50;

	private Input t = null;
	private Input d = null;
	private Output f = null;
	private T1_Rulebase rulebase = null;

	public FuzzyControllerT() {
		this.t = new Input("Theta", new Tuple(-40, 40));
		this.d = new Input("ThetaD", new Tuple(-8, 8));
		this.f = new Output("Force", new Tuple(-32, 32));

		/*
		 * Membership functions of Theta angle (Input 1).
		 */
		T1MF_Triangular tNVBMF = new T1MF_Triangular("Theta negative very big", -40, -40, -25);
		T1MF_Triangular tNBMF = new T1MF_Triangular("Theta negative big", -40, -25, -10);
		T1MF_Triangular tNMF = new T1MF_Triangular("Theta negative", -20, -10, 0);
		T1MF_Triangular tZMF = new T1MF_Triangular("Theta zero", -5, 0, 5);
		T1MF_Triangular tPMF = new T1MF_Triangular("Theta positive", 0, 10, 20);
		T1MF_Triangular tPBMF = new T1MF_Triangular("Theta positive big", 10, 25, 40);
		T1MF_Triangular tPVBMF = new T1MF_Triangular("Theta positive very big", 25, 40, 40);

		/*
		 * Membership functions of change in theta angle (Input 2).
		 */
		T1MF_Triangular dNBMF = new T1MF_Triangular("ThetaD negative big", -8, -8, -3);
		T1MF_Triangular dNMF = new T1MF_Triangular("ThetaD negative", -6, -3, 0);
		T1MF_Triangular dZMF = new T1MF_Triangular("ThetaD zero", -1, 0, 1);
		T1MF_Triangular dPMF = new T1MF_Triangular("ThetaD positive", 0, 3, 6);
		T1MF_Triangular dPBMF = new T1MF_Triangular("ThetaD positive big", 3, 8, 8);

		/*
		 * Membership functions of force (Output).
		 */
		T1MF_Triangular fNVVBMF = new T1MF_Triangular("Force negative very very big", -32, -32, -24);
		T1MF_Triangular fNVBMF = new T1MF_Triangular("Force negative very big", -32, -24, -16);
		T1MF_Triangular fNBMF = new T1MF_Triangular("Force negative big", -24, -16, -8);
		T1MF_Triangular fNMF = new T1MF_Triangular("Force negative", -16, -8, 0);
		T1MF_Triangular fZMF = new T1MF_Triangular("Force zero", -4, 0, 4);
		T1MF_Triangular fPMF = new T1MF_Triangular("Force positive", 0, 8, 16);
		T1MF_Triangular fPBMF = new T1MF_Triangular("Force positive big", 8, 16, 24);
		T1MF_Triangular fPVBMF = new T1MF_Triangular("Force positive very big", 16, 24, 32);
		T1MF_Triangular fPVVBMF = new T1MF_Triangular("Force positive very very big", 24, 32, 32);

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

//		double[] aggregatedSet = new double[discritisationLevel]; 

		for (int ts = -40; ts < 41; ts++) {
			for (int ds = -8; ds < 9; ds++) {
				getForce(ts, ds);
//				HashMap<Output, double[]> outputSets = getForce(ts, ds);
//				Iterator<Output> itr = outputSets.keySet().iterator();
//				while (itr.hasNext()) {
//					Output output = itr.next();
//					double[] outputSet = outputSets.get(output);
//					for (int i = 0; i < discritisationLevel; i++) {						
//						if (aggregatedSet[i] < outputSet[i])
//							aggregatedSet[i] = outputSet[i];
//					}
//				}
			}
		}

		System.out.println("\n" + this.rulebase);

//        JMathPlotter plotter = new JMathPlotter(12,12,12);
//        Tuple xTuple = new Tuple();
//        xTuple.setLeft(0);
//        xTuple.setRight(discritisationLevel);
//        Tuple yTuple = new Tuple();
//        yTuple.setLeft(0);
//        yTuple.setRight(1);
//        plotter.plotInputOutput("Aggregated output sets", aggregatedSet, xTuple, yTuple);
//        plotter.show();

		//plot some sets, discretizing each input into 100 steps.
        plotMFs("Theta Membership Functions", new T1MF_Interface[]{tNVBMF, tNBMF, tNMF, tZMF, tPMF, tPBMF, tPVBMF}, this.t.getDomain(), 100); 
        plotMFs("ThetaD Membership Functions", new T1MF_Interface[]{dNBMF, dNMF, dZMF, dPMF, dPBMF}, this.d.getDomain(), 100);
        plotMFs("Force Membership Functions", new T1MF_Interface[]{fNVVBMF, fNVBMF, fNBMF, fNMF, fZMF, fPMF, fPBMF, fPVBMF, fPVVBMF}, this.f.getDomain(), 100);
       
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
