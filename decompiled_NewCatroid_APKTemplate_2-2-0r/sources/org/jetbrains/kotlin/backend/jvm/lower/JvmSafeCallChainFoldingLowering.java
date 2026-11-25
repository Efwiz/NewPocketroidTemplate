package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredStatementOrigin;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlock;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrWhen;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrBranchImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrCompositeImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrWhenImpl;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmSafeCallChainFoldingLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001%B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\nH\u0002J\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0002J \u0010\u0014\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\f\u0010\u001b\u001a\u00020\n*\u00020\nH\u0002J\f\u0010\u001c\u001a\u00020\u001d*\u00020\nH\u0002J\f\u0010\u001e\u001a\u00020\u000f*\u00020\u001fH\u0002J\f\u0010 \u001a\u00020\u000f*\u00020\u001fH\u0002J\u0016\u0010!\u001a\u00020\"*\u00020\n2\b\u0010#\u001a\u0004\u0018\u00010$H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmSafeCallChainFoldingLowering;", "Lorg/jetbrains/kotlin/backend/common/FileLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "booleanNot", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "irAndAnd", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "left", "right", "irFalse", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "startOffset", "", "endOffset", "irTrue", "irValNotNull", "irVariable", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "lower", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "irEqEqNull", "irNot", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrCallImpl;", "isJvmNullable", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isJvmPrimitive", "wrapWithBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "origin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "Transformer", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSafeCallChainFoldingLowering implements FileLoweringPass {
    private final IrSimpleFunctionSymbol booleanNot;
    private final JvmBackendContext context;

    public JvmSafeCallChainFoldingLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.booleanNot = context.getIrBuiltIns().getBooleanNotSymbol();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.FileLoweringPass
    public void lower(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        IrElementTransformerVoidKt.transformChildrenVoid(irFile, new Transformer());
    }

    private final IrCallImpl irNot(IrExpression irExpression) {
        IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(irExpression.getStartOffset(), irExpression.getEndOffset(), this.booleanNot);
        irCallImplFromSymbolOwner.setDispatchReceiver(irExpression);
        return irCallImplFromSymbolOwner;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irAndAnd(IrExpression left, IrExpression right) {
        IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(right.getStartOffset(), right.getEndOffset(), this.context.getIrBuiltIns().getAndandSymbol());
        irCallImplFromSymbolOwner.putValueArgument(0, left);
        irCallImplFromSymbolOwner.putValueArgument(1, right);
        return irCallImplFromSymbolOwner;
    }

    private final IrExpression irEqEqNull(IrExpression irExpression) {
        IrCallImpl irCallImplFromSymbolOwner = IrCallImpl.INSTANCE.fromSymbolOwner(irExpression.getStartOffset(), irExpression.getEndOffset(), this.context.getIrBuiltIns().getEqeqSymbol());
        irCallImplFromSymbolOwner.putValueArgument(0, irExpression);
        irCallImplFromSymbolOwner.putValueArgument(1, IrConstImpl.INSTANCE.constNull(irCallImplFromSymbolOwner.getStartOffset(), irCallImplFromSymbolOwner.getEndOffset(), this.context.getIrBuiltIns().getNothingNType()));
        return irCallImplFromSymbolOwner;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrBlock wrapWithBlock(IrExpression irExpression, IrStatementOrigin irStatementOrigin) {
        return new IrBlockImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), irStatementOrigin, CollectionsKt.listOf(irExpression));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Boolean> irTrue(int startOffset, int endOffset) {
        return IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, this.context.getIrBuiltIns().getBooleanType(), true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrConstImpl<Boolean> irFalse(int startOffset, int endOffset) {
        return IrConstImpl.INSTANCE.m7206boolean(startOffset, endOffset, this.context.getIrBuiltIns().getBooleanType(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression irValNotNull(int startOffset, int endOffset, IrVariable irVariable) {
        IrExpression initializer;
        if (isJvmNullable(irVariable.getType()) || (initializer = irVariable.getInitializer()) == null || !IrUtilsKt.isConstantLike(initializer)) {
            return irNot(irEqEqNull(new IrGetValueImpl(startOffset, endOffset, irVariable.getSymbol(), null, 8, null)));
        }
        return irTrue(startOffset, endOffset);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isJvmNullable(IrType irType) {
        if (!IrTypeUtilsKt.isNullable(irType)) {
            FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
            if (!IrUtilsKt.hasAnnotation(irType, ENHANCED_NULLABILITY_ANNOTATION)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isJvmPrimitive(IrType irType) {
        if (IrTypePredicatesKt.isBoolean(irType) || IrTypePredicatesKt.isByte(irType) || IrTypePredicatesKt.isShort(irType) || IrTypePredicatesKt.isInt(irType) || IrTypePredicatesKt.isLong(irType) || IrTypePredicatesKt.isChar(irType) || IrTypePredicatesKt.isFloat(irType) || IrTypePredicatesKt.isDouble(irType)) {
            FqName ENHANCED_NULLABILITY_ANNOTATION = JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(ENHANCED_NULLABILITY_ANNOTATION, "ENHANCED_NULLABILITY_ANNOTATION");
            if (!IrUtilsKt.hasAnnotation(irType, ENHANCED_NULLABILITY_ANNOTATION)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: compiled from: JvmSafeCallChainFoldingLowering.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u000eH\u0016J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0004H\u0002¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/JvmSafeCallChainFoldingLowering$Transformer;", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformerVoid;", "(Lorg/jetbrains/kotlin/backend/jvm/lower/JvmSafeCallChainFoldingLowering;)V", "foldElvis", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "elvisInfo", "Lorg/jetbrains/kotlin/backend/jvm/lower/ElvisInfo;", "foldSafeCall", "safeCallInfo", "Lorg/jetbrains/kotlin/backend/jvm/lower/SafeCallInfo;", "visitBlock", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "visitCall", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "isFoldedSafeCallWithNonNullResult", "", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class Transformer extends IrElementTransformerVoid {
        public Transformer() {
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitBlock(IrBlock expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            transformChildrenVoid(expression);
            SafeCallInfo safeCall = JvmSafeCallChainFoldingLoweringKt.parseSafeCall(expression, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns());
            if (safeCall != null) {
                return foldSafeCall(safeCall);
            }
            ElvisInfo elvis = JvmSafeCallChainFoldingLoweringKt.parseElvis(expression, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns());
            if (elvis != null) {
                return foldElvis(elvis);
            }
            return expression;
        }

        private final IrExpression foldSafeCall(SafeCallInfo safeCallInfo) {
            IrBlock block = safeCallInfo.getBlock();
            int startOffset = block.getStartOffset();
            int endOffset = block.getEndOffset();
            IrType type = block.getType();
            IrVariable tmpVal = safeCallInfo.getTmpVal();
            IrExpression initializer = tmpVal.getInitializer();
            if (initializer instanceof IrBlock) {
                IrBlock irBlock = (IrBlock) initializer;
                if (Intrinsics.areEqual(irBlock.getOrigin(), JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE)) {
                    IrStatement irStatement = irBlock.getStatements().get(0);
                    Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
                    IrWhen irWhen = (IrWhen) irStatement;
                    IrExpression condition = irWhen.getBranches().get(0).getCondition();
                    IrExpression result = irWhen.getBranches().get(0).getResult();
                    tmpVal.setInitializer(result);
                    tmpVal.setType(result.getType());
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns().getBooleanType(), null, CollectionsKt.listOf((Object[]) new IrStatement[]{tmpVal, JvmSafeCallChainFoldingLowering.this.irValNotNull(startOffset, endOffset, tmpVal)}));
                    irBlock.setType(type);
                    irWhen.setType(type);
                    irWhen.getBranches().get(0).setCondition(JvmSafeCallChainFoldingLowering.this.irAndAnd(condition, irCompositeImpl));
                    irWhen.getBranches().get(0).setResult(safeCallInfo.getIfNotNullBranch().getResult());
                    return irBlock;
                }
            }
            return JvmSafeCallChainFoldingLowering.this.wrapWithBlock(new IrWhenImpl(startOffset, endOffset, type, JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE, CollectionsKt.listOf((Object[]) new IrBranchImpl[]{new IrBranchImpl(startOffset, endOffset, new IrCompositeImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns().getBooleanType(), null, CollectionsKt.listOf((Object[]) new IrStatement[]{tmpVal, JvmSafeCallChainFoldingLowering.this.irValNotNull(startOffset, endOffset, tmpVal)})), safeCallInfo.getIfNotNullBranch().getResult()), new IrBranchImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.irTrue(startOffset, endOffset), safeCallInfo.getIfNullBranch().getResult())})), JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE);
        }

        private final IrExpression foldElvis(ElvisInfo elvisInfo) {
            IrExpression elvisLhs = elvisInfo.getElvisLhs();
            IrBlock block = elvisInfo.getBlock();
            int startOffset = block.getStartOffset();
            int endOffset = block.getEndOffset();
            IrType type = block.getType();
            IrVariable tmpVal = elvisInfo.getTmpVal();
            boolean z = elvisLhs instanceof IrBlock;
            if (z) {
                IrBlock irBlock = (IrBlock) elvisLhs;
                if (Intrinsics.areEqual(irBlock.getOrigin(), JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE)) {
                    IrStatement irStatement = irBlock.getStatements().get(0);
                    Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
                    IrWhen irWhen = (IrWhen) irStatement;
                    IrExpression condition = irWhen.getBranches().get(0).getCondition();
                    IrExpression result = irWhen.getBranches().get(0).getResult();
                    tmpVal.setInitializer(result);
                    tmpVal.setType(result.getType());
                    IrCompositeImpl irCompositeImpl = new IrCompositeImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns().getBooleanType(), null, CollectionsKt.listOf((Object[]) new IrStatement[]{tmpVal, JvmSafeCallChainFoldingLowering.this.irValNotNull(startOffset, endOffset, tmpVal)}));
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new IrBranchImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.irAndAnd(condition, irCompositeImpl), new IrGetValueImpl(startOffset, endOffset, tmpVal.getSymbol(), null, 8, null)));
                    IrExpression elvisRhs = elvisInfo.getElvisRhs();
                    if (isFoldedSafeCallWithNonNullResult(elvisRhs)) {
                        Intrinsics.checkNotNull(elvisRhs, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlock");
                        IrStatement irStatement2 = ((IrBlock) elvisRhs).getStatements().get(0);
                        Intrinsics.checkNotNull(irStatement2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
                        arrayList.addAll(((IrWhen) irStatement2).getBranches());
                    } else {
                        arrayList.add(new IrBranchImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.irTrue(startOffset, endOffset), elvisInfo.getElvisRhs()));
                    }
                    return JvmSafeCallChainFoldingLowering.this.wrapWithBlock(new IrWhenImpl(startOffset, endOffset, type, JvmLoweredStatementOrigin.FOLDED_ELVIS.INSTANCE, arrayList), JvmLoweredStatementOrigin.FOLDED_ELVIS.INSTANCE);
                }
            }
            if (z) {
                IrBlock irBlock2 = (IrBlock) elvisLhs;
                if (Intrinsics.areEqual(irBlock2.getOrigin(), JvmLoweredStatementOrigin.FOLDED_ELVIS.INSTANCE)) {
                    IrStatement irStatement3 = irBlock2.getStatements().get(0);
                    Intrinsics.checkNotNull(irStatement3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
                    IrWhen irWhen2 = (IrWhen) irStatement3;
                    IrBranch irBranch = (IrBranch) CollectionsKt.last((List) irWhen2.getBranches());
                    IrExpression result2 = irBranch.getResult();
                    tmpVal.setInitializer(result2);
                    tmpVal.setType(result2.getType());
                    irBranch.setCondition(new IrCompositeImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns().getBooleanType(), null, CollectionsKt.listOf((Object[]) new IrElement[]{tmpVal, JvmSafeCallChainFoldingLowering.this.irValNotNull(startOffset, endOffset, tmpVal)})));
                    irBranch.setResult(new IrGetValueImpl(startOffset, endOffset, tmpVal.getSymbol(), null, 8, null));
                    irWhen2.getBranches().add(new IrBranchImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.irTrue(startOffset, endOffset), elvisInfo.getElvisRhs()));
                    irWhen2.setType(type);
                    return JvmSafeCallChainFoldingLowering.this.wrapWithBlock(irWhen2, JvmLoweredStatementOrigin.FOLDED_ELVIS.INSTANCE);
                }
            }
            return JvmSafeCallChainFoldingLowering.this.wrapWithBlock(new IrWhenImpl(startOffset, endOffset, type, JvmLoweredStatementOrigin.FOLDED_ELVIS.INSTANCE, CollectionsKt.listOf((Object[]) new IrBranchImpl[]{new IrBranchImpl(startOffset, endOffset, new IrCompositeImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns().getBooleanType(), null, CollectionsKt.listOf((Object[]) new IrElement[]{tmpVal, JvmSafeCallChainFoldingLowering.this.irValNotNull(startOffset, endOffset, tmpVal)})), new IrGetValueImpl(startOffset, endOffset, tmpVal.getSymbol(), null, 8, null)), new IrBranchImpl(startOffset, endOffset, JvmSafeCallChainFoldingLowering.this.irTrue(startOffset, endOffset), elvisInfo.getElvisRhs())})), JvmLoweredStatementOrigin.FOLDED_ELVIS.INSTANCE);
        }

        private final boolean isFoldedSafeCallWithNonNullResult(IrExpression irExpression) {
            if (!(irExpression instanceof IrBlock)) {
                return false;
            }
            IrBlock irBlock = (IrBlock) irExpression;
            if (!Intrinsics.areEqual(irBlock.getOrigin(), JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE)) {
                return false;
            }
            IrStatement irStatement = irBlock.getStatements().get(0);
            IrWhen irWhen = irStatement instanceof IrWhen ? (IrWhen) irStatement : null;
            if (irWhen == null) {
                return false;
            }
            return !JvmSafeCallChainFoldingLowering.this.isJvmNullable(irWhen.getBranches().get(0).getResult().getType());
        }

        @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
        public IrExpression visitCall(IrCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            IrCall irCall = expression;
            transformChildrenVoid(irCall);
            if (Intrinsics.areEqual(expression.getSymbol(), JvmSafeCallChainFoldingLowering.this.getContext().getIrBuiltIns().getEqeqSymbol())) {
                int startOffset = expression.getStartOffset();
                int endOffset = expression.getEndOffset();
                IrExpression valueArgument = expression.getValueArgument(0);
                if (valueArgument == null) {
                    throw new AssertionError("No value argument #0: " + DumpIrTreeKt.dump$default(irCall, null, 1, null));
                }
                IrExpression valueArgument2 = expression.getValueArgument(1);
                if (valueArgument2 == null) {
                    throw new AssertionError("No value argument #1: " + DumpIrTreeKt.dump$default(irCall, null, 1, null));
                }
                if (valueArgument instanceof IrBlock) {
                    IrBlock irBlock = (IrBlock) valueArgument;
                    if (Intrinsics.areEqual(irBlock.getOrigin(), JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE) && JvmSafeCallChainFoldingLowering.this.isJvmPrimitive(valueArgument2.getType())) {
                        IrStatement irStatement = irBlock.getStatements().get(0);
                        Intrinsics.checkNotNull(irStatement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
                        IrWhen irWhen = (IrWhen) irStatement;
                        expression.putValueArgument(0, irWhen.getBranches().get(0).getResult());
                        irWhen.getBranches().get(0).setResult(expression);
                        irWhen.getBranches().get(1).setResult(JvmSafeCallChainFoldingLowering.this.irFalse(startOffset, endOffset));
                        irWhen.setType(expression.getType());
                        return JvmSafeCallChainFoldingLowering.this.wrapWithBlock(irWhen, null);
                    }
                }
                if (valueArgument2 instanceof IrBlock) {
                    IrBlock irBlock2 = (IrBlock) valueArgument2;
                    if (Intrinsics.areEqual(irBlock2.getOrigin(), JvmLoweredStatementOrigin.FOLDED_SAFE_CALL.INSTANCE) && JvmSafeCallChainFoldingLowering.this.isJvmPrimitive(valueArgument.getType())) {
                        IrStatement irStatement2 = irBlock2.getStatements().get(0);
                        Intrinsics.checkNotNull(irStatement2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrWhen");
                        IrWhen irWhen2 = (IrWhen) irStatement2;
                        expression.putValueArgument(1, irWhen2.getBranches().get(0).getResult());
                        irWhen2.getBranches().get(0).setResult(expression);
                        irWhen2.getBranches().get(1).setResult(JvmSafeCallChainFoldingLowering.this.irFalse(startOffset, endOffset));
                        irWhen2.setType(expression.getType());
                        return JvmSafeCallChainFoldingLowering.this.wrapWithBlock(irWhen2, null);
                    }
                }
            }
            return expression;
        }
    }
}
