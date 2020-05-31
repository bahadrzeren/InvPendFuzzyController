package org.fuzzy.invpend.intrpr.cont;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	@Override
	public String getControllerName() {
		return controllerName;
	}

	@Override
	public double calculateControlInput(double theta, double thetaD) {
		this.t.setInput(theta);
		this.d.setInput(thetaD);
		return rulebase.evaluate(1).get(this.f);
	}

	@Override
	public void plotMembershipFunctions(String filePrefix, boolean updateNames) {
		//plot some sets, discretizing each input into 100 steps.
        plotMFs("t", "Theta Membership Functions", new T1MF_Interface[]{tNVBMF, tNBMF, tNMF, tZMF, tPMF, tPBMF, tPVBMF}, this.t.getDomain(), discritisationLevel * 2); 
        plotMFs("td", "ThetaD Membership Functions", new T1MF_Interface[]{dNBMF, dNMF, dZMF, dPMF, dPBMF}, this.d.getDomain(), discritisationLevel * 2);
        plotMFs("f", "Force Membership Functions", new T1MF_Interface[]{fNVVBMF, fNVBMF, fNBMF, fNMF, fZMF, fPMF, fPBMF, fPVBMF, fPVVBMF}, this.f.getDomain(), discritisationLevel * 2);
	}

	@Override
	public void plotControlSurface(String filePrefix) {
        plotControlSurface(true);
	}

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	private void plotMFs(String fileName, String name, T1MF_Interface[] sets, Tuple xAxisRange, int discretizationLevel) {
        JMathPlotter plotter = new JMathPlotter(12,12,12);
        for (int i=0;i<sets.length;i++) {
            plotter.plotMF(sets[i].getName(), sets[i], discretizationLevel, xAxisRange, new Tuple(0.0, 1.0), false);
        }
        plotter.show(name);

        try {
            Thread.sleep(25);
        	plotter.toGraphicFile(new File(LocalDateTime.now().format(dateTimeFormatter) + "_fuzzy_" + fileName + ".png"));
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
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

//        try {
//            Thread.sleep(25);
//        	plotter.toGraphicFile(new File(LocalDateTime.now().format(dateTimeFormatter) + "_fuzzy_surf.png"));
//        } catch (Exception ex) {
//        	ex.printStackTrace();
//        }
	}
}
