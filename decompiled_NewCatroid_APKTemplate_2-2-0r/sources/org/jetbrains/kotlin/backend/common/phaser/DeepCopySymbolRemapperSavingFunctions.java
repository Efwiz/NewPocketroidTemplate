package org.jetbrains.kotlin.backend.common.phaser;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapperPreservingSignatures;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: performByIrFile.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u0005H\u0016J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tH\u0016R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/phaser/DeepCopySymbolRemapperSavingFunctions;", "Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapperPreservingSignatures;", "()V", "declaredClasses", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getDeclaredClasses", "()Ljava/util/Set;", "declaredFunctions", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getDeclaredFunctions", "getDeclaredClass", NativeSymbol.TYPE_NAME, "getDeclaredFunction", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DeepCopySymbolRemapperSavingFunctions extends DeepCopySymbolRemapperPreservingSignatures {
    private final Set<IrSimpleFunctionSymbol> declaredFunctions = new LinkedHashSet();
    private final Set<IrClassSymbol> declaredClasses = new LinkedHashSet();

    public final Set<IrSimpleFunctionSymbol> getDeclaredFunctions() {
        return this.declaredFunctions;
    }

    public final Set<IrClassSymbol> getDeclaredClasses() {
        return this.declaredClasses;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrSimpleFunctionSymbol getDeclaredFunction(IrSimpleFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.declaredFunctions.add(symbol);
        return super.getDeclaredFunction(symbol);
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassSymbol getDeclaredClass(IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.declaredClasses.add(symbol);
        return super.getDeclaredClass(symbol);
    }
}
