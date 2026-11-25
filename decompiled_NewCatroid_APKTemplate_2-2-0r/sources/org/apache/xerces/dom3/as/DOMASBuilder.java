package org.apache.xerces.dom3.as;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

/* JADX INFO: loaded from: classes13.dex */
public interface DOMASBuilder extends LSParser {
    ASModel getAbstractSchema();

    ASModel parseASInputSource(LSInput lSInput) throws Exception;

    ASModel parseASURI(String str) throws Exception;

    void setAbstractSchema(ASModel aSModel);
}
