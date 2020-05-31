package org.fuzzy.invpend.opt.run.solution;

public class ControllerParams {
	private MembershipParams tMfNMin = null;
	private MembershipParams tMfN1 = null;
	private MembershipParams tMf0 = null;
	private MembershipParams tMfP1 = null;
	private MembershipParams tMfPMax = null;

	private MembershipParams dMfNMin = null;
	private MembershipParams dMf0 = null;
	private MembershipParams dMfPMax = null;

	private MembershipParams fMfNMin = null;
	private MembershipParams fMfN2 = null;
	private MembershipParams fMfN1 = null;
	private MembershipParams fMf0 = null;
	private MembershipParams fMfP1 = null;
	private MembershipParams fMfP2 = null;
	private MembershipParams fMfPMax = null;

	public ControllerParams(MembershipParams tMfNMin, MembershipParams tMfN1,
			MembershipParams tMf0, MembershipParams tMfP1, MembershipParams tMfPMax,
			MembershipParams dMfNMin, MembershipParams dMf0, MembershipParams dMfPMax,
			MembershipParams fMfNMin, MembershipParams fMfN2, MembershipParams fMfN1,
			MembershipParams fMf0, MembershipParams fMfP1, MembershipParams fMfP2,
			MembershipParams fMfPMax) {
		this.tMfNMin = tMfNMin;
		this.tMfN1 = tMfN1;
		this.tMf0 = tMf0;
		this.tMfP1 = tMfP1;
		this.tMfPMax = tMfPMax;
		this.dMfNMin = dMfNMin;
		this.dMf0 = dMf0;
		this.dMfPMax = dMfPMax;
		this.fMfNMin = fMfNMin;
		this.fMfN2 = fMfN2;
		this.fMfN1 = fMfN1;
		this.fMf0 = fMf0;
		this.fMfP1 = fMfP1;
		this.fMfP2 = fMfP2;
		this.fMfPMax = fMfPMax;
	}

	public MembershipParams gettMfNMin() {
		return tMfNMin;
	}

	public MembershipParams gettMfN1() {
		return tMfN1;
	}

	public MembershipParams gettMf0() {
		return tMf0;
	}

	public MembershipParams gettMfP1() {
		return tMfP1;
	}

	public MembershipParams gettMfPMax() {
		return tMfPMax;
	}

	public MembershipParams getdMfNMin() {
		return dMfNMin;
	}

	public MembershipParams getdMf0() {
		return dMf0;
	}

	public MembershipParams getdMfPMax() {
		return dMfPMax;
	}

	public MembershipParams getfMfNMin() {
		return fMfNMin;
	}

	public MembershipParams getfMfN2() {
		return fMfN2;
	}

	public MembershipParams getfMfN1() {
		return fMfN1;
	}

	public MembershipParams getfMf0() {
		return fMf0;
	}

	public MembershipParams getfMfP1() {
		return fMfP1;
	}

	public MembershipParams getfMfP2() {
		return fMfP2;
	}

	public MembershipParams getfMfPMax() {
		return fMfPMax;
	}
}
