package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import org.apache.xerces.util.XMLChar;

/* JADX INFO: loaded from: classes13.dex */
public final class CharactersImpl extends XMLEventImpl implements Characters {
    private final String fData;

    public CharactersImpl(String str, int i, Location location) {
        super(i, location);
        this.fData = str == null ? "" : str;
    }

    @Override // javax.xml.stream.events.Characters
    public String getData() {
        return this.fData;
    }

    @Override // javax.xml.stream.events.Characters
    public boolean isCData() {
        return 12 == getEventType();
    }

    @Override // javax.xml.stream.events.Characters
    public boolean isIgnorableWhiteSpace() {
        return 6 == getEventType();
    }

    @Override // javax.xml.stream.events.Characters
    public boolean isWhiteSpace() {
        String str = this.fData;
        int length = str != null ? str.length() : 0;
        if (length == 0) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!XMLChar.isSpace(this.fData.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write(this.fData);
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
