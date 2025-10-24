/*
 * Construct recursive-descent parsers, starting with the following grammars:
 * S -> + S S | - S S | a
 *
 * Copyright (C) 2022 Evgenii Iuliugin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
