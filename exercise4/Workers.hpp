#include <condition_variable>
#include <functional>
#include <iostream>
#include <list>
#include <mutex>
#include <thread>
#include <vector>
#include <atomic>

using namespace std;

class Workers {
private:
  condition_variable cv;
  mutex task_mutex;
  mutex finished_mutex;
  list<function<void()>> tasks;
  int no_threads;
  bool is_running = false;
  atomic<bool> finished{false};
  vector<thread> threads;

public:
  Workers(bool start_threads) : is_running(start_threads) {
    if(start_threads) 
      start(); 
  }
  Workers(int no_threads_) : no_threads(no_threads_) {}

  ~Workers() {
    cout << "Destructor is called." << endl;
  }

  template<typename... Args> 
  void post(Args&&... args) {
    {
      unique_lock<mutex> lock(task_mutex);
      tasks.emplace_back(bind(forward<Args>(args)...));
    }
    {
        //unique_lock<mutex> lock(wait_mutex);
        //wait = false;
    }
    //cv.notify_all();
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
    for(int i=0; i < no_threads; i++) {
      threads.emplace_back([this] {
        for(;;) {
            function<void()> task;
            {
              unique_lock<mutex> lock(task_mutex);
              if(!tasks.empty()) {
                task = *tasks.begin();
                tasks.pop_front();
              } else {
                if(finished) return;
                cv.wait(lock);
              }
          }
          if(task) {
            task();
          } 
        }
      });
    }
  }

 void join() {
   stop();
   for(auto &t : threads) {
     t.join();
   }
 }

 void stop() {
   finished.exchange(true);
   cv.notify_all();
 }

 void post_tasks(int length, int &count, mutex &count_mutex) {
   for(int i=0; i < length; i++) {
     post([&count, &count_mutex]{
       unique_lock<mutex> lock(count_mutex);
       count++;
     });
   }
 }
};
