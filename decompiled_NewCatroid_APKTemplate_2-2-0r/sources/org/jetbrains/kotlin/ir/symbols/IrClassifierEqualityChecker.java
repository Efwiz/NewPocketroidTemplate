package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrClassifierEqualityChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrClassifierEqualityChecker;", "", "areEqual", "", "left", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "right", "getHashCode", "", NativeSymbol.TYPE_NAME, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrClassifierEqualityChecker {
    boolean areEqual(IrClassifierSymbol left, IrClassifierSymbol right);

    int getHashCode(IrClassifierSymbol symbol);
}
