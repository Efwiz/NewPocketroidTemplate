package org.apache.html.dom;

import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLOptionElement;
import org.w3c.dom.html.HTMLSelectElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLOptionElementImpl extends HTMLElementImpl implements HTMLOptionElement {
    private static final long serialVersionUID = -4486774554137530907L;

    public HTMLOptionElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public boolean getDefaultSelected() {
        return getBinary("default-selected");
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public boolean getDisabled() {
        return getBinary(CompilerOptions.DISABLED);
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public int getIndex() {
        Node parentNode = getParentNode();
        while (parentNode != null && !(parentNode instanceof HTMLSelectElement)) {
            parentNode = parentNode.getParentNode();
        }
        if (parentNode == null) {
            return -1;
        }
        NodeList elementsByTagName = ((HTMLElement) parentNode).getElementsByTagName("OPTION");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            if (elementsByTagName.item(i) == this) {
                return i;
            }
        }
        return -1;
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public String getLabel() {
        return capitalize(getAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME));
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public boolean getSelected() {
        return getBinary("selected");
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public String getText() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Node firstChild = getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild instanceof Text) {
                stringBuffer.append(((Text) firstChild).getData());
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public String getValue() {
        return getAttribute("value");
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public void setDefaultSelected(boolean z) {
        setAttribute("default-selected", z);
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public void setDisabled(boolean z) {
        setAttribute(CompilerOptions.DISABLED, z);
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public void setIndex(int i) {
        Node parentNode = getParentNode();
        while (parentNode != null && !(parentNode instanceof HTMLSelectElement)) {
            parentNode = parentNode.getParentNode();
        }
        if (parentNode != null) {
            NodeList elementsByTagName = ((HTMLElement) parentNode).getElementsByTagName("OPTION");
            if (elementsByTagName.item(i) != this) {
                getParentNode().removeChild(this);
                Node nodeItem = elementsByTagName.item(i);
                nodeItem.getParentNode().insertBefore(this, nodeItem);
            }
        }
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public void setLabel(String str) {
        setAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, str);
    }

    public void setSelected(boolean z) {
        setAttribute("selected", z);
    }

    public void setText(String str) {
        Node firstChild = getFirstChild();
        while (firstChild != null) {
            Node nextSibling = firstChild.getNextSibling();
            removeChild(firstChild);
            firstChild = nextSibling;
        }
        insertBefore(getOwnerDocument().createTextNode(str), getFirstChild());
    }

    @Override // org.w3c.dom.html.HTMLOptionElement
    public void setValue(String str) {
        setAttribute("value", str);
    }
}
