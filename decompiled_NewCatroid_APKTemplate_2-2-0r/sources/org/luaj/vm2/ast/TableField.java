package org.luaj.vm2.ast;

/* JADX INFO: loaded from: classes6.dex */
public class TableField extends SyntaxElement {
    public final Exp index;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final String f1054name;
    public final Exp rhs;

    public TableField(Exp exp, String str, Exp exp2) {
        this.index = exp;
        this.f1054name = str;
        this.rhs = exp2;
    }

    public static TableField keyedField(Exp exp, Exp exp2) {
        return new TableField(exp, null, exp2);
    }

    public static TableField listField(Exp exp) {
        return new TableField(null, null, exp);
    }

    public static TableField namedField(String str, Exp exp) {
        return new TableField(null, str, exp);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
