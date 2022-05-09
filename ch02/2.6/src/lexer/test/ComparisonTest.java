package lexer.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class ComparisonTest {
    @Test
    public void testGreater() throws IOException {
        System.setIn(new ByteArrayInputStream(">".getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.GREATER, token.tag);
    }

    @Test
    public void testLess() throws IOException {
        System.setIn(new ByteArrayInputStream("<".getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.LESS, token.tag);
    }

    @Test
    public void testGreaterOrEqual() throws IOException {
        System.setIn(new ByteArrayInputStream(">=".getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.GREATER_OR_EQUAL, token.tag);
    }

    @Test
    public void testLessOrEqual() throws IOException {
        System.setIn(new ByteArrayInputStream("<=".getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.LESS_OR_EQUAL, token.tag);
    }

    @Test
    public void testEqual() throws IOException {
        System.setIn(new ByteArrayInputStream("==".getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.EQUAL, token.tag);
    }

    @Test
    public void testNotEqual() throws IOException {
        System.setIn(new ByteArrayInputStream("!=".getBytes()));
        Lexer lexer = new Lexer();
        Token token = lexer.scan();
        assertEquals(Tag.NOT_EQUAL, token.tag);
    }
}
