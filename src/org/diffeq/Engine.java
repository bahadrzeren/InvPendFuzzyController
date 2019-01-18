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
public class Engine implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3368122571640479344L;

	private double thrustForce = 105000;    //  Newton
    private double D_EngineFrontFace = 1.88;    //  Meters
    private double delta_T = 0.0;

    public Engine() {
    }

    public double getA_EngineFrontFace() {
        return Math.PI * (D_EngineFrontFace / 2) * (D_EngineFrontFace / 2);
    }

    /**
     * @return the thrustForce
     */
    public double getThrustForce() {
        return thrustForce;
    }

    /**
     * @param thrustForce the thrustForce to set
     */
    public void setThrustForce(double thrustForce) {
        this.thrustForce = thrustForce;
    }

    /**
     * @return the D_EngineFrontFace
     */
    public double getD_EngineFrontFace() {
        return D_EngineFrontFace;
    }

    /**
     * @param D_EngineFrontFace the D_EngineFrontFace to set
     */
    public void setD_EngineFrontFace(double D_EngineFrontFace) {
        this.D_EngineFrontFace = D_EngineFrontFace;
    }

    /**
     * @return the delta_T
     */
    public double getDelta_T() {
        return delta_T;
    }

    /**
     * @param delta_T the delta_T to set
     */
    public void setDelta_T(double delta_T) {
        this.delta_T = delta_T;
    }
}
