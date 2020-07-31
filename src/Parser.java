import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(statement());
        }

        return statements;
    }

    private Stmt statement() {
        Stmt stmt = null;
        if (checkNext(TokenType.EQUAL)) {
            String name = consume(TokenType.IDENTIFIER, "Expect identifier.").lexeme;
            consume(TokenType.EQUAL, "Expect  = .");
            stmt = new Stmt.Assign(name, expression());
        } else if (checkNext(TokenType.LEFT_PAREN)) {
            stmt = execute();
        }
        consume(TokenType.SEMICOLON, "Expect ; at end of statement.");
        return stmt;
    }

    private Stmt execute() {
        Expr right = expression();
        consume(TokenType.LEFT_PAREN, "Expect (.");

        List<Integer> arguments = new ArrayList<>();
        if (!check(TokenType.RIGHT_PAREN)) {
            do {
                arguments.add(consume(TokenType.NUMBER, "Expect numer as parameter.").literal);
            } while (match(TokenType.COMMA));
        }

        consume(TokenType.RIGHT_PAREN, "Expect ')' after arguments.");
        return new Stmt.Execute(right, arguments);
    }

    private Expr expression() {
        Expr result = null;
        if (match(TokenType.CONSTANT)) {
            consume(TokenType.LEFT_PAREN, "Expect (.");
            int total = consume(TokenType.NUMBER, "Expect number.").literal;
            consume(TokenType.COMMA, "Expect comma.");
            int number = consume(TokenType.NUMBER, "Expect number.").literal;
            consume(TokenType.RIGHT_PAREN, "Expect ).");
            result = new Expr.Constant(total, number);
        } else if (match(TokenType.SUCCESSOR)) {
            consume(TokenType.LEFT_PAREN, "Expect (.");
            Expr expr = expression();
            consume(TokenType.RIGHT_PAREN, "Expect ).");
            result = new Expr.Successor(expr);
        } else if (match(TokenType.PROJECTION)) {
            consume(TokenType.LEFT_PAREN, "Expect (.");
            int total = consume(TokenType.NUMBER, "Expect number.").literal;
            consume(TokenType.COMMA, "Expect comma.");
            int number = consume(TokenType.NUMBER, "Expect number.").literal;
            consume(TokenType.RIGHT_PAREN, "Expect ).");
            result = new Expr.Projection(total, number);
        } else if (match(TokenType.RECURSIVE)) {
            consume(TokenType.LEFT_PAREN, "Expect (.");
            Expr zero = expression();
            consume(TokenType.COMMA, "Expect comma.");
            Expr recursive = expression();
            consume(TokenType.RIGHT_PAREN, "Expect ).");
            result = new Expr.Rec(zero, recursive);

        } else if (match(TokenType.IDENTIFIER)) {
            String name = previous().lexeme;
            result = new Expr.CallVar(name);
        }
        int pos = current;
        if(match(TokenType.LEFT_PAREN)){
            List<Expr> arguments = new ArrayList<>();
            if (!check(TokenType.RIGHT_PAREN)) {
                do {
                    Expr arg = expression();
                    if(arg == null)
                    {
                        current = pos;
                        return result;
                    }
                    arguments.add(arg);
                } while (match(TokenType.COMMA));
            }

            consume(TokenType.RIGHT_PAREN, "Expect ')' after arguments.");
            result = new Expr.Call(result, arguments);
        }
        return result;

    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }

        error(peek(), message);
        return null;
    }

    private void error(Token token, String message) {
        System.out.println(message + " at token " + token);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) {
            return false;
        }
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) {
            current++;
        }
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean checkNext(TokenType tokenType) {
        if (isAtEnd())
            return false;
        if (tokens.get(current + 1).type == TokenType.EOF)
            return false;
        return tokens.get(current + 1).type == tokenType;
    }
}