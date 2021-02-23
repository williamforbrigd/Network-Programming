#include "Workers.hpp"
#include <iostream>
#include <thread>

using namespace std;

int main() {
  Workers worker_threads(4);
  Workers event_loop(1);

  worker_threads.start(); // Create 4 internal threads.
  event_loop.start();     // Create 1 internal thread.

  worker_threads.post([] {
    this_thread::sleep_for(1s);
    cout << "Task A running in thread " << this_thread::get_id() << endl;
  });

  worker_threads.post([] {
    cout << "Task B running in thread " << this_thread::get_id() << endl;
  });

  event_loop.post([] {
    cout << "Task C running in thread " << this_thread::get_id() << endl;
  });

  event_loop.post([] {
    cout << "Task D running in thread " << this_thread::get_id() << endl;
  });

  worker_threads.join(); //Calls join() on the worker threads
  event_loop.join(); //Calls join() on the event loop
}
