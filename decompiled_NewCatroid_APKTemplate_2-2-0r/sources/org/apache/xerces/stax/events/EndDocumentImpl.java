package org.apache.xerces.stax.events;

import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndDocument;

/* JADX INFO: loaded from: classes13.dex */
public final class EndDocumentImpl extends XMLEventImpl implements EndDocument {
    public EndDocumentImpl(Location location) {
        super(8, location);
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
    }
}
