package org.apache.html.dom;

import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.html.HTMLButtonElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLButtonElementImpl extends HTMLElementImpl implements HTMLButtonElement, HTMLFormControl {
    private static final long serialVersionUID = -753685852948076730L;

    public HTMLButtonElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public String getAccessKey() {
        String attribute = getAttribute(AmazonS3.Keys.ACCESS_KEY);
        return (attribute == null || attribute.length() <= 1) ? attribute : attribute.substring(0, 1);
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public boolean getDisabled() {
        return getBinary(CompilerOptions.DISABLED);
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public String getName() {
        return getAttribute("name");
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public int getTabIndex() {
        try {
            return Integer.parseInt(getAttribute("tabindex"));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public String getType() {
        return capitalize(getAttribute(ModuleXmlParser.TYPE));
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public String getValue() {
        return getAttribute("value");
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public void setAccessKey(String str) {
        if (str != null && str.length() > 1) {
            str = str.substring(0, 1);
        }
        setAttribute(AmazonS3.Keys.ACCESS_KEY, str);
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public void setDisabled(boolean z) {
        setAttribute(CompilerOptions.DISABLED, z);
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public void setName(String str) {
        setAttribute("name", str);
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public void setTabIndex(int i) {
        setAttribute("tabindex", String.valueOf(i));
    }

    @Override // org.w3c.dom.html.HTMLButtonElement
    public void setValue(String str) {
        setAttribute("value", str);
    }
}
