public enum TokenType {
     // Single-character tokens.
     LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
     COMMA, SEMICOLON,

    SUCCESSOR, PROJECTION, CONSTANT, RECURSIVE,MUE,

     // One or two character tokens.
     EQUAL,
 
     // Literals.
     IDENTIFIER, NUMBER,
 
     EOF
}