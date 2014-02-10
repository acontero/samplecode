#!/usr/bin/env python

# this is the buggy version of sort that yields an "interesting" result
def sort(a):
	if a == []: 
		return []
	p = a[0]
	return [sort([x for x in a if x < p])] + [p] + [sort([x for x in a[1:] if x >= p])]

def sorted(t):
	if t == []: 
		return []
	return sorted(t[0]) + [t[1]] + sorted(t[2])

# returns true if n is in t
def search(t,n):
		a = _search(t,n)
		if a == []:
			return False;
		elif a[1]==n: 
			return True

		return False

# if n is absent, insert it into t (in-place), otherwise do nothing.
def insert(t,n):
	a = _search(t,n)
 	if a == []:
 		a.extend([[],n,[]])
 		

# helper function _search
# returns the subtree (list) rooted at x when x is found, or the [] where x should be inserted
def _search(t,n):
	if t == []: 
		return t

	if t[1] == n:
		return [t[0]] + [t[1]] + [t[2]]

	elif n < t[1]:
		return _search(t[0],n)

	elif n > t[1]:
		return _search(t[2],n)

print "tree = sort([4,2,6,3,5,7,1,9])"
tree = sort([4,2,6,3,5,7,1,9])
print "tree"
print tree
print "sorted(tree)"
print sorted(tree)
print "search(tree, 6)"
print search(tree, 6)
print "search(tree, 6.5)"
print search(tree, 6.5)
print "insert(tree, 6.5)"
print insert(tree, 6.5)
print "tree"
print tree
print "insert(tree, 3)"
print insert(tree, 3)
print "tree"
print tree


