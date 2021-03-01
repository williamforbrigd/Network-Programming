
#include "Workers.hpp"
#include <iostream>
#include <thread>

using namespace std;

int main() {
  Workers worker_threads(4);
  Workers event_loop(1);

  worker_threads.start(); // Create 4 internal threads.
  event_loop.start();     // Create 1 internal thread.

  // mutex count_mutex;
  // int count = 0;
  // worker_threads.post_tasks(12356, ref(count), ref(count_mutex));
  // event_loop.post_tasks(644, ref(count), ref(count_mutex));

  worker_threads.post([] { cout << "Task A" << endl; });

  worker_threads.post([] { cout << "Task B" << endl; });

  event_loop.post([] { cout << "Task C" << endl; });

  event_loop.post([] { cout << "Task D" << endl; });

  // The task is executed after 3 seconds.
  worker_threads.post_timeout([] { cout << "Task E" << endl; }, 3000);

  // The tasks is executed after 3 seconds.
  event_loop.post_timeout([] { cout << "Task F" << endl; }, 3000);

  worker_threads.join(); // Calls join() on the worker threads
  event_loop.join();     // Calls join() on the event loop

  // cout << "The count is: " << count << endl;

  return 0;
}
