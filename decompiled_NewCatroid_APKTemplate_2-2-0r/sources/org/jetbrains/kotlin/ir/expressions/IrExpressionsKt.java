package org.jetbrains.kotlin.ir.expressions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrExpressions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000r\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0012\u0010\u0018\u001a\u00020\u0019*\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\t\u001a\u0012\u0010\u001c\u001a\u00020\u0019*\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f\u001a(\u0010 \u001a\u00020\u0019*\u0006\u0012\u0002\b\u00030!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00142\u0006\u0010%\u001a\u00020\u0014H\u0000\u001a$\u0010&\u001a\u00020\u0019*\u0006\u0012\u0002\b\u00030!2\n\u0010'\u001a\u0006\u0012\u0002\b\u00030!2\b\b\u0002\u0010(\u001a\u00020\u0014\u001a\n\u0010)\u001a\u00020\u0001*\u00020\t\u001a\u0014\u0010*\u001a\u00020\t*\u00020\t2\b\u0010+\u001a\u0004\u0018\u00010,\u001a\n\u0010-\u001a\u00020\u0001*\u00020\t\u001a\u001a\u0010.\u001a\u00020\u0019*\u00020/2\u0006\u00100\u001a\u0002012\u0006\u0010\u001b\u001a\u00020\t\u001a&\u0010.\u001a\u00020\u0019*\u0006\u0012\u0002\b\u00030!2\u0006\u00102\u001a\u0002032\u0006\u00100\u001a\u0002012\u0006\u0010\u001b\u001a\u00020\t\u001a\u001a\u00104\u001a\u00020\u0019*\u00020\u001d2\u0006\u00105\u001a\u00020\u00142\u0006\u00106\u001a\u00020\u001f\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006\"\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0003\"(\u0010\n\u001a\u00020\t*\u00020\u000b2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\"(\u0010\u0010\u001a\u00020\t*\u00020\u000b2\u0006\u0010\b\u001a\u00020\t8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\r\"\u0004\b\u0012\u0010\u000f\"\u0015\u0010\u0013\u001a\u00020\u0014*\u00020\u00158F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017¨\u00067"}, d2 = {"isAdapterWithReflection", "", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)Z", "isTransparentScope", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;)Z", "isWithReflection", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "left", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "getLeft", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "setLeft", "(Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "right", "getRight", "setRight", "typeParametersCount", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getTypeParametersCount", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)I", "addArgument", "", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "argument", "addElement", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "varargElement", "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", "checkArgumentSlotAccess", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", Namer.METADATA_CLASS_KIND, "", "index", "total", "copyTypeArgumentsFrom", "other", "shift", "hasNoSideEffects", "implicitCastTo", "expectedType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isUnchanging", "putArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "callee", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "putElement", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrExpressionsKt {
    public static final boolean isWithReflection(IrFunctionReference irFunctionReference) {
        Intrinsics.checkNotNullParameter(irFunctionReference, "<this>");
        return irFunctionReference.getReflectionTarget() != null;
    }

    public static final boolean isAdapterWithReflection(IrFunctionReference irFunctionReference) {
        Intrinsics.checkNotNullParameter(irFunctionReference, "<this>");
        return (irFunctionReference.getReflectionTarget() == null || Intrinsics.areEqual(irFunctionReference.getReflectionTarget(), irFunctionReference.getSymbol())) ? false : true;
    }

    public static final IrExpression getLeft(IrDynamicOperatorExpression irDynamicOperatorExpression) {
        Intrinsics.checkNotNullParameter(irDynamicOperatorExpression, "<this>");
        return irDynamicOperatorExpression.getReceiver();
    }

    public static final void setLeft(IrDynamicOperatorExpression irDynamicOperatorExpression, IrExpression value) {
        Intrinsics.checkNotNullParameter(irDynamicOperatorExpression, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        irDynamicOperatorExpression.setReceiver(value);
    }

    public static final IrExpression getRight(IrDynamicOperatorExpression irDynamicOperatorExpression) {
        Intrinsics.checkNotNullParameter(irDynamicOperatorExpression, "<this>");
        return irDynamicOperatorExpression.getArguments().get(0);
    }

    public static final void setRight(IrDynamicOperatorExpression irDynamicOperatorExpression, IrExpression value) {
        Intrinsics.checkNotNullParameter(irDynamicOperatorExpression, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        if (irDynamicOperatorExpression.getArguments().isEmpty()) {
            irDynamicOperatorExpression.getArguments().add(value);
        } else {
            irDynamicOperatorExpression.getArguments().set(0, value);
        }
    }

    public static final void putArgument(IrFunctionAccessExpression irFunctionAccessExpression, IrValueParameter parameter, IrExpression argument) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(argument, "argument");
        putArgument(irFunctionAccessExpression, irFunctionAccessExpression.getSymbol().getOwner(), parameter, argument);
    }

    public static final void putElement(IrVararg irVararg, int i, IrVarargElement element) {
        Intrinsics.checkNotNullParameter(irVararg, "<this>");
        Intrinsics.checkNotNullParameter(element, "element");
        irVararg.getElements().set(i, element);
    }

    public static final void addElement(IrVararg irVararg, IrVarargElement varargElement) {
        Intrinsics.checkNotNullParameter(irVararg, "<this>");
        Intrinsics.checkNotNullParameter(varargElement, "varargElement");
        irVararg.getElements().add(varargElement);
    }

    public static final void addArgument(IrStringConcatenation irStringConcatenation, IrExpression argument) {
        Intrinsics.checkNotNullParameter(irStringConcatenation, "<this>");
        Intrinsics.checkNotNullParameter(argument, "argument");
        irStringConcatenation.getArguments().add(argument);
    }

    public static final boolean isTransparentScope(IrContainerExpression irContainerExpression) {
        Intrinsics.checkNotNullParameter(irContainerExpression, "<this>");
        return irContainerExpression instanceof IrComposite;
    }

    public static final IrExpression implicitCastTo(IrExpression irExpression, IrType irType) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return irType == null ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irType, IrTypeOperator.IMPLICIT_CAST, irType, irExpression);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c  */
    /* JADX WARN: Code duplicated, block: B:16:0x0028  */
    public static final boolean isUnchanging(IrExpression irExpression) {
        IrValueDeclaration owner;
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (!(irExpression instanceof IrFunctionExpression)) {
            if (irExpression instanceof IrCallableReference) {
                IrCallableReference irCallableReference = (IrCallableReference) irExpression;
                if (irCallableReference.getDispatchReceiver() != null || irCallableReference.getExtensionReceiver() != null) {
                    if (!(irExpression instanceof IrClassReference) && !(irExpression instanceof IrConst)) {
                        if (irExpression instanceof IrGetValue) {
                            owner = ((IrGetValue) irExpression).getSymbol().getOwner();
                            if ((owner instanceof IrVariable) || !((IrVariable) owner).getIsVar()) {
                            }
                        }
                        return false;
                    }
                }
            } else if (!(irExpression instanceof IrClassReference)) {
                if (irExpression instanceof IrGetValue) {
                    owner = ((IrGetValue) irExpression).getSymbol().getOwner();
                    if (owner instanceof IrVariable) {
                    }
                }
                return false;
            }
        }
        return true;
    }

    public static final boolean hasNoSideEffects(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return isUnchanging(irExpression) || (irExpression instanceof IrGetValue);
    }

    public static final void checkArgumentSlotAccess(IrMemberAccessExpression<?> irMemberAccessExpression, String kind, int i, int i2) {
        String str;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(kind, "kind");
        if (i >= i2) {
            StringBuilder sbAppend = new StringBuilder("No such ").append(kind).append(" argument slot in ").append(irMemberAccessExpression.getClass().getSimpleName()).append(": ").append(i).append(" (total=").append(i2).append(')');
            IdSignature signature = irMemberAccessExpression.getSymbol().getSignature();
            if (signature == null || (str = ".\nSymbol: " + signature) == null) {
                str = "";
            }
            throw new AssertionError(sbAppend.append(str).toString());
        }
    }

    public static /* synthetic */ void copyTypeArgumentsFrom$default(IrMemberAccessExpression irMemberAccessExpression, IrMemberAccessExpression irMemberAccessExpression2, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        copyTypeArgumentsFrom(irMemberAccessExpression, irMemberAccessExpression2, i);
    }

    public static final void copyTypeArgumentsFrom(IrMemberAccessExpression<?> irMemberAccessExpression, IrMemberAccessExpression<?> other, int i) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(other, "other");
        irMemberAccessExpression.getTypeArgumentsCount();
        other.getTypeArgumentsCount();
        int typeArgumentsCount = other.getTypeArgumentsCount();
        for (int i2 = 0; i2 < typeArgumentsCount; i2++) {
            irMemberAccessExpression.putTypeArgument(i2 + i, other.getTypeArgument(i2));
        }
    }

    public static final int getTypeParametersCount(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return callableDescriptor instanceof PropertyAccessorDescriptor ? ((PropertyAccessorDescriptor) callableDescriptor).getCorrespondingProperty().getTypeParameters().size() : callableDescriptor.getTypeParameters().size();
    }

    public static final void putArgument(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction callee, IrValueParameter parameter, IrExpression argument) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(callee, "callee");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(argument, "argument");
        if (Intrinsics.areEqual(parameter, callee.getDispatchReceiverParameter())) {
            irMemberAccessExpression.setDispatchReceiver(argument);
        } else if (Intrinsics.areEqual(parameter, callee.getExtensionReceiverParameter())) {
            irMemberAccessExpression.setExtensionReceiver(argument);
        } else {
            irMemberAccessExpression.putValueArgument(parameter.getIndex(), argument);
        }
    }
}
