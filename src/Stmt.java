import java.util.List;

abstract class Stmt {
    interface Visitor<R> {
        R visitAssingStmt(Assign stmt);

        R visitExecuteStmt(Execute stmt);
    }

    static class Assign extends Stmt {
        Assign(String name, Expr value) {
            this.name = name;
            this.value = value;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssingStmt(this);
        }

        final String name;
        final Expr value;
    }

    static class Execute extends Stmt {
        Execute(Expr expression, List<Integer> arguments) {
            this.expression = expression;
            this.arguments = arguments;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitExecuteStmt(this);
        }

        final Expr expression;
        final List<Integer> arguments;
    }

    abstract <R> R accept(Visitor<R> visitor);
}