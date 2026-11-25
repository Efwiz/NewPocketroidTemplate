package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.util.SymbolRemapper;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ScriptLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/ScriptsToClassesSymbolRemapper;", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper$Empty;", "()V", "getReferencedClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", NativeSymbol.TYPE_NAME, "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ScriptsToClassesSymbolRemapper extends SymbolRemapper.Empty {
    @Override // org.jetbrains.kotlin.ir.util.SymbolRemapper.Empty, org.jetbrains.kotlin.ir.util.SymbolRemapper
    public IrClassifierSymbol getReferencedClassifier(IrClassifierSymbol symbol) {
        IrClassSymbol targetClass;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrSymbolOwner owner = symbol.getOwner();
        IrScript irScript = owner instanceof IrScript ? (IrScript) owner : null;
        return (irScript == null || (targetClass = irScript.getTargetClass()) == null) ? symbol : targetClass;
    }
}
