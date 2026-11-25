package com.reandroid.dex.resource;

import com.reandroid.dex.model.DexInstruction;
import java.util.function.Predicate;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes11.dex */
public final /* synthetic */ class RDeclareStyleable$PrimitiveArrayFillSequence$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((DexInstruction) obj).isNumber();
    }
}
