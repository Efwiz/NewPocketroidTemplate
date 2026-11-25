package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.FileLoweringPass;
import org.jetbrains.kotlin.backend.common.lower.FlattenStringConcatenationLowering;
import org.jetbrains.kotlin.backend.common.lower.FlattenStringConcatenationLoweringKt;
import org.jetbrains.kotlin.backend.common.lower.loops.ForLoopsLoweringKt;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.InlineClassAbi;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrStatementContainer;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrIfThenElseImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: JvmStringConcatenationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000fH\u0002\u001a\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000fH\u0002\u001a\u0014\u0010\u0013\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000fH\u0002\u001a\f\u0010\u0014\u001a\u00020\u000f*\u00020\u000fH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"0\u0010\u0002\u001a\u001e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005`\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"MAX_STRING_CONCAT_DEPTH", "", "jvmStringConcatenationLowering", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getJvmStringConcatenationLowering", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "toStringFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getToStringFunction", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "callToString", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/backend/jvm/ir/JvmIrBuilder;", "expression", "lowerInlineClassArgument", "normalizeArgument", "unwrapImplicitNotNull", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmStringConcatenationLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> jvmStringConcatenationLowering = PhaseBuildersKt.makeIrFilePhase(new Function1<JvmBackendContext, FileLoweringPass>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.JvmStringConcatenationLoweringKt$jvmStringConcatenationLowering$1
        @Override // kotlin.jvm.functions.Function1
        public final FileLoweringPass invoke(JvmBackendContext context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (!context.getConfig().getRuntimeStringConcat().isDynamic()) {
                return new JvmStringConcatenationLowering(context);
            }
            return new JvmDynamicStringConcatenationLowering(context);
        }
    }, "StringConcatenation", "Replace IrStringConcatenation with string builders", (248 & 8) != 0 ? SetsKt.emptySet() : SetsKt.setOf((Object[]) new NamedCompilerPhase[]{FlattenStringConcatenationLoweringKt.getFlattenStringConcatenationPhase(), ForLoopsLoweringKt.getForLoopsPhase()}), (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getJvmStringConcatenationLowering() {
        return jvmStringConcatenationLowering;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrSimpleFunction getToStringFunction(IrClass irClass) {
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(irClass)) {
            if (FlattenStringConcatenationLowering.INSTANCE.isToString(irSimpleFunction2)) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                z = true;
                irSimpleFunction = irSimpleFunction2;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        return irSimpleFunction;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression callToString(JvmIrBuilder jvmIrBuilder, IrExpression irExpression) {
        IrExpression irExpressionNormalizeArgument = normalizeArgument(jvmIrBuilder, irExpression);
        IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder, jvmIrBuilder.getBackendContext().getIr().getSymbols().typeToStringValueOfFunction(IrTypePredicatesKt.isPrimitiveType$default(irExpressionNormalizeArgument.getType(), false, 1, null) ? irExpressionNormalizeArgument.getType() : jvmIrBuilder.getContext().getIrBuiltIns().getAnyNType()));
        irCall.putValueArgument(0, irExpressionNormalizeArgument);
        return irCall;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression normalizeArgument(JvmIrBuilder jvmIrBuilder, IrExpression irExpression) {
        IrType type = irExpression.getType();
        if (IrTypePredicatesKt.isByte(type) || IrTypePredicatesKt.isShort(type)) {
            return ExpressionHelpersKt.irImplicitCast(jvmIrBuilder, irExpression, jvmIrBuilder.getContext().getIrBuiltIns().getIntType());
        }
        if (irExpression instanceof IrConst) {
            IrConst irConst = (IrConst) irExpression;
            if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.String.INSTANCE)) {
                Object value = irConst.getValue();
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type kotlin.String");
                if (((String) value).length() == 1) {
                    Object value2 = irConst.getValue();
                    Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.String");
                    return ExpressionHelpersKt.irChar(jvmIrBuilder, ((String) value2).charAt(0));
                }
            }
        }
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(type);
        IrTypeParameterSymbol irTypeParameterSymbol = classifierOrNull instanceof IrTypeParameterSymbol ? (IrTypeParameterSymbol) classifierOrNull : null;
        if (irTypeParameterSymbol != null) {
            IrType representativeUpperBound = JvmIrTypeUtilsKt.getRepresentativeUpperBound(irTypeParameterSymbol.getOwner());
            if (Intrinsics.areEqual(IrTypesKt.getClassifierOrNull(representativeUpperBound), jvmIrBuilder.getContext().getIrBuiltIns().getStringClass())) {
                return ExpressionHelpersKt.irImplicitCast(jvmIrBuilder, irExpression, IrTypesKt.makeNullable(jvmIrBuilder.getContext().getIrBuiltIns().getStringType()));
            }
            if (!IrTypeUtilsKt.isNullable(type)) {
                if (IrTypePredicatesKt.isByte(representativeUpperBound) || IrTypePredicatesKt.isShort(representativeUpperBound)) {
                    return ExpressionHelpersKt.irImplicitCast(jvmIrBuilder, irExpression, jvmIrBuilder.getContext().getIrBuiltIns().getIntType());
                }
                if (IrTypePredicatesKt.isPrimitiveType$default(representativeUpperBound, false, 1, null)) {
                    return ExpressionHelpersKt.irImplicitCast(jvmIrBuilder, irExpression, representativeUpperBound);
                }
            }
        }
        return irExpression;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:35:0x0080  */
    /* JADX WARN: Multi-variable type inference failed */
    public static final IrExpression lowerInlineClassArgument(JvmIrBuilder jvmIrBuilder, IrExpression irExpression) {
        IrClassSymbol classOrNull;
        IrClass owner;
        IrSimpleFunction toStringFunction;
        IrSimpleFunction replacementFunction;
        IrVariable irVariable;
        IrValueSymbol symbol;
        if (InlineClassAbi.INSTANCE.unboxType(irExpression.getType()) != null && (classOrNull = IrTypesKt.getClassOrNull(irExpression.getType())) != null && (owner = classOrNull.getOwner()) != null && (toStringFunction = getToStringFunction(owner)) != null) {
            IrSimpleFunction irSimpleFunction = toStringFunction instanceof IrAttributeContainer ? toStringFunction : null;
            IrElement attributeOwnerId = irSimpleFunction != null ? irSimpleFunction.getAttributeOwnerId() : null;
            IrSimpleFunction irSimpleFunction2 = attributeOwnerId instanceof IrFunction ? (IrFunction) attributeOwnerId : null;
            if (irSimpleFunction2 == null) {
                irSimpleFunction2 = toStringFunction;
            }
            if (irSimpleFunction2 == null || (replacementFunction = jvmIrBuilder.getBackendContext().getInlineClassReplacements().getReplacementFunction(irSimpleFunction2)) == null) {
                return null;
            }
            if (IrTypeUtilsKt.isNullable(irExpression.getType())) {
                JvmIrBuilder jvmIrBuilder2 = jvmIrBuilder;
                if (irExpression instanceof IrGetValue) {
                    IrGetValue irGetValue = (IrGetValue) irExpression;
                    if (!IrUtilsKt.isImmutable(irGetValue.getSymbol().getOwner())) {
                        IrVariable irVariableCreateTemporaryVariable = jvmIrBuilder2.getScope().createTemporaryVariable(irExpression, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpression.getStartOffset() : 0, (108 & 64) != 0 ? irExpression.getEndOffset() : 0);
                        irVariable = irVariableCreateTemporaryVariable;
                        symbol = irVariableCreateTemporaryVariable.getSymbol();
                    } else {
                        symbol = irGetValue.getSymbol();
                        irVariable = null;
                    }
                } else {
                    IrVariable irVariableCreateTemporaryVariable2 = jvmIrBuilder2.getScope().createTemporaryVariable(irExpression, (108 & 2) != 0 ? null : null, (108 & 4) != 0 ? false : false, (108 & 8) != 0 ? IrDeclarationOrigin.IR_TEMPORARY_VARIABLE.INSTANCE : null, (108 & 16) == 0 ? null : null, (108 & 32) != 0 ? irExpression.getStartOffset() : 0, (108 & 64) != 0 ? irExpression.getEndOffset() : 0);
                    irVariable = irVariableCreateTemporaryVariable2;
                    symbol = irVariableCreateTemporaryVariable2.getSymbol();
                }
                IrType stringType = jvmIrBuilder.getContext().getIrBuiltIns().getStringType();
                IrGetValueImpl irGetValueImplIrGet = ExpressionHelpersKt.irGet(jvmIrBuilder2, symbol.getOwner());
                IrConstImpl<String> irConstImplIrString = ExpressionHelpersKt.irString(jvmIrBuilder2, "null");
                IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder2, replacementFunction);
                irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(jvmIrBuilder2, symbol.getOwner()));
                Unit unit = Unit.INSTANCE;
                IrIfThenElseImpl irIfThenElseImplIrIfNull = ExpressionHelpersKt.irIfNull(jvmIrBuilder2, stringType, irGetValueImplIrGet, irConstImplIrString, irFunctionAccessExpressionIrCall);
                if (irVariable == null) {
                    return irIfThenElseImplIrIfNull;
                }
                IrBlockImpl irBlockImpl = new IrBlockImpl(jvmIrBuilder2.getStartOffset(), jvmIrBuilder2.getEndOffset(), irIfThenElseImplIrIfNull.getType(), null);
                irBlockImpl.getStatements().add(irVariable);
                if (irIfThenElseImplIrIfNull instanceof IrStatementContainer) {
                    irBlockImpl.getStatements().addAll(((IrStatementContainer) irIfThenElseImplIrIfNull).getStatements());
                } else {
                    irBlockImpl.getStatements().add(irIfThenElseImplIrIfNull);
                }
                return irBlockImpl;
            }
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall2 = ExpressionHelpersKt.irCall(jvmIrBuilder, replacementFunction);
            irFunctionAccessExpressionIrCall2.putValueArgument(0, irExpression);
            return irFunctionAccessExpressionIrCall2;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrExpression unwrapImplicitNotNull(IrExpression irExpression) {
        if (!(irExpression instanceof IrTypeOperatorCall)) {
            return irExpression;
        }
        IrTypeOperatorCall irTypeOperatorCall = (IrTypeOperatorCall) irExpression;
        return irTypeOperatorCall.getOperator() == IrTypeOperator.IMPLICIT_NOTNULL ? irTypeOperatorCall.getArgument() : irExpression;
    }
}
