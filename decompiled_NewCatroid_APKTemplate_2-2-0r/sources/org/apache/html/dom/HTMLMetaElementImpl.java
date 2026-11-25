package org.apache.html.dom;

import com.google.firebase.analytics.FirebaseAnalytics;
import org.w3c.dom.html.HTMLMetaElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLMetaElementImpl extends HTMLElementImpl implements HTMLMetaElement {
    private static final long serialVersionUID = -2401961905874264272L;

    public HTMLMetaElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public String getContent() {
        return getAttribute(FirebaseAnalytics.Param.CONTENT);
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public String getHttpEquiv() {
        return getAttribute("http-equiv");
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public String getName() {
        return getAttribute("name");
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public String getScheme() {
        return getAttribute("scheme");
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public void setContent(String str) {
        setAttribute(FirebaseAnalytics.Param.CONTENT, str);
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public void setHttpEquiv(String str) {
        setAttribute("http-equiv", str);
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public void setName(String str) {
        setAttribute("name", str);
    }

    @Override // org.w3c.dom.html.HTMLMetaElement
    public void setScheme(String str) {
        setAttribute("scheme", str);
    }
}
