#!/usr/bin/env python
# finds the longest path within a binary tree

# x is the longest length
def longest(tree):
	lpt, t1, lht, t2 = _longest(tree)
	return lpt, t1

# returns path, height
def _longest(tree):
	if tree == []:
		return 0, [], -1, []

	lp, longestPathLeft , lh, longestDepthLeft = _longest(tree[0])
	rp, longestPathRight, rh, longestDepthRight = _longest(tree[2])

	tp = max(lp,rp,(lh+rh+1+1))
	th = max(lh,rh)+1  

	if lh < rh:
		longestDepth = [tree[1]] + longestDepthRight		
	else:
		longestDepth = longestDepthLeft + [tree[1]]

	if tp == lp:
		return tp, longestPathLeft, th, longestDepth
	elif tp == rp: 
		return tp, longestPathRight, th, longestDepth
	elif tp == (lh+rh+1+1):
		return tp, longestDepthLeft + [tree[1]] + longestDepthRight, th, longestDepth


# TO TEST:
print "longest([[[[], 1, []], 2, [[], 3, []]], 4, [[[], 5, []], 6, [[], 7, [[], 9, []]]]])"
print longest([[[[], 1, []], 2, [[], 3, []]], 4, [[[], 5, []], 6, [[], 7, [[], 9, []]]]])