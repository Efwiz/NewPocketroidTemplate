package org.jetbrains.kotlin.ir.util;

import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes5.dex */
public final /* synthetic */ class IrTypeUtilsKt$$ExternalSyntheticLambda0 implements DFS.Neighbors {
    @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
    public final Iterable getNeighbors(Object obj) {
        return IrTypeUtilsKt.superTypes((IrType) obj);
    }
}
