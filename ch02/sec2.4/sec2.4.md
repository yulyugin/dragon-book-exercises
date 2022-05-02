# 2.4 Parsing

## Exercise 2.4.1
Construct recursive-descent parsers, starting with the following grammars:

a) `S -> 0 S 1 | 0 1`
```
// Translation that produces the same result:
// S -> 0 S1 1
// S1 -> 0 1 | epsilon

void match(const char token) {
    if (lookahead == token)
        lookahead = token;
    else
        error();
}

void S() {
    if (lookahead == '0') {
        match('0');
        S();
        match('1');
    } else {
        error();
    }
}

void S1() {
    if (lookahead == '0') {
        match('0');
        match('1');
    } else {
        // match(epsilon);
    }
}
```

b) `S -> + S S | - S S | a`

```
void S() {
    switch (lookahead) {
    case '+':
        match('+');
        S();
        S();
        break;
    case '-':
        match('-');
        S();
        S();
        break;
    case 'a':
        march('a');
        break;
    default:
        error();
    }
}
```
