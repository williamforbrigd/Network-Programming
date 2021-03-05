FROM gcc:10.2.0
RUN mkdir /cpp
COPY main.cpp /cpp
WORKDIR /cpp/
RUN g++ main.cpp -o main
CMD ["./main"]
