package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.optimizations.PropertyAccessorInlineLowering;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JsPropertyAccessorInlineLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u00020\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/JsPropertyAccessorInlineLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/optimizations/PropertyAccessorInlineLowering;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "isSafeToInline", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "accessContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsPropertyAccessorInlineLowering extends PropertyAccessorInlineLowering {
    private final JsIrBackendContext context;

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JsPropertyAccessorInlineLowering(JsIrBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.optimizations.PropertyAccessorInlineLowering
    public boolean isSafeToInline(IrProperty irProperty, IrDeclaration accessContainer) {
        IrFile fileOrNull;
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        Intrinsics.checkNotNullParameter(accessContainer, "accessContainer");
        if (!isSafeToInlineInClosedWorld(irProperty)) {
            return false;
        }
        IrProperty irProperty2 = irProperty;
        if ((!IrUtilsKt.isTopLevel(irProperty2) && !this.context.getIncrementalCacheEnabled()) || Intrinsics.areEqual(irProperty.getSymbol(), this.context.getIntrinsics().getVoid())) {
            return true;
        }
        IrFile fileOrNull2 = IrUtilsKt.getFileOrNull(accessContainer);
        if (fileOrNull2 == null || (fileOrNull = IrUtilsKt.getFileOrNull(irProperty2)) == null) {
            return false;
        }
        return Intrinsics.areEqual(fileOrNull2, fileOrNull);
    }
}
