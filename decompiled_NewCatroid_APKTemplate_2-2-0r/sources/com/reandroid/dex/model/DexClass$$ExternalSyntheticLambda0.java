package com.reandroid.dex.model;

import com.reandroid.dex.data.MethodDef;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class DexClass$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ DexClass f$0;

    public /* synthetic */ DexClass$$ExternalSyntheticLambda0(DexClass dexClass) {
        this.f$0 = dexClass;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.initializeMethod((MethodDef) obj);
    }
}
