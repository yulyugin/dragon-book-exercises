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

public class commentTest {
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
}
