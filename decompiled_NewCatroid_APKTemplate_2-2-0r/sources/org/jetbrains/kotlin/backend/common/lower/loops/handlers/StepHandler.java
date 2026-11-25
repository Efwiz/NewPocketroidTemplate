package org.jetbrains.kotlin.backend.common.lower.loops.handlers;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoBuilder;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UnsignedProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UtilsKt;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrStringConcatenationImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: StepHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\"\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0002H\u0016J,\u0010\u0011\u001a\u00020\u0012*\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0012H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/handlers/StepHandler;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoHandler;", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ProgressionType;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "visitor", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfoBuilder;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "expression", "data", "scopeOwner", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "matchIterable", "", "callGetProgressionLastElementIfNecessary", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "progressionType", "first", "last", "step", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StepHandler implements HeaderInfoHandler<IrCall, ProgressionType> {
    private final CommonBackendContext context;
    private final HeaderInfoBuilder visitor;

    /* JADX INFO: compiled from: StepHandler.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProgressionDirection.values().length];
            try {
                iArr[ProgressionDirection.INCREASING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProgressionDirection.DECREASING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProgressionDirection.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public StepHandler(CommonBackendContext context, HeaderInfoBuilder visitor) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        this.context = context;
        this.visitor = visitor;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public boolean matchIterable(IrCall expression) {
        IrType type;
        IrType type2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) owner.getValueParameters());
        if (irValueParameter != null && (type = irValueParameter.getType()) != null && (IrTypePredicatesKt.isInt(type) || IrTypePredicatesKt.isLong(type))) {
            List<IrClassSymbol> progressionClasses = this.context.getIr().getSymbols().getProgressionClasses();
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            if (CollectionsKt.contains(progressionClasses, (extensionReceiverParameter == null || (type2 = extensionReceiverParameter.getType()) == null) ? null : IrTypesKt.getClassOrNull(type2)) && Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName("kotlin.ranges.step"))) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:33:0x012d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x012f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:35:0x0131  */
    /* JADX WARN: Code duplicated, block: B:37:0x0170  */
    /* JADX WARN: Code duplicated, block: B:38:0x01b0  */
    /* JADX WARN: Code duplicated, block: B:40:0x0205  */
    /* JADX WARN: Code duplicated, block: B:42:0x020b  */
    /* JADX WARN: Code duplicated, block: B:44:0x0213  */
    /* JADX WARN: Code duplicated, block: B:45:0x023c  */
    /* JADX WARN: Code duplicated, block: B:47:0x026c  */
    /* JADX WARN: Code duplicated, block: B:50:0x02cc  */
    /* JADX WARN: Code duplicated, block: B:51:0x02df  */
    @Override // org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfoHandler
    public HeaderInfo build(IrCall expression, ProgressionType data, IrSymbol scopeOwner) {
        IrStatement irStatement;
        IrCall irCallInvoke;
        int i;
        char c;
        IrExpression irExpression;
        IrVariable irVariable;
        IrStatement irStatement2;
        IrVariable irVariable2;
        IrVariable irVariable3;
        List listListOfNotNull;
        IrGetValueImpl irGetValueImplIrGet;
        IrVariable irVariableCreateTmpVariable$default;
        DeclarationIrBuilder declarationIrBuilder;
        IrCall irCall;
        IrIfThenElseImpl irIfThenElseImplIrIfThen$default;
        IrGetValueImpl irGetValueImplIrGet2;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        final DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(this.context, scopeOwner, expression.getStartOffset(), expression.getEndOffset());
        IrExpression extensionReceiver = expression.getExtensionReceiver();
        Intrinsics.checkNotNull(extensionReceiver);
        Object objAccept = extensionReceiver.accept(this.visitor, null);
        ProgressionHeaderInfo progressionHeaderInfoRevertToLastInclusive = objAccept instanceof ProgressionHeaderInfo ? (ProgressionHeaderInfo) objAccept : null;
        if (progressionHeaderInfoRevertToLastInclusive == null) {
            return null;
        }
        if (!progressionHeaderInfoRevertToLastInclusive.getIsLastInclusive() && (progressionHeaderInfoRevertToLastInclusive = progressionHeaderInfoRevertToLastInclusive.revertToLastInclusive()) == null) {
            return null;
        }
        ProgressionHeaderInfo progressionHeaderInfo = progressionHeaderInfoRevertToLastInclusive;
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        if (UtilsKt.getConstLongValue(valueArgument) != null) {
            Long constLongValue = UtilsKt.getConstLongValue(progressionHeaderInfo.getStep());
            if (Intrinsics.areEqual(constLongValue != null ? Long.valueOf(Math.abs(constLongValue.longValue())) : null, UtilsKt.getConstLongValue(valueArgument))) {
                return progressionHeaderInfo;
            }
        }
        Pair pairCreateLoopTemporaryVariableIfNecessary$default = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, valueArgument, "stepArg", null, true, 4, null);
        IrVariable irVariable4 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default.component1();
        final IrExpression irExpression2 = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default.component2();
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(data.getStepClass());
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) MapsKt.getValue(declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getLessOrEqualFunByOperandType(), data.getStepClass().getSymbol());
        Function0<IrCall> function0 = new Function0<IrCall>() { // from class: org.jetbrains.kotlin.backend.common.lower.loops.handlers.StepHandler$build$1$throwIllegalStepExceptionCall$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrCall invoke() {
                DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilderCreateIrBuilder;
                IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, declarationIrBuilder2.getContext().getIrBuiltIns().getIllegalArgumentExceptionSymbol());
                DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilderCreateIrBuilder;
                IrExpression irExpression3 = irExpression2;
                DeclarationIrBuilder declarationIrBuilder4 = declarationIrBuilder3;
                IrStringConcatenationImpl irStringConcatenationImplIrConcat = ExpressionHelpersKt.irConcat(declarationIrBuilder4);
                IrStringConcatenationImpl irStringConcatenationImpl = irStringConcatenationImplIrConcat;
                IrExpressionsKt.addArgument(irStringConcatenationImpl, ExpressionHelpersKt.irString(declarationIrBuilder4, "Step must be positive, was: "));
                IrExpressionsKt.addArgument(irStringConcatenationImpl, IrUtilsKt.shallowCopy(irExpression3));
                IrExpressionsKt.addArgument(irStringConcatenationImpl, ExpressionHelpersKt.irString(declarationIrBuilder4, "."));
                irCall2.putValueArgument(0, irStringConcatenationImplIrConcat);
                return irCall2;
            }
        };
        Long constLongValue2 = UtilsKt.getConstLongValue(irExpression2);
        if (constLongValue2 == null) {
            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilderCreateIrBuilder;
            IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, irSimpleFunctionSymbol);
            irCall2.putValueArgument(0, IrUtilsKt.shallowCopy(irExpression2));
            irCall2.putValueArgument(1, data.zeroStepExpression(declarationIrBuilderCreateIrBuilder));
            irCallInvoke = ExpressionHelpersKt.irIfThen$default(declarationIrBuilder2, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getUnitType(), irCall2, function0.invoke(), null, 8, null);
        } else {
            if (constLongValue2.longValue() <= 0) {
                irCallInvoke = function0.invoke();
            } else {
                irStatement = null;
            }
            i = WhenMappings.$EnumSwitchMapping$0[progressionHeaderInfo.getDirection().ordinal()];
            if (i != 1) {
                c = 1;
                irExpression = irExpression2;
                irVariable = null;
                irStatement2 = null;
            } else if (i != 2) {
                c = 1;
                if (irVariable4 == null) {
                    irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilderCreateIrBuilder.getScope(), UtilsKt.negate(IrUtilsKt.shallowCopy(irExpression2)), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
                    irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, irVariableCreateTmpVariable$default);
                } else {
                    DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilderCreateIrBuilder;
                    IrVariable irVariable5 = irVariable4;
                    IrSetValueImpl irSetValueImplIrSet$default = ExpressionHelpersKt.irSet$default(declarationIrBuilder3, irVariable4.getSymbol(), UtilsKt.negate(ExpressionHelpersKt.irGet(declarationIrBuilder3, irVariable5)), (IrStatementOrigin) null, 4, (Object) null);
                    irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilder3, irVariable5);
                    irVariableCreateTmpVariable$default = irSetValueImplIrSet$default;
                }
                irStatement2 = irVariableCreateTmpVariable$default;
                irExpression = irGetValueImplIrGet;
                irVariable = null;
            } else {
                if (i == 3) {
                    throw new NoWhenBranchMatchedException();
                }
                Pair pairCreateLoopTemporaryVariableIfNecessary$default2 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, progressionHeaderInfo.getStep(), "nestedStep", null, false, 12, null);
                IrVariable irVariable6 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default2.component1();
                IrExpression irExpression3 = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default2.component2();
                declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
                irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol);
                irCall.putValueArgument(0, IrUtilsKt.shallowCopy(irExpression3));
                irCall.putValueArgument(1, data.zeroStepExpression(declarationIrBuilderCreateIrBuilder));
                if (irVariable4 == null) {
                    irIfThenElseImplIrIfThen$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilderCreateIrBuilder.getScope(), (IrExpression) ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, defaultType, irCall, UtilsKt.negate(IrUtilsKt.shallowCopy(irExpression2)), IrUtilsKt.shallowCopy(irExpression2), null, 16, null), "maybeNegatedStep", false, (IrDeclarationOrigin) null, (IrType) null, 28, (Object) null);
                    irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(declarationIrBuilder, irIfThenElseImplIrIfThen$default);
                } else {
                    IrVariable irVariable7 = irVariable4;
                    irIfThenElseImplIrIfThen$default = ExpressionHelpersKt.irIfThen$default(declarationIrBuilder, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getUnitType(), irCall, ExpressionHelpersKt.irSet$default(declarationIrBuilder, irVariable4.getSymbol(), UtilsKt.negate(ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable7)), (IrStatementOrigin) null, 4, (Object) null), null, 8, null);
                    irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable7);
                }
                irStatement2 = irIfThenElseImplIrIfThen$default;
                irExpression = irGetValueImplIrGet2;
                c = 1;
                irVariable = irVariable6;
            }
            Pair pairCreateLoopTemporaryVariableIfNecessary$default3 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, progressionHeaderInfo.getFirst(), "nestedFirst", null, false, 12, null);
            irVariable2 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default3.component1();
            IrExpression irExpression4 = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default3.component2();
            Pair pairCreateLoopTemporaryVariableIfNecessary$default4 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, progressionHeaderInfo.getLast(), "nestedLast", null, false, 12, null);
            irVariable3 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default4.component1();
            IrExpression irExpressionCallGetProgressionLastElementIfNecessary = callGetProgressionLastElementIfNecessary(declarationIrBuilderCreateIrBuilder, data, irExpression4, (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default4.component2(), irExpression);
            List<IrStatement> additionalStatements = progressionHeaderInfo.getAdditionalStatements();
            if (progressionHeaderInfo.getIsReversed()) {
                IrStatement[] irStatementArr = new IrStatement[6];
                irStatementArr[0] = irVariable3;
                irStatementArr[c] = irVariable2;
                irStatementArr[2] = irVariable;
                irStatementArr[3] = irVariable4;
                irStatementArr[4] = irStatement;
                irStatementArr[5] = irStatement2;
                listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) irStatementArr);
            } else {
                IrStatement[] irStatementArr2 = new IrStatement[6];
                irStatementArr2[0] = irVariable2;
                irStatementArr2[c] = irVariable3;
                irStatementArr2[r13] = irVariable;
                irStatementArr2[r14] = irVariable4;
                irStatementArr2[4] = irStatement;
                irStatementArr2[5] = irStatement2;
                listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) irStatementArr2);
            }
            return new ProgressionHeaderInfo(data, IrUtilsKt.shallowCopy(irExpression4), irExpressionCallGetProgressionLastElementIfNecessary, IrUtilsKt.shallowCopy(irExpression), false, progressionHeaderInfo.getIsReversed(), null, progressionHeaderInfo.getDirection(), CollectionsKt.plus((Collection) additionalStatements, (Iterable) listListOfNotNull), null, 592, null);
        }
        irStatement = irCallInvoke;
        i = WhenMappings.$EnumSwitchMapping$0[progressionHeaderInfo.getDirection().ordinal()];
        if (i != 1) {
            c = 1;
            irExpression = irExpression2;
            irVariable = null;
            irStatement2 = null;
        } else if (i != 2) {
            c = 1;
            if (irVariable4 == null) {
                irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilderCreateIrBuilder.getScope(), UtilsKt.negate(IrUtilsKt.shallowCopy(irExpression2)), (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
                irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilderCreateIrBuilder, irVariableCreateTmpVariable$default);
            } else {
                DeclarationIrBuilder declarationIrBuilder4 = declarationIrBuilderCreateIrBuilder;
                IrVariable irVariable8 = irVariable4;
                IrSetValueImpl irSetValueImplIrSet$default2 = ExpressionHelpersKt.irSet$default(declarationIrBuilder4, irVariable4.getSymbol(), UtilsKt.negate(ExpressionHelpersKt.irGet(declarationIrBuilder4, irVariable8)), (IrStatementOrigin) null, 4, (Object) null);
                irGetValueImplIrGet = ExpressionHelpersKt.irGet(declarationIrBuilder4, irVariable8);
                irVariableCreateTmpVariable$default = irSetValueImplIrSet$default2;
            }
            irStatement2 = irVariableCreateTmpVariable$default;
            irExpression = irGetValueImplIrGet;
            irVariable = null;
        } else {
            if (i == 3) {
                throw new NoWhenBranchMatchedException();
            }
            Pair pairCreateLoopTemporaryVariableIfNecessary$default5 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, progressionHeaderInfo.getStep(), "nestedStep", null, false, 12, null);
            IrVariable irVariable9 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default5.component1();
            IrExpression irExpression5 = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default5.component2();
            declarationIrBuilder = declarationIrBuilderCreateIrBuilder;
            irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol);
            irCall.putValueArgument(0, IrUtilsKt.shallowCopy(irExpression5));
            irCall.putValueArgument(1, data.zeroStepExpression(declarationIrBuilderCreateIrBuilder));
            if (irVariable4 == null) {
                irIfThenElseImplIrIfThen$default = IrBuilderKt.createTmpVariable$default(declarationIrBuilderCreateIrBuilder.getScope(), (IrExpression) ExpressionHelpersKt.irIfThenElse$default(declarationIrBuilder, defaultType, irCall, UtilsKt.negate(IrUtilsKt.shallowCopy(irExpression2)), IrUtilsKt.shallowCopy(irExpression2), null, 16, null), "maybeNegatedStep", false, (IrDeclarationOrigin) null, (IrType) null, 28, (Object) null);
                irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(declarationIrBuilder, irIfThenElseImplIrIfThen$default);
            } else {
                IrVariable irVariable10 = irVariable4;
                irIfThenElseImplIrIfThen$default = ExpressionHelpersKt.irIfThen$default(declarationIrBuilder, declarationIrBuilderCreateIrBuilder.getContext().getIrBuiltIns().getUnitType(), irCall, ExpressionHelpersKt.irSet$default(declarationIrBuilder, irVariable4.getSymbol(), UtilsKt.negate(ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable10)), (IrStatementOrigin) null, 4, (Object) null), null, 8, null);
                irGetValueImplIrGet2 = ExpressionHelpersKt.irGet(declarationIrBuilder, irVariable10);
            }
            irStatement2 = irIfThenElseImplIrIfThen$default;
            irExpression = irGetValueImplIrGet2;
            c = 1;
            irVariable = irVariable9;
        }
        Pair pairCreateLoopTemporaryVariableIfNecessary$default6 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, progressionHeaderInfo.getFirst(), "nestedFirst", null, false, 12, null);
        irVariable2 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default6.component1();
        IrExpression irExpression6 = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default6.component2();
        Pair pairCreateLoopTemporaryVariableIfNecessary$default7 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(declarationIrBuilderCreateIrBuilder, progressionHeaderInfo.getLast(), "nestedLast", null, false, 12, null);
        irVariable3 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default7.component1();
        IrExpression irExpressionCallGetProgressionLastElementIfNecessary2 = callGetProgressionLastElementIfNecessary(declarationIrBuilderCreateIrBuilder, data, irExpression6, (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default7.component2(), irExpression);
        List<IrStatement> additionalStatements2 = progressionHeaderInfo.getAdditionalStatements();
        if (progressionHeaderInfo.getIsReversed()) {
            IrStatement[] irStatementArr3 = new IrStatement[6];
            irStatementArr3[0] = irVariable3;
            irStatementArr3[c] = irVariable2;
            irStatementArr3[2] = irVariable;
            irStatementArr3[3] = irVariable4;
            irStatementArr3[4] = irStatement;
            irStatementArr3[5] = irStatement2;
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) irStatementArr3);
        } else {
            IrStatement[] irStatementArr4 = new IrStatement[6];
            irStatementArr4[0] = irVariable2;
            irStatementArr4[c] = irVariable3;
            irStatementArr4[r13] = irVariable;
            irStatementArr4[r14] = irVariable4;
            irStatementArr4[4] = irStatement;
            irStatementArr4[5] = irStatement2;
            listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) irStatementArr4);
        }
        return new ProgressionHeaderInfo(data, IrUtilsKt.shallowCopy(irExpression6), irExpressionCallGetProgressionLastElementIfNecessary2, IrUtilsKt.shallowCopy(irExpression), false, progressionHeaderInfo.getIsReversed(), null, progressionHeaderInfo.getDirection(), CollectionsKt.plus((Collection) additionalStatements2, (Iterable) listListOfNotNull), null, 592, null);
    }

    private final IrExpression callGetProgressionLastElementIfNecessary(DeclarationIrBuilder declarationIrBuilder, ProgressionType progressionType, IrExpression irExpression, IrExpression irExpression2, IrExpression irExpression3) {
        Long constLongValue = UtilsKt.getConstLongValue(irExpression3);
        if (constLongValue != null && Math.abs(constLongValue.longValue()) == 1) {
            return IrUtilsKt.shallowCopy(irExpression2);
        }
        IrSimpleFunctionSymbol getProgressionLastElementFunction = progressionType.getGetProgressionLastElementFunction();
        if (getProgressionLastElementFunction == null) {
            throw new IllegalStateException(("No `getProgressionLastElement` for progression type " + Reflection.getOrCreateKotlinClass(progressionType.getClass()).getSimpleName()).toString());
        }
        if (progressionType instanceof UnsignedProgressionType) {
            UnsignedProgressionType unsignedProgressionType = (UnsignedProgressionType) progressionType;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, getProgressionLastElementFunction);
            irCall.putValueArgument(0, unsignedProgressionType.asUnsigned(progressionType.asElementType(IrUtilsKt.shallowCopy(irExpression))));
            irCall.putValueArgument(1, unsignedProgressionType.asUnsigned(progressionType.asElementType(IrUtilsKt.shallowCopy(irExpression2))));
            irCall.putValueArgument(2, progressionType.asStepType(IrUtilsKt.shallowCopy(irExpression3)));
            return unsignedProgressionType.asSigned(irCall);
        }
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, getProgressionLastElementFunction);
        irCall2.putValueArgument(0, progressionType.asStepType(IrUtilsKt.shallowCopy(irExpression)));
        irCall2.putValueArgument(1, progressionType.asStepType(IrUtilsKt.shallowCopy(irExpression2)));
        irCall2.putValueArgument(2, progressionType.asStepType(IrUtilsKt.shallowCopy(irExpression3)));
        return irCall2;
    }
}
