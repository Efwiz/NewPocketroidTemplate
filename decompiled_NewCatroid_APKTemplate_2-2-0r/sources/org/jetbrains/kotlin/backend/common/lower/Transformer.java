package org.jetbrains.kotlin.backend.common.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ScopeWithIr;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.backend.common.lower.loops.ComparableRangeInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.FloatingPointRangeHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.HeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.IndexedGetHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.NumericHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionDirection;
import org.jetbrains.kotlin.backend.common.lower.loops.ProgressionHeaderInfo;
import org.jetbrains.kotlin.backend.common.lower.loops.UnsignedProgressionType;
import org.jetbrains.kotlin.backend.common.lower.loops.UtilsKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.IrGeneratorContext;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.builders.Scope;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: RangeContainsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J*\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\"\u0010\u0017\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J&\u0010 \u001a\u0004\u0018\u00010\u001f*\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020&H\u0002J\u000e\u0010'\u001a\u00020\u001b*\u0004\u0018\u00010(H\u0002J\u000e\u0010)\u001a\u00020\u001b*\u0004\u0018\u00010*H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/Transformer;", "Lorg/jetbrains/kotlin/backend/common/IrElementTransformerVoidWithContext;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "headerInfoBuilder", "Lorg/jetbrains/kotlin/backend/common/lower/RangeHeaderInfoBuilder;", "computeComparisonClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "symbols", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "lowerType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "upperType", "argumentType", "getScopeOwnerSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "leastCommonPrimitiveNumericType", "t1", "t2", "matchStdlibExtensionContainsCall", "", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "buildContainsComparison", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "headerInfo", "Lorg/jetbrains/kotlin/backend/common/lower/loops/HeaderInfo;", "argument", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "isPromotableToInt", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "isPromotableToUInt", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Transformer extends IrElementTransformerVoidWithContext {
    private final IrSymbolOwner container;
    private final CommonBackendContext context;
    private final RangeHeaderInfoBuilder headerInfoBuilder;

    /* JADX INFO: compiled from: RangeContainsLowering.kt */
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

    public final CommonBackendContext getContext() {
        return this.context;
    }

    public final IrSymbolOwner getContainer() {
        return this.container;
    }

    public Transformer(CommonBackendContext context, IrSymbolOwner container) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(container, "container");
        this.context = context;
        this.container = container;
        this.headerInfoBuilder = new RangeHeaderInfoBuilder(context, new Transformer$headerInfoBuilder$1(this));
    }

    public final IrSymbol getScopeOwnerSymbol() {
        Scope scope;
        IrSymbol scopeOwnerSymbol;
        ScopeWithIr currentScope = getCurrentScope();
        return (currentScope == null || (scope = currentScope.getScope()) == null || (scopeOwnerSymbol = scope.getScopeOwnerSymbol()) == null) ? this.container.getSymbol() : scopeOwnerSymbol;
    }

    private final boolean matchStdlibExtensionContainsCall(IrCall expression) {
        IrValueParameter extensionReceiverParameter;
        IrType type;
        IrSimpleFunction owner = expression.getSymbol().getOwner();
        return owner.getValueParameters().size() == 1 && (extensionReceiverParameter = owner.getExtensionReceiverParameter()) != null && (type = extensionReceiverParameter.getType()) != null && IrTypeUtilsKt.isSubtypeOfClass(type, this.context.getIr().getSymbols().getClosedRange()) && Intrinsics.areEqual(AdditionalIrUtilsKt.getKotlinFqName(owner), new FqName(new StringBuilder("kotlin.ranges.").append(OperatorNameConventions.CONTAINS).toString()));
    }

    @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
    public IrExpression visitCall(IrCall expression) {
        HeaderInfo headerInfo;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrStatementOrigin origin = expression.getOrigin();
        if (!Intrinsics.areEqual(origin, IrStatementOrigin.IN.INSTANCE) && !Intrinsics.areEqual(origin, IrStatementOrigin.NOT_IN.INSTANCE)) {
            return super.visitCall(expression);
        }
        if (Intrinsics.areEqual(origin, IrStatementOrigin.NOT_IN.INSTANCE) && Intrinsics.areEqual(expression.getSymbol(), this.context.getIrBuiltIns().getBooleanNotSymbol())) {
            return super.visitCall(expression);
        }
        if (expression.getExtensionReceiver() != null && !matchStdlibExtensionContainsCall(expression)) {
            return super.visitCall(expression);
        }
        IrExpression dispatchReceiver = expression.getDispatchReceiver();
        if (dispatchReceiver == null) {
            dispatchReceiver = expression.getExtensionReceiver();
        }
        if (dispatchReceiver == null || (headerInfo = (HeaderInfo) dispatchReceiver.accept(this.headerInfoBuilder, expression)) == null) {
            return super.visitCall(expression);
        }
        IrExpression valueArgument = expression.getValueArgument(0);
        Intrinsics.checkNotNull(valueArgument);
        if (IrTypeUtilsKt.isNullable(valueArgument.getType())) {
            return super.visitCall(expression);
        }
        IrExpression irExpressionBuildContainsComparison = buildContainsComparison(LowerUtilsKt.createIrBuilder(this.context, getScopeOwnerSymbol(), expression.getStartOffset(), expression.getEndOffset()), headerInfo, valueArgument, origin);
        return irExpressionBuildContainsComparison == null ? super.visitCall(expression) : irExpressionBuildContainsComparison;
    }

    private final IrExpression buildContainsComparison(DeclarationIrBuilder declarationIrBuilder, HeaderInfo headerInfo, IrExpression irExpression, IrStatementOrigin irStatementOrigin) {
        IrExpression endInclusive;
        IrExpression irExpressionShallowCopy;
        boolean isLastInclusive;
        boolean z;
        boolean z2;
        boolean z3;
        IrClass owner;
        IrVariable irVariable;
        boolean z4;
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> lessFunByOperandType;
        int i;
        IrCall irCall;
        IrCall irCall2;
        IrExpression first;
        IrExpression last;
        boolean isReversed;
        ArrayList arrayList = new ArrayList();
        if (headerInfo instanceof NumericHeaderInfo) {
            NumericHeaderInfo numericHeaderInfo = (NumericHeaderInfo) headerInfo;
            if (numericHeaderInfo instanceof ProgressionHeaderInfo) {
                arrayList.addAll(((ProgressionHeaderInfo) headerInfo).getAdditionalStatements());
            } else if (numericHeaderInfo instanceof IndexedGetHeaderInfo) {
                throw new IllegalStateException("Unexpected IndexedGetHeaderInfo returned by RangeHeaderInfoBuilder".toString());
            }
            int i2 = WhenMappings.$EnumSwitchMapping$0[numericHeaderInfo.getDirection().ordinal()];
            if (i2 == 1) {
                Long constLongValue = UtilsKt.getConstLongValue(numericHeaderInfo.getStep());
                if (constLongValue == null || constLongValue.longValue() != 1) {
                    return null;
                }
                first = numericHeaderInfo.getFirst();
                last = numericHeaderInfo.getLast();
                isReversed = numericHeaderInfo.getIsReversed();
            } else {
                if (i2 != 2) {
                    if (i2 == 3) {
                        return null;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                Long constLongValue2 = UtilsKt.getConstLongValue(numericHeaderInfo.getStep());
                if (constLongValue2 == null || constLongValue2.longValue() != -1) {
                    return null;
                }
                first = numericHeaderInfo.getLast();
                last = numericHeaderInfo.getFirst();
                isReversed = !numericHeaderInfo.getIsReversed();
            }
            boolean z5 = numericHeaderInfo.getProgressionType() instanceof UnsignedProgressionType;
            isLastInclusive = numericHeaderInfo.getIsLastInclusive();
            irExpressionShallowCopy = first;
            endInclusive = last;
            z2 = isReversed;
            z = z5;
            z3 = true;
        } else if (headerInfo instanceof FloatingPointRangeHeaderInfo) {
            FloatingPointRangeHeaderInfo floatingPointRangeHeaderInfo = (FloatingPointRangeHeaderInfo) headerInfo;
            IrExpression start = floatingPointRangeHeaderInfo.getStart();
            endInclusive = floatingPointRangeHeaderInfo.getEndInclusive();
            irExpressionShallowCopy = start;
            isLastInclusive = true;
            z3 = true;
            z2 = false;
            z = false;
        } else {
            if (!(headerInfo instanceof ComparableRangeInfo)) {
                return null;
            }
            ComparableRangeInfo comparableRangeInfo = (ComparableRangeInfo) headerInfo;
            IrExpression start2 = comparableRangeInfo.getStart();
            endInclusive = comparableRangeInfo.getEndInclusive();
            irExpressionShallowCopy = start2;
            isLastInclusive = true;
            z = true;
            z2 = false;
            z3 = false;
        }
        IrBuiltIns irBuiltIns = declarationIrBuilder.getContext().getIrBuiltIns();
        if (z3) {
            owner = computeComparisonClass(this.context.getIr().getSymbols(), irExpressionShallowCopy.getType(), endInclusive.getType(), irExpression.getType());
            if (owner == null) {
                return null;
            }
        } else {
            boolean z6 = headerInfo instanceof ComparableRangeInfo;
            owner = this.context.getIr().getSymbols().getComparable().getOwner();
        }
        IrClass irClass = owner;
        IrExpression irExpressionCastIfNecessary = irExpression;
        if (z3) {
            irExpressionCastIfNecessary = UtilsKt.castIfNecessary(irExpressionCastIfNecessary, irClass);
        }
        Pair pairCreateTemporaryVariableIfNecessary$default = UtilsKt.createTemporaryVariableIfNecessary$default(declarationIrBuilder, irExpressionCastIfNecessary, "containsArg", null, false, 12, null);
        IrVariable irVariable2 = (IrVariable) pairCreateTemporaryVariableIfNecessary$default.component1();
        IrExpression irExpression2 = (IrExpression) pairCreateTemporaryVariableIfNecessary$default.component2();
        if (irVariable2 != null) {
            Pair pairCreateTemporaryVariableIfNecessary$default2 = UtilsKt.createTemporaryVariableIfNecessary$default(declarationIrBuilder, irExpressionShallowCopy, "containsLower", null, false, 12, null);
            IrVariable irVariable3 = (IrVariable) pairCreateTemporaryVariableIfNecessary$default2.component1();
            IrExpression irExpression3 = (IrExpression) pairCreateTemporaryVariableIfNecessary$default2.component2();
            Pair pairCreateTemporaryVariableIfNecessary$default3 = UtilsKt.createTemporaryVariableIfNecessary$default(declarationIrBuilder, endInclusive, "containsUpper", null, false, 12, null);
            IrVariable irVariable4 = (IrVariable) pairCreateTemporaryVariableIfNecessary$default3.component1();
            IrExpression irExpression4 = (IrExpression) pairCreateTemporaryVariableIfNecessary$default3.component2();
            if (z2) {
                ArrayList arrayList2 = arrayList;
                CollectionsKt.addIfNotNull(arrayList2, irVariable4);
                CollectionsKt.addIfNotNull(arrayList2, irVariable3);
            } else {
                ArrayList arrayList3 = arrayList;
                CollectionsKt.addIfNotNull(arrayList3, irVariable3);
                CollectionsKt.addIfNotNull(arrayList3, irVariable4);
            }
            IrExpression irExpressionShallowCopy2 = IrUtilsKt.shallowCopy(irExpression3);
            endInclusive = IrUtilsKt.shallowCopy(irExpression4);
            z4 = true;
            irExpressionShallowCopy = irExpressionShallowCopy2;
            irVariable = irVariable2;
        } else {
            if (!UtilsKt.getCanHaveSideEffects(irExpressionShallowCopy) || !UtilsKt.getCanHaveSideEffects(endInclusive)) {
                irVariable = irVariable2;
            } else if (z2) {
                Pair pairCreateTemporaryVariableIfNecessary$default4 = UtilsKt.createTemporaryVariableIfNecessary$default(declarationIrBuilder, endInclusive, "containsUpper", null, false, 12, null);
                IrVariable irVariable5 = (IrVariable) pairCreateTemporaryVariableIfNecessary$default4.component1();
                IrExpression irExpression5 = (IrExpression) pairCreateTemporaryVariableIfNecessary$default4.component2();
                Intrinsics.checkNotNull(irVariable5);
                arrayList.add(irVariable5);
                irVariable = irVariable2;
                endInclusive = IrUtilsKt.shallowCopy(irExpression5);
            } else {
                Pair pairCreateTemporaryVariableIfNecessary$default5 = UtilsKt.createTemporaryVariableIfNecessary$default(declarationIrBuilder, irExpressionShallowCopy, "containsLower", null, false, 12, null);
                IrVariable irVariable6 = (IrVariable) pairCreateTemporaryVariableIfNecessary$default5.component1();
                IrExpression irExpression6 = (IrExpression) pairCreateTemporaryVariableIfNecessary$default5.component2();
                Intrinsics.checkNotNull(irVariable6);
                arrayList.add(irVariable6);
                irVariable = irVariable2;
                irExpressionShallowCopy = IrUtilsKt.shallowCopy(irExpression6);
                z4 = false;
            }
            z4 = true;
        }
        CollectionsKt.addIfNotNull(arrayList, irVariable);
        if (z3) {
            irExpressionShallowCopy = UtilsKt.castIfNecessary(irExpressionShallowCopy, irClass);
            endInclusive = UtilsKt.castIfNecessary(endInclusive, irClass);
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) MapsKt.getValue(irBuiltIns.getLessOrEqualFunByOperandType(), z ? irBuiltIns.getIntClass() : irClass.getSymbol());
        if (isLastInclusive) {
            lessFunByOperandType = irBuiltIns.getLessOrEqualFunByOperandType();
        } else {
            lessFunByOperandType = irBuiltIns.getLessFunByOperandType();
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2 = (IrSimpleFunctionSymbol) MapsKt.getValue(lessFunByOperandType, z ? irBuiltIns.getIntClass() : irClass.getSymbol());
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(irClass).iterator();
        IrSimpleFunction irSimpleFunction = null;
        boolean z7 = false;
        while (true) {
            if (!it.hasNext()) {
                if (!z7) {
                    break;
                }
                break;
            }
            IrSimpleFunction next = it.next();
            IrSimpleFunction irSimpleFunction2 = next;
            if (Intrinsics.areEqual(irSimpleFunction2.getName(), OperatorNameConventions.COMPARE_TO) && irSimpleFunction2.getDispatchReceiverParameter() != null && irSimpleFunction2.getExtensionReceiverParameter() == null && irSimpleFunction2.getValueParameters().size() == 1 && (!z3 || Intrinsics.areEqual(irSimpleFunction2.getValueParameters().get(0).getType(), IrUtilsKt.getDefaultType(irClass)))) {
                if (!z7) {
                    irSimpleFunction = next;
                    z7 = true;
                }
            }
            irSimpleFunction = null;
            break;
        }
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction;
        if (irSimpleFunction3 == null) {
            return null;
        }
        if (z) {
            DeclarationIrBuilder declarationIrBuilder2 = declarationIrBuilder;
            irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder2, irSimpleFunctionSymbol);
            i = 0;
            irCall.putValueArgument(0, ExpressionHelpersKt.irInt$default(declarationIrBuilder2, 0, null, 2, null));
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(declarationIrBuilder2, irSimpleFunction3);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(IrUtilsKt.shallowCopy(irExpression2));
            irFunctionAccessExpressionIrCall.putValueArgument(0, irExpressionShallowCopy);
            Unit unit = Unit.INSTANCE;
            irCall.putValueArgument(1, irFunctionAccessExpressionIrCall);
        } else {
            i = 0;
            irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol);
            irCall.putValueArgument(0, irExpressionShallowCopy);
            irCall.putValueArgument(1, IrUtilsKt.shallowCopy(irExpression2));
        }
        if (z) {
            DeclarationIrBuilder declarationIrBuilder3 = declarationIrBuilder;
            irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder3, irSimpleFunctionSymbol2);
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(declarationIrBuilder3, irSimpleFunction3);
            irFunctionAccessExpressionIrCall2.setDispatchReceiver(IrUtilsKt.shallowCopy(irExpression2));
            irFunctionAccessExpressionIrCall2.putValueArgument(i, endInclusive);
            Unit unit2 = Unit.INSTANCE;
            irCall2.putValueArgument(i, irFunctionAccessExpressionIrCall2);
            irCall2.putValueArgument(1, ExpressionHelpersKt.irInt$default(declarationIrBuilder3, i, null, 2, null));
        } else {
            irCall2 = ExpressionHelpersKt.irCall((IrBuilderWithScope) declarationIrBuilder, irSimpleFunctionSymbol2);
            irCall2.putValueArgument(i, IrUtilsKt.shallowCopy(irExpression2));
            irCall2.putValueArgument(1, endInclusive);
        }
        IrGeneratorContext context = declarationIrBuilder.getContext();
        IrCall irCall3 = z4 ? irCall : irCall2;
        if (z4) {
            irCall = irCall2;
        }
        IrWhen irWhenAndand = PrimitivesKt.andand(context, irCall3, irCall, irStatementOrigin);
        if (arrayList.isEmpty()) {
            return irWhenAndand;
        }
        DeclarationIrBuilder declarationIrBuilder4 = declarationIrBuilder;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilder4.getContext(), declarationIrBuilder4.getScope(), declarationIrBuilder4.getStartOffset(), declarationIrBuilder4.getEndOffset(), null, null, false, 64, null);
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            irBlockBuilder.unaryPlus((IrStatement) it2.next());
        }
        irBlockBuilder.unaryPlus(irWhenAndand);
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrClass computeComparisonClass(Symbols symbols, IrType lowerType, IrType upperType, IrType argumentType) {
        IrType irTypeLeastCommonPrimitiveNumericType;
        IrType irTypeLeastCommonPrimitiveNumericType2 = leastCommonPrimitiveNumericType(symbols, lowerType, upperType);
        if (irTypeLeastCommonPrimitiveNumericType2 == null || (irTypeLeastCommonPrimitiveNumericType = leastCommonPrimitiveNumericType(symbols, argumentType, irTypeLeastCommonPrimitiveNumericType2)) == null) {
            return null;
        }
        return IrTypesKt.getClass(irTypeLeastCommonPrimitiveNumericType);
    }

    private final IrType leastCommonPrimitiveNumericType(Symbols symbols, IrType t1, IrType t2) {
        IrClassSymbol uLong;
        PrimitiveType primitiveType = IrTypePredicatesKt.getPrimitiveType(t1);
        PrimitiveType primitiveType2 = IrTypePredicatesKt.getPrimitiveType(t2);
        UnsignedType unsignedType = IrTypePredicatesKt.getUnsignedType(t1);
        UnsignedType unsignedType2 = IrTypePredicatesKt.getUnsignedType(t2);
        if (primitiveType == PrimitiveType.DOUBLE || primitiveType2 == PrimitiveType.DOUBLE) {
            uLong = symbols.getDouble();
        } else if (primitiveType == PrimitiveType.FLOAT || primitiveType2 == PrimitiveType.FLOAT) {
            uLong = symbols.getFloat();
        } else if (unsignedType == UnsignedType.ULONG || unsignedType2 == UnsignedType.ULONG) {
            uLong = symbols.getULong();
            Intrinsics.checkNotNull(uLong);
        } else if (isPromotableToUInt(unsignedType) || isPromotableToUInt(unsignedType2)) {
            uLong = symbols.getUInt();
            Intrinsics.checkNotNull(uLong);
        } else if (primitiveType == PrimitiveType.LONG || primitiveType2 == PrimitiveType.LONG) {
            uLong = symbols.getLong();
        } else if (isPromotableToInt(primitiveType) || isPromotableToInt(primitiveType2)) {
            uLong = symbols.getInt();
        } else if (primitiveType == PrimitiveType.CHAR || primitiveType2 == PrimitiveType.CHAR) {
            uLong = symbols.getChar();
        } else {
            throw new IllegalStateException(("Unexpected types: t1=" + RenderIrElementKt.render$default(t1, (DumpIrTreeOptions) null, 1, (Object) null) + ", t2=" + RenderIrElementKt.render$default(t2, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return IrTypesKt.getDefaultType(uLong);
    }

    private final boolean isPromotableToInt(PrimitiveType primitiveType) {
        return primitiveType == PrimitiveType.INT || primitiveType == PrimitiveType.SHORT || primitiveType == PrimitiveType.BYTE;
    }

    private final boolean isPromotableToUInt(UnsignedType unsignedType) {
        return unsignedType == UnsignedType.UINT || unsignedType == UnsignedType.USHORT || unsignedType == UnsignedType.UBYTE;
    }
}
