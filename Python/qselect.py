#!/usr/bin/env python
import random

# Quick Select with Randomized Pivot
# We are looking for the i-th smallest value in list a.
# This method returns that value.
# NOTE: assumes the values are distinct

def qselect(i,a):
	if len(a)==1: return a[0]
	
	p = random.randint(0,(len(a)-1))
	v = a[p]

	left = [x for x in a if x < v]
	right = [x for x in a if x > v]

	k = len(left) + 1 # this will tell you exactly where the pivot is after the partitioning
	# there are exactly k elements less than ith element

	if i==k: return v 
	
	elif i<k:
		return qselect(i,left) 
	elif i>k: 
		return qselect(i-k,right) 

print "qselect(2, [3, 10, 4, 7, 19])"
print qselect(2, [3, 10, 4, 7, 19])
print "qselect(4, [11, 2, 8, 3])"
print qselect(4, [11, 2, 8, 3])