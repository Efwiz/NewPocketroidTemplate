package org.apache.xerces.xni.parser;

import java.io.IOException;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;

/* JADX INFO: loaded from: classes13.dex */
public interface XMLEntityResolver {
    XMLInputSource resolveEntity(XMLResourceIdentifier xMLResourceIdentifier) throws IOException, XNIException;
}
