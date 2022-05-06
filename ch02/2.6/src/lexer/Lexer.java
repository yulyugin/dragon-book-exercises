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
                    //peek = input.read();
                    return true;
                }
            }
        } else {
            input.unread(nextSymbol);
            return false;
        }
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

        if( Character.isDigit(peek) ) {
            int v = 0;
            do {
                v = 10*v + Character.digit(peek, 10);
                peek = input.read();
            } while( Character.isDigit(peek) );

            return new Num(v);
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

        Token t = new Token(peek);
        peek = ' ';
        return t;
    }
}
