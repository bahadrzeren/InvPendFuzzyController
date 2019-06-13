package org.fuzzy.invpend.sim;
import java.awt.Container;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.JFrame;

import org.dynamics.invpend.InvertedPendulum;
import org.dynamics.invpend.State;
import org.math.plot.Plot2DPanel;

public class Simulator {

	public static NumberFormat formatter1 = new DecimalFormat("#0.00");
	public static NumberFormat formatter4 = new DecimalFormat("#0.0000");

	/*
	 * Pendulum properties.
	 */
	private static double mp = 0.1;	//	kg
	private static double mc = 1.0;	//	kg
	private static double l = 1.0;		//	meter
	private static double g = 9.8;		//	meter/sn2
	private static double fcp = 0.0;
	private static double fcc = 0.0;

	/*
	 * Initial state.
	 */
	private static double xInit = 0.0;
	private static double xdInit = 0.0;
	private static double tInit = -30.0 * Math.PI / 180.0;
	private static double tdInit = 4.0 * Math.PI / 180.0;

	/*
	 * Test duration.
	 */
	private static double step = 0.01;	//	sn
	private static double duration = 5.0;	//	sn
	private static double plotDuration = 5.0;	//	sn

	/*
	 * Disturbance.
	 */
	private static double appStart = 1.0;
	private static double appEnd = 1.1;
	private static double disturbance = 0.0;	//	Newton


	public static InvertedPendulum generateNewPendulum() {
		return new InvertedPendulum(mp, mc, l, g, fcp, fcc, new State(xInit, xdInit, tInit, tdInit));
	}

	public static void resetPendulum(InvertedPendulum pend) {
		pend.reset(mp, mc, l, g, fcp, fcc, xInit, xdInit, tInit, tdInit);
	}

	public static void simulate(SystemPair[] systemPairs, boolean plot) {
		double time = 0.0;

		double[] times = new double[0];
		while (time < duration) {
			times = Arrays.copyOf(times, times.length + 1);
			times[times.length - 1] = time;

			for (int i = 0; i < systemPairs.length; i++) {
				double force = 0.0;
				force = systemPairs[i].cont.getControlInput(systemPairs[i].pend.getS().getT(), systemPairs[i].pend.getS().getTd());

				force = Math.floor(force * 10000.0) / 10000.0;
//				State prevState = systemPairs[i].pend.getS().getCopy();
				if ((time >= appStart)
						&& (time < appEnd)) {
					systemPairs[i].pend.move(step, force, disturbance);
//					System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState +
//							"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + " + disturbance + " -> " + systemPairs[i].pend);
	
				} else {
					systemPairs[i].pend.move(step, force, 0.0);
//					System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState + 
//							"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + 0.0 -> " + systemPairs[i].pend);
				}
			}

			time += step;
		}

		calculateError(systemPairs, times, plot);
	}


	private static Container plot = null;
	private static Font legendFont = new Font("Tahoma", 1, 12);
	private static Font axisFont = new Font("Tahoma", 1, 12);
	private static Font axisLightFont = new Font("Tahoma", 1, 10);

	private static void calculateError(SystemPair[] systemPairs, double[] times, boolean plot) {

		double[][] t = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		t[i][j] = systemPairs[i].pend.getStateHistory()[j].getT() * 180.0 / Math.PI;
        		systemPairs[i].rmseT += (t[i][j] * t[i][j]);
        	}

        if (plot)
        	plotResponse(systemPairs, "T response", "Theta (degree)", times, t);

        double[][] td = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		td[i][j] = systemPairs[i].pend.getStateHistory()[j].getTd() * 180.0 / Math.PI;
        		systemPairs[i].rmseTd += (td[i][j] * td[i][j]);
        	}

        if (plot)
        	plotResponse(systemPairs, "Td response", "ThetaDelta (degree/sec)", times, td);

        double[][] x = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		x[i][j] = systemPairs[i].pend.getStateHistory()[j].getX();
        		systemPairs[i].rmseX += (x[i][j] * x[i][j]);
        	}

        if (plot)
        	plotResponse(systemPairs, "X response", "Position (meters)", times, x);

        double[][] v = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		v[i][j] = systemPairs[i].pend.getStateHistory()[j].getXd();
        		systemPairs[i].rmseXd += (v[i][j] * v[i][j]);
        	}

        if (plot)
        	plotResponse(systemPairs, "Xd response", "PositionDelta (meters/sec)", times, v);

        double[][] f = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++) {
        	f[i] = systemPairs[i].pend.getForceHistory();
        	for (int j = 0; j < f[i].length; j++)
        		systemPairs[i].rmseF += (f[i][j] * f[i][j]);
        }

        for (int i = 0; i < systemPairs.length; i++) {
        	systemPairs[i].rmseT = Math.sqrt(systemPairs[i].rmseT / times.length);
        	systemPairs[i].rmseTd = Math.sqrt(systemPairs[i].rmseTd / times.length);
        	systemPairs[i].rmseF = Math.sqrt(systemPairs[i].rmseF / times.length);
        	systemPairs[i].rmseX = Math.sqrt(systemPairs[i].rmseX / times.length);
        	systemPairs[i].rmseXd = Math.sqrt(systemPairs[i].rmseXd / times.length);
    	}

//        for (int i = 0; i < systemPairs.length; i++) {
//        	System.out.println(systemPairs[i].cont.getControllerName() + " - RMSE");
//        	System.out.println("rmseT: " + systemPairs[i].rmseT);
//        	System.out.println("rmseTd: " + systemPairs[i].rmseTd);
//        	System.out.println("rmseF: " + systemPairs[i].rmseF);
//        	System.out.println("rmseX: " + systemPairs[i].rmseX);
//        	System.out.println("rmseXd: " + systemPairs[i].rmseXd);
//    	}

        if (plot)
        	plotResponse(systemPairs, "Controller Output", "Force (Newton)", times, f);
	}

	private static void plotResponse(SystemPair[] systemPairs, String title, String fieldName, double[] times, double[][] values) {

		int plotLength = (int) Math.round(times.length * plotDuration / duration);

		double minV = Integer.MAX_VALUE;
		double maxV = Integer.MIN_VALUE;

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < plotLength; j++) {
            	if (values[i][j] > maxV) maxV = values[i][j];
            	if (values[i][j] < minV) minV = values[i][j];
            }
        }

        if (minV > 0) minV *= 0.9; else minV *= 1.1;
        if (maxV < 0) maxV *= 0.9; else maxV *= 1.1;

		plot = new Plot2DPanel();
        ((Plot2DPanel)plot).setFont(legendFont);
        ((Plot2DPanel)plot).getAxis(0).setLabelText("Time (sec)");
        ((Plot2DPanel)plot).getAxis(0).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(0).setLightLabelFont(axisLightFont);
        ((Plot2DPanel)plot).getAxis(1).setLabelText(fieldName);
        ((Plot2DPanel)plot).getAxis(1).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(1).setLightLabelFont(axisLightFont);
        // define the legend position
        ((Plot2DPanel)plot).addLegend("SOUTH");            

        for (int i = 0; i < values.length; i++) {
	        // add a line plot to the PlotPanel
	        ((Plot2DPanel)plot).addLinePlot(systemPairs[i].caption, systemPairs[i].color, Arrays.copyOf(times, plotLength), Arrays.copyOf(values[i], plotLength));
	        ((Plot2DPanel)plot).setFixedBounds(1, minV, maxV);
	        ((Plot2DPanel)plot).setFixedBounds(0, 0, plotDuration);
        }

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setContentPane(plot);
        frame.setVisible(true);
	}
}
