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
        assertEquals(numToken.value, v);
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
        assertEquals(numToken.value, v);
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
}
