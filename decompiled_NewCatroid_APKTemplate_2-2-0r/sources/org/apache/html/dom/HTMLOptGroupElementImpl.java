package org.apache.html.dom;

import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.w3c.dom.html.HTMLOptGroupElement;

/* JADX INFO: loaded from: classes13.dex */
public class HTMLOptGroupElementImpl extends HTMLElementImpl implements HTMLOptGroupElement {
    private static final long serialVersionUID = -8807098641226171501L;

    public HTMLOptGroupElementImpl(HTMLDocumentImpl hTMLDocumentImpl, String str) {
        super(hTMLDocumentImpl, str);
    }

    @Override // org.w3c.dom.html.HTMLOptGroupElement
    public boolean getDisabled() {
        return getBinary(CompilerOptions.DISABLED);
    }

    @Override // org.w3c.dom.html.HTMLOptGroupElement
    public String getLabel() {
        return capitalize(getAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME));
    }

    @Override // org.w3c.dom.html.HTMLOptGroupElement
    public void setDisabled(boolean z) {
        setAttribute(CompilerOptions.DISABLED, z);
    }

    @Override // org.w3c.dom.html.HTMLOptGroupElement
    public void setLabel(String str) {
        setAttribute(CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME, str);
    }
}
