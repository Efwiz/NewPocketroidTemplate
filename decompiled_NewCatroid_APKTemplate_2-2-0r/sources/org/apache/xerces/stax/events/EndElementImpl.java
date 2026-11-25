package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;

/* JADX INFO: loaded from: classes13.dex */
public final class EndElementImpl extends ElementImpl implements EndElement {
    public EndElementImpl(QName qName, Iterator it, Location location) {
        super(qName, false, it, location);
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write("</");
            QName name2 = getName();
            String prefix = name2.getPrefix();
            if (prefix != null && prefix.length() > 0) {
                writer.write(prefix);
                writer.write(58);
            }
            writer.write(name2.getLocalPart());
            writer.write(62);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
