package com.rg.service.util.filter;

public interface IPredicate<T> {
	boolean apply(T type);
}