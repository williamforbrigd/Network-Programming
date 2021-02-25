#include <algorithm>
#include <iostream>
#include <iterator>
#include <mutex>
#include <thread>
#include <vector>

using namespace std;

/*
 * Program to find prime numbers between two numbers with a given number of
 * threads.
 * The program prints out the number of primes and all the primes that was
 * found.
 * */

// To check if a given number is prime.
bool isPrime(int num) {
  int count = 0;
  if (num == 0 || num == 1)
    return false;
  else if (num == 2) {
    return true;
  }
  int tmp = num;
  while (--tmp >= 2) {
    if (num % tmp == 0)
      count++;
  }
  if (count == 0)
    return true;
  else
    return false;
}

// Finds all the primes in the interval.
// Using a mutex to lock when incrementing the count and when adding the prime
// numbers to the vector.
int findPrimes(unsigned long long &count, int num1, int num2,
               vector<int> &primeNums, mutex &count_mutex) {
  for (unsigned int j = num1; j <= num2; j++) {
    if (isPrime(j)) {
      unique_lock<mutex> lock(count_mutex);
      count++;
      primeNums.push_back(j);
    }
  }
  return count;
}

bool sortFunc(int i, int j) { return (i < j); }

int main() {
  int num1, num2, noThreads;
  cout << "Enter lower limit: " << endl;
  cin >> num1;
  cout << "Enter the upper limit: " << endl;
  cin >> num2;
  cout << "Enter number of threads: " << endl;
  cin >> noThreads;

  if (num1 > num2) {
    cerr << "The lower limit cannot be greater than the upper." << endl;
    return 1;
  } else if (noThreads > (num2 - num1)) {
    cerr << "The number of threads cannot be greater than the length of the "
            "interval."
         << endl;
    return 1;
  }

  unsigned long long count = 0;
  vector<int> primeNums;
  mutex count_mutex;
  vector<thread> threads;

  int lower = num1;
  int upper = num2;
  int split = (upper - lower) / noThreads;
  // The upper limit is always 1 split greater than the lower.
  upper = split + lower;
  for (int i = 0; i < noThreads; i++) {
    // The last thread has to find the rest if the primes, if any.
    // Adds 1 because the threads goes to the upper limit - 1.
    if (i == noThreads - 1 && upper % num2 > 0) {
      upper = num2 + 1;
    } else if (i == noThreads - 1) {
      upper++;
    }

    threads.push_back(thread(findPrimes, ref(count), lower, upper - 1,
                             ref(primeNums), ref(count_mutex)));

    lower += split;
    upper += split;
  }

  for (auto &thread : threads) {
    thread.join();
  }

  cout << "Number of primes: " << count << endl;

  sort(primeNums.begin(), primeNums.end(), sortFunc);

  // Printing the prime numbers using an iterator.
  for (auto i = primeNums.begin(); i != primeNums.end(); i++) {
    cout << *i << " ";
  }

  return 0;
}
