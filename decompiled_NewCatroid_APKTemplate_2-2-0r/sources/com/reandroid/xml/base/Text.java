package com.reandroid.xml.base;

/* JADX INFO: loaded from: classes11.dex */
public interface Text extends Node {
    @Override // com.reandroid.xml.base.Node, com.reandroid.xml.base.Attribute
    NodeTree<?> getParentNode();

    String getText();

    void setText(String str);
}
