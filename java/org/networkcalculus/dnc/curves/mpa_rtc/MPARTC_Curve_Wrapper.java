package org.networkcalculus.dnc.curves.mpa_rtc;

import ch.ethz.rtc.kernel.Curve;

public abstract class MPARTC_Curve_Wrapper {
	protected Curve rtc_curve;

	public Curve getRtc_curve() {
		return rtc_curve;
	}
}
