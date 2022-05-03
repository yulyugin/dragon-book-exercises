cmake_minimum_required(VERSION 3.11)

include(FetchContent)
FetchContent_Declare(
  googletest
  GIT_REPOSITORY https://github.com/google/googletest.git
  GIT_TAG        e2239ee6043f73722e7aa812a459f54a28552929 # release-1.11.0
)
FetchContent_MakeAvailable(googletest)

project(dragon-book-exercises)

enable_testing()
include(GoogleTest)

add_executable(p2_1 ch02/P2.1/p2_1.cpp)
target_link_libraries(p2_1 gtest_main)
gtest_discover_tests(p2_1)

add_executable(2.4.1c ch02/sec2.4/src/2.4.1c.cpp)
target_link_libraries(2.4.1c gtest_main)
gtest_discover_tests(2.4.1c)
