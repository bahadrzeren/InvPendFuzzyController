import java.awt.Container;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.JFrame;

import org.dynamics.invpend.InvertedPendulum;
import org.dynamics.invpend.State;
import org.fuzzy.cont.FuzzyControllerTriangular;
import org.fuzzy.cont.FuzzyInvPendController;
import org.math.plot.Plot2DPanel;

public class Simulator {

	public static NumberFormat formatter1 = new DecimalFormat("#0.0");
	public static NumberFormat formatter4 = new DecimalFormat("#0.0000");

	public static void main(String[] args) {

		double mp = 0.2;	//	kg
		double mc = 1.0;	//	kg
		double l = 2.0;		//	meter
		double g = 9.8;		//	meter/sn2
		double fcp = 0.0;
		double fcc = 0.0;

		double x = 0.0;
		double v = 0.0;
		double t = - 30.0 * Math.PI / 180.0;
		double td = 2.0 * Math.PI / 180.0;

		State s = new State(x, v, t, td);

		InvertedPendulum p = new InvertedPendulum(mp, mc, l, g, fcp, fcc, s);

		double step = 0.1;	//	sn
		double duration = 10.0;	//	sn

		FuzzyInvPendController contTriangularRef = new FuzzyControllerTriangular();
////		contTriangularRef.plotControlSurface();
//		FuzzyInvPendController contGaussianRef = new FuzzyControllerGaussian();
////		contGaussianRef.plotControlSurface();
//		FuzzyInvPendController contGaussianSft = new FuzzyControllerDictSft();
////		contGaussianSft.plotControlSurface();
//		FuzzyInvPendController contGaussianSftSn = new FuzzyControllerDictSftSn();
////		contGaussianSftSn.plotControlSurface();

		double time = 0.0;

		double appStart = 1.0;
		double appEnd = 1.1;
		double f = 0.0;	//	newton

		double[] times = new double[0];
		while (time < duration) {
			times = Arrays.copyOf(times, times.length + 1);
			times[times.length - 1] = time;

			double extF = 0.0;

			if ((time >= appStart)
					&& (time < appEnd))
				extF = f;

			double controllerOutput = Math.floor(contTriangularRef.getControlInput(p.getS().getT(), p.getS().getTd()) * 10000.0) / 10000.0;

			controllerOutput += extF;

			State prevState = p.getS().getCopy();
			p.move(step, controllerOutput);
			System.out.println("sec: " + formatter1.format(time) + " -> state: " + prevState + 
								"; sec: " + formatter1.format(time + step) + " -> output: " + formatter4.format(controllerOutput) + " + " + extF + " -> " + p);

			time += step;
		}

		plotT(contTriangularRef, times, p.getHistory(), 40.0 * Math.PI /180.0, 10.0, duration);
	}

	private static Container plot = null;
	private static Font legendFont = new Font("SansSerif",1,17);
	private static Font axisFont = new Font("SansSerif",1,17);
	private static Font axisLightFont = new Font("SansSerif",1,15);

	private static void plotT(FuzzyInvPendController cont, double[] times, State[] s, double tLimit, double xLimit, double duration) {
//		JMathPlotter plotter = new JMathPlotter(12,12,12);
//		plotter.plotInputOutput(cont.getControllerName() + " T response", t, new Tuple(- tLimit, tLimit), new Tuple(0, duration));
//		plotter.plotInputOutput(cont.getControllerName() + " X response", x, new Tuple(- xLimit, xLimit), new Tuple(0, duration));
    
        double[] t = new double[s.length];
        for (int i = 0; i < s.length; i++)
			t[i] = s[i].getT();

        plotT(cont.getControllerName() + " T response", "Theta", times, t, tLimit, duration);

        double[] x = new double[s.length];
        for (int i = 0; i < s.length; i++)
			x[i] = s[i].getT();

        plotT(cont.getControllerName() + " X response", "X", times, x, xLimit, duration);
	}

	private static void plotT(String title, String fieldName, double[] times, double[] values, double limit, double duration) {

        plot = new Plot2DPanel();
        ((Plot2DPanel)plot).setFont(legendFont);
        ((Plot2DPanel)plot).getAxis(0).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(0).setLightLabelFont(axisLightFont);
        ((Plot2DPanel)plot).getAxis(1).setLabelFont(axisFont);
        ((Plot2DPanel)plot).getAxis(1).setLightLabelFont(axisLightFont);
        // define the legend position
        ((Plot2DPanel)plot).addLegend("SOUTH");            

        // add a line plot to the PlotPanel
        ((Plot2DPanel)plot).addLinePlot(fieldName, times, values);
        ((Plot2DPanel)plot).setFixedBounds(1, -limit, limit);
        ((Plot2DPanel)plot).setFixedBounds(0, 0, duration);

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
	}
}
