/*
 * This file is part of the Deterministic Network Calculator (DNC).
 *
 * Copyright (C) 2017 - 2018 The DiscoDNC contributors
 * Copyright (C) 2019+ The DNC contributors
 *
 * http://networkcalculus.org
 *
 *
 * The Deterministic Network Calculator (DNC) is free software;
 * you can redistribute it and/or modify it under the terms of the 
 * GNU Lesser General Public License as published by the Free Software Foundation; 
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package org.networkcalculus.dnc;

import java.io.File;

import org.networkcalculus.dnc.AlgDncBackend;
import org.networkcalculus.dnc.algebra.MinPlus;
import org.networkcalculus.dnc.algebra.disco.MinPlus_Disco_ConPwAffine;
import org.networkcalculus.dnc.bounds.BoundingCurves;
import org.networkcalculus.dnc.bounds.Bounds;
import org.networkcalculus.dnc.bounds.disco.BoundingCurves_Disco_ConPwAffine;
import org.networkcalculus.dnc.bounds.disco.Bounds_Disco_PwAffine;
import org.networkcalculus.dnc.curves.Curve_PwAffine;
import org.networkcalculus.dnc.curves.LinearSegment;
import org.networkcalculus.dnc.curves.mpa_rtc.LinearSegment_MPARTC;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.Curve_MPARTC_PwAffine;

public enum AlgDncBackend_MPARTC_DISCO_PwAffine implements AlgDncBackend {
	MPARTC_PWAFFINEC_DISCO_CONPWAFFINEMP;

	@Override
	public MinPlus getMinPlus() {
		return MinPlus_Disco_ConPwAffine.MINPLUS_DISCO_CONPWAFFINE;
	}

	/**
	 * See Github issue #15: Dispatch to Native Bounding Methods
	 * https://github.com/NetCal/DNCext_MPARTC/issues/15
	 */
	@Override
	public BoundingCurves getBoundingCurves() {
		return BoundingCurves_Disco_ConPwAffine.BOUNDINGCURVES_DISCO_CONPWAFFINE;
	}

	/**
	 * See Github issue #15: Dispatch to Native Bounding Methods
	 * https://github.com/NetCal/DNCext_MPARTC/issues/15
	 */
	@Override
	public Bounds getBounds() {
		return Bounds_Disco_PwAffine.BOUNDS_DISCO_PWAFFINE;
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
		return LinearSegment_MPARTC.getBuilder();
	}

    @Override
    public String toString() {
        return assembleString(this.name(), MinPlus_Disco_ConPwAffine.MINPLUS_DISCO_CONPWAFFINE.name());
    }
}
