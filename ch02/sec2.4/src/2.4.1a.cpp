/* Copyright (C) 2022 Evgenii Iuliugin
 *
 * Construct recursive-descent parsers, starting with the following grammars:
 * S -> + S S | - S S | a
 */

#include <gtest/gtest.h>

class SyntaxError : public std::exception {};

class Parser {
    char lookahead;
    std::stringstream parse_string;

    char NextToken() {
        return parse_string.get();
    }

    void Match(char token) {
        if (lookahead == token)
            lookahead = NextToken();
        else
            throw SyntaxError();
    }

    void S() {
        switch (lookahead) {
        case '+':
            Match('+');
            S();
            S();
            break;
        case '-':
            Match('-');
            S();
            S();
            break;
        case 'a':
            Match('a');
            break;
        default:
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

Parser E241a;

TEST(E241a, testEmpty) {
    EXPECT_THROW(E241a.Parse(""), SyntaxError);
}

TEST(E241a, testIncompletePlus) {
    EXPECT_THROW(E241a.Parse("+a"), SyntaxError);
}

TEST(E241a, testA) {
    E241a.Parse("a");
}

TEST(E241a, testSimplePlus) {
    E241a.Parse("+aa");
}

TEST(E241a, testSimpleMinus) {
    E241a.Parse("-aa");
}

TEST(E241a, testPlusMinus) {
    E241a.Parse("+-aa+aa");
}
