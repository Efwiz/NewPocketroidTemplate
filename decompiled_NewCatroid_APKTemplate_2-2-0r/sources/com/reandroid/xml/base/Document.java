package com.reandroid.xml.base;

import com.reandroid.xml.base.Element;

/* JADX INFO: loaded from: classes11.dex */
public interface Document<E extends Element<?>> extends Node, NodeFactory {
    E getDocumentElement();

    void setDocumentElement(E e);
}
