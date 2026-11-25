package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;

/* JADX INFO: loaded from: classes5.dex */
public interface JsNode {
    void accept(JsVisitor jsVisitor);

    void acceptChildren(JsVisitor jsVisitor);

    JsNode deepCopy();

    List<JsComment> getCommentsAfterNode();

    List<JsComment> getCommentsBeforeNode();

    Object getSource();

    void setCommentsAfterNode(List<JsComment> list);

    void setCommentsBeforeNode(List<JsComment> list);

    void setSource(Object obj);

    JsNode source(Object obj);

    void traverse(JsVisitorWithContext jsVisitorWithContext, JsContext jsContext);
}
