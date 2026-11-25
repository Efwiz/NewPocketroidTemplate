package com.android.tools.r8.position;

import com.android.tools.r8.references.TypeReference;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class MethodPosition$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((TypeReference) obj).getDescriptor();
    }
}
