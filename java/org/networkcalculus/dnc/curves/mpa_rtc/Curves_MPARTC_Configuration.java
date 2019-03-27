package org.networkcalculus.dnc.curves.mpa_rtc;

public class Curves_MPARTC_Configuration {
	private static Curves_MPARTC_Configuration instance = new Curves_MPARTC_Configuration();

	public static Curves_MPARTC_Configuration getInstance() {
		return instance;
	}

	private boolean ARRIVAL_CURVE_CHECKS = false;
	private boolean SERVICE_CURVE_CHECKS = false;

	public void disableAllChecks() {
		ARRIVAL_CURVE_CHECKS = false;
		SERVICE_CURVE_CHECKS = false;
	}

	public void enableAllChecks() {
		ARRIVAL_CURVE_CHECKS = true;
		SERVICE_CURVE_CHECKS = true;
	}

	public boolean exec_arrival_curve_checks() {
		return ARRIVAL_CURVE_CHECKS;
	}

	public boolean exec_service_curve_checks() {
		return SERVICE_CURVE_CHECKS;
	}

	@Override
	public String toString() {
		StringBuffer config_str = new StringBuffer();

		if (exec_arrival_curve_checks()) {
			config_str.append("AC checks");
			config_str.append(", ");
		}
		if (exec_service_curve_checks()) {
			config_str.append("SC checks");
			config_str.append(", ");
		}

		if( config_str.length() == 0 ) {
			config_str.append("All MPA RTC curve checks are disabled");
		} else {
			config_str.delete(config_str.lastIndexOf(","), config_str.length());
		}
		
		return config_str.toString();
	}
}
