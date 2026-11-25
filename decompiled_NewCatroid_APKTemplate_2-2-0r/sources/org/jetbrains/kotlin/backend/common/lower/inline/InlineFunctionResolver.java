package org.jetbrains.kotlin.backend.common.lower.inline;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FunctionInlining.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0003H&J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/inline/InlineFunctionResolver;", "", "getFunctionDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "getFunctionSymbol", "irFunction", "shouldExcludeFunctionFromInlining", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface InlineFunctionResolver {
    IrFunction getFunctionDeclaration(IrFunctionSymbol symbol);

    IrFunctionSymbol getFunctionSymbol(IrFunction irFunction);

    default boolean shouldExcludeFunctionFromInlining(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return Symbols.INSTANCE.isLateinitIsInitializedPropertyGetter(symbol) || Symbols.INSTANCE.isTypeOfIntrinsic(symbol);
    }
}
