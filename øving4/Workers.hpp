#include <condition_variable>
#include <functional>
#include <iostream>
#include <list>
#include <mutex>
#include <thread>
#include <vector>

using namespace std;

class Workers {
public:
  Workers(bool start_threads) : is_running(start_threads) {
    if(start_threads) 
      start(); 
  }
  Workers(int no_threads_) : no_threads(no_threads_) {}

  ~Workers() {
    stop();
  }

  void start() {
    {
      unique_lock<mutex> lock(task_mutex);
      if(is_running == true) return;
      is_running = true;
    }
    add_threads();
  }

  void add_threads() {
    for(int i=0; i < no_threads; i++) {
      threads.emplace_back([this] {
        for(;;) {
          function<void()> task;
          {
            unique_lock<mutex> lock(task_mutex);
            //TODO: use conditional variables. 
            cv.wait(lock, [&] {
              return !tasks.empty() + !is_running;
            });
            if(!tasks.empty()) {
              task = *tasks.begin();
              tasks.pop_front();
            }
          }
          if(task)
            task();
        }
      });
    }
  }

  template<typename... Args> 
  void post(Args&&... args) {
    {
      unique_lock<mutex> lock(task_mutex);
      tasks.emplace_back(bind(forward<Args>(args)...));
    }
    cv.notify_all();
  }

/*
  void post2(function<void()> task) {
    {
      unique_lock<mutex> lock(task_mutex);
      tasks.emplace_back(task);
    }
    cv.notify_all();
  }
  */

 void join() {
   for(auto &t : threads) {
     t.join();
   }
 }

 void stop() {
   {
     unique_lock<mutex> lock(task_mutex);
     if(is_running == false) return;
     is_running = false;
   }
   cv.notify_all();
 }

  

private:
  condition_variable cv;
  mutex task_mutex;
  list<function<void()>> tasks;
  int no_threads;
  vector<thread> threads;
  bool is_running = false;
};
