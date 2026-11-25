package org.apache.html.dom;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.mlkit.common.sdkinternal.OptionalModuleUtils;
import org.w3c.dom.html.HTMLBaseFontElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLBaseFontElementImpl extends HTMLElementImpl implements HTMLBaseFontElement {
    private static final long serialVersionUID = -3650249921091097229L;

    public HTMLBaseFontElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLBaseFontElement
    public String getColor() {
        return capitalize(getAttribute(TypedValues.Custom.S_COLOR));
    }

    @Override // org.w3c.dom.html.HTMLBaseFontElement
    public String getFace() {
        return capitalize(getAttribute(OptionalModuleUtils.FACE));
    }

    @Override // org.w3c.dom.html.HTMLBaseFontElement
    public String getSize() {
        return getAttribute("size");
    }

    @Override // org.w3c.dom.html.HTMLBaseFontElement
    public void setColor(String str) {
        setAttribute(TypedValues.Custom.S_COLOR, str);
    }

    @Override // org.w3c.dom.html.HTMLBaseFontElement
    public void setFace(String str) {
        setAttribute(OptionalModuleUtils.FACE, str);
    }

    @Override // org.w3c.dom.html.HTMLBaseFontElement
    public void setSize(String str) {
        setAttribute("size", str);
    }
}
