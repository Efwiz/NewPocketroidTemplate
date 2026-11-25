package org.xml.sax;

import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public interface EntityResolver {
    InputSource resolveEntity(String str, String str2) throws SAXException, IOException;
}
