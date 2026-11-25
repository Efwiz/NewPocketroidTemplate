package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: JsMainFunctionDetector.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002¨\u0006\b"}, d2 = {"isContinuationParameter", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "isLoweredSuspendFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "isStringArrayParameter", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsMainFunctionDetectorKt {
    public static final boolean isStringArrayParameter(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        IrType type = irValueParameter.getType();
        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
        if (irSimpleType == null || !IrTypePredicatesKt.isArray(irSimpleType) || irSimpleType.getArguments().size() != 1) {
            return false;
        }
        Object objSingle = CollectionsKt.single((List<? extends Object>) irSimpleType.getArguments());
        IrTypeProjection irTypeProjection = objSingle instanceof IrTypeProjection ? (IrTypeProjection) objSingle : null;
        if (irTypeProjection == null || irTypeProjection.getVariance() == Variance.IN_VARIANCE) {
            return false;
        }
        return IrTypePredicatesKt.isString(irTypeProjection.getType());
    }

    public static final boolean isLoweredSuspendFunction(IrFunction irFunction, JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.lastOrNull((List) irFunction.getValueParameters());
        if (irValueParameter == null) {
            return false;
        }
        IrType type = irValueParameter.getType();
        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
        if (irSimpleType == null) {
            return false;
        }
        return Intrinsics.areEqual(irSimpleType.getClassifier(), context.getCoroutineSymbols().getContinuationClass());
    }

    public static final boolean isContinuationParameter(IrValueParameter irValueParameter, JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        IrType type = irValueParameter.getType();
        IrSimpleType irSimpleType = type instanceof IrSimpleType ? (IrSimpleType) type : null;
        if (irSimpleType == null) {
            return false;
        }
        return Intrinsics.areEqual(irSimpleType.getClassifier(), context.getCoroutineSymbols().getContinuationClass());
    }
}
