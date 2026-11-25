package org.jetbrains.kotlin.org.codehaus.stax2.typed;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/* JADX INFO: loaded from: classes4.dex */
public interface TypedXMLStreamReader extends XMLStreamReader {
    boolean getAttributeAsBoolean(int i) throws XMLStreamException;

    int getAttributeAsInt(int i) throws XMLStreamException;
}
