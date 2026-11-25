package org.apache.xerces.impl.xs.util;

import org.apache.xerces.xni.XMLLocator;

/* JADX INFO: loaded from: classes13.dex */
public final class SimpleLocator implements XMLLocator {
    private int charOffset;
    private int column;
    private String esid;
    private int line;
    private String lsid;

    public SimpleLocator() {
    }

    public SimpleLocator(String str, String str2, int i, int i2) {
        this(str, str2, i, i2, -1);
    }

    public SimpleLocator(String str, String str2, int i, int i2, int i3) {
        this.line = i;
        this.column = i2;
        this.lsid = str;
        this.esid = str2;
        this.charOffset = i3;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getBaseSystemId() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public int getCharacterOffset() {
        return this.charOffset;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public int getColumnNumber() {
        return this.column;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getEncoding() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getExpandedSystemId() {
        return this.esid;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public int getLineNumber() {
        return this.line;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getLiteralSystemId() {
        return this.lsid;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getPublicId() {
        return null;
    }

    @Override // org.apache.xerces.xni.XMLLocator
    public String getXMLVersion() {
        return null;
    }

    public void setBaseSystemId(String str) {
    }

    public void setCharacterOffset(int i) {
        this.charOffset = i;
    }

    public void setColumnNumber(int i) {
        this.column = i;
    }

    public void setExpandedSystemId(String str) {
        this.esid = str;
    }

    public void setLineNumber(int i) {
        this.line = i;
    }

    public void setLiteralSystemId(String str) {
        this.lsid = str;
    }

    public void setPublicId(String str) {
    }

    public void setValues(String str, String str2, int i, int i2) {
        setValues(str, str2, i, i2, -1);
    }

    public void setValues(String str, String str2, int i, int i2, int i3) {
        this.line = i;
        this.column = i2;
        this.lsid = str;
        this.esid = str2;
        this.charOffset = i3;
    }
}
