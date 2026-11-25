package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrClassifierEqualityChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/FqNameEqualityChecker;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierEqualityChecker;", "()V", "areEqual", "", "left", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "right", "getHashCode", "", NativeSymbol.TYPE_NAME, "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FqNameEqualityChecker implements IrClassifierEqualityChecker {
    public static final FqNameEqualityChecker INSTANCE = new FqNameEqualityChecker();

    private FqNameEqualityChecker() {
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrClassifierEqualityChecker
    public boolean areEqual(IrClassifierSymbol left, IrClassifierSymbol right) {
        Intrinsics.checkNotNullParameter(left, "left");
        Intrinsics.checkNotNullParameter(right, "right");
        return left == right || (left.getSignature() != null && Intrinsics.areEqual(left.getSignature(), right.getSignature()));
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrClassifierEqualityChecker
    public int getHashCode(IrClassifierSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IdSignature signature = symbol.getSignature();
        return signature != null ? signature.hashCode() : symbol.hashCode();
    }
}
