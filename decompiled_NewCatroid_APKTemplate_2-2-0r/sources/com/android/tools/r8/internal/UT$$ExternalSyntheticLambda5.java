package com.android.tools.r8.internal;

import com.android.tools.r8.references.TypeReference;
import java.util.function.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes7.dex */
public final /* synthetic */ class UT$$ExternalSyntheticLambda5 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((TypeReference) obj).getTypeName();
    }
}
