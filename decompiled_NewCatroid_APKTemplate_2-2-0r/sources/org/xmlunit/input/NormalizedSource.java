package org.xmlunit.input;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlunit.util.Convert;

/* JADX INFO: loaded from: classes3.dex */
public class NormalizedSource extends DOMSource {
    public NormalizedSource() {
    }

    public NormalizedSource(Source source) {
        if (source != null) {
            Document document = Convert.toDocument(source);
            document.normalizeDocument();
            super.setNode(document);
            setSystemId(source.getSystemId());
        }
    }

    public NormalizedSource(Document document) {
        this(document, (String) null);
    }

    public NormalizedSource(Document document, String str) {
        if (document != null) {
            document.normalizeDocument();
        }
        super.setNode(document);
        setSystemId(str);
    }

    public NormalizedSource(Node node) {
        this(node, (String) null);
    }

    public NormalizedSource(Node node, String str) {
        setNormalizedNode(node);
        setSystemId(str);
    }

    @Override // javax.xml.transform.dom.DOMSource
    public void setNode(Node node) {
        setNormalizedNode(node);
    }

    private void setNormalizedNode(Node node) {
        if (node != null) {
            node.normalize();
        }
        super.setNode(node);
    }
}
