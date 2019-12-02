# Twitter Keyword Search

[![Build Status](https://travis-ci.com/8128/TwitterKeywordSearch.svg?branch=master)](https://travis-ci.com/8128/TwitterKeywordSearch)

The Twitter Keyword Search is a Springboot web application for keyword searching.

## Architecture

### Database

The backend database is MySQL, it has pre-stored CSV file for 30000+ twitters. It can be used as demo. Also, users are welcomed to upload their own databases for twitter searching. Everytime users upload a new file to the application, the temp table will be cleared and new data will be sent in.

- t_twitter_internal
- t_twitter_temp

The project is using MyBatis as the solution for MySQL reading. TwitterData object will be generated when the tables are being read.

### Template Engine:

Thymeleaf is chose to be the template engine of this project.

## Algorithm

### Bucket Sort

Bucket sort, or bin sort, is a sorting algorithm that works by distributing the elements of an array into a number of buckets. Each bucket is then sorted individually, either using a different sorting algorithm, or by recursively applying the bucket sorting algorithm. It is a distribution sort, a generalization of pigeonhole sort, and is a cousin of radix sort in the most-to-least significant digit flavor. Bucket sort can be implemented with comparisons and therefore can also be considered a comparison sort algorithm. The computational complexity depends on the algorithm used to sort each bucket, the number of buckets to use, and whether the input is uniformly distributed.

Bucket sort works as follows:

1. Set up an array of initially empty "buckets".
2. Scatter: Go over the original array, putting each object in its bucket.
3. Sort each non-empty bucket.
4. Gather: Visit the buckets in order and put all elements back into the original array.

#### Analysis

##### Worst-case analysis

Bucket sort is mainly useful when input is uniformly distributed over a range. When the input contains several keys that are close to each other (clustering), those elements are likely to be placed in the same bucket, which results in some buckets containing more elements than average. The worst-case scenario occurs when all the elements are placed in a single bucket. The overall performance would then be dominated by the algorithm used to sort each bucket, which is typically O(n^2) insertion sort, making bucket sort less optimal than O(n*log(n)) comparison sort algorithms like Quicksort.

##### Average-case analysis

K is the bucket size. If k is chosen to be k=Theta (n), then bucket sort runs in O(n) average time, given a uniformly distributed input.

### Heap Sort

In computer science, heapsort is a comparison-based sorting algorithm. Heapsort can be thought of as an improved selection sort: like that algorithm, it divides its input into a sorted and an unsorted region, and it iteratively shrinks the unsorted region by extracting the largest element and moving that to the sorted region. The improvement consists of the use of a heap data structure rather than a linear-time search to find the maximum.

Although somewhat slower in practice on most machines than a well-implemented quicksort, it has the advantage of a more favorable worst-case O(n log n) runtime. Heapsort is an in-place algorithm, but it is not a stable sort.

The Heapsort algorithm involves preparing the list by first turning it into a max heap. The algorithm then repeatedly swaps the first value of the list with the last value, decreasing the range of values considered in the heap operation by one, and sifting the new first value into its position in the heap. This repeats until the range of considered values is one value in length.

The steps are:

1. Call the buildMaxHeap() function on the list. Also referred to as heapify(), this builds a heap from a list in O(n) operations.
2. Swap the first element of the list with the final element. Decrease the considered range of the list by one.
3. Call the siftDown() function on the list to sift the new first element to its appropriate index in the heap.
4. Go to step (2) unless the considered range of the list is one element.

The buildMaxHeap() operation is run once, and is O(n) in performance. The siftDown() function is O(log n), and is called n times. Therefore, the performance of this algorithm is O(n + n log n) = O(n log n).

## Functions