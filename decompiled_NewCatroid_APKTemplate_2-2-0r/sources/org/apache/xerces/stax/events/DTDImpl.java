package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.DTD;

/* JADX INFO: loaded from: classes13.dex */
public final class DTDImpl extends XMLEventImpl implements DTD {
    private final String fDTD;

    public DTDImpl(String str, Location location) {
        super(11, location);
        this.fDTD = str == null ? null : str;
    }

    @Override // javax.xml.stream.events.DTD
    public String getDocumentTypeDeclaration() {
        return this.fDTD;
    }

    @Override // javax.xml.stream.events.DTD
    public List getEntities() {
        return Collections.EMPTY_LIST;
    }

    @Override // javax.xml.stream.events.DTD
    public List getNotations() {
        return Collections.EMPTY_LIST;
    }

    @Override // javax.xml.stream.events.DTD
    public Object getProcessedDTD() {
        return null;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write(this.fDTD);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
