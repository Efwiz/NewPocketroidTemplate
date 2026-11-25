package org.apache.xerces.impl.xpath;

/* JADX INFO: loaded from: classes13.dex */
public class XPathException extends Exception {
    static final long serialVersionUID = -948482312169512085L;
    private final String fKey;

    public XPathException() {
        this.fKey = "c-general-xpath";
    }

    public XPathException(String str) {
        this.fKey = str;
    }

    public String getKey() {
        return this.fKey;
    }
}
