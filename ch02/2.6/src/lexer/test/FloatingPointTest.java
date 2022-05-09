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
