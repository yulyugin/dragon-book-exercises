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
