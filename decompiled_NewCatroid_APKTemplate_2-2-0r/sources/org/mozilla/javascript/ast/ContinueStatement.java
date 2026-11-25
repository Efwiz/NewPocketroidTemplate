package org.mozilla.javascript.ast;

import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes4.dex */
public class ContinueStatement extends Jump {
    private Name label;
    private Loop target;

    public ContinueStatement() {
        this.type = 122;
    }

    public ContinueStatement(int i) {
        this(i, -1);
    }

    public ContinueStatement(int i, int i2) {
        this.type = 122;
        this.position = i;
        this.length = i2;
    }

    public ContinueStatement(Name name2) {
        this.type = 122;
        setLabel(name2);
    }

    public ContinueStatement(int i, Name name2) {
        this(i);
        setLabel(name2);
    }

    public ContinueStatement(int i, int i2, Name name2) {
        this(i, i2);
        setLabel(name2);
    }

    public Loop getTarget() {
        return this.target;
    }

    public void setTarget(Loop loop) {
        assertNotNull(loop);
        this.target = loop;
        setJumpStatement(loop);
    }

    public Name getLabel() {
        return this.label;
    }

    public void setLabel(Name name2) {
        this.label = name2;
        if (name2 != null) {
            name2.setParent(this);
        }
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public String toSource(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i));
        sb.append(PsiKeyword.CONTINUE);
        if (this.label != null) {
            sb.append(" ");
            sb.append(this.label.toSource(0));
        }
        sb.append(";\n");
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.Jump, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        Name name2;
        if (!nodeVisitor.visit(this) || (name2 = this.label) == null) {
            return;
        }
        name2.visit(nodeVisitor);
    }
}
