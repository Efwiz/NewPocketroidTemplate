package org.mozilla.javascript.ast;

/* JADX INFO: loaded from: classes4.dex */
public class PropertyGet extends InfixExpression {
    public PropertyGet() {
        this.type = 33;
    }

    public PropertyGet(int i) {
        super(i);
        this.type = 33;
    }

    public PropertyGet(int i, int i2) {
        super(i, i2);
        this.type = 33;
    }

    public PropertyGet(int i, int i2, AstNode astNode, Name name2) {
        super(i, i2, astNode, name2);
        this.type = 33;
    }

    public PropertyGet(AstNode astNode, Name name2) {
        super(astNode, name2);
        this.type = 33;
    }

    public PropertyGet(AstNode astNode, Name name2, int i) {
        super(33, astNode, name2, i);
        this.type = 33;
    }

    public AstNode getTarget() {
        return getLeft();
    }

    public void setTarget(AstNode astNode) {
        setLeft(astNode);
    }

    public Name getProperty() {
        return (Name) getRight();
    }

    public void setProperty(Name name2) {
        setRight(name2);
    }

    @Override // org.mozilla.javascript.ast.InfixExpression, org.mozilla.javascript.ast.AstNode
    public String toSource(int i) {
        return makeIndent(i) + getLeft().toSource(0) + "." + getRight().toSource(0);
    }

    @Override // org.mozilla.javascript.ast.InfixExpression, org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            getTarget().visit(nodeVisitor);
            getProperty().visit(nodeVisitor);
        }
    }
}
