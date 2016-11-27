/**
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
 *  b)  void test_nthElementSort(uint64 s, uint64 k) :
 *
 *      Runs a simple test run of the nthElementSort function. It takes
 *      as input the size 's' of the input array 'data' to use as well as
 *      the number 'k' for the nthElementSort input.
 *
 */


// Declear this to be a module, such that
// it could be easily imported to other files.
module nthElementSort;

// Import necessary modules
import stdlib;
import shared3p;
import shared3p_sort;

// Specify a security domain for testing
domain pd_shared3p shared3p;

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
void test_nthElementSort(uint64 s, uint64 k)
{
    //  Output info message
    print("Testing nthElementSort function\n\n");

    // Create the input array.
    pd_shared3p int64[[1]] input(s);
    for (uint i=0; i < s; ++i)
        input[i] = (int64)(s - i);

    // Print the input array.
    print("From  the input array:");
    printVector(declassify(input));

    // Print the k
    print("Find the k-th smallest element, where k is");
    print(k);

    // Get the k-th smaller element using nthElementSort
    pd_shared3p int64 output = nthElementSort(input, k);

    // Print the k-th smallest element
    print("The k-th smallest element:");
    print(declassify(output));
    print("\n");
}

