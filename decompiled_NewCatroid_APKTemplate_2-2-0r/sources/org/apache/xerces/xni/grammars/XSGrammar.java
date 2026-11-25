package org.apache.xerces.xni.grammars;

import org.apache.xerces.xs.XSModel;

/* JADX INFO: loaded from: classes13.dex */
public interface XSGrammar extends Grammar {
    XSModel toXSModel();

    XSModel toXSModel(XSGrammar[] xSGrammarArr);
}
