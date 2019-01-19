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
public class EngineWrapper implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2648462321030747088L;

	private Engine engine = new Engine();
    private double x_T = 3.8;   //  Meters
    private double y_T = -5.1;   //  Meters
    private double z_T = 1.6;   //  Meters
    private double fi_T = 0.0 * Math.PI / 180;    //  Deg
    private double ps_T = -2.0 * Math.PI / 180;   //  Deg

    private double engineWorkingFactor = 1.0;

    private Aircraft aircraft = null;

    public EngineWrapper(Aircraft aircraft,
                            double thrustForce,
                            double D_EngineFrontFace,
                            double x_T,
                            double y_T,
                            double z_T,
                            double fi_T,
                            double ps_T,
                            double wf) {
        this.aircraft = aircraft;
        engine.setD_EngineFrontFace(D_EngineFrontFace);
        setEngineWorkingFactor(wf);
        setFi_T(fi_T);
        setPs_T(ps_T);
        setX_T(x_T);
        setY_T(y_T);
        setZ_T(z_T);
    }

    public double getDragForceEngine(double dynamicPressure) {
        return 0.3 *
                engine.getA_EngineFrontFace() *
                dynamicPressure *
                (1.0 - engineWorkingFactor);
    }

    public double getThrustForceEngine() {
        double thrustForce = engine.getThrustForce();
        double densityRatio = aircraft.getDensityRatio();
        double delta_t = engine.getDelta_T();
        return  thrustForce * densityRatio * delta_t;
    }

    public double getThrustForceX(double dynamicPressure) {
        return getThrustForceEngine() *
                Math.cos(fi_T) *
                Math.cos(ps_T)

                - getDragForceEngine(dynamicPressure) *
                Math.cos(aircraft.getAlpha());
    }

    public double getThrustForceY() {
        return getThrustForceEngine() *
                Math.cos(fi_T) *
                Math.sin(ps_T);
    }

    public double getThrustForceZ(double dynamicPressure) {
        return - getThrustForceEngine() *
                Math.sin(fi_T)

                - getDragForceEngine(dynamicPressure) *
                Math.sin(aircraft.getAlpha());
    }

    public double getThrustMomentX(double dynamicPressure) {
        return - getThrustForceY() * z_T + getThrustForceZ(dynamicPressure) * y_T;
    }

    public double getThrustMomentY(double dynamicPressure) {
        return getThrustForceX(dynamicPressure) * z_T - getThrustForceZ(dynamicPressure) * x_T;
    }

    public double getThrustMomentZ(double dynamicPressure) {
        return getThrustForceY() * x_T - getThrustForceX(dynamicPressure) * y_T;
    }

    /**
     * @return the engine
     */
    public Engine getEngine() {
        return engine;
    }

    /**
     * @param engine the engine to set
     */
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    /**
     * @return the z_T
     */
    public double getZ_T() {
        return z_T;
    }

    /**
     * @param z_T the z_T to set
     */
    public void setZ_T(double z_T) {
        this.z_T = z_T;
    }

    /**
     * @return the x_T
     */
    public double getX_T() {
        return x_T;
    }

    /**
     * @param x_T the x_T to set
     */
    public void setX_T(double x_T) {
        this.x_T = x_T;
    }

    /**
     * @return the y_T
     */
    public double getY_T() {
        return y_T;
    }

    /**
     * @param y_T the y_T to set
     */
    public void setY_T(double y_T) {
        this.y_T = y_T;
    }

    /**
     * @return the fi_T
     */
    public double getFi_T() {
        return fi_T;
    }

    /**
     * @param fi_T the fi_T to set
     */
    public void setFi_T(double fi_T) {
        this.fi_T = fi_T;
    }

    /**
     * @return the ps_T
     */
    public double getPs_T() {
        return ps_T;
    }

    /**
     * @param ps_T the ps_T to set
     */
    public void setPs_T(double ps_T) {
        this.ps_T = ps_T;
    }

    /**
     * @return the engineWorkingFactor
     */
    public double getEngineWorkingFactor() {
        return engineWorkingFactor;
    }

    /**
     * @param engineWorkingFactor the engineWorkingFactor to set
     */
    public void setEngineWorkingFactor(double engineWorkingFactor) {
        this.engineWorkingFactor = engineWorkingFactor;
    }
}
