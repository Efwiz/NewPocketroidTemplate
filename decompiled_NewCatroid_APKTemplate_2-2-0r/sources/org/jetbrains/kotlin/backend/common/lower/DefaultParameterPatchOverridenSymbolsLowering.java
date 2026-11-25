package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;

/* JADX INFO: compiled from: DefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0006\u0010\n\u001a\u00020\tH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/DefaultParameterPatchOverridenSymbolsLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultParameterPatchOverridenSymbolsLowering implements DeclarationTransformer {
    private final CommonBackendContext context;

    public DefaultParameterPatchOverridenSymbolsLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrSimpleFunction) {
            IrFunction irFunction = this.context.getMapping().getDefaultArgumentsOriginalFunction().get(declaration);
            IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
            if (irSimpleFunction != null) {
                IrSimpleFunction irSimpleFunction2 = (IrSimpleFunction) declaration;
                List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction2.getOverriddenSymbols();
                List<IrSimpleFunctionSymbol> overriddenSymbols2 = irSimpleFunction.getOverriddenSymbols();
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = overriddenSymbols2.iterator();
                while (it.hasNext()) {
                    IrFunction irFunction2 = this.context.getMapping().getDefaultArgumentsDispatchFunction().get(((IrSimpleFunctionSymbol) it.next()).getOwner());
                    IrSimpleFunction irSimpleFunction3 = irFunction2 instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction2 : null;
                    IrSimpleFunctionSymbol symbol = irSimpleFunction3 != null ? irSimpleFunction3.getSymbol() : null;
                    if (symbol != null) {
                        arrayList.add(symbol);
                    }
                }
                irSimpleFunction2.setOverriddenSymbols(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) overriddenSymbols, (List) arrayList));
            }
        }
        return null;
    }
}
