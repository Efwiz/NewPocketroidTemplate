package org.jetbrains.kotlin.backend.common.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.BackendContext;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.types.IrType;

/* JADX INFO: compiled from: AnnotationImplementationTransformer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001BP\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012)\u0010\n\u001a%\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u000b¢\u0006\u0002\b\u000f¢\u0006\u0002\u0010\u0010J$\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\r2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ \u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001b2\u0006\u0010 \u001a\u00020\u000eH\u0014J\f\u0010!\u001a\u00020\u0007*\u00020\u0005H\u0016J\u0014\u0010\"\u001a\u00020\u000e*\u00020\u001e2\u0006\u0010#\u001a\u00020$H\u0014R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R4\u0010\n\u001a%\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u000b¢\u0006\u0002\b\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/AnnotationImplementationMemberGenerator;", "Lorg/jetbrains/kotlin/backend/common/lower/LoweringDataClassMemberGenerator;", "backendContext", "Lorg/jetbrains/kotlin/backend/common/BackendContext;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "nameForToString", "", "forbidDirectFieldAccess", "", "selectEquals", "Lkotlin/Function4;", "Lorg/jetbrains/kotlin/ir/builders/IrBlockBodyBuilder;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/backend/common/BackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Ljava/lang/String;ZLkotlin/jvm/functions/Function4;)V", "getNameForToString", "()Ljava/lang/String;", "getSelectEquals", "()Lkotlin/jvm/functions/Function4;", "generateEqualsUsingGetters", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "equalsFun", "typeForEquals", "properties", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getHashCodeOf", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "property", "irValue", "classNameForToString", "shiftResultOfHashCode", "irResultVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationImplementationMemberGenerator extends LoweringDataClassMemberGenerator {
    private final String nameForToString;
    private final Function4<IrBlockBodyBuilder, IrType, IrExpression, IrExpression, IrExpression> selectEquals;

    public final String getNameForToString() {
        return this.nameForToString;
    }

    public final Function4<IrBlockBodyBuilder, IrType, IrExpression, IrExpression, IrExpression> getSelectEquals() {
        return this.selectEquals;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AnnotationImplementationMemberGenerator(BackendContext backendContext, IrClass irClass, String nameForToString, boolean z, Function4<? super IrBlockBodyBuilder, ? super IrType, ? super IrExpression, ? super IrExpression, ? extends IrExpression> selectEquals) {
        super(backendContext, irClass, AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION(), z);
        Intrinsics.checkNotNullParameter(backendContext, "backendContext");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(nameForToString, "nameForToString");
        Intrinsics.checkNotNullParameter(selectEquals, "selectEquals");
        this.nameForToString = nameForToString;
        this.selectEquals = selectEquals;
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    public String classNameForToString(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return this.nameForToString;
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    protected IrExpression shiftResultOfHashCode(IrBuilderWithScope irBuilderWithScope, IrVariable irResultVar) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(irResultVar, "irResultVar");
        return ExpressionHelpersKt.irGet(irBuilderWithScope, irResultVar);
    }

    @Override // org.jetbrains.kotlin.ir.util.DataClassMembersGenerator
    protected IrExpression getHashCodeOf(IrBuilderWithScope builder, IrProperty property, IrExpression irValue) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(irValue, "irValue");
        IrExpression hashCodeOf = getHashCodeOf(builder, getType(property), irValue);
        IrType stringType = getBackendContext().getIrBuiltIns().getStringType();
        String string = property.getName().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return ExpressionHelpersKt.irCallOp(builder, builder.getContext().getIrBuiltIns().getIntXorSymbol(), builder.getContext().getIrBuiltIns().getIntType(), ExpressionHelpersKt.irCallOp(builder, builder.getContext().getIrBuiltIns().getIntTimesSymbol(), builder.getContext().getIrBuiltIns().getIntType(), getHashCodeOf(builder, stringType, ExpressionHelpersKt.irString(builder, string)), (24 & 8) != 0 ? null : ExpressionHelpersKt.irInt$default(builder, 127, null, 2, null), (24 & 16) != 0 ? null : null), (24 & 8) != 0 ? null : hashCodeOf, (24 & 16) != 0 ? null : null);
    }

    public final IrSimpleFunction generateEqualsUsingGetters(IrSimpleFunction equalsFun, IrType typeForEquals, List<? extends IrProperty> properties) {
        Intrinsics.checkNotNullParameter(equalsFun, "equalsFun");
        Intrinsics.checkNotNullParameter(typeForEquals, "typeForEquals");
        Intrinsics.checkNotNullParameter(properties, "properties");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(getBackendContext(), equalsFun.getSymbol(), -2, -2);
        IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset());
        IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irIfThenReturnFalse(irBlockBodyBuilder2, ExpressionHelpersKt.irNotIs(irBlockBodyBuilder2, generateEqualsUsingGetters$lambda$3$lambda$2$irOther(irBlockBodyBuilder, equalsFun), typeForEquals)));
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBodyBuilder, ExpressionHelpersKt.irAs(irBlockBodyBuilder2, generateEqualsUsingGetters$lambda$3$lambda$2$irOther(irBlockBodyBuilder, equalsFun), typeForEquals), "other_with_cast", null, false, null, 28, null);
        for (IrProperty irProperty : properties) {
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irIfThenReturnFalse(irBlockBodyBuilder2, LowerUtilsKt.irNot(irBlockBodyBuilder2, this.selectEquals.invoke(irBlockBodyBuilder, getType(irProperty), generateEqualsUsingGetters$lambda$3$lambda$2$get(irProperty, irBlockBodyBuilder, generateEqualsUsingGetters$lambda$3$lambda$2$irThis(irBlockBodyBuilder, equalsFun)), generateEqualsUsingGetters$lambda$3$lambda$2$get(irProperty, irBlockBodyBuilder, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, typeForEquals, irVariableIrTemporary$default.getSymbol()))))));
        }
        irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irReturnTrue(irBlockBodyBuilder2));
        equalsFun.setBody(irBlockBodyBuilder.getIrBlockBody());
        return equalsFun;
    }

    private static final IrGetValueImpl generateEqualsUsingGetters$lambda$3$lambda$2$irOther(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction) {
        return ExpressionHelpersKt.irGet(irBlockBodyBuilder, irSimpleFunction.getValueParameters().get(0));
    }

    private static final IrGetValueImpl generateEqualsUsingGetters$lambda$3$lambda$2$irThis(IrBlockBodyBuilder irBlockBodyBuilder, IrSimpleFunction irSimpleFunction) {
        IrValueParameter dispatchReceiverParameter = irSimpleFunction.getDispatchReceiverParameter();
        Intrinsics.checkNotNull(dispatchReceiverParameter);
        return ExpressionHelpersKt.irGet(irBlockBodyBuilder, dispatchReceiverParameter);
    }

    private static final IrFunctionAccessExpression generateEqualsUsingGetters$lambda$3$lambda$2$get(IrProperty irProperty, IrBlockBodyBuilder irBlockBodyBuilder, IrExpression irExpression) {
        IrSimpleFunction getter = irProperty.getGetter();
        Intrinsics.checkNotNull(getter);
        IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(irBlockBodyBuilder, getter);
        irFunctionAccessExpressionIrCall.setDispatchReceiver(irExpression);
        return irFunctionAccessExpressionIrCall;
    }
}
