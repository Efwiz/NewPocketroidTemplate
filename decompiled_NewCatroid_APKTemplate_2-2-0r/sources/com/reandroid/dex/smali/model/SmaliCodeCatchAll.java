package com.reandroid.dex.smali.model;

import com.reandroid.dex.smali.SmaliDirective;

/* JADX INFO: loaded from: classes11.dex */
public class SmaliCodeCatchAll extends SmaliCodeExceptionHandler {
    @Override // com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return SmaliDirective.CATCH_ALL;
    }
}
