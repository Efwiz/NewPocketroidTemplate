package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmDefaultArgumentStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\b\u0010\t\u001a\u00020\nH\u0014J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u001c\u0010\r\u001a\u00020\u000e*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\bH\u0014¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmDefaultArgumentStubGenerator;", "Lorg/jetbrains/kotlin/backend/common/lower/DefaultArgumentStubGenerator;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "context", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "defaultArgumentStubVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getOriginForCallToImplementation", "Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredStatementOrigin$DEFAULT_STUB_CALL_TO_IMPLEMENTATION;", "useConstructorMarker", "", "generateSuperCallHandlerCheckIfNeeded", "", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "irFunction", "newIrFunction", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultArgumentStubGenerator extends DefaultArgumentStubGenerator<JvmBackendContext> {
    /* JADX WARN: Illegal instructions before constructor call */
    public JvmDefaultArgumentStubGenerator(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        JvmBackendContext jvmBackendContext = context;
        super(jvmBackendContext, new JvmDefaultArgumentFunctionFactory(jvmBackendContext), false, false, false, 16, null);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator
    protected DescriptorVisibility defaultArgumentStubVisibility(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return JvmIrUtilsKt.getJvmVisibilityOfDefaultArgumentStub(function);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator
    protected boolean useConstructorMarker(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return (function instanceof IrConstructor) || Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_CONSTRUCTOR.INSTANCE) || Intrinsics.areEqual(function.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator
    protected void generateSuperCallHandlerCheckIfNeeded(IrBlockBodyBuilder irBlockBodyBuilder, IrFunction irFunction, IrFunction newIrFunction) {
        Intrinsics.checkNotNullParameter(irBlockBodyBuilder, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(newIrFunction, "newIrFunction");
        if ((irFunction instanceof IrSimpleFunction) && getContext().getIr().shouldGenerateHandlerParameterForDefaultBodyFun() && !AdditionalIrUtilsKt.isTopLevelDeclaration(irFunction)) {
            IrDeclarationParent parent = irFunction.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass == null || !IrUtilsKt.isFinalClass(irClass)) {
                IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.last((List) newIrFunction.getValueParameters());
                IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
                IrType unitType = irBlockBodyBuilder.getContext().getIrBuiltIns().getUnitType();
                IrExpression irExpressionIrNot = LowerUtilsKt.irNot(irBlockBodyBuilder2, ExpressionHelpersKt.irEqualsNull(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter)));
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) irBlockBodyBuilder2, getContext().getIr().getSymbols().getThrowUnsupportedOperationException());
                irCall.putValueArgument(0, ExpressionHelpersKt.irString(irBlockBodyBuilder2, "Super calls with default arguments not supported in this target, function: " + irFunction.getName().asString()));
                Unit unit = Unit.INSTANCE;
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irIfThen$default(irBlockBodyBuilder2, unitType, irExpressionIrNot, irCall, null, 8, null));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.lower.DefaultArgumentStubGenerator
    public JvmLoweredStatementOrigin.DEFAULT_STUB_CALL_TO_IMPLEMENTATION getOriginForCallToImplementation() {
        return JvmLoweredStatementOrigin.DEFAULT_STUB_CALL_TO_IMPLEMENTATION.INSTANCE;
    }
}
