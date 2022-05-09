/* Copyright (C) 2022 Evgenii Iuliugin
 *
 * Construct recursive-descent parsers, starting with the following grammars:
 * S -> S ( S ) S | epsilon
 *
 * The given grammar cannot be used as is, because it is left recursive.
 * Recursive-descent parser loops forever if applied for left recursive grammar.
 *
 * Apply the following transformation from left recursive grammar
 * A -> A alpha | beta
 * to
 * A -> beta R
 * R -> alpha R | epsilon
 *
 * Result for the given translation:
 * S -> ( S ) S | epsilon
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
        case '(':
            Match('(');
            if (lookahead != ')')
                S();
            Match(')');
            break;
        case EOF:
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

Parser E241b;

TEST(E241b, testEmpty) {
    E241b.Parse("");
}

TEST(E241b, testInvalid) {
    EXPECT_THROW(E241b.Parse(")"), SyntaxError);
}

TEST(E241b, testSimple) {
    E241b.Parse("()");
}

TEST(E241b, testComplex) {
    E241b.Parse("(())(())()");
}
