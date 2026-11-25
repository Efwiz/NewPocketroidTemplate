package org.apache.html.dom;

import org.w3c.dom.html.HTMLDivElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLDivElementImpl extends HTMLElementImpl implements HTMLDivElement {
    private static final long serialVersionUID = 2327098984177358833L;

    public HTMLDivElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLDivElement
    public String getAlign() {
        return capitalize(getAttribute("align"));
    }

    @Override // org.w3c.dom.html.HTMLDivElement
    public void setAlign(String str) {
        setAttribute("align", str);
    }
}
