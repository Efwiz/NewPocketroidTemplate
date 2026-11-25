package org.jetbrains.kotlin.ir.backend.js.lower;

import com.reandroid.archive.PathTree;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.InlineClassesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: VarargLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aJ\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\tH\u0002J\u0014\u0010\u001e\u001a\u00020\u001a2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001a0 J\u000e\u0010!\u001a\u00020\u001a2\u0006\u0010\"\u001a\u00020\u001aJ\u000e\u0010#\u001a\u0004\u0018\u00010\t*\u00020\u0005H\u0002R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\rR\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\rR\u0011\u0010\u0017\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\r¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/InlineClassArrayInfo;", "", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "elementType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "arrayType", "(Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "arrayInlineClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getArrayInlineClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getArrayType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "getElementType", "inlined", "", "getInlined", "()Z", "primitiveArrayType", "getPrimitiveArrayType", "primitiveElementType", "getPrimitiveElementType", "boxArrayIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "array", "getInlineClassUnderlyingType", "irClass", "toPrimitiveArrayLiteral", PathTree.NAME_elements, "", "unboxElementIfNeeded", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getInlinedClass", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InlineClassArrayInfo {
    private final IrClass arrayInlineClass;
    private final IrType arrayType;
    private final JsIrBackendContext context;
    private final IrType elementType;
    private final boolean inlined;
    private final IrType primitiveArrayType;
    private final IrType primitiveElementType;

    public InlineClassArrayInfo(JsIrBackendContext context, IrType elementType, IrType arrayType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        this.context = context;
        this.elementType = elementType;
        this.arrayType = arrayType;
        IrClass inlinedClass = getInlinedClass(arrayType);
        this.arrayInlineClass = inlinedClass;
        boolean z = inlinedClass != null;
        this.inlined = z;
        if (z) {
            IrClass inlinedClass2 = getInlinedClass(elementType);
            if (inlinedClass2 != null) {
                elementType = getInlineClassUnderlyingType(inlinedClass2);
            } else {
                CompilationExceptionKt.compilationException("Could not get inlined class", elementType);
                throw null;
            }
        }
        this.primitiveElementType = elementType;
        if (z) {
            Intrinsics.checkNotNull(inlinedClass);
            arrayType = getInlineClassUnderlyingType(inlinedClass);
        }
        this.primitiveArrayType = arrayType;
    }

    public final IrType getArrayType() {
        return this.arrayType;
    }

    public final JsIrBackendContext getContext() {
        return this.context;
    }

    public final IrType getElementType() {
        return this.elementType;
    }

    private final IrClass getInlinedClass(IrType irType) {
        return this.context.getInlineClassesUtils().getInlinedClass(irType);
    }

    private final IrType getInlineClassUnderlyingType(IrClass irClass) {
        return this.context.getInlineClassesUtils().getInlineClassUnderlyingType(irClass);
    }

    public final IrClass getArrayInlineClass() {
        return this.arrayInlineClass;
    }

    public final boolean getInlined() {
        return this.inlined;
    }

    public final IrType getPrimitiveElementType() {
        return this.primitiveElementType;
    }

    public final IrType getPrimitiveArrayType() {
        return this.primitiveArrayType;
    }

    public final IrExpression boxArrayIfNeeded(IrExpression array) {
        Intrinsics.checkNotNullParameter(array, "array");
        if (this.arrayInlineClass == null) {
            return array;
        }
        IrConstructorCallImpl.Companion companion = IrConstructorCallImpl.INSTANCE;
        int startOffset = array.getStartOffset();
        int endOffset = array.getEndOffset();
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(this.arrayInlineClass);
        IrConstructor irConstructor = null;
        boolean z = false;
        for (IrConstructor irConstructor2 : IrUtilsKt.getConstructors(this.arrayInlineClass)) {
            if (irConstructor2.getIsPrimary()) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irConstructor = irConstructor2;
            }
        }
        if (z) {
            IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(companion, startOffset, endOffset, defaultType, irConstructor.getSymbol(), this.arrayInlineClass.getTypeParameters().size(), null, 32, null);
            irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, array);
            return irConstructorCallImplFromSymbolOwner$default;
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final IrExpression unboxElementIfNeeded(IrExpression element) {
        IrClass inlinedClass;
        Intrinsics.checkNotNullParameter(element, "element");
        if (this.arrayInlineClass == null || (inlinedClass = getInlinedClass(element.getType())) == null) {
            return element;
        }
        IrField inlineClassBackingField = InlineClassesKt.getInlineClassBackingField(inlinedClass);
        return new IrGetFieldImpl(element.getStartOffset(), element.getEndOffset(), inlineClassBackingField.getSymbol(), inlineClassBackingField.getType(), element, null, null, 96, null);
    }

    public final IrExpression toPrimitiveArrayLiteral(List<? extends IrExpression> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return VarargLoweringKt.toArrayLiteral(elements, this.context, this.primitiveArrayType, this.primitiveElementType);
    }
}
