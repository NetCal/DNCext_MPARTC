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

package org.networkcalculus.dnc.algebra.mpa_rtc;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.networkcalculus.dnc.algebra.MinPlus;
import org.networkcalculus.dnc.curves.ArrivalCurve;
import org.networkcalculus.dnc.curves.Curve;
import org.networkcalculus.dnc.curves.MaxServiceCurve;
import org.networkcalculus.dnc.curves.ServiceCurve;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.ArrivalCurve_MPARTC_PwAffine;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.Curve_MPARTC_PwAffine;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.MaxServiceCurve_MPARTC_PwAffine;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.ServiceCurve_MPARTC_PwAffine;

import ch.ethz.rtc.kernel.CurveMath;

/**
 * A dispatcher calling the MPA RTC's implementation of min-plus convolution and deconvolution.
 * 
 * For compatibility with the NetCal DNC, this class implements all the methods defined in the MinPlus interface.
 * 
 * Dispatching simply works follows:
 * Given MPA RTC curves that are wrapped to comply with the DNC Curve interfaces,
 * the respective method unwraps them, 
 * applies the according MPA RTC operation and 
 * wraps the result for DNC compliance.
 */
public enum MinPlus_MPARTC_PwAffine implements MinPlus {
	MINPLUS_MPARTC;
	
	// --------------------------------------------------------------------------------------------------------------
	// Min-Plus-Operation Dispatching
	// --------------------------------------------------------------------------------------------------------------

	// ------------------------------------------------------------
	// Convolution
	// ------------------------------------------------------------

	// Service Curves
	public ServiceCurve convolve(ServiceCurve service_curve_1, ServiceCurve service_curve_2) throws Exception {
		return convolve(service_curve_1, service_curve_2, false);
	}

	public ServiceCurve convolve(ServiceCurve service_curve_1, ServiceCurve service_curve_2, boolean tb_rl_optimized)
			throws Exception {
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusConv(
				((Curve_MPARTC_PwAffine) service_curve_1).getRtc_curve(),
				((Curve_MPARTC_PwAffine) service_curve_2).getRtc_curve());

		return new ServiceCurve_MPARTC_PwAffine(result);
	}

	public Set<ServiceCurve> convolve(Set<ServiceCurve> service_curves_1, Set<ServiceCurve> service_curves_2)
			throws Exception {
		return convolve_SCs_SCs(service_curves_1, service_curves_2, false);
	}

	public Set<ServiceCurve> convolve_SCs_SCs(Set<ServiceCurve> service_curves_1, Set<ServiceCurve> service_curves_2,
			boolean tb_rl_optimized) throws Exception {

		if (service_curves_1.isEmpty()) {
			return service_curves_2;
		}
		if (service_curves_2.isEmpty()) {
			return service_curves_1;
		}

		Set<ServiceCurve> results = new HashSet<ServiceCurve>();

		for (ServiceCurve beta_1 : service_curves_1) {
			for (ServiceCurve beta_2 : service_curves_2) {

				Curve_MPARTC_PwAffine s11 = (Curve_MPARTC_PwAffine) beta_1;
				Curve_MPARTC_PwAffine s12 = (Curve_MPARTC_PwAffine) beta_2;

				results.add( 
						new ServiceCurve_MPARTC_PwAffine(
								CurveMath.minPlusConv(s11.getRtc_curve(), s12.getRtc_curve()).toString()
						));
			}
		}
		return results;
	}

	// Arrival Curves
	public ArrivalCurve convolve(ArrivalCurve arrival_curve_1, ArrivalCurve arrival_curve_2) throws Exception {
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusConv(
				((Curve_MPARTC_PwAffine) arrival_curve_1).getRtc_curve(),
				((Curve_MPARTC_PwAffine) arrival_curve_2).getRtc_curve());

		return new ArrivalCurve_MPARTC_PwAffine( result );
	}

	public ArrivalCurve convolve(Set<ArrivalCurve> arrival_curves) throws Exception {
		if (arrival_curves == null || arrival_curves.isEmpty()) {
			return Curve_MPARTC_PwAffine.getFactory().createZeroArrivals();
		}
		
		Iterator<ArrivalCurve> ac_iter = arrival_curves.iterator();
		ArrivalCurve curve1 = ac_iter.next();
		
		if (!ac_iter.hasNext()) {
			return curve1.copy();
		}
		
		ArrivalCurve curve2;
		while(ac_iter.hasNext()) {
			curve2 = ac_iter.next();
			curve1 = convolve(curve1, curve2);
		}
		
		return curve1;
	}

