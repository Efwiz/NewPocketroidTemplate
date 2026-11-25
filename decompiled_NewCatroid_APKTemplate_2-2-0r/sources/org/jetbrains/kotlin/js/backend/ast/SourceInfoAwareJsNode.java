package org.jetbrains.kotlin.js.backend.ast;

import java.util.List;
import java.util.Map;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;

/* JADX INFO: loaded from: classes5.dex */
public abstract class SourceInfoAwareJsNode extends AbstractNode {
    private Object source;

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor jsVisitor) {
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ void copyMetadataFrom(HasMetadata hasMetadata) {
        super.copyMetadataFrom(hasMetadata);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ List getCommentsAfterNode() {
        return super.getCommentsAfterNode();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ List getCommentsBeforeNode() {
        return super.getCommentsBeforeNode();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ Object getData(String str) {
        return super.getData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ Map getRawMetadata() {
        return super.getRawMetadata();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ boolean hasData(String str) {
        return super.hasData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ void removeData(String str) {
        super.removeData(str);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ void setCommentsAfterNode(List list) {
        super.setCommentsAfterNode(list);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public /* bridge */ /* synthetic */ void setCommentsBeforeNode(List list) {
        super.setCommentsBeforeNode(list);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode, org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata
    public /* bridge */ /* synthetic */ void setData(String str, Object obj) {
        super.setData(str, obj);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.AbstractNode
    public /* bridge */ /* synthetic */ HasMetadata withMetadataFrom(HasMetadata hasMetadata) {
        return super.withMetadataFrom(hasMetadata);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public Object getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void setSource(Object obj) {
        this.source = obj;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsNode source(Object obj) {
        this.source = obj;
        return this;
    }
}
