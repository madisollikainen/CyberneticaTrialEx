/*
 *  Author : Madis Ollikainen
 *  File : run_nthElementObliviousTest.sc
 *
 *  Runs the tests for the functions
 *      D int64 nthElementOblivious (D int64[[1]] data, D uint64 k, bool shuffle)
 *
 *
 */

import nthElementOblivious;

void main()
{
    uint64 s = 1000;
    pd_shared3p uint64 k_ = 4;
    test_nthElementOblivious(s, k_);
}
