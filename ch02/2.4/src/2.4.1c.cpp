/* Copyright (C) 2022 Evgenii Iuliugin
 *
 * Construct recursive-descent parsers, starting with the following grammars:
 * S -> 0 S 1 | 0 1
 */

#include <gtest/gtest.h>

class SyntaxError : public std::exception {};

class Parser {
    char lookahead;
    std::stringstream parse_string;

    char NextToken() {
        return parse_string.get();
    }

    void Match(const char token) {
        if (lookahead == token)
            lookahead = NextToken();
        else
            throw SyntaxError();
    }

    void S() {
        if (lookahead == '0') {
            Match('0');
            if (lookahead != '1') {
                S();
            }
            Match('1');
        } else {
            throw SyntaxError();
        }
    }

public:
    void Parse(std::string s) {
        parse_string = std::stringstream(s);
        lookahead = NextToken();
        S();
    }
};

Parser E241c;

TEST(E241c, testEmpty) {
    EXPECT_THROW(E241c.Parse(""), SyntaxError);
}

TEST(E241c, testInvalid) {
    EXPECT_THROW(E241c.Parse("1"), SyntaxError);
}

TEST(E241c, testSimple) {
    E241c.Parse("01");
}

TEST(E241c, test0011) {
    E241c.Parse("0011");
}

TEST(E241c, testIncomplete) {
    EXPECT_THROW(E241c.Parse("001"), SyntaxError);
}
