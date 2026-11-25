package org.apache.xerces.xni;

/* JADX INFO: loaded from: classes13.dex */
public interface XMLLocator {
    String getBaseSystemId();

    int getCharacterOffset();

    int getColumnNumber();

    String getEncoding();

    String getExpandedSystemId();

    int getLineNumber();

    String getLiteralSystemId();

    String getPublicId();

    String getXMLVersion();
}
