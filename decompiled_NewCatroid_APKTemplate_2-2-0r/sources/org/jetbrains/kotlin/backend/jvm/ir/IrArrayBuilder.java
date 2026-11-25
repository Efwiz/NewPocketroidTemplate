package org.jetbrains.kotlin.backend.jvm.ir;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.archive.PathTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbiKt;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;

/* JADX INFO: compiled from: IrArrayBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0019J\u0006\u0010\u001b\u001a\u00020\u0019J\b\u0010\u001c\u001a\u00020\u0019H\u0002J\b\u0010\u001d\u001a\u00020\u0019H\u0002J\u0018\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\u0005H\u0002J\u0010\u0010 \u001a\u00020\u00192\u0006\u0010!\u001a\u00020\u0019H\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0019H\u0002J\r\u0010%\u001a\u00020\u0011*\u00020\u0019H\u0086\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u00118F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\u0015\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\b¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/ir/IrArrayBuilder;", "", "builder", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "arrayType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getArrayType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "getBuilder", "()Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "elementType", "getElementType", PathTree.NAME_elements, "", "Lorg/jetbrains/kotlin/backend/jvm/ir/IrArrayElement;", "hasSpread", "", "getHasSpread", "()Z", "isUnboxedInlineClassArray", "unwrappedArrayType", "getUnwrappedArrayType", ProductAction.ACTION_ADD, "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "addSpread", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "buildComplexArray", "buildSimpleArray", "coerce", "irType", "copyArray", "spread", "newArray", "size", "", "unaryPlus", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrArrayBuilder {
    private final IrType arrayType;
    private final JvmIrBuilder builder;
    private final IrType elementType;
    private final List<IrArrayElement> elements;
    private final IrType unwrappedArrayType;

    public IrArrayBuilder(JvmIrBuilder builder, IrType arrayType) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        this.builder = builder;
        this.arrayType = arrayType;
        IrType irTypeUnboxInlineClass = InlineClassAbiKt.unboxInlineClass(arrayType);
        this.unwrappedArrayType = irTypeUnboxInlineClass;
        this.elementType = IrTypeUtilsKt.getArrayElementType(irTypeUnboxInlineClass, builder.getContext().getIrBuiltIns());
        this.elements = new ArrayList();
    }

    public final IrType getArrayType() {
        return this.arrayType;
    }

    public final JvmIrBuilder getBuilder() {
        return this.builder;
    }

    public final IrType getUnwrappedArrayType() {
        return this.unwrappedArrayType;
    }

    public final boolean isUnboxedInlineClassArray() {
        return this.arrayType != this.unwrappedArrayType;
    }

    public final IrType getElementType() {
        return this.elementType;
    }

    private final boolean getHasSpread() {
        List<IrArrayElement> list = this.elements;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((IrArrayElement) it.next()).getIsSpread()) {
                return true;
            }
        }
        return false;
    }

    public final boolean unaryPlus(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return add(irExpression);
    }

    public final boolean add(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return this.elements.add(new IrArrayElement(expression, false));
    }

    public final boolean addSpread(IrExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        return this.elements.add(new IrArrayElement(expression, true));
    }

    public final IrExpression build() {
        IrExpression irExpressionCopyArray;
        if (this.elements.isEmpty()) {
            irExpressionCopyArray = newArray(0);
        } else if (getHasSpread()) {
            irExpressionCopyArray = this.elements.size() == 1 ? copyArray(((IrArrayElement) CollectionsKt.single((List) this.elements)).getExpression()) : buildComplexArray();
        } else {
            irExpressionCopyArray = buildSimpleArray();
        }
        return coerce(irExpressionCopyArray, this.arrayType);
    }

    private final IrExpression newArray(int size) {
        return newArray(ExpressionHelpersKt.irInt$default(this.builder, size, null, 2, null));
    }

    private final IrExpression newArray(IrExpression size) {
        DeclarationSymbolMarker arrayOfNulls;
        if (IrTypeUtilsKt.isBoxedArray(this.unwrappedArrayType)) {
            arrayOfNulls = this.builder.getIrSymbols().getArrayOfNulls();
        } else {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(this.unwrappedArrayType);
            Intrinsics.checkNotNull(classOrNull);
            DeclarationSymbolMarker declarationSymbolMarker = null;
            boolean z = false;
            for (DeclarationSymbolMarker declarationSymbolMarker2 : IrUtilsKt.getConstructors(classOrNull)) {
                if (((IrConstructorSymbol) declarationSymbolMarker2).getOwner().getValueParameters().size() == 1) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    declarationSymbolMarker = declarationSymbolMarker2;
                    z = true;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            arrayOfNulls = (IrSymbol) declarationSymbolMarker;
        }
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(this.builder, (IrFunctionSymbol) arrayOfNulls, this.unwrappedArrayType);
        if (irFunctionAccessExpressionIrCall.getTypeArgumentsCount() != 0) {
            irFunctionAccessExpressionIrCall.putTypeArgument(0, this.elementType);
        }
        irFunctionAccessExpressionIrCall.putValueArgument(0, size);
        return irFunctionAccessExpressionIrCall;
    }

    private final IrExpression buildSimpleArray() {
        JvmIrBuilder jvmIrBuilder = this.builder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), null, null, false, 64, null);
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, newArray(this.elements.size()), null, null, false, null, 30, null);
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(this.unwrappedArrayType);
        Intrinsics.checkNotNull(classOrNull);
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(classOrNull)) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().asString(), FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                z = true;
            }
        }
        if (z) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = irSimpleFunctionSymbol;
            int i = 0;
            for (IrArrayElement irArrayElement : this.elements) {
                IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, irSimpleFunctionSymbol3);
                irCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default));
                irCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(irBlockBuilder2, i, null, 2, null));
                irCall.putValueArgument(1, coerce(irArrayElement.getExpression(), this.elementType));
                irBlockBuilder.unaryPlus(irCall);
                i++;
            }
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irGet(irBlockBuilder, irVariableIrTemporary$default));
            return irBlockBuilder.getIrBlockBody();
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final IrExpression copyArray(IrExpression spread) {
        if (spread instanceof IrConstructorCall) {
            return spread;
        }
        if ((spread instanceof IrFunctionAccessExpression) && Intrinsics.areEqual(((IrFunctionAccessExpression) spread).getSymbol(), this.builder.getIrSymbols().getArrayOfNulls())) {
            return spread;
        }
        JvmIrBuilder jvmIrBuilder = this.builder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), null, null, false, 64, null);
        IrVariable owner = spread instanceof IrGetValue ? ((IrGetValue) spread).getSymbol().getOwner() : ExpressionHelpersKt.irTemporary$default(irBlockBuilder, spread, null, null, false, null, 30, null);
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(this.unwrappedArrayType);
        Intrinsics.checkNotNull(classOrNull);
        IrSimpleFunctionSymbol propertyGetter = AdditionalIrUtilsKt.getPropertyGetter(classOrNull, "size");
        Intrinsics.checkNotNull(propertyGetter);
        JvmSymbols irSymbols = this.builder.getIrSymbols();
        IrType irType = this.unwrappedArrayType;
        Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        IrSimpleFunctionSymbol arraysCopyOfFunction = irSymbols.getArraysCopyOfFunction((IrSimpleType) irType);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, arraysCopyOfFunction);
        irCall.putValueArgument(0, coerce(ExpressionHelpersKt.irGet(irBlockBuilder2, owner), this.unwrappedArrayType));
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, propertyGetter);
        irCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, owner));
        Unit unit = Unit.INSTANCE;
        irCall.putValueArgument(1, irCall2);
        irBlockBuilder.unaryPlus(irCall);
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression buildComplexArray() {
        IrClassSymbol spreadBuilder;
        if (IrTypeUtilsKt.isBoxedArray(this.unwrappedArrayType)) {
            spreadBuilder = this.builder.getIrSymbols().getSpreadBuilder();
        } else {
            spreadBuilder = (IrClassSymbol) MapsKt.getValue(this.builder.getIrSymbols().getPrimitiveSpreadBuilders(), this.elementType);
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        boolean z = false;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(spreadBuilder)) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().asString(), ProductAction.ACTION_ADD)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                z = true;
            }
        }
        if (z) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = irSimpleFunctionSymbol;
            IrSimpleFunctionSymbol irSimpleFunctionSymbol4 = null;
            boolean z2 = false;
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol5 : IrUtilsKt.getFunctions(spreadBuilder)) {
                if (Intrinsics.areEqual(irSimpleFunctionSymbol5.getOwner().getName().asString(), "addSpread")) {
                    if (z2) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    z2 = true;
                    irSimpleFunctionSymbol4 = irSimpleFunctionSymbol5;
                }
            }
            if (z2) {
                IrSimpleFunctionSymbol irSimpleFunctionSymbol6 = irSimpleFunctionSymbol4;
                IrSimpleFunctionSymbol irSimpleFunctionSymbol7 = null;
                boolean z3 = false;
                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol8 : IrUtilsKt.getFunctions(spreadBuilder)) {
                    if (Intrinsics.areEqual(irSimpleFunctionSymbol8.getOwner().getName().asString(), "toArray")) {
                        if (z3) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        z3 = true;
                        irSimpleFunctionSymbol7 = irSimpleFunctionSymbol8;
                    }
                }
                if (z3) {
                    IrSimpleFunctionSymbol irSimpleFunctionSymbol9 = irSimpleFunctionSymbol7;
                    JvmIrBuilder jvmIrBuilder = this.builder;
                    IrBlockBuilder irBlockBuilder = new IrBlockBuilder(jvmIrBuilder.getContext(), jvmIrBuilder.getScope(), jvmIrBuilder.getStartOffset(), jvmIrBuilder.getEndOffset(), null, null, false, 64, null);
                    IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
                    IrConstructorCall irConstructorCallIrCallConstructor = ExpressionHelpersKt.irCallConstructor(irBlockBuilder2, (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(spreadBuilder)), CollectionsKt.emptyList());
                    irConstructorCallIrCallConstructor.putValueArgument(0, ExpressionHelpersKt.irInt$default(irBlockBuilder2, this.elements.size(), null, 2, null));
                    IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irConstructorCallIrCallConstructor, null, null, false, null, 30, null);
                    for (IrArrayElement irArrayElement : this.elements) {
                        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, irArrayElement.getIsSpread() ? irSimpleFunctionSymbol6 : irSimpleFunctionSymbol3);
                        irCall.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default));
                        irCall.putValueArgument(0, coerce(irArrayElement.getExpression(), irArrayElement.getIsSpread() ? this.unwrappedArrayType : this.elementType));
                        irBlockBuilder.unaryPlus(irCall);
                    }
                    IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, irSimpleFunctionSymbol9);
                    IrVariable irVariable = irVariableIrTemporary$default;
                    irCall2.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
                    if (IrTypeUtilsKt.isBoxedArray(this.unwrappedArrayType)) {
                        boolean z4 = false;
                        IrSimpleFunctionSymbol irSimpleFunctionSymbol10 = null;
                        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol11 : IrUtilsKt.getFunctions(spreadBuilder)) {
                            if (Intrinsics.areEqual(irSimpleFunctionSymbol11.getOwner().getName().asString(), "size")) {
                                if (z4) {
                                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                }
                                z4 = true;
                                irSimpleFunctionSymbol10 = irSimpleFunctionSymbol11;
                            }
                        }
                        if (z4) {
                            IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(irBlockBuilder2, this.builder.getIrSymbols().getArrayOfNulls(), this.arrayType, 0, 0, null, 28, null);
                            irCallIrCall$default.putTypeArgument(0, this.elementType);
                            IrCall irCall3 = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBuilder2, irSimpleFunctionSymbol10);
                            irCall3.setDispatchReceiver(ExpressionHelpersKt.irGet(irBlockBuilder2, irVariable));
                            Unit unit = Unit.INSTANCE;
                            irCallIrCall$default.putValueArgument(0, irCall3);
                            Unit unit2 = Unit.INSTANCE;
                            irCall2.putValueArgument(0, irCallIrCall$default);
                        } else {
                            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                        }
                    }
                    if (IrTypeUtilsKt.isBoxedArray(this.unwrappedArrayType)) {
                        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irImplicitCast(this.builder, irCall2, this.unwrappedArrayType));
                    } else {
                        irBlockBuilder.unaryPlus(irCall2);
                    }
                    return irBlockBuilder.getIrBlockBody();
                }
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    private final IrExpression coerce(IrExpression expression, IrType irType) {
        if (!isUnboxedInlineClassArray()) {
            return expression;
        }
        JvmIrBuilder jvmIrBuilder = this.builder;
        IrCall irCallIrCall$default = ExpressionHelpersKt.irCall$default(jvmIrBuilder, jvmIrBuilder.getIrSymbols().getUnsafeCoerceIntrinsic(), irType, 0, 0, null, 28, null);
        irCallIrCall$default.putTypeArgument(0, expression.getType());
        irCallIrCall$default.putTypeArgument(1, irType);
        irCallIrCall$default.putValueArgument(0, expression);
        return irCallIrCall$default;
    }
}
