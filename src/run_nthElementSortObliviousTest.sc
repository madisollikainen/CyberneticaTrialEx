/*
 *  Author : Madis Ollikainen
 *  File : run_nthElementSortObliviousTest.sc
 *
 *  Runs the tests for the functions
 *      D int64 nthElementSortOblivious(D int64[[1]] data, D uint64 k)
 *
 *
 */

import nthElementSortOblivious;

void main()
{
    uint64 s = 100;
    pd_shared3p uint64 k_ = 4;
    test_nthElementSortOblivious(s, k_);
}
