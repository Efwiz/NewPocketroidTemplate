package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.expressions.IrExpression;

/* JADX INFO: compiled from: BlockDecomposerLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsBlockDecomposerLowering;", "Lorg/jetbrains/kotlin/ir/backend/js/lower/AbstractBlockDecomposerLowering;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "unreachableExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsBlockDecomposerLowering extends AbstractBlockDecomposerLowering {
    private final JsIrBackendContext context;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsBlockDecomposerLowering(JsIrBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.lower.AbstractBlockDecomposerLowering
    public IrExpression unreachableExpression() {
        return JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, this.context.getIntrinsics().getUnreachable(), this.context.getIrBuiltIns().getNothingType(), null, null, null, 28, null);
    }
}