	// Maximum Service Curves
	public MaxServiceCurve convolve(MaxServiceCurve max_service_curve_1, MaxServiceCurve max_service_curve_2)
			throws Exception {
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusConv(
				((Curve_MPARTC_PwAffine) max_service_curve_1).getRtc_curve(),
				((Curve_MPARTC_PwAffine) max_service_curve_2).getRtc_curve());

		return new MaxServiceCurve_MPARTC_PwAffine(result);
	}

	// Arrival Curves and Max Service Curves
	public Set<Curve> convolve_ACs_MaxSC(Set<ArrivalCurve> arrival_curves, MaxServiceCurve maximum_service_curve)
			throws Exception {

		Set<Curve> results = new HashSet<Curve>();

		Curve_MPARTC_PwAffine msc_mpa_rtc = (Curve_MPARTC_PwAffine) maximum_service_curve;
		for (ArrivalCurve alpha_tmp : arrival_curves) {
			// Do not mind the semantics "Arrival Curve"
			results.add(
					new ArrivalCurve_MPARTC_PwAffine(
							// Need to cast to get access to the getRtc_curve() method.
							CurveMath.minPlusConv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(), msc_mpa_rtc.getRtc_curve())
					));
		}
		return results;
	}

	public Set<ArrivalCurve> convolve_ACs_MaxScRate(Set<ArrivalCurve> arrival_curves, MaxServiceCurve extra_gamma_curve)
			throws Exception {
		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		Curve_MPARTC_PwAffine egamma_mpa_rtc = (Curve_MPARTC_PwAffine) extra_gamma_curve;
		for (ArrivalCurve alpha_tmp : arrival_curves) {
			results.add(
					new ArrivalCurve_MPARTC_PwAffine(
						// Need to cast to get access to the getRtc_curve() method.
						CurveMath.minPlusConv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(), egamma_mpa_rtc.getRtc_curve())
					));
		}
		return results;
	}

	// ------------------------------------------------------------
	// Deconvolution
	// ------------------------------------------------------------
	public Set<ArrivalCurve> deconvolve(Set<ArrivalCurve> arrival_curves, ServiceCurve service_curve) throws Exception {
		return deconvolve(arrival_curves, service_curve, false);
	}

	public Set<ArrivalCurve> deconvolve(Set<ArrivalCurve> arrival_curves, ServiceCurve service_curve,
			boolean tb_rl_optimized) throws Exception {
		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		Curve_MPARTC_PwAffine beta_mpa_rtc = (Curve_MPARTC_PwAffine) service_curve;
		for (ArrivalCurve alpha_tmp : arrival_curves) {
			results.add(
					new ArrivalCurve_MPARTC_PwAffine(
							CurveMath.minPlusDeconv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(), beta_mpa_rtc.getRtc_curve())
					));
		}
		return results;
	}

	public Set<ArrivalCurve> deconvolve(Set<ArrivalCurve> arrival_curves, Set<ServiceCurve> service_curves)
			throws Exception {
		return deconvolve(arrival_curves, service_curves, false);
	}

	public Set<ArrivalCurve> deconvolve(Set<ArrivalCurve> arrival_curves, Set<ServiceCurve> service_curves,
			boolean tb_rl_optimized) throws Exception {
		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		for (ServiceCurve beta_tmp : service_curves) {
			for (ArrivalCurve alpha_tmp : arrival_curves) {
				results.add(
						new ArrivalCurve_MPARTC_PwAffine(
								CurveMath.minPlusDeconv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(),((Curve_MPARTC_PwAffine) beta_tmp).getRtc_curve())
						));
			}
		}
		return results;
	}

	public ArrivalCurve deconvolve(ArrivalCurve arrival_curve, ServiceCurve service_curve) throws Exception {
		return deconvolve(arrival_curve, service_curve, false);
	}

	public ArrivalCurve deconvolve(ArrivalCurve arrival_curve, ServiceCurve service_curve, boolean tb_rl_optimized)
			throws Exception {
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusDeconv(
				((Curve_MPARTC_PwAffine) arrival_curve).getRtc_curve(),
				((Curve_MPARTC_PwAffine) service_curve).getRtc_curve());

		return new ArrivalCurve_MPARTC_PwAffine( result );
	}

	public Set<ArrivalCurve> deconvolve_almostConcCs_SCs(Set<Curve> curves, Set<ServiceCurve> service_curves)
			throws Exception {
		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		for (ServiceCurve beta_tmp : service_curves) {
			for (Curve c_tmp : curves) {
				// Do not mind the semantics "Arrival Curve"
				results.add(
						new ArrivalCurve_MPARTC_PwAffine(
								CurveMath.minPlusDeconv(((Curve_MPARTC_PwAffine) c_tmp).getRtc_curve(),	((Curve_MPARTC_PwAffine) beta_tmp).getRtc_curve())
						));
			}
		}
		return results;
	}
}
