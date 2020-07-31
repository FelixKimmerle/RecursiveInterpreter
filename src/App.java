import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner("add = rec(pi(1,1),s(pi(3,1))); delta = rec(c(0,0),add(pi(2,1),s(pi(2,2)))); delta(6);");
        List<Token> tokens = scanner.scanTokens();
        for (Token token : tokens) {
            System.out.println(token);
        }
        Parser parser = new Parser(tokens);
        Stmt.Block stmts = parser.parse();
        
        Compiler compiler = new Compiler();
        compiler.visitBlockStmt(stmts);
    }
}
