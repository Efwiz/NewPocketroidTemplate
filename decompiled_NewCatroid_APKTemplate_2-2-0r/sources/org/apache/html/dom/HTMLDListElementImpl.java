package org.apache.html.dom;

import org.w3c.dom.html.HTMLDListElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLDListElementImpl extends HTMLElementImpl implements HTMLDListElement {
    private static final long serialVersionUID = -2130005642453038604L;

    public HTMLDListElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLDListElement
    public boolean getCompact() {
        return getBinary("compact");
    }

    @Override // org.w3c.dom.html.HTMLDListElement
    public void setCompact(boolean z) {
        setAttribute("compact", z);
    }
}
