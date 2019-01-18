package org.diffeq;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ACFlightDynamics {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
/*
 * 
 * 	6DOF Trim
 * 
 */
		final Button runNewtonRaphsonButton = new Button("Run Newton-Raphson");

		final TextBox trmACWeightField = new TextBox();
		final TextBox trmACSwField = new TextBox();
		final TextBox trmACARField = new TextBox();
		final TextBox trmACcwField = new TextBox();
		final TextBox trmACbwField = new TextBox();

		final TextBox trmACEngThrust1Field = new TextBox();
		final TextBox trmACEngThrust2Field = new TextBox();

		final TextBox trmACEngDEngFrontFace1Field = new TextBox();
		final TextBox trmACEngDEngFrontFace2Field = new TextBox();

		final TextBox trmACEngXT1Field = new TextBox();
		final TextBox trmACEngXT2Field = new TextBox();

		final TextBox trmACEngYT1Field = new TextBox();
		final TextBox trmACEngYT2Field = new TextBox();

		final TextBox trmACEngZT1Field = new TextBox();
		final TextBox trmACEngZT2Field = new TextBox();

		final TextBox trmACEngFi1Field = new TextBox();
		final TextBox trmACEngFi2Field = new TextBox();

		final TextBox trmACEngPs1Field = new TextBox();
		final TextBox trmACEngPs2Field = new TextBox();

		final TextBox trmAD_CD0Field = new TextBox();
		final TextBox trmAD_eField = new TextBox();
		final TextBox trmAD_CL0Field = new TextBox();
		final TextBox trmAD_CL_AlphaField = new TextBox();
		final TextBox trmAD_CL_ihField = new TextBox();
		final TextBox trmAD_CL_Delta_eField = new TextBox();
		final TextBox trmAD_Cm0Field = new TextBox();
		final TextBox trmAD_Cm_AlphaField = new TextBox();
		final TextBox trmAD_Cm_ihField = new TextBox();
		final TextBox trmAD_Cm_Delta_eField = new TextBox();
		final TextBox trmAD_Cy0Field = new TextBox();
		final TextBox trmAD_Cy_BetaField = new TextBox();
		final TextBox trmAD_Cy_Delta_aField = new TextBox();
		final TextBox trmAD_Cy_Delta_rField = new TextBox();
		final TextBox trmAD_Cl0Field = new TextBox();
		final TextBox trmAD_Cl_BetaField = new TextBox();
		final TextBox trmAD_Cl_Delta_aField = new TextBox();
		final TextBox trmAD_Cl_Delta_rField = new TextBox();
		final TextBox trmAD_Cn0Field = new TextBox();
		final TextBox trmAD_Cn_BetaField = new TextBox();
		final TextBox trmAD_Cn_Delta_aField = new TextBox();
		final TextBox trmAD_Cn_Delta_rField = new TextBox();

		final TextBox trmPrm_MachField = new TextBox();
		final TextBox trmPrm_GamaField = new TextBox();
		final TextBox trmPrm_FiField = new TextBox();
		final TextBox trmPrm_AlphaField = new TextBox();
		final CheckBox trmPrm_AlphaCheckField = new CheckBox();
		final TextBox trmPrm_BetaField = new TextBox();
		final CheckBox trmPrm_BetaCheckField = new CheckBox();
		final TextBox trmPrm_ihField = new TextBox();
		final CheckBox trmPrm_ihCheckField = new CheckBox();
		final TextBox trmPrm_Delta_eField = new TextBox();
		final CheckBox trmPrm_Delta_e_CheckField = new CheckBox();
		final TextBox trmPrm_Delta_aField = new TextBox();
		final CheckBox trmPrm_Delta_a_CheckField = new CheckBox();
		final TextBox trmPrm_Delta_rField = new TextBox();
		final CheckBox trmPrm_Delta_r_CheckField = new CheckBox();
		final TextBox trmPrm_Delta_T1Field = new TextBox();
		final CheckBox trmPrm_Delta_T1_CheckField = new CheckBox();
		final TextBox trmPrm_Delta_T2Field = new TextBox();
		final CheckBox trmPrm_Delta_T2_CheckField = new CheckBox();

		trmACWeightField.setWidth("100");
		trmACSwField.setWidth("100");
		trmACARField.setWidth("100");
		trmACcwField.setWidth("100");
		trmACbwField.setWidth("100");

		trmACEngThrust1Field.setWidth("100");
		trmACEngThrust2Field.setWidth("100");

		trmACEngDEngFrontFace1Field.setWidth("100");
		trmACEngDEngFrontFace2Field.setWidth("100");

		trmACEngXT1Field.setWidth("100");
		trmACEngXT2Field.setWidth("100");

		trmACEngYT1Field.setWidth("100");
		trmACEngYT2Field.setWidth("100");

		trmACEngZT1Field.setWidth("100");
		trmACEngZT2Field.setWidth("100");

		trmACEngFi1Field.setWidth("100");
		trmACEngFi2Field.setWidth("100");

		trmACEngPs1Field.setWidth("100");
		trmACEngPs2Field.setWidth("100");

		trmAD_CD0Field.setWidth("100");
		trmAD_eField.setWidth("100");
		trmAD_CL0Field.setWidth("100");
		trmAD_CL_AlphaField.setWidth("100");
		trmAD_CL_ihField.setWidth("100");
		trmAD_CL_Delta_eField.setWidth("100");
		trmAD_Cm0Field.setWidth("100");
		trmAD_Cm_AlphaField.setWidth("100");
		trmAD_Cm_ihField.setWidth("100");
		trmAD_Cm_Delta_eField.setWidth("100");
		trmAD_Cy0Field.setWidth("100");
		trmAD_Cy_BetaField.setWidth("100");
		trmAD_Cy_Delta_aField.setWidth("100");
		trmAD_Cy_Delta_rField.setWidth("100");
		trmAD_Cl0Field.setWidth("100");
		trmAD_Cl_BetaField.setWidth("100");
		trmAD_Cl_Delta_aField.setWidth("100");
		trmAD_Cl_Delta_rField.setWidth("100");
		trmAD_Cn0Field.setWidth("100");
		trmAD_Cn_BetaField.setWidth("100");
		trmAD_Cn_Delta_aField.setWidth("100");
		trmAD_Cn_Delta_rField.setWidth("100");

		trmPrm_MachField.setWidth("100");
		trmPrm_GamaField.setWidth("100");
		trmPrm_FiField.setWidth("100");
		trmPrm_AlphaField.setWidth("100");
		trmPrm_AlphaCheckField.setWidth("100");
		trmPrm_BetaField.setWidth("100");
		trmPrm_BetaCheckField.setWidth("100");
		trmPrm_ihField.setWidth("100");
		trmPrm_ihCheckField.setWidth("100");
		trmPrm_Delta_eField.setWidth("100");
		trmPrm_Delta_e_CheckField.setWidth("100");
		trmPrm_Delta_aField.setWidth("100");
		trmPrm_Delta_a_CheckField.setWidth("100");
		trmPrm_Delta_rField.setWidth("100");
		trmPrm_Delta_r_CheckField.setWidth("100");
		trmPrm_Delta_T1Field.setWidth("100");
		trmPrm_Delta_T1_CheckField.setWidth("100");
		trmPrm_Delta_T2Field.setWidth("100");
		trmPrm_Delta_T2_CheckField.setWidth("100");

		trmACWeightField.setText("500000.0");
		trmACSwField.setText("105.0");
		trmACARField.setText("8.12");
		trmACcwField.setText("4.05");
		trmACbwField.setText("29.2");

		trmACEngThrust1Field.setText("105000.0");
		trmACEngThrust2Field.setText("105000.0");

		trmACEngDEngFrontFace1Field.setText("1.88");
		trmACEngDEngFrontFace2Field.setText("1.88");

		trmACEngXT1Field.setText("3.8");
		trmACEngXT2Field.setText("3.8");

		trmACEngYT1Field.setText("-5.1");
		trmACEngYT2Field.setText("5.1");

		trmACEngZT1Field.setText("1.6");
		trmACEngZT2Field.setText("1.6");

		trmACEngFi1Field.setText("0.0");
		trmACEngFi2Field.setText("0.0");

		trmACEngPs1Field.setText("2.0");
		trmACEngPs2Field.setText("-2.0");

		trmAD_CD0Field.setText("0.0203");
		trmAD_eField.setText("0.85");
		trmAD_CL0Field.setText("0.2165");
		trmAD_CL_AlphaField.setText("5.4039");
		trmAD_CL_ihField.setText("1.0652");
		trmAD_CL_Delta_eField.setText("0.4981");
		trmAD_Cm0Field.setText("0.1093");
		trmAD_Cm_AlphaField.setText("-1.7936");
		trmAD_Cm_ihField.setText("-3.8246");
		trmAD_Cm_Delta_eField.setText("-1.7884");
		trmAD_Cy0Field.setText("0.0");
		trmAD_Cy_BetaField.setText("-0.7316");
		trmAD_Cy_Delta_aField.setText("0.0");
		trmAD_Cy_Delta_rField.setText("0.2513");
		trmAD_Cl0Field.setText("0.0");
		trmAD_Cl_BetaField.setText("-0.2195");
		trmAD_Cl_Delta_aField.setText("0.0677");
		trmAD_Cl_Delta_rField.setText("0.0153");
		trmAD_Cn0Field.setText("0.0");
		trmAD_Cn_BetaField.setText("0.0511");
		trmAD_Cn_Delta_aField.setText("0.0");
		trmAD_Cn_Delta_rField.setText("-0.1382");

		trmPrm_MachField.setText("0.5");
		trmPrm_GamaField.setText("0.0");
		trmPrm_FiField.setText("0.0");

		trmPrm_AlphaField.setText("0.0");
		trmPrm_AlphaCheckField.setValue(true);
		trmPrm_BetaField.setText("0.0");
		trmPrm_BetaCheckField.setValue(false);
		trmPrm_ihField.setText("0.0");
		trmPrm_ihCheckField.setValue(true);
		trmPrm_Delta_eField.setText("4.0");
		trmPrm_Delta_e_CheckField.setValue(false);
		trmPrm_Delta_aField.setText("0.0");
		trmPrm_Delta_a_CheckField.setValue(true);
		trmPrm_Delta_rField.setText("0.0");
		trmPrm_Delta_r_CheckField.setValue(true);
		trmPrm_Delta_T1Field.setText("0.0");
		trmPrm_Delta_T1_CheckField.setValue(true);
		trmPrm_Delta_T2Field.setText("0.0");
		trmPrm_Delta_T2_CheckField.setValue(true);


/*
 * 
 * 	Flight Simulation Runge Kutta
 * 
 */

		final Button runSimulation = new Button("Run Flight Simulation");

		final TextBox simIxxField = new TextBox();
		final TextBox simIyyField = new TextBox();
		final TextBox simIzzField = new TextBox();
		final TextBox simIxzField = new TextBox();

		final TextBox trmAD_CL_Alpha_DotField = new TextBox();
		final TextBox trmAD_CL_qField = new TextBox();
		final TextBox trmAD_CD_Alpha_DotField = new TextBox();
		final TextBox trmAD_CD_qField = new TextBox();
		final TextBox trmAD_Cm_Alpha_DotField = new TextBox();
		final TextBox trmAD_Cm_qField = new TextBox();
		final TextBox trmAD_C_l_Beta_DotField = new TextBox();
		final TextBox trmAD_C_l_pField = new TextBox();
		final TextBox trmAD_C_l_rField = new TextBox();
		final TextBox trmAD_C_y_Beta_DotField = new TextBox();
		final TextBox trmAD_C_y_pField = new TextBox();
		final TextBox trmAD_C_y_rField = new TextBox();
		final TextBox trmAD_C_n_Beta_DotField = new TextBox();
		final TextBox trmAD_C_n_pField = new TextBox();
		final TextBox trmAD_C_n_rField = new TextBox();
		final TextBox simPrm_ih1Field = new TextBox();
		final TextBox simPrm_ih2Field = new TextBox();
		final TextBox simPrm_Delta_e_1Field = new TextBox();
		final TextBox simPrm_Delta_e_2Field = new TextBox();
		final TextBox simPrm_Delta_a_1Field = new TextBox();
		final TextBox simPrm_Delta_a_2Field = new TextBox();
		final TextBox simPrm_Delta_r_1Field = new TextBox();
		final TextBox simPrm_Delta_r_2Field = new TextBox();
		final TextBox simPrm_Delta_T1_1Field = new TextBox();
		final TextBox simPrm_Delta_T1_2Field = new TextBox();
		final TextBox simPrm_Delta_T2_1Field = new TextBox();
		final TextBox simPrm_Delta_T2_2Field = new TextBox();
		final TextBox simPrm_Time1Field = new TextBox();
		final TextBox simPrm_Time2Field = new TextBox();

		final TextArea simResultsField = new TextArea();

		final Image imgU = new Image();
		final Image imgV = new Image();
		final Image imgW = new Image();
		final Image imgP = new Image();
		final Image imgQ = new Image();
		final Image imgR = new Image();
		final Image imgPhi = new Image();
		final Image imgTheta = new Image();
		final Image imgPsi = new Image();
		final Image imgX = new Image();
		final Image imgY = new Image();
		final Image imgZ = new Image();

		final Image imgAlpha = new Image();
		final Image imgBeta = new Image();

		final Image imgIh = new Image();
		final Image imgDelta_e = new Image();
		final Image imgDelta_a = new Image();
		final Image imgDelta_r = new Image();
		final Image imgDelta_t1 = new Image();
		final Image imgDelta_t2 = new Image();

		final Image imgL = new Image();
		final Image imgD = new Image();
		final Image imgT = new Image();

		final Image imgLx = new Image();
		final Image imgMy = new Image();
		final Image imgNz = new Image();

		final Image imgMA = new Image();
		final Image imgMT = new Image();
		final Image imgMAT = new Image();
/*
		String emptyImgUrl = "empty.jpg";

		imgU.setUrl(emptyImgUrl);
		imgV.setUrl(emptyImgUrl);
		imgW.setUrl(emptyImgUrl);
		imgP.setUrl(emptyImgUrl);
		imgQ.setUrl(emptyImgUrl);
		imgR.setUrl(emptyImgUrl);
		imgPhi.setUrl(emptyImgUrl);
		imgTheta.setUrl(emptyImgUrl);
		imgPsi.setUrl(emptyImgUrl);
		imgX.setUrl(emptyImgUrl);
		imgY.setUrl(emptyImgUrl);
		imgZ.setUrl(emptyImgUrl);
		imgAlpha.setUrl(emptyImgUrl);
		imgBeta.setUrl(emptyImgUrl);
		imgIh.setUrl(emptyImgUrl);
		imgDelta_e.setUrl(emptyImgUrl);
		imgDelta_a.setUrl(emptyImgUrl);
		imgDelta_r.setUrl(emptyImgUrl);
		imgDelta_t1.setUrl(emptyImgUrl);
		imgDelta_t2.setUrl(emptyImgUrl);
		imgL.setUrl(emptyImgUrl);
		imgD.setUrl(emptyImgUrl);
		imgT.setUrl(emptyImgUrl);

		imgLx.setUrl(emptyImgUrl);
		imgMy.setUrl(emptyImgUrl);
		imgNz.setUrl(emptyImgUrl);

		imgMA.setUrl(emptyImgUrl);
		imgMT.setUrl(emptyImgUrl);
		imgMAT.setUrl(emptyImgUrl);
*/

		imgU.setVisible(false);
		imgV.setVisible(false);
		imgW.setVisible(false);
		imgP.setVisible(false);
		imgQ.setVisible(false);
		imgR.setVisible(false);
		imgPhi.setVisible(false);
		imgTheta.setVisible(false);
		imgPsi.setVisible(false);
		imgX.setVisible(false);
		imgY.setVisible(false);
		imgZ.setVisible(false);
		imgAlpha.setVisible(false);
		imgBeta.setVisible(false);
		imgIh.setVisible(false);
		imgDelta_e.setVisible(false);
		imgDelta_a.setVisible(false);
		imgDelta_r.setVisible(false);
		imgDelta_t1.setVisible(false);
		imgDelta_t2.setVisible(false);
		imgL.setVisible(false);
		imgD.setVisible(false);
		imgT.setVisible(false);

		imgLx.setVisible(false);
		imgMy.setVisible(false);
		imgNz.setVisible(false);

		imgMA.setVisible(false);
		imgMT.setVisible(false);
		imgMAT.setVisible(false);

		simIxxField.setWidth("100");
		simIyyField.setWidth("100");
		simIzzField.setWidth("100");
		simIxzField.setWidth("100");

		trmAD_CL_Alpha_DotField.setWidth("100");
		trmAD_CL_qField.setWidth("100");
		trmAD_CD_Alpha_DotField.setWidth("100");
		trmAD_CD_qField.setWidth("100");
		trmAD_Cm_Alpha_DotField.setWidth("100");
		trmAD_Cm_qField.setWidth("100");
		trmAD_C_l_Beta_DotField.setWidth("100");
		trmAD_C_l_pField.setWidth("100");
		trmAD_C_l_rField.setWidth("100");
		trmAD_C_y_Beta_DotField.setWidth("100");
		trmAD_C_y_pField.setWidth("100");
		trmAD_C_y_rField.setWidth("100");
		trmAD_C_n_Beta_DotField.setWidth("100");
		trmAD_C_n_pField.setWidth("100");
		trmAD_C_n_rField.setWidth("100");
		simPrm_ih1Field.setWidth("100");
		simPrm_ih2Field.setWidth("100");
		simPrm_Delta_e_1Field.setWidth("100");
		simPrm_Delta_e_2Field.setWidth("100");
		simPrm_Delta_a_1Field.setWidth("100");
		simPrm_Delta_a_2Field.setWidth("100");
		simPrm_Delta_r_1Field.setWidth("100");
		simPrm_Delta_r_2Field.setWidth("100");
		simPrm_Delta_T1_1Field.setWidth("100");
		simPrm_Delta_T1_2Field.setWidth("100");
		simPrm_Delta_T2_1Field.setWidth("100");
		simPrm_Delta_T2_2Field.setWidth("100");
		simPrm_Time1Field.setWidth("100");
		simPrm_Time2Field.setWidth("100");

		simResultsField.setWidth("1000");
		simResultsField.setHeight("400");

		simIxxField.setText("828253.5");
		simIyyField.setText("2552412.6");
		simIzzField.setText("3239451.5");
		simIxzField.setText("60728.0");

		trmAD_CL_Alpha_DotField.setText("2.5740");
		trmAD_CL_qField.setText("7.9072");
		trmAD_CD_Alpha_DotField.setText("0.0");
		trmAD_CD_qField.setText("0.0");
		trmAD_Cm_Alpha_DotField.setText("-9.2423");
		trmAD_Cm_qField.setText("-29.4652");
		trmAD_C_l_Beta_DotField.setText("-0.0028");
		trmAD_C_l_pField.setText("-0.4261");
		trmAD_C_l_rField.setText("0.1595");
		trmAD_C_y_Beta_DotField.setText("-0.0535");
		trmAD_C_y_pField.setText("-0.2962");
		trmAD_C_y_rField.setText("0.4878");
		trmAD_C_n_Beta_DotField.setText("-0.0239");
		trmAD_C_n_pField.setText("-0.0339");
		trmAD_C_n_rField.setText("-0.2394");

		simPrm_ih1Field.setText("0.0");
		simPrm_ih2Field.setText("0.0");
		simPrm_Delta_e_1Field.setText("0.0");
		simPrm_Delta_e_2Field.setText("0.0");
		simPrm_Delta_a_1Field.setText("0.0");
		simPrm_Delta_a_2Field.setText("0.0");
		simPrm_Delta_r_1Field.setText("0.0");
		simPrm_Delta_r_2Field.setText("0.0");
		simPrm_Delta_T1_1Field.setText("0.0");
		simPrm_Delta_T1_2Field.setText("0.0");
		simPrm_Delta_T2_1Field.setText("0.0");
		simPrm_Delta_T2_2Field.setText("0.0");
		simPrm_Time1Field.setText("50");
		simPrm_Time2Field.setText("60");



		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("An error occured");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label errorTextLabel = new Label();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("500");
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>An error occured:</b>"));
		dialogVPanel.add(errorTextLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				calculateBodyVectorButton.setEnabled(true);
				calculateEarthVectorButton.setEnabled(true);
				runNewtonRaphsonButton.setEnabled(true);
				runSimulation.setEnabled(true);
			}
		});
