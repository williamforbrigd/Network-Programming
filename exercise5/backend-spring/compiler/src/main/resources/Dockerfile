FROM gcc:10.2.0
RUN mkdir /cpp
COPY HelloWorld.cpp /cpp
WORKDIR /cpp/
RUN g++ HelloWorld.cpp -o HelloWorld
CMD ["./HelloWorld"]
