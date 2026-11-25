package org.xmlunit.input;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.xmlunit.util.Convert;
import org.xmlunit.util.Nodes;

/* JADX INFO: loaded from: classes3.dex */
public class ElementContentWhitespaceStrippedSource extends DOMSource {
    public ElementContentWhitespaceStrippedSource(Source source) {
        super(Nodes.stripElementContentWhitespace(Convert.toDocument(source)));
        setSystemId(source.getSystemId());
    }

    public ElementContentWhitespaceStrippedSource(Source source, DocumentBuilderFactory documentBuilderFactory) {
        super(Nodes.stripElementContentWhitespace(Convert.toDocument(source, documentBuilderFactory)));
        setSystemId(source.getSystemId());
    }
}
