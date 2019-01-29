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

package org.networkcalculus.dnc.algebra.mpa_rtc.pw_affine;

import java.util.HashSet;
import java.util.Set;

import org.networkcalculus.dnc.Calculator;
import org.networkcalculus.dnc.algebra.MinPlus;
import org.networkcalculus.dnc.curves.ArrivalCurve;
import org.networkcalculus.dnc.curves.Curve;
import org.networkcalculus.dnc.curves.MaxServiceCurve;
import org.networkcalculus.dnc.curves.ServiceCurve;
import org.networkcalculus.dnc.curves.mpa_rtc.pw_affine.Curve_MPARTC_PwAffine;

import ch.ethz.rtc.kernel.CurveMath;
import ch.ethz.rtc.kernel.Segment;
import ch.ethz.rtc.kernel.SegmentList;

public enum MinPlus_MPARTC implements MinPlus {
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
		// Must be CurveClass.MPA_RTC + OpertionClass.NATIVE
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusConv(
				((Curve_MPARTC_PwAffine) service_curve_1).getRtc_curve(),
				((Curve_MPARTC_PwAffine) service_curve_2).getRtc_curve());

		return Calculator.getInstance().getCurveFactory().createServiceCurve(result.toString());
	}

	// Java won't let us call this method "convolve" because it does not care about
	// the Sets' types; tells that there's already another method taking the same
	// arguments.
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

				results.add(Calculator.getInstance().getCurveFactory()
						.createServiceCurve(CurveMath.minPlusConv(s11.getRtc_curve(), s12.getRtc_curve()).toString()));
			}
		}
		return results;
	}

	// Arrival Curves
	public ArrivalCurve convolve(ArrivalCurve arrival_curve_1, ArrivalCurve arrival_curve_2) throws Exception {
		// DNC operations work with DNC and MPA_RTC curves
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusConv(
				((Curve_MPARTC_PwAffine) arrival_curve_1).getRtc_curve(),
				((Curve_MPARTC_PwAffine) arrival_curve_2).getRtc_curve());

		return Calculator.getInstance().getCurveFactory().createArrivalCurve(result.toString());
	}

	public ArrivalCurve convolve(Set<ArrivalCurve> arrival_curves) throws Exception {
		// DNC operations work with DNC and MPA_RTC curves

		// TODO Double check
		if (arrival_curves == null || arrival_curves.isEmpty()) {
			return Calculator.getInstance().getCurveFactory().createZeroArrivals();
		}
		if (arrival_curves.size() == 1) {
			return arrival_curves.iterator().next().copy();
		}
		Segment s = new Segment(0, 0, 0);
		SegmentList sl = new SegmentList();
		sl.add(s);
		ch.ethz.rtc.kernel.Curve result = new ch.ethz.rtc.kernel.Curve(sl);
		ch.ethz.rtc.kernel.Curve ac2 = null;
		for (ArrivalCurve arrival_curve_2 : arrival_curves) {
			ArrivalCurve result_curves = Calculator.getInstance().getCurveFactory().createArrivalCurve(arrival_curve_2.toString());
			Curve_MPARTC_PwAffine c = (Curve_MPARTC_PwAffine) result_curves;
			ac2 = c.getRtc_curve();

			result = CurveMath.minPlusConv(result, ac2);
		}

		return Calculator.getInstance().getCurveFactory().createArrivalCurve(ac2.toString());
	}

	// Maximum Service Curves
	public MaxServiceCurve convolve(MaxServiceCurve max_service_curve_1, MaxServiceCurve max_service_curve_2)
			throws Exception {
		// DNC operations work with DNC and MPA_RTC curves
		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusConv(
				((Curve_MPARTC_PwAffine) max_service_curve_1).getRtc_curve(),
				((Curve_MPARTC_PwAffine) max_service_curve_2).getRtc_curve());

		return Calculator.getInstance().getCurveFactory().createMaxServiceCurve(result.toString());
	}

	// Arrival Curves and Max Service Curves
	public Set<Curve> convolve_ACs_MaxSC(Set<ArrivalCurve> arrival_curves, MaxServiceCurve maximum_service_curve)
			throws Exception {
		// DNC operations work with DNC and MPA_RTC curves

		Set<Curve> results = new HashSet<Curve>();

		Curve_MPARTC_PwAffine msc_mpa_rtc = (Curve_MPARTC_PwAffine) maximum_service_curve;
		for (ArrivalCurve alpha_tmp : arrival_curves) {
			// Do not mind the semantics "Arrival Curve"
			results.add(Calculator.getInstance().getCurveFactory()
					.createArrivalCurve(CurveMath
							.minPlusConv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(), msc_mpa_rtc.getRtc_curve())
							.toString()));
		}
		return results;
	}

	public Set<ArrivalCurve> convolve_ACs_MaxScRate(Set<ArrivalCurve> arrival_curves, MaxServiceCurve extra_gamma_curve)
			throws Exception {
		// DNC operations work with DNC and MPA_RTC curves

		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		Curve_MPARTC_PwAffine egamma_mpa_rtc = (Curve_MPARTC_PwAffine) extra_gamma_curve;
		for (ArrivalCurve alpha_tmp : arrival_curves) {
			results.add(Calculator.getInstance().getCurveFactory().createArrivalCurve(CurveMath
					.minPlusConv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(), egamma_mpa_rtc.getRtc_curve())
					.toString()));
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
		// DNC operations work with DNC and MPA_RTC curves

		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		Curve_MPARTC_PwAffine beta_mpa_rtc = (Curve_MPARTC_PwAffine) service_curve;
		for (ArrivalCurve alpha_tmp : arrival_curves) {
			results.add(Calculator.getInstance().getCurveFactory().createArrivalCurve(CurveMath
					.minPlusDeconv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(), beta_mpa_rtc.getRtc_curve())
					.toString()));
		}
		return results;
	}

	public Set<ArrivalCurve> deconvolve(Set<ArrivalCurve> arrival_curves, Set<ServiceCurve> service_curves)
			throws Exception {
		return deconvolve(arrival_curves, service_curves, false);
	}

	public Set<ArrivalCurve> deconvolve(Set<ArrivalCurve> arrival_curves, Set<ServiceCurve> service_curves,
			boolean tb_rl_optimized) throws Exception {
		// DNC operations work with DNC and MPA_RTC curves

		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		for (ServiceCurve beta_tmp : service_curves) {
			for (ArrivalCurve alpha_tmp : arrival_curves) {
				results.add(Calculator.getInstance().getCurveFactory()
						.createArrivalCurve(CurveMath.minPlusDeconv(((Curve_MPARTC_PwAffine) alpha_tmp).getRtc_curve(),
								((Curve_MPARTC_PwAffine) beta_tmp).getRtc_curve()).toString()));
			}
		}
		return results;
	}

	public ArrivalCurve deconvolve(ArrivalCurve arrival_curve, ServiceCurve service_curve) throws Exception {
		return deconvolve(arrival_curve, service_curve, false);
	}

	public ArrivalCurve deconvolve(ArrivalCurve arrival_curve, ServiceCurve service_curve, boolean tb_rl_optimized)
			throws Exception {
		// DNC operations work with DNC and MPA_RTC curves

		ch.ethz.rtc.kernel.Curve result = CurveMath.minPlusDeconv(
				((Curve_MPARTC_PwAffine) arrival_curve).getRtc_curve(),
				((Curve_MPARTC_PwAffine) service_curve).getRtc_curve());

		return Calculator.getInstance().getCurveFactory().createArrivalCurve(result.toString());
	}

	public Set<ArrivalCurve> deconvolve_almostConcCs_SCs(Set<Curve> curves, Set<ServiceCurve> service_curves)
			throws Exception {
		// DNC operations work with DNC and MPA_RTC curves

		Set<ArrivalCurve> results = new HashSet<ArrivalCurve>();

		for (ServiceCurve beta_tmp : service_curves) {
			for (Curve c_tmp : curves) {
				// Do not mind the semantics "Arrival Curve"
				results.add(Calculator.getInstance().getCurveFactory()
						.createArrivalCurve(CurveMath.minPlusDeconv(((Curve_MPARTC_PwAffine) c_tmp).getRtc_curve(),
								((Curve_MPARTC_PwAffine) beta_tmp).getRtc_curve()).toString()));
			}
		}
		return results;
	}

}