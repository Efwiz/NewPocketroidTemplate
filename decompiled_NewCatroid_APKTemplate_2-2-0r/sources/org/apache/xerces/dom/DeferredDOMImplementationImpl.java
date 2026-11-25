package org.apache.xerces.dom;

import org.w3c.dom.DOMImplementation;

/* JADX INFO: loaded from: classes13.dex */
public class DeferredDOMImplementationImpl extends DOMImplementationImpl {
    static final DeferredDOMImplementationImpl singleton = new DeferredDOMImplementationImpl();

    public static DOMImplementation getDOMImplementation() {
        return singleton;
    }
}
