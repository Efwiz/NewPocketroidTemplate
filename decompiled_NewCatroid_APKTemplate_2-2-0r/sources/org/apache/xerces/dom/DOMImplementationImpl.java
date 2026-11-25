package org.apache.xerces.dom;

import org.slf4j.Marker;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

/* JADX INFO: loaded from: classes13.dex */
public class DOMImplementationImpl extends CoreDOMImplementationImpl implements DOMImplementation {
    static final DOMImplementationImpl singleton = new DOMImplementationImpl();

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    @Override // org.apache.xerces.dom.CoreDOMImplementationImpl
    protected CoreDocumentImpl createDocument(DocumentType documentType) {
        return new DocumentImpl(documentType);
    }

    @Override // org.apache.xerces.dom.CoreDOMImplementationImpl, org.w3c.dom.DOMImplementation
    public boolean hasFeature(String str, String str2) {
        boolean zHasFeature = super.hasFeature(str, str2);
        if (zHasFeature) {
            return zHasFeature;
        }
        boolean z = str2 == null || str2.length() == 0;
        if (str.startsWith(Marker.ANY_NON_NULL_MARKER)) {
            str = str.substring(1);
        }
        if ((!str.equalsIgnoreCase("Events") || (!z && !str2.equals("2.0"))) && ((!str.equalsIgnoreCase("MutationEvents") || (!z && !str2.equals("2.0"))) && ((!str.equalsIgnoreCase("Traversal") || (!z && !str2.equals("2.0"))) && (!str.equalsIgnoreCase("Range") || (!z && !str2.equals("2.0")))))) {
            if (!str.equalsIgnoreCase("MutationEvents")) {
                return false;
            }
            if (!z && !str2.equals("2.0")) {
                return false;
            }
        }
        return true;
    }
}
