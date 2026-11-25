package com.reandroid.xml.base;

import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public interface Node extends XmlSerializable, XmlReader {
    int getColumnNumber();

    int getLineNumber();

    Node getParentNode();

    void setColumnNumber(int i);

    void setLineNumber(int i);

    default String toXmlString() {
        return toXmlString(true);
    }

    default String toXmlString(boolean z) {
        try {
            return XmlSerializable.toXmlString(this, z);
        } catch (IOException unused) {
            return null;
        }
    }
}
