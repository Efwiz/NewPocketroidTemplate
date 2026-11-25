package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.NotationDeclaration;

/* JADX INFO: loaded from: classes13.dex */
public final class NotationDeclarationImpl extends XMLEventImpl implements NotationDeclaration {
    private final String fName;
    private final String fPublicId;
    private final String fSystemId;

    public NotationDeclarationImpl(String str, String str2, String str3, Location location) {
        super(14, location);
        this.fName = str;
        this.fPublicId = str2;
        this.fSystemId = str3;
    }

    @Override // javax.xml.stream.events.NotationDeclaration
    public String getName() {
        return this.fName;
    }

    @Override // javax.xml.stream.events.NotationDeclaration
    public String getPublicId() {
        return this.fPublicId;
    }

    @Override // javax.xml.stream.events.NotationDeclaration
    public String getSystemId() {
        return this.fSystemId;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write("<!NOTATION ");
            if (this.fPublicId != null) {
                writer.write("PUBLIC \"");
                writer.write(this.fPublicId);
                writer.write(34);
                if (this.fSystemId != null) {
                    writer.write(" \"");
                    writer.write(this.fSystemId);
                }
                writer.write(this.fName);
                writer.write(62);
            }
            writer.write("SYSTEM \"");
            writer.write(this.fSystemId);
            writer.write(34);
            writer.write(this.fName);
            writer.write(62);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
