package org.jetbrains.kotlin.ir.backend.js.lower.inline;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.lower.inline.DefaultInlineFunctionResolver;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SaveInlineFunctionsBeforeInlining.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\tH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\n\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f0\u000bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\f`\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/inline/JsInlineFunctionResolver;", "Lorg/jetbrains/kotlin/backend/common/lower/inline/DefaultInlineFunctionResolver;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "enumEntriesIntrinsic", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "inlineFunctionsBeforeInlining", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "inlineFunctionsBeforeInliningSymbols", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lkotlin/collections/HashMap;", "getFunctionDeclaration", NativeSymbol.TYPE_NAME, "getFunctionSymbol", "irFunction", "shouldExcludeFunctionFromInlining", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsInlineFunctionResolver extends DefaultInlineFunctionResolver {
    private final IrSimpleFunctionSymbol enumEntriesIntrinsic;
    private final Mapping.Delegate<IrFunction, IrFunction> inlineFunctionsBeforeInlining;
    private final HashMap<IrFunction, IrFunctionSymbol> inlineFunctionsBeforeInliningSymbols;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsInlineFunctionResolver(JsIrBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.enumEntriesIntrinsic = context.getIntrinsics().getEnumEntriesIntrinsic();
        this.inlineFunctionsBeforeInlining = context.getMapping().getInlineFunctionsBeforeInlining();
        this.inlineFunctionsBeforeInliningSymbols = new HashMap<>();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
    public boolean shouldExcludeFunctionFromInlining(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return Intrinsics.areEqual(symbol, this.enumEntriesIntrinsic) || super.shouldExcludeFunctionFromInlining(symbol);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.inline.DefaultInlineFunctionResolver, org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
    public IrFunction getFunctionDeclaration(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrFunction functionDeclaration = super.getFunctionDeclaration(symbol);
        IrFunction irFunction = this.inlineFunctionsBeforeInlining.get(functionDeclaration);
        if (irFunction == null) {
            return functionDeclaration;
        }
        this.inlineFunctionsBeforeInliningSymbols.put(irFunction, functionDeclaration.getSymbol());
        return irFunction;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.inline.DefaultInlineFunctionResolver, org.jetbrains.kotlin.backend.common.lower.inline.InlineFunctionResolver
    public IrFunctionSymbol getFunctionSymbol(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        IrFunctionSymbol irFunctionSymbol = this.inlineFunctionsBeforeInliningSymbols.get(irFunction);
        return irFunctionSymbol == null ? super.getFunctionSymbol(irFunction) : irFunctionSymbol;
    }
}
