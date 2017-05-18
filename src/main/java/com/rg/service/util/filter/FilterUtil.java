package com.rg.service.util.filter;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FilterUtil<T> {

	public static <T, C> List<T> filter(Collection<T> target,
			IPredicate<T> predicate) {
		List<T> result = new LinkedList<T>();
		for (T element : target) {
			if (predicate.apply(element)) {
				result.add(element);
			}
		}
		return result;
	}
}
