package org.apache.html.dom;

import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.w3c.dom.html.HTMLBRElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLBRElementImpl extends HTMLElementImpl implements HTMLBRElement {
    private static final long serialVersionUID = 311960206282154750L;

    public HTMLBRElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLBRElement
    public String getClear() {
        return capitalize(getAttribute(LineReader.CLEAR));
    }

    @Override // org.w3c.dom.html.HTMLBRElement
    public void setClear(String str) {
        setAttribute(LineReader.CLEAR, str);
    }
}
