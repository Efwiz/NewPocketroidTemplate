package org.apache.xerces.stax.events;

import java.io.StringWriter;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.apache.xerces.stax.EmptyLocation;
import org.apache.xerces.stax.ImmutableLocation;

/* JADX INFO: loaded from: classes13.dex */
abstract class XMLEventImpl implements XMLEvent {
    private int fEventType;
    private Location fLocation;

    XMLEventImpl(int i, Location location) {
        this.fEventType = i;
        this.fLocation = location != null ? new ImmutableLocation(location) : EmptyLocation.getInstance();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.xml.stream.events.XMLEvent
    public final Characters asCharacters() {
        return (Characters) this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.xml.stream.events.XMLEvent
    public final EndElement asEndElement() {
        return (EndElement) this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.xml.stream.events.XMLEvent
    public final StartElement asStartElement() {
        return (StartElement) this;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final int getEventType() {
        return this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final Location getLocation() {
        return this.fLocation;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final QName getSchemaType() {
        return null;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isAttribute() {
        return 10 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isCharacters() {
        int i = this.fEventType;
        return 4 == i || 12 == i || 6 == i;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isEndDocument() {
        return 8 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isEndElement() {
        return 2 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isEntityReference() {
        return 9 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isNamespace() {
        return 13 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isProcessingInstruction() {
        return 3 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isStartDocument() {
        return 7 == this.fEventType;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final boolean isStartElement() {
        return 1 == this.fEventType;
    }

    public final String toString() {
        StringWriter stringWriter = new StringWriter();
        try {
            writeAsEncodedUnicode(stringWriter);
        } catch (XMLStreamException unused) {
        }
        return stringWriter.toString();
    }
}
