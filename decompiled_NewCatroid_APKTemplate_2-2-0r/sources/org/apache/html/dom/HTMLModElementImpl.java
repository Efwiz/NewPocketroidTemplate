package org.apache.html.dom;

import org.w3c.dom.html.HTMLModElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLModElementImpl extends HTMLElementImpl implements HTMLModElement {
    private static final long serialVersionUID = 6424581972706750120L;

    public HTMLModElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLModElement
    public String getCite() {
        return getAttribute("cite");
    }

    @Override // org.w3c.dom.html.HTMLModElement
    public String getDateTime() {
        return getAttribute("datetime");
    }

    @Override // org.w3c.dom.html.HTMLModElement
    public void setCite(String str) {
        setAttribute("cite", str);
    }

    @Override // org.w3c.dom.html.HTMLModElement
    public void setDateTime(String str) {
        setAttribute("datetime", str);
    }
}
