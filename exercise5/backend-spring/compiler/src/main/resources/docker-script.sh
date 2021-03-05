cd src/main/resources || exit
docker build -t cpp-program . && docker run --rm cpp-program