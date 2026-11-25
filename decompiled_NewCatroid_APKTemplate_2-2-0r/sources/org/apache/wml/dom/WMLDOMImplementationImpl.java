package org.apache.wml.dom;

import org.apache.wml.WMLDOMImplementation;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

/* JADX INFO: loaded from: classes13.dex */
public class WMLDOMImplementationImpl extends DOMImplementationImpl implements WMLDOMImplementation {
    static final DOMImplementationImpl singleton = new WMLDOMImplementationImpl();

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    @Override // org.apache.xerces.dom.DOMImplementationImpl, org.apache.xerces.dom.CoreDOMImplementationImpl
    protected CoreDocumentImpl createDocument(DocumentType documentType) {
        return new WMLDocumentImpl(documentType);
    }
}
