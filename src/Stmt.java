import java.util.LinkedList;

abstract class Stmt {
    interface Visitor<R> {
        R visitExecuteStmt(Execute stmt);
    }

    static class Execute extends Stmt {
        Execute(Expr expression, LinkedList<Integer> arguments) {
            this.expression = expression;
            this.arguments = arguments;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitExecuteStmt(this);
        }

        final Expr expression;
        final LinkedList<Integer> arguments;
    }

    abstract <R> R accept(Visitor<R> visitor);
}