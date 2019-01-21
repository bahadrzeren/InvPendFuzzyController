package org.fuzzy.cont;

import generic.Input;
import generic.Output;
import generic.Tuple;
import tools.JMathPlotter;
import type1.sets.T1MF_Interface;
import type1.sets.T1MF_Prototype;
import type1.system.T1_Rulebase;

public abstract class FuzzyInvPendController {
	protected static int discritisationLevel = 50;

	protected String controllerName = null;

	protected Input t = null;
	protected Input d = null;
	protected Output f = null;
	protected T1_Rulebase rulebase = null;

	/*
	 * Membership functions of Theta angle (Input 1).
	 */
	protected T1MF_Prototype tNVBMF = null;
	protected T1MF_Prototype tNBMF = null;
	protected T1MF_Prototype tNMF = null;
	protected T1MF_Prototype tZMF = null;
	protected T1MF_Prototype tPMF = null;
	protected T1MF_Prototype tPBMF = null;
	protected T1MF_Prototype tPVBMF = null;

	/*
	 * Membership functions of change in theta angle (Input 2).
	 */
	protected T1MF_Prototype dNBMF = null;
	protected T1MF_Prototype dNMF = null;
	protected T1MF_Prototype dZMF = null;
	protected T1MF_Prototype dPMF = null;
	protected T1MF_Prototype dPBMF = null;

	/*
	 * Membership functions of force (Output).
	 */
	protected T1MF_Prototype fNVVBMF = null;
	protected T1MF_Prototype fNVBMF = null;
	protected T1MF_Prototype fNBMF = null;
	protected T1MF_Prototype fNMF = null;
	protected T1MF_Prototype fZMF = null;
	protected T1MF_Prototype fPMF = null;
	protected T1MF_Prototype fPBMF = null;
	protected T1MF_Prototype fPVBMF = null;
	protected T1MF_Prototype fPVVBMF = null;

	protected abstract void initialize();

	public FuzzyInvPendController(String controllerName) {
		this.controllerName = controllerName;
		this.initialize();
	}

	public String getControllerName() {
		return controllerName;
	}

	public double getControlInput(double theta, double thetaD) {
		this.t.setInput(theta);
		this.d.setInput(thetaD);
		this.d.setInput(thetaD);
		return rulebase.evaluate(1).get(this.f);
	}

	public void plotMembershipFunctions() {
		//plot some sets, discretizing each input into 100 steps.
        plotMFs("Theta Membership Functions", new T1MF_Interface[]{tNVBMF, tNBMF, tNMF, tZMF, tPMF, tPBMF, tPVBMF}, this.t.getDomain(), 100); 
        plotMFs("ThetaD Membership Functions", new T1MF_Interface[]{dNBMF, dNMF, dZMF, dPMF, dPBMF}, this.d.getDomain(), 100);
        plotMFs("Force Membership Functions", new T1MF_Interface[]{fNVVBMF, fNVBMF, fNBMF, fNMF, fZMF, fPMF, fPBMF, fPVBMF, fPVVBMF}, this.f.getDomain(), 100);
	}

	public void plotControlSurface() {
        plotControlSurface(true, 80, 16);
	}

	private void plotMFs(String name, T1MF_Interface[] sets, Tuple xAxisRange, int discretizationLevel) {
        JMathPlotter plotter = new JMathPlotter(12,12,12);
        for (int i=0;i<sets.length;i++) {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, xAxisRange, new Tuple(0.0,1.0), false);
        }
        plotter.show(name);
    }

	private void plotControlSurface(boolean useCentroidDefuzzification, int input1Discs, int input2Discs) {
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
        plotter.plotControlSurface("Control Surface of " + this.controllerName, new String[]{this.t.getName(), this.d.getName(), this.f.getName()}, x, y, z, new Tuple(-32.0, 32.0), true);   
        plotter.show(this.controllerName);
    }
}
