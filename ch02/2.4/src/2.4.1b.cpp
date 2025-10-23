/*
 * Construct recursive-descent parsers, starting with the following grammars:
 * S -> S ( S ) S | epsilon
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

/*
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
