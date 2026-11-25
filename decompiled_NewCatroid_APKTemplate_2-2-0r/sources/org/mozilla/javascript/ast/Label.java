package org.mozilla.javascript.ast;

/* JADX INFO: loaded from: classes4.dex */
public class Label extends Jump {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private String f1067name;

    public Label() {
        this.type = 131;
    }

    public Label(int i) {
        this(i, -1);
    }

    public Label(int i, int i2) {
        this.type = 131;
        this.position = i;
        this.length = i2;
    }

    public Label(int i, int i2, String str) {
        this(i, i2);
        setName(str);
    }

    public String getName() {
        return this.f1067name;
    }

    public void setName(String str) {
        String strTrim = str == null ? null : str.trim();
        if (strTrim == null || "".equals(strTrim)) {
            throw new IllegalArgumentException("invalid label name");
        }
        this.f1067name = strTrim;
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i) {
        return makeIndent(i) + this.f1067name + ":\n";
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        nodeVisitor.visit(this);
    }
}
