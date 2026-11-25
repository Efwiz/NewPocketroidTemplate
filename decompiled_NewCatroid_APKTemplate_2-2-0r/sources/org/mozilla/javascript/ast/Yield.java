package org.mozilla.javascript.ast;

import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: loaded from: classes4.dex */
public class Yield extends AstNode {
    private AstNode value;

    public Yield() {
        this.type = 73;
    }

    public Yield(int i) {
        super(i);
        this.type = 73;
    }

    public Yield(int i, int i2) {
        super(i, i2);
        this.type = 73;
    }

    public Yield(int i, int i2, AstNode astNode, boolean z) {
        super(i, i2);
        this.type = z ? 166 : 73;
        setValue(astNode);
    }

    public AstNode getValue() {
        return this.value;
    }

    public void setValue(AstNode astNode) {
        this.value = astNode;
        if (astNode != null) {
            astNode.setParent(this);
        }
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i) {
        return this.value == null ? PsiKeyword.YIELD : "yield " + this.value.toSource(0);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        AstNode astNode;
        if (!nodeVisitor.visit(this) || (astNode = this.value) == null) {
            return;
        }
        astNode.visit(nodeVisitor);
    }
}
