package org.fuzzy.invpend.opt.cont;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fuzzy.Dictionary;
import org.fuzzy.invpend.cont.FuzzyController;

import generic.Input;
import generic.Output;
import generic.Tuple;
import similarity.JaccardSimilarityEngine;
import tools.JMathPlotter;
import type1.sets.T1MF_Gaussian;
import type1.sets.T1MF_Interface;
import type1.system.T1_Rulebase;

public abstract class FuzzyInvPendController implements FuzzyController {

	private static Logger logger = LogManager.getLogger(FuzzyInvPendController.class);

	public static int discritisationLevel = 50;
	private static double normMin = 0.0;
	private static double normMax = 10.0;

	private static double tMin = -40.0 * Math.PI / 180.0;
	private static double tMax = 40.0 * Math.PI / 180.0;
	private static double dMin = -120.0 * Math.PI / 180.0;
	private static double dMax = 120.0 * Math.PI / 180.0;
	private static double fMin = -32.0;
	private static double fMax = 32.0;

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

	private List<Double> variables = null;

	public List<Double> getVariables() {
		if (variables == null) {
			variables = new ArrayList<Double>();

			variables.add(this.tMfNMin.getMean());
			variables.add(this.tMfNMin.getSpread());
			variables.add(this.tMfN1.getMean());
			variables.add(this.tMfN1.getSpread());
			variables.add(this.tMf0.getMean());
			variables.add(this.tMf0.getSpread());
			variables.add(this.tMfP1.getMean());
			variables.add(this.tMfP1.getSpread());
			variables.add(this.tMfPMax.getMean());
			variables.add(this.tMfPMax.getSpread());

			variables.add(this.dMfNMin.getMean());
			variables.add(this.dMfNMin.getSpread());
			variables.add(this.dMf0.getMean());
			variables.add(this.dMf0.getSpread());
			variables.add(this.dMfPMax.getMean());
			variables.add(this.dMfPMax.getSpread());

			variables.add(this.fMfNMin.getMean());
			variables.add(this.fMfNMin.getSpread());
			variables.add(this.fMfN2.getMean());
			variables.add(this.fMfN2.getSpread());
			variables.add(this.fMfN1.getMean());
			variables.add(this.fMfN1.getSpread());
			variables.add(this.fMf0.getMean());
			variables.add(this.fMf0.getSpread());
			variables.add(this.fMfP1.getMean());
			variables.add(this.fMfP1.getSpread());
			variables.add(this.fMfP2.getMean());
			variables.add(this.fMfP2.getSpread());
			variables.add(this.fMfPMax.getMean());
			variables.add(this.fMfPMax.getSpread());
		}

		return variables;
	}

	public FuzzyInvPendController(String controllerName, List<Double> variables) {
		this.controllerName = controllerName;
		this.t = new Input("Theta", new Tuple(normMin, normMax));
		this.d = new Input("ThetaD", new Tuple(normMin, normMax));
		this.f = new Output("Force", new Tuple(normMin, normMax));
		this.initialize(variables);
	}

	private double getNormalizedTValue(double realValue) {
		return normMin + (normMax - normMin) * (realValue - tMin) / (tMax - tMin);
	}
	private double getRealTValue(double normalizedValue) {
		return tMin + (tMax - tMin) * (normalizedValue - normMin) / (normMax - normMin);
	}

	private double getNormalizedDValue(double realValue) {
		return normMin + (normMax - normMin) * (realValue - dMin) / (dMax - dMin);
	}
	private double getRealDValue(double normalizedValue) {
		return dMin + (dMax - dMin) * (normalizedValue - normMin) / (normMax - normMin);
	}

//	private double getNormalizedFValue(double realValue) {
//		return xMin + (xMax - xMin) * (realValue - fMin) / (fMax - fMin);
//	}
	private double getRealFValue(double normalizedValue) {
		return fMin + (fMax - fMin) * (normalizedValue - normMin) / (normMax - normMin);
	}

	@Override
	public String getControllerName() {
		return controllerName;
	}

//	public static NumberFormat formatter1 = new DecimalFormat("#0.0000");
//	private int itr = 0;

