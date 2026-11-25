package org.apache.html.dom;

import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.html.HTMLStyleElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLStyleElementImpl extends HTMLElementImpl implements HTMLStyleElement {
    private static final long serialVersionUID = -9001815754196124532L;

    public HTMLStyleElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLStyleElement
    public boolean getDisabled() {
        return getBinary(CompilerOptions.DISABLED);
    }

    @Override // org.w3c.dom.html.HTMLStyleElement
    public String getMedia() {
        return getAttribute("media");
    }

    @Override // org.w3c.dom.html.HTMLStyleElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.w3c.dom.html.HTMLStyleElement
    public void setDisabled(boolean z) {
        setAttribute(CompilerOptions.DISABLED, z);
    }

    @Override // org.w3c.dom.html.HTMLStyleElement
    public void setMedia(String str) {
        setAttribute("media", str);
    }

    @Override // org.w3c.dom.html.HTMLStyleElement
    public void setType(String str) {
        setAttribute(ModuleXmlParser.TYPE, str);
    }
}
