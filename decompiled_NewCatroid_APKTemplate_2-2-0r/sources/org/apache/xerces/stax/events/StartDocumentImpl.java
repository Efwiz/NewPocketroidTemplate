package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartDocument;
import org.catrobat.catroid.BuildConfig;

/* JADX INFO: loaded from: classes13.dex */
public final class StartDocumentImpl extends XMLEventImpl implements StartDocument {
    private final String fCharEncoding;
    private final boolean fEncodingSet;
    private final boolean fIsStandalone;
    private final boolean fStandaloneSet;
    private final String fVersion;

    public StartDocumentImpl(String str, boolean z, boolean z2, boolean z3, String str2, Location location) {
        super(7, location);
        this.fCharEncoding = str;
        this.fEncodingSet = z;
        this.fIsStandalone = z2;
        this.fStandaloneSet = z3;
        this.fVersion = str2;
    }

    @Override // javax.xml.stream.events.StartDocument
    public boolean encodingSet() {
        return this.fEncodingSet;
    }

    @Override // javax.xml.stream.events.StartDocument
    public String getCharacterEncodingScheme() {
        return this.fCharEncoding;
    }

    @Override // javax.xml.stream.events.StartDocument
    public String getSystemId() {
        return getLocation().getSystemId();
    }

    @Override // javax.xml.stream.events.StartDocument
    public String getVersion() {
        return this.fVersion;
    }

    @Override // javax.xml.stream.events.StartDocument
    public boolean isStandalone() {
        return this.fIsStandalone;
    }

    @Override // javax.xml.stream.events.StartDocument
    public boolean standaloneSet() {
        return this.fStandaloneSet;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write("<?xml version=\"");
            String str = this.fVersion;
            writer.write((str == null || str.length() <= 0) ? BuildConfig.VERSION_NAME : this.fVersion);
            writer.write(34);
            if (encodingSet()) {
                writer.write(" encoding=\"");
                writer.write(this.fCharEncoding);
                writer.write(34);
            }
            if (standaloneSet()) {
                writer.write(" standalone=\"");
                writer.write(this.fIsStandalone ? "yes" : "no");
                writer.write(34);
            }
            writer.write("?>");
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
