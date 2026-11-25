package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrWhen;

/* JADX INFO: compiled from: ES6AddBoxParameterLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00018F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007\"\u0015\u0010\b\u001a\u00020\u0006*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u000b"}, d2 = {"boxParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getBoxParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "isBoxParameter", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)Z", "isBoxParameterDefaultResolution", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "(Lorg/jetbrains/kotlin/ir/expressions/IrWhen;)Z", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ES6AddBoxParameterLoweringKt {
    public static final boolean isBoxParameter(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        return Intrinsics.areEqual(irValueParameter.getOrigin(), ES6_BOX_PARAMETER.INSTANCE);
    }

    public static final boolean isBoxParameterDefaultResolution(IrWhen irWhen) {
        Intrinsics.checkNotNullParameter(irWhen, "<this>");
        return Intrinsics.areEqual(irWhen.getOrigin(), ES6_BOX_PARAMETER_DEFAULT_RESOLUTION.INSTANCE);
    }

    public static final IrValueParameter getBoxParameter(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irFunction.getValueParameters());
        if (irValueParameter == null || !isBoxParameter(irValueParameter)) {
            return null;
        }
        return irValueParameter;
    }
}
