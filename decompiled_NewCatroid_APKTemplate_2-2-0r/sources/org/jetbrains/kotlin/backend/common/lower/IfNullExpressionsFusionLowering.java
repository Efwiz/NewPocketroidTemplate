package org.jetbrains.kotlin.backend.common.lower;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderKt;
import org.jetbrains.kotlin.ir.builders.IrStatementsBuilder;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;

/* JADX INFO: compiled from: IfNullExpressionsFusionLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0016\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\f\u0010\u000b\u001a\u00020\f*\u00020\fH\u0002J\u000e\u0010\r\u001a\u0004\u0018\u00010\f*\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\fH\u0002J\"\u0010\u0011\u001a\u00020\f*\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0015H\u0002J\u001c\u0010\u0011\u001a\u00020\f*\u00020\f2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "copyIfTrivial", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getElseBranchResultOrNull", "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "matchIfNullExpr", "Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering$IfNullExpr;", "remap", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "to", "Lkotlin/Lazy;", "IfNullExpr", "Transformer", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IfNullExpressionsFusionLowering implements FileLoweringPass {
    private final CommonBackendContext context;

    public IfNullExpressionsFusionLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new Transformer());
    }

    /* JADX INFO: compiled from: IfNullExpressionsFusionLowering.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0002J\f\u0010\n\u001a\u00020\u0004*\u00020\u0006H\u0002J#\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\fH\u0002¢\u0006\u0002\u0010\u0010J+\u0010\u0011\u001a\u00020\u0004*\u00020\t2\u0006\u0010\u0012\u001a\u00020\u00042\b\u0010\u0013\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002¢\u0006\u0002\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering$Transformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "(Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering;)V", "visitBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "createIrBuilder", "Lorg/jetbrains/kotlin/backend/common/lower/DeclarationIrBuilder;", "Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering$IfNullExpr;", "fuseIfNull", "isNull", "", "knownVariableSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;", "knownVariableIsNull", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/symbols/IrVariableSymbol;Z)Ljava/lang/Boolean;", "substitute", "subject", "knownNullability", "temporaryVarType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering$IfNullExpr;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Ljava/lang/Boolean;Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Transformer extends IrElementTransformerVoid {
        public Transformer() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitBlock(IrBlock expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return visitExpression(fuseIfNull(expression));
        }

        private final IrExpression fuseIfNull(IrBlock irBlock) {
            IrExpression initializer;
            IfNullExpr ifNullExprMatchIfNullExpr;
            IrBlock irBlock2 = irBlock;
            IfNullExpr ifNullExprMatchIfNullExpr2 = IfNullExpressionsFusionLowering.this.matchIfNullExpr(irBlock2);
            if (ifNullExprMatchIfNullExpr2 == null || !Intrinsics.areEqual(ifNullExprMatchIfNullExpr2.getSubjectVar().getOrigin(), IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE) || (initializer = ifNullExprMatchIfNullExpr2.getSubjectVar().getInitializer()) == null || (ifNullExprMatchIfNullExpr = IfNullExpressionsFusionLowering.this.matchIfNullExpr(initializer)) == null) {
                return irBlock2;
            }
            Boolean boolIsNull = isNull(ifNullExprMatchIfNullExpr.getIfNullExpr(), ifNullExprMatchIfNullExpr.getSubjectVar().getSymbol(), true);
            Boolean boolIsNull2 = isNull(ifNullExprMatchIfNullExpr.getIfNotNullExpr(), ifNullExprMatchIfNullExpr.getSubjectVar().getSymbol(), false);
            if ((!IrUtilsKt.isTrivial(ifNullExprMatchIfNullExpr2.getIfNotNullExpr()) && !Intrinsics.areEqual((Object) boolIsNull, (Object) true) && !Intrinsics.areEqual((Object) boolIsNull2, (Object) true)) || (!IrUtilsKt.isTrivial(ifNullExprMatchIfNullExpr2.getIfNullExpr()) && !Intrinsics.areEqual((Object) boolIsNull, (Object) false) && !Intrinsics.areEqual((Object) boolIsNull2, (Object) false))) {
                return irBlock2;
            }
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = createIrBuilder(ifNullExprMatchIfNullExpr);
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset(), null, null, false, 64, null);
            IrExpression irExpressionSubstitute = substitute(ifNullExprMatchIfNullExpr2, ifNullExprMatchIfNullExpr.getIfNullExpr(), boolIsNull, ifNullExprMatchIfNullExpr.getType());
            IrExpression irExpressionSubstitute2 = substitute(ifNullExprMatchIfNullExpr2, ifNullExprMatchIfNullExpr.getIfNotNullExpr(), boolIsNull2, ifNullExprMatchIfNullExpr.getType());
            irBlockBuilder.unaryPlus(ifNullExprMatchIfNullExpr.getSubjectVar());
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, ifNullExprMatchIfNullExpr2.getType(), ExpressionHelpersKt.irGet(irBlockBuilder2, ifNullExprMatchIfNullExpr.getSubjectVar()), irExpressionSubstitute, irExpressionSubstitute2));
            IrContainerExpression irContainerExpressionDoBuild = irBlockBuilder.getIrBlockBody();
            Intrinsics.checkNotNull(irContainerExpressionDoBuild, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlock");
            return fuseIfNull((IrBlock) irContainerExpressionDoBuild);
        }

        private final IrExpression substitute(IfNullExpr ifNullExpr, IrExpression irExpression, Boolean bool, IrType irType) {
            if (bool != null) {
                return Intrinsics.areEqual((Object) bool, (Object) true) ? IfNullExpressionsFusionLowering.this.remap(ifNullExpr.getIfNullExpr(), ifNullExpr.getSubjectVar(), irExpression) : IfNullExpressionsFusionLowering.this.remap(ifNullExpr.getIfNotNullExpr(), ifNullExpr.getSubjectVar(), irExpression);
            }
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = createIrBuilder(ifNullExpr);
            IfNullExpressionsFusionLowering ifNullExpressionsFusionLowering = IfNullExpressionsFusionLowering.this;
            IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset(), null, null, false, 64, null);
            final IrVariable irVariableCreateTmpVariable$default = IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, irExpression, (String) null, false, (IrDeclarationOrigin) null, irType, 14, (Object) null);
            IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
            irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, ifNullExpr.getType(), ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableCreateTmpVariable$default), ifNullExpressionsFusionLowering.remap(ifNullExpr.getIfNullExpr(), ifNullExpr.getSubjectVar(), (Lazy<? extends IrVariable>) LazyKt.lazy(new Function0<IrVariable>() { // from class: org.jetbrains.kotlin.backend.common.lower.IfNullExpressionsFusionLowering$Transformer$substitute$1$ifNull$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrVariable invoke() {
                    return irVariableCreateTmpVariable$default;
                }
            })), ifNullExpressionsFusionLowering.remap(ifNullExpr.getIfNotNullExpr(), ifNullExpr.getSubjectVar(), (Lazy<? extends IrVariable>) LazyKt.lazy(new Function0<IrVariable>() { // from class: org.jetbrains.kotlin.backend.common.lower.IfNullExpressionsFusionLowering$Transformer$substitute$1$ifNotNull$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final IrVariable invoke() {
                    return irVariableCreateTmpVariable$default;
                }
            }))));
            return irBlockBuilder.getIrBlockBody();
        }

        private final DeclarationIrBuilder createIrBuilder(IfNullExpr ifNullExpr) {
            CommonBackendContext context = IfNullExpressionsFusionLowering.this.getContext();
            IrDeclarationParent parent = ifNullExpr.getSubjectVar().getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
            return LowerUtilsKt.createIrBuilder(context, ((IrSymbolOwner) parent).getSymbol(), ifNullExpr.getSubjectVar().getStartOffset(), ifNullExpr.getSubjectVar().getEndOffset());
        }

        private final Boolean isNull(IrExpression irExpression, IrVariableSymbol irVariableSymbol, boolean z) {
            if (irExpression instanceof IrConst) {
                return Boolean.valueOf(((IrConst) irExpression).getValue() == null);
            }
            if (irExpression instanceof IrGetValue) {
                if (Intrinsics.areEqual(((IrGetValue) irExpression).getSymbol(), irVariableSymbol)) {
                    return Boolean.valueOf(z);
                }
                if (!IrTypeUtilsKt.isNullable(irExpression.getType())) {
                    return false;
                }
            } else {
                if (irExpression instanceof IrConstructorCall ? true : irExpression instanceof IrGetSingletonValue ? true : irExpression instanceof IrFunctionExpression ? true : irExpression instanceof IrCallableReference ? true : irExpression instanceof IrClassReference ? true : irExpression instanceof IrGetClass) {
                    return false;
                }
                if (irExpression instanceof IrCall) {
                    if (IfNullExpressionsFusionLowering.this.getContext().getOptimizeNullChecksUsingKotlinNullability() && !IrTypeUtilsKt.isNullable(irExpression.getType())) {
                        return false;
                    }
                } else if (irExpression instanceof IrGetField) {
                    if (IfNullExpressionsFusionLowering.this.getContext().getOptimizeNullChecksUsingKotlinNullability() && !IrTypeUtilsKt.isNullable(irExpression.getType())) {
                        return false;
                    }
                } else if (irExpression instanceof IrBlock) {
                    IrStatement irStatement = (IrStatement) CollectionsKt.singleOrNull((List) ((IrBlock) irExpression).getStatements());
                    if (irStatement instanceof IrExpression) {
                        IrExpression irExpression2 = (IrExpression) irStatement;
                        if (Intrinsics.areEqual(irExpression2.getType(), irExpression.getType())) {
                            return isNull(irExpression2, irVariableSymbol, z);
                        }
                    }
                }
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: IfNullExpressionsFusionLowering.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/lower/IfNullExpressionsFusionLowering$IfNullExpr;", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "subjectVar", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "ifNullExpr", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ifNotNullExpr", "(Lorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)V", "getIfNotNullExpr", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getIfNullExpr", "getSubjectVar", "()Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IfNullExpr {
        private final IrExpression ifNotNullExpr;
        private final IrExpression ifNullExpr;
        private final IrVariable subjectVar;
        private final IrType type;

        public IfNullExpr(IrType type, IrVariable subjectVar, IrExpression ifNullExpr, IrExpression ifNotNullExpr) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(subjectVar, "subjectVar");
            Intrinsics.checkNotNullParameter(ifNullExpr, "ifNullExpr");
            Intrinsics.checkNotNullParameter(ifNotNullExpr, "ifNotNullExpr");
            this.type = type;
            this.subjectVar = subjectVar;
            this.ifNullExpr = ifNullExpr;
            this.ifNotNullExpr = ifNotNullExpr;
        }

        public final IrType getType() {
            return this.type;
        }

        public final IrVariable getSubjectVar() {
            return this.subjectVar;
        }

        public final IrExpression getIfNullExpr() {
            return this.ifNullExpr;
        }

        public final IrExpression getIfNotNullExpr() {
            return this.ifNotNullExpr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IfNullExpr matchIfNullExpr(IrExpression irExpression) {
        IrExpression elseBranchResultOrNull;
        if (!(irExpression instanceof IrBlock)) {
            return null;
        }
        IrBlock irBlock = (IrBlock) irExpression;
        if (irBlock.getStatements().size() != 2) {
            return null;
        }
        IrStatement irStatement = irBlock.getStatements().get(0);
        IrVariable irVariable = irStatement instanceof IrVariable ? (IrVariable) irStatement : null;
        if (irVariable == null || irVariable.getIsVar() || irVariable.getInitializer() == null) {
            return null;
        }
        IrStatement irStatement2 = irBlock.getStatements().get(1);
        IrWhen irWhen = irStatement2 instanceof IrWhen ? (IrWhen) irStatement2 : null;
        if (irWhen == null || irWhen.getBranches().size() != 2) {
            return null;
        }
        IrBranch irBranch = irWhen.getBranches().get(0);
        IrExpression condition = irBranch.getCondition();
        IrCall irCall = condition instanceof IrCall ? (IrCall) condition : null;
        if (irCall == null || !Intrinsics.areEqual(irCall.getSymbol(), this.context.getIrBuiltIns().getEqeqSymbol())) {
            return null;
        }
        IrExpression valueArgument = irCall.getValueArgument(0);
        IrGetValue irGetValue = valueArgument instanceof IrGetValue ? (IrGetValue) valueArgument : null;
        if (irGetValue == null || !Intrinsics.areEqual(irGetValue.getSymbol(), irVariable.getSymbol())) {
            return null;
        }
        IrExpression valueArgument2 = irCall.getValueArgument(1);
        IrConst irConst = valueArgument2 instanceof IrConst ? (IrConst) valueArgument2 : null;
        if (irConst == null || irConst.getValue() != null || (elseBranchResultOrNull = getElseBranchResultOrNull(irWhen.getBranches().get(1))) == null) {
            return null;
        }
        return new IfNullExpr(irWhen.getType(), irVariable, irBranch.getResult(), elseBranchResultOrNull);
    }

    private final IrExpression getElseBranchResultOrNull(IrBranch irBranch) {
        IrExpression condition = irBranch.getCondition();
        if ((condition instanceof IrConst) && Intrinsics.areEqual(((IrConst) condition).getValue(), (Object) true)) {
            return irBranch.getResult();
        }
        return null;
    }

    private final IrExpression copyIfTrivial(IrExpression irExpression) {
        return IrUtilsKt.isTrivial(irExpression) ? IrUtilsKt.shallowCopy(irExpression) : irExpression;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression remap(IrExpression irExpression, final IrVariable irVariable, final Lazy<? extends IrVariable> lazy) {
        return copyIfTrivial(irExpression).transform((IrElementTransformer<? super Object>) new AbstractVariableRemapper() { // from class: org.jetbrains.kotlin.backend.common.lower.IfNullExpressionsFusionLowering.remap.1
            @Override // org.jetbrains.kotlin.backend.common.lower.AbstractVariableRemapper
            protected IrValueDeclaration remapVariable(IrValueDeclaration value) {
                Intrinsics.checkNotNullParameter(value, "value");
                if (Intrinsics.areEqual(value.getSymbol(), irVariable.getSymbol())) {
                    return lazy.getValue();
                }
                return null;
            }
        }, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression remap(IrExpression irExpression, IrVariable irVariable, final IrExpression irExpression2) {
        if ((irExpression instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) irExpression).getSymbol(), irVariable.getSymbol())) {
            return irExpression2;
        }
        if (irExpression instanceof IrTypeOperatorCall) {
            int startOffset = irExpression.getStartOffset();
            int endOffset = irExpression.getEndOffset();
            IrType type = irExpression.getType();
            IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
            return new IrTypeOperatorCallImpl(startOffset, endOffset, type, irTypeOperatorCall.getOperator(), irTypeOperatorCall.getTypeOperand(), remap(irTypeOperatorCall.getArgument(), irVariable, irExpression2));
        }
        CommonBackendContext commonBackendContext = this.context;
        IrDeclarationParent parent = irVariable.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
        DeclarationIrBuilder declarationIrBuilderCreateIrBuilder = LowerUtilsKt.createIrBuilder(commonBackendContext, ((IrSymbolOwner) parent).getSymbol(), irExpression.getStartOffset(), irExpression.getEndOffset());
        final IrBlockBuilder irBlockBuilder = new IrBlockBuilder(declarationIrBuilderCreateIrBuilder.getContext(), declarationIrBuilderCreateIrBuilder.getScope(), declarationIrBuilderCreateIrBuilder.getStartOffset(), declarationIrBuilderCreateIrBuilder.getEndOffset(), null, null, false, 64, null);
        irBlockBuilder.unaryPlus(remap(irExpression, irVariable, LazyKt.lazy(new Function0<IrVariable>() { // from class: org.jetbrains.kotlin.backend.common.lower.IfNullExpressionsFusionLowering$remap$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrVariable invoke() {
                return IrBuilderKt.createTmpVariable$default((IrStatementsBuilder) irBlockBuilder, irExpression2, (String) null, false, (IrDeclarationOrigin) null, (IrType) null, 30, (Object) null);
            }
        })));
        IrContainerExpression irContainerExpressionDoBuild = irBlockBuilder.getIrBlockBody();
        IrExpression irExpression3 = (IrExpression) CollectionsKt.singleOrNull((List) irContainerExpressionDoBuild.getStatements());
        return irExpression3 == null ? irContainerExpressionDoBuild : irExpression3;
    }
}
