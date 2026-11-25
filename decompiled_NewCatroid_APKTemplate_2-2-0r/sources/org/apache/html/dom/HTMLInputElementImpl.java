package org.apache.html.dom;

import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.transport.AmazonS3;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.html.HTMLInputElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLInputElementImpl extends HTMLElementImpl implements HTMLInputElement, HTMLFormControl {
    private static final long serialVersionUID = 640139325394332007L;

    public HTMLInputElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void blur() {
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void click() {
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void focus() {
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getAccept() {
        return getAttribute("accept");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getAccessKey() {
        String attribute = getAttribute(AmazonS3.Keys.ACCESS_KEY);
        return (attribute == null || attribute.length() <= 1) ? attribute : attribute.substring(0, 1);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getAlign() {
        return capitalize(getAttribute("align"));
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getAlt() {
        return getAttribute("alt");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public boolean getChecked() {
        return getBinary("checked");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public boolean getDefaultChecked() {
        return getBinary("defaultChecked");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getDefaultValue() {
        return getAttribute("defaultValue");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public boolean getDisabled() {
        return getBinary(CompilerOptions.DISABLED);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public int getMaxLength() {
        return getInteger(getAttribute("maxlength"));
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getName() {
        return getAttribute("name");
    }

    @Override // org.apache.xerces.dom.NodeImpl, org.w3c.dom.html.HTMLInputElement
    public boolean getReadOnly() {
        return getBinary("readonly");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getSize() {
        return getAttribute("size");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getSrc() {
        return getAttribute("src");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public int getTabIndex() {
        try {
            return Integer.parseInt(getAttribute("tabindex"));
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getUseMap() {
        return getAttribute("useMap");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public String getValue() {
        return getAttribute("value");
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void select() {
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setAccept(String str) {
        setAttribute("accept", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setAccessKey(String str) {
        if (str != null && str.length() > 1) {
            str = str.substring(0, 1);
        }
        setAttribute(AmazonS3.Keys.ACCESS_KEY, str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setAlign(String str) {
        setAttribute("align", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setAlt(String str) {
        setAttribute("alt", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setChecked(boolean z) {
        setAttribute("checked", z);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setDefaultChecked(boolean z) {
        setAttribute("defaultChecked", z);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setDefaultValue(String str) {
        setAttribute("defaultValue", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setDisabled(boolean z) {
        setAttribute(CompilerOptions.DISABLED, z);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setMaxLength(int i) {
        setAttribute("maxlength", String.valueOf(i));
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setName(String str) {
        setAttribute("name", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setReadOnly(boolean z) {
        setAttribute("readonly", z);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setSize(String str) {
        setAttribute("size", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setSrc(String str) {
        setAttribute("src", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setTabIndex(int i) {
        setAttribute("tabindex", String.valueOf(i));
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setUseMap(String str) {
        setAttribute("useMap", str);
    }

    @Override // org.w3c.dom.html.HTMLInputElement
    public void setValue(String str) {
        setAttribute("value", str);
    }
}
