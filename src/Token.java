public class Token {
    final TokenType type;
    final String lexeme;
    final Integer literal;
    final int line;

    Token(TokenType type, String lexeme, Integer literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}