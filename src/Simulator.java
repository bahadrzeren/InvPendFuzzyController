import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.JFrame;

import org.dynamics.invpend.InvertedPendulum;
import org.dynamics.invpend.State;
import org.fuzzy.cont.FuzzyControllerStandardDict;
import org.fuzzy.cont.FuzzyControllerNormalizedDict;
import org.fuzzy.cont.FuzzyControllerGaussian;
import org.fuzzy.cont.FuzzyControllerTriangular;
import org.math.plot.Plot2DPanel;

public class Simulator {

	public static NumberFormat formatter1 = new DecimalFormat("#0.0");
	public static NumberFormat formatter4 = new DecimalFormat("#0.0000");

	/*
	 * Pendulum properties.
	 */
	private static double mp = 0.2;	//	kg
	private static double mc = 1.0;	//	kg
	private static double l = 2.0;		//	meter
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
	private static double step = 0.1;	//	sn
	private static double duration = 12.0;	//	sn

	/*
	 * Disturbance.
	 */
	private static double appStart = 1.0;
	private static double appEnd = 1.1;
	private static double disturbance = 1.0;	//	Newton


	private static InvertedPendulum generateNewPendulum() {
		return new InvertedPendulum(mp, mc, l, g, fcp, fcc, new State(xInit, xdInit, tInit, tdInit));
	}

	public static void main(String[] args) {

		SystemPair[] systemPairs = new SystemPair[4];

		//	Triangular reference
		systemPairs[0] = new SystemPair();
		systemPairs[0].caption = "Triangular Reference (T)";
		systemPairs[0].color = Color.RED;
		systemPairs[0].cont = new FuzzyControllerTriangular();
		systemPairs[0].pend = generateNewPendulum();
		//	Gaussian reference
		systemPairs[1] = new SystemPair();
		systemPairs[1].color = Color.BLUE;
		systemPairs[1].caption = "Gaussian Reference (G)";
		systemPairs[1].cont = new FuzzyControllerGaussian();
		systemPairs[1].pend = generateNewPendulum();
		//	Standard dictionary reference
		systemPairs[2] = new SystemPair();
		systemPairs[2].color = new Color(60, 180, 100);
		systemPairs[2].caption = "Standard Fuzzy Transfer (S)";
		systemPairs[2].cont = new FuzzyControllerStandardDict();
		systemPairs[2].pend = generateNewPendulum();
		//	Normalized dictionary reference
		systemPairs[3] = new SystemPair();
		systemPairs[3].color = new Color(128, 64, 64);
		systemPairs[3].caption = "Normalised Standard Fuzzy Transfer (SN)";
		systemPairs[3].cont = new FuzzyControllerNormalizedDict();
		systemPairs[3].pend = generateNewPendulum();

//		for (int i = 0; i < systemPairs.length; i++) {
//			for (int ts = -40; ts < 41; ts++) {
//				for (int ds = -8; ds < 9; ds++) {
//					double output = systemPairs[i].cont.getControlInput(ts * Math.PI / 180.0, ds * Math.PI / 180.0);
//					System.out.println("Cont" + i +
//										" > Theta: " + formatter1.format(ts) + 
//										", ThetaD: " + formatter1.format(ds) + 
//										" -> Centroid defuzzification: " + formatter4.format(output));
//				}
//			}
//		}

		double time = 0.0;

		double[] times = new double[0];
		while (time < duration) {
			times = Arrays.copyOf(times, times.length + 1);
			times[times.length - 1] = time;

			for (int i = 0; i < systemPairs.length; i++) {
				double force = Math.floor(systemPairs[i].cont.getControlInput(systemPairs[i].pend.getS().getT(), systemPairs[i].pend.getS().getTd()) * 10000.0) / 10000.0;
				State prevState = systemPairs[i].pend.getS().getCopy();
				if ((time >= appStart)
						&& (time < appEnd)) {
					systemPairs[i].pend.move(step, force, disturbance);
					System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState +
							"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + " + disturbance + " -> " + systemPairs[i].pend);
	
				} else {
					systemPairs[i].pend.move(step, force, 0.0);
					System.out.println("Cont" + i + "> sec: " + formatter1.format(time) + " -> state: " + prevState + 
							"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(force) + " + 0.0 -> " + systemPairs[i].pend);
				}
			}

			time += step;
		}

		plot(systemPairs, times, duration);
	}

