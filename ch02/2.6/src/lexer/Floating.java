package lexer;

public class Floating extends Token {
    public final double value;

    public Floating(int whole, int fraction) {
        super(Tag.FLOATING);
        value = Double.parseDouble(whole + "." + fraction);
    }
}
