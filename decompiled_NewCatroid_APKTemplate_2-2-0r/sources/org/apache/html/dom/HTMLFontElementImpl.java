package org.apache.html.dom;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.mlkit.common.sdkinternal.OptionalModuleUtils;
import org.w3c.dom.html.HTMLFontElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLFontElementImpl extends HTMLElementImpl implements HTMLFontElement {
    private static final long serialVersionUID = -415914342045846318L;

    public HTMLFontElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLFontElement
    public String getColor() {
        return capitalize(getAttribute(TypedValues.Custom.S_COLOR));
    }

    @Override // org.w3c.dom.html.HTMLFontElement
    public String getFace() {
        return capitalize(getAttribute(OptionalModuleUtils.FACE));
    }

    @Override // org.w3c.dom.html.HTMLFontElement
    public String getSize() {
        return getAttribute("size");
    }

    @Override // org.w3c.dom.html.HTMLFontElement
    public void setColor(String str) {
        setAttribute(TypedValues.Custom.S_COLOR, str);
    }

    @Override // org.w3c.dom.html.HTMLFontElement
    public void setFace(String str) {
        setAttribute(OptionalModuleUtils.FACE, str);
    }

    @Override // org.w3c.dom.html.HTMLFontElement
    public void setSize(String str) {
        setAttribute("size", str);
    }
}
