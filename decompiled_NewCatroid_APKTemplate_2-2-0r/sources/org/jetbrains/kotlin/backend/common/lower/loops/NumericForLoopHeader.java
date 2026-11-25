package org.jetbrains.kotlin.backend.common.lower.loops;

import androidx.exifinterface.media.ExifInterface;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.loops.NumericHeaderInfo;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbols;
import org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper;
import org.jetbrains.kotlin.ir.util.DeepCopyTypeRemapper;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.PatchDeclarationParentsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: NumericForLoopHeader.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u001d\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010%\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0006H\u0004J\u0010\u0010&\u001a\u00020'2\u0006\u0010\u0005\u001a\u00020\u0006H\u0004J\u0014\u0010(\u001a\u00020\u0018*\u00020\u00062\u0006\u0010)\u001a\u00020\u0018H\u0002R\u0014\u0010\n\u001a\u00020\u000bX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0007\u001a\u00020\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\u0017\u001a\u00020\u00188DX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u0014X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0011\u0010\u001d\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u0016\u0010\u001f\u001a\u0004\u0018\u00010\u0014X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u0014\u0010!\u001a\u00020\"X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericForLoopHeader;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/ForLoopHeader;", "headerInfo", "builder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "consumesLoopVariableComponents", "", "getConsumesLoopVariableComponents", "()Z", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "getHeaderInfo", "()Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", "Lorg/jetbrains/kotlin/backend/common/lower/loops/NumericHeaderInfo;", HeaderProcessorKt.inductionVariableName, "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getInductionVariable", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "lastExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getLastExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "lastVariableIfCanCacheLast", "getLastVariableIfCanCacheLast", "stepExpression", "getStepExpression", "stepVariable", "getStepVariable", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "buildLoopCondition", "incrementInductionVariable", "Lorg/jetbrains/kotlin/ir/IrStatement;", "ensureNotNullable", "expression", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NumericForLoopHeader<T extends NumericHeaderInfo> implements ForLoopHeader {
    private final boolean consumesLoopVariableComponents;
    private final CommonBackendContext context;
    private final T headerInfo;
    private final IrVariable inductionVariable;
    private final IrExpression lastExpression;
    private final IrVariable lastVariableIfCanCacheLast;
    private final IrExpression stepExpression;
    private final IrVariable stepVariable;
    private final Symbols symbols;

    /* JADX INFO: compiled from: NumericForLoopHeader.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProgressionDirection.values().length];
            try {
                iArr[ProgressionDirection.DECREASING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProgressionDirection.INCREASING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProgressionDirection.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public NumericForLoopHeader(T headerInfo, DeclarationIrBuilder builder, CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(headerInfo, "headerInfo");
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(context, "context");
        this.headerInfo = headerInfo;
        this.context = context;
        this.symbols = context.getIr().getSymbols();
        ProgressionType progressionType = headerInfo.getProgressionType();
        this.inductionVariable = IrBuilderKt.createTmpVariable(builder.getScope(), progressionType.asElementType(headerInfo.getFirst()), HeaderProcessorKt.inductionVariableName, true, context.getInductionVariableOrigin(), (IrType) IrUtilsKt.getDefaultType(progressionType.getElementClass()));
        IrExpression irExpressionAsElementType = progressionType.asElementType(headerInfo.getLast());
        if (headerInfo.getCanCacheLast()) {
            Pair pairCreateLoopTemporaryVariableIfNecessary$default = UtilsKt.createLoopTemporaryVariableIfNecessary$default(builder, irExpressionAsElementType, "last", null, false, 12, null);
            IrVariable irVariable = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default.component1();
            IrExpression irExpression = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default.component2();
            this.lastVariableIfCanCacheLast = irVariable;
            this.lastExpression = IrUtilsKt.shallowCopy(irExpression);
        } else {
            this.lastVariableIfCanCacheLast = null;
            this.lastExpression = irExpressionAsElementType;
        }
        Pair pairCreateLoopTemporaryVariableIfNecessary$default2 = UtilsKt.createLoopTemporaryVariableIfNecessary$default(builder, ensureNotNullable(builder, progressionType.asStepType(headerInfo.getStep())), "step", IrUtilsKt.getDefaultType(progressionType.getStepClass()), false, 8, null);
        IrVariable irVariable2 = (IrVariable) pairCreateLoopTemporaryVariableIfNecessary$default2.component1();
        IrExpression irExpression2 = (IrExpression) pairCreateLoopTemporaryVariableIfNecessary$default2.component2();
        this.stepVariable = irVariable2;
        this.stepExpression = irExpression2;
    }

    public final T getHeaderInfo() {
        return this.headerInfo;
    }

    protected final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.lower.loops.ForLoopHeader
    public boolean getConsumesLoopVariableComponents() {
        return this.consumesLoopVariableComponents;
    }

    public final IrVariable getInductionVariable() {
        return this.inductionVariable;
    }

    protected final IrVariable getStepVariable() {
        return this.stepVariable;
    }

    public final IrExpression getStepExpression() {
        return this.stepExpression;
    }

    protected final IrVariable getLastVariableIfCanCacheLast() {
        return this.lastVariableIfCanCacheLast;
    }

    protected final IrExpression getLastExpression() {
        IrExpression irExpressionShallowCopyOrNull = IrUtilsKt.shallowCopyOrNull(this.lastExpression);
        if (irExpressionShallowCopyOrNull != null) {
            return irExpressionShallowCopyOrNull;
        }
        IrExpression irExpression = this.lastExpression;
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
        IrVisitorsKt.acceptVoid(irExpression, deepCopySymbolRemapper);
        DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irExpression.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), null);
        if (irElementPatchDeclarationParents != null) {
            return (IrExpression) irElementPatchDeclarationParents;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
    }

    protected final Symbols getSymbols() {
        return this.symbols;
    }

    private final IrExpression ensureNotNullable(DeclarationIrBuilder declarationIrBuilder, IrExpression irExpression) {
        return ((irExpression.getType() instanceof IrSimpleType) && IrTypeUtilsKt.isNullable(irExpression.getType())) ? ExpressionHelpersKt.irImplicitCast(declarationIrBuilder, irExpression, IrTypesKt.makeNotNull(irExpression.getType())) : irExpression;
    }

    protected final IrStatement incrementInductionVariable(DeclarationIrBuilder builder) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        ProgressionType progressionType = this.headerInfo.getProgressionType();
        IrSimpleType defaultType = IrUtilsKt.getDefaultType(progressionType.getStepClass());
        IrClass irClass = IrTypesKt.getClass(IrUtilsKt.getDefaultType(progressionType.getElementClass()));
        Intrinsics.checkNotNull(irClass);
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
            if (Intrinsics.areEqual(irSimpleFunction3.getName(), OperatorNameConventions.PLUS) && irSimpleFunction3.getValueParameters().size() == 1 && Intrinsics.areEqual(irSimpleFunction3.getValueParameters().get(0).getType(), defaultType)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunction = irSimpleFunction2;
                z = true;
            }
        }
        if (z) {
            IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
            DeclarationIrBuilder declarationIrBuilder = builder;
            return ExpressionHelpersKt.irSet(declarationIrBuilder, this.inductionVariable.getSymbol(), ExpressionHelpersKt.irCallOp(declarationIrBuilder, irSimpleFunction4.getSymbol(), irSimpleFunction4.getReturnType(), ExpressionHelpersKt.irGet(declarationIrBuilder, this.inductionVariable), IrUtilsKt.shallowCopy(this.stepExpression), IrStatementOrigin.PLUSEQ.INSTANCE), IrStatementOrigin.PLUSEQ.INSTANCE);
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    protected final IrExpression buildLoopCondition(DeclarationIrBuilder builder) {
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2;
        Intrinsics.checkNotNullParameter(builder, "builder");
        ProgressionType progressionType = this.headerInfo.getProgressionType();
        IrBuiltIns irBuiltIns = builder.getContext().getIrBuiltIns();
        if (this.headerInfo.getIsLastInclusive()) {
            irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) MapsKt.getValue(irBuiltIns.getLessOrEqualFunByOperandType(), irBuiltIns.getIntClass());
        } else {
            irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) MapsKt.getValue(irBuiltIns.getLessFunByOperandType(), irBuiltIns.getIntClass());
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = irSimpleFunctionSymbol;
        IrSimpleFunction irSimpleFunction = null;
        if (progressionType instanceof UnsignedProgressionType) {
            UnsignedProgressionType unsignedProgressionType = (UnsignedProgressionType) progressionType;
            IrClass irClass = IrTypesKt.getClass(unsignedProgressionType.getUnsignedType());
            Intrinsics.checkNotNull(irClass);
            boolean z = false;
            for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
                IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
                if (Intrinsics.areEqual(irSimpleFunction3.getName(), OperatorNameConventions.COMPARE_TO) && irSimpleFunction3.getDispatchReceiverParameter() != null && irSimpleFunction3.getExtensionReceiverParameter() == null && irSimpleFunction3.getValueParameters().size() == 1 && Intrinsics.areEqual(irSimpleFunction3.getValueParameters().get(0).getType(), unsignedProgressionType.getUnsignedType())) {
                    if (z) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunction = irSimpleFunction2;
                    z = true;
                }
            }
            if (!z) {
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            irSimpleFunction = irSimpleFunction;
        }
        IrSimpleFunction irSimpleFunction4 = irSimpleFunction;
        if (this.headerInfo.getIsLastInclusive()) {
            irSimpleFunctionSymbol2 = irBuiltIns.getLessOrEqualFunByOperandType().get(progressionType.getElementClass().getSymbol());
        } else {
            irSimpleFunctionSymbol2 = irBuiltIns.getLessFunByOperandType().get(progressionType.getElementClass().getSymbol());
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol4 = irSimpleFunctionSymbol2;
        int i = WhenMappings.$EnumSwitchMapping$0[this.headerInfo.getDirection().ordinal()];
        if (i == 1) {
            return buildLoopCondition$lambda$15$lambda$14$conditionForDecreasing(progressionType, builder, irSimpleFunctionSymbol3, irSimpleFunctionSymbol4, irSimpleFunction4, this);
        }
        if (i == 2) {
            return buildLoopCondition$lambda$15$lambda$14$conditionForIncreasing(progressionType, builder, irSimpleFunctionSymbol3, irSimpleFunctionSymbol4, irSimpleFunction4, this);
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        IrGeneratorContext context = builder.getContext();
        IrGeneratorContext context2 = builder.getContext();
        DeclarationIrBuilder declarationIrBuilder = builder;
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, (IrSimpleFunctionSymbol) MapsKt.getValue(irBuiltIns.getGreaterFunByOperandType(), progressionType.getStepClass().getSymbol()));
        irCall.putValueArgument(0, IrUtilsKt.shallowCopy(this.stepExpression));
        irCall.putValueArgument(1, progressionType.zeroStepExpression(builder));
        Unit unit = Unit.INSTANCE;
        IrWhen irWhenAndand$default = PrimitivesKt.andand$default(context2, irCall, buildLoopCondition$lambda$15$lambda$14$conditionForIncreasing(progressionType, builder, irSimpleFunctionSymbol3, irSimpleFunctionSymbol4, irSimpleFunction4, this), null, 4, null);
        IrGeneratorContext context3 = builder.getContext();
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, (IrSimpleFunctionSymbol) MapsKt.getValue(irBuiltIns.getLessFunByOperandType(), progressionType.getStepClass().getSymbol()));
        irCall2.putValueArgument(0, IrUtilsKt.shallowCopy(this.stepExpression));
        irCall2.putValueArgument(1, progressionType.zeroStepExpression(builder));
        Unit unit2 = Unit.INSTANCE;
        return PrimitivesKt.oror$default(context, irWhenAndand$default, PrimitivesKt.andand$default(context3, irCall2, buildLoopCondition$lambda$15$lambda$14$conditionForDecreasing(progressionType, builder, irSimpleFunctionSymbol3, irSimpleFunctionSymbol4, irSimpleFunction4, this), null, 4, null), null, 4, null);
    }

    private static final <T extends NumericHeaderInfo> IrExpression buildLoopCondition$lambda$15$lambda$14$conditionForDecreasing(ProgressionType progressionType, DeclarationIrBuilder declarationIrBuilder, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrSimpleFunction irSimpleFunction, NumericForLoopHeader<T> numericForLoopHeader) {
        if (progressionType instanceof UnsignedProgressionType) {
            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, irSimpleFunctionSymbol);
            Intrinsics.checkNotNull(irSimpleFunction);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder2, irSimpleFunction);
            UnsignedProgressionType unsignedProgressionType = (UnsignedProgressionType) progressionType;
            irFunctionAccessExpressionIrCall.setDispatchReceiver(unsignedProgressionType.asUnsigned(numericForLoopHeader.getLastExpression()));
            irFunctionAccessExpressionIrCall.putValueArgument(0, unsignedProgressionType.asUnsigned(ExpressionHelpersKt.irGet(declarationIrBuilder2, ((NumericForLoopHeader) numericForLoopHeader).inductionVariable)));
            Unit unit = Unit.INSTANCE;
            irCall.putValueArgument(0, irFunctionAccessExpressionIrCall);
            irCall.putValueArgument(1, ExpressionHelpersKt.irInt$default(declarationIrBuilder2, 0, null, 2, null));
            return irCall;
        }
        DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilder;
        Intrinsics.checkNotNull(irSimpleFunctionSymbol2);
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder3, irSimpleFunctionSymbol2);
        irCall2.putValueArgument(0, numericForLoopHeader.getLastExpression());
        irCall2.putValueArgument(1, ExpressionHelpersKt.irGet(declarationIrBuilder3, ((NumericForLoopHeader) numericForLoopHeader).inductionVariable));
        return irCall2;
    }

    private static final <T extends NumericHeaderInfo> IrExpression buildLoopCondition$lambda$15$lambda$14$conditionForIncreasing(ProgressionType progressionType, DeclarationIrBuilder declarationIrBuilder, IrSimpleFunctionSymbol irSimpleFunctionSymbol, IrSimpleFunctionSymbol irSimpleFunctionSymbol2, IrSimpleFunction irSimpleFunction, NumericForLoopHeader<T> numericForLoopHeader) {
        if (progressionType instanceof UnsignedProgressionType) {
            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilder;
            IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, irSimpleFunctionSymbol);
            Intrinsics.checkNotNull(irSimpleFunction);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder2, irSimpleFunction);
            UnsignedProgressionType unsignedProgressionType = (UnsignedProgressionType) progressionType;
            irFunctionAccessExpressionIrCall.setDispatchReceiver(unsignedProgressionType.asUnsigned(ExpressionHelpersKt.irGet(declarationIrBuilder2, ((NumericForLoopHeader) numericForLoopHeader).inductionVariable)));
            irFunctionAccessExpressionIrCall.putValueArgument(0, unsignedProgressionType.asUnsigned(numericForLoopHeader.getLastExpression()));
            Unit unit = Unit.INSTANCE;
            irCall.putValueArgument(0, irFunctionAccessExpressionIrCall);
            irCall.putValueArgument(1, ExpressionHelpersKt.irInt$default(declarationIrBuilder2, 0, null, 2, null));
            return irCall;
        }
        DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilder;
        Intrinsics.checkNotNull(irSimpleFunctionSymbol2);
        IrCall irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder3, irSimpleFunctionSymbol2);
        irCall2.putValueArgument(0, ExpressionHelpersKt.irGet(declarationIrBuilder3, ((NumericForLoopHeader) numericForLoopHeader).inductionVariable));
        irCall2.putValueArgument(1, numericForLoopHeader.getLastExpression());
        return irCall2;
    }
}
