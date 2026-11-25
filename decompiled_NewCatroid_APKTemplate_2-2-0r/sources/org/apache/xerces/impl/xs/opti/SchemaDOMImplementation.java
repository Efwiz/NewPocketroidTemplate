package org.apache.xerces.impl.xs.opti;

import org.catrobat.catroid.BuildConfig;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

/* JADX INFO: loaded from: classes13.dex */
final class SchemaDOMImplementation implements DOMImplementation {
    private static final SchemaDOMImplementation singleton = new SchemaDOMImplementation();

    private SchemaDOMImplementation() {
    }

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    @Override // org.w3c.dom.DOMImplementation
    public Document createDocument(String str, String str2, DocumentType documentType) throws DOMException {
        throw new DOMException((short) 9, "Method not supported");
    }

    @Override // org.w3c.dom.DOMImplementation
    public DocumentType createDocumentType(String str, String str2, String str3) throws DOMException {
        throw new DOMException((short) 9, "Method not supported");
    }

    @Override // org.w3c.dom.DOMImplementation
    public Object getFeature(String str, String str2) {
        SchemaDOMImplementation schemaDOMImplementation = singleton;
        if (schemaDOMImplementation.hasFeature(str, str2)) {
            return schemaDOMImplementation;
        }
        return null;
    }

    @Override // org.w3c.dom.DOMImplementation
    public boolean hasFeature(String str, String str2) {
        boolean z = str2 == null || str2.length() == 0;
        if (str.equalsIgnoreCase("Core") || str.equalsIgnoreCase("XML")) {
            return z || str2.equals(BuildConfig.VERSION_NAME) || str2.equals("2.0") || str2.equals("3.0");
        }
        return false;
    }
}
