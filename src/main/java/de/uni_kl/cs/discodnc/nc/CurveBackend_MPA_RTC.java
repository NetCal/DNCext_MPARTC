package de.uni_kl.cs.discodnc.nc;

import java.io.File;

import de.uni_kl.cs.discodnc.CurveBackend;
import de.uni_kl.cs.discodnc.curves.CurvePwAffine;
import de.uni_kl.cs.discodnc.curves.LinearSegment;
import de.uni_kl.cs.discodnc.curves.mpa_rtc_pwaffine.Curve_MPARTC_PwAffine;
import de.uni_kl.cs.discodnc.curves.mpa_rtc_pwaffine.LinearSegment_MPARTC_PwAffine;
import de.uni_kl.cs.discodnc.minplus.MinPlus;
import de.uni_kl.cs.discodnc.minplus.MinPlus_MPA_RTC;

public enum CurveBackend_MPA_RTC implements CurveBackend {
	MPA_RTC;

	@Override
	public MinPlus getMinPlus() {
		return MinPlus_MPA_RTC.MINPLUS_MPA_RTC;
	}

	@Override
	public CurvePwAffine getCurveFactory() {
		return Curve_MPARTC_PwAffine.getFactory();
	}

	@Override 
	public void checkDependencies() {
		String classpath = System.getProperty("java.class.path");
		for (String classpathEntry : classpath.split(File.pathSeparator)) {
			if (classpathEntry.contains("rtc.jar")) {
				return;
			}
		}
		throw new RuntimeException("rtc.jar cannot be found on the classpath!");
	}
	
	@Override
	public LinearSegment.Builder getLinearSegmentFactory() {
		return LinearSegment_MPARTC_PwAffine.getBuilder();
	}
}
