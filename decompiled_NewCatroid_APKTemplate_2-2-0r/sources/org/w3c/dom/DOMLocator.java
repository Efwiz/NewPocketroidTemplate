package org.w3c.dom;

/* JADX INFO: loaded from: classes7.dex */
public interface DOMLocator {
    int getByteOffset();

    int getColumnNumber();

    int getLineNumber();

    Node getRelatedNode();

    String getUri();

    int getUtf16Offset();
}
