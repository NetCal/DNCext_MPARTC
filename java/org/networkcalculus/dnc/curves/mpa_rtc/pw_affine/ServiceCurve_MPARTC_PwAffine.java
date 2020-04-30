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

package org.networkcalculus.dnc.curves.mpa_rtc.pw_affine;

import org.networkcalculus.dnc.curves.ServiceCurve;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.Curve_MPARTC_PwAffine;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.ServiceCurve_MPARTC_PwAffine;

import ch.ethz.rtc.kernel.Curve;

public class ServiceCurve_MPARTC_PwAffine extends Curve_MPARTC_PwAffine implements ServiceCurve {
    // --------------------------------------------------------------------------------------------------------------
    // Constructors
    // --------------------------------------------------------------------------------------------------------------
    public ServiceCurve_MPARTC_PwAffine() {
        super();
    }

    public ServiceCurve_MPARTC_PwAffine(int segment_count) {
        super(segment_count);
    }

    public ServiceCurve_MPARTC_PwAffine(org.networkcalculus.dnc.curves.Curve curve) {
        copy(curve);
    }

    public ServiceCurve_MPARTC_PwAffine(Curve curve) {
        rtc_curve = curve.clone();
    }

    public ServiceCurve_MPARTC_PwAffine(String service_curve_str) throws Exception {
        super.initializeCurve(service_curve_str);
    }

    // --------------------------------------------------------------------------------------------------------------
    // Interface Implementations
    // --------------------------------------------------------------------------------------------------------------
    @Override
    public ServiceCurve_MPARTC_PwAffine copy() {
        ServiceCurve_MPARTC_PwAffine sc_copy = new ServiceCurve_MPARTC_PwAffine();
        sc_copy.copy(this);
        return sc_copy;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ServiceCurve_MPARTC_PwAffine) && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return "SC".hashCode() * super.hashCode();
    }

    /**
     * Returns a string representation of this curve.
     *
     * @return the curve represented as a string.
     */
    @Override
    public String toString() {
        return "SC" + super.toString();
    }
}
