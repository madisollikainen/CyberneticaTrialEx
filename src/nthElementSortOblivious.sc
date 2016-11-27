/**
 *  Author : Madis Ollikainen
 *  File : nthElementSortOblivious.sc
 *
 *  Implements the modul nthElementSortOblivious, which holds two functions:
 *
 *  a)  D int64 nthElementSortOblivious(D int64[[1]] data, D uint64 k) :
 *
 *      Obliviously returns the k-th smallest element from the input array 'data'.
 *      Here, oblivious referres to the fact that both k and 'data' remain private.
 *      The selection is based on first sorting the input array 'data'
 *      and then obliviously returning the k-th entry in the sorted array.
 *      The sorting is done using the sort function from the shared3p_sort modul
 *      and oblivious array querying is done using the vectorLoopup function
 *      from the oblivious module.
 *
 *  b)  void test_nthElementSortOblivious(uint64 s, D uint64 k) :
 *
 *      Runs a simple test run of the nthElementSortOblivious function. It takes
 *      as input the size 's' of the input array 'data' to use as well as
 *      the number 'k' for the nthElementSortOblivious input.
 *
 */


// Declear this to be a module, such that
// it could be easily imported to other files.
module nthElementSortOblivious;

// Import necessary modules
import stdlib;
import shared3p;
import shared3p_sort;
import oblivious;

// Specify a security domain for testing
domain pd_shared3p shared3p;

// ----------------------------------- //
// ----- nthElementSortOblivious ----- //
// ----------------------------------- //
template<domain D>
D int64 nthElementSortOblivious (D int64[[1]] data, D uint64 k)
{
    // Sort the input array
    data = sort(data);

    // Return the k-th smallest value
    return vectorLookup(data, k);
}

// ---------------------------------------- //
// ----- test_nthElementSortOblivious ----- //
// ---------------------------------------- //
void test_nthElementSortOblivious(uint64 s, pd_shared3p uint64 k)
{
    //  Output info message
    print("Testing nthElementSortOblivious function\n\n");

    // Create the input array.
    pd_shared3p int64[[1]] input(s);
    for (uint i=0; i < s; ++i)
        input[i] = (int64)(s - i);

    // Print the input array.
    print("The input array:");
    printVector(declassify(input));

    // Print the k
    print("Find the k-th smallest element, where k is");
    print(declassify(k));

    // Get the k-th smaller element using nthElementSort
    pd_shared3p int64 output = nthElementSortOblivious(input, k);

    // Print the k-th smallest element
    print("The k-th smallest element:");
    print(declassify(output));
    print("\n");
}

