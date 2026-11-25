package org.jetbrains.kotlin.backend.wasm.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.backend.js.utils.AnnotationUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.wasm.ir.WasmImportDescriptor;

/* JADX INFO: compiled from: Annotations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a\f\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\f\u0010\u0003\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\f\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0002\u001a\f\u0010\u0006\u001a\u0004\u0018\u00010\u0001*\u00020\u0007\u001a\f\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\u0007\u001a\f\u0010\n\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\n\u0010\u000b\u001a\u00020\f*\u00020\u0002\u001a\n\u0010\r\u001a\u00020\f*\u00020\u0002\u001a\n\u0010\u000e\u001a\u00020\f*\u00020\u0002\u001a\n\u0010\u000f\u001a\u00020\f*\u00020\u0002¨\u0006\u0010"}, d2 = {"getJsFunAnnotation", "", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "getJsPrimitiveType", "getWasmArrayAnnotation", "Lorg/jetbrains/kotlin/backend/wasm/utils/WasmArrayInfo;", "getWasmExportNameIfWasmExport", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getWasmImportDescriptor", "Lorg/jetbrains/kotlin/wasm/ir/WasmImportDescriptor;", "getWasmOpAnnotation", "hasExcludedFromCodegenAnnotation", "", "hasWasmAutoboxedAnnotation", "hasWasmNoOpCastAnnotation", "hasWasmPrimitiveConstructorAnnotation", "backend.wasm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AnnotationsKt {
    public static final boolean hasExcludedFromCodegenAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.ExcludedFromCodegen"));
    }

    public static final WasmImportDescriptor getWasmImportDescriptor(IrFunction irFunction) {
        String strAsString;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irFunction, new FqName("kotlin.wasm.WasmImport"));
        if (annotation == null) {
            return null;
        }
        IrExpression valueArgument = annotation.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<kotlin.String>");
        String str = (String) ((IrConst) valueArgument).getValue();
        IrExpression valueArgument2 = annotation.getValueArgument(1);
        IrConst irConst = valueArgument2 instanceof IrConst ? (IrConst) valueArgument2 : null;
        if (irConst == null || (strAsString = (String) irConst.getValue()) == null) {
            strAsString = irFunction.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        return new WasmImportDescriptor(str, strAsString);
    }

    public static final String getWasmOpAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.WasmOp"));
        if (annotation != null) {
            return AnnotationUtilsKt.getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final boolean hasWasmNoOpCastAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.WasmNoOpCast"));
    }

    public static final boolean hasWasmAutoboxedAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.WasmAutoboxed"));
    }

    public static final boolean hasWasmPrimitiveConstructorAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        return IrUtilsKt.hasAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.WasmPrimitiveConstructor"));
    }

    public static final WasmArrayInfo getWasmArrayAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.WasmArrayOf"));
        if (annotation == null) {
            return null;
        }
        IrExpression valueArgument = annotation.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrClassReference");
        IrSymbolOwner owner = ((IrClassReference) valueArgument).getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrExpression valueArgument2 = annotation.getValueArgument(1);
        Intrinsics.checkNotNull(valueArgument2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConst<*>");
        Object value = ((IrConst) valueArgument2).getValue();
        Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.Boolean");
        return new WasmArrayInfo((IrClass) owner, ((Boolean) value).booleanValue());
    }

    public static final String getJsFunAnnotation(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, new FqName("kotlin.JsFun"));
        if (annotation != null) {
            return AnnotationUtilsKt.getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final String getJsPrimitiveType(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irAnnotationContainer, new FqName("kotlin.wasm.internal.JsPrimitive"));
        if (annotation != null) {
            return AnnotationUtilsKt.getSingleConstStringArgument(annotation);
        }
        return null;
    }

    public static final String getWasmExportNameIfWasmExport(IrFunction irFunction) {
        String str;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irFunction, new FqName("kotlin.wasm.WasmExport"));
        if (annotation == null) {
            return null;
        }
        if (annotation.getValueArgumentsCount() == 0) {
            return irFunction.getName().getIdentifier();
        }
        IrExpression valueArgument = annotation.getValueArgument(0);
        IrConst irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
        return (irConst == null || (str = (String) irConst.getValue()) == null) ? irFunction.getName().getIdentifier() : str;
    }
}
