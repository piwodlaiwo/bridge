package com.kadziela.games.bridge.util;

public class ComparisonUtils 
{
	@SuppressWarnings("unchecked")
	public static int nullSafeCompare(Comparable o1, Comparable o2)
	{
		if (o1 == null && o2 == null) return 0;
		if (o1 == null) return -1;
		if (o2 == null) return 1;
		return o1.compareTo(o2);
	}
}