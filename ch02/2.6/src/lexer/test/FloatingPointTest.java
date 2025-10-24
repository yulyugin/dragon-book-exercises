/*
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

package lexer.test;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import lexer.Floating;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class FloatingPointTest {
    @Test
    public void testPi() throws IOException {
        String pi = "3.1415";
        System.setIn(new ByteArrayInputStream(pi.getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.FLOATING, token.tag);
        Floating f = (Floating)token;
        assertEquals(Double.parseDouble(pi), f.value, 0.);
    }

    @Test
    public void testFloatingTwo() throws IOException {
        String n = "2.";
        System.setIn(new ByteArrayInputStream(n.getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.FLOATING, token.tag);
        Floating f = (Floating)token;
        assertEquals(Double.parseDouble(n), f.value, 0.);
    }

    @Test
    public void testFractionOnly() throws IOException {
        String n = ".3";
        System.setIn(new ByteArrayInputStream(n.getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.FLOATING, token.tag);
        Floating f = (Floating)token;
        assertEquals(Double.parseDouble(n), f.value, 0.);
    }
}
