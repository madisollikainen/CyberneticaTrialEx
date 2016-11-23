/*
 *  Author : Madis Ollikainen
 *  File : nthElementSort.sc
 *
 *  Implements the modul nthElementSort, which holds two functions:
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


// Declear this to be a module, such that
// it could be easily imported to other files.
module nthElementSort;

// Import necessary modules
import stdlib;
import shared3p;
import shared3p_sort;

// -------------------------- //
// ----- nthElementSort ----- //
// -------------------------- //
template<domain D>
D int64 nthElementSort (D int64[[1]] data, uint64 k)
{
    // Sort the input array
    data = sort(data);

    // Return the k-th smallest value
    return data[k];
}

// ------------------------------- //
// ----- test_nthElementSort ----- //
// ------------------------------- //
void test_nthElementSort(unit64 s)
{
    domain pd_shared3p shared3p;

    pd_shared3p int64[[1]] input(s);

    for (uint i=0; i < s; ++i)
    {
        input[i] = (int64)(s - i);
    }

    print("Inputs:");
//    printVector(declassify(input[0:min(s, 10::uint)]));
    printVector(declassify(input));


    pd_shared3p int64 output = nthElementSort(input, k);

    print(declassify(output));
}

