/**
 *  Author : Madis Ollikainen
 *  File : nthElementOblivious.sc
 *
 *  This implements the modul nthElementOblivious, which hold two functions:
 *
 *  a)  D int64 nthElementOblivious(D int64[[1]] data, D uint64 k) :
 *
 *      A modified version of the nthElement function implemented in
 *      shared3p_statistics_common module of the SecreC language standard library.  
 *      It return the k-th smallest element of the input array 'data', such that 
 *      both 'data' and k stay private. Essentially it is the same code as for
 *      the nthElement function in the shared3p_statistics_common module, but with
 *      the difference that almost all vector accessing and updating is done using 
 *      the vectorLookup and vectorUpdate functions from the oblivious module.
 *
 *  b)  void test_nthElementOblivious(uint64 s, uint64 k) :
 *
 *      Runs a simple test run of the nthElementOblivious function. It takes
 *      as input the size 's' of the input array 'data' to use as well as
 *      the number 'k' for the nthElementOblivious input.
 *
 */

// Declear this to be a module, such that
// it could be easily imported to other files.
module nthElementOblivious;

// Import necessary modules
import stdlib;
import shared3p;
import shared3p_random;
import oblivious;

// Specify a security domain for testing
domain pd_shared3p shared3p;

/**
 *  Modification of the _partition function into the _partitionOblivious.
 *
 *  The original function can be found from:
 *  https://github.com/sharemind-sdk/stdlib/blob/master/lib/shared3p_statistics_common.sc
 *
 *  ! I've kept the original code as comments starting with an exclamation mark (!), this 
 *  ! allows comparison between the original and the oblivious version implemented here.
 *
 *  Description and explanation on the partitioning function
 *  and its role in the selection algorithm can be found from:
 *  http://en.wikipedia.org/wiki/Selection_algorithm#Partition-based_general_selection_algorithm
 *
 */
template <domain D, type T>
D T[[1]] _partitionOblivious(D T[[1]] dataVector, D uint left, D uint right, D uint pivotIndex) {
    //  ! D T pivotValue = dataVector[pivotIndex];
    //  ! dataVector[pivotIndex] = dataVector[right];
    //  ! dataVector[right] = pivotValue;
    //  ! uint storeIndex = left;
    D T pivotValue = vectorLookup(dataVector, pivotIndex);
    dataVector = vectorUpdate(dataVector, pivotIndex, vectorLookup(dataVector, right));
    dataVector = vectorUpdate(dataVector, right, pivotValue);
    D uint storeIndex = left;
    D T temp;

    // In order to hide the left and right values,
    // the loop is formulated over delta = right - left,
    // thus only leaking the size of the sub-array which is partitioned.
    uint delta = declassify(right-left);

    for (uint i = 0; i < delta; i++) {   
    //  ! for (uint i = left; i < right; i++) {
        // NB! We leak a quite random comparison result
        //  ! if (declassify (dataVector[i] < pivotValue)) {
        if (declassify (vectorLookup(dataVector, left+i) < pivotValue)) {
            //  ! temp = dataVector[storeIndex];
            //  ! dataVector[storeIndex] = dataVector[i];
            //  ! dataVector[i] = temp;
            temp = vectorLookup(dataVector, storeIndex);
            dataVector=vectorUpdate(dataVector, storeIndex, vectorLookup(dataVector, left+i));
            dataVector=vectorUpdate(dataVector, left+i, temp);
            //  ! storeIndex++;
            storeIndex+=1;
        }
    }
    //  ! temp = dataVector[storeIndex];
    //  ! dataVector[storeIndex] = dataVector[right];
    //  ! dataVector[right] = temp;
    temp = vectorLookup(dataVector, storeIndex);
    dataVector=vectorUpdate(dataVector, storeIndex, vectorLookup(dataVector, right));
    dataVector=vectorUpdate(dataVector, right, temp);

    D T[[1]] retval (size(dataVector) + 1);
    retval[0] = (T)storeIndex;
    retval[1:size(dataVector)+1] = dataVector[0:size(dataVector)];
    return retval;
}

/**
 *  Modification of the _nthElement function into the _nthElementOblivious.
 *
 *  The original function can be found from:
 *  https://github.com/sharemind-sdk/stdlib/blob/master/lib/shared3p_statistics_common.sc
 *
 *  ! I've kept the original code as comments starting with an exclamation mark (!), this 
 *  ! allows comparison between the original and the oblivious version implemented here.
 *
 */
template <domain D : shared3p, type T>
D T _nthElementOblivious (D T[[1]] data, D uint64 left, D uint64 right, D uint64 k, bool shuffle) {
    assert( declassify(left >= 0) );
    assert( declassify(right < size(data)) );
    assert( declassify(k < size(data)) );

    // The algorithm uses k = 1... internally
    k = k + 1;

    // IMPORTANT! We need to shuffle, because we do not hide the execution flow
    // Sometimes the data is already shuffled (like with shuffling and cutting)
    // Then reshuffling is unnecessary
    if (shuffle){
        data = shuffle (data);
    }

    while (true) {
        //  ! uint pivotIndex = (left + right) / 2;
        D uint pivotIndex = (left + right) / 2;

        // TODO: We need either structures or references to let
        // partition return both the new data and the index.
        // The current hack of putting the index into the vector is
        // quite bad.

        //  ! D T[[1]] partData = _partition (data, left, right, pivotIndex);
        //  ! uint pivotNewIndex = (uint)declassify (partData[0]);
        D T[[1]] partData = _partitionOblivious (data, left, right, pivotIndex);
        D uint pivotNewIndex = (uint)partData[0];
        data[0:size(data)] = partData[1:size(data)+1];

        //  ! uint pivotDist = pivotNewIndex - left + 1;
        D uint pivotDist = pivotNewIndex - left + 1;

        // NB! We leak a random? comparison result
        //  ! if (pivotDist == k) {
        //  !   return data[pivotNewIndex];
        if ( declassify(pivotDist == k) ) {
            return vectorLookup(data, pivotNewIndex);

        //  ! } else if (k < pivotDist) {
        } else if ( declassify(k < pivotDist) ) {
            right = pivotNewIndex - 1;

        } else {
            k = k - pivotDist;
            left = pivotNewIndex + 1;
        }
    }
    return 0;
}

/**
 *  Modification of the nthElement wrapper function into the nthElementOblivious.
 *
 *  The original function can be found from:
 *  https://github.com/sharemind-sdk/stdlib/blob/master/lib/shared3p_statistics_common.sc
 *
 */
template<domain D : shared3p>
D int64 nthElementOblivious (D int64[[1]] data, D uint64 k) {
    D uint left_ = 0;
    D uint right_ = size(data)-1;
    return _nthElementOblivious (data, left_, right_, k, true);
}

// ------------------------------------ //
// ----- test_nthElementOblivious ----- //
// ------------------------------------ //
void test_nthElementOblivious(uint64 s, pd_shared3p uint64 k)
{
    //  Output info message
    print("Testing nthElementOblivious function\n\n");

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
    pd_shared3p int64 output = nthElementOblivious(input, k);

    // Print the k-th smallest element
    print("The k-th smallest element:");
    print(declassify(output));
    print("\n");
}
