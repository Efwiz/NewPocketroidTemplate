package org.jetbrains.kotlin.backend.jvm;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmSymbols.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"fieldByName", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "name", "", "functionByName", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmSymbolsKt {
    public static final IrSimpleFunctionSymbol functionByName(IrClassSymbol irClassSymbol, String name2) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(irClassSymbol)) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().asString(), name2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irSimpleFunctionSymbol;
    }

    public static final IrFieldSymbol fieldByName(IrClassSymbol irClassSymbol, String name2) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrFieldSymbol irFieldSymbol = null;
        boolean z = false;
        for (IrFieldSymbol irFieldSymbol2 : IrUtilsKt.getFields(irClassSymbol)) {
            if (Intrinsics.areEqual(irFieldSymbol2.getOwner().getName().asString(), name2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irFieldSymbol = irFieldSymbol2;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irFieldSymbol;
    }
}
