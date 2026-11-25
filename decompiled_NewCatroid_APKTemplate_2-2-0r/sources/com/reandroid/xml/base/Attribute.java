package com.reandroid.xml.base;

import com.reandroid.common.Namespace;

/* JADX INFO: loaded from: classes11.dex */
public interface Attribute extends Node, NamedNode {
    @Override // com.reandroid.xml.base.NamedNode
    Namespace getNamespace();

    Element<?> getParentNode();

    String getValueAsString();

    @Override // com.reandroid.xml.base.NamedNode
    void setNamespace(Namespace namespace);

    default Object getAttributeValue() {
        return getValueAsString();
    }
}
