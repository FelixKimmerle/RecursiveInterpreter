import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class App {
    public static void runfile(String path) {
        try {
            String source = new java.util.Scanner(new File(path)).useDelimiter("\\Z").next();
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scanTokens();
            Parser parser = new Parser(tokens);
            List<Stmt> stmts = parser.parse();
            Compiler compiler = new Compiler();
            compiler.interpret(stmts);
        } catch (FileNotFoundException e) {
            System.out.println("File: " + path + " not found!");
        }
    }

    public static void main(String[] args) throws Exception {
        runfile("test.pr");
    }
}
