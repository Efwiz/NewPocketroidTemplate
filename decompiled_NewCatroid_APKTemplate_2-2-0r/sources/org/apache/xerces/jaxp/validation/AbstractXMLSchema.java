package org.apache.xerces.jaxp.validation;

import java.util.HashMap;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

/* JADX INFO: loaded from: classes13.dex */
abstract class AbstractXMLSchema extends Schema implements XSGrammarPoolContainer {
    private final HashMap fFeatures = new HashMap();

    @Override // org.apache.xerces.jaxp.validation.XSGrammarPoolContainer
    public final Boolean getFeature(String str) {
        return (Boolean) this.fFeatures.get(str);
    }

    @Override // javax.xml.validation.Schema
    public final Validator newValidator() {
        return new ValidatorImpl(this);
    }

    @Override // javax.xml.validation.Schema
    public final ValidatorHandler newValidatorHandler() {
        return new ValidatorHandlerImpl(this);
    }

    final void setFeature(String str, boolean z) {
        this.fFeatures.put(str, z ? Boolean.TRUE : Boolean.FALSE);
    }
}
