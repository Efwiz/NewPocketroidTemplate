package org.apache.html.dom;

import org.eclipse.jgit.transport.AmazonS3;
import org.w3c.dom.html.HTMLLegendElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLLegendElementImpl extends HTMLElementImpl implements HTMLLegendElement {
    private static final long serialVersionUID = -621849164029630762L;

    public HTMLLegendElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLLegendElement
    public String getAccessKey() {
        String attribute = getAttribute(AmazonS3.Keys.ACCESS_KEY);
        return (attribute == null || attribute.length() <= 1) ? attribute : attribute.substring(0, 1);
    }

    @Override // org.w3c.dom.html.HTMLLegendElement
    public String getAlign() {
        return getAttribute("align");
    }

    @Override // org.w3c.dom.html.HTMLLegendElement
    public void setAccessKey(String str) {
        if (str != null && str.length() > 1) {
            str = str.substring(0, 1);
        }
        setAttribute(AmazonS3.Keys.ACCESS_KEY, str);
    }

    @Override // org.w3c.dom.html.HTMLLegendElement
    public void setAlign(String str) {
        setAttribute("align", str);
    }
}
