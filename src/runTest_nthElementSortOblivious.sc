/**
 *  Author : Madis Ollikainen
 *  File : run_nthElementSortObliviousTest.sc
 *
 *  Runs the test for the function
 *      D int64 nthElementSortOblivious(D int64[[1]] data, D uint64 k)
 *
 *
 */

import nthElementSortOblivious;

void main()
{
	uint64 s = 20;
	for (uint i=0; i < s; ++i)
	{
		pd_shared3p uint64 k = i;
		test_nthElementSortOblivious(s, k);
	}
}
