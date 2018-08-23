package de.uni_kl.cs.discodnc;

import java.io.File;

import de.uni_kl.cs.discodnc.AlgDncBackend;
import de.uni_kl.cs.discodnc.algebra.MinPlus;
import de.uni_kl.cs.discodnc.curves.Curve_PwAffine;
import de.uni_kl.cs.discodnc.curves.LinearSegment;
import de.uni_kl.cs.discodnc.curves.mpartc.pwaffine.Curve_MPARTC_PwAffine;
import de.uni_kl.cs.discodnc.curves.mpartc.pwaffine.LinearSegment_MPARTC_PwAffine;
import de.uni_kl.cs.discodnc.minplus.mpa_rtc.MinPlus_MPARTC;

public enum AlgDncBackend_MPARTC_PwAffine implements AlgDncBackend {
	MPARTC_PWAFFINE;

	@Override
	public MinPlus getMinPlus() {
		return MinPlus_MPARTC.MINPLUS_MPARTC;
	}

	@Override
	public Curve_PwAffine getCurveFactory() {
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
