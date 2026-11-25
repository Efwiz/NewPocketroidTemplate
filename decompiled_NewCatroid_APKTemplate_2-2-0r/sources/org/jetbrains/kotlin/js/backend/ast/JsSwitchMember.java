package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.util.SmartList;

/* JADX INFO: loaded from: classes5.dex */
public abstract class JsSwitchMember extends SourceInfoAwareJsNode {
    protected final List<JsStatement> statements = new SmartList();

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public abstract JsSwitchMember deepCopy();

    protected JsSwitchMember() {
    }

    public List<JsStatement> getStatements() {
        return this.statements;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
        jsVisitor.acceptWithInsertRemove(this.statements);
    }
}
