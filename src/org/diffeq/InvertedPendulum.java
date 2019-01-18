package org.diffeq;

public class InvertedPendulum {

	private double m = 0.1;	//	kg
	private double M = 1.0;	//	kg
	private double l = 2.0;	//	meter
	private double g = 9.8;	//	meter/sn2

	private double force = 0.0;	//	Newton

	private double posXDot = 0.0;	//	meter/sn
	private double posX = 0.0;		//	meter
	private double thetaDot = 0.0;	//	deg/sn
	private double theta = 0.0;		//	deg

	private void setDerivatives(double step, double[] k) {
		k[0] = step * ();
	}

	public void runRungeKutta4(double step) {

        double[] y = new double[12];
        double[] y2 = new double[12];
        double[] y3 = new double[12];
        double[] y4 = new double[12];

        y[0] = odeU;
        y[1] = odeV;
        y[2] = odeW;
        y[3] = odeP;

        double[] k1;
        double[] k2;
        double[] k3;
        double[] k4;

        calculateForceAndMomentCoefficients(true);

//  K1
        k1 = calculateDerivationsForRungeKutta();

//  K2
        for (int i = 0; i < 12; i++) {
            y2[i] = y[i] + k1[i] / 2;
        }

        setParametersForRungeKutta(y2);

        push();
        calculateForceAndMomentCoefficients(true);

        k2 = calculateDerivationsForRungeKutta();

        pop();
//  K3
        for (int i = 0; i < 12; i++) {
            y3[i] = y[i] + k2[i] / 2;
        }

        setParametersForRungeKutta(y3);

        push();
        calculateForceAndMomentCoefficients(true);

        k3 = calculateDerivationsForRungeKutta();

        pop();


//  K4
        for (int i = 0; i < 12; i++) {
            y4[i] = y[i] + k3[i];
        }

        setParametersForRungeKutta(y4);

        push();
        calculateForceAndMomentCoefficients(true);

        k4 = calculateDerivationsForRungeKutta();

        pop();

        for (int i = 0; i < 12; i++) {
            y[i] = y[i] + (k1[i] + 2.0 * k2[i] + 2.0 * k3[i] + k4[i]) / 6.0;
        }

        setParametersForRungeKutta(y);

        calculateForceAndMomentCoefficients(true);

        printSimulationStep(step);
    }


}
