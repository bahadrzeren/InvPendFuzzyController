package org.fuzzy.invpend.opt.cont;

import java.util.List;

import org.fuzzy.invpend.cont.FuzzyController;

import generic.Input;
import generic.Output;
import generic.Tuple;
import tools.JMathPlotter;
import type1.sets.T1MF_Interface;
import type1.sets.T1MF_Prototype;
import type1.system.T1_Rulebase;

public abstract class FuzzyInvPendController implements FuzzyController {
	protected static int discritisationLevel = 50;
	protected static double xMin = 0.0;
	protected static double xMax = 10.0;

	protected static double tMin = -40.0 * Math.PI / 180.0;
	protected static double tMax = 40.0 * Math.PI / 180.0;
	protected static double dMin = -90.0 * Math.PI / 180.0;
	protected static double dMax = 90.0 * Math.PI / 180.0;
	protected static double fMin = -32.0;
	protected static double fMax = 32.0;

	protected String controllerName = null;

	protected Input t = null;
	protected Input d = null;
	protected Output f = null;
	protected T1_Rulebase rulebase = null;

	/*
	 * Membership functions of Theta angle (Input 1).
	 */
	protected T1MF_Prototype tMfNMin = null;
	protected T1MF_Prototype tMfN1 = null;
	protected T1MF_Prototype tMf0 = null;
	protected T1MF_Prototype tMfP1 = null;
	protected T1MF_Prototype tMfPMax = null;

	/*
	 * Membership functions of change in theta angle (Input 2).
	 */
	protected T1MF_Prototype dMfNMin = null;
	protected T1MF_Prototype dMf0 = null;
	protected T1MF_Prototype dMfPMax = null;

	/*
	 * Membership functions of force (Output).
	 */
	protected T1MF_Prototype fMfNMin = null;
	protected T1MF_Prototype fMfN2 = null;
	protected T1MF_Prototype fMfN1 = null;
	protected T1MF_Prototype fMf0 = null;
	protected T1MF_Prototype fMfP1 = null;
	protected T1MF_Prototype fMfP2 = null;
	protected T1MF_Prototype fMfPMax = null;

	protected abstract void initialize(List<Double> variables);

	public FuzzyInvPendController(String controllerName, List<Double> variables) {
		this.controllerName = controllerName;
		this.t = new Input("Theta", new Tuple(xMin, xMax));
		this.d = new Input("ThetaD", new Tuple(xMin, xMax));
		this.f = new Output("Force", new Tuple(xMin, xMax));
		this.initialize(variables);
	}

	private double getNormalizedTValue(double realValue) {
		return xMin + (xMax - xMin) * (realValue - tMin) / (tMax - tMin);
	}
	private double getRealTValue(double normalizedValue) {
		return tMin + (tMax - tMin) * (normalizedValue - xMin) / (xMax - xMin);
	}

	private double getNormalizedDValue(double realValue) {
		return xMin + (xMax - xMin) * (realValue - dMin) / (dMax - dMin);
	}
	private double getRealDValue(double normalizedValue) {
		return dMin + (dMax - dMin) * (normalizedValue - xMin) / (xMax - xMin);
	}

//	private double getNormalizedFValue(double realValue) {
//		return xMin + (xMax - xMin) * (realValue - fMin) / (fMax - fMin);
//	}
	private double getRealFValue(double normalizedValue) {
		return fMin + (fMax - fMin) * (normalizedValue - xMin) / (xMax - xMin);
	}

	@Override
	public String getControllerName() {
		return controllerName;
	}

//	public static NumberFormat formatter1 = new DecimalFormat("#0.0000");
//	private int itr = 0;

