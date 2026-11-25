package com.reandroid.xml.base;

import com.reandroid.common.Namespace;
import com.reandroid.xml.base.Node;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public interface Element<T extends Node> extends NodeTree<T>, NamedNode, NodeFactory {
    void addAttribute(int i, Attribute attribute);

    void addAttribute(Attribute attribute);

    Attribute getAttributeAt(int i);

    int getAttributeCount();

    Iterator<? extends Attribute> getAttributes();

    Namespace getNamespaceAt(int i);

    int getNamespaceCount();

    Iterator<? extends Namespace> getNamespaces();

    Element<T> newElement();
}
