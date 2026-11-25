package org.xml.sax.helpers;

import java.util.Vector;
import org.xml.sax.AttributeList;

/* JADX INFO: loaded from: classes3.dex */
public class AttributeListImpl implements AttributeList {
    Vector names = new Vector();
    Vector types = new Vector();
    Vector values = new Vector();

    public AttributeListImpl() {
    }

    public AttributeListImpl(AttributeList attributeList) {
        setAttributeList(attributeList);
    }

    public void addAttribute(String str, String str2, String str3) {
        this.names.addElement(str);
        this.types.addElement(str2);
        this.values.addElement(str3);
    }

    public void clear() {
        this.names.removeAllElements();
        this.types.removeAllElements();
        this.values.removeAllElements();
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public int getLength() {
        return this.names.size();
    }

    @Override // org.xml.sax.AttributeList
    public String getName(int i) {
        if (i < 0) {
            return null;
        }
        try {
            return (String) this.names.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getType(int i) {
        if (i < 0) {
            return null;
        }
        try {
            return (String) this.types.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getType(String str) {
        return getType(this.names.indexOf(str));
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getValue(int i) {
        if (i < 0) {
            return null;
        }
        try {
            return (String) this.values.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException unused) {
            return null;
        }
    }

    @Override // org.xml.sax.AttributeList, org.xml.sax.Attributes
    public String getValue(String str) {
        return getValue(this.names.indexOf(str));
    }

    public void removeAttribute(String str) {
        int iIndexOf = this.names.indexOf(str);
        if (iIndexOf >= 0) {
            this.names.removeElementAt(iIndexOf);
            this.types.removeElementAt(iIndexOf);
            this.values.removeElementAt(iIndexOf);
        }
    }

    public void setAttributeList(AttributeList attributeList) {
        int length = attributeList.getLength();
        clear();
        for (int i = 0; i < length; i++) {
            addAttribute(attributeList.getName(i), attributeList.getType(i), attributeList.getValue(i));
        }
    }
}
