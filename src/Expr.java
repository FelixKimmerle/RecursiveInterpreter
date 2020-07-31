import java.util.List;

abstract class Expr {
    interface Visitor<R> {
        R visitCallVarExpr(CallVar expr);

        R visitCallExpr(Call expr);

        R visitSuccessorExpr(Successor expr);

        R visitProjectionExpr(Projection expr);

        R visitRecExpr(Rec expr);

        R visitConstant(Constant expr);
    }

    static class Constant extends Expr {
        final int total;
        final int number;

        public Constant(int total, int number) {
            this.total = total;
            this.number = number;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitConstant(this);
        }
    }
    static class Successor extends Expr {
        final Expr param;

        Successor(Expr param) {
            this.param = param;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSuccessorExpr(this);
        }

    }

    static class Projection extends Expr {
        final int total;
        final int index;

        public Projection(int total, int index) {
            this.total = total;
            this.index = index;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitProjectionExpr(this);
        }
    }

    static class Rec extends Expr {
        final Expr zero;
        final Expr recursive;

        public Rec(Expr zero, Expr recursive){
            this.zero = zero;
            this.recursive = recursive;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitRecExpr(this);
        }
        
    }

    static class CallVar extends Expr {
        CallVar(String name) {
            this.name = name;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallVarExpr(this);
        }

        final String name;
    }

    static class Call extends Expr {
        Call(Expr expr,List<Expr> args) {
            this.args = args;
            this.expr = expr;
        }

        <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallExpr(this);
        }

        final List<Expr> args;
        final Expr expr;
    }

    abstract <R> R accept(Visitor<R> visitor);
}