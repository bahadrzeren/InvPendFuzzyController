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
public class Aircraft implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9148825978580831511L;

	private double altitude = 4000.0;  //  Meters

    private static double[] pressures = new double[] {101325.0, //  Newton / m2
                                    95460.0, 89874.0, 84555.0, 79495.0, 74682.0,
                                    70108.0, 65764.0, 61640.0, 57728.0, 54019.0,
                                    50506.0, 47181.0, 44034.0,41060.0, 38251.0,
                                    35599.0, 33099.0, 30742.0, 28523.0, 26436.0,
                                    24474.0, 22632.0, 20916.0, 19330.0, 17864.0,
                                    16510.0, 15218.0, 14101.0, 13032.0, 12044.0,
                                    11131.0, 10287.0, 9507.0, 8787.0, 8121.0,
                                    7505.0, 6936.0, 6410.0, 5924.0, 5475.0};

    private static double[] densityRatios = new double[] {1.0,
                                    0.9529, 0.9075, 0.8637, 0.8216, 0.7811,
                                    0.7421, 0.7047, 0.6687, 0.6341, 0.6009,
                                    0.5691, 0.5385, 0.5093, 0.4812, 0.4544,
                                    0.4287, 0.4042, 0.3807, 0.3583, 0.3369,
                                    0.3165, 0.2971, 0.2746, 0.2537, 0.2345,
                                    0.2167, 0.2003, 0.1851, 0.1711, 0.1581,
                                    0.1461, 0.1350, 0.1248, 0.1153, 0.1066,
                                    0.09851, 0.09104, 0.08414, 0.07776, 0.07187};

    private static double[] densities = new double[] {1.2250,  //  Kg / m3
                                    1.1673, 1.1116, 1.0581, 1.0065, 0.95686,
                                    0.90912, 0.86323, 0.81913, 0.77677, 0.73612,
                                    0.69711, 0.65970, 0.62384, 0.58950, 0.55662,
                                    0.52517, 0.49509, 0.46635, 0.43890, 0.41271,
                                    0.38773, 0.36392, 0.33633, 0.31083, 0.28726,
                                    0.26548, 0.24536, 0.22675, 0.20956, 0.19367,
                                    0.17899, 0.16542, 0.15288, 0.14129, 0.13058,
                                    0.12068, 0.11153, 0.10307, 0.09525, 0.08803};

    private static double[] speedsOfSound = new double[] {340.3,    //  m/sec
                                    338.4, 336.4, 334.5, 332.5, 330.6,
                                    328.6, 326.6, 324.6, 322.6, 320.5,
                                    318.5, 316.4, 314.4, 312.4, 310.2,
                                    308.1, 305.9, 303.8, 301.6, 299.5,
                                    297.3, 295.1, 295.1, 295.1, 295.1,
                                    295.1, 295.1, 295.1, 295.1, 295.1,
                                    295.1, 295.1, 295.1, 295.1, 295.1,
                                    295.1, 295.1, 295.1, 295.1, 295.1};

    private double getPressure() {  //  Newton/meters square
        int arrayIndex = (int) Math.floor(altitude / 500.0);
        if (arrayIndex < 0) return pressures[0];
        if (arrayIndex >= pressures.length) return pressures[pressures.length - 1];
        return pressures[arrayIndex] +
                (altitude - arrayIndex * 500.0) *
                (pressures[arrayIndex + 1] - pressures[arrayIndex]) / 500.0;
    }

	public double getDensityRatio() {
        int arrayIndex = (int) Math.floor(altitude / 500.0);
        if (arrayIndex < 0) return densityRatios[0];
        if (arrayIndex >= densityRatios.length) return densityRatios[densityRatios.length - 1];
        return densityRatios[arrayIndex] +
                (altitude - arrayIndex * 500.0) *
                (densityRatios[arrayIndex + 1] - densityRatios[arrayIndex]) / 500.0;
	}

	private double getDensity() {
        int arrayIndex = (int) Math.floor(altitude / 500.0);
        if (arrayIndex < 0) return densities[0];
        if (arrayIndex >= densities.length) return densities[densities.length - 1];
        return densities[arrayIndex] +
                (altitude - arrayIndex * 500.0) *
                (densities[arrayIndex + 1] - densities[arrayIndex]) / 500.0;
	}

	private double getSpeedOfSound() {
        int arrayIndex = (int) Math.floor(altitude / 500.0);
        if (arrayIndex < 0) return speedsOfSound[0];
        if (arrayIndex >= speedsOfSound.length) return speedsOfSound[speedsOfSound.length - 1];
        return speedsOfSound[arrayIndex] +
                (altitude - arrayIndex * 500.0) *
                (speedsOfSound[arrayIndex + 1] - speedsOfSound[arrayIndex]) / 500.0;
	}

    private double dynamicPressureByMachNumber(double mach) {
        return (1.4 * getPressure() * mach * mach) / 2.0;  //  Dynamic Pressure
    }

    public double dynamicPressureByTrueAirVelocity(double velocity) {
        return (getDensity() * velocity * velocity) / 2.0;  //  Dynamic Pressure
    }

    private double weight = 500000.0; //  Newtons
    private double S_w = 105.0;    //  Square Meters
    private double AR = 8.12;
    private double c_Wing = 4.05;  //  Meter
    private double b_Wing = 29.2;  //  Meters

    private double Ixx = 828253.5;  //  Kg Square Meters
    private double Iyy = 2552412.6;  //  Kg Square Meters
    private double Izz = 3239451.5;  //  Kg Square Meters
    private double Ixz = 60728.0;  //  Kg Square Meters

    private double mach = 0.5;
    private double alpha = 0.0 * Math.PI / 180.0;   // Deg
    private double beta = 0.0 * Math.PI / 180.0;    // Deg
    private double gama = 0.0 * Math.PI / 180.0;    // Deg
    private double delta_e = 4.0 * Math.PI / 180.0; // Deg
    private double ih = 0.0 * Math.PI / 180.0;      //  Deg
    private double delta_a = 0.0 * Math.PI / 180.0; //  Deg
    private double delta_r = 0.0 * Math.PI / 180.0; //  Deg
/*
*
* Aerodynamic data
*
*/
    private double _C_D0 = 0.0203;
    private double OswaldEfficiency = 0.85;        //  Oswald Efficiency
    private double C_L0 = 0.2165;
    private double C_L_Alpha = 5.4039;      //  1 / Rad
    private double C_L_ih = 1.0652;         //  1 / Rad
    private double C_L_Delta_e = 0.4981;    //  1 / Rad
    private double C_m0 = 0.1093;
    private double C_m_Alpha = - 1.7936;    //  1 / Rad
    private double C_m_ih = - 3.8246;       //  1 / Rad
    private double C_m_Delta_e = - 1.7884;  //  1 / Rad
    private double C_y_0 = 0.0;
    private double C_y_Beta = - 0.7316;     //  1 / Rad
    private double C_y_Delta_a = 0.0;       //  1 / Rad
    private double C_y_Delta_r = 0.2513;    //  1 / Rad
    private double C_l0 = 0.0;
    private double C_l_Beta = - 0.2195;     //  1 / Rad
    private double C_l_Delta_a = 0.0677;    //  1 / Rad
    private double C_l_Delta_r = 0.0153;    //  1 / Rad
    private double C_n0 = 0.0;
    private double C_n_Beta = 0.0511;       //  1 / Rad
    private double C_n_Delta_a = 0.0;       //  1 / Rad
    private double C_n_Delta_r = - 0.1382;  //  1 / Rad

/*
*
* Unsteady aerodynamic data
*
*/
    private double C_L_Alpha_Dot = 2.5740;
    private double C_L_q = 7.9072;
    private double C_D_Alpha_Dot = 0.0;
    private double C_D_q = 0.0;
    private double C_m_Alpha_Dot = -9.2423;
    private double C_m_q = -29.4652;

    private double C_l_Beta_Dot = -0.0028;
    private double C_l_p = -0.4261;
    private double C_l_r = 0.1595;

    private double C_y_Beta_Dot = -0.0535;
    private double C_y_p = -0.2962;
    private double C_y_r = 0.4878;

    private double C_n_Beta_Dot = -0.0239;
    private double C_n_p = -0.0339;
    private double C_n_r = -0.2394;


/*
*
* Dynamic parameters
*
*/
    private double C_L = 0.0;
    private double C_D_Alpha = 0.0;
    private double C_D0 = 0.0;
    private double C_D = 0.0;
    private double C_y = 0.0;
    private double C_l = 0.0;
    private double C_m = 0.0;
    private double C_n = 0.0;

    private double odeU = 0.0;
    private double odeV = 0.0;
    private double odeW = 0.0;
    private double odeP = 0.0;
    private double odeQ = 0.0;
    private double odeR = 0.0;
    private double odeFi = 0.0;
    private double odeTheta = 0.0;
    private double odePsi = 0.0;
    private double odeX = 0.0;
    private double odeY = 0.0;
    private double odeZ = 0.0;

    private double alpha_Dot = 0.0;
    private double beta_Dot = 0.0;



    private EngineFacade engineFacade = new EngineFacade();

    private boolean findDelta_e = false;
	private boolean findBeta = false;
    private boolean findAlpha = false;
    private boolean findIh = false;
    private boolean findDelta_a = false;
    private boolean findDelta_r = false;

    public boolean isFindDelta_e() {
		return findDelta_e;
	}

	public boolean isFindBeta() {
		return findBeta;
	}

	public boolean isFindAlpha() {
		return findAlpha;
	}

	public boolean isFindIh() {
		return findIh;
	}

	public boolean isFindDelta_a() {
		return findDelta_a;
	}

	public boolean isFindDelta_r() {
		return findDelta_r;
	}

    public Aircraft() {
    }

    public void setEngine1(double thrustForce,
            double D_EngineFrontFace,
            double x_T,
            double y_T,
            double z_T,
            double fi_T,
            double ps_T,
            double wf) {
		engineFacade.setEngine1(this,
				                thrustForce,
				                D_EngineFrontFace,
				                x_T,
				                y_T,
				                z_T,
				                fi_T,
				                ps_T,
                                wf);
    }

    public void setEngine2(double thrustForce,
            double D_EngineFrontFace,
            double x_T,
            double y_T,
            double z_T,
            double fi_T,
            double ps_T,
            double wf) {
		engineFacade.setEngine2(this,
				                thrustForce,
				                D_EngineFrontFace,
				                x_T,
				                y_T,
				                z_T,
				                fi_T,
				                ps_T,
                                wf);
    }

    private void calculateForceAndMomentCoefficients(boolean unsteady) {

        if (unsteady) {
            beta_Dot = Math.asin(odeV / getV_Air_T()) - beta;
            beta = Math.asin(odeV / getV_Air_T());
            alpha_Dot = Math.asin(odeW / (getV_Air_T() * Math.cos(beta))) - alpha;
            alpha = Math.asin(odeW / (getV_Air_T() * Math.cos(beta)));

            altitude = - odeZ;
        }

        C_L = C_L0 +
                C_L_Alpha * alpha +
                C_L_Delta_e * delta_e +
                C_L_ih * ih;

        C_D_Alpha = 2 * C_L * C_L_Alpha / (Math.PI * OswaldEfficiency * AR);

        C_D0 = _C_D0 +
                (C_L * C_L) / (Math.PI * OswaldEfficiency * AR) -
                C_D_Alpha * alpha;

        C_D = C_D0 + C_D_Alpha * alpha;

        C_y = C_y_0 +
                C_y_Beta * beta +
                C_y_Delta_a * delta_a +
                C_y_Delta_r * delta_r;

        C_l = C_l0 +
                C_l_Beta * beta +
                C_l_Delta_a * delta_a +
                C_l_Delta_r * delta_r;

        C_m = C_m0 +
                C_m_Alpha * alpha +
                C_m_Delta_e * delta_e +
                C_m_ih * ih;

        C_n = C_n0 +
                C_n_Beta * beta +
                C_n_Delta_a * delta_a +
                C_n_Delta_r * delta_r;

        if (unsteady) {

            C_L += C_L_Alpha_Dot * (alpha_Dot * c_Wing / (2 * getV_Air_T())) +
                    C_L_q * (odeQ * c_Wing / (2 * getV_Air_T()));

            C_D += C_D_Alpha_Dot * (alpha_Dot * c_Wing / (2 * getV_Air_T())) +
                    C_D_q * (odeQ * c_Wing / (2 * getV_Air_T()));

            C_y += C_y_Beta_Dot * (beta_Dot * b_Wing / (2 * getV_Air_T())) +
                    C_y_p * (odeP * b_Wing / (2 * getV_Air_T())) +
                    C_y_r * (odeR * b_Wing / (2 * getV_Air_T()));

            C_l += C_l_Beta_Dot * (beta_Dot * b_Wing / (2 * getV_Air_T())) +
                    C_l_p * (odeP * b_Wing / (2 * getV_Air_T())) +
                    C_l_r * (odeR * b_Wing / (2 * getV_Air_T()));

            C_m += C_m_Alpha_Dot * (alpha_Dot * c_Wing / (2 * getV_Air_T())) +
                    C_m_q * (odeQ * c_Wing / (2 * getV_Air_T()));

            C_n += C_n_Beta_Dot * (beta_Dot * b_Wing / (2 * getV_Air_T())) +
                    C_n_p * (odeP * b_Wing / (2 * getV_Air_T())) +
                    C_n_r * (odeR * b_Wing / (2 * getV_Air_T()));
        }

        /*
         *
         * Unsteady calculations
         *
         */
/*
        C_L_Unsteady = C_L0 +
                        C_L_Alpha * alpha +
                        C_L_Delta_e * delta_e +
                        C_L_ih * ih +
                        C_L_Alpha_Dot * (alpha_Dot * c_Wing / (2 * V_air_T)) +
                        C_L_q * (Q * c_Wing / (2 * V_air_T));

        C_D_Alpha_Unsteady = 2 * C_L_Unsteady * C_L_Alpha / (Math.PI * OswaldEfficiency * AR);
        C_D0_Unsteady = _C_D0 + 
                            (C_L_Unsteady * C_L_Unsteady) / (Math.PI * OswaldEfficiency * AR) -
                            C_D_Alpha_Unsteady * alpha;
        C_D_Unsteady = C_D0_Unsteady + 
                        C_D_Alpha_Unsteady * alpha +
                        C_D_Alpha_Dot * (alpha_Dot * c_Wing / (2 * V_air_T)) +
                        C_D_q * (Q * c_Wing / (2 * V_air_T));

        C_y_Unsteady = C_y_0 + C_y_Beta * beta + C_y_Delta_a * delta_a + C_y_Delta_r * delta_r +
                        C_y_Beta_Dot * (beta_Dot * b_Wing / (2 * V_air_T)) +
                        C_y_p * (P * b_Wing / (2 * V_air_T)) +
                        C_y_r * (R * b_Wing / (2 * V_air_T));
        C_l_Unsteady = C_l0 + C_l_Beta * beta + C_l_Delta_a * delta_a + C_l_Delta_r * delta_r +
                        C_l_Beta_Dot * (beta_Dot * b_Wing / (2 * V_air_T)) +
                        C_l_p * (P * b_Wing / (2 * V_air_T)) +
                        C_l_r * (R * b_Wing / (2 * V_air_T));
        C_m_Unsteady = C_m0 + C_m_Alpha * alpha + C_m_Delta_e * delta_e + C_m_ih * ih +
                        C_m_Alpha_Dot * (alpha_Dot * c_Wing / (2 * V_air_T)) +
                        C_m_q * (Q * c_Wing / (2 * V_air_T));
        C_n_Unsteady = C_n0 + C_n_Beta * beta + C_n_Delta_a * delta_a + C_n_Delta_r * delta_r +
                        C_n_Beta_Dot * (beta_Dot * b_Wing / (2 * V_air_T)) +
                        C_n_p * (P * b_Wing / (2 * V_air_T)) +
                        C_n_r * (R * b_Wing / (2 * V_air_T));
*/
    }

    private double getGravityXForce() {
        return - weight * Math.sin(odeTheta);
    }

    private double getGravityYForce() {
        return weight * Math.sin(odeFi) * Math.cos(odeTheta);
    }

    private double getGravityZForce() {
        return weight * Math.cos(odeFi) * Math.cos(odeTheta);
    }

    public double getLiftForceAero(double dynamicPressure) {
        return C_L * dynamicPressure * S_w;
    }

    public double getDragForceAero(double dynamicPressure) {
        return C_D * dynamicPressure * S_w;
    }

    private double getAeroXForce(double dynamicPressure) {
        return - getDragForceAero(dynamicPressure) * Math.cos(alpha)
               + getLiftForceAero(dynamicPressure) * Math.sin(alpha);
    }

    private double getAeroYForce(double dynamicPressure) {
        return C_y * dynamicPressure * S_w;
    }

    private double getAeroZForce(double dynamicPressure) {
        return - getDragForceAero(dynamicPressure) * Math.sin(alpha)
               - getLiftForceAero(dynamicPressure) * Math.cos(alpha);
    }

    private double getAeroMomentX(double dynamicPressure) {
        return C_l * dynamicPressure * S_w * b_Wing;
    }

    public double getAeroMomentY(double dynamicPressure) {
        return C_m * dynamicPressure * S_w * c_Wing;
    }

    private double getAeroMomentZ(double dynamicPressure) {
        return C_n * dynamicPressure * S_w * b_Wing;
    }

    private double getTotalXForce(double dynamicPressure) {
        return getGravityXForce() + 
                getAeroXForce(dynamicPressure) +
                engineFacade.getThrustForceX(dynamicPressure);
    }

    private double getTotalYForce(double dynamicPressure) {
        return getGravityYForce() + 
                getAeroYForce(dynamicPressure) +
                engineFacade.getThrustForceY();
    }

    private double getTotalZForce(double dynamicPressure) {
        return getGravityZForce() + 
                getAeroZForce(dynamicPressure) +
                engineFacade.getThrustForceZ(dynamicPressure);
    }

    public double getTotalXMoment(double dynamicPressure) {
        return getAeroMomentX(dynamicPressure) +
                engineFacade.getThrustMomentX(dynamicPressure);
    }

    public double getTotalYMoment(double dynamicPressure) {
        return getAeroMomentY(dynamicPressure) +
                engineFacade.getThrustMomentY(dynamicPressure);
    }

    public double getTotalZMoment(double dynamicPressure) {
        return getAeroMomentZ(dynamicPressure) +
                engineFacade.getThrustMomentZ(dynamicPressure);
    }

    private double getFunctionResultForTrim(int index, double dynamicPressure) {
        if (index == 0) return getTotalXForce(dynamicPressure);
        else if (index == 1) return getTotalYForce(dynamicPressure);
        else if (index == 2) return getTotalZForce(dynamicPressure);
        else if (index == 3) return getTotalXMoment(dynamicPressure);
        else if (index == 4) return getTotalYMoment(dynamicPressure);
        else if (index == 5) return getTotalZMoment(dynamicPressure);
        else return 0;
    }

    /**
     * @return the altitude
     */
    public double getAltitude() {
        return altitude;
    }

    /**
     * @param altitude the altitude to set
     */
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the S_w
     */
    public double getS_w() {
        return S_w;
    }

    /**
     * @param S_w the S_w to set
     */
    public void setS_w(double S_w) {
        this.S_w = S_w;
    }

    /**
     * @return the AR
     */
    public double getAR() {
        return AR;
    }

    /**
     * @param AR the AR to set
     */
    public void setAR(double AR) {
        this.AR = AR;
    }

    /**
     * @return the c_Wing
     */
    public double getC_Wing() {
        return c_Wing;
    }

    /**
     * @param c_Wing the c_Wing to set
     */
    public void setC_Wing(double c_Wing) {
        this.c_Wing = c_Wing;
    }

    /**
     * @return the b_Wing
     */
    public double getB_Wing() {
        return b_Wing;
    }

    /**
     * @param b_Wing the b_Wing to set
     */
    public void setB_Wing(double b_Wing) {
        this.b_Wing = b_Wing;
    }

    /**
     * @return the Ixx
     */
    public double getIxx() {
        return Ixx;
    }

    /**
     * @param Ixx the Ixx to set
     */
    public void setIxx(double Ixx) {
        this.Ixx = Ixx;
    }

    /**
     * @return the Iyy
     */
    public double getIyy() {
        return Iyy;
    }

    /**
     * @param Iyy the Iyy to set
     */
    public void setIyy(double Iyy) {
        this.Iyy = Iyy;
    }

    /**
     * @return the Izz
     */
    public double getIzz() {
        return Izz;
    }

    /**
     * @param Izz the Izz to set
     */
    public void setIzz(double Izz) {
        this.Izz = Izz;
    }

    /**
     * @return the Ixz
     */
    public double getIxz() {
        return Ixz;
    }

    /**
     * @param Ixz the Ixz to set
     */
    public void setIxz(double Ixz) {
        this.Ixz = Ixz;
    }

    /**
     * @return the _C_D0
     */
    public double get_C_D0() {
        return _C_D0;
    }

    /**
     * @param _C_D0 the _C_D0 to set
     */
    public void set_C_D0(double _C_D0) {
        this._C_D0 = _C_D0;
    }

    /**
     * @return the OswaldEfficiency
     */
    public double getOswaldEfficiency() {
        return OswaldEfficiency;
    }

    /**
     * @param OswaldEfficiency the OswaldEfficiency to set
     */
    public void setOswaldEfficiency(double OswaldEfficiency) {
        this.OswaldEfficiency = OswaldEfficiency;
    }

    /**
     * @return the C_L0
     */
    public double getC_L0() {
        return C_L0;
    }

    /**
     * @param C_L0 the C_L0 to set
     */
    public void setC_L0(double C_L0) {
        this.C_L0 = C_L0;
    }

    /**
     * @return the C_L_Alpha
     */
    public double getC_L_Alpha() {
        return C_L_Alpha;
    }

    /**
     * @param C_L_Alpha the C_L_Alpha to set
     */
    public void setC_L_Alpha(double C_L_Alpha) {
        this.C_L_Alpha = C_L_Alpha;
    }

    /**
     * @return the C_L_ih
     */
    public double getC_L_ih() {
        return C_L_ih;
    }

    /**
     * @param C_L_ih the C_L_ih to set
     */
    public void setC_L_ih(double C_L_ih) {
        this.C_L_ih = C_L_ih;
    }

    /**
     * @return the C_L_Delta_e
     */
    public double getC_L_Delta_e() {
        return C_L_Delta_e;
    }

    /**
     * @param C_L_Delta_e the C_L_Delta_e to set
     */
    public void setC_L_Delta_e(double C_L_Delta_e) {
        this.C_L_Delta_e = C_L_Delta_e;
    }

    /**
     * @return the C_m0
     */
    public double getC_m0() {
        return C_m0;
    }

    /**
     * @param C_m0 the C_m0 to set
     */
    public void setC_m0(double C_m0) {
        this.C_m0 = C_m0;
    }

    /**
     * @return the C_m_Alpha
     */
    public double getC_m_Alpha() {
        return C_m_Alpha;
    }

    /**
     * @param C_m_Alpha the C_m_Alpha to set
     */
    public void setC_m_Alpha(double C_m_Alpha) {
        this.C_m_Alpha = C_m_Alpha;
    }

    /**
     * @return the C_m_ih
     */
    public double getC_m_ih() {
        return C_m_ih;
    }

    /**
     * @param C_m_ih the C_m_ih to set
     */
    public void setC_m_ih(double C_m_ih) {
        this.C_m_ih = C_m_ih;
    }

    /**
     * @return the C_m_Delta_e
     */
    public double getC_m_Delta_e() {
        return C_m_Delta_e;
    }

    /**
     * @param C_m_Delta_e the C_m_Delta_e to set
     */
    public void setC_m_Delta_e(double C_m_Delta_e) {
        this.C_m_Delta_e = C_m_Delta_e;
    }

    /**
     * @return the C_y_0
     */
    public double getC_y_0() {
        return C_y_0;
    }

    /**
     * @param C_y_0 the C_y_0 to set
     */
    public void setC_y_0(double C_y_0) {
        this.C_y_0 = C_y_0;
    }

    /**
     * @return the C_y_Beta
     */
    public double getC_y_Beta() {
        return C_y_Beta;
    }

    /**
     * @param C_y_Beta the C_y_Beta to set
     */
    public void setC_y_Beta(double C_y_Beta) {
        this.C_y_Beta = C_y_Beta;
    }

    /**
     * @return the C_y_Delta_a
     */
    public double getC_y_Delta_a() {
        return C_y_Delta_a;
    }

    /**
     * @param C_y_Delta_a the C_y_Delta_a to set
     */
    public void setC_y_Delta_a(double C_y_Delta_a) {
        this.C_y_Delta_a = C_y_Delta_a;
    }

    /**
     * @return the C_y_Delta_r
     */
    public double getC_y_Delta_r() {
        return C_y_Delta_r;
    }

    /**
     * @param C_y_Delta_r the C_y_Delta_r to set
     */
    public void setC_y_Delta_r(double C_y_Delta_r) {
        this.C_y_Delta_r = C_y_Delta_r;
    }

    /**
     * @return the C_l0
     */
    public double getC_l0() {
        return C_l0;
    }

    /**
     * @param C_l0 the C_l0 to set
     */
    public void setC_l0(double C_l0) {
        this.C_l0 = C_l0;
    }

    /**
     * @return the C_l_Beta
     */
    public double getC_l_Beta() {
        return C_l_Beta;
    }

    /**
     * @param C_l_Beta the C_l_Beta to set
     */
    public void setC_l_Beta(double C_l_Beta) {
        this.C_l_Beta = C_l_Beta;
    }

    /**
     * @return the C_l_Delta_a
     */
    public double getC_l_Delta_a() {
        return C_l_Delta_a;
    }

    /**
     * @param C_l_Delta_a the C_l_Delta_a to set
     */
    public void setC_l_Delta_a(double C_l_Delta_a) {
        this.C_l_Delta_a = C_l_Delta_a;
    }

    /**
     * @return the C_l_Delta_r
     */
    public double getC_l_Delta_r() {
        return C_l_Delta_r;
    }

    /**
     * @param C_l_Delta_r the C_l_Delta_r to set
     */
    public void setC_l_Delta_r(double C_l_Delta_r) {
        this.C_l_Delta_r = C_l_Delta_r;
    }

    /**
     * @return the C_n0
     */
    public double getC_n0() {
        return C_n0;
    }

    /**
     * @param C_n0 the C_n0 to set
     */
    public void setC_n0(double C_n0) {
        this.C_n0 = C_n0;
    }

    /**
     * @return the C_n_Beta
     */
    public double getC_n_Beta() {
        return C_n_Beta;
    }

    /**
     * @param C_n_Beta the C_n_Beta to set
     */
    public void setC_n_Beta(double C_n_Beta) {
        this.C_n_Beta = C_n_Beta;
    }

    /**
     * @return the C_n_Delta_a
     */
    public double getC_n_Delta_a() {
        return C_n_Delta_a;
    }

    /**
     * @param C_n_Delta_a the C_n_Delta_a to set
     */
    public void setC_n_Delta_a(double C_n_Delta_a) {
        this.C_n_Delta_a = C_n_Delta_a;
    }

    /**
     * @return the C_n_Delta_r
     */
    public double getC_n_Delta_r() {
        return C_n_Delta_r;
    }

    /**
     * @param C_n_Delta_r the C_n_Delta_r to set
     */
    public void setC_n_Delta_r(double C_n_Delta_r) {
        this.C_n_Delta_r = C_n_Delta_r;
    }

    /**
     * @return the C_L_Alpha_Dot
     */
    public double getC_L_Alpha_Dot() {
        return C_L_Alpha_Dot;
    }

    /**
     * @param C_L_Alpha_Dot the C_L_Alpha_Dot to set
     */
    public void setC_L_Alpha_Dot(double C_L_Alpha_Dot) {
        this.C_L_Alpha_Dot = C_L_Alpha_Dot;
    }

    /**
     * @return the C_L_q
     */
    public double getC_L_q() {
        return C_L_q;
    }

    /**
     * @param C_L_q the C_L_q to set
     */
    public void setC_L_q(double C_L_q) {
        this.C_L_q = C_L_q;
    }

    /**
     * @return the C_D_Alpha_Dot
     */
    public double getC_D_Alpha_Dot() {
        return C_D_Alpha_Dot;
    }

    /**
     * @param C_D_Alpha_Dot the C_D_Alpha_Dot to set
     */
    public void setC_D_Alpha_Dot(double C_D_Alpha_Dot) {
        this.C_D_Alpha_Dot = C_D_Alpha_Dot;
    }

    /**
     * @return the C_D_q
     */
    public double getC_D_q() {
        return C_D_q;
    }

    /**
     * @param C_D_q the C_D_q to set
     */
    public void setC_D_q(double C_D_q) {
        this.C_D_q = C_D_q;
    }

    /**
     * @return the C_m_Alpha_Dot
     */
    public double getC_m_Alpha_Dot() {
        return C_m_Alpha_Dot;
    }

    /**
     * @param C_m_Alpha_Dot the C_m_Alpha_Dot to set
     */
    public void setC_m_Alpha_Dot(double C_m_Alpha_Dot) {
        this.C_m_Alpha_Dot = C_m_Alpha_Dot;
    }

    /**
     * @return the C_m_q
     */
    public double getC_m_q() {
        return C_m_q;
    }

    /**
     * @param C_m_q the C_m_q to set
     */
    public void setC_m_q(double C_m_q) {
        this.C_m_q = C_m_q;
    }

    /**
     * @return the C_l_Beta_Dot
     */
    public double getC_l_Beta_Dot() {
        return C_l_Beta_Dot;
    }

    /**
     * @param C_l_Beta_Dot the C_l_Beta_Dot to set
     */
    public void setC_l_Beta_Dot(double C_l_Beta_Dot) {
        this.C_l_Beta_Dot = C_l_Beta_Dot;
    }

    /**
     * @return the C_l_p
     */
    public double getC_l_p() {
        return C_l_p;
    }

    /**
     * @param C_l_p the C_l_p to set
     */
    public void setC_l_p(double C_l_p) {
        this.C_l_p = C_l_p;
    }

    /**
     * @return the C_l_r
     */
    public double getC_l_r() {
        return C_l_r;
    }

    /**
     * @param C_l_r the C_l_r to set
     */
    public void setC_l_r(double C_l_r) {
        this.C_l_r = C_l_r;
    }

    /**
     * @return the C_y_Beta_Dot
     */
    public double getC_y_Beta_Dot() {
        return C_y_Beta_Dot;
    }

    /**
     * @param C_y_Beta_Dot the C_y_Beta_Dot to set
     */
    public void setC_y_Beta_Dot(double C_y_Beta_Dot) {
        this.C_y_Beta_Dot = C_y_Beta_Dot;
    }

    /**
     * @return the C_y_p
     */
    public double getC_y_p() {
        return C_y_p;
    }

    /**
     * @param C_y_p the C_y_p to set
     */
    public void setC_y_p(double C_y_p) {
        this.C_y_p = C_y_p;
    }

    /**
     * @return the C_y_r
     */
    public double getC_y_r() {
        return C_y_r;
    }

    /**
     * @param C_y_r the C_y_r to set
     */
    public void setC_y_r(double C_y_r) {
        this.C_y_r = C_y_r;
    }

    /**
     * @return the C_n_Beta_Dot
     */
    public double getC_n_Beta_Dot() {
        return C_n_Beta_Dot;
    }

    /**
     * @param C_n_Beta_Dot the C_n_Beta_Dot to set
     */
    public void setC_n_Beta_Dot(double C_n_Beta_Dot) {
        this.C_n_Beta_Dot = C_n_Beta_Dot;
    }

    /**
     * @return the C_n_p
     */
    public double getC_n_p() {
        return C_n_p;
    }

    /**
     * @param C_n_p the C_n_p to set
     */
    public void setC_n_p(double C_n_p) {
        this.C_n_p = C_n_p;
    }

    /**
     * @return the C_n_r
     */
    public double getC_n_r() {
        return C_n_r;
    }

    /**
     * @param C_n_r the C_n_r to set
     */
    public void setC_n_r(double C_n_r) {
        this.C_n_r = C_n_r;
    }

    /**
     * @return the engineFacade
     */
    public EngineFacade getEngineFacade() {
        return engineFacade;
    }

    /**
     * @param engineFacade the engineFacade to set
     */
    public void setEngineFacade(EngineFacade engineFacade) {
        this.engineFacade = engineFacade;
    }

    /**
     * @return the mach
     */
    public double getMach() {
        return mach;
    }

    /**
     * @param mach the mach to set
     */
    public void setMach(double mach) {
        this.mach = mach;
    }

    /**
     * @return the alpha
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     * @param alpha the alpha to set
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     * @return the beta
     */
    public double getBeta() {
        return beta;
    }

    /**
     * @param beta the beta to set
     */
    public void setBeta(double beta) {
        this.beta = beta;
    }

    /**
     * @return the gama
     */
    public double getGama() {
        return gama;
    }

    /**
     * @param gama the gama to set
     */
    public void setGama(double gama) {
        this.gama = gama;
    }

    /**
     * @return the delta_e
     */
    public double getDelta_e() {
        return delta_e;
    }

    /**
     * @param delta_e the delta_e to set
     */
    public void setDelta_e(double delta_e) {
        this.delta_e = delta_e;
    }

    /**
     * @return the ih
     */
    public double getIh() {
        return ih;
    }

    /**
     * @param ih the ih to set
     */
    public void setIh(double ih) {
        this.ih = ih;
    }

    /**
     * @return the delta_a
     */
    public double getDelta_a() {
        return delta_a;
    }

    /**
     * @param delta_a the delta_a to set
     */
    public void setDelta_a(double delta_a) {
        this.delta_a = delta_a;
    }

    /**
     * @return the delta_r
     */
    public double getDelta_r() {
        return delta_r;
    }

    /**
     * @param delta_r the delta_r to set
     */
    public void setDelta_r(double delta_r) {
        this.delta_r = delta_r;
    }

    /**
     * @param findDelta_e the findDelta_e to set
     */
    public void setFindDelta_e(boolean findDelta_e) {
        this.findDelta_e = findDelta_e;
    }

    /**
     * @param findBeta the findBeta to set
     */
    public void setFindBeta(boolean findBeta) {
        this.findBeta = findBeta;
    }

    /**
     * @param findAlpha the findAlpha to set
     */
    public void setFindAlpha(boolean findAlpha) {
        this.findAlpha = findAlpha;
    }

    /**
     * @param findIh the findIh to set
     */
    public void setFindIh(boolean findIh) {
        this.findIh = findIh;
    }

    /**
     * @param findDelta_a the findDelta_a to set
     */
    public void setFindDelta_a(boolean findDelta_a) {
        this.findDelta_a = findDelta_a;
    }

    /**
     * @param findDelta_r the findDelta_r to set
     */
    public void setFindDelta_r(boolean findDelta_r) {
        this.findDelta_r = findDelta_r;
    }

    public void runNewtonRaphson(double derivationDelta) {

    	int itr = 0;
        while (itr < 100) {
        	itr++;

            odeTheta = alpha + gama;

            calculateForceAndMomentCoefficients(false);

            double[] functions = new double[6];
            double[][] taylor = new double[6][6];

            for (int i = 0; i < 6; i++)
                functions[i] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
/*
            System.out.println("XF = " + String.valueOf(functions[0]) + "; " +
                                "YF = " + String.valueOf(functions[1]) + "; " +
                                "ZF = " + String.valueOf(functions[2]) + "; " +
                                "XM = " + String.valueOf(functions[3]) + "; " +
                                "YM = " + String.valueOf(functions[4]) + "; " +
                                "ZM = " + String.valueOf(functions[5]) + "; ");
*/
            int columnIndex = 0;

            if (findAlpha) {
                alpha = alpha + derivationDelta;
                odeTheta = alpha + gama;
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                alpha = alpha - derivationDelta;
                odeTheta = alpha + gama;
                columnIndex++;
            }

            if (findBeta) {
                beta = beta + derivationDelta;
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                beta = beta - derivationDelta;
                columnIndex++;
            }

            if (findIh) {
                ih = ih + derivationDelta;
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                ih = ih - derivationDelta;
                columnIndex++;
            }

            if (findDelta_e) {
                delta_e = delta_e + derivationDelta;
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                delta_e = delta_e - derivationDelta;
                columnIndex++;
            }

            if (findDelta_a) {
                delta_a = delta_a + derivationDelta;
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                delta_a = delta_a - derivationDelta;
                columnIndex++;
            }

            if (findDelta_r) {
                delta_r = delta_r + derivationDelta;
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                delta_r = delta_r - derivationDelta;
                columnIndex++;
            }

            if (engineFacade.getEngineWrapper(0).getEngineWorkingFactor() == 1.0) {
                engineFacade.getEngineWrapper(0).getEngine().setDelta_T(
                        engineFacade.getEngineWrapper(0).getEngine().getDelta_T() +
                        derivationDelta);
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                engineFacade.getEngineWrapper(0).getEngine().setDelta_T(
                        engineFacade.getEngineWrapper(0).getEngine().getDelta_T() -
                        derivationDelta);
                columnIndex++;
            }

            if (engineFacade.getEngineWrapper(1).getEngineWorkingFactor() == 1.0) {
                engineFacade.getEngineWrapper(1).getEngine().setDelta_T(
                        engineFacade.getEngineWrapper(1).getEngine().getDelta_T() +
                        derivationDelta);
                calculateForceAndMomentCoefficients(false);
                for (int i = 0; i < 6; i++)
                    taylor[i][columnIndex] = getFunctionResultForTrim(i, dynamicPressureByMachNumber(mach));
                engineFacade.getEngineWrapper(1).getEngine().setDelta_T(
                        engineFacade.getEngineWrapper(1).getEngine().getDelta_T() -
                        derivationDelta);
                columnIndex++;
            }

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    taylor[i][j] = (taylor[i][j] - functions[i]) / derivationDelta;
                }
            }

            double[][] invJm = Inverse.invert(taylor);

            double[] diffs = new double[6];

            if (invJm != null) {

                for (int i = 0; i < 6; i++) {
                    diffs[i] = 0;
                    for (int j = 0; j < 6; j++) {
                        diffs[i] = diffs[i] - functions[j] * invJm[i][j];
                    }
                }

                int rowIndex = 0;

                if (findAlpha) {
                    alpha = alpha + diffs[rowIndex];
                    odeTheta = alpha + gama;
                    rowIndex++;
                }

                if (findBeta) {
                    beta = beta + diffs[rowIndex];
                    rowIndex++;
                }

                if (findIh) {
                    ih = ih + diffs[rowIndex];
                    rowIndex++;
                }

                if (findDelta_e) {
                    delta_e = delta_e + diffs[rowIndex];
                    rowIndex++;
                }

                if (findDelta_a) {
                    delta_a = delta_a + diffs[rowIndex];
                    rowIndex++;
                }

                if (findDelta_r) {
                    delta_r = delta_r + diffs[rowIndex];
                    rowIndex++;
                }

                if (engineFacade.getEngineWrapper(0).getEngineWorkingFactor() == 1.0) {
                    engineFacade.getEngineWrapper(0).getEngine().setDelta_T(
                            engineFacade.getEngineWrapper(0).getEngine().getDelta_T() +
                            diffs[rowIndex]);
                    rowIndex++;
                }

                if (engineFacade.getEngineWrapper(1).getEngineWorkingFactor() == 1.0) {
                    engineFacade.getEngineWrapper(1).getEngine().setDelta_T(
                            engineFacade.getEngineWrapper(1).getEngine().getDelta_T() +
                            diffs[rowIndex]);
                    rowIndex++;
                }

            }
/*
            System.out.println("alpha = " + String.valueOf(alpha * 180.0 / Math.PI) + "; " +
                                "beta = " + String.valueOf(beta * 180.0 / Math.PI) + "; " +
                                "ih = " + String.valueOf(ih * 180.0 / Math.PI) + "; " +
                                "delta_e = " + String.valueOf(delta_e * 180.0 / Math.PI) + "; " +
                                "delta_a = " + String.valueOf(delta_a * 180.0 / Math.PI) + "; " +
                                "delta_r = " + String.valueOf(delta_r * 180.0 / Math.PI) + "; " +
                                "delta_T1 = " + String.valueOf(engineFacade.getEngineWrapper(0).getEngine().getDelta_T()) + "; " +
                                "delta_T2 = " + String.valueOf(engineFacade.getEngineWrapper(1).getEngine().getDelta_T()) + "; ");
*/
            if ((0.00000001 > Math.abs(diffs[0]))
                && (0.00000001 > Math.abs(diffs[1]))
                && (0.00000001 > Math.abs(diffs[2]))
                && (0.00000001 > Math.abs(diffs[3]))
                && (0.00000001 > Math.abs(diffs[4]))
                && (0.00000001 > Math.abs(diffs[5]))) {
/*
                alpha = Math.round(alpha * 1000000000.0) / 1000000000.0;
              beta = Math.round(beta * 1000000000.0) / 1000000000.0;
              ih = Math.round(ih * 1000000000.0) / 1000000000.0;
              delta_e = Math.round(delta_e * 1000000000.0) / 1000000000.0;
              delta_a = Math.round(delta_a * 1000000000.0) / 1000000000.0;
              delta_r = Math.round(delta_r * 1000000000.0) / 1000000000.0;
              engineFacade.getEngineWrapper(0).getEngine().setDelta_T(
                Math.round(engineFacade.getEngineWrapper(0).getEngine().getDelta_T() * 1000000000.0) / 1000000000.0);
              engineFacade.getEngineWrapper(1).getEngine().setDelta_T(
                Math.round(engineFacade.getEngineWrapper(1).getEngine().getDelta_T() * 1000000000.0) / 1000000000.0);
*/
              break;
            }
        }

    }

    public double getV_Air_T() {
        return Math.sqrt(odeU * odeU +
                            odeV * odeV +
                            odeW * odeW);
    }

    private double calculata_R4() {
        return (- odeQ * odeR * (Izz - Iyy) +
                odeP * odeQ * Ixz +
                getTotalXMoment(dynamicPressureByTrueAirVelocity(getV_Air_T()))) / Ixx;
    }

    private double calculata_R6() {
        return (- odeP * odeQ * (Iyy - Ixx) -
                odeQ * odeR * Ixz +
                getTotalZMoment(dynamicPressureByTrueAirVelocity(getV_Air_T()))) / Izz;
    }

    private double calculata_R9() {
        return (odeR * Math.cos(odeFi) +
                odeQ * Math.sin(odeFi)) /
                Math.cos(odeTheta);
    }

    private double calculate_Ode_U_Dot() {
        return - odeQ * odeW +
                    odeR * odeV +
                    getTotalXForce(dynamicPressureByTrueAirVelocity(getV_Air_T())) / (weight / 9.81);
    }

    private double calculate_Ode_V_Dot() {
        return - odeR * odeU +
                    odeP * odeW +
                    getTotalYForce(dynamicPressureByTrueAirVelocity(getV_Air_T())) / (weight / 9.81);
    }

    private double calculate_Ode_W_Dot() {
        return - odeP * odeV +
                    odeQ * odeU +
                    getTotalZForce(dynamicPressureByTrueAirVelocity(getV_Air_T())) / (weight / 9.81);
    }

    private double calculate_Ode_P_Dot() {
        return (calculata_R4() + calculata_R6() * Ixz / Ixx) / (1 - Ixz * Ixz / (Ixx * Izz));
    }

    private double calculate_Ode_Q_Dot() {
        return (odeP * odeR * (Izz - Ixx) -
                (odeP * odeP - odeR * odeR) * Ixz +
                getTotalYMoment(dynamicPressureByTrueAirVelocity(getV_Air_T()))) / Iyy;
    }

    private double calculate_Ode_R_Dot() {
        return (calculata_R6() + calculata_R4() * Ixz / Izz) / (1 - Ixz * Ixz / (Ixx * Izz));
    }

    private double calculate_Ode_Fi_Dot() {
        return odeP + Math.sin(odeTheta) * calculata_R9();
    }

    private double calculate_Ode_Theta_Dot() {
        return odeQ * Math.cos(odeFi) - odeR * Math.sin(odeFi);
    }

    private double calculate_Ode_Psi_Dot() {
        return calculata_R9();
    }

    private double calculate_Ode_X_Dot() {
        return odeU * Math.cos(odeTheta) * Math.cos(odePsi) +
                odeV * (Math.sin(odeFi) * Math.sin(odeTheta) * Math.cos(odePsi) -
                                Math.cos(odeFi) * Math.sin(odePsi)) +
                odeW * (Math.cos(odeFi) * Math.sin(odeTheta) * Math.cos(odePsi) +
                                Math.sin(odeFi) * Math.sin(odePsi));
    }

    private double calculate_Ode_Y_Dot() {
        return odeU * Math.cos(odeTheta) * Math.sin(odePsi) +
                odeV * (Math.sin(odeFi) * Math.sin(odeTheta) * Math.sin(odePsi) +
                                Math.cos(odeFi) * Math.cos(odePsi)) +
                odeW * (Math.cos(odeFi) * Math.sin(odeTheta) * Math.sin(odePsi) -
                                Math.sin(odeFi) * Math.cos(odePsi));
    }

    private double calculate_Ode_Z_Dot() {
        return - odeU * Math.sin(odeTheta) +
                odeV * Math.sin(odeFi) * Math.cos(odeTheta) +
                odeW * Math.cos(odeFi) * Math.cos(odeTheta);
    }

    private void printSimulationStep(int step) {

//        double L = getLiftForceAero(dynamicPressureByTrueAirVelocity(getV_Air_T()));
//        double D = getDragForceAero(dynamicPressureByTrueAirVelocity(getV_Air_T()));
//        D += engineFacade.getEngineWrapper(0).getDragForceEngine(
//                        dynamicPressureByTrueAirVelocity(getV_Air_T()));
//        D += engineFacade.getEngineWrapper(1).getDragForceEngine(
//                        dynamicPressureByTrueAirVelocity(getV_Air_T()));
//        double T = engineFacade.getEngineWrapper(0).getThrustForceEngine();
//        T += engineFacade.getEngineWrapper(1).getThrustForceEngine();
/*
        System.out.println(
                            "U = " + String.valueOf(odeU) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(0)) + "\t" +
                            "V = " + String.valueOf(odeV) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(1)) + "\t" +
                            "W = " + String.valueOf(odeW) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(2)) + "\t" +
                            "P = " + String.valueOf(odeP * 180.0 / Math.PI) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(3) * 180.0 / Math.PI) + "\t" +
                            "Q = " + String.valueOf(odeQ * 180.0 / Math.PI) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(4) * 180.0 / Math.PI) + "\t" +
                            "R = " + String.valueOf(odeR * 180.0 / Math.PI) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(5) * 180.0 / Math.PI) + "\t" +
                            "Fi = " + String.valueOf(odeFi * 180.0 / Math.PI) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(6) * 180.0 / Math.PI) + "\t" +
                            "Theta = " + String.valueOf(odeTheta * 180.0 / Math.PI) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(7) * 180.0 / Math.PI) + "\t" +
                            "Psi = " + String.valueOf(odePsi * 180.0 / Math.PI) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(8) * 180.0 / Math.PI) + "\t" +
                            "X = " + String.valueOf(odeX) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(9)) + "\t" +
                            "Y = " + String.valueOf(odeY) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(10)) + "\t" +
                            "Z = " + String.valueOf(odeZ) + "\t" +
//                            String.valueOf(getFunctionResultForRungeKutta(11)) + "\t" +

                            "L = " + String.valueOf(L) + "\t" +
                            "D = " + String.valueOf(D) + "\t" +
                            "T = " + String.valueOf(T) + "\t" +

                            "MA = " + String.valueOf(
                                        getAeroMomentY(
                                            dynamicPressureByTrueAirVelocity(getV_Air_T()))) + "\t" +
                            "MT = " + String.valueOf(
                                        engineFacade.getEngineWrapper(0).getThrustMomentY(
                                            dynamicPressureByTrueAirVelocity(getV_Air_T())) +
                                        engineFacade.getEngineWrapper(1).getThrustMomentY(
                                            dynamicPressureByTrueAirVelocity(getV_Air_T()))) + "\t" +
                            "MA+MT = " + String.valueOf(
                                        getAeroMomentY(
                                            dynamicPressureByTrueAirVelocity(getV_Air_T())) +
                                        engineFacade.getEngineWrapper(0).getThrustMomentY(
                                            dynamicPressureByTrueAirVelocity(getV_Air_T())) +
                                        engineFacade.getEngineWrapper(1).getThrustMomentY(
                                            dynamicPressureByTrueAirVelocity(getV_Air_T()))) + "\t" +

                            "Alpha = " + String.valueOf(alpha * 180.0 / Math.PI) + "\t" +
                            "Beta = " + String.valueOf(beta * 180.0 / Math.PI) + "\t" +

                            "ih = " + String.valueOf(ih * 180.0 / Math.PI) + "\t" +
                            "Delta_e = " + String.valueOf(delta_e * 180.0 / Math.PI) + "\t" +
                            "Delta_a = " + String.valueOf(delta_a * 180.0 / Math.PI) + "\t" +
                            "Delta_r = " + String.valueOf(delta_r * 180.0 / Math.PI) + "\t" +
                            "Delta_t1 = " + String.valueOf(engineFacade.getEngineWrapper(0).getEngine().getDelta_T()) + "\t" +
                            "Delta_t2 = " + String.valueOf(engineFacade.getEngineWrapper(1).getEngine().getDelta_T()) + "\t" +
                            String.valueOf(step)
                            );
*/
    }

    public void initializeSimulation() {

        odeU = mach * getSpeedOfSound() * Math.cos(alpha) * Math.cos(beta);
        odeV = mach * getSpeedOfSound() * Math.cos(alpha) * Math.sin(beta);
        odeW = mach * getSpeedOfSound() * Math.sin(alpha) * Math.cos(beta);

//        odeU = V_air_T;
//        odeV = 0.0;
//        odeW = 0.0;

        odeP = 0.0;
        odeQ = 0.0;
        odeR = 0.0;

        odeTheta = alpha + gama;
        odePsi = beta;

        odeX = 0.0;
        odeY = 0.0;
        odeZ = - altitude;

        calculateForceAndMomentCoefficients(true);

        printSimulationStep(0);
    }

    private double getFunctionResultForRungeKutta(int index) {
        if (index == 0) return calculate_Ode_U_Dot();
        else if (index == 1) return calculate_Ode_V_Dot();
        else if (index == 2) return calculate_Ode_W_Dot();
        else if (index == 3) return calculate_Ode_P_Dot();
        else if (index == 4) return calculate_Ode_Q_Dot();
        else if (index == 5) return calculate_Ode_R_Dot();
        else if (index == 6) return calculate_Ode_Fi_Dot();
        else if (index == 7) return calculate_Ode_Theta_Dot();
        else if (index == 8) return calculate_Ode_Psi_Dot();
        else if (index == 9) return calculate_Ode_X_Dot();
        else if (index == 10) return calculate_Ode_Y_Dot();
        else if (index == 11) return calculate_Ode_Z_Dot();
        else
            return 0;
    }

    private void setParametersForRungeKutta(double[] values) {
        for (int i = 0; i < 12; i++)
            setParameterForRungeKutta(i, values[i]);
    }

    private void setParameterForRungeKutta(int index, double value) {
        if (index == 0) odeU = value;
        else if (index == 1) odeV = value;
        else if (index == 2) odeW = value;
        else if (index == 3) odeP = value;
        else if (index == 4) odeQ = value;
        else if (index == 5) odeR = value;
        else if (index == 6) {
//            if (value > Math.PI)
//                odeFi = value - 2 * Math.PI;
//            else
//            if (value < - Math.PI)
//                odeFi = value + 2 * Math.PI;
//            else
                odeFi = value;
        }
        else if (index == 7) {
//            if (value > Math.PI / 2)
//                odeTheta = value - 2 * Math.PI;
//            else
//            if (value < - Math.PI / 2)
//                odeTheta = value + 2 * Math.PI;
//            else
                odeTheta = value;
        }
        else if (index == 8) {
            odePsi = value;
        }
        else if (index == 9) odeX = value;
        else if (index == 10) odeY = value;
        else if (index == 11) odeZ = value;
    }

    double alpha_temp = 0.0;
    double alpha_dot_temp = 0.0;
    double beta_temp = 0.0;
    double beta_dot_temp = 0.0;
    double altitude_temp = 0.0;

    private void push() {
        alpha_temp = alpha;
        alpha_dot_temp = alpha_Dot;
        beta_temp = beta;
        beta_dot_temp = beta_Dot;
        altitude_temp = altitude;
    }

    private void pop() {
        alpha = alpha_temp;
        alpha_Dot = alpha_dot_temp;
        beta = beta_temp;
        beta_Dot = beta_dot_temp;
        altitude = altitude_temp;
    }

    private double[] calculateDerivationsForRungeKutta() {
        double[] res = new double[12];
        for (int i = 0; i < 12; i++) {
            res[i] = getFunctionResultForRungeKutta(i);
        }
        return res;
    }

    public void runRungeKutta4(int step) {

        double[] y = new double[12];
        double[] y2 = new double[12];
        double[] y3 = new double[12];
        double[] y4 = new double[12];

        y[0] = odeU;
        y[1] = odeV;
        y[2] = odeW;
        y[3] = odeP;
        y[4] = odeQ;
        y[5] = odeR;
        y[6] = odeFi;
        y[7] = odeTheta;
        y[8] = odePsi;
        y[9] = odeX;
        y[10] = odeY;
        y[11] = odeZ;

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

    /**
     * @return the odeU
     */
    public double getOdeU() {
        return odeU;
    }

    /**
     * @param odeU the odeU to set
     */
    public void setOdeU(double odeU) {
        this.odeU = odeU;
    }

    /**
     * @return the odeV
     */
    public double getOdeV() {
        return odeV;
    }

    /**
     * @param odeV the odeV to set
     */
    public void setOdeV(double odeV) {
        this.odeV = odeV;
    }

    /**
     * @return the odeW
     */
    public double getOdeW() {
        return odeW;
    }

    /**
     * @param odeW the odeW to set
     */
    public void setOdeW(double odeW) {
        this.odeW = odeW;
    }

    /**
     * @return the odeP
     */
    public double getOdeP() {
        return odeP;
    }

    /**
     * @param odeP the odeP to set
     */
    public void setOdeP(double odeP) {
        this.odeP = odeP;
    }

    /**
     * @return the odeQ
     */
    public double getOdeQ() {
        return odeQ;
    }

    /**
     * @param odeQ the odeQ to set
     */
    public void setOdeQ(double odeQ) {
        this.odeQ = odeQ;
    }

    /**
     * @return the odeR
     */
    public double getOdeR() {
        return odeR;
    }

    /**
     * @param odeR the odeR to set
     */
    public void setOdeR(double odeR) {
        this.odeR = odeR;
    }

    /**
     * @return the odeFi
     */
    public double getOdeFi() {
        return odeFi;
    }

    /**
     * @param odeFi the odeFi to set
     */
    public void setOdeFi(double odeFi) {
        this.odeFi = odeFi;
    }

    /**
     * @return the odeTheta
     */
    public double getOdeTheta() {
        return odeTheta;
    }

    /**
     * @param odeTheta the odeTheta to set
     */
    public void setOdeTheta(double odeTheta) {
        this.odeTheta = odeTheta;
    }

    /**
     * @return the odePsi
     */
    public double getOdePsi() {
        return odePsi;
    }

    /**
     * @param odePsi the odePsi to set
     */
    public void setOdePsi(double odePsi) {
        this.odePsi = odePsi;
    }

    /**
     * @return the odeX
     */
    public double getOdeX() {
        return odeX;
    }

    /**
     * @param odeX the odeX to set
     */
    public void setOdeX(double odeX) {
        this.odeX = odeX;
    }

    /**
     * @return the odeY
     */
    public double getOdeY() {
        return odeY;
    }

    /**
     * @param odeY the odeY to set
     */
    public void setOdeY(double odeY) {
        this.odeY = odeY;
    }

    /**
     * @return the odeZ
     */
    public double getOdeZ() {
        return odeZ;
    }

    /**
     * @param odeZ the odeZ to set
     */
    public void setOdeZ(double odeZ) {
        this.odeZ = odeZ;
    }

}
