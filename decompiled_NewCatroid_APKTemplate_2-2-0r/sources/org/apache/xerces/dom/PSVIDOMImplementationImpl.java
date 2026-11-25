package org.apache.xerces.dom;

import org.apache.xerces.impl.Constants;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

/* JADX INFO: loaded from: classes13.dex */
public class PSVIDOMImplementationImpl extends DOMImplementationImpl {
    static final PSVIDOMImplementationImpl singleton = new PSVIDOMImplementationImpl();

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    @Override // org.apache.xerces.dom.DOMImplementationImpl, org.apache.xerces.dom.CoreDOMImplementationImpl
    protected CoreDocumentImpl createDocument(DocumentType documentType) {
        return new PSVIDocumentImpl(documentType);
    }

    @Override // org.apache.xerces.dom.DOMImplementationImpl, org.apache.xerces.dom.CoreDOMImplementationImpl, org.w3c.dom.DOMImplementation
    public boolean hasFeature(String str, String str2) {
        return super.hasFeature(str, str2) || str.equalsIgnoreCase(Constants.DOM_PSVI);
    }
}
