package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmIrLowerUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0005\u001a\u0004\u0018\u00010\u0006*\u00020\u0007H\u0000\u001a\u000e\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\u0007H\u0000\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0007\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u000b"}, d2 = {"returnsResultOfStdlibCall", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getReturnsResultOfStdlibCall", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "getPropertyReferenceForOptimizableDelegatedProperty", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getSingletonOrConstantForOptimizableDelegatedProperty", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isJvmOptimizableDelegate", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrLowerUtilsKt {
    private static final boolean _get_returnsResultOfStdlibCall_$isStdlibCall(IrStatement irStatement) {
        return (irStatement instanceof IrCall) && Intrinsics.areEqual(IrUtilsKt.getPackageFragment((IrDeclaration) ((IrCall) irStatement).getSymbol().getOwner()).getPackageFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME);
    }

    public static final boolean getReturnsResultOfStdlibCall(IrSimpleFunction irSimpleFunction) {
        IrStatement irStatement;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        IrBody body = irSimpleFunction.getBody();
        if (body instanceof IrExpressionBody) {
            return _get_returnsResultOfStdlibCall_$isStdlibCall(((IrExpressionBody) body).getExpression());
        }
        return (body instanceof IrBlockBody) && (irStatement = (IrStatement) CollectionsKt.singleOrNull((List) ((IrBlockBody) body).getStatements())) != null && (_get_returnsResultOfStdlibCall_$isStdlibCall(irStatement) || ((irStatement instanceof IrReturn) && _get_returnsResultOfStdlibCall_$isStdlibCall(((IrReturn) irStatement).getValue())));
    }

    public static final IrPropertyReference getPropertyReferenceForOptimizableDelegatedProperty(IrProperty irProperty) {
        IrSimpleFunction getter;
        IrSimpleFunction setter;
        IrExpressionBody initializer;
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        if (irProperty.getIsDelegated() && !irProperty.getIsFakeOverride() && irProperty.getBackingField() != null) {
            IrField backingField = irProperty.getBackingField();
            IrExpression expression = (backingField == null || (initializer = backingField.getInitializer()) == null) ? null : initializer.getExpression();
            if ((expression instanceof IrPropertyReference) && (((getter = irProperty.getGetter()) == null || getReturnsResultOfStdlibCall(getter)) && ((setter = irProperty.getSetter()) == null || getReturnsResultOfStdlibCall(setter)))) {
                return (IrPropertyReference) expression;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:57:0x00a3  */
    private static final boolean getSingletonOrConstantForOptimizableDelegatedProperty$isInlineable(IrExpression irExpression) {
        boolean z;
        IrFieldSymbol symbol;
        IrField owner;
        List<IrStatement> statements;
        if (irExpression instanceof IrConst ? true : irExpression instanceof IrGetSingletonValue) {
            return true;
        }
        if (irExpression instanceof IrCall) {
            IrCall irCall = (IrCall) irExpression;
            IrExpression dispatchReceiver = irCall.getDispatchReceiver();
            if (!((dispatchReceiver == null || getSingletonOrConstantForOptimizableDelegatedProperty$isInlineable(dispatchReceiver)) ? false : true)) {
                IrExpression extensionReceiver = irCall.getExtensionReceiver();
                if (!((extensionReceiver == null || getSingletonOrConstantForOptimizableDelegatedProperty$isInlineable(extensionReceiver)) ? false : true) && irCall.getValueArgumentsCount() == 0) {
                    IrSimpleFunction owner2 = irCall.getSymbol().getOwner();
                    if (owner2.getModality() == Modality.FINAL && Intrinsics.areEqual(owner2.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE)) {
                        IrBody body = owner2.getBody();
                        IrStatement irStatement = (body == null || (statements = IrUtilsKt.getStatements(body)) == null) ? null : (IrStatement) CollectionsKt.singleOrNull((List) statements);
                        IrReturn irReturn = irStatement instanceof IrReturn ? (IrReturn) irStatement : null;
                        IrExpression value = irReturn != null ? irReturn.getValue() : null;
                        IrGetField irGetField = value instanceof IrGetField ? (IrGetField) value : null;
                        if ((irGetField == null || (symbol = irGetField.getSymbol()) == null || (owner = symbol.getOwner()) == null || !owner.getIsFinal()) ? false : true) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    if (z) {
                        return true;
                    }
                }
            }
        } else if (irExpression instanceof IrGetValue) {
            return Intrinsics.areEqual(((IrGetValue) irExpression).getSymbol().getOwner().getOrigin(), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE);
        }
        return false;
    }

    public static final IrExpression getSingletonOrConstantForOptimizableDelegatedProperty(IrProperty irProperty) {
        IrField backingField;
        IrExpressionBody initializer;
        IrExpression expression;
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        if (!irProperty.getIsDelegated() || irProperty.getIsFakeOverride() || irProperty.getBackingField() == null || (backingField = irProperty.getBackingField()) == null || (initializer = backingField.getInitializer()) == null || (expression = initializer.getExpression()) == null || !getSingletonOrConstantForOptimizableDelegatedProperty$isInlineable(expression)) {
            return null;
        }
        return expression;
    }

    public static final boolean isJvmOptimizableDelegate(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        return (!irProperty.getIsDelegated() || irProperty.getIsFakeOverride() || irProperty.getBackingField() == null || (getPropertyReferenceForOptimizableDelegatedProperty(irProperty) == null && getSingletonOrConstantForOptimizableDelegatedProperty(irProperty) == null)) ? false : true;
    }
}
