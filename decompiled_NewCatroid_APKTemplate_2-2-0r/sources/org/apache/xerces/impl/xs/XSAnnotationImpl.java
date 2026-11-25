package org.apache.xerces.impl.xs;

import java.io.IOException;
import java.io.StringReader;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.parsers.SAXParser;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSNamespaceItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/* JADX INFO: loaded from: classes13.dex */
public class XSAnnotationImpl implements XSAnnotation {
    private String fData;
    private SchemaGrammar fGrammar;

    public XSAnnotationImpl(String str, SchemaGrammar schemaGrammar) {
        this.fData = str;
        this.fGrammar = schemaGrammar;
    }

    private synchronized void writeToDOM(Node node, short s) {
        Node nodeImportNode;
        try {
            Document ownerDocument = s == 1 ? node.getOwnerDocument() : (Document) node;
            DOMParser dOMParser = this.fGrammar.getDOMParser();
            try {
                dOMParser.parse(new InputSource(new StringReader(this.fData)));
            } catch (IOException | SAXException unused) {
            }
            Document document = dOMParser.getDocument();
            dOMParser.dropDocumentReferences();
            Element documentElement = document.getDocumentElement();
            if (!(ownerDocument instanceof CoreDocumentImpl) || (nodeImportNode = ownerDocument.adoptNode(documentElement)) == null) {
                nodeImportNode = ownerDocument.importNode(documentElement, true);
            }
            node.insertBefore(nodeImportNode, node.getFirstChild());
        } catch (Throwable th) {
            throw th;
        }
    }

    private synchronized void writeToSAX(ContentHandler contentHandler) {
        SAXParser sAXParser = this.fGrammar.getSAXParser();
        InputSource inputSource = new InputSource(new StringReader(this.fData));
        sAXParser.setContentHandler(contentHandler);
        try {
            sAXParser.parse(inputSource);
        } catch (IOException | SAXException unused) {
        }
        sAXParser.setContentHandler(null);
    }

    @Override // org.apache.xerces.xs.XSAnnotation
    public String getAnnotationString() {
        return this.fData;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getName() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getNamespace() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public XSNamespaceItem getNamespaceItem() {
        return null;
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 12;
    }

    @Override // org.apache.xerces.xs.XSAnnotation
    public boolean writeAnnotation(Object obj, short s) {
        if (s == 1 || s == 3) {
            writeToDOM((Node) obj, s);
            return true;
        }
        if (s != 2) {
            return false;
        }
        writeToSAX((ContentHandler) obj);
        return true;
    }
}
