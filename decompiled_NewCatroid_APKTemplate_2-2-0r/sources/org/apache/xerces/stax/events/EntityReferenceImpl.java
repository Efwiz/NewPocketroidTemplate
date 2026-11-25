package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;

/* JADX INFO: loaded from: classes13.dex */
public final class EntityReferenceImpl extends XMLEventImpl implements EntityReference {
    private final EntityDeclaration fDecl;
    private final String fName;

    public EntityReferenceImpl(String str, EntityDeclaration entityDeclaration, Location location) {
        super(9, location);
        this.fName = str == null ? "" : str;
        this.fDecl = entityDeclaration;
    }

    public EntityReferenceImpl(EntityDeclaration entityDeclaration, Location location) {
        this(entityDeclaration != null ? entityDeclaration.getName() : "", entityDeclaration, location);
    }

    @Override // javax.xml.stream.events.EntityReference
    public EntityDeclaration getDeclaration() {
        return this.fDecl;
    }

    @Override // javax.xml.stream.events.EntityReference
    public String getName() {
        return this.fName;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write(38);
            writer.write(this.fName);
            writer.write(59);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
