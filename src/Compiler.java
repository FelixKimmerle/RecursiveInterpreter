import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Compiler implements Expr.Visitor<Integer>, Stmt.Visitor<Void> {

    Stack<LinkedList<Integer>> stack = new Stack<LinkedList<Integer>>();

    public void interpret(List<Stmt> stmts) {
        for (Stmt stmt : stmts) {
            stmt.accept(this);
        }
    }


    @Override
    public Void visitExecuteStmt(Stmt.Execute stmt) {
        stack.push(stmt.arguments);
        System.out.println(stmt.expression.accept(this));
        stack.pop();
        return null;
    }


    @Override
    public Integer visitCallExpr(Expr.Call expr) {
        LinkedList<Integer> args = new LinkedList<Integer>();
        for (Expr arg : expr.args) {
            args.add(arg.accept(this));
        }
        stack.push(args);
        Integer erg = expr.expr.accept(this);
        stack.pop();
        return erg;
    }

    @Override
    public Integer visitSuccessorExpr(Expr.Successor expr) {
        return expr.param.accept(this) + 1;
    }

    @Override
    public Integer visitProjectionExpr(Expr.Projection expr) {
        return stack.peek().get(expr.index - 1);
    }

    @Override
    public Integer visitRecExpr(Expr.Rec expr) {
        LinkedList<Integer> params = new LinkedList<>(stack.peek());
        params.removeFirst();
        stack.push(params);
        int prev = expr.zero.accept(this);
        stack.pop();
        for (int i = 0; i < stack.peek().get(0); i++) {
            params = new LinkedList<>(stack.peek());
            params.removeFirst();
            params.addFirst(i);
            params.addFirst(prev);
            stack.push(params);
            prev = expr.recursive.accept(this);
            stack.pop();
        }
        return prev;
    }

    @Override
    public Integer visitConstant(Expr.Constant expr) {
        return expr.number;
    }

    @Override
    public Integer visitMue(Expr.Mue expr) {
        int x = 0;
        stack.peek().addFirst(x);
        while(expr.function.accept(this) != 0){
            x++;
            stack.peek().set(0, x);
        }
        return x;
    }

}