package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsCommonInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.utils.IrResolveUtilsKt;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: AutoboxingTransformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020!H\u0014J\f\u0010\"\u001a\u00020#*\u00020\u001eH\u0004J\u0014\u0010$\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010%\u001a\u00020#H\u0014J\u0014\u0010&\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010 \u001a\u00020\u0014H\u0014J\u0014\u0010'\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010 \u001a\u00020\u0014H\u0014J\u001c\u0010(\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010 \u001a\u00020\u00142\u0006\u0010)\u001a\u00020*H\u0014J\u001c\u0010+\u001a\u00020\u001e*\u00020\u001e2\u0006\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020#H&R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0018\u0010\u0013\u001a\u00020\u000f*\u00020\u00148BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/AbstractValueUsageLowering;", "Lorg/jetbrains/kotlin/backend/common/lower/AbstractValueUsageTransformer;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "replaceTypesInsideInlinedFunctionBlock", "", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;Z)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "icUtils", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "getIcUtils", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonInlineClassesUtils;", "callTarget", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "getCallTarget", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "target", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getTarget", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "useAsVarargElement", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "getActualType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "useAs", ModuleXmlParser.TYPE, "useAsDispatchReceiver", "useAsExtensionReceiver", "useAsValueArgument", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "useExpressionAsType", "actualType", "expectedType", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractValueUsageLowering extends AbstractValueUsageTransformer implements BodyLoweringPass {
    private final JsCommonBackendContext context;
    private final JsCommonInlineClassesUtils icUtils;

    public abstract IrExpression useExpressionAsType(IrExpression irExpression, IrType irType, IrType irType2);

    public /* synthetic */ AbstractValueUsageLowering(JsCommonBackendContext jsCommonBackendContext, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jsCommonBackendContext, (i & 2) != 0 ? false : z);
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractValueUsageLowering(JsCommonBackendContext context, boolean z) {
        super(context.getIrBuiltIns(), z);
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.icUtils = context.getInlineClassesUtils();
    }

    public final JsCommonInlineClassesUtils getIcUtils() {
        return this.icUtils;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        if (container.getParent() instanceof IrFunction) {
            return;
        }
        IrElement irElementTransform = container.transform(this, null);
        Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
        if (container != ((IrDeclaration) irElementTransform)) {
            CompilationExceptionKt.compilationException("Declaration has changed", container);
            throw null;
        }
        IrBody irBody2 = irBody;
        IrDeclarationParent parent = container instanceof IrDeclarationParent ? (IrDeclarationParent) container : null;
        if (parent == null) {
            parent = container.getParent();
        }
        PatchDeclarationParentsKt.patchDeclarationParents(irBody2, parent);
    }

    protected final IrType getActualType(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrConstructorCall) {
            return ((IrConstructorCall) irExpression).getSymbol().getOwner().getReturnType();
        }
        if (irExpression instanceof IrCall) {
            return IrResolveUtilsKt.getRealOverrideTarget(((IrCall) irExpression).getSymbol().getOwner()).getReturnType();
        }
        if (irExpression instanceof IrGetField) {
            return ((IrGetField) irExpression).getSymbol().getOwner().getType();
        }
        if (irExpression instanceof IrTypeOperatorCall) {
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
            if (irTypeOperatorCall.getOperator() == IrTypeOperator.REINTERPRET_CAST) {
                return irTypeOperatorCall.getTypeOperand();
            }
            return irExpression.getType();
        }
        if (irExpression instanceof IrGetValue) {
            IrValueDeclaration owner = ((IrGetValue) irExpression).getSymbol().getOwner();
            if ((owner instanceof IrValueParameter) && this.icUtils.shouldValueParameterBeBoxed((IrValueParameter) owner)) {
                return getIrBuiltIns().getAnyType();
            }
            return irExpression.getType();
        }
        return irExpression.getType();
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAs(IrExpression irExpression, IrType type) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return useExpressionAsType(irExpression, getActualType(irExpression), type);
    }

    private final IrFunction getTarget(IrFunctionAccessExpression irFunctionAccessExpression) {
        if (irFunctionAccessExpression instanceof IrConstructorCall) {
            return ((IrConstructorCall) irFunctionAccessExpression).getSymbol().getOwner();
        }
        if (irFunctionAccessExpression instanceof IrDelegatingConstructorCall) {
            return ((IrDelegatingConstructorCall) irFunctionAccessExpression).getSymbol().getOwner();
        }
        if (irFunctionAccessExpression instanceof IrCall) {
            return getCallTarget((IrCall) irFunctionAccessExpression);
        }
        throw new NotImplementedError("An operation is not implemented: " + RenderIrElementKt.render$default(irFunctionAccessExpression, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    private final IrFunction getCallTarget(IrCall irCall) {
        return IrResolveUtilsKt.getRealOverrideTarget(irCall.getSymbol().getOwner());
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsDispatchReceiver(IrExpression irExpression, IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueParameter dispatchReceiverParameter = expression.getSymbol().getOwner().getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null && this.icUtils.shouldValueParameterBeBoxed(dispatchReceiverParameter)) {
            return useAs(irExpression, getIrBuiltIns().getAnyType());
        }
        IrValueParameter dispatchReceiverParameter2 = getTarget(expression).getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter2);
        return useAsArgument(irExpression, dispatchReceiverParameter2);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsExtensionReceiver(IrExpression irExpression, IrFunctionAccessExpression expression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrValueParameter extensionReceiverParameter = getTarget(expression).getExtensionReceiverParameter();
        Intrinsics.checkNotNull(extensionReceiverParameter);
        return useAsArgument(irExpression, extensionReceiverParameter);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsValueArgument(IrExpression irExpression, IrFunctionAccessExpression expression, IrValueParameter parameter) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return useAsArgument(irExpression, getTarget(expression).getValueParameters().get(parameter.getIndex()));
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.AbstractValueUsageTransformer
    protected IrExpression useAsVarargElement(IrExpression element, IrVararg expression) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrType varargElementType = ((!this.icUtils.isTypeInlined(element.getType()) || this.icUtils.isTypeInlined(expression.getType()) || IrTypeUtilsKt.isPrimitiveArray(expression.getType())) && IrTypeUtilsKt.isPrimitiveArray(expression.getType())) ? expression.getVarargElementType() : getIrBuiltIns().getAnyNType();
        return useAs(element, varargElementType);
    }
}
