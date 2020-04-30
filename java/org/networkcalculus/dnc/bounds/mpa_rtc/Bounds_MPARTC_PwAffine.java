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

package org.networkcalculus.dnc.bounds.mpa_rtc;

import org.networkcalculus.dnc.Calculator;
import org.networkcalculus.dnc.bounds.Bounds;
import org.networkcalculus.dnc.curves.ArrivalCurve;
import org.networkcalculus.dnc.curves.Curve;
import org.networkcalculus.dnc.curves.ServiceCurve;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.Curve_MPARTC_PwAffine;
import org.networkcalculus.num.Num;

import ch.ethz.rtc.kernel.CurveMath;

public enum Bounds_MPARTC_PwAffine implements Bounds {
	BOUNDS_MPARTC_PWAFFINE;
	
    // --------------------------------------------------------------------------------------------------------------
    // Backlog
    // --------------------------------------------------------------------------------------------------------------

    public Num backlog(ArrivalCurve arrival_curve, ServiceCurve service_curve) {
    	return Num.getFactory(Calculator.getInstance().getNumBackend()).create(
		    	CurveMath.maxVDist(
						((Curve_MPARTC_PwAffine) arrival_curve).getRtc_curve(),
						((Curve_MPARTC_PwAffine) service_curve).getRtc_curve()
				));
    }
    
    // --------------------------------------------------------------------------------------------------------------
    // Delay
    // --------------------------------------------------------------------------------------------------------------

    /**
     * MPA RTC does not have an implementation for finding the x-coordinate of
     * two curves' first intersection. We have to use the implementation by DISCO,
     * relying on the curve interface compliance of the MPA RTC curve wrapper.
     * 
     * TODO: This restricts the entire class to non-periodic curves,
     *       a restriction inherited from the DISCO implementation. 
     */
    public Num delayARB(ArrivalCurve arrival_curve, ServiceCurve service_curve) {
    	return Curve.getUtils().getXIntersection(arrival_curve,service_curve);
    }

    public Num delayFIFO(ArrivalCurve arrival_curve, ServiceCurve service_curve) {
    	return Num.getFactory(Calculator.getInstance().getNumBackend()).create(
    	    	CurveMath.maxHDist(
    					((Curve_MPARTC_PwAffine) arrival_curve).getRtc_curve(),
    					((Curve_MPARTC_PwAffine) service_curve).getRtc_curve()
    			));
    }
}