	@Override
	public double getControlInput(double theta, double thetaD) {
		this.t.setInput(getNormalizedTValue(theta));
		this.d.setInput(getNormalizedDValue(thetaD));
		double res = rulebase.evaluate(1).get(this.f);

//		System.out.println(itr++ + "; "
//						+ "TDF: " + formatter1.format(theta) + ", " + formatter1.format(thetaD) + ", " + formatter1.format(getRealFValue(res)) + "; "
//						+ "degTDF: " + formatter1.format(theta * 180.0 / Math.PI) + ", " + formatter1.format(thetaD * 180.0 / Math.PI) + ", " + formatter1.format(getRealFValue(res)) + "; "
//						+ "normTDF: " + formatter1.format(getNormalizedTValue(theta)) + ", " + formatter1.format(getNormalizedDValue(thetaD)) + ", " + formatter1.format(res));

		return getRealFValue(res);
	}

	@Override
	public void plotMembershipFunctions() {
		//plot some sets, discretizing each input into 100 steps.
        plotMFs("Theta Membership Functions", new T1MF_Interface[]{tMfNMin, tMfN1, tMf0, tMfP1, tMfPMax}, this.t.getDomain(), discritisationLevel * 2); 
        plotMFs("ThetaD Membership Functions", new T1MF_Interface[]{dMfNMin, dMf0, dMfPMax}, this.d.getDomain(), discritisationLevel * 2);
        plotMFs("Force Membership Functions", new T1MF_Interface[]{fMfNMin, fMfN2, fMfN1, fMf0, fMfP1, fMfP2, fMfPMax}, this.f.getDomain(), discritisationLevel * 2);
	}

	public void plotControlSurface() {
        plotControlSurface(true);
	}

	private void plotMFs(String name, T1MF_Interface[] sets, Tuple xAxisRange, int discretizationLevel) {
        JMathPlotter plotter = new JMathPlotter(12,12,12);
        for (int i=0;i<sets.length;i++) {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, xAxisRange, new Tuple(0.0, 1.0), false);
        }
        plotter.show(name);
    }

	private void plotControlSurface(boolean useCentroidDefuzzification) {
        double output;
        double[] x = new double[(int) Math.floor(this.t.getDomain().getSize()) + 1];
        double[] y = new double[(int) Math.floor(this.d.getDomain().getSize()) + 1];
        double[][] z = new double[y.length][x.length];

        //first, get the values
        int min = (int) Math.floor(this.t.getDomain().getLeft());
        int len = (int) Math.floor(this.t.getDomain().getSize() + 1);
        for(int currentX = 0; currentX < len; currentX++) {
            x[currentX] = getRealTValue(min + currentX);
            System.out.print("[" + (min + currentX) + " " + x[currentX] + "]");
            System.out.print(", ");
        }
        System.out.println();

        min = (int) Math.floor(this.d.getDomain().getLeft());
        len = (int) Math.floor(this.d.getDomain().getSize() + 1);
        for(int currentY = 0; currentY < len; currentY++) {
            y[currentY] = getRealDValue(min + currentY);
            System.out.print("[" + (min + currentY) + " " + y[currentY] + "]");
            System.out.print(", ");
        }
        System.out.println();

        for(int currentX=0; currentX < x.length; currentX++) {
            this.t.setInput(getNormalizedTValue(x[currentX]));
            for(int currentY=0; currentY < y.length; currentY++) {
                this.d.setInput(getNormalizedDValue(y[currentY]));
                if(useCentroidDefuzzification)
                    output = rulebase.evaluate(1).get(this.f);
                else
                    output = rulebase.evaluate(0).get(this.f);

                z[currentY][currentX] = getRealFValue(output);

                System.out.print("[" + output + " " + z[currentY][currentX] + "]");
                System.out.print(", ");
            }    
            System.out.println();
        }
        System.out.println();

        //now do the plotting
        JMathPlotter plotter = new JMathPlotter(12, 12, 12);
        plotter.plotControlSurface("Control Surface of " + this.controllerName, new String[]{this.t.getName(), this.d.getName(), this.f.getName()}, x, y, z, new Tuple(fMin, fMax), true);   
        plotter.show(this.controllerName);
    }
}
