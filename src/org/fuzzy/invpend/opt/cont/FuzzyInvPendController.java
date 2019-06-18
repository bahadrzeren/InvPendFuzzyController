package org.fuzzy.invpend.opt.cont;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.fuzzy.invpend.cont.FuzzyController;

import generalType2zSlices.sets.GenT2zMF_Interface;
import generic.Input;
import generic.Output;
import generic.Tuple;
import similarity.JaccardSimilarityEngine;
import tools.JMathPlotter;
import type1.sets.T1MF_Gaussian;
import type1.sets.T1MF_Interface;
import type1.system.T1_Rulebase;

public abstract class FuzzyInvPendController implements FuzzyController {
	protected static int discritisationLevel = 50;
	protected static double xMin = 0.0;
	protected static double xMax = 10.0;

	protected static double tMin = -40.0 * Math.PI / 180.0;
	protected static double tMax = 40.0 * Math.PI / 180.0;
	protected static double dMin = -120.0 * Math.PI / 180.0;
	protected static double dMax = 120.0 * Math.PI / 180.0;
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
	protected T1MF_Gaussian tMfNMin = null;
	protected T1MF_Gaussian tMfN1 = null;
	protected T1MF_Gaussian tMf0 = null;
	protected T1MF_Gaussian tMfP1 = null;
	protected T1MF_Gaussian tMfPMax = null;

	/*
	 * Membership functions of change in theta angle (Input 2).
	 */
	protected T1MF_Gaussian dMfNMin = null;
	protected T1MF_Gaussian dMf0 = null;
	protected T1MF_Gaussian dMfPMax = null;

	/*
	 * Membership functions of force (Output).
	 */
	protected T1MF_Gaussian fMfNMin = null;
	protected T1MF_Gaussian fMfN2 = null;
	protected T1MF_Gaussian fMfN1 = null;
	protected T1MF_Gaussian fMf0 = null;
	protected T1MF_Gaussian fMfP1 = null;
	protected T1MF_Gaussian fMfP2 = null;
	protected T1MF_Gaussian fMfPMax = null;

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

	private static JaccardSimilarityEngine jse = new JaccardSimilarityEngine();

