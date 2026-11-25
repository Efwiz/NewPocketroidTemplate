package org.apache.wml.dom;

import org.apache.wml.WMLOneventElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: loaded from: classes13.dex */
public class WMLOneventElementImpl extends WMLElementImpl implements WMLOneventElement {
    private static final long serialVersionUID = -4279215241146570871L;

    public WMLOneventElementImpl(WMLDocumentImpl wMLDocumentImpl, String str) {
        super(wMLDocumentImpl, str);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public String getClassName() {
        return getAttribute("class");
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public String getId() {
        return getAttribute("id");
    }

    @Override // org.apache.wml.WMLOneventElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public void setClassName(String str) {
        setAttribute("class", str);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public void setId(String str) {
        setAttribute("id", str);
    }

    @Override // org.apache.wml.WMLOneventElement
    public void setType(String str) {
        setAttribute(ModuleXmlParser.TYPE, str);
    }
}
