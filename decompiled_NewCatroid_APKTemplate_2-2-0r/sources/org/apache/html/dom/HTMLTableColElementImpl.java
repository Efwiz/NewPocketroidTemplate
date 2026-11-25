package org.apache.html.dom;

import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.w3c.dom.html.HTMLTableColElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLTableColElementImpl extends HTMLElementImpl implements HTMLTableColElement {
    private static final long serialVersionUID = -6189626162811911792L;

    public HTMLTableColElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public String getAlign() {
        return capitalize(getAttribute("align"));
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public String getCh() {
        String attribute = getAttribute(PsiKeyword.CHAR);
        return (attribute == null || attribute.length() <= 1) ? attribute : attribute.substring(0, 1);
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public String getChOff() {
        return getAttribute("charoff");
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public int getSpan() {
        return getInteger(getAttribute("span"));
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public String getVAlign() {
        return capitalize(getAttribute("valign"));
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public String getWidth() {
        return getAttribute("width");
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public void setAlign(String str) {
        setAttribute("align", str);
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public void setCh(String str) {
        if (str != null && str.length() > 1) {
            str = str.substring(0, 1);
        }
        setAttribute(PsiKeyword.CHAR, str);
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public void setChOff(String str) {
        setAttribute("charoff", str);
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public void setSpan(int i) {
        setAttribute("span", String.valueOf(i));
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public void setVAlign(String str) {
        setAttribute("valign", str);
    }

    @Override // org.w3c.dom.html.HTMLTableColElement
    public void setWidth(String str) {
        setAttribute("width", str);
    }
}
