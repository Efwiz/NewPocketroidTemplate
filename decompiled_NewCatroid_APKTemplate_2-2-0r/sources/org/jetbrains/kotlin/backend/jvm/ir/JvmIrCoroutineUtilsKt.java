package org.jetbrains.kotlin.backend.jvm.ir;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadesKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmIrCoroutineUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\f\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u0005\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\u0005\u001a\f\u0010\b\u001a\u00020\u0007*\u00020\u0005H\u0002\u001a\f\u0010\t\u001a\u00020\u0007*\u00020\u0005H\u0002\u001a\f\u0010\n\u001a\u00020\u0007*\u00020\u0005H\u0002\u001a\n\u0010\u000b\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\f\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\r\u001a\u00020\u0007*\u00020\u0005\u001a\f\u0010\u000e\u001a\u00020\u0007*\u0004\u0018\u00010\u000f\u001a\f\u0010\u0010\u001a\u00020\u0007*\u00020\u0005H\u0002\u001a\n\u0010\u0011\u001a\u00020\u0007*\u00020\u0005\u001a\n\u0010\u0012\u001a\u00020\u0007*\u00020\u0005\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"BRIDGE_ORIGINS", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "continuationParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "hasContinuation", "", "isBridgeToSuspendImplMethod", "isInvokeOfSuspendCallableReference", "isInvokeSuspendForInlineOfLambda", "isInvokeSuspendOfContinuation", "isInvokeSuspendOfLambda", "isNonBoxingSuspendDelegation", "isReadOfCrossinline", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "isStaticInlineClassReplacementDelegatingCall", "isStaticInlineClassReplacementForDefaultInterfaceMethod", "shouldContainSuspendMarkers", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmIrCoroutineUtilsKt {
    private static final Set<IrDeclarationOriginImpl> BRIDGE_ORIGINS = SetsKt.setOf((Object[]) new IrDeclarationOriginImpl[]{IrDeclarationOrigin.FUNCTION_FOR_DEFAULT_PARAMETER.INSTANCE, JvmLoweredDeclarationOrigin.JVM_STATIC_WRAPPER.INSTANCE, JvmLoweredDeclarationOrigin.JVM_OVERLOADS_WRAPPER.INSTANCE, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR.INSTANCE, JvmLoweredDeclarationOrigin.SUPER_INTERFACE_METHOD_BRIDGE.INSTANCE, IrDeclarationOrigin.BRIDGE.INSTANCE, IrDeclarationOrigin.BRIDGE_SPECIAL.INSTANCE, IrDeclarationOrigin.SYNTHETIC_GENERATED_SAM_IMPLEMENTATION.INSTANCE});

    public static final IrValueParameter continuationParameter(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (isInvokeSuspendOfLambda(irFunction) || isInvokeSuspendForInlineOfLambda(irFunction)) {
            return irFunction.getDispatchReceiverParameter();
        }
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : irFunction.getValueParameters()) {
            if (Intrinsics.areEqual(((IrValueParameter) obj3).getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE)) {
                if (z) {
                    return (IrValueParameter) obj;
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            obj = obj2;
        }
        return (IrValueParameter) obj;
    }

    public static final boolean isInvokeSuspendOfLambda(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getName().asString(), CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME) && Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irFunction).getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_LAMBDA.INSTANCE);
    }

    private static final boolean isInvokeSuspendForInlineOfLambda(IrFunction irFunction) {
        return Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE) && Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irFunction).getOrigin(), JvmLoweredDeclarationOrigin.SUSPEND_LAMBDA.INSTANCE);
    }

    public static final boolean isInvokeSuspendOfContinuation(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getName().asString(), CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME) && Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irFunction).getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS.INSTANCE);
    }

    private static final boolean isInvokeOfSuspendCallableReference(IrFunction irFunction) {
        IrFunctionSymbol symbol;
        IrFunction owner;
        if (!AdditionalIrUtilsKt.isSuspend(irFunction)) {
            return false;
        }
        String strAsString = irFunction.getName().asString();
        if (!Intrinsics.areEqual(strAsString, "invoke")) {
            Intrinsics.checkNotNull(strAsString);
            if (!StringsKt.startsWith$default(strAsString, "invoke-", false, 2, (Object) null)) {
                return false;
            }
        }
        IrFunction irFunction2 = irFunction;
        if (!Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irFunction2).getOrigin(), JvmLoweredDeclarationOrigin.FUNCTION_REFERENCE_IMPL.INSTANCE)) {
            return false;
        }
        IrAttributeContainer attributeOwnerId = IrUtilsKt.getParentAsClass(irFunction2).getAttributeOwnerId();
        IrFunctionReference irFunctionReference = attributeOwnerId instanceof IrFunctionReference ? (IrFunctionReference) attributeOwnerId : null;
        return irFunctionReference == null || (symbol = irFunctionReference.getSymbol()) == null || (owner = symbol.getOwner()) == null || !owner.getIsInline();
    }

    private static final boolean isBridgeToSuspendImplMethod(IrFunction irFunction) {
        Sequence<IrSimpleFunction> functions;
        if (AdditionalIrUtilsKt.isSuspend(irFunction) && (irFunction instanceof IrSimpleFunction)) {
            IrDeclarationParent parent = irFunction.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass != null && (functions = IrUtilsKt.getFunctions(irClass)) != null) {
                for (IrSimpleFunction irSimpleFunction : functions) {
                    if (Intrinsics.areEqual(irSimpleFunction.getName().asString(), irFunction.getName().asString() + CoroutineCodegenUtilKt.SUSPEND_IMPL_NAME_SUFFIX) && Intrinsics.areEqual(irSimpleFunction.getAttributeOwnerId(), ((IrSimpleFunction) irFunction).getAttributeOwnerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final boolean isStaticInlineClassReplacementDelegatingCall(IrFunction irFunction) {
        if (!(irFunction instanceof IrAttributeContainer) || JvmIrUtilsKt.isStaticInlineClassReplacement(irFunction)) {
            return false;
        }
        IrDeclarationParent parent = irFunction.getParent();
        Object obj = null;
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null || !IrDeclarationsKt.isSingleFieldValueClass(irClass)) {
            return false;
        }
        for (Object obj2 : irClass.getDeclarations()) {
            IrDeclaration irDeclaration = (IrDeclaration) obj2;
            if ((irDeclaration instanceof IrAttributeContainer) && Intrinsics.areEqual(((IrAttributeContainer) irDeclaration).getAttributeOwnerId(), ((IrAttributeContainer) irFunction).getAttributeOwnerId()) && irDeclaration != irFunction) {
                obj = obj2;
                break;
            }
        }
        IrDeclaration irDeclaration2 = (IrDeclaration) obj;
        return irDeclaration2 != null && JvmIrUtilsKt.isStaticInlineClassReplacement(irDeclaration2);
    }

    public static final boolean isNonBoxingSuspendDelegation(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return CollectionsKt.contains(BRIDGE_ORIGINS, irFunction.getOrigin()) || MultifileFacadesKt.isMultifileBridge(irFunction) || isBridgeToSuspendImplMethod(irFunction) || isStaticInlineClassReplacementForDefaultInterfaceMethod(irFunction);
    }

    public static final boolean isStaticInlineClassReplacementForDefaultInterfaceMethod(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (JvmIrUtilsKt.isStaticInlineClassReplacement(irFunction) && (irFunction instanceof IrSimpleFunction)) {
            IrAttributeContainer attributeOwnerId = ((IrSimpleFunction) irFunction).getAttributeOwnerId();
            Intrinsics.checkNotNull(attributeOwnerId, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSimpleFunction");
            if (((IrSimpleFunction) attributeOwnerId).getIsFakeOverride()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean shouldContainSuspendMarkers(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return (isNonBoxingSuspendDelegation(irFunction) || Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) || isInvokeSuspendOfContinuation(irFunction) || isInvokeOfSuspendCallableReference(irFunction) || isStaticInlineClassReplacementDelegatingCall(irFunction)) ? false : true;
    }

    public static final boolean hasContinuation(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return isInvokeSuspendOfLambda(irFunction) || !(!AdditionalIrUtilsKt.isSuspend(irFunction) || !shouldContainSuspendMarkers(irFunction) || JvmIrInlineUtilsKt.isEffectivelyInlineOnly(irFunction) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE));
    }

    public static final boolean isReadOfCrossinline(IrExpression irExpression) {
        if (irExpression instanceof IrGetValue) {
            IrValueDeclaration owner = ((IrGetValue) irExpression).getSymbol().getOwner();
            IrValueParameter irValueParameter = owner instanceof IrValueParameter ? (IrValueParameter) owner : null;
            return irValueParameter != null && irValueParameter.getIsCrossinline();
        }
        if (irExpression instanceof IrGetField) {
            return Intrinsics.areEqual(((IrGetField) irExpression).getSymbol().getOwner().getOrigin(), LocalDeclarationsLowering.DECLARATION_ORIGIN_FIELD_FOR_CROSSINLINE_CAPTURED_VALUE.INSTANCE);
        }
        return false;
    }
}
