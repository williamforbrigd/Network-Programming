#include "Workers.hpp"
#include <iostream>
#include <thread>

using namespace std;

int main() {
  Workers worker_threads(4);
  Workers event_loop(1);

  worker_threads.start(); // Create 4 internal threads.
  event_loop.start();     // Create 1 internal thread.
  mutex count_mutex;
  int count = 0;

  worker_threads.post_tasks(12356, ref(count), ref(count_mutex));
  event_loop.post_tasks(644, ref(count), ref(count_mutex));

/*
  worker_threads.post([] {
    //cout << "Task A running in thread " << this_thread::get_id() << endl;
    cout << "Task A" << endl;
  });

  worker_threads.post([] {
    //cout << "Task B running in thread " << this_thread::get_id() << endl;
    cout << "Task B" << endl;
  });

  event_loop.post([] {
    //cout << "Task C running in thread " << this_thread::get_id() << endl;
    cout << "Task C" << endl;
  });

  event_loop.post([] {
    //cout << "Task D running in thread " << this_thread::get_id() << endl;
    cout << "Task D" << endl;
  });
  */

  worker_threads.join(); //Calls join() on the worker threads
  event_loop.join(); //Calls join() on the event loop

  cout << "The count is: " << count << endl;

  return 0;
}
