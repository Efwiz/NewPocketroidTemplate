package org.apache.xerces.xni.parser;

import java.io.IOException;
import org.apache.xerces.xni.XNIException;

/* JADX INFO: loaded from: classes13.dex */
public interface XMLDocumentScanner extends XMLDocumentSource {
    boolean scanDocument(boolean z) throws IOException, XNIException;

    void setInputSource(XMLInputSource xMLInputSource) throws IOException;
}
