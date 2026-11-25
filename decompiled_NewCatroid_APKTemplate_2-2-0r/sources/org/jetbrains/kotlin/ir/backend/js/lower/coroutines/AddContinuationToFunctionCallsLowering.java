package org.jetbrains.kotlin.ir.backend.js.lower.coroutines;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;

/* JADX INFO: compiled from: AddContinuationToFunctionCallsLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0014R\u0014\u0010\u0002\u001a\u00020\u0003X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/coroutines/AddContinuationToFunctionCallsLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/coroutines/AbstractAddContinuationToFunctionCallsLowering;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "isContinuationItself", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AddContinuationToFunctionCallsLowering extends AbstractAddContinuationToFunctionCallsLowering {
    private final JsCommonBackendContext context;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering
    public JsCommonBackendContext getContext() {
        return this.context;
    }

    public AddContinuationToFunctionCallsLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.coroutines.AbstractAddContinuationToFunctionCallsLowering
    protected boolean isContinuationItself(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
        if ((overriddenSymbols instanceof Collection) && overriddenSymbols.isEmpty()) {
            return false;
        }
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol : overriddenSymbols) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol.getOwner().getName().asString(), "doResume") && Intrinsics.areEqual(irSimpleFunctionSymbol.getOwner().getParent(), getContext().getCoroutineSymbols().getCoroutineImpl().getOwner())) {
                return true;
            }
        }
        return false;
    }
}
