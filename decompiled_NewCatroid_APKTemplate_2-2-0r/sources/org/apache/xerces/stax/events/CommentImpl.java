package org.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Comment;

/* JADX INFO: loaded from: classes13.dex */
public final class CommentImpl extends XMLEventImpl implements Comment {
    private final String fText;

    public CommentImpl(String str, Location location) {
        super(5, location);
        this.fText = str == null ? "" : str;
    }

    @Override // javax.xml.stream.events.Comment
    public String getText() {
        return this.fText;
    }

    @Override // javax.xml.stream.events.XMLEvent
    public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
        try {
            writer.write("<!--");
            writer.write(this.fText);
            writer.write("-->");
        } catch (IOException e) {
            throw new XMLStreamException(e);
        }
    }
}