/*
 * 
 * 	6DOF Trim
 * 
 */
		RootPanel.get("yawAngleFieldContainer").add(yawAngleField);

		RootPanel.get("trmACWeight").add(trmACWeightField);
		RootPanel.get("trmACSw").add(trmACSwField);
		RootPanel.get("trmACAR").add(trmACARField);
		RootPanel.get("trmACcw").add(trmACcwField);
		RootPanel.get("trmACbw").add(trmACbwField);

		RootPanel.get("trmACEngThrust1").add(trmACEngThrust1Field);
		RootPanel.get("trmACEngThrust2").add(trmACEngThrust2Field);

		RootPanel.get("trmACEngDEngFrontFace1").add(trmACEngDEngFrontFace1Field);
		RootPanel.get("trmACEngDEngFrontFace2").add(trmACEngDEngFrontFace2Field);

		RootPanel.get("trmACEngXT1").add(trmACEngXT1Field);
		RootPanel.get("trmACEngXT2").add(trmACEngXT2Field);

		RootPanel.get("trmACEngYT1").add(trmACEngYT1Field);
		RootPanel.get("trmACEngYT2").add(trmACEngYT2Field);

		RootPanel.get("trmACEngZT1").add(trmACEngZT1Field);
		RootPanel.get("trmACEngZT2").add(trmACEngZT2Field);

		RootPanel.get("trmACEngFi1").add(trmACEngFi1Field);
		RootPanel.get("trmACEngFi2").add(trmACEngFi2Field);

		RootPanel.get("trmACEngPs1").add(trmACEngPs1Field);
		RootPanel.get("trmACEngPs2").add(trmACEngPs2Field);

		RootPanel.get("trmAD_CD0").add(trmAD_CD0Field);
		RootPanel.get("trmAD_e").add(trmAD_eField);
		RootPanel.get("trmAD_CL0").add(trmAD_CL0Field);
		RootPanel.get("trmAD_CL_Alpha").add(trmAD_CL_AlphaField);
		RootPanel.get("trmAD_CL_ih").add(trmAD_CL_ihField);
		RootPanel.get("trmAD_CL_Delta_e").add(trmAD_CL_Delta_eField);
		RootPanel.get("trmAD_Cm0").add(trmAD_Cm0Field);
		RootPanel.get("trmAD_Cm_Alpha").add(trmAD_Cm_AlphaField);
		RootPanel.get("trmAD_Cm_ih").add(trmAD_Cm_ihField);
		RootPanel.get("trmAD_Cm_Delta_e").add(trmAD_Cm_Delta_eField);
		RootPanel.get("trmAD_Cy0").add(trmAD_Cy0Field);
		RootPanel.get("trmAD_Cy_Beta").add(trmAD_Cy_BetaField);
		RootPanel.get("trmAD_Cy_Delta_a").add(trmAD_Cy_Delta_aField);
		RootPanel.get("trmAD_Cy_Delta_r").add(trmAD_Cy_Delta_rField);
		RootPanel.get("trmAD_C_l0").add(trmAD_Cl0Field);
		RootPanel.get("trmAD_C_l_Beta").add(trmAD_Cl_BetaField);
		RootPanel.get("trmAD_C_l_Delta_a").add(trmAD_Cl_Delta_aField);
		RootPanel.get("trmAD_C_l_Delta_r").add(trmAD_Cl_Delta_rField);
		RootPanel.get("trmAD_Cn0").add(trmAD_Cn0Field);
		RootPanel.get("trmAD_Cn_Beta").add(trmAD_Cn_BetaField);
		RootPanel.get("trmAD_Cn_Delta_a").add(trmAD_Cn_Delta_aField);
		RootPanel.get("trmAD_Cn_Delta_r").add(trmAD_Cn_Delta_rField);

		RootPanel.get("trmPrm_Mach").add(trmPrm_MachField);
		RootPanel.get("trmPrm_Gama").add(trmPrm_GamaField);
		RootPanel.get("trmPrm_Fi").add(trmPrm_FiField);
		RootPanel.get("trmPrm_Alpha").add(trmPrm_AlphaField);
		RootPanel.get("trmPrm_AlphaCheck").add(trmPrm_AlphaCheckField);
		RootPanel.get("trmPrm_Beta").add(trmPrm_BetaField);
		RootPanel.get("trmPrm_BetaCheck").add(trmPrm_BetaCheckField);
		RootPanel.get("trmPrm_ih").add(trmPrm_ihField);
		RootPanel.get("trmPrm_ihCheck").add(trmPrm_ihCheckField);
		RootPanel.get("trmPrm_Delta_e").add(trmPrm_Delta_eField);
		RootPanel.get("trmPrm_Delta_e_Check").add(trmPrm_Delta_e_CheckField);
		RootPanel.get("trmPrm_Delta_a").add(trmPrm_Delta_aField);
		RootPanel.get("trmPrm_Delta_a_Check").add(trmPrm_Delta_a_CheckField);
		RootPanel.get("trmPrm_Delta_r").add(trmPrm_Delta_rField);
		RootPanel.get("trmPrm_Delta_r_Check").add(trmPrm_Delta_r_CheckField);
		RootPanel.get("trmPrm_Delta_T1").add(trmPrm_Delta_T1Field);
		RootPanel.get("trmPrm_Delta_T1_Check").add(trmPrm_Delta_T1_CheckField);
		RootPanel.get("trmPrm_Delta_T2").add(trmPrm_Delta_T2Field);
		RootPanel.get("trmPrm_Delta_T2_Check").add(trmPrm_Delta_T2_CheckField);

		RootPanel.get("runNewtonRaphson").add(runNewtonRaphsonButton);

