/*
 *  Author : Madis Ollikainen
 *  File : main.sc
 *
 *  Runs the tests for the functions
 *
 *  a)  D int64 nthElementSort(D int64[[1]] data, uint64 k) :
 *
 *      Returns the k-th smallest element from the input array 'data'.
 *      The selection is based on first sorting the input array 'data'
 *      and then returning the k-th entry in the sorted array. The sorting
 *      is done using the sort function from the shared3p_sort modul.
 *
 *  b)
 *
 *
 *
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
