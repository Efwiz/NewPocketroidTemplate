package org.apache.wml.dom;

import org.apache.wml.WMLDoElement;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;

/* JADX INFO: loaded from: classes13.dex */
public class WMLDoElementImpl extends WMLElementImpl implements WMLDoElement {
    private static final long serialVersionUID = 7755861458464251322L;

    public WMLDoElementImpl(WMLDocumentImpl wMLDocumentImpl, String str) {
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

    @Override // org.apache.wml.WMLDoElement
    public String getLabel() {
        return getAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME);
    }

    @Override // org.apache.wml.WMLDoElement
    public String getName() {
        return getAttribute("name");
    }

    @Override // org.apache.wml.WMLDoElement
    public String getOptional() {
        return getAttribute(SchemaSymbols.ATTVAL_OPTIONAL);
    }

    @Override // org.apache.wml.WMLDoElement
    public String getType() {
        return getAttribute(ModuleXmlParser.TYPE);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLAElement
    public String getXmlLang() {
        return getAttribute("xml:lang");
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public void setClassName(String str) {
        setAttribute("class", str);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLElement
    public void setId(String str) {
        setAttribute("id", str);
    }

    @Override // org.apache.wml.WMLDoElement
    public void setLabel(String str) {
        setAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, str);
    }

    @Override // org.apache.wml.WMLDoElement
    public void setName(String str) {
        setAttribute("name", str);
    }

    @Override // org.apache.wml.WMLDoElement
    public void setOptional(String str) {
        setAttribute(SchemaSymbols.ATTVAL_OPTIONAL, str);
    }

    @Override // org.apache.wml.WMLDoElement
    public void setType(String str) {
        setAttribute(ModuleXmlParser.TYPE, str);
    }

    @Override // org.apache.wml.dom.WMLElementImpl, org.apache.wml.WMLAElement
    public void setXmlLang(String str) {
        setAttribute("xml:lang", str);
    }
}
