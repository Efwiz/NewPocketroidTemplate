package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EntityDeclaration;

/* JADX INFO: loaded from: classes13.dex */
public final class EntityDeclarationImpl extends XMLEventImpl implements EntityDeclaration {
    private final String fName;
    private final String fNotationName;
    private final String fPublicId;
    private final String fSystemId;

    public EntityDeclarationImpl(String str, String str2, String str3, String str4, Location location) {
        super(15, location);
        this.fPublicId = str;
        this.fSystemId = str2;
        this.fName = str3;
        this.fNotationName = str4;
    }

    @Override // javax.xml.stream.events.EntityDeclaration
    public String getBaseURI() {
        return null;
    }

    @Override // javax.xml.stream.events.EntityDeclaration
    public String getName() {
        return this.fName;
    }

    @Override // javax.xml.stream.events.EntityDeclaration
    public String getNotationName() {
        return this.fNotationName;
    }

    @Override // javax.xml.stream.events.EntityDeclaration
    public String getPublicId() {
        return this.fPublicId;
    }

    @Override // javax.xml.stream.events.EntityDeclaration
    public String getReplacementText() {
        return null;
    }

    @Override // javax.xml.stream.events.EntityDeclaration
    public String getSystemId() {
        return this.fSystemId;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write("<!ENTITY ");
            writer.write(this.fName);
            if (this.fPublicId != null) {
                writer.write(" PUBLIC \"");
                writer.write(this.fPublicId);
                writer.write("\" \"");
                writer.write(this.fSystemId);
            } else {
                writer.write(" SYSTEM \"");
                writer.write(this.fSystemId);
            }
            writer.write(34);
            if (this.fNotationName != null) {
                writer.write(" NDATA ");
                writer.write(this.fNotationName);
            }
            writer.write(62);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
