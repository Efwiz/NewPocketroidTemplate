package org.apache.xerces.jaxp.validation;

import java.lang.ref.WeakReference;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

/* JADX INFO: loaded from: classes13.dex */
final class WeakReferenceXMLSchema extends AbstractXMLSchema {
    private WeakReference fGrammarPool = new WeakReference(null);

    @Override // org.apache.xerces.jaxp.validation.XSGrammarPoolContainer
    public synchronized XMLGrammarPool getGrammarPool() {
        XMLGrammarPool softReferenceGrammarPool;
        softReferenceGrammarPool = (XMLGrammarPool) this.fGrammarPool.get();
        if (softReferenceGrammarPool == null) {
            softReferenceGrammarPool = new SoftReferenceGrammarPool();
            this.fGrammarPool = new WeakReference(softReferenceGrammarPool);
        }
        return softReferenceGrammarPool;
    }

    @Override // org.apache.xerces.jaxp.validation.XSGrammarPoolContainer
    public boolean isFullyComposed() {
        return false;
    }
}
