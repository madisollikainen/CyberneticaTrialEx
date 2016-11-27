/**
 *  Author : Madis Ollikainen
 *  File : run_nthElementSortTest.sc
 *
 *  Runs the test for the function
 *      D int64 nthElementSort(D int64[[1]] data, uint64 k)
 *
 *
 */

import nthElementSort;

void main()
{
    uint64 s = 20;
    for (uint i=0; i < s; ++i) 
    	test_nthElementSort(s, i);
}
