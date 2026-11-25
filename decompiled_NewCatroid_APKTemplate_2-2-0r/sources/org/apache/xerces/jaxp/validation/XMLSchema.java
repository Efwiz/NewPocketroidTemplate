package org.apache.xerces.jaxp.validation;

import org.apache.xerces.xni.grammars.XMLGrammarPool;

/* JADX INFO: loaded from: classes13.dex */
final class XMLSchema extends AbstractXMLSchema {
    private final boolean fFullyComposed;
    private final XMLGrammarPool fGrammarPool;

    public XMLSchema(XMLGrammarPool xMLGrammarPool) {
        this(xMLGrammarPool, true);
    }

    public XMLSchema(XMLGrammarPool xMLGrammarPool, boolean z) {
        this.fGrammarPool = xMLGrammarPool;
        this.fFullyComposed = z;
    }

    @Override // org.apache.xerces.jaxp.validation.XSGrammarPoolContainer
    public XMLGrammarPool getGrammarPool() {
        return this.fGrammarPool;
    }

    @Override // org.apache.xerces.jaxp.validation.XSGrammarPoolContainer
    public boolean isFullyComposed() {
        return this.fFullyComposed;
    }
}
