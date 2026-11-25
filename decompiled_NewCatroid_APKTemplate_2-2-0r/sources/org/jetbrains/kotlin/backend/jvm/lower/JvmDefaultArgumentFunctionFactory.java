package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.MaskedDefaultArgumentFunctionFactory;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: JvmDefaultArgumentFunctionFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0007H\u0014¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmDefaultArgumentFunctionFactory;", "Lorg/jetbrains/kotlin/backend/common/lower/MaskedDefaultArgumentFunctionFactory;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "hasNullAsUndefinedValue", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultArgumentFunctionFactory extends MaskedDefaultArgumentFunctionFactory {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmDefaultArgumentFunctionFactory(CommonBackendContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentFunctionFactory
    protected boolean hasNullAsUndefinedValue(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        List<IrType> primitiveIrTypes = getContext().getIrBuiltIns().getPrimitiveIrTypes();
        IrType irTypeUnboxType = InlineClassAbi.INSTANCE.unboxType(irType);
        if (irTypeUnboxType != null) {
            irType = irTypeUnboxType;
        }
        return !primitiveIrTypes.contains(irType);
    }
}
