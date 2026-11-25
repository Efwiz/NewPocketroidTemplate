package org.apache.xerces.xs;

import org.w3c.dom.ls.LSInput;

/* JADX INFO: loaded from: classes13.dex */
public interface XSImplementation {
    LSInputList createLSInputList(LSInput[] lSInputArr);

    StringList createStringList(String[] strArr);

    XSLoader createXSLoader(StringList stringList) throws XSException;

    StringList getRecognizedVersions();
}
