# Copyright (c) 2019 Evgenii Iuliugin
#
# Permission is hereby granted, free of charge, to any person obtaining a copy
# of this software and associated documentation files (the "Software"), to deal
# in the Software without restriction, including without limitation the rights
# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
# copies of the Software, and to permit persons to whom the Software is
# furnished to do so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

set(JUNIT_VERSION "6.0.0")
set(JUNIT "junit-platform-console-standalone-${JUNIT_VERSION}.jar")
set(JUNIT_JAR ${CMAKE_BINARY_DIR}/${JUNIT})
set(JUNIT_URL "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/${JUNIT_VERSION}/${JUNIT}")
file(DOWNLOAD ${JUNIT_URL} ${JUNIT_JAR})

find_package(Java REQUIRED)
include(UseJava)

file(GLOB LEXER_FILES src/lexer/*.java)
add_jar(Lexer ${LEXER_FILES})
get_target_property(LEXER_JAR Lexer JAR_FILE)

function(add_java_test TEST_FILE)
    get_filename_component(TEST_NAME ${TEST_FILE} NAME_WE)
    add_jar(${TEST_NAME} ${TEST_FILE} INCLUDE_JARS ${LEXER_JAR} ${JUNIT_JAR})
    get_target_property(_classDir ${TEST_NAME} CLASSDIR)

    set(CLASSPATH ${_classDir}:${LEXER_JAR})
    add_test(
        NAME ${TEST_FILE}
        COMMAND ${Java_JAVA_EXECUTABLE} -jar ${JUNIT_JAR} execute -cp ${CLASSPATH} --scan-classpath)
endfunction()

add_java_test(src/lexer/test/CommentTest.java)
add_java_test(src/lexer/test/LexerTest.java)
add_java_test(src/lexer/test/FloatingPointTest.java)
add_java_test(src/lexer/test/ComparisonTest.java)
