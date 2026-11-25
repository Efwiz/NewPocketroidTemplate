package org.apache.xerces.dom;

import org.apache.xerces.dom3.as.ASModel;
import org.apache.xerces.dom3.as.DOMASBuilder;
import org.apache.xerces.dom3.as.DOMASWriter;
import org.apache.xerces.dom3.as.DOMImplementationAS;
import org.apache.xerces.parsers.DOMASBuilderImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;

/* JADX INFO: loaded from: classes13.dex */
public class ASDOMImplementationImpl extends DOMImplementationImpl implements DOMImplementationAS {
    static final ASDOMImplementationImpl singleton = new ASDOMImplementationImpl();

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }

    @Override // org.apache.xerces.dom3.as.DOMImplementationAS
    public ASModel createAS(boolean z) {
        return new ASModelImpl(z);
    }

    @Override // org.apache.xerces.dom3.as.DOMImplementationAS
    public DOMASBuilder createDOMASBuilder() {
        return new DOMASBuilderImpl();
    }

    @Override // org.apache.xerces.dom3.as.DOMImplementationAS
    public DOMASWriter createDOMASWriter() {
        throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
    }
}
