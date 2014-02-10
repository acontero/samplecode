#!/usr/bin/env python

def mergesorted(a,b):
	if len(a)==0 or len(b)==0:
		return a + b
	if a[0] < b[0]:
		return [a[0]] + mergesorted(a[1:],b)
	return [b[0]] + mergesorted(a,b[1:])

def mergesort(a):
	if len(a) <= 1:
		return a
	left, right = mergesort(a[0:(len(a)/2)]), mergesort(a[(len(a)/2):])
	return mergesorted(left,right)