package org.apache.xerces.impl.dtd;

import org.apache.xerces.xni.parser.XMLDocumentFilter;

/* JADX INFO: loaded from: classes13.dex */
public interface XMLDTDValidatorFilter extends XMLDocumentFilter {
    boolean hasGrammar();

    boolean validate();
}
