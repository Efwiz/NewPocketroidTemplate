package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

/* JADX INFO: loaded from: classes13.dex */
public class AttributeImpl extends XMLEventImpl implements Attribute {
    private final String fDtdType;
    private final boolean fIsSpecified;
    private final QName fName;
    private final String fValue;

    protected AttributeImpl(int i, QName qName, String str, String str2, boolean z, Location location) {
        super(i, location);
        this.fName = qName;
        this.fValue = str;
        this.fDtdType = str2;
        this.fIsSpecified = z;
    }

    public AttributeImpl(QName qName, String str, String str2, boolean z, Location location) {
        this(10, qName, str, str2, z, location);
    }

    @Override // javax.xml.stream.events.Attribute
    public final String getDTDType() {
        return this.fDtdType;
    }

    @Override // javax.xml.stream.events.Attribute
    public final QName getName() {
        return this.fName;
    }

    @Override // javax.xml.stream.events.Attribute
    public final String getValue() {
        return this.fValue;
    }

    @Override // javax.xml.stream.events.Attribute
    public final boolean isSpecified() {
        return this.fIsSpecified;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public final void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            String prefix = this.fName.getPrefix();
            if (prefix != null && prefix.length() > 0) {
                writer.write(prefix);
                writer.write(58);
            }
            writer.write(this.fName.getLocalPart());
            writer.write("=\"");
            writer.write(this.fValue);
            writer.write(34);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
