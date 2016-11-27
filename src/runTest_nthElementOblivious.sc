/**
 *  Author : Madis Ollikainen
 *  File : run_nthElementObliviousTest.sc
 *
 *  Runs the test for the function
 *      D int64 nthElementOblivious (D int64[[1]] data, D uint64 k)
 *
 *
 */

import nthElementOblivious;

void main()
{
    uint64 s = 20;
	for (uint i=0; i < s; ++i){
        pd_shared3p uint64 k = i;
        test_nthElementOblivious(s, k);
    }
}
