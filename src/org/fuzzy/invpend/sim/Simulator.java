package org.fuzzy.invpend.sim;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

/**
 * Static class that stores simulation parameters and initial conditions and runs pendulum simulations.
 *
 * @author bahadr
 * 
 */
public class Simulator {
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

	/*
	 * Time series.
	 */
	private static double[] times = new double[(int) Math.ceil(duration / step)];

	static {
		double time = 0.0;
		for (int i = 0; i < times.length; i++) {
			times[i] = time;
			time += step;
		}
	}

	public static void simulate(ControlSystem[] controlSystems, boolean plot, String filePrefix) {

//		double time = 0.0;
//
//		double[] times = new double[0];
//
//		while (time < duration) {
//			times = Arrays.copyOf(times, times.length + 1);
//			times[times.length - 1] = time;
//			time += step;
//		}

		for (int i = 0; i < controlSystems.length; i++) {
			controlSystems[i].runSimulation(times, appStart, appEnd, disturbance, step);
		}

		if (plot) {

    		double[][] t = new double[controlSystems.length][times.length];
    		double[][] td = new double[controlSystems.length][times.length];
    		double[][] x = new double[controlSystems.length][times.length];
    		double[][] xd = new double[controlSystems.length][times.length];
    		double[][] f = new double[controlSystems.length][times.length];

    		for (int i = 0; i < controlSystems.length; i++) {
    			t[i] = Arrays.stream(controlSystems[i].getPend().getStateHistory()).mapToDouble(s -> s.getT()).toArray();
    			td[i] = Arrays.stream(controlSystems[i].getPend().getStateHistory()).mapToDouble(s -> s.getTd()).toArray();
    			x[i] = Arrays.stream(controlSystems[i].getPend().getStateHistory()).mapToDouble(s -> s.getX()).toArray();
    			xd[i] = Arrays.stream(controlSystems[i].getPend().getStateHistory()).mapToDouble(s -> s.getXd()).toArray();
    			f[i] = controlSystems[i].getPend().getForceHistory();
    		}

	    	plot(controlSystems, filePrefix + "_t", "T response", "Theta (degree)", times, t);
	    	plot(controlSystems, filePrefix + "_td", "Td response", "ThetaDelta (degree/sec)", times, td);
	    	plot(controlSystems, filePrefix + "_x", "X response", "Position (meters)", times, x);
	    	plot(controlSystems, filePrefix + "_xd", "Xd response", "PositionDelta (meters/sec)", times, xd);
	    	plot(controlSystems, filePrefix + "_f", "Controller Output", "Force (Newton)", times, f);
    	}

//		double time = 0.0;
//
//		double[] times = new double[0];
//		while (time < duration) {
//			times = Arrays.copyOf(times, times.length + 1);
//			times[times.length - 1] = time;
//
//			for (int i = 0; i < controlSystems.length; i++) {
////				double force = 0.0;
////				force = controlSystems[i].cont.calculateControlInput(controlSystems[i].pend.getS().getT(), controlSystems[i].pend.getS().getTd());
////
////				force = Math.floor(force * 10000.0) / 10000.0;
//////				State prevState = systemPairs[i].pend.getS().getCopy();
////				if ((time >= appStart)
////						&& (time < appEnd)) {
////					controlSystems[i].pend.move(step, force, disturbance);
//////					System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState +
//////							"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + " + disturbance + " -> " + systemPairs[i].pend);
////	
////				} else {
////					controlSystems[i].pend.move(step, force, 0.0);
//////					System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState + 
//////							"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + 0.0 -> " + systemPairs[i].pend);
////				}
//
//				controlSystems[i].proceedToNextStep(time, appStart, appEnd, disturbance, step);
//			}
//
//			time += step;
//		}
//
//		calculateError(controlSystems, times, plot);
	}

//	private static void calculateError(ControlSystem[] controlSystems, double[] times, boolean plot) {
//
//		double[][] t = new double[controlSystems.length][times.length];
//        for (int i = 0; i < controlSystems.length; i++)
//        	for (int j = 0; j < times.length; j++) {
//        		t[i][j] = controlSystems[i].pend.getStateHistory()[j].getT() * 180.0 / Math.PI;
//        		controlSystems[i].rmseT += (t[i][j] * t[i][j]);
//        	}
//
//        if (plot)
//        	plotResponse(controlSystems, "T response", "Theta (degree)", times, t);
//
//        double[][] td = new double[controlSystems.length][times.length];
//        for (int i = 0; i < controlSystems.length; i++)
//        	for (int j = 0; j < times.length; j++) {
//        		td[i][j] = controlSystems[i].pend.getStateHistory()[j].getTd() * 180.0 / Math.PI;
//        		controlSystems[i].rmseTd += (td[i][j] * td[i][j]);
//        	}
//
//        if (plot)
//        	plotResponse(controlSystems, "Td response", "ThetaDelta (degree/sec)", times, td);
//
//        double[][] x = new double[controlSystems.length][times.length];
//        for (int i = 0; i < controlSystems.length; i++)
//        	for (int j = 0; j < times.length; j++) {
//        		x[i][j] = controlSystems[i].pend.getStateHistory()[j].getX();
//        		controlSystems[i].rmseX += (x[i][j] * x[i][j]);
//        	}
//
//        if (plot)
//        	plotResponse(controlSystems, "X response", "Position (meters)", times, x);
//
//        double[][] v = new double[controlSystems.length][times.length];
//        for (int i = 0; i < controlSystems.length; i++)
//        	for (int j = 0; j < times.length; j++) {
//        		v[i][j] = controlSystems[i].pend.getStateHistory()[j].getXd();
//        		controlSystems[i].rmseXd += (v[i][j] * v[i][j]);
//        	}
//
//        if (plot)
//        	plotResponse(controlSystems, "Xd response", "PositionDelta (meters/sec)", times, v);
//
//        double[][] f = new double[controlSystems.length][times.length];
//        for (int i = 0; i < controlSystems.length; i++) {
//        	f[i] = controlSystems[i].pend.getForceHistory();
//        	for (int j = 0; j < f[i].length; j++)
//        		controlSystems[i].rmseF += (f[i][j] * f[i][j]);
//        }
//
//        for (int i = 0; i < controlSystems.length; i++) {
//        	controlSystems[i].rmseT = Math.sqrt(controlSystems[i].rmseT / times.length);
//        	controlSystems[i].rmseTd = Math.sqrt(controlSystems[i].rmseTd / times.length);
//        	controlSystems[i].rmseF = Math.sqrt(controlSystems[i].rmseF / times.length);
//        	controlSystems[i].rmseX = Math.sqrt(controlSystems[i].rmseX / times.length);
//        	controlSystems[i].rmseXd = Math.sqrt(controlSystems[i].rmseXd / times.length);
//    	}
//
////        for (int i = 0; i < systemPairs.length; i++) {
////        	System.out.println(systemPairs[i].cont.getControllerName() + " - RMSE");
////        	System.out.println("rmseT: " + systemPairs[i].rmseT);
////        	System.out.println("rmseTd: " + systemPairs[i].rmseTd);
////        	System.out.println("rmseF: " + systemPairs[i].rmseF);
////        	System.out.println("rmseX: " + systemPairs[i].rmseX);
////        	System.out.println("rmseXd: " + systemPairs[i].rmseXd);
////    	}
//
//        if (plot)
//        	plotResponse(controlSystems, "Controller Output", "Force (Newton)", times, f);
//	}

