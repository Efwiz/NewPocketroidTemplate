package org.apache.html.dom;

import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.w3c.dom.html.HTMLAppletElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLAppletElementImpl extends HTMLElementImpl implements HTMLAppletElement {
    private static final long serialVersionUID = 8375794094117740967L;

    public HTMLAppletElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getAlign() {
        return getAttribute("align");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getAlt() {
        return getAttribute("alt");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getArchive() {
        return getAttribute("archive");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getCode() {
        return getAttribute(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getCodeBase() {
        return getAttribute("codebase");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getHeight() {
        return getAttribute("height");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getHspace() {
        return getAttribute("height");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getName() {
        return getAttribute("name");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getObject() {
        return getAttribute("object");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getVspace() {
        return getAttribute("vspace");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public String getWidth() {
        return getAttribute("width");
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setAlign(String str) {
        setAttribute("align", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setAlt(String str) {
        setAttribute("alt", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setArchive(String str) {
        setAttribute("archive", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setCode(String str) {
        setAttribute(ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setCodeBase(String str) {
        setAttribute("codebase", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setHeight(String str) {
        setAttribute("height", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setHspace(String str) {
        setAttribute("height", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setName(String str) {
        setAttribute("name", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setObject(String str) {
        setAttribute("object", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setVspace(String str) {
        setAttribute("vspace", str);
    }

    @Override // org.w3c.dom.html.HTMLAppletElement
    public void setWidth(String str) {
        setAttribute("width", str);
    }
}
