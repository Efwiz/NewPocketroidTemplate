package org.apache.xerces.util;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.xni.XMLAttributes;
import org.xml.sax.AttributeList;
import org.xml.sax.ext.Attributes2;

/* JADX INFO: loaded from: classes13.dex */
public final class AttributesProxy implements AttributeList, Attributes2 {
    private XMLAttributes fAttributes;

    public AttributesProxy(XMLAttributes xMLAttributes) {
        this.fAttributes = xMLAttributes;
    }

    public XMLAttributes getAttributes() {
        return this.fAttributes;
    }

    @Override // org.xml.sax.Attributes
    public int getIndex(String str) {
        return this.fAttributes.getIndex(str);
    }

    @Override // org.xml.sax.Attributes
    public int getIndex(String str, String str2) {
        return str.equals(XMLSymbols.EMPTY_STRING) ? this.fAttributes.getIndex(null, str2) : this.fAttributes.getIndex(str, str2);
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public int getLength() {
        return this.fAttributes.getLength();
    }

    @Override // org.xml.sax.Attributes
    public String getLocalName(int i) {
        return this.fAttributes.getLocalName(i);
    }

    @Override // org.xml.sax.AttributeList
    public String getName(int i) {
        return this.fAttributes.getQName(i);
    }

    @Override // org.xml.sax.Attributes
    public String getQName(int i) {
        return this.fAttributes.getQName(i);
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getType(int i) {
        return this.fAttributes.getType(i);
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getType(String str) {
        return this.fAttributes.getType(str);
    }

    @Override // org.xml.sax.Attributes
    public String getType(String str, String str2) {
        return str.equals(XMLSymbols.EMPTY_STRING) ? this.fAttributes.getType(null, str2) : this.fAttributes.getType(str, str2);
    }

    @Override // org.xml.sax.Attributes
    public String getURI(int i) {
        String uri = this.fAttributes.getURI(i);
        return uri != null ? uri : XMLSymbols.EMPTY_STRING;
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getValue(int i) {
        return this.fAttributes.getValue(i);
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getValue(String str) {
        return this.fAttributes.getValue(str);
    }

    @Override // org.xml.sax.Attributes
    public String getValue(String str, String str2) {
        return str.equals(XMLSymbols.EMPTY_STRING) ? this.fAttributes.getValue(null, str2) : this.fAttributes.getValue(str, str2);
    }

    @Override // org.xml.sax.ext.Attributes2
    public boolean isDeclared(int i) {
        if (i < 0 || i >= this.fAttributes.getLength()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return Boolean.TRUE.equals(this.fAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_DECLARED));
    }

    @Override // org.xml.sax.ext.Attributes2
    public boolean isDeclared(String str) {
        int index = getIndex(str);
        if (index != -1) {
            return Boolean.TRUE.equals(this.fAttributes.getAugmentations(index).getItem(Constants.ATTRIBUTE_DECLARED));
        }
        throw new IllegalArgumentException(str);
    }

    @Override // org.xml.sax.ext.Attributes2
    public boolean isDeclared(String str, String str2) {
        int index = getIndex(str, str2);
        if (index != -1) {
            return Boolean.TRUE.equals(this.fAttributes.getAugmentations(index).getItem(Constants.ATTRIBUTE_DECLARED));
        }
        throw new IllegalArgumentException(str2);
    }

    @Override // org.xml.sax.ext.Attributes2
    public boolean isSpecified(int i) {
        if (i < 0 || i >= this.fAttributes.getLength()) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.fAttributes.isSpecified(i);
    }

    @Override // org.xml.sax.ext.Attributes2
    public boolean isSpecified(String str) {
        int index = getIndex(str);
        if (index != -1) {
            return this.fAttributes.isSpecified(index);
        }
        throw new IllegalArgumentException(str);
    }

    @Override // org.xml.sax.ext.Attributes2
    public boolean isSpecified(String str, String str2) {
        int index = getIndex(str, str2);
        if (index != -1) {
            return this.fAttributes.isSpecified(index);
        }
        throw new IllegalArgumentException(str2);
    }

    public void setAttributes(XMLAttributes xMLAttributes) {
        this.fAttributes = xMLAttributes;
    }
}
