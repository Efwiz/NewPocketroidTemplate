package org.apache.xerces.impl.dtd;

import org.apache.xerces.xni.QName;

/* JADX INFO: loaded from: classes13.dex */
public class XMLAttributeDecl {
    public boolean optional;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public final QName f473name = new QName();
    public final XMLSimpleType simpleType = new XMLSimpleType();

    public void clear() {
        this.f473name.clear();
        this.simpleType.clear();
        this.optional = false;
    }

    public void setValues(QName qName, XMLSimpleType xMLSimpleType, boolean z) {
        this.f473name.setValues(qName);
        this.simpleType.setValues(xMLSimpleType);
        this.optional = z;
    }
}
