package org.apache.html.dom;

import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLScriptElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLScriptElementImpl extends HTMLElementImpl implements HTMLScriptElement {
    private static final long serialVersionUID = 5090330049085326558L;

    public HTMLScriptElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public String getCharset() {
        return getAttribute("charset");
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public boolean getDefer() {
        return getBinary("defer");
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public String getEvent() {
        return getAttribute("event");
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public String getHtmlFor() {
        return getAttribute(PsiKeyword.FOR);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public String getSrc() {
        return getAttribute("src");
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public String getText() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Node firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof Text) {
                stringBuffer.append(((Text) firstChild).getData());
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setCharset(String str) {
        setAttribute("charset", str);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setDefer(boolean z) {
        setAttribute("defer", z);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setEvent(String str) {
        setAttribute("event", str);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setHtmlFor(String str) {
        setAttribute(PsiKeyword.FOR, str);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setSrc(String str) {
        setAttribute("src", str);
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setText(String str) {
        Node firstChild = getFirstChild();
        while (firstChild != null) {
            Node nextSibling = firstChild.getNextSibling();
            removeChild(firstChild);
            firstChild = nextSibling;
        }
        insertBefore(getOwnerDocument().createTextNode(str), getFirstChild());
    }

    @Override // org.w3c.dom.html.HTMLScriptElement
    public void setType(String str) {
        setAttribute(ModuleXmlParser.TYPE, str);
    }
}
