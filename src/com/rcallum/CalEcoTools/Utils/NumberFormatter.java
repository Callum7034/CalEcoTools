package com.rcallum.CalEcoTools.Utils;

import java.text.DecimalFormat;

public class NumberFormatter {
	public static DecimalFormat format = new DecimalFormat("$###,###");
	public static DecimalFormat formatC = new DecimalFormat("###,###");

	public static String formatNumber(double i) {
		String balance = String.valueOf(format.format(i));
		if ((i >= 1000) && (i < 1000000)) {
			balance = String.format("$%.1fk", new Object[] { Double.valueOf(i / 1000.0D) });
		}
		if ((i >= 1000000) && (i < 1000000000)) {
			balance = String.format("$%.1fm", new Object[] { Double.valueOf(i / 1000000.0D) });
		}
		if ((i >= 1000000000) && (i < 1000000000000L)) {
			balance = String.format("$%.1fb", new Object[] { Double.valueOf(i / 1.0E9D) });
		}
		if (i >= 1000000000000L) {
			balance = String.format("$%.1ft", new Object[] { Double.valueOf(i / 1.0E12D) });
		}
		return balance;
	}

	public static String caneNumberFormatter(double i) {
		String balance = String.valueOf(formatC.format(i));
		if ((i >= 1000) && (i < 1000000)) {
			balance = String.format("%.1fk", new Object[] { Double.valueOf(i / 1000.0D) });
		}
		if ((i >= 1000000) && (i < 1000000000)) {
			balance = String.format("%.1fm", new Object[] { Double.valueOf(i / 1000000.0D) });
		}
		if ((i >= 1000000000) && (i < 1000000000000L)) {
			balance = String.format("%.1fb", new Object[] { Double.valueOf(i / 1.0E9D) });
		}
		if ((i >= 1000000000000L) && (i < 1000000000000000L)) {
			balance = String.format("%.1ft", new Object[] { Double.valueOf(i / 1.0E12D) });
		}
		if (i >= 1000000000000000L) {
			balance = String.format("%.1fq", new Object[] { Double.valueOf(i / 1.0E15D) });
		}
		return balance;
	}

	public static String withLargeIntegers(double value) {
		DecimalFormat df = new DecimalFormat("###,###,###");
		return df.format(value);
	}
}
