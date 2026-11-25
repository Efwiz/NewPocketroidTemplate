package org.jetbrains.kotlin.com.intellij.psi.util;

import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class CachedValueProfiler$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return CachedValueProfiler.findCallerPlace((Throwable) obj);
    }
}
