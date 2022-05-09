package lexer;

import java.io.*;
import java.util.*;

class InputBuffer {
    private PushbackInputStream input;

    public InputBuffer(InputStream in) {
        input = new PushbackInputStream(in);
    }

    public char read() throws IOException {
        if (input.available() == 0)
            input = new PushbackInputStream(System.in);
        return (char)input.read();
    }

    public void unread(char symbol) throws IOException {
        input.unread(symbol);
    }
}

public class Lexer {
    public int line = 1;
    private char peek = ' ';
    InputBuffer input = new InputBuffer(System.in);
    private Hashtable words = new Hashtable();
    
    void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    public Lexer() {
        reserve( new Word(Tag.TRUE, "true") );
        reserve( new Word(Tag.FALSE, "false") );
    }

    private boolean removeComment() throws IOException {
        if (peek != '/')
            return false;
        char nextSymbol = input.read();
        if ( nextSymbol == '/' ) {
            do {
                peek = input.read();
            } while(peek != '\n');
            ++line;
            return true;
        } else if ( nextSymbol == '*' ) {
            while (true) {
                do {
                    peek = input.read();
                    if (peek == '\n')
                        ++line;
                } while ( peek != '*');
                peek = input.read();
                if (peek == '/') {
                    return true;
                }
            }
        } else {
            input.unread(nextSymbol);
            return false;
        }
    }

    private int parseNumber() throws IOException {
        int n = 0;
        while (Character.isDigit(peek)) {
            n = 10 * n + Character.digit(peek, 10);
            peek = input.read();
        }
        return n;
    }

    private boolean possbileNumber() {
        return Character.isDigit(peek) || peek == '.';
    }

    private Token scanNumbers() throws IOException {
        if (!possbileNumber())
            return null;

        int whole = parseNumber();

        if (peek == '.') {
            peek = input.read();
            int fraction = parseNumber();
            return new Floating(whole, fraction);
        }

        return new Num(whole);
    }

    public Token scan() throws IOException {
        for( ; ; peek = input.read() ) {
            if (removeComment())
                continue;
            else if( peek == ' ' || peek == '\t' )
                continue;
            else if( peek == '\n' )
                line = line + 1;
            else
                break;
        }

        if( Character.isLetter(peek) ) {
            StringBuffer b = new StringBuffer();

            do {
                b.append(peek);
                peek = input.read();
            } while( Character.isLetterOrDigit(peek) );

            String s = b.toString();

            Word w = (Word)words.get(s);
            if( w != null )
                return w;

            w = new Word(Tag.ID, s);
            words.put(s, w);
            return w;
        }

        Token t = scanRelationOperators();
        if (t != null)
            return scanSuccessful(t);

        t = scanNumbers();
        if (t != null)
            return t;

        return scanSuccessful(new Token(peek));
    }

    private Token scanSuccessful(Token t) {
        peek = ' ';
        return t;
    }

    private Token scanRelationOperators() throws IOException {
        if ( "<!=>".indexOf(peek) != -1 ) {
            String s = Character.toString(peek);
            char nextSymbol = input.read();

            if ( nextSymbol == '=' )
                s += nextSymbol;
            else
                input.unread(nextSymbol);

            int tag = relationStringToTag(s);
            if (tag != -1)
                return new Token(tag);
        }

        return null;
    }

    private int relationStringToTag(String s) {
        switch (s) {
        case ">":
            return Tag.GREATER;
        case "<":
            return Tag.LESS;
        case ">=":
            return Tag.GREATER_OR_EQUAL;
        case "<=":
            return Tag.LESS_OR_EQUAL;
        case "==":
            return Tag.EQUAL;
        case "!=":
            return Tag.NOT_EQUAL;
        }
        return -1;
    }
}
