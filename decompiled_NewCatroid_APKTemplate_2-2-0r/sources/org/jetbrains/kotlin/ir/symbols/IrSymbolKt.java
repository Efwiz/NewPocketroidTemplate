package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IrSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isPublicApi", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Z", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrSymbolKt {
    public static final boolean isPublicApi(IrSymbol irSymbol) {
        Intrinsics.checkNotNullParameter(irSymbol, "<this>");
        return irSymbol.getSignature() != null;
    }
}
