package org.apache.html.dom;

import org.w3c.dom.html.HTMLBodyElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLBodyElementImpl extends HTMLElementImpl implements HTMLBodyElement {
    private static final long serialVersionUID = 9058852459426595202L;

    public HTMLBodyElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public String getALink() {
        return getAttribute("alink");
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public String getBackground() {
        return getAttribute("background");
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public String getBgColor() {
        return getAttribute("bgcolor");
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public String getLink() {
        return getAttribute("link");
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public String getText() {
        return getAttribute("text");
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public String getVLink() {
        return getAttribute("vlink");
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public void setALink(String str) {
        setAttribute("alink", str);
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public void setBackground(String str) {
        setAttribute("background", str);
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public void setBgColor(String str) {
        setAttribute("bgcolor", str);
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public void setLink(String str) {
        setAttribute("link", str);
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public void setText(String str) {
        setAttribute("text", str);
    }

    @Override // org.w3c.dom.html.HTMLBodyElement
    public void setVLink(String str) {
        setAttribute("vlink", str);
    }
}
