package org.apache.html.dom;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.html.HTMLOListElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLOListElementImpl extends HTMLElementImpl implements HTMLOListElement {
    private static final long serialVersionUID = 1293750546025862146L;

    public HTMLOListElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLOListElement
    public boolean getCompact() {
        return getBinary("compact");
    }

    @Override // org.w3c.dom.html.HTMLOListElement
    public int getStart() {
        return getInteger(getAttribute("start"));
    }

    @Override // org.w3c.dom.html.HTMLOListElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.w3c.dom.html.HTMLOListElement
    public void setCompact(boolean z) {
        setAttribute("compact", z);
    }

    @Override // org.w3c.dom.html.HTMLOListElement
    public void setStart(int i) {
        setAttribute("start", String.valueOf(i));
    }

    @Override // org.w3c.dom.html.HTMLOListElement
    public void setType(String str) {
        setAttribute(ModuleXmlParser.TYPE, str);
    }
}
