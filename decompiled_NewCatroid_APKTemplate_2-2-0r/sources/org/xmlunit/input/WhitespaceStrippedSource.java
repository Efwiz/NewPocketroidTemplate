package org.xmlunit.input;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.xmlunit.util.Convert;
import org.xmlunit.util.Nodes;

/* JADX INFO: loaded from: classes3.dex */
public class WhitespaceStrippedSource extends DOMSource {
    public WhitespaceStrippedSource(Source source) {
        super(Nodes.stripWhitespace(Convert.toDocument(source)));
        setSystemId(source.getSystemId());
    }

    public WhitespaceStrippedSource(Source source, DocumentBuilderFactory documentBuilderFactory) {
        super(Nodes.stripWhitespace(Convert.toDocument(source, documentBuilderFactory)));
        setSystemId(source.getSystemId());
    }
}
