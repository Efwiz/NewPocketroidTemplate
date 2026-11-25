package org.apache.wml.dom;

import org.apache.wml.WMLAccessElement;
import org.eclipse.jgit.transport.AmazonS3;

/* JADX INFO: loaded from: classes13.dex */
public class WMLAccessElementImpl extends WMLElementImpl implements WMLAccessElement {
    private static final long serialVersionUID = -235627181817012806L;

    public WMLAccessElementImpl(WMLDocumentImpl wMLDocumentImpl, String str) {
        super(wMLDocumentImpl, str);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public String getClassName() {
        return getAttribute("class");
    }

    @Override // org.apache.wml.WMLAccessElement
    public String getDomain() {
        return getAttribute(AmazonS3.Keys.DOMAIN);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public String getId() {
        return getAttribute("id");
    }

    @Override // org.apache.wml.WMLAccessElement
    public String getPath() {
        return getAttribute("path");
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public void setClassName(String str) {
        setAttribute("class", str);
    }

    @Override // org.apache.wml.WMLAccessElement
    public void setDomain(String str) {
        setAttribute(AmazonS3.Keys.DOMAIN, str);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public void setId(String str) {
        setAttribute("id", str);
    }

    @Override // org.apache.wml.WMLAccessElement
    public void setPath(String str) {
        setAttribute("path", str);
    }
}
