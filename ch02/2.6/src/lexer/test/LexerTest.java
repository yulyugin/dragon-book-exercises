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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import lexer.Lexer;
import lexer.Token;
import lexer.Word;
import lexer.Tag;
import lexer.Num;

public class LexerTest {
    @Test
    public void scanDigit() throws IOException {
        final int v = 2;
        Lexer lexer = new Lexer();
        System.setIn(new ByteArrayInputStream(
                            Integer.toString(v).getBytes()));
        Token token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);
        Num numToken = (Num)token;
        assertEquals(v, numToken.value);
    }

    @Test
    public void scanNumber() throws IOException {
        final int v = 22;
        Lexer lexer = new Lexer();
        System.setIn(new ByteArrayInputStream(
                            Integer.toString(v).getBytes()));
        Token token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);
        Num numToken = (Num)token;
        assertEquals(v, numToken.value);
    }

    @Test
    public void scanTrue() throws IOException {
        Lexer lexer = new Lexer();
        System.setIn(new ByteArrayInputStream("true".getBytes()));
        Token token = lexer.scan();
        assertEquals(Tag.TRUE, token.tag);
    }

    @Test
    public void scanID() throws IOException {
        Lexer lexer = new Lexer();
        String id = "id123a";
        System.setIn(new ByteArrayInputStream(id.getBytes()));
        Token token = lexer.scan();
        assertEquals(Tag.ID, token.tag);
        Word wordToken = (Word)token;
        assertEquals(id, wordToken.lexeme);
    }

    @Test
    public void scanSpaceSeparatedValues() throws IOException {
        Lexer lexer = new Lexer();
        String id = "May";
        int num = 4;
        String input = id + " " + Integer.toString(num);
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Token token = lexer.scan();
        assertEquals(Tag.ID, token.tag);
        Word wordToken = (Word)token;
        assertEquals(id, wordToken.lexeme);

        token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);
        Num numToken = (Num)token;
        assertEquals(num, numToken.value);
    }

    @Test
    public void scanTabSeparatedValues() throws IOException {
        Lexer lexer = new Lexer();
        String id = "May";
        int num = 4;
        String input = id + "\t" + Integer.toString(num);
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Token token = lexer.scan();
        assertEquals(Tag.ID, token.tag);
        Word wordToken = (Word)token;
        assertEquals(id, wordToken.lexeme);

        token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);
        Num numToken = (Num)token;
        assertEquals(num, numToken.value);
    }

    @Test
    public void scanMultiline() throws IOException {
        Lexer lexer = new Lexer();
        String id = "May";
        int num = 4;
        String input = id + "\n" + Integer.toString(num);
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Token token = lexer.scan();
        assertEquals(Tag.ID, token.tag);
        Word wordToken = (Word)token;
        assertEquals(id, wordToken.lexeme);

        token = lexer.scan();
        assertEquals(Tag.NUM, token.tag);
        Num numToken = (Num)token;
        assertEquals(num, numToken.value);
    }

    @Test
    public void scanNewLine() throws IOException {
        Lexer lexer = new Lexer();
        String id = "newLineID";
        String input = "\n" + id;
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Token token = lexer.scan();
        assertEquals(2, lexer.line);
        assertEquals(Tag.ID, token.tag);
        Word wordToken = (Word)token;
        assertEquals(id, wordToken.lexeme);
    }
}
