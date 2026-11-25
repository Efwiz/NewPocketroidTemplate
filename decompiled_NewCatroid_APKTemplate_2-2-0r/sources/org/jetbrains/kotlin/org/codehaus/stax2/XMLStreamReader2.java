package org.jetbrains.kotlin.org.codehaus.stax2;

import javax.xml.stream.XMLStreamException;
import org.jetbrains.kotlin.org.codehaus.stax2.typed.TypedXMLStreamReader;

/* JADX INFO: loaded from: classes4.dex */
public interface XMLStreamReader2 extends TypedXMLStreamReader {
    void closeCompletely() throws XMLStreamException;

    LocationInfo getLocationInfo();

    boolean isEmptyElement() throws XMLStreamException;

    void skipElement() throws XMLStreamException;
}
