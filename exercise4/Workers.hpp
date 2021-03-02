#include <condition_variable>
#include <functional>
#include <iostream>
#include <list>
#include <mutex>
#include <thread>
#include <vector>

using namespace std;

class Workers {
private:
  condition_variable cv;
  mutex task_mutex;
  mutex finished_mutex;
  mutex wait_mutex;
  list<function<void()>> tasks;
  int no_threads;
  bool is_running = false;
  bool finished = false;
  vector<thread> threads;

public:
  Workers(int no_threads_) : no_threads(no_threads_) {}

  template <typename... Args> void post(Args &&...args) {
    {
      unique_lock<mutex> lock(task_mutex);
      tasks.emplace_back(bind(forward<Args>(args)...));
    }
    cv.notify_one();
  }

  void start() {
    {
      unique_lock<mutex> lock(task_mutex);
      is_running = true;
    }
    run_threads();
  }

  void run_threads() {
    for (int i = 0; i < no_threads; i++) {
      threads.emplace_back([this] {
        for (;;) {
          function<void()> task;
          {
            unique_lock<mutex> lock(task_mutex);
            if (!tasks.empty()) {
              task = *tasks.begin();
              tasks.pop_front();
            } else {
              if (finished)
                return;
              else
                cv.wait(lock);
            }
          }
          if (task) {
            task();
          }
        }
      });
    }
  }

  void join() {
    stop();                   // Calls stop() which stops all the threads
    for (auto &t : threads) { // joins the threads after they are stopped
      t.join();
    }
  }

  void stop() {
    {
      unique_lock<mutex> lock(task_mutex);
      finished = true;
    }
    cv.notify_all();
  }

  void post_timeout(function<void()> task, int ms) {
    {
      unique_lock<mutex> lock(task_mutex);
      tasks.emplace_back([task, ms] {
        this_thread::sleep_for(chrono::milliseconds(ms));
        task();
      });
    }
  }

  // Method for testing purposes.
  void post_tasks(int length, int &count, mutex &count_mutex) {
    for (int i = 0; i < length; i++) {
      post([&count, &count_mutex] {
        unique_lock<mutex> lock(count_mutex);
        count++;
      });
    }
  }
};