	@Override
	public double calculateControlInput(double theta, double thetaD) {
		this.t.setInput(getNormalizedTValue(theta));
		this.d.setInput(getNormalizedDValue(thetaD));
		double res = rulebase.evaluate(1).get(this.f);

//		logger.info(itr++ + "; "
//						+ "TDF: " + formatter1.format(theta) + ", " + formatter1.format(thetaD) + ", " + formatter1.format(getRealFValue(res)) + "; "
//						+ "degTDF: " + formatter1.format(theta * 180.0 / Math.PI) + ", " + formatter1.format(thetaD * 180.0 / Math.PI) + ", " + formatter1.format(getRealFValue(res)) + "; "
//						+ "normTDF: " + formatter1.format(getNormalizedTValue(theta)) + ", " + formatter1.format(getNormalizedDValue(thetaD)) + ", " + formatter1.format(res));

		return getRealFValue(res);
	}

	@Override
	public void plotMembershipFunctions(String filePrefix, boolean updateNames) {
		//plot some sets, discretizing each input into 100 steps.
		if (updateNames) {
			tMfNMin.setName(Dictionary.getTheMostSimilarOnesName(tMfNMin));
			tMfN1.setName(Dictionary.getTheMostSimilarOnesName(tMfN1));
			tMf0.setName(Dictionary.getTheMostSimilarOnesName(tMf0));
			tMfP1.setName(Dictionary.getTheMostSimilarOnesName(tMfP1));
			tMfPMax.setName(Dictionary.getTheMostSimilarOnesName(tMfPMax));

			dMfNMin.setName(Dictionary.getTheMostSimilarOnesName(dMfNMin));
			dMf0.setName(Dictionary.getTheMostSimilarOnesName(dMf0));
			dMfPMax.setName(Dictionary.getTheMostSimilarOnesName(dMfPMax));

			fMfNMin.setName(Dictionary.getTheMostSimilarOnesName(fMfNMin));
			fMfN2.setName(Dictionary.getTheMostSimilarOnesName(fMfN2));
			fMfN1.setName(Dictionary.getTheMostSimilarOnesName(fMfN1));
			fMf0.setName(Dictionary.getTheMostSimilarOnesName(fMf0));
			fMfP1.setName(Dictionary.getTheMostSimilarOnesName(fMfP1));
			fMfP2.setName(Dictionary.getTheMostSimilarOnesName(fMfP2));
			fMfPMax.setName(Dictionary.getTheMostSimilarOnesName(fMfPMax));
		}
        plotMFs(filePrefix + "_t", "Theta Membership Functions", new T1MF_Interface[]{tMfNMin, tMfN1, tMf0, tMfP1, tMfPMax}, this.t.getDomain(), discritisationLevel * 2); 
        plotMFs(filePrefix + "_td", "ThetaD Membership Functions", new T1MF_Interface[]{dMfNMin, dMf0, dMfPMax}, this.d.getDomain(), discritisationLevel * 2);
        plotMFs(filePrefix + "_f", "Force Membership Functions", new T1MF_Interface[]{fMfNMin, fMfN2, fMfN1, fMf0, fMfP1, fMfP2, fMfPMax}, this.f.getDomain(), discritisationLevel * 2);
	}

	public void plotControlSurface(String filePrefix) {
        plotControlSurface(filePrefix, true);
	}

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	private void plotMFs(String fileName, String name, T1MF_Interface[] sets, Tuple xAxisRange, int discretizationLevel) {
        JMathPlotter plotter = new JMathPlotter(12,12,12);
        for (int i=0;i<sets.length;i++) {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, xAxisRange, new Tuple(0.0, 1.0), false);
        }
//        plotter.show(fileName + " " + name);

        try {
            Thread.sleep(50);
        	plotter.toGraphicFile(new File(LocalDateTime.now().format(dateTimeFormatter) + "_" + fileName + "_fuzzy" + ".png"));
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}

