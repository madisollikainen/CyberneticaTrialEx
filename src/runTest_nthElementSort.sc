/*
 *  Author : Madis Ollikainen
 *  File : run_nthElementSortTest.sc
 *
 *  Runs the tests for the functions
 *      D int64 nthElementSort(D int64[[1]] data, uint64 k)
 *
 *
 */

import nthElementSort;

void main()
{
    uint64 s = 100;
    uint64 k = 4;
    test_nthElementSort(s, k);
}
