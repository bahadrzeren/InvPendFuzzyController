/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.diffeq;

import java.io.Serializable;


/**
 *
 * @author bahadir
 */
public class EngineFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -212867442982604912L;

	private EngineWrapper engineWrapper1 = null;
	private EngineWrapper engineWrapper2 = null;

    public EngineFacade() {
    }

    public void setEngine1(Aircraft aircraft,
            double thrustForce,
            double D_EngineFrontFace,
            double x_T,
            double y_T,
            double z_T,
            double fi_T,
            double ps_T,
            double wf) {
    	engineWrapper1 = new EngineWrapper(aircraft,
			                                thrustForce,
			                                D_EngineFrontFace,
			                                x_T,
			                                y_T,
			                                z_T,
			                                fi_T,
			                                ps_T,
                                            wf);
    }

    public void setEngine2(Aircraft aircraft,
            double thrustForce,
            double D_EngineFrontFace,
            double x_T,
            double y_T,
            double z_T,
            double fi_T,
            double ps_T,
            double wf) {
    	engineWrapper2 = new EngineWrapper(aircraft,
			                                thrustForce,
			                                D_EngineFrontFace,
			                                x_T,
			                                y_T,
			                                z_T,
			                                fi_T,
			                                ps_T,
                                            wf);
    }

    public double getThrustForceX(double dynamicPressure) {
        double res = 0;
        res = engineWrapper1.getThrustForceX(dynamicPressure) +
                engineWrapper2.getThrustForceX(dynamicPressure);
        return res;
    }

    public double getThrustForceY() {
        double res = 0;
        res = engineWrapper1.getThrustForceY() +
                engineWrapper2.getThrustForceY();
        return res;
    }

    public double getThrustForceZ(double dynamicPressure) {
        double res = 0;
        res = engineWrapper1.getThrustForceZ(dynamicPressure) +
                engineWrapper2.getThrustForceZ(dynamicPressure);
        return res;
    }

    public double getThrustMomentX(double dynamicPressure) {
        double res = 0;
        res = engineWrapper1.getThrustMomentX(dynamicPressure) +
                engineWrapper2.getThrustMomentX(dynamicPressure);
        return res;
    }

    public double getThrustMomentY(double dynamicPressure) {
        double res = 0;
        res = engineWrapper1.getThrustMomentY(dynamicPressure) +
                engineWrapper2.getThrustMomentY(dynamicPressure);
        return res;
    }

    public double getThrustMomentZ(double dynamicPressure) {
        double res = 0;
        res = engineWrapper1.getThrustMomentZ(dynamicPressure) +
                engineWrapper2.getThrustMomentZ(dynamicPressure);
        return res;
    }

    public EngineWrapper getEngineWrapper(int indexOfEngineWrapper) {
    	if (indexOfEngineWrapper == 0)
            return engineWrapper1;
    	else if (indexOfEngineWrapper == 1)
    		return engineWrapper2;
        else
            return null;
    }
}
