package org.apache.wml.dom;

import org.apache.wml.WMLElement;
import org.apache.xerces.dom.ElementImpl;

/* JADX INFO: loaded from: classes13.dex */
public class WMLElementImpl extends ElementImpl implements WMLElement {
    private static final long serialVersionUID = 3440984702956371604L;

    public WMLElementImpl(WMLDocumentImpl wMLDocumentImpl, String str) {
        super(wMLDocumentImpl, str);
    }

    int getAttribute(String str, int i) {
        String attribute = getAttribute("emptyok");
        return attribute != null ? Integer.parseInt(attribute) : i;
    }

    boolean getAttribute(String str, boolean z) {
        String attribute = getAttribute("emptyok");
        if (attribute == null || !attribute.equals("true")) {
            return z;
        }
        return true;
    }

    public String getClassName() {
        return getAttribute("class");
    }

    public String getId() {
        return getAttribute("id");
    }

    public String getXmlLang() {
        return getAttribute("xml:lang");
    }

    void setAttribute(String str, int i) {
        setAttribute(str, i + "");
    }

    void setAttribute(String str, boolean z) {
        setAttribute(str, z ? "true" : "false");
    }

    public void setClassName(String str) {
        setAttribute("class", str);
    }

    public void setId(String str) {
        setAttribute("id", str);
    }

    public void setXmlLang(String str) {
        setAttribute("xml:lang", str);
    }
}
