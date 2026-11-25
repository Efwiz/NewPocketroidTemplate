package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsLoweredDeclarationOrigin;
import org.jetbrains.kotlin.ir.backend.js.export.ExportModelGeneratorKt;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: misc.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\n\u0010\f\u001a\u00020\t*\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u000f*\u00020\r\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013\u001a\n\u0010\u0014\u001a\u00020\u0001*\u00020\u0015\u001a\n\u0010\u0016\u001a\u00020\u0001*\u00020\u0011\u001a\n\u0010\u0017\u001a\u00020\u0001*\u00020\u0015\u001a\u0012\u0010\u0018\u001a\u00020\u0019*\u00020\u001a2\u0006\u0010\n\u001a\u00020\u000b\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u001b"}, d2 = {"isDispatchReceiver", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Z", "TODO", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "invokeFunForLambda", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "findUnitGetInstanceFunction", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "findUnitInstanceField", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "hasStableJsName", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "hasStaticDispatch", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "isEqualsInheritedFromAny", "isImportedFromModuleOnly", "prependFunctionCall", "", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MiscKt {
    public static final Void TODO(IrElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        throw new NotImplementedError("An operation is not implemented: " + (element.getClass().getSimpleName() + " is not supported yet here"));
    }

    /* JADX WARN: Code duplicated, block: B:32:0x007a  */
    public static final boolean hasStableJsName(IrFunction irFunction, JsIrBackendContext context) {
        boolean z;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!Intrinsics.areEqual(irFunction.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_WITH_STABLE_NAME.INSTANCE)) {
            boolean z2 = irFunction instanceof IrSimpleFunction;
            IrSimpleFunction irSimpleFunction = z2 ? (IrSimpleFunction) irFunction : null;
            if (irSimpleFunction == null || !IrUtilsKt.isMethodOfAny(irSimpleFunction)) {
                if (Intrinsics.areEqual(irFunction.getOrigin(), JsLoweredDeclarationOrigin.JS_SHADOWED_EXPORT.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_WITHOUT_STABLE_NAME.INSTANCE) || Intrinsics.areEqual(irFunction.getOrigin(), JsLoweredDeclarationOrigin.BRIDGE_PROPERTY_ACCESSOR.INSTANCE)) {
                    return false;
                }
                if (z2) {
                    IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irFunction).getCorrespondingPropertySymbol();
                    IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                    if (owner != null) {
                        IrSimpleFunction getter = owner.getGetter();
                        if ((getter != null ? AnnotationUtilsKt.getJsName(getter) : null) != null) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = true;
                    }
                } else {
                    z = true;
                }
                return (IrUtilsKt.isEffectivelyExternal((IrPossiblyExternalDeclaration) irFunction) || AnnotationUtilsKt.getJsName(irFunction) != null || ExportModelGeneratorKt.isExported(irFunction, context)) && z;
            }
        }
        return true;
    }

    public static final boolean isEqualsInheritedFromAny(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getName(), OperatorNameConventions.EQUALS) && irFunction.getDispatchReceiverParameter() != null && irFunction.getExtensionReceiverParameter() == null && irFunction.getValueParameters().size() == 1 && IrTypePredicatesKt.isNullableAny(irFunction.getValueParameters().get(0).getType());
    }

    public static final boolean hasStaticDispatch(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration instanceof IrSimpleFunction) {
            return ((IrSimpleFunction) irDeclaration).getDispatchReceiverParameter() == null;
        }
        if (irDeclaration instanceof IrProperty) {
            return AdditionalIrUtilsKt.isTopLevelDeclaration(irDeclaration);
        }
        if (irDeclaration instanceof IrField) {
            return ((IrField) irDeclaration).getIsStatic();
        }
        return true;
    }

    public static final boolean isDispatchReceiver(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "<this>");
        IrDeclarationParent parent = irValueDeclaration.getParent();
        if (parent instanceof IrClass) {
            return true;
        }
        return (parent instanceof IrFunction) && Intrinsics.areEqual(((IrFunction) parent).getDispatchReceiverParameter(), irValueDeclaration);
    }

    public static final void prependFunctionCall(IrBody irBody, IrCall call) {
        Intrinsics.checkNotNullParameter(irBody, "<this>");
        Intrinsics.checkNotNullParameter(call, "call");
        if (irBody instanceof IrExpressionBody) {
            IrExpressionBody irExpressionBody = (IrExpressionBody) irBody;
            irExpressionBody.setExpression(JsIrBuilder.INSTANCE.buildComposite(irExpressionBody.getExpression().getType(), CollectionsKt.listOf((Object[]) new IrExpression[]{call, irExpressionBody.getExpression()})));
        } else if (irBody instanceof IrBlockBody) {
            ((IrBlockBody) irBody).getStatements().add(0, call);
        }
    }

    public static final IrSimpleFunction findUnitGetInstanceFunction(JsCommonBackendContext jsCommonBackendContext) {
        Intrinsics.checkNotNullParameter(jsCommonBackendContext, "<this>");
        IrSimpleFunction irSimpleFunction = jsCommonBackendContext.getMapping().getObjectToGetInstanceFunction().get(jsCommonBackendContext.getIrBuiltIns().getUnitClass().getOwner());
        Intrinsics.checkNotNull(irSimpleFunction);
        return irSimpleFunction;
    }

    public static final IrField findUnitInstanceField(JsCommonBackendContext jsCommonBackendContext) {
        Intrinsics.checkNotNullParameter(jsCommonBackendContext, "<this>");
        IrField irField = jsCommonBackendContext.getMapping().getObjectToInstanceField().get(jsCommonBackendContext.getIrBuiltIns().getUnitClass().getOwner());
        Intrinsics.checkNotNull(irField);
        return irField;
    }

    public static final boolean isImportedFromModuleOnly(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (IrUtilsKt.isTopLevel(irDeclaration) && IrUtilsKt.isEffectivelyExternal(irDeclaration)) {
            IrDeclaration irDeclaration2 = irDeclaration;
            if (AnnotationUtilsKt.getJsModule(irDeclaration2) == null || AnnotationUtilsKt.isJsNonModule(irDeclaration2)) {
                IrDeclarationParent parent = irDeclaration.getParent();
                IrAnnotationContainer irAnnotationContainer = parent instanceof IrAnnotationContainer ? (IrAnnotationContainer) parent : null;
                if ((irAnnotationContainer != null ? AnnotationUtilsKt.getJsModule(irAnnotationContainer) : null) != null) {
                }
            }
            return true;
        }
        return false;
    }

    public static final IrSimpleFunction invokeFunForLambda(IrCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        IrExpression extensionReceiver = call.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        IrClass irClass = IrTypesKt.getClass(extensionReceiver.getType());
        Intrinsics.checkNotNull(irClass);
        IrSimpleFunction invokeFun = IrUtilsKt.getInvokeFun(irClass);
        Intrinsics.checkNotNull(invokeFun);
        return invokeFun;
    }
}
