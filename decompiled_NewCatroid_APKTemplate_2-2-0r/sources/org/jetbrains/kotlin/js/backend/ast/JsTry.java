package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.js.util.AstUtil;

/* JADX INFO: loaded from: classes5.dex */
public class JsTry extends SourceInfoAwareJsNode implements JsStatement {
    private final List<JsCatch> catches;
    private JsBlock finallyBlock;
    private JsBlock tryBlock;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/js/backend/ast/JsTry", "deepCopy"));
    }

    public JsTry() {
        this.catches = new SmartList();
    }

    public JsTry(JsBlock jsBlock, List<JsCatch> list, JsBlock jsBlock2) {
        this.tryBlock = jsBlock;
        this.catches = list;
        this.finallyBlock = jsBlock2;
    }

    public JsTry(JsBlock jsBlock, JsCatch jsCatch, JsBlock jsBlock2) {
        this(jsBlock, new SmartList(), jsBlock2);
        if (jsCatch != null) {
            this.catches.add(jsCatch);
        }
    }

    public List<JsCatch> getCatches() {
        return this.catches;
    }

    public JsBlock getFinallyBlock() {
        return this.finallyBlock;
    }

    public JsBlock getTryBlock() {
        return this.tryBlock;
    }

    public void setFinallyBlock(JsBlock jsBlock) {
        this.finallyBlock = jsBlock;
    }

    public void setTryBlock(JsBlock jsBlock) {
        this.tryBlock = jsBlock;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor jsVisitor) {
        jsVisitor.visitTry(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.accept(this.tryBlock);
        jsVisitor.acceptWithInsertRemove(this.catches);
        JsBlock jsBlock = this.finallyBlock;
        if (jsBlock != null) {
            jsVisitor.accept(jsBlock);
        }
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext) {
        if (jsVisitorWithContext.visit(this, jsContext)) {
            this.tryBlock = (JsBlock) jsVisitorWithContext.acceptStatement(this.tryBlock);
            jsVisitorWithContext.acceptList(this.catches);
            JsBlock jsBlock = this.finallyBlock;
            if (jsBlock != null) {
                this.finallyBlock = (JsBlock) jsVisitorWithContext.acceptStatement(jsBlock);
            }
        }
        jsVisitorWithContext.endVisit(this, jsContext);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsTry deepCopy() {
        JsTry jsTry = (JsTry) new JsTry((JsBlock) AstUtil.deepCopy(this.tryBlock), (List<JsCatch>) AstUtil.deepCopy(this.catches), (JsBlock) AstUtil.deepCopy(this.finallyBlock)).withMetadataFrom(this);
        if (jsTry == null) {
            $$$reportNull$$$0(0);
        }
        return jsTry;
    }
}
