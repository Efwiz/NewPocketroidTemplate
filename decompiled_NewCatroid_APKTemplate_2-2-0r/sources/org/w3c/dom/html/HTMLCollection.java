package org.w3c.dom.html;

import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public interface HTMLCollection {
    int getLength();

    Node item(int i);

    Node namedItem(String str);
}
