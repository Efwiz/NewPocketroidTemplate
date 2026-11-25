package org.apache.xerces.util;

import org.apache.xerces.xni.XMLLocator;
import org.xml.sax.ext.Locator2;

/* JADX INFO: loaded from: classes13.dex */
public class LocatorProxy implements Locator2 {
    private final XMLLocator fLocator;

    public LocatorProxy(XMLLocator xMLLocator) {
        this.fLocator = xMLLocator;
    }

    @Override // org.xml.sax.Locator
    public int getColumnNumber() {
        return this.fLocator.getColumnNumber();
    }

    @Override // org.xml.sax.ext.Locator2
    public String getEncoding() {
        return this.fLocator.getEncoding();
    }

    @Override // org.xml.sax.Locator
    public int getLineNumber() {
        return this.fLocator.getLineNumber();
    }

    @Override // org.xml.sax.Locator
    public String getPublicId() {
        return this.fLocator.getPublicId();
    }

    @Override // org.xml.sax.Locator
    public String getSystemId() {
        return this.fLocator.getExpandedSystemId();
    }

    @Override // org.xml.sax.ext.Locator2
    public String getXMLVersion() {
        return this.fLocator.getXMLVersion();
    }
}
