package org.jetbrains.kotlin.backend.jvm.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContextBase;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmIrBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "backendContext", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;II)V", "getBackendContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "irSymbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "getIrSymbols", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrBuilder extends IrBuilderWithScope {
    private final JvmBackendContext backendContext;
    private final IrSymbol symbol;

    public /* synthetic */ JvmIrBuilder(JvmBackendContext jvmBackendContext, IrSymbol irSymbol, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(jvmBackendContext, irSymbol, (i3 & 4) != 0 ? -1 : i, (i3 & 8) != 0 ? -1 : i2);
    }

    public final JvmBackendContext getBackendContext() {
        return this.backendContext;
    }

    public final IrSymbol getSymbol() {
        return this.symbol;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmIrBuilder(JvmBackendContext backendContext, IrSymbol symbol, int i, int i2) {
        super(new IrGeneratorContextBase(backendContext.getIrBuiltIns()), new Scope(symbol), i, i2);
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.backendContext = backendContext;
        this.symbol = symbol;
    }

    public final JvmSymbols getIrSymbols() {
        return this.backendContext.getIr().getSymbols();
    }
}
