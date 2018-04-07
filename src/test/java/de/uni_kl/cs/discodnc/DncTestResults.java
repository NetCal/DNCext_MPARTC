/*
 * This file is part of the Disco Deterministic Network Calculator.
 *
 * Copyright (C) 2017 - 2018 Steffen Bondorf
 * Copyright (C) 2017+ The DiscoDNC contributors
 *
 * Distributed Computer Systems (DISCO) Lab
 * University of Kaiserslautern, Germany
 *
 * http://discodnc.cs.uni-kl.de
 *
 *
 * The Disco Deterministic Network Calculator (DiscoDNC) is free software;
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

package de.uni_kl.cs.discodnc;

import de.uni_kl.cs.discodnc.misc.Pair;
import de.uni_kl.cs.discodnc.nc.Analysis.Analyses;
import de.uni_kl.cs.discodnc.nc.AnalysisConfig;
import de.uni_kl.cs.discodnc.nc.AnalysisResults;
import de.uni_kl.cs.discodnc.network.Flow;
import de.uni_kl.cs.discodnc.numbers.Num;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DncTestResults {
	private Map<Flow, AnalysisResults> tfa_bounds_arb;
	private Map<Flow, AnalysisResults> tfa_bounds_fifo;
	private Map<Flow, AnalysisResults> sfa_bounds_arb;
	private Map<Flow, AnalysisResults> sfa_bounds_fifo;
	private Map<Flow, AnalysisResults> pmoo_bounds_arb;

	public DncTestResults() {
		tfa_bounds_arb = new HashMap<Flow, AnalysisResults>();
		tfa_bounds_fifo = new HashMap<Flow, AnalysisResults>();
		sfa_bounds_arb = new HashMap<Flow, AnalysisResults>();
		sfa_bounds_fifo = new HashMap<Flow, AnalysisResults>();
		pmoo_bounds_arb = new HashMap<Flow, AnalysisResults>();
	}

	protected void clear() {
		tfa_bounds_arb.clear();
		tfa_bounds_fifo.clear();
		sfa_bounds_arb.clear();
		sfa_bounds_fifo.clear();
		pmoo_bounds_arb.clear();
	}
	
	protected void setBounds(Analyses analysis, AnalysisConfig.Multiplexing mux, Flow flow, Num delay, Num backlog) {
		AnalysisResults bounds = new AnalysisResults(delay, backlog, null);

		Pair<Map<Flow, AnalysisResults>> bounded_analysis;
		switch (analysis) {
		case TFA:
			bounded_analysis = new Pair<Map<Flow, AnalysisResults>>(tfa_bounds_arb, tfa_bounds_fifo);
			break;
		case SFA:
			bounded_analysis = new Pair<Map<Flow, AnalysisResults>>(sfa_bounds_arb, sfa_bounds_fifo);
			break;
		case PMOO:
			bounded_analysis = new Pair<Map<Flow, AnalysisResults>>(pmoo_bounds_arb, null);
			break;
		default:
			throw new RuntimeException("Invalid analysis given.");
		}

		if (mux == AnalysisConfig.Multiplexing.ARBITRARY) {
			bounded_analysis.getFirst().put(flow, bounds);
		} else {
			bounded_analysis.getSecond().put(flow, bounds);
		}
	}

	public AnalysisResults getBounds(Analyses analysis, AnalysisConfig.Multiplexing mux, Flow flow) {
		Pair<Map<Flow, AnalysisResults>> bounded_analysis;
		switch (analysis) {
		case TFA:
			bounded_analysis = new Pair<Map<Flow, AnalysisResults>>(tfa_bounds_arb, tfa_bounds_fifo);
			break;
		case SFA:
			bounded_analysis = new Pair<Map<Flow, AnalysisResults>>(sfa_bounds_arb, sfa_bounds_fifo);
			break;
		case PMOO:
			bounded_analysis = new Pair<Map<Flow, AnalysisResults>>(pmoo_bounds_arb, null);
			break;
		default:
			throw new RuntimeException("Invalid analysis given.");
		}

		if (mux == AnalysisConfig.Multiplexing.ARBITRARY) {
			return bounded_analysis.getFirst().get(flow);
		} else {
			return bounded_analysis.getSecond().get(flow);
		}
	}
	
	@Override
	public String toString() {
		 StringBuffer exp_results_str = new StringBuffer();
		 
		 for( Entry<Flow,AnalysisResults> tfa_bound : tfa_bounds_arb.entrySet() ) {
			 exp_results_str.append( tfa_bound.getKey().getAlias() );
			 exp_results_str.append( " TFA_ARB " );
			 exp_results_str.append( tfa_bound.getValue().toString() );
			 exp_results_str.append( "\n" );
		 }
		 for( Entry<Flow,AnalysisResults> tfa_bound : tfa_bounds_fifo.entrySet() ) {
			 exp_results_str.append( tfa_bound.getKey().getAlias() );
			 exp_results_str.append( " TFA_FIFO " );
			 exp_results_str.append( tfa_bound.getValue().toString() );
			 exp_results_str.append( "\n" );
		 }

		 for( Entry<Flow,AnalysisResults> sfa_bound : sfa_bounds_arb.entrySet() ) {
			 exp_results_str.append( sfa_bound.getKey().getAlias() );
			 exp_results_str.append( " SFA_ARB " );
			 exp_results_str.append( sfa_bound.getValue().toString() );
			 exp_results_str.append( "\n" );
		 }
		 for( Entry<Flow,AnalysisResults> sfa_bound : sfa_bounds_fifo.entrySet() ) {
			 exp_results_str.append( sfa_bound.getKey().getAlias() );
			 exp_results_str.append( " SFA_FIFO " );
			 exp_results_str.append( sfa_bound.getValue().toString() );
			 exp_results_str.append( "\n" );
		 }

		 for( Entry<Flow,AnalysisResults> pmoo_bound : pmoo_bounds_arb.entrySet() ) {
			 exp_results_str.append( pmoo_bound.getKey().getAlias() );
			 exp_results_str.append( " PMOO_ARB " );
			 exp_results_str.append( pmoo_bound.getValue().toString() );
			 exp_results_str.append( "\n" );
		 }
		 
		 return exp_results_str.toString();
	}
}
