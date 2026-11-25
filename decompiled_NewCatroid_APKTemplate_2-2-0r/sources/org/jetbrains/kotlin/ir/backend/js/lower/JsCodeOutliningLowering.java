package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsCodeOutliningLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "backendContext", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getBackendContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsCodeOutliningLowering implements BodyLoweringPass {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final IrDeclarationOriginImpl OUTLINED_JS_CODE_ORIGIN = new IrDeclarationOriginImpl() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.JsCodeOutliningLowering$Companion$OUTLINED_JS_CODE_ORIGIN$1
    };
    private final JsIrBackendContext backendContext;

    public JsCodeOutliningLowering(JsIrBackendContext backendContext) {
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        this.backendContext = backendContext;
    }

    public final JsIrBackendContext getBackendContext() {
        return this.backendContext;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrBody irBody2 = irBody;
        if (JsCodeOutliningLoweringKt.containsCallsTo(irBody2, this.backendContext.getIntrinsics().getJsCode())) {
            IrElementTransformerVoidKt.transformChildrenVoid(irBody2, new JsCodeOutlineTransformer(this.backendContext, container));
        }
    }

    /* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsCodeOutliningLowering$Companion;", "", "()V", "OUTLINED_JS_CODE_ORIGIN", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "getOUTLINED_JS_CODE_ORIGIN", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrDeclarationOriginImpl getOUTLINED_JS_CODE_ORIGIN() {
            return JsCodeOutliningLowering.OUTLINED_JS_CODE_ORIGIN;
        }
    }
}
