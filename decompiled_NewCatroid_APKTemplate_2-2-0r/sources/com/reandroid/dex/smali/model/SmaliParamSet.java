package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliReader;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliParamSet extends SmaliSet<SmaliMethodParameter> {
    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.smali.model.SmaliSet
    public SmaliMethodParameter createNext(SmaliReader smaliReader) {
        if (SmaliDirective.parse(smaliReader, false) == SmaliDirective.PARAM) {
            return new SmaliMethodParameter();
        }
        return null;
    }
}