	private void plotControlSurface(String filePrefix, boolean useCentroidDefuzzification) {
        double output;
        double[] x = new double[(int) Math.floor(this.t.getDomain().getSize()) + 1];
        double[] y = new double[(int) Math.floor(this.d.getDomain().getSize()) + 1];
        double[][] z = new double[y.length][x.length];

        //first, get the values
        int min = (int) Math.floor(this.t.getDomain().getLeft());
        int len = (int) Math.floor(this.t.getDomain().getSize() + 1);
        System.out.println("[T]");
        for(int currentX = 0; currentX < len; currentX++) {
            x[currentX] = getRealTValue(min + currentX);
            System.out.print("[" + (min + currentX) + " " + x[currentX] + "]");
            System.out.print(", ");
        }
        System.out.println();

        System.out.println("[D]");
        min = (int) Math.floor(this.d.getDomain().getLeft());
        len = (int) Math.floor(this.d.getDomain().getSize() + 1);
        for(int currentY = 0; currentY < len; currentY++) {
            y[currentY] = getRealDValue(min + currentY);
            System.out.print("[" + (min + currentY) + " " + y[currentY] + "]");
            System.out.print(", ");
        }
        System.out.println();

        System.out.println("[F]");
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
//        plotter.show(filePrefix + " " + this.controllerName);

//		try {
//			Thread.sleep(25);
//			plotter.toGraphicFile(new File(LocalDateTime.now().format(dateTimeFormatter) + "_" + filePrefix + "_fuzzy_surf.png"));
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	private static JaccardSimilarityEngine jse = new JaccardSimilarityEngine();

	public double getAvgJaccardSimilarity(FuzzyInvPendController optFuzzyCont) {
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

		return (sI11 + sI12 + sI13 + sI14 + sI15
				+ sI21 + sI22 + sI23
				+ sO1 + sO2 + sO3 + sO4 + sO5 + sO6 + sO7) / 15.0;
	}

	public static NumberFormat formatter = new DecimalFormat("#0.0000");

	private static void reportSimilarity(String defaultName, T1MF_Gaussian defaultFuzzyMF,
											String midName, T1MF_Gaussian midFuzzyMF,
											List<Double> midJacSim,
											String optName, T1MF_Gaussian optFuzzyMF,
											List<Double> optJacSim) {
		double jacMid = jse.getSimilarity(defaultFuzzyMF, midFuzzyMF, discritisationLevel);
		midJacSim.add(jacMid);
		double jacOpt = jse.getSimilarity(defaultFuzzyMF, optFuzzyMF, discritisationLevel);
		optJacSim.add(jacOpt);
		logger.info(defaultName + "(Center/Sigma/Jaccard):" + formatter.format(defaultFuzzyMF.getMean()) + "/" + formatter.format(defaultFuzzyMF.getSpread()) + "/1.0\t" +
						midName + "(Center/Sigma/Jaccard):" + formatter.format(midFuzzyMF.getMean()) + "/" + formatter.format(midFuzzyMF.getSpread()) + "/" + formatter.format(jacMid) + "\t" +
						optName + "(Center/Sigma/Jaccard):" + formatter.format(optFuzzyMF.getMean()) + "/" + formatter.format(optFuzzyMF.getSpread()) + "/" + formatter.format(jacOpt));
	}

	public static String reportSimilarityStats(String logPrefix, List<Double> jacSim) {
		double simmin = Integer.MAX_VALUE;
		double simmax = Integer.MIN_VALUE;
		double simtot = 0.0;
		double simavg = 0.0;

		for (int i = 0; i < jacSim.size(); i++) {
			if (simmin > jacSim.get(i)) simmin = jacSim.get(i);
			if (simmax < jacSim.get(i)) simmax = jacSim.get(i);
			simtot += jacSim.get(i);
		}
		simavg = simtot / jacSim.size();
		simavg = simtot / jacSim.size();

		return logPrefix + "(Min/Avg/Max):" + formatter.format(simmin) + "/" + formatter.format(simavg) + "/" + formatter.format(simmax);
	}

	public static void reportSimilarity(String defaultHeader, String midHeader, String optHeader,
										FuzzyInvPendController dictFuzzyCont,
										FuzzyInvPendController midFuzzyCont,
										FuzzyInvPendController optFuzzyCont) {

		List<Double> midJacSim = new ArrayList<Double>();
		List<Double> optJacSim = new ArrayList<Double>();

		logger.info(defaultHeader + "\t" + midHeader + "\t" + optHeader);
		logger.info("-----------------------------------------------------------------------");

		reportSimilarity("Tiny", dictFuzzyCont.tMfNMin,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.tMfNMin), midFuzzyCont.tMfNMin, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.tMfNMin), optFuzzyCont.tMfNMin, optJacSim);
		reportSimilarity("Some", dictFuzzyCont.tMfN1,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.tMfN1), midFuzzyCont.tMfN1, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.tMfN1), optFuzzyCont.tMfN1, optJacSim);
		reportSimilarity("Medium", dictFuzzyCont.tMf0,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.tMf0), midFuzzyCont.tMf0, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.tMf0), optFuzzyCont.tMf0, optJacSim);
		reportSimilarity("Good amount", dictFuzzyCont.tMfP1,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.tMfP1), midFuzzyCont.tMfP1, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.tMfP1), optFuzzyCont.tMfP1, optJacSim);
		reportSimilarity("Very large", dictFuzzyCont.tMfPMax,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.tMfPMax), midFuzzyCont.tMfPMax, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.tMfPMax), optFuzzyCont.tMfPMax, optJacSim);

		reportSimilarity("Tiny", dictFuzzyCont.dMfNMin,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.dMfNMin), midFuzzyCont.dMfNMin, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.dMfNMin), optFuzzyCont.dMfNMin, optJacSim);
		reportSimilarity("Medium", dictFuzzyCont.dMf0,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.dMf0), midFuzzyCont.dMf0, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.dMf0), optFuzzyCont.dMf0, optJacSim);
		reportSimilarity("Very large", dictFuzzyCont.dMfPMax,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.dMfPMax), midFuzzyCont.dMfPMax, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.dMfPMax), optFuzzyCont.dMfPMax, optJacSim);

		reportSimilarity("Tiny", dictFuzzyCont.fMfNMin,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMfNMin), midFuzzyCont.fMfNMin, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMfNMin), optFuzzyCont.fMfNMin, optJacSim);
		reportSimilarity("Small", dictFuzzyCont.fMfN2,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMfN2), midFuzzyCont.fMfN2, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMfN2), optFuzzyCont.fMfN2, optJacSim);
		reportSimilarity("Some", dictFuzzyCont.fMfN1,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMfN1), midFuzzyCont.fMfN1, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMfN1), optFuzzyCont.fMfN1, optJacSim);
		reportSimilarity("Medium", dictFuzzyCont.fMf0,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMf0), midFuzzyCont.fMf0, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMf0), optFuzzyCont.fMf0, optJacSim);
		reportSimilarity("Good amount", dictFuzzyCont.fMfP1,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMfP1), midFuzzyCont.fMfP1, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMfP1), optFuzzyCont.fMfP1, optJacSim);
		reportSimilarity("Large", dictFuzzyCont.fMfP2,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMfP2), midFuzzyCont.fMfP2, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMfP2), optFuzzyCont.fMfP2, optJacSim);
		reportSimilarity("Very large", dictFuzzyCont.fMfPMax,
							Dictionary.getTheMostSimilarOnesName(midFuzzyCont.fMfPMax), midFuzzyCont.fMfPMax, midJacSim,
							Dictionary.getTheMostSimilarOnesName(optFuzzyCont.fMfPMax), optFuzzyCont.fMfPMax, optJacSim);
		logger.info("-----------------------------------------------------------------------");
		logger.info("def(Min/Avg/Max):1.0/1.0/1.0\t" + reportSimilarityStats("mid", midJacSim) + "\t" + reportSimilarityStats("opt", optJacSim));
		logger.info("-----------------------------------------------------------------------");
	}
}