/*
 * 
 * Flight Simulation
 * 
 */

		RootPanel.get("simIxx").add(simIxxField);
		RootPanel.get("simIyy").add(simIyyField);
		RootPanel.get("simIzz").add(simIzzField);
		RootPanel.get("simIxz").add(simIxzField);

		RootPanel.get("trmAD_CL_Alpha_Dot").add(trmAD_CL_Alpha_DotField);
		RootPanel.get("trmAD_CL_q").add(trmAD_CL_qField);
		RootPanel.get("trmAD_CD_Alpha_Dot").add(trmAD_CD_Alpha_DotField);
		RootPanel.get("trmAD_CD_q").add(trmAD_CD_qField);
		RootPanel.get("trmAD_Cm_Alpha_Dot").add(trmAD_Cm_Alpha_DotField);
		RootPanel.get("trmAD_Cm_q").add(trmAD_Cm_qField);
		RootPanel.get("trmAD_C_l_Beta_Dot").add(trmAD_C_l_Beta_DotField);
		RootPanel.get("trmAD_C_l_p").add(trmAD_C_l_pField);
		RootPanel.get("trmAD_C_l_r").add(trmAD_C_l_rField);
		RootPanel.get("trmAD_C_y_Beta_Dot").add(trmAD_C_y_Beta_DotField);
		RootPanel.get("trmAD_C_y_p").add(trmAD_C_y_pField);
		RootPanel.get("trmAD_C_y_r").add(trmAD_C_y_rField);
		RootPanel.get("trmAD_C_n_Beta_Dot").add(trmAD_C_n_Beta_DotField);
		RootPanel.get("trmAD_C_n_p").add(trmAD_C_n_pField);
		RootPanel.get("trmAD_C_n_r").add(trmAD_C_n_rField);
		RootPanel.get("simPrm_ih1").add(simPrm_ih1Field);
		RootPanel.get("simPrm_ih2").add(simPrm_ih2Field);
		RootPanel.get("simPrm_Delta_e_1").add(simPrm_Delta_e_1Field);
		RootPanel.get("simPrm_Delta_e_2").add(simPrm_Delta_e_2Field);
		RootPanel.get("simPrm_Delta_a_1").add(simPrm_Delta_a_1Field);
		RootPanel.get("simPrm_Delta_a_2").add(simPrm_Delta_a_2Field);
		RootPanel.get("simPrm_Delta_r_1").add(simPrm_Delta_r_1Field);
		RootPanel.get("simPrm_Delta_r_2").add(simPrm_Delta_r_2Field);
		RootPanel.get("simPrm_Delta_T1_1").add(simPrm_Delta_T1_1Field);
		RootPanel.get("simPrm_Delta_T1_2").add(simPrm_Delta_T1_2Field);
		RootPanel.get("simPrm_Delta_T2_1").add(simPrm_Delta_T2_1Field);
		RootPanel.get("simPrm_Delta_T2_2").add(simPrm_Delta_T2_2Field);
		RootPanel.get("simPrm_Time1").add(simPrm_Time1Field);
		RootPanel.get("simPrm_Time2").add(simPrm_Time2Field);

		RootPanel.get("runSimulation").add(runSimulation);

		RootPanel.get("simResults").add(simResultsField);

		RootPanel.get("imgU").add(imgU);
		RootPanel.get("imgV").add(imgV);
		RootPanel.get("imgW").add(imgW);
		RootPanel.get("imgP").add(imgP);
		RootPanel.get("imgQ").add(imgQ);
		RootPanel.get("imgR").add(imgR);
		RootPanel.get("imgPhi").add(imgPhi);
		RootPanel.get("imgTheta").add(imgTheta);
		RootPanel.get("imgPsi").add(imgPsi);
		RootPanel.get("imgX").add(imgX);
		RootPanel.get("imgY").add(imgY);
		RootPanel.get("imgZ").add(imgZ);

		RootPanel.get("imgAlpha").add(imgAlpha);
		RootPanel.get("imgBeta").add(imgBeta);

		RootPanel.get("imgIh").add(imgIh);
		RootPanel.get("imgDelta_e").add(imgDelta_e);
		RootPanel.get("imgDelta_a").add(imgDelta_a);
		RootPanel.get("imgDelta_r").add(imgDelta_r);
		RootPanel.get("imgDelta_t1").add(imgDelta_t1);
		RootPanel.get("imgDelta_t2").add(imgDelta_t2);

		RootPanel.get("imgL").add(imgL);
		RootPanel.get("imgD").add(imgD);
		RootPanel.get("imgT").add(imgT);

		RootPanel.get("imgLx").add(imgLx);
		RootPanel.get("imgMy").add(imgMy);
		RootPanel.get("imgNz").add(imgNz);

		RootPanel.get("imgMA").add(imgMA);
		RootPanel.get("imgMT").add(imgMT);
		RootPanel.get("imgMAT").add(imgMAT);

		earthXField.setFocus(true);
		earthXField.selectAll();

		class TrimHandler implements ClickHandler, KeyUpHandler {
			public void onClick(ClickEvent event) {
				runNewtonRaphsonTrimAnalisys(event.getSource());
			}

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					runNewtonRaphsonTrimAnalisys(event.getSource());
				}
			}

			private void runNewtonRaphsonTrimAnalisys(Object sender) {
				runNewtonRaphsonButton.setEnabled(false);

				try {
					Aircraft b737 = new Aircraft();
					b737.setWeight(Double.valueOf(trmACWeightField.getValue()));
					b737.setS_w(Double.valueOf(trmACSwField.getValue()));
					b737.setAR(Double.valueOf(trmACARField.getValue()));
					b737.setC_Wing(Double.valueOf(trmACcwField.getValue()));
					b737.setB_Wing(Double.valueOf(trmACbwField.getValue()));

					double engineWorkingFactor = 0.0;
					if (trmPrm_Delta_T1_CheckField.getValue())
						engineWorkingFactor = 1.0;

					b737.setEngine1(Double.valueOf(trmACEngThrust1Field.getValue()), 
			        				Double.valueOf(trmACEngDEngFrontFace1Field.getValue()), 
			        				Double.valueOf(trmACEngXT1Field.getValue()), 
			        				Double.valueOf(trmACEngYT1Field.getValue()), 
			        				Double.valueOf(trmACEngZT1Field.getValue()), 
			        				Double.valueOf(trmACEngFi1Field.getValue()) * Math.PI / 180, 
			        				Double.valueOf(trmACEngPs1Field.getValue()) * Math.PI / 180,
			        				engineWorkingFactor);
					engineWorkingFactor = 0.0;
					if (trmPrm_Delta_T2_CheckField.getValue())
						engineWorkingFactor = 1.0;
			        b737.setEngine2(Double.valueOf(trmACEngThrust2Field.getValue()), 
			        				Double.valueOf(trmACEngDEngFrontFace2Field.getValue()), 
			        				Double.valueOf(trmACEngXT2Field.getValue()), 
			        				Double.valueOf(trmACEngYT2Field.getValue()), 
			        				Double.valueOf(trmACEngZT2Field.getValue()), 
			        				Double.valueOf(trmACEngFi2Field.getValue()) * Math.PI / 180, 
			        				Double.valueOf(trmACEngPs2Field.getValue()) * Math.PI / 180,
			        				engineWorkingFactor);

					b737.set_C_D0(Double.valueOf(trmAD_CD0Field.getValue()));
					b737.setOswaldEfficiency(Double.valueOf(trmAD_eField.getValue()));

					b737.setC_L0(Double.valueOf(trmAD_CL0Field.getValue()));
					b737.setC_L_Alpha(Double.valueOf(trmAD_CL_AlphaField.getValue()));
					b737.setC_L_ih(Double.valueOf(trmAD_CL_ihField.getValue()));
					b737.setC_L_Delta_e(Double.valueOf(trmAD_CL_Delta_eField.getValue()));
					b737.setC_m0(Double.valueOf(trmAD_Cm0Field.getValue()));
					b737.setC_m_Alpha(Double.valueOf(trmAD_Cm_AlphaField.getValue()));
					b737.setC_m_ih(Double.valueOf(trmAD_Cm_ihField.getValue()));
					b737.setC_m_Delta_e(Double.valueOf(trmAD_Cm_Delta_eField.getValue()));
					b737.setC_y_0(Double.valueOf(trmAD_Cy0Field.getValue()));
					b737.setC_y_Beta(Double.valueOf(trmAD_Cy_BetaField.getValue()));
					b737.setC_y_Delta_a(Double.valueOf(trmAD_Cy_Delta_aField.getValue()));
					b737.setC_y_Delta_r(Double.valueOf(trmAD_Cy_Delta_rField.getValue()));
					b737.setC_l0(Double.valueOf(trmAD_Cl0Field.getValue()));
					b737.setC_l_Beta(Double.valueOf(trmAD_Cl_BetaField.getValue()));
					b737.setC_l_Delta_a(Double.valueOf(trmAD_Cl_Delta_aField.getValue()));
					b737.setC_l_Delta_r(Double.valueOf(trmAD_Cl_Delta_rField.getValue()));
					b737.setC_n0(Double.valueOf(trmAD_Cn0Field.getValue()));
					b737.setC_n_Beta(Double.valueOf(trmAD_Cn_BetaField.getValue()));
					b737.setC_n_Delta_a(Double.valueOf(trmAD_Cn_Delta_aField.getValue()));
					b737.setC_n_Delta_r(Double.valueOf(trmAD_Cn_Delta_rField.getValue()));

					b737.setMach(Double.valueOf(trmPrm_MachField.getValue()));
					b737.setGama(Double.valueOf(trmPrm_GamaField.getValue()) * Math.PI / 180.0);
					b737.setOdeFi(Double.valueOf(trmPrm_FiField.getValue()) * Math.PI / 180.0);
					b737.setAlpha(Double.valueOf(trmPrm_AlphaField.getValue()) * Math.PI / 180.0);
					b737.setFindAlpha(trmPrm_AlphaCheckField.getValue());
					b737.setBeta(Double.valueOf(trmPrm_BetaField.getValue()) * Math.PI / 180.0);
					b737.setFindBeta(trmPrm_BetaCheckField.getValue());
					b737.setIh(Double.valueOf(trmPrm_ihField.getValue()) * Math.PI / 180.0);
					b737.setFindIh(trmPrm_ihCheckField.getValue());
					b737.setDelta_e(Double.valueOf(trmPrm_Delta_eField.getValue()) * Math.PI / 180.0);
					b737.setFindDelta_e(trmPrm_Delta_e_CheckField.getValue());
					b737.setDelta_a(Double.valueOf(trmPrm_Delta_aField.getValue()) * Math.PI / 180.0);
					b737.setFindDelta_a(trmPrm_Delta_a_CheckField.getValue());
					b737.setDelta_r(Double.valueOf(trmPrm_Delta_rField.getValue()) * Math.PI / 180.0);
					b737.setFindDelta_r(trmPrm_Delta_r_CheckField.getValue());

					b737.getEngineFacade().getEngineWrapper(0).getEngine().setDelta_T(Double.valueOf(trmPrm_Delta_T1Field.getValue()));
					engineWorkingFactor = 0.0;
					if (trmPrm_Delta_T1_CheckField.getValue())
						engineWorkingFactor = 1.0;
					b737.getEngineFacade().getEngineWrapper(0).setEngineWorkingFactor(engineWorkingFactor);

					b737.getEngineFacade().getEngineWrapper(1).getEngine().setDelta_T(Double.valueOf(trmPrm_Delta_T2Field.getValue()));
					engineWorkingFactor = 0.0;
					if (trmPrm_Delta_T2_CheckField.getValue())
						engineWorkingFactor = 1.0;
					b737.getEngineFacade().getEngineWrapper(1).setEngineWorkingFactor(engineWorkingFactor);

					int numOfUnknowns = 0;
					if (trmPrm_AlphaCheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_BetaCheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_ihCheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_Delta_e_CheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_Delta_a_CheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_Delta_r_CheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_Delta_T1_CheckField.getValue())
						numOfUnknowns++;
					if (trmPrm_Delta_T2_CheckField.getValue())
						numOfUnknowns++;

					try {
						if (numOfUnknowns == 6) {
							b737.runNewtonRaphson(0.005);
							trmPrm_AlphaField.setText(String.valueOf(Math.round(1000.0 * b737.getAlpha() * 180.0 / Math.PI) / 1000.0));
							trmPrm_BetaField.setText(String.valueOf(Math.round(1000.0 * b737.getBeta() * 180.0 / Math.PI) / 1000.0));
							trmPrm_ihField.setText(String.valueOf(Math.round(1000.0 * b737.getIh() * 180.0 / Math.PI) / 1000.0));
							trmPrm_Delta_eField.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_e() * 180.0 / Math.PI) / 1000.0));
							trmPrm_Delta_aField.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_a() * 180.0 / Math.PI) / 1000.0));
							trmPrm_Delta_rField.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_r() * 180.0 / Math.PI) / 1000.0));
							trmPrm_Delta_T1Field.setText(String.valueOf(Math.round(1000.0 * b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T()) / 1000.0));
							trmPrm_Delta_T2Field.setText(String.valueOf(Math.round(1000.0 * b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T()) / 1000.0));

							simPrm_ih1Field.setText(String.valueOf(Math.round(1000.0 * b737.getIh() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_e_1Field.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_e() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_a_1Field.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_a() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_r_1Field.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_r() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_T1_1Field.setText(String.valueOf(Math.round(1000.0 * b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T()) / 1000.0));
							simPrm_Delta_T2_1Field.setText(String.valueOf(Math.round(1000.0 * b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T()) / 1000.0));

							simPrm_ih2Field.setText(String.valueOf(Math.round(1000.0 * b737.getIh() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_e_2Field.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_e() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_a_2Field.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_a() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_r_2Field.setText(String.valueOf(Math.round(1000.0 * b737.getDelta_r() * 180.0 / Math.PI) / 1000.0));
							simPrm_Delta_T1_2Field.setText(String.valueOf(Math.round(1000.0 * b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T()) / 1000.0));
							simPrm_Delta_T2_2Field.setText(String.valueOf(Math.round(1000.0 * b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T()) / 1000.0));
							runNewtonRaphsonButton.setEnabled(true);
						} else  {
							errorTextLabel.setText("Number of unknowns must be 6!");
							dialogBox.center();
						}
					} catch (Exception ex) {
						errorTextLabel.setText("Wrong input value");
						dialogBox.center();
					}
				} catch (Exception ex) {
					errorTextLabel.setText("Wrong input value");
					dialogBox.center();
				}
			}
		}

		class FlightSimulationHandler implements ClickHandler, KeyUpHandler {
			public void onClick(ClickEvent event) {
				runFlightSimulation(event.getSource());
			}

			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					runFlightSimulation(event.getSource());
				}
			}

			private void runFlightSimulation(Object sender) {
				runSimulation.setEnabled(false);
				simResultsField.setText("");

				try {
					Aircraft b737 = new Aircraft();
					b737.setWeight(Double.valueOf(trmACWeightField.getValue()));
					b737.setS_w(Double.valueOf(trmACSwField.getValue()));
					b737.setAR(Double.valueOf(trmACARField.getValue()));
					b737.setC_Wing(Double.valueOf(trmACcwField.getValue()));
					b737.setB_Wing(Double.valueOf(trmACbwField.getValue()));

					double engineWorkingFactor = 0.0;
					if (trmPrm_Delta_T1_CheckField.getValue())
						engineWorkingFactor = 1.0;

					b737.setEngine1(Double.valueOf(trmACEngThrust1Field.getValue()), 
			        				Double.valueOf(trmACEngDEngFrontFace1Field.getValue()), 
			        				Double.valueOf(trmACEngXT1Field.getValue()), 
			        				Double.valueOf(trmACEngYT1Field.getValue()), 
			        				Double.valueOf(trmACEngZT1Field.getValue()), 
			        				Double.valueOf(trmACEngFi1Field.getValue()) * Math.PI / 180.0, 
			        				Double.valueOf(trmACEngPs1Field.getValue()) * Math.PI / 180.0,
			        				engineWorkingFactor);
					engineWorkingFactor = 0.0;
					if (trmPrm_Delta_T2_CheckField.getValue())
						engineWorkingFactor = 1.0;
			        b737.setEngine2(Double.valueOf(trmACEngThrust2Field.getValue()), 
			        				Double.valueOf(trmACEngDEngFrontFace2Field.getValue()), 
			        				Double.valueOf(trmACEngXT2Field.getValue()), 
			        				Double.valueOf(trmACEngYT2Field.getValue()), 
			        				Double.valueOf(trmACEngZT2Field.getValue()), 
			        				Double.valueOf(trmACEngFi2Field.getValue()) * Math.PI / 180.0, 
			        				Double.valueOf(trmACEngPs2Field.getValue()) * Math.PI / 180.0,
			        				engineWorkingFactor);

					b737.set_C_D0(Double.valueOf(trmAD_CD0Field.getValue()));
					b737.setOswaldEfficiency(Double.valueOf(trmAD_eField.getValue()));

					b737.setC_L0(Double.valueOf(trmAD_CL0Field.getValue()));
					b737.setC_L_Alpha(Double.valueOf(trmAD_CL_AlphaField.getValue()));
					b737.setC_L_ih(Double.valueOf(trmAD_CL_ihField.getValue()));
					b737.setC_L_Delta_e(Double.valueOf(trmAD_CL_Delta_eField.getValue()));
					b737.setC_m0(Double.valueOf(trmAD_Cm0Field.getValue()));
					b737.setC_m_Alpha(Double.valueOf(trmAD_Cm_AlphaField.getValue()));
					b737.setC_m_ih(Double.valueOf(trmAD_Cm_ihField.getValue()));
					b737.setC_m_Delta_e(Double.valueOf(trmAD_Cm_Delta_eField.getValue()));
					b737.setC_y_0(Double.valueOf(trmAD_Cy0Field.getValue()));
					b737.setC_y_Beta(Double.valueOf(trmAD_Cy_BetaField.getValue()));
					b737.setC_y_Delta_a(Double.valueOf(trmAD_Cy_Delta_aField.getValue()));
					b737.setC_y_Delta_r(Double.valueOf(trmAD_Cy_Delta_rField.getValue()));
					b737.setC_l0(Double.valueOf(trmAD_Cl0Field.getValue()));
					b737.setC_l_Beta(Double.valueOf(trmAD_Cl_BetaField.getValue()));
					b737.setC_l_Delta_a(Double.valueOf(trmAD_Cl_Delta_aField.getValue()));
					b737.setC_l_Delta_r(Double.valueOf(trmAD_Cl_Delta_rField.getValue()));
					b737.setC_n0(Double.valueOf(trmAD_Cn0Field.getValue()));
					b737.setC_n_Beta(Double.valueOf(trmAD_Cn_BetaField.getValue()));
					b737.setC_n_Delta_a(Double.valueOf(trmAD_Cn_Delta_aField.getValue()));
					b737.setC_n_Delta_r(Double.valueOf(trmAD_Cn_Delta_rField.getValue()));

					b737.setIxx(Double.valueOf(simIxxField.getValue()));
					b737.setIyy(Double.valueOf(simIyyField.getValue()));
					b737.setIzz(Double.valueOf(simIzzField.getValue()));
					b737.setIxz(Double.valueOf(simIxzField.getValue()));

					b737.setC_L_Alpha_Dot(Double.valueOf(trmAD_CL_Alpha_DotField.getValue()));
					b737.setC_L_q(Double.valueOf(trmAD_CL_qField.getValue()));
					b737.setC_D_Alpha_Dot(Double.valueOf(trmAD_CD_Alpha_DotField.getValue()));
					b737.setC_D_q(Double.valueOf(trmAD_CD_qField.getValue()));
					b737.setC_m_Alpha_Dot(Double.valueOf(trmAD_Cm_Alpha_DotField.getValue()));
					b737.setC_m_q(Double.valueOf(trmAD_Cm_qField.getValue()));
					b737.setC_l_Beta_Dot(Double.valueOf(trmAD_C_l_Beta_DotField.getValue()));
					b737.setC_l_p(Double.valueOf(trmAD_C_l_pField.getValue()));
					b737.setC_l_r(Double.valueOf(trmAD_C_l_rField.getValue()));
					b737.setC_y_Beta_Dot(Double.valueOf(trmAD_C_y_Beta_DotField.getValue()));
					b737.setC_y_p(Double.valueOf(trmAD_C_y_pField.getValue()));
					b737.setC_y_r(Double.valueOf(trmAD_C_y_rField.getValue()));
					b737.setC_n_Beta_Dot(Double.valueOf(trmAD_C_n_Beta_DotField.getValue()));
					b737.setC_n_p(Double.valueOf(trmAD_C_n_pField.getValue()));
					b737.setC_n_r(Double.valueOf(trmAD_C_n_rField.getValue()));

					b737.setMach(Double.valueOf(trmPrm_MachField.getValue()));
					b737.setGama(Double.valueOf(trmPrm_GamaField.getValue()) * Math.PI / 180.0);
					b737.setOdeFi(Double.valueOf(trmPrm_FiField.getValue()) * Math.PI / 180.0);
					b737.setAlpha(Double.valueOf(trmPrm_AlphaField.getValue()) * Math.PI / 180.0);
					b737.setBeta(Double.valueOf(trmPrm_BetaField.getValue()) * Math.PI / 180.0);
					b737.setOdeTheta(b737.getAlpha() + b737.getGama());


					b737.setIh(Double.valueOf(simPrm_ih1Field.getValue()) * Math.PI / 180.0);
					b737.setDelta_e(Double.valueOf(simPrm_Delta_e_1Field.getValue()) * Math.PI / 180.0);
					b737.setDelta_a(Double.valueOf(simPrm_Delta_a_1Field.getValue()) * Math.PI / 180.0);
					b737.setDelta_r(Double.valueOf(simPrm_Delta_r_1Field.getValue()) * Math.PI / 180.0);

					b737.getEngineFacade().getEngineWrapper(0).getEngine().setDelta_T(Double.valueOf(simPrm_Delta_T1_1Field.getValue()));
					engineWorkingFactor = 0.0;
					if (Double.valueOf(simPrm_Delta_T1_1Field.getValue()) > 0.0)
						engineWorkingFactor = 1.0;
					b737.getEngineFacade().getEngineWrapper(0).setEngineWorkingFactor(engineWorkingFactor);

					b737.getEngineFacade().getEngineWrapper(1).getEngine().setDelta_T(Double.valueOf(simPrm_Delta_T2_1Field.getValue()));
					engineWorkingFactor = 0.0;
					if (Double.valueOf(simPrm_Delta_T2_1Field.getValue()) > 0.0)
						engineWorkingFactor = 1.0;
					b737.getEngineFacade().getEngineWrapper(1).setEngineWorkingFactor(engineWorkingFactor);


			        double fc1_ih = Double.valueOf(simPrm_ih1Field.getValue()) * Math.PI / 180.0;
			        double fc1_Delta_e = Double.valueOf(simPrm_Delta_e_1Field.getValue()) * Math.PI / 180.0;
			        double fc1_Delta_a = Double.valueOf(simPrm_Delta_a_1Field.getValue()) * Math.PI / 180.0;
			        double fc1_Delta_r = Double.valueOf(simPrm_Delta_r_1Field.getValue()) * Math.PI / 180.0;
			        double fc1_Delta_t1 = Double.valueOf(simPrm_Delta_T1_1Field.getValue());
			        double fc1_Delta_t2 = Double.valueOf(simPrm_Delta_T2_1Field.getValue());
			        double fc1_EngineWorkingFactor1 = 0.0;
			        if (fc1_Delta_t1 > 0.0)
			        	fc1_EngineWorkingFactor1 = 1.0;
			        double fc1_EngineWorkingFactor2 = 0.0;
			        if (fc1_Delta_t2 > 0.0)
			        	fc1_EngineWorkingFactor2 = 1.0;

			        double fc2_ih = Double.valueOf(simPrm_ih2Field.getValue()) * Math.PI / 180.0;
			        double fc2_Delta_e = Double.valueOf(simPrm_Delta_e_2Field.getValue()) * Math.PI / 180.0;
			        double fc2_Delta_a = Double.valueOf(simPrm_Delta_a_2Field.getValue()) * Math.PI / 180.0;
			        double fc2_Delta_r = Double.valueOf(simPrm_Delta_r_2Field.getValue()) * Math.PI / 180.0;
			        double fc2_Delta_t1 = Double.valueOf(simPrm_Delta_T1_2Field.getValue());
			        double fc2_Delta_t2 = Double.valueOf(simPrm_Delta_T2_2Field.getValue());
			        double fc2_EngineWorkingFactor1 = 0.0;
			        if (fc2_Delta_t1 > 0.0)
			        	fc2_EngineWorkingFactor1 = 1.0;
			        double fc2_EngineWorkingFactor2 = 0.0;
			        if (fc2_Delta_t2 > 0.0)
			        	fc2_EngineWorkingFactor2 = 1.0;

			        int simulationPeriod = 200;    //  in secs
			        int controlInputStartTime = Integer.valueOf(simPrm_Time1Field.getText());    //  in secs
			        int controlInputEndTime = Integer.valueOf(simPrm_Time2Field.getText());    //  in secs
			        int controlPeriod = controlInputEndTime - controlInputStartTime;

			        double ih_Step = (fc2_ih - fc1_ih) / controlPeriod;
			        double delta_e_Step = (fc2_Delta_e - fc1_Delta_e) / controlPeriod;
			        double delta_a_Step = (fc2_Delta_a - fc1_Delta_a) / controlPeriod;
			        double delta_r_Step = (fc2_Delta_r - fc1_Delta_r) / controlPeriod;
			        double delta_t1_Step = (fc2_Delta_t1 - fc1_Delta_t1) / controlPeriod;
			        double delta_t2_Step = (fc2_Delta_t2 - fc1_Delta_t2) / controlPeriod;
			        double delta_engineWorkingFactor1_Step = (fc2_EngineWorkingFactor1 - fc1_EngineWorkingFactor1) / controlPeriod;
			        double delta_engineWorkingFactor2_Step = (fc2_EngineWorkingFactor2 - fc1_EngineWorkingFactor2) / controlPeriod;

			        StringBuilder sbResults = new StringBuilder(); 

			        double[] valuesOfImgU = new double[simulationPeriod + 1];
			        double[] valuesOfImgV = new double[simulationPeriod + 1];
			        double[] valuesOfImgW = new double[simulationPeriod + 1];
			        double[] valuesOfImgP = new double[simulationPeriod + 1];
			        double[] valuesOfImgQ = new double[simulationPeriod + 1];
			        double[] valuesOfImgR = new double[simulationPeriod + 1];
			        double[] valuesOfImgPhi = new double[simulationPeriod + 1];
			        double[] valuesOfImgTheta = new double[simulationPeriod + 1];
			        double[] valuesOfImgPsi = new double[simulationPeriod + 1];
			        double[] valuesOfImgX = new double[simulationPeriod + 1];
			        double[] valuesOfImgY = new double[simulationPeriod + 1];
			        double[] valuesOfImgZ = new double[simulationPeriod + 1];

			        double[] valuesOfImgAlpha = new double[simulationPeriod + 1];
			        double[] valuesOfImgBeta = new double[simulationPeriod + 1];

			        double[] valuesOfImgIh = new double[simulationPeriod + 1];
			        double[] valuesOfImgDelta_e = new double[simulationPeriod + 1];
			        double[] valuesOfImgDelta_a = new double[simulationPeriod + 1];
			        double[] valuesOfImgDelta_r = new double[simulationPeriod + 1];
			        double[] valuesOfImgDelta_t1 = new double[simulationPeriod + 1];
			        double[] valuesOfImgDelta_t2 = new double[simulationPeriod + 1];

			        double[] valuesOfImgL = new double[simulationPeriod + 1];
			        double[] valuesOfImgD = new double[simulationPeriod + 1];
			        double[] valuesOfImgT = new double[simulationPeriod + 1];

			        double[] valuesOfImgLx = new double[simulationPeriod + 1];
			        double[] valuesOfImgMy = new double[simulationPeriod + 1];
			        double[] valuesOfImgNz = new double[simulationPeriod + 1];

			        double[] valuesOfImgMA = new double[simulationPeriod + 1];
			        double[] valuesOfImgMT = new double[simulationPeriod + 1];
			        double[] valuesOfImgMAT = new double[simulationPeriod + 1];

			        try {
						if ((controlInputEndTime > controlInputStartTime)
								&& (controlInputStartTime >= 0)
								&& (controlInputEndTime >= 0)
								&& (controlInputStartTime <= simulationPeriod)
								&& (controlInputEndTime <= simulationPeriod)) {

							b737.initializeSimulation();

							valuesOfImgU[0] = Math.round(b737.getOdeU() * 1000.0) / 1000.0;
							valuesOfImgV[0] = Math.round(b737.getOdeV() * 1000.0) / 1000.0;
							valuesOfImgW[0] = Math.round(b737.getOdeW() * 1000.0) / 1000.0;
							valuesOfImgP[0] = Math.round(b737.getOdeP() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgQ[0] = Math.round(b737.getOdeQ() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgR[0] = Math.round(b737.getOdeR() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgPhi[0] = Math.round(b737.getOdeFi() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgTheta[0] = Math.round(b737.getOdeTheta() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgPsi[0] = Math.round(b737.getOdePsi() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgX[0] = Math.round(b737.getOdeX() * 1000.0) / 1000.0;
							valuesOfImgY[0] = Math.round(b737.getOdeY() * 1000.0) / 1000.0;
							valuesOfImgZ[0] = Math.round(b737.getOdeZ() * 1000.0) / 1000.0;

							valuesOfImgAlpha[0] = Math.round(b737.getAlpha() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgBeta[0] = Math.round(b737.getBeta() * 180000.0 / Math.PI) / 1000.0;

							valuesOfImgIh[0] = Math.round(b737.getIh() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgDelta_e[0] = Math.round(b737.getDelta_e() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgDelta_a[0] = Math.round(b737.getDelta_a() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgDelta_r[0] = Math.round(b737.getDelta_r() * 180000.0 / Math.PI) / 1000.0;
							valuesOfImgDelta_t1[0] = Math.round(b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T() * 1000.0) / 1000.0;
							valuesOfImgDelta_t2[0] = Math.round(b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T() * 1000.0) / 1000.0;

				            double L = b737.getLiftForceAero(b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
				            double D = b737.getDragForceAero(b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
				            D += b737.getEngineFacade().getEngineWrapper(0).getDragForceEngine(
				            		b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
				            D += b737.getEngineFacade().getEngineWrapper(1).getDragForceEngine(
				            		b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
				            double T = b737.getEngineFacade().getEngineWrapper(0).getThrustForceEngine();
				            T += b737.getEngineFacade().getEngineWrapper(1).getThrustForceEngine();

				            valuesOfImgL[0] = Math.round(L * 1000.0) / 1000.0;
							valuesOfImgD[0] = Math.round(D * 1000.0) / 1000.0;
							valuesOfImgT[0] = Math.round(T * 1000.0) / 1000.0;

				            valuesOfImgMA[0] = Math.round(b737.getAeroMomentY(
				            								b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) * 1000.0) / 1000.0;

				            valuesOfImgMT[0] = Math.round((b737.getEngineFacade().getEngineWrapper(0).getThrustMomentY(
				            								b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
				                            				b737.getEngineFacade().getEngineWrapper(1).getThrustMomentY(
				                            				b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) * 1000.0) / 1000.0;
				
				            valuesOfImgMAT[0] = Math.round((b737.getAeroMomentY(
															b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
															b737.getEngineFacade().getEngineWrapper(0).getThrustMomentY(
															b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
															b737.getEngineFacade().getEngineWrapper(1).getThrustMomentY(
															b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) * 1000.0) / 1000.0;

            				for (int i = 1; i <= simulationPeriod; i++) {
					            if ((i > controlInputStartTime)
					                    && (i < (controlInputEndTime + 1))) {

					                b737.setIh(b737.getIh() + ih_Step);
					                b737.setDelta_e(b737.getDelta_e() + delta_e_Step);
					                b737.setDelta_a(b737.getDelta_a() + delta_a_Step);
					                b737.setDelta_r(b737.getDelta_r() + delta_r_Step);

					                b737.getEngineFacade().getEngineWrapper(0).getEngine().setDelta_T(
					                        b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T() +
					                        delta_t1_Step);
					                b737.getEngineFacade().getEngineWrapper(0).setEngineWorkingFactor(
					                        b737.getEngineFacade().getEngineWrapper(0).getEngineWorkingFactor() +
					                        delta_engineWorkingFactor1_Step);
					                b737.getEngineFacade().getEngineWrapper(1).getEngine().setDelta_T(
					                        b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T() +
					                        delta_t2_Step);
					                b737.getEngineFacade().getEngineWrapper(1).setEngineWorkingFactor(
					                        b737.getEngineFacade().getEngineWrapper(1).getEngineWorkingFactor() +
					                        delta_engineWorkingFactor2_Step);

					            }

					            b737.runRungeKutta4(i);

					            if (b737.getOdeZ() >= 0)
					                break;

								valuesOfImgU[i] = Math.round(b737.getOdeU() * 1000.0) / 1000.0;
								valuesOfImgV[i] = Math.round(b737.getOdeV() * 1000.0) / 1000.0;
								valuesOfImgW[i] = Math.round(b737.getOdeW() * 1000.0) / 1000.0;
								valuesOfImgP[i] = Math.round(b737.getOdeP() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgQ[i] = Math.round(b737.getOdeQ() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgR[i] = Math.round(b737.getOdeR() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgPhi[i] = Math.round(b737.getOdeFi() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgTheta[i] = Math.round(b737.getOdeTheta() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgPsi[i] = Math.round(b737.getOdePsi() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgX[i] = Math.round(b737.getOdeX() * 1000.0) / 1000.0;
								valuesOfImgY[i] = Math.round(b737.getOdeY() * 1000.0) / 1000.0;
								valuesOfImgZ[i] = Math.round(b737.getOdeZ() * 1000.0) / 1000.0;

								valuesOfImgAlpha[i] = Math.round(b737.getAlpha() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgBeta[i] = Math.round(b737.getBeta() * 180000.0 / Math.PI) / 1000.0;

								valuesOfImgIh[i] = Math.round(b737.getIh() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgDelta_e[i] = Math.round(b737.getDelta_e() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgDelta_a[i] = Math.round(b737.getDelta_a() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgDelta_r[i] = Math.round(b737.getDelta_r() * 180000.0 / Math.PI) / 1000.0;
								valuesOfImgDelta_t1[i] = Math.round(b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T() * 1000.0) / 1000.0;
								valuesOfImgDelta_t2[i] = Math.round(b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T() * 1000.0) / 1000.0;

					            L = b737.getLiftForceAero(b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
					            D = b737.getDragForceAero(b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
					            D += b737.getEngineFacade().getEngineWrapper(0).getDragForceEngine(
					            		b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
					            D += b737.getEngineFacade().getEngineWrapper(1).getDragForceEngine(
					            		b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()));
					            T = b737.getEngineFacade().getEngineWrapper(0).getThrustForceEngine();
					            T += b737.getEngineFacade().getEngineWrapper(1).getThrustForceEngine();

					            valuesOfImgL[i] = Math.round(L * 1000.0) / 1000.0;
								valuesOfImgD[i] = Math.round(D * 1000.0) / 1000.0;
								valuesOfImgT[i] = Math.round(T * 1000.0) / 1000.0;

					            valuesOfImgLx[i] = Math.round(b737.getTotalXMoment(
					            						b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) * 1000.0) / 1000.0;
								valuesOfImgMy[i] = Math.round(b737.getTotalYMoment(
														b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) * 1000.0) / 1000.0;
								valuesOfImgNz[i] = Math.round(b737.getTotalZMoment(
	            										b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) * 1000.0) / 1000.0;

					            valuesOfImgMA[i] = Math.round(b737.getAeroMomentY(
					            						b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) * 1000.0) / 1000.0;

					            valuesOfImgMT[i] = Math.round((b737.getEngineFacade().getEngineWrapper(0).getThrustMomentY(
					            								b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
                                                				b737.getEngineFacade().getEngineWrapper(1).getThrustMomentY(
                                                				b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) * 1000.0) / 1000.0;

					            valuesOfImgMAT[i] = Math.round((b737.getAeroMomentY(
	            												b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
	            												b737.getEngineFacade().getEngineWrapper(0).getThrustMomentY(
        														b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
        														b737.getEngineFacade().getEngineWrapper(1).getThrustMomentY(
        														b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) * 1000.0) / 1000.0;

								String result = String.valueOf(i) + "\t" +
				            	"U = " + String.valueOf(b737.getOdeU()) + "\t" +
	                            "V = " + String.valueOf(b737.getOdeV()) + "\t" +
	                            "W = " + String.valueOf(b737.getOdeW()) + "\t" +
	                            "P = " + String.valueOf(b737.getOdeP() * 180.0 / Math.PI) + "\t" +
	                            "Q = " + String.valueOf(b737.getOdeQ() * 180.0 / Math.PI) + "\t" +
	                            "R = " + String.valueOf(b737.getOdeR() * 180.0 / Math.PI) + "\t" +
	                            "Fi = " + String.valueOf(b737.getOdeFi() * 180.0 / Math.PI) + "\t" +
	                            "Theta = " + String.valueOf(b737.getOdeTheta() * 180.0 / Math.PI) + "\t" +
	                            "Psi = " + String.valueOf(b737.getOdePsi() * 180.0 / Math.PI) + "\t" +
	                            "X = " + String.valueOf(b737.getOdeX()) + "\t" +
	                            "Y = " + String.valueOf(b737.getOdeY()) + "\t" +
	                            "Z = " + String.valueOf(b737.getOdeZ()) + "\t" +

	                            "L = " + String.valueOf(L) + "\t" +
	                            "D = " + String.valueOf(D) + "\t" +
	                            "T = " + String.valueOf(T) + "\t" +

	                            "MA = " + String.valueOf(
	                            		b737.getAeroMomentY(
	                                        	b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) + "\t" +
	                            "MT = " + String.valueOf(
	                            		b737.getEngineFacade().getEngineWrapper(0).getThrustMomentY(
	                                        	b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
	                                        	b737.getEngineFacade().getEngineWrapper(1).getThrustMomentY(
	                                        	b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) + "\t" +
	                            "MA+MT = " + String.valueOf(
	                            		b737.getAeroMomentY(
	                                        	b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
	                                        	b737.getEngineFacade().getEngineWrapper(0).getThrustMomentY(
	                                        	b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T())) +
	                                        	b737.getEngineFacade().getEngineWrapper(1).getThrustMomentY(
	                                        	b737.dynamicPressureByTrueAirVelocity(b737.getV_Air_T()))) + "\t" +

	                            "Alpha = " + String.valueOf(b737.getAlpha() * 180.0 / Math.PI) + "\t" +
	                            "Beta = " + String.valueOf(b737.getBeta() * 180.0 / Math.PI) + "\t" +

	                            "ih = " + String.valueOf(b737.getIh() * 180.0 / Math.PI) + "\t" +
	                            "Delta_e = " + String.valueOf(b737.getDelta_e() * 180.0 / Math.PI) + "\t" +
	                            "Delta_a = " + String.valueOf(b737.getDelta_a() * 180.0 / Math.PI) + "\t" +
	                            "Delta_r = " + String.valueOf(b737.getDelta_r() * 180.0 / Math.PI) + "\t" +
	                            "Delta_t1 = " + String.valueOf(b737.getEngineFacade().getEngineWrapper(0).getEngine().getDelta_T()) + "\t" +
	                            "Delta_t2 = " + String.valueOf(b737.getEngineFacade().getEngineWrapper(1).getEngine().getDelta_T());

					            sbResults.append(result + "\r\n");

							}
				            simResultsField.setText(sbResults.toString());

				            String myDebugStr = generateGraphUrl("U", valuesOfImgU);
				            imgU.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("V", valuesOfImgV);
				            imgV.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("W", valuesOfImgW);
				            imgW.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("P", valuesOfImgP);
				            imgP.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Q", valuesOfImgQ);
				            imgQ.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("R", valuesOfImgR);
				            imgR.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Phi", valuesOfImgPhi);
				            imgPhi.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Theta", valuesOfImgTheta);
				            imgTheta.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Psi", valuesOfImgPsi);
				            imgPsi.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("X", valuesOfImgX);
				            imgX.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Y", valuesOfImgY);
				            imgY.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Z", valuesOfImgZ);
				            imgZ.setUrl(myDebugStr);


				            myDebugStr = generateGraphUrl("Alpha", valuesOfImgAlpha);
				            imgAlpha.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Beta", valuesOfImgBeta);
				            imgBeta.setUrl(myDebugStr);


				            myDebugStr = generateGraphUrl("Ih", valuesOfImgIh);
				            imgIh.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Delta_e", valuesOfImgDelta_e);
				            imgDelta_e.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Delta_a", valuesOfImgDelta_a);
				            imgDelta_a.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Delta_r", valuesOfImgDelta_r);
				            imgDelta_r.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Delta_t1", valuesOfImgDelta_t1);
				            imgDelta_t1.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Delta_t2", valuesOfImgDelta_t2);
				            imgDelta_t2.setUrl(myDebugStr);


				            myDebugStr = generateGraphUrl("Lift", valuesOfImgL);
				            imgL.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Drag", valuesOfImgD);
				            imgD.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Thrust", valuesOfImgT);
				            imgT.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("Lx", valuesOfImgLx);
				            imgLx.setUrl(myDebugStr);
				            myDebugStr = generateGraphUrl("My", valuesOfImgMy);
				            imgMy.setUrl(myDebugStr);
				            myDebugStr = generateGraphUrl("Nz", valuesOfImgNz);
				            imgNz.setUrl(myDebugStr);

				            myDebugStr = generateGraphUrl("MA", valuesOfImgMA);
				            imgMA.setUrl(myDebugStr);
				            myDebugStr = generateGraphUrl("MT", valuesOfImgMT);
				            imgMT.setUrl(myDebugStr);
				            myDebugStr = generateGraphUrl("MA+MT", valuesOfImgMAT);
				            imgMAT.setUrl(myDebugStr);

				            imgU.setVisible(true);
				    		imgV.setVisible(true);
				    		imgW.setVisible(true);
				    		imgP.setVisible(true);
				    		imgQ.setVisible(true);
				    		imgR.setVisible(true);
				    		imgPhi.setVisible(true);
				    		imgTheta.setVisible(true);
				    		imgPsi.setVisible(true);
				    		imgX.setVisible(true);
				    		imgY.setVisible(true);
				    		imgZ.setVisible(true);
				    		imgAlpha.setVisible(true);
				    		imgBeta.setVisible(true);
				    		imgIh.setVisible(true);
				    		imgDelta_e.setVisible(true);
				    		imgDelta_a.setVisible(true);
				    		imgDelta_r.setVisible(true);
				    		imgDelta_t1.setVisible(true);
				    		imgDelta_t2.setVisible(true);
				    		imgL.setVisible(true);
				    		imgD.setVisible(true);
				    		imgT.setVisible(true);

				    		imgLx.setVisible(true);
				    		imgMy.setVisible(true);
				    		imgNz.setVisible(true);

				    		imgMA.setVisible(true);
				    		imgMT.setVisible(true);
				    		imgMAT.setVisible(true);

				    		runSimulation.setEnabled(true);
						} else  {
							errorTextLabel.setText("Invalid time input!");
							dialogBox.center();
						}
					} catch (Exception ex) {
						errorTextLabel.setText("Wrong input value");
						dialogBox.center();
					}
				} catch (Exception ex) {
					errorTextLabel.setText("Wrong input value");
					dialogBox.center();
				}
			}
		}

		// Add a handler to send the name to the server
		EarthToBodyHandler handler = new EarthToBodyHandler();
		calculateBodyVectorButton.addClickHandler(handler);
		calculateEarthVectorButton.addClickHandler(handler);
		earthXField.addKeyUpHandler(handler);
		earthYField.addKeyUpHandler(handler);
		earthZField.addKeyUpHandler(handler);
		bodyXField.addKeyUpHandler(handler);
		bodyYField.addKeyUpHandler(handler);
		bodyZField.addKeyUpHandler(handler);

		TrimHandler trimHandler = new TrimHandler();
		runNewtonRaphsonButton.addClickHandler(trimHandler);

		FlightSimulationHandler flightSimulationHandler = new FlightSimulationHandler();
		runSimulation.addClickHandler(flightSimulationHandler);
		
	}

	private String generateGraphUrl(String valueLabel, double[] values) {
		double maxValue = - Double.MAX_VALUE;
		double minValue = Double.MAX_VALUE;

		for (int i = 0; i < values.length; i++) {
			if (values[i] > maxValue)
				maxValue = values[i];
			if (values[i] < minValue)
				minValue = values[i];
		}

		double diffValue = maxValue - minValue;
		double quart1Value = minValue + diffValue / 4;
		double midValue = minValue + diffValue / 2;
		double quart2Value = minValue + 3 * diffValue / 4;

		String baseUrl = "http://chart.apis.google.com/chart?cht=lc&chs=330x200&chtt=" + valueLabel +
		"&chxt=x,y,x,y&chxl=0:|0|50|100|150|200|" + 
		"1:|" + 
		String.valueOf(minValue) + "|" +
		String.valueOf(quart1Value) + "|" +
		String.valueOf(midValue) + "|" +
		String.valueOf(quart2Value) + "|" +
		String.valueOf(maxValue) + "|" +
		"2:|t|3:|" + valueLabel +
		"|&chxr=1," +
		String.valueOf(String.valueOf(Math.round(1000.0 * minValue) / 1000.0)) + "|" +
		String.valueOf(String.valueOf(Math.round(1000.0 * maxValue) / 1000.0)) + "|" +
		String.valueOf(String.valueOf(Math.round(1000.0 * (maxValue - minValue) / 4.0) / 1000.0)) + "|" +
		"&chxp=2,50|3,50&chd=t:";

		StringBuilder sbValues = new StringBuilder();
		sbValues.append(String.valueOf(Math.round(100000.0 * (values[0] - minValue) / diffValue) / 1000.0));

		for (int i = 1; i < values.length; i++) {
			sbValues.append("," + String.valueOf(Math.round(100000.0 * (values[i] - minValue) / diffValue) / 1000.0));
		}

		return baseUrl + sbValues.toString();
	}
}
