package com.reandroid.dex.model;

import com.reandroid.dex.data.FieldDef;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class DexClass$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ DexClass f$0;

    public /* synthetic */ DexClass$$ExternalSyntheticLambda3(DexClass dexClass) {
        this.f$0 = dexClass;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.initializeField((FieldDef) obj);
    }
}
