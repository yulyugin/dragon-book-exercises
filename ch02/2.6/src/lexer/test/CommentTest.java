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
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import lexer.Lexer;
import lexer.Word;
import lexer.Token;
import lexer.Tag;

public class CommentTest {
    @Test
    public void testSingleLineComment() throws IOException {
        final String id = "someId";
        final String comment = "// Random comment";
        final String input = comment + "\n" + id;
        Lexer lexer = new Lexer();
        System.setIn(new ByteArrayInputStream(
                        input.getBytes()));
        Token token = lexer.scan();
        assertEquals(2, lexer.line);
        assertEquals(Tag.ID, token.tag);
        Word idToken = (Word)token;
        assertEquals(idToken.lexeme, id);
    }

    @Test
    public void testTwoSingleLineComments() throws IOException {
        final String id = "someId";
        final String comment = "// Random comment";
        final String input = comment + "\n" + comment + '\n' + id;
        Lexer lexer = new Lexer();
        System.setIn(new ByteArrayInputStream(
                        input.getBytes()));
        Token token = lexer.scan();
        assertEquals(3, lexer.line);
        assertEquals(Tag.ID, token.tag);
        Word idToken = (Word)token;
        assertEquals(idToken.lexeme, id);
    }

    @Test
    public void testMisformedComment() throws IOException {
        final String comment = "/misformed comment";
        final String input = comment + "\n" + "doesntMatter";
        Lexer lexer = new Lexer();
        System.setIn(new ByteArrayInputStream(
                        input.getBytes()));
        Token token = lexer.scan();
        assertEquals(1, lexer.line);
        assertNotEquals(Tag.ID, token.tag);
    }

    @Test
    public void testEmptyComment() throws IOException {
        final String emptyComment = "/**/";
        final String id = "newID";
        final String input = emptyComment + id;
        System.setIn(new ByteArrayInputStream(
                        input.getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(lexer.line, 1);
        assertEquals(Tag.ID, token.tag);
    }

    @Test
    public void testMultilineComment() throws IOException {
        final String longComment = "/* Long\ncomment*/";
        final String id = "anotherID";
        final String input = longComment + id;
        System.setIn(new ByteArrayInputStream(
                        input.getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(lexer.line, 2);
        assertEquals(Tag.ID, token.tag);
    }

    @Test
    public void testTwoMultilineComments() throws IOException {
        final String firstComment = "/* First comment */";
        final String secondComment = "/* Second comment */";
        final String id = "anotherID";
        final String input = firstComment + secondComment + id;
        System.setIn(new ByteArrayInputStream(
                        input.getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(lexer.line, 1);
        assertEquals(Tag.ID, token.tag);
    }
}
