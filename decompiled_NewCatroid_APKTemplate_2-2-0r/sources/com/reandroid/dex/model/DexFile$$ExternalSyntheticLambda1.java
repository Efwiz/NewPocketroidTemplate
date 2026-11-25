package com.reandroid.dex.model;

import com.reandroid.dex.id.ClassId;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class DexFile$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ DexFile f$0;

    public /* synthetic */ DexFile$$ExternalSyntheticLambda1(DexFile dexFile) {
        this.f$0 = dexFile;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return this.f$0.create((ClassId) obj);
    }
}
