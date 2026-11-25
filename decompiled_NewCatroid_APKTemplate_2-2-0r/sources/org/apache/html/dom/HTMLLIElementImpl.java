package org.apache.html.dom;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.html.HTMLLIElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLLIElementImpl extends HTMLElementImpl implements HTMLLIElement {
    private static final long serialVersionUID = -8987309345926701831L;

    public HTMLLIElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLLIElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.w3c.dom.html.HTMLLIElement
    public int getValue() {
        return getInteger(getAttribute("value"));
    }

    @Override // org.w3c.dom.html.HTMLLIElement
    public void setType(String str) {
        setAttribute(ModuleXmlParser.TYPE, str);
    }

    @Override // org.w3c.dom.html.HTMLLIElement
    public void setValue(int i) {
        setAttribute("value", String.valueOf(i));
    }
}
