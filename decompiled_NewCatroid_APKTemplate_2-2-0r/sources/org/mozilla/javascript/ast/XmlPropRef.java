package org.mozilla.javascript.ast;

import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: loaded from: classes4.dex */
public class XmlPropRef extends XmlRef {
    private Name propName;

    public XmlPropRef() {
        this.type = 80;
    }

    public XmlPropRef(int i) {
        super(i);
        this.type = 80;
    }

    public XmlPropRef(int i, int i2) {
        super(i, i2);
        this.type = 80;
    }

    public Name getPropName() {
        return this.propName;
    }

    public void setPropName(Name name2) {
        assertNotNull(name2);
        this.propName = name2;
        name2.setParent(this);
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public String toSource(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(makeIndent(i));
        if (isAttributeAccess()) {
            sb.append(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        }
        if (this.namespace != null) {
            sb.append(this.namespace.toSource(0));
            sb.append("::");
        }
        sb.append(this.propName.toSource(0));
        return sb.toString();
    }

    @Override // org.mozilla.javascript.ast.AstNode
    public void visit(NodeVisitor nodeVisitor) {
        if (nodeVisitor.visit(this)) {
            if (this.namespace != null) {
                this.namespace.visit(nodeVisitor);
            }
            this.propName.visit(nodeVisitor);
        }
    }
}
