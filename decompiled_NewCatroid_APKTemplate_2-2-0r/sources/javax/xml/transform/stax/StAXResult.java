package javax.xml.transform.stax;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;

/* JADX INFO: loaded from: classes11.dex */
public class StAXResult implements Result {
    public static final String FEATURE = "http://javax.xml.transform.stax.StAXResult/feature";
    private final XMLEventWriter xmlEventWriter;
    private final XMLStreamWriter xmlStreamWriter;

    public StAXResult(XMLEventWriter xMLEventWriter) {
        if (xMLEventWriter == null) {
            throw new IllegalArgumentException("XMLEventWriter cannot be null.");
        }
        this.xmlStreamWriter = null;
        this.xmlEventWriter = xMLEventWriter;
    }

    public StAXResult(XMLStreamWriter xMLStreamWriter) {
        if (xMLStreamWriter == null) {
            throw new IllegalArgumentException("XMLStreamWriter cannot be null.");
        }
        this.xmlStreamWriter = xMLStreamWriter;
        this.xmlEventWriter = null;
    }

    @Override // javax.xml.transform.Result
    public String getSystemId() {
        return null;
    }

    public XMLEventWriter getXMLEventWriter() {
        return this.xmlEventWriter;
    }

    public XMLStreamWriter getXMLStreamWriter() {
        return this.xmlStreamWriter;
    }

    @Override // javax.xml.transform.Result
    public void setSystemId(String str) {
        throw new UnsupportedOperationException("Setting systemId is not supported.");
    }
}