	private static Font legendFont = new Font("Tahoma", 1, 12);
	private static Font axisFont = new Font("Tahoma", 1, 12);
	private static Font axisLightFont = new Font("Tahoma", 1, 10);
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

	private static void plot(ControlSystem[] controlSystems, String fileName, String title, String fieldName, double[] times, double[][] values) {

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

        Container plot = new Plot2DPanel();

        ((Plot2DPanel)plot).setFont(legendFont);
        ((Plot2DPanel)plot).getAxis(0).setLabelText("Time (sec)");
        ((Plot2DPanel)plot).getAxis(0).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(0).setLightLabelFont(axisLightFont);
        ((Plot2DPanel)plot).getAxis(1).setLabelText(fieldName);
        ((Plot2DPanel)plot).getAxis(1).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(1).setLightLabelFont(axisLightFont);
        // define the legend position
        ((Plot2DPanel)plot).addLegend("SOUTH");            

        for (int i = 0; i < controlSystems.length; i++) {
	        // add a line plot to the PlotPanel
	        ((Plot2DPanel)plot).addLinePlot(controlSystems[i].getCaption(), controlSystems[i].getColor(), Arrays.copyOf(times, plotLength), Arrays.copyOf(values[i], plotLength));
	        ((Plot2DPanel)plot).setFixedBounds(1, minV, maxV);
	        ((Plot2DPanel)plot).setFixedBounds(0, 0, plotDuration);
        }

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setContentPane(plot);
        frame.setVisible(true);

        try {
        	Thread.sleep(50);
        	((Plot2DPanel)plot).toGraphicFile(new File(LocalDateTime.now().format(dateTimeFormatter) + "_" + fileName + "_resp.png"));
        	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
	}
}
