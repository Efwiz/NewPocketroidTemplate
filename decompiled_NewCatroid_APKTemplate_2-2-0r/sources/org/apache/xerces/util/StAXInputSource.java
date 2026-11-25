package org.apache.xerces.util;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.xerces.xni.parser.XMLInputSource;

/* JADX INFO: loaded from: classes13.dex */
public final class StAXInputSource extends XMLInputSource {
    private final boolean fConsumeRemainingContent;
    private final XMLEventReader fEventReader;
    private final XMLStreamReader fStreamReader;

    public StAXInputSource(XMLEventReader xMLEventReader) {
        this(xMLEventReader, false);
    }

    public StAXInputSource(XMLEventReader xMLEventReader, boolean z) {
        super(null, getEventReaderSystemId(xMLEventReader), null);
        if (xMLEventReader == null) {
            throw new IllegalArgumentException("XMLEventReader parameter cannot be null.");
        }
        this.fStreamReader = null;
        this.fEventReader = xMLEventReader;
        this.fConsumeRemainingContent = z;
    }

    public StAXInputSource(XMLStreamReader xMLStreamReader) {
        this(xMLStreamReader, false);
    }

    public StAXInputSource(XMLStreamReader xMLStreamReader, boolean z) {
        super(null, getStreamReaderSystemId(xMLStreamReader), null);
        if (xMLStreamReader == null) {
            throw new IllegalArgumentException("XMLStreamReader parameter cannot be null.");
        }
        this.fStreamReader = xMLStreamReader;
        this.fEventReader = null;
        this.fConsumeRemainingContent = z;
    }

    private static String getEventReaderSystemId(XMLEventReader xMLEventReader) {
        if (xMLEventReader == null) {
            return null;
        }
        try {
            return xMLEventReader.peek().getLocation().getSystemId();
        } catch (XMLStreamException unused) {
            return null;
        }
    }

    private static String getStreamReaderSystemId(XMLStreamReader xMLStreamReader) {
        if (xMLStreamReader != null) {
            return xMLStreamReader.getLocation().getSystemId();
        }
        return null;
    }

    public XMLEventReader getXMLEventReader() {
        return this.fEventReader;
    }

    public XMLStreamReader getXMLStreamReader() {
        return this.fStreamReader;
    }

    @Override // org.apache.xerces.xni.parser.XMLInputSource
    public void setSystemId(String str) {
        throw new UnsupportedOperationException("Cannot set the system ID on a StAXInputSource");
    }

    public boolean shouldConsumeRemainingContent() {
        return this.fConsumeRemainingContent;
    }
}
