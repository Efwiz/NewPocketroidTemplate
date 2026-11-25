package com.reandroid.dex.smali.model;

import com.reandroid.dex.debug.DebugElementType;
import com.reandroid.dex.smali.SmaliDirective;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliDebugElement extends SmaliDebug {
    public abstract DebugElementType<?> getDebugElementType();

    @Override // com.reandroid.dex.smali.model.SmaliDebug, com.reandroid.dex.smali.SmaliRegion
    public SmaliDirective getSmaliDirective() {
        return getDebugElementType().getSmaliDirective();
    }
}
