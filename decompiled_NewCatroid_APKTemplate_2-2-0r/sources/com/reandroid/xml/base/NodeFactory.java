package com.reandroid.xml.base;

import com.reandroid.common.Namespace;

/* JADX INFO: loaded from: classes11.dex */
public interface NodeFactory {
    Attribute newAttribute();

    Comment newComment();

    Element<?> newElement();

    Namespace newNamespace(String str, String str2);

    Text newText();
}
