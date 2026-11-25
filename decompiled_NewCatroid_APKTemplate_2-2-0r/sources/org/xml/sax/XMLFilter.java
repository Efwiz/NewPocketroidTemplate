package org.xml.sax;

/* JADX INFO: loaded from: classes3.dex */
public interface XMLFilter extends XMLReader {
    XMLReader getParent();

    void setParent(XMLReader xMLReader);
}