	private static Container plot = null;
	private static Font legendFont = new Font("Tahoma", 1, 12);
	private static Font axisFont = new Font("Tahoma", 1, 12);
	private static Font axisLightFont = new Font("Tahoma", 1, 10);

	private static void plot(SystemPair[] systemPairs, double[] times, double duration) {
		double[] rmseT = new double[systemPairs.length];
		double[] rmseTd = new double[systemPairs.length];
		double[] rmseF = new double[systemPairs.length];
		double[] rmseX = new double[systemPairs.length];
		double[] rmseXd = new double[systemPairs.length];

		double[][] t = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		t[i][j] = systemPairs[i].pend.getStateHistory()[j].getT() * 180.0 / Math.PI;
        		rmseT[i] += (t[i][j] * t[i][j]);
        	}

        plotResponse(systemPairs, "T response", "Theta (degree)", times, t, duration);

        double[][] td = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		td[i][j] = systemPairs[i].pend.getStateHistory()[j].getTd() * 180.0 / Math.PI;
        		rmseTd[i] += (td[i][j] * td[i][j]);
        	}

        plotResponse(systemPairs, "Td response", "ThetaDelta (degree/sec)", times, td, duration);

        double[][] x = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		x[i][j] = systemPairs[i].pend.getStateHistory()[j].getX();
        		rmseX[i] += (x[i][j] * x[i][j]);
        	}

        plotResponse(systemPairs, "X response", "Position (meters)", times, x, duration);

        double[][] v = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++)
        	for (int j = 0; j < times.length; j++) {
        		v[i][j] = systemPairs[i].pend.getStateHistory()[j].getXd();
        		rmseXd[i] += (v[i][j] * v[i][j]);
        	}

        plotResponse(systemPairs, "Xd response", "PositionDelta (meters/sec)", times, v, duration);

        double[][] f = new double[systemPairs.length][times.length];
        for (int i = 0; i < systemPairs.length; i++) {
        	f[i] = systemPairs[i].pend.getForceHistory();
        	for (int j = 0; j < f[i].length; j++)
        		rmseF[i] += (f[i][j] * f[i][j]);
        }

        for (int i = 0; i < systemPairs.length; i++) {
        	System.out.println(systemPairs[i].cont.getControllerName() + " - RMSE");
        	System.out.println("rmseT: " + Math.sqrt(rmseT[i] / times.length));
        	System.out.println("rmseTd: " + Math.sqrt(rmseTd[i] / times.length));
        	System.out.println("rmseF: " + Math.sqrt(rmseF[i] / times.length));
        	System.out.println("rmseX: " + Math.sqrt(rmseX[i] / times.length));
        	System.out.println("rmseXd: " + Math.sqrt(rmseXd[i] / times.length));
    	}

        plotResponse(systemPairs, "Controller Output", "Force (Newton)", times, f, duration);
	}

	private static void plotResponse(SystemPair[] systemPairs, String title, String fieldName, double[] times, double[][] values, double duration) {

		double minV = Integer.MAX_VALUE;
		double maxV = Integer.MIN_VALUE;

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
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
	        ((Plot2DPanel)plot).addLinePlot(systemPairs[i].caption, systemPairs[i].color, times, values[i]);
	        ((Plot2DPanel)plot).setFixedBounds(1, minV, maxV);
	        ((Plot2DPanel)plot).setFixedBounds(0, 0, duration);
        }

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 500);
        frame.setContentPane(plot);
        frame.setVisible(true);
	}
}
