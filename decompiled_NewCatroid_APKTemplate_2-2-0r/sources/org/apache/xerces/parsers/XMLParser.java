package org.apache.xerces.parsers;

import java.io.IOException;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xerces.xni.parser.XMLParserConfiguration;

/* JADX INFO: loaded from: classes13.dex */
public abstract class XMLParser {
    protected static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
    protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
    private static final String[] RECOGNIZED_PROPERTIES = {"http://apache.org/xml/properties/internal/entity-resolver", ERROR_HANDLER};
    protected final XMLParserConfiguration fConfiguration;

    protected XMLParser(XMLParserConfiguration xMLParserConfiguration) {
        this.fConfiguration = xMLParserConfiguration;
        xMLParserConfiguration.addRecognizedProperties(RECOGNIZED_PROPERTIES);
    }

    public void parse(XMLInputSource xMLInputSource) throws IOException, XNIException {
        reset();
        this.fConfiguration.parse(xMLInputSource);
    }

    protected void reset() throws XNIException {
    }
}
