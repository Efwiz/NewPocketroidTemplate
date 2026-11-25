package org.xmlunit.input;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.xmlunit.util.Convert;
import org.xmlunit.util.Nodes;

/* JADX INFO: loaded from: classes3.dex */
public class WhitespaceNormalizedSource extends DOMSource {
    public WhitespaceNormalizedSource(Source source) {
        super(Nodes.normalizeWhitespace(Convert.toDocument(source)));
        setSystemId(source.getSystemId());
    }

    public WhitespaceNormalizedSource(Source source, DocumentBuilderFactory documentBuilderFactory) {
        super(Nodes.normalizeWhitespace(Convert.toDocument(source, documentBuilderFactory)));
        setSystemId(source.getSystemId());
    }
}