	public double getJaccardSimilarity(FuzzyInvPendController optFuzzyCont) {
		double sI11 = jse.getSimilarity(this.tMfNMin, optFuzzyCont.tMfNMin, discritisationLevel);
		double sI12 = jse.getSimilarity(this.tMfN1, optFuzzyCont.tMfN1, discritisationLevel);
		double sI13 = jse.getSimilarity(this.tMf0, optFuzzyCont.tMf0, discritisationLevel);
		double sI14 = jse.getSimilarity(this.tMfP1, optFuzzyCont.tMfP1, discritisationLevel);
		double sI15 = jse.getSimilarity(this.tMfPMax, optFuzzyCont.tMfPMax, discritisationLevel);

		double sI21 = jse.getSimilarity(this.dMfNMin, optFuzzyCont.dMfNMin, discritisationLevel);
		double sI22 = jse.getSimilarity(this.dMf0, optFuzzyCont.dMf0, discritisationLevel);
		double sI23 = jse.getSimilarity(this.dMfPMax, optFuzzyCont.dMfPMax, discritisationLevel);

		double sO1 = jse.getSimilarity(this.fMfNMin, optFuzzyCont.fMfNMin, discritisationLevel);
		double sO2 = jse.getSimilarity(this.fMfN2, optFuzzyCont.fMfN2, discritisationLevel);
		double sO3 = jse.getSimilarity(this.fMfN1, optFuzzyCont.fMfN1, discritisationLevel);
		double sO4 = jse.getSimilarity(this.fMf0, optFuzzyCont.fMf0, discritisationLevel);
		double sO5 = jse.getSimilarity(this.fMfP1, optFuzzyCont.fMfP1, discritisationLevel);
		double sO6 = jse.getSimilarity(this.fMfP2, optFuzzyCont.fMfP2, discritisationLevel);
		double sO7 = jse.getSimilarity(this.fMfPMax, optFuzzyCont.fMfPMax, discritisationLevel);

		return sI11 + sI12 + sI13 + sI14 + sI15
				+ sI21 + sI22 + sI23
				+ sO1 + sO2 + sO3 + sO4 + sO5 + sO6 + sO7; 
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	private static void reportSimilarity(String mfName, T1MF_Gaussian dictFuzzyMF, T1MF_Gaussian optFuzzyMF, List<Double> jacSim) {
		double jaccard = jse.getSimilarity(dictFuzzyMF, optFuzzyMF, discritisationLevel);
		jacSim.add(jaccard);
		System.out.println(mfName + " Center/Sigma#" + formatter.format(dictFuzzyMF.getMean()) + "/" + formatter.format(dictFuzzyMF.getSpread()) + "#"
														+ formatter.format(optFuzzyMF.getMean()) + "/" + formatter.format(optFuzzyMF.getSpread()) + "#jaccard = " + formatter.format(jaccard));
	}

	public static void reportSimilarity(FuzzyInvPendController dictFuzzyCont,
										FuzzyInvPendController optFuzzyCont) {

		List<Double> jacSim = new ArrayList<Double>();

		System.out.println("#DICTIONARY#OPTIMIZED#JACCARD SIMILARITY");


		reportSimilarity("Tiny", dictFuzzyCont.tMfNMin, optFuzzyCont.tMfNMin, jacSim);
		reportSimilarity("Some", dictFuzzyCont.tMfN1, optFuzzyCont.tMfN1, jacSim);
		reportSimilarity("Medium", dictFuzzyCont.tMf0, optFuzzyCont.tMf0, jacSim);
		reportSimilarity("Good amount", dictFuzzyCont.tMfP1, optFuzzyCont.tMfP1, jacSim);
		reportSimilarity("Very large", dictFuzzyCont.tMfPMax, optFuzzyCont.tMfPMax, jacSim);
                         
		reportSimilarity("Tiny", dictFuzzyCont.dMfNMin, optFuzzyCont.dMfNMin, jacSim);
		reportSimilarity("Medium", dictFuzzyCont.dMf0, optFuzzyCont.dMf0, jacSim);
		reportSimilarity("Very large", dictFuzzyCont.dMfPMax, optFuzzyCont.dMfPMax, jacSim);
                         
		reportSimilarity("Tiny", dictFuzzyCont.fMfNMin, optFuzzyCont.fMfNMin, jacSim);
		reportSimilarity("Small", dictFuzzyCont.fMfN2, optFuzzyCont.fMfN2, jacSim);
		reportSimilarity("Some", dictFuzzyCont.fMfN1, optFuzzyCont.fMfN1, jacSim);
		reportSimilarity("Medium", dictFuzzyCont.fMf0, optFuzzyCont.fMf0, jacSim);
		reportSimilarity("Good amount", dictFuzzyCont.fMfP1, optFuzzyCont.fMfP1, jacSim);
		reportSimilarity("Large", dictFuzzyCont.fMfP2, optFuzzyCont.fMfP2, jacSim);
		reportSimilarity("Very large", dictFuzzyCont.fMfPMax, optFuzzyCont.fMfPMax, jacSim);


		double simCmin = Integer.MAX_VALUE;
		double simCmax = Integer.MIN_VALUE;
		double simCtot = 0.0;
		double simCavg = 0.0;
		double simSmin = Integer.MAX_VALUE;
		double simSmax = Integer.MIN_VALUE;
		double simStot = 0.0;
		double simSavg = 0.0;

		for (int i = 0; i < varNdx / 2; i++) {
			if (simCmin > cSim.get(i)) simCmin = cSim.get(i);
			if (simCmax < cSim.get(i)) simCmax = cSim.get(i);
			simCtot += cSim.get(i);
			if (simSmin > sSim.get(i)) simSmin = sSim.get(i);
			if (simSmax < sSim.get(i)) simSmax = sSim.get(i);
			simStot += sSim.get(i);
		}
		simCavg = 2.0 * simCtot / varNdx;
		simSavg = 2.0 * simStot / varNdx;

		System.out.println("Center similarity (max/avg/min/tot): ###" + formatter.format(simCmax) + "/" + formatter.format(simCavg) + "/" + formatter.format(simCmin) + "/" + formatter.format(simCtot));
		System.out.println("Sigma similarity (max/avg/min/tot): ###" + formatter.format(simSmax) + "/" + formatter.format(simSavg) + "/" + formatter.format(simSmin) + "/" + formatter.format(simStot));
		System.out.println("Weighted similarity (max/avg/min/tot): ###" + formatter.format(simCmax * centerW + simSmax * sigmaW) + "/" +
																		formatter.format(simCavg * centerW + simSavg * sigmaW) + "/" +
																		formatter.format(simCmin * centerW + simSmin * sigmaW) + "/" +
																		formatter.format(simCtot * centerW + simStot * sigmaW));
	}
}
