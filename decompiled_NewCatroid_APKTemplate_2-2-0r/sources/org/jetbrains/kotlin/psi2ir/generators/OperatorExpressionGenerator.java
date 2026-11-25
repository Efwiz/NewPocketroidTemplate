package org.jetbrains.kotlin.psi2ir.generators;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.PrimitivesKt;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDynamicOperatorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrTypeBaseKt;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtPostfixExpression;
import org.jetbrains.kotlin.psi.KtPrefixExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi2ir.KotlinUtilsKt;
import org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors;
import org.jetbrains.kotlin.psi2ir.intermediate.CallBuilder;
import org.jetbrains.kotlin.psi2ir.intermediate.SafeCallReceiverKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.CommonSuperTypeUtilsKt;
import org.jetbrains.kotlin.resolve.calls.NewCommonSuperTypeCalculator;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.checkers.PrimitiveNumericComparisonInfo;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.evaluate.ConstantExpressionEvaluator;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.IntersectionTypeKt;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: OperatorExpressionGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000®\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0011J\u001a\u0010\u0016\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00112\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013H\u0002J&\u0010\u0018\u001a\u00020\u00102\n\u0010\u0019\u001a\u0006\u0012\u0002\b\u00030\u001a2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0013H\u0002J\u000e\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u001cJ\u0018\u0010\u001d\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u000e\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020 J\u0010\u0010!\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0011H\u0002J\u0010\u0010\"\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0011H\u0002J8\u0010%\u001a\u00020\u00102\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010)\u001a\u00020\u00102\u0006\u0010*\u001a\u00020\u00102\b\u0010+\u001a\u0004\u0018\u00010,J\u0018\u0010-\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u0010.\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020/2\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u0018\u00100\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u0018\u00101\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\u000e\u00102\u001a\u0002032\u0006\u0010\u0015\u001a\u000204J\u000e\u00105\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020/J\u000e\u00106\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u000207J\u0018\u00108\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u0002072\u0006\u0010\u0017\u001a\u00020\u0013H\u0002J\u0018\u00109\u001a\u00020:2\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010;\u001a\u00020\bH\u0002J\u0012\u0010<\u001a\u0004\u0018\u00010,2\u0006\u0010\u000b\u001a\u00020\u0011H\u0002J\u0010\u0010=\u001a\u00020>2\u0006\u0010\u0015\u001a\u00020\fH\u0002J(\u0010?\u001a\u00020\u00102\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\u0006\u0010@\u001a\u00020A2\u0006\u0010B\u001a\u00020\u0010H\u0002J\u0010\u0010C\u001a\u00020D2\u0006\u0010\u000b\u001a\u00020\u0011H\u0002J\u0010\u0010E\u001a\u00020D2\u0006\u0010F\u001a\u00020#H\u0002J\u0014\u0010G\u001a\u0004\u0018\u00010\b2\b\u0010H\u001a\u0004\u0018\u00010>H\u0002J\u0016\u0010I\u001a\u0004\u0018\u00010A*\u00020>2\u0006\u0010J\u001a\u00020>H\u0002J \u0010K\u001a\u00020\u0010*\u00020\f2\b\u0010L\u001a\u0004\u0018\u00010>2\b\u0010M\u001a\u0004\u0018\u00010>H\u0002J \u0010N\u001a\u00020\u0010*\u00020\u00102\b\u0010O\u001a\u0004\u0018\u00010>2\b\u0010J\u001a\u0004\u0018\u00010>H\u0002R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006P"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/OperatorExpressionGenerator;", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGeneratorExtension;", "statementGenerator", "Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;", "(Lorg/jetbrains/kotlin/psi2ir/generators/StatementGenerator;)V", "primitiveTypeMapping", "", "Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "createErrorExpression", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrErrorExpressionImpl;", "ktExpression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "text", "", "generateBinaryBooleanOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "irOperator", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "generateBinaryExpression", "expression", "generateBinaryOperatorAsCall", "origin", "generateCall", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "generateCastExpression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "generateComparisonOperator", "generateDynamicArrayAccess", "ktArrayAccessExpression", "Lorg/jetbrains/kotlin/psi/KtArrayAccessExpression;", "generateDynamicBinaryExpression", "generateDynamicUnaryExpression", "Lorg/jetbrains/kotlin/psi/KtUnaryExpression;", "generateElvis", "generateEquality", "startOffset", "", "endOffset", "arg1", "arg2", "comparisonInfo", "Lorg/jetbrains/kotlin/resolve/checkers/PrimitiveNumericComparisonInfo;", "generateEqualityOperator", "generateExclExclOperator", "Lorg/jetbrains/kotlin/psi/KtPostfixExpression;", "generateIdentityOperator", "generateInOperator", "generateInstanceOfExpression", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "generatePostfixExpression", "generatePrefixExpression", "Lorg/jetbrains/kotlin/psi/KtPrefixExpression;", "generatePrefixOperatorAsCall", "getComparisonOperatorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "primitiveNumericType", "getPrimitiveNumericComparisonInfo", "getResultTypeForElvis", "Lorg/jetbrains/kotlin/types/KotlinType;", "invokeConversionFunction", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "receiver", "isDynamicBinaryOperator", "", "isDynamicUnaryOperator", "ktUnaryExpression", "kotlinTypeToIrType", "kotlinType", "findConversionFunctionTo", "targetType", "generateAsPrimitiveNumericComparisonOperand", "expressionType", "comparisonType", "promoteToPrimitiveNumericType", "operandType", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OperatorExpressionGenerator extends StatementGeneratorExtension {
    private final Map<SimpleType, IrType> primitiveTypeMapping;

    /* JADX INFO: compiled from: OperatorExpressionGenerator.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[IrTypeOperator.values().length];
            try {
                iArr[IrTypeOperator.CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[IrTypeOperator.SAFE_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OperatorExpressionGenerator(StatementGenerator statementGenerator) {
        super(statementGenerator);
        Intrinsics.checkNotNullParameter(statementGenerator, "statementGenerator");
        IrBuiltIns irBuiltIns = getContext().getIrBuiltIns();
        Intrinsics.checkNotNull(irBuiltIns, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
        IrBuiltInsOverDescriptors irBuiltInsOverDescriptors = (IrBuiltInsOverDescriptors) irBuiltIns;
        this.primitiveTypeMapping = MapsKt.toMap(CollectionsKt.zip(irBuiltInsOverDescriptors.getPrimitiveTypes(), irBuiltInsOverDescriptors.getPrimitiveIrTypes()));
    }

    private final IrErrorExpressionImpl createErrorExpression(KtExpression ktExpression, String text) {
        KtExpression ktExpression2 = ktExpression;
        return new IrErrorExpressionImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktExpression2), PsiUtilsKt.getEndOffset(ktExpression2), getContext().getIrBuiltIns().getNothingType(), text);
    }

    public final IrExpression generatePrefixExpression(KtPrefixExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IElementType referencedNameElementType = expression.getOperationReference().getReferencedNameElementType();
        IrStatementOrigin prefixOperator = OperatorConventionsKt.getPrefixOperator(referencedNameElementType);
        if (prefixOperator == null) {
            throw new AssertionError("Unexpected prefix operator: " + referencedNameElementType);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getINCREMENT_DECREMENT_OPERATORS(), prefixOperator)) {
            return new AssignmentGenerator(getStatementGenerator()).generatePrefixIncrementDecrement(expression, prefixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getOPERATORS_DESUGARED_TO_CALLS(), prefixOperator)) {
            return generatePrefixOperatorAsCall(expression, prefixOperator);
        }
        String string = referencedNameElementType.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return createErrorExpression(expression, string);
    }

    public final IrExpression generatePostfixExpression(KtPostfixExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IElementType referencedNameElementType = expression.getOperationReference().getReferencedNameElementType();
        IrStatementOrigin postfixOperator = OperatorConventionsKt.getPostfixOperator(referencedNameElementType);
        if (postfixOperator == null) {
            throw new AssertionError("Unexpected postfix operator: " + referencedNameElementType);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getINCREMENT_DECREMENT_OPERATORS(), postfixOperator)) {
            return new AssignmentGenerator(getStatementGenerator()).generatePostfixIncrementDecrement(expression, postfixOperator);
        }
        if (Intrinsics.areEqual(postfixOperator, IrStatementOrigin.EXCLEXCL.INSTANCE)) {
            return generateExclExclOperator(expression, postfixOperator);
        }
        String string = referencedNameElementType.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return createErrorExpression(expression, string);
    }

    public final IrExpression generateCastExpression(KtBinaryExpressionWithTypeRHS expression) {
        KotlinType kotlinTypeMakeNullable;
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrTypeOperator irTypeOperator = OperatorConventionsKt.getIrTypeOperator(expression.getOperationReference().getReferencedNameElementType());
        WritableSlice<KtTypeReference, KotlinType> TYPE = BindingContext.TYPE;
        Intrinsics.checkNotNullExpressionValue(TYPE, "TYPE");
        KtTypeReference right = expression.getRight();
        Intrinsics.checkNotNull(right);
        KotlinType kotlinType = (KotlinType) GeneratorKt.getOrFail(this, TYPE, right);
        int i = irTypeOperator == null ? -1 : WhenMappings.$EnumSwitchMapping$0[irTypeOperator.ordinal()];
        if (i == 1) {
            kotlinTypeMakeNullable = kotlinType;
        } else if (i == 2) {
            Intrinsics.checkNotNull(kotlinType);
            kotlinTypeMakeNullable = TypeUtilsKt.makeNullable(kotlinType);
        } else {
            throw new AssertionError("Unexpected IrTypeOperator: " + irTypeOperator);
        }
        KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpressionWithTypeRHS);
        int endOffset = PsiUtilsKt.getEndOffset(ktBinaryExpressionWithTypeRHS);
        Intrinsics.checkNotNull(kotlinTypeMakeNullable);
        IrType irType = toIrType(kotlinTypeMakeNullable);
        Intrinsics.checkNotNull(kotlinType);
        IrType irType2 = toIrType(kotlinType);
        KtExpression left = expression.getLeft();
        Intrinsics.checkNotNullExpressionValue(left, "getLeft(...)");
        return new IrTypeOperatorCallImpl(startOffsetSkippingComments, endOffset, irType, irTypeOperator, irType2, genExpr(left));
    }

    public final IrStatement generateInstanceOfExpression(KtIsExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IrTypeOperator irTypeOperator = OperatorConventionsKt.getIrTypeOperator(expression.getOperationReference().getReferencedNameElementType());
        Intrinsics.checkNotNull(irTypeOperator);
        WritableSlice<KtTypeReference, KotlinType> TYPE = BindingContext.TYPE;
        Intrinsics.checkNotNullExpressionValue(TYPE, "TYPE");
        KotlinType kotlinType = (KotlinType) GeneratorKt.getOrFail(this, TYPE, expression.getTypeReference());
        KtIsExpression ktIsExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktIsExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktIsExpression);
        IrType booleanType = getContext().getIrBuiltIns().getBooleanType();
        Intrinsics.checkNotNull(kotlinType);
        IrType irType = toIrType(kotlinType);
        KtExpression leftHandSide = expression.getLeftHandSide();
        Intrinsics.checkNotNullExpressionValue(leftHandSide, "getLeftHandSide(...)");
        return new IrTypeOperatorCallImpl(startOffsetSkippingComments, endOffset, booleanType, irTypeOperator, irType, genExpr(leftHandSide));
    }

    public final IrExpression generateBinaryExpression(KtBinaryExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        IElementType referencedNameElementType = expression.getOperationReference().getReferencedNameElementType();
        if (Intrinsics.areEqual(referencedNameElementType, KtTokens.IDENTIFIER)) {
            return generateBinaryOperatorAsCall(expression, null);
        }
        IrStatementOrigin infixOperator = OperatorConventionsKt.getInfixOperator(referencedNameElementType);
        if (infixOperator == null) {
            throw new AssertionError("Unexpected infix operator: " + referencedNameElementType);
        }
        if (Intrinsics.areEqual(infixOperator, IrStatementOrigin.EQ.INSTANCE)) {
            return new AssignmentGenerator(getStatementGenerator()).generateAssignment(expression, infixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getAUGMENTED_ASSIGNMENTS(), infixOperator)) {
            return new AssignmentGenerator(getStatementGenerator()).generateAugmentedAssignment(expression, infixOperator);
        }
        if (Intrinsics.areEqual(infixOperator, IrStatementOrigin.ELVIS.INSTANCE)) {
            return generateElvis(expression);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getOPERATORS_DESUGARED_TO_CALLS(), infixOperator)) {
            return generateBinaryOperatorAsCall(expression, infixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getCOMPARISON_OPERATORS(), infixOperator)) {
            return generateComparisonOperator(expression, infixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getEQUALITY_OPERATORS(), infixOperator)) {
            return generateEqualityOperator(expression, infixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getIDENTITY_OPERATORS(), infixOperator)) {
            return generateIdentityOperator(expression, infixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getIN_OPERATORS(), infixOperator)) {
            return generateInOperator(expression, infixOperator);
        }
        if (CollectionsKt.contains(OperatorConventionsKt.getBINARY_BOOLEAN_OPERATORS(), infixOperator)) {
            return generateBinaryBooleanOperator(expression, infixOperator);
        }
        String string = referencedNameElementType.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return createErrorExpression(expression, string);
    }

    private final boolean isDynamicUnaryOperator(KtUnaryExpression ktUnaryExpression) {
        KotlinType type;
        KtExpression baseExpression = ktUnaryExpression.getBaseExpression();
        if (baseExpression == null || (type = getContext().getBindingContext().getType(baseExpression)) == null) {
            return false;
        }
        return DynamicTypesKt.isDynamic(type);
    }

    private final boolean isDynamicBinaryOperator(KtBinaryExpression ktExpression) {
        KotlinType type;
        KtExpression left = ktExpression.getLeft();
        if (left == null || (type = getContext().getBindingContext().getType(left)) == null) {
            return false;
        }
        return DynamicTypesKt.isDynamic(type);
    }

    private final IrExpression generateDynamicUnaryExpression(KtUnaryExpression ktExpression) {
        KtExpression baseExpression = ktExpression.getBaseExpression();
        if (baseExpression == null) {
            throw new AssertionError("No arg in " + ktExpression.getText());
        }
        KotlinType type = getContext().getBindingContext().getType(ktExpression);
        if (type == null) {
            throw new AssertionError("No type for " + ktExpression.getText());
        }
        IrDynamicOperator dynamicOperator = DynamicCallsKt.getDynamicOperator(ktExpression);
        KtUnaryExpression ktUnaryExpression = ktExpression;
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(PsiUtilsKt.getStartOffsetSkippingComments(ktUnaryExpression), PsiUtilsKt.getEndOffset(ktUnaryExpression), toIrType(type), dynamicOperator);
        irDynamicOperatorExpressionImpl.setReceiver(genExpr(baseExpression));
        return irDynamicOperatorExpressionImpl;
    }

    private final IrExpression generateDynamicBinaryExpression(KtBinaryExpression ktExpression) {
        KtExpression left = ktExpression.getLeft();
        if (left == null) {
            throw new AssertionError("No LHS in " + ktExpression.getText());
        }
        KtExpression right = ktExpression.getRight();
        if (right == null) {
            throw new AssertionError("No RHS in " + ktExpression.getText());
        }
        KtBinaryExpression ktBinaryExpression = ktExpression;
        KotlinType type = getContext().getBindingContext().getType(ktBinaryExpression);
        if (type == null) {
            throw new AssertionError("No type for " + ktExpression.getText());
        }
        KtBinaryExpression ktBinaryExpression2 = ktExpression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression2);
        int endOffset = PsiUtilsKt.getEndOffset(ktBinaryExpression2);
        IrType irType = toIrType(type);
        if (Intrinsics.areEqual(ktExpression.getOperationToken(), KtTokens.IDENTIFIER)) {
            ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktExpression);
            Intrinsics.checkNotNull(resolvedCall);
            return generateCall(resolvedCall, ktBinaryExpression, null);
        }
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(startOffsetSkippingComments, endOffset, irType, DynamicCallsKt.getDynamicOperator(ktExpression));
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl2 = irDynamicOperatorExpressionImpl;
        IrExpressionsKt.setLeft(irDynamicOperatorExpressionImpl2, genExpr(left));
        IrExpressionsKt.setRight(irDynamicOperatorExpressionImpl2, genExpr(right));
        return irDynamicOperatorExpressionImpl;
    }

    private final KotlinType getResultTypeForElvis(KtExpression expression) {
        KtExpression ktExpressionSafeDeparenthesize = KtPsiUtil.safeDeparenthesize(expression);
        Intrinsics.checkNotNullExpressionValue(ktExpressionSafeDeparenthesize, "safeDeparenthesize(...)");
        KotlinType type = getContext().getBindingContext().getType(ktExpressionSafeDeparenthesize);
        Intrinsics.checkNotNull(type);
        if (ktExpressionSafeDeparenthesize instanceof KtBinaryExpression) {
            KtBinaryExpression ktBinaryExpression = (KtBinaryExpression) ktExpressionSafeDeparenthesize;
            if (Intrinsics.areEqual(ktBinaryExpression.getOperationToken(), KtTokens.ELVIS)) {
                ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktExpressionSafeDeparenthesize);
                Intrinsics.checkNotNull(resolvedCall);
                KotlinType returnType = resolvedCall.getResultingDescriptor().getReturnType();
                Intrinsics.checkNotNull(returnType);
                if (getContext().getLanguageVersionSettings().supportsFeature(LanguageFeature.NewInference) || !KotlinTypeKt.isError(returnType)) {
                    return returnType;
                }
                KtExpression right = ktBinaryExpression.getRight();
                Intrinsics.checkNotNull(right);
                UnwrappedType unwrappedTypeUnwrap = getResultTypeForElvis(right).unwrap();
                KtExpression left = ktBinaryExpression.getLeft();
                Intrinsics.checkNotNull(left);
                IrBuiltIns irBuiltIns = getContext().getIrBuiltIns();
                Intrinsics.checkNotNull(irBuiltIns, "null cannot be cast to non-null type org.jetbrains.kotlin.psi2ir.descriptors.IrBuiltInsOverDescriptors");
                return CommonSuperTypeUtilsKt.commonSuperType(NewCommonSuperTypeCalculator.INSTANCE, CollectionsKt.listOf((Object[]) new UnwrappedType[]{unwrappedTypeUnwrap, IntersectionTypeKt.m7477intersectTypes((List<? extends UnwrappedType>) CollectionsKt.listOf((Object[]) new UnwrappedType[]{getResultTypeForElvis(left).unwrap(), ((IrBuiltInsOverDescriptors) irBuiltIns).getAny()}))}));
            }
        }
        return type;
    }

    private final IrExpression generateElvis(KtBinaryExpression expression) {
        IrType irType = toIrType(getResultTypeForElvis(expression));
        KtExpression left = expression.getLeft();
        Intrinsics.checkNotNull(left);
        IrExpression irExpressionGenExpr = genExpr(left);
        KtExpression right = expression.getRight();
        Intrinsics.checkNotNull(right);
        IrExpression irExpressionGenExpr2 = genExpr(right);
        OperatorExpressionGenerator operatorExpressionGenerator = this;
        KtBinaryExpression ktBinaryExpression = expression;
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(operatorExpressionGenerator.getContext(), operatorExpressionGenerator.getScope(), PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression), PsiUtilsKt.getEndOffset(ktBinaryExpression), IrStatementOrigin.ELVIS.INSTANCE, irType, false, 64, null);
        IrVariable irVariableIrTemporary$default = ExpressionHelpersKt.irTemporary$default(irBlockBuilder, irExpressionGenExpr, "elvis_lhs", null, false, null, 28, null);
        IrBlockBuilder irBlockBuilder2 = irBlockBuilder;
        irBlockBuilder.unaryPlus(ExpressionHelpersKt.irIfNull(irBlockBuilder2, irType, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default.getType(), irVariableIrTemporary$default.getSymbol()), irExpressionGenExpr2, ExpressionHelpersKt.irGet(irBlockBuilder2, irVariableIrTemporary$default.getType(), irVariableIrTemporary$default.getSymbol())));
        return irBlockBuilder.getIrBlockBody();
    }

    private final IrExpression generateBinaryBooleanOperator(KtBinaryExpression ktExpression, IrStatementOrigin irOperator) {
        if (isDynamicBinaryOperator(ktExpression)) {
            return generateDynamicBinaryExpression(ktExpression);
        }
        KtExpression left = ktExpression.getLeft();
        if (left == null) {
            throw new AssertionError("No LHS in " + ktExpression.getText());
        }
        KtExpression right = ktExpression.getRight();
        if (right == null) {
            throw new AssertionError("No RHS in " + ktExpression.getText());
        }
        IrExpression irExpressionGenExpr = genExpr(left);
        IrExpression irExpressionGenExpr2 = genExpr(right);
        KtBinaryExpression ktBinaryExpression = ktExpression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktBinaryExpression);
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.OROR.INSTANCE)) {
            return PrimitivesKt.oror$default(getContext(), startOffsetSkippingComments, endOffset, irExpressionGenExpr, irExpressionGenExpr2, null, 16, null);
        }
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.ANDAND.INSTANCE)) {
            return PrimitivesKt.andand$default(getContext(), startOffsetSkippingComments, endOffset, irExpressionGenExpr, irExpressionGenExpr2, null, 16, null);
        }
        throw new AssertionError("Unexpected binary boolean operator " + irOperator);
    }

    private final IrExpression generateInOperator(KtBinaryExpression expression, IrStatementOrigin irOperator) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, expression);
        Intrinsics.checkNotNull(resolvedCall);
        IrExpression irExpressionGenerateCall = generateCall(resolvedCall, expression, irOperator);
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.IN.INSTANCE)) {
            return irExpressionGenerateCall;
        }
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.NOT_IN.INSTANCE)) {
            KtBinaryExpression ktBinaryExpression = expression;
            return PrimitivesKt.primitiveOp1(PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression), PsiUtilsKt.getEndOffset(ktBinaryExpression), getContext().getIrBuiltIns().getBooleanNotSymbol(), getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.NOT_IN.INSTANCE, irExpressionGenerateCall);
        }
        throw new AssertionError("Unexpected in-operator " + irOperator);
    }

    private final IrExpression generateIdentityOperator(KtBinaryExpression expression, IrStatementOrigin irOperator) {
        if (isDynamicBinaryOperator(expression)) {
            return generateDynamicBinaryExpression(expression);
        }
        KtExpression left = expression.getLeft();
        Intrinsics.checkNotNull(left);
        IrExpression irExpressionGenExpr = genExpr(left);
        KtExpression right = expression.getRight();
        Intrinsics.checkNotNull(right);
        KtBinaryExpression ktBinaryExpression = expression;
        IrExpression irExpressionPrimitiveOp2 = PrimitivesKt.primitiveOp2(PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression), PsiUtilsKt.getEndOffset(ktBinaryExpression), getContext().getIrBuiltIns().getEqeqeqSymbol(), getContext().getIrBuiltIns().getBooleanType(), irOperator, irExpressionGenExpr, genExpr(right));
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.EQEQEQ.INSTANCE)) {
            return irExpressionPrimitiveOp2;
        }
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.EXCLEQEQ.INSTANCE)) {
            return PrimitivesKt.primitiveOp1(PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression), PsiUtilsKt.getEndOffset(ktBinaryExpression), getContext().getIrBuiltIns().getBooleanNotSymbol(), getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.EXCLEQEQ.INSTANCE, irExpressionPrimitiveOp2);
        }
        throw new AssertionError("Unexpected identity operator " + irOperator);
    }

    private final IrExpression generateAsPrimitiveNumericComparisonOperand(KtExpression ktExpression, KotlinType kotlinType, KotlinType kotlinType2) {
        return promoteToPrimitiveNumericType(genExpr(ktExpression), kotlinType, kotlinType2);
    }

    private final PrimitiveNumericComparisonInfo getPrimitiveNumericComparisonInfo(KtBinaryExpression ktExpression) {
        return (PrimitiveNumericComparisonInfo) getContext().getBindingContext().get(BindingContext.PRIMITIVE_NUMERIC_COMPARISON_INFO, ktExpression);
    }

    private final IrExpression generateEqualityOperator(KtBinaryExpression expression, IrStatementOrigin irOperator) {
        if (isDynamicBinaryOperator(expression)) {
            return generateDynamicBinaryExpression(expression);
        }
        PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo = getPrimitiveNumericComparisonInfo(expression);
        KotlinType comparisonType = primitiveNumericComparisonInfo != null ? primitiveNumericComparisonInfo.getComparisonType() : null;
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> ieee754equalsFunByOperandType = getContext().getIrBuiltIns().getIeee754equalsFunByOperandType();
        IrType irTypeKotlinTypeToIrType = kotlinTypeToIrType(comparisonType);
        IrSimpleFunctionSymbol eqeqSymbol = ieee754equalsFunByOperandType.get(irTypeKotlinTypeToIrType != null ? IrTypesKt.getClassifierOrNull(irTypeKotlinTypeToIrType) : null);
        if (eqeqSymbol == null) {
            eqeqSymbol = getContext().getIrBuiltIns().getEqeqSymbol();
        }
        KtBinaryExpression ktBinaryExpression = expression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktBinaryExpression);
        IrType booleanType = getContext().getIrBuiltIns().getBooleanType();
        KtExpression left = expression.getLeft();
        Intrinsics.checkNotNull(left);
        IrExpression irExpressionGenerateAsPrimitiveNumericComparisonOperand = generateAsPrimitiveNumericComparisonOperand(left, primitiveNumericComparisonInfo != null ? primitiveNumericComparisonInfo.getLeftPrimitiveType() : null, comparisonType);
        KtExpression right = expression.getRight();
        Intrinsics.checkNotNull(right);
        IrExpression irExpressionPrimitiveOp2 = PrimitivesKt.primitiveOp2(startOffsetSkippingComments, endOffset, eqeqSymbol, booleanType, irOperator, irExpressionGenerateAsPrimitiveNumericComparisonOperand, generateAsPrimitiveNumericComparisonOperand(right, primitiveNumericComparisonInfo != null ? primitiveNumericComparisonInfo.getRightPrimitiveType() : null, comparisonType));
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.EQEQ.INSTANCE)) {
            return irExpressionPrimitiveOp2;
        }
        if (Intrinsics.areEqual(irOperator, IrStatementOrigin.EXCLEQ.INSTANCE)) {
            return PrimitivesKt.primitiveOp1(PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression), PsiUtilsKt.getEndOffset(ktBinaryExpression), getContext().getIrBuiltIns().getBooleanNotSymbol(), getContext().getIrBuiltIns().getBooleanType(), IrStatementOrigin.EXCLEQ.INSTANCE, irExpressionPrimitiveOp2);
        }
        throw new AssertionError("Unexpected equality operator " + irOperator);
    }

    public final IrExpression generateEquality(int startOffset, int endOffset, IrStatementOrigin irOperator, IrExpression arg1, IrExpression arg2, PrimitiveNumericComparisonInfo comparisonInfo) {
        Intrinsics.checkNotNullParameter(irOperator, "irOperator");
        Intrinsics.checkNotNullParameter(arg1, "arg1");
        Intrinsics.checkNotNullParameter(arg2, "arg2");
        if (comparisonInfo != null) {
            KotlinType comparisonType = comparisonInfo.getComparisonType();
            Map<IrClassifierSymbol, IrSimpleFunctionSymbol> ieee754equalsFunByOperandType = getContext().getIrBuiltIns().getIeee754equalsFunByOperandType();
            IrType irTypeKotlinTypeToIrType = kotlinTypeToIrType(comparisonType);
            IrSimpleFunctionSymbol eqeqSymbol = ieee754equalsFunByOperandType.get(irTypeKotlinTypeToIrType != null ? IrTypesKt.getClassifierOrNull(irTypeKotlinTypeToIrType) : null);
            if (eqeqSymbol == null) {
                eqeqSymbol = getContext().getIrBuiltIns().getEqeqSymbol();
            }
            return PrimitivesKt.primitiveOp2(startOffset, endOffset, eqeqSymbol, getContext().getIrBuiltIns().getBooleanType(), irOperator, promoteToPrimitiveNumericType(arg1, comparisonInfo.getLeftPrimitiveType(), comparisonType), promoteToPrimitiveNumericType(arg2, comparisonInfo.getRightPrimitiveType(), comparisonType));
        }
        return PrimitivesKt.primitiveOp2(startOffset, endOffset, getContext().getIrBuiltIns().getEqeqSymbol(), getContext().getIrBuiltIns().getBooleanType(), irOperator, arg1, arg2);
    }

    private final IrExpression promoteToPrimitiveNumericType(final IrExpression irExpression, KotlinType kotlinType, final KotlinType kotlinType2) {
        if (kotlinType2 == null) {
            return irExpression;
        }
        if (kotlinType == null) {
            throw new AssertionError("operandType should be non-null");
        }
        KotlinType kotlinTypeMakeNotNullable = TypeUtilsKt.makeNotNullable(kotlinType);
        final FunctionDescriptor functionDescriptorFindConversionFunctionTo = findConversionFunctionTo(kotlinTypeMakeNotNullable, kotlinType2);
        if (!TypeUtilsKt.isPrimitiveNumberType(kotlinTypeMakeNotNullable)) {
            throw new AssertionError("Primitive number type or nullable primitive number type expected: " + irExpression.getType());
        }
        if (Intrinsics.areEqual(kotlinType, kotlinType2) || Intrinsics.areEqual(kotlinTypeMakeNotNullable, kotlinType2)) {
            return irExpression;
        }
        KotlinType originalKotlinType = IrTypeBaseKt.getOriginalKotlinType(irExpression.getType());
        Intrinsics.checkNotNull(originalKotlinType);
        if (KotlinUtilsKt.containsNull(originalKotlinType)) {
            return SafeCallReceiverKt.safeCallOnDispatchReceiver(irExpression, this, irExpression.getStartOffset(), irExpression.getEndOffset(), new Function1<IrExpression, IrExpression>() { // from class: org.jetbrains.kotlin.psi2ir.generators.OperatorExpressionGenerator.promoteToPrimitiveNumericType.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final IrExpression invoke(IrExpression dispatchReceiver) {
                    Intrinsics.checkNotNullParameter(dispatchReceiver, "dispatchReceiver");
                    OperatorExpressionGenerator operatorExpressionGenerator = OperatorExpressionGenerator.this;
                    int startOffset = irExpression.getStartOffset();
                    int endOffset = irExpression.getEndOffset();
                    FunctionDescriptor functionDescriptor = functionDescriptorFindConversionFunctionTo;
                    if (functionDescriptor != null) {
                        return operatorExpressionGenerator.invokeConversionFunction(startOffset, endOffset, functionDescriptor, dispatchReceiver);
                    }
                    throw new AssertionError("No conversion function for " + irExpression.getType() + " ~> " + kotlinType2);
                }
            });
        }
        int startOffset = irExpression.getStartOffset();
        int endOffset = irExpression.getEndOffset();
        if (functionDescriptorFindConversionFunctionTo != null) {
            return invokeConversionFunction(startOffset, endOffset, functionDescriptorFindConversionFunctionTo, irExpression);
        }
        throw new AssertionError("No conversion function for " + irExpression.getType() + " ~> " + kotlinType2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression invokeConversionFunction(int startOffset, int endOffset, FunctionDescriptor functionDescriptor, IrExpression receiver) {
        DescriptorSymbolTableExtension descriptorExtension = getContext().getSymbolTable().getDescriptorExtension();
        FunctionDescriptor original = functionDescriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        IrSimpleFunctionSymbol irSimpleFunctionSymbolReferenceSimpleFunction = descriptorExtension.referenceSimpleFunction(original);
        IrCallImpl.Companion companion = IrCallImpl.INSTANCE;
        KotlinType returnType = functionDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        IrCallImpl irCallImplFromSymbolDescriptor = companion.fromSymbolDescriptor(startOffset, endOffset, toIrType(returnType), irSimpleFunctionSymbolReferenceSimpleFunction, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor()) : 0, (176 & 32) != 0 ? irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getValueParameters().size() + irSimpleFunctionSymbolReferenceSimpleFunction.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : null, (176 & 128) != 0 ? null : null);
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImplFromSymbolDescriptor, functionDescriptor);
        irCallImplFromSymbolDescriptor.setDispatchReceiver(receiver);
        return irCallImplFromSymbolDescriptor;
    }

    private final FunctionDescriptor findConversionFunctionTo(KotlinType kotlinType, KotlinType kotlinType2) {
        Name name2;
        String strAsString;
        ClassifierDescriptor this$0 = kotlinType2.getConstructor().getThis$0();
        if (this$0 == null || (name2 = this$0.getShortName()) == null || (strAsString = name2.asString()) == null) {
            return null;
        }
        MemberScope memberScope = kotlinType.getMemberScope();
        Name nameIdentifier = Name.identifier("to" + strAsString);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return KotlinUtilsKt.findSingleFunction(memberScope, nameIdentifier);
    }

    private final IrType kotlinTypeToIrType(KotlinType kotlinType) {
        if (kotlinType != null) {
            return this.primitiveTypeMapping.get(kotlinType);
        }
        return null;
    }

    private final IrExpression generateComparisonOperator(KtBinaryExpression ktExpression, IrStatementOrigin origin) {
        if (isDynamicBinaryOperator(ktExpression)) {
            return generateDynamicBinaryExpression(ktExpression);
        }
        KtBinaryExpression ktBinaryExpression = ktExpression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression);
        int endOffset = PsiUtilsKt.getEndOffset(ktBinaryExpression);
        PrimitiveNumericComparisonInfo primitiveNumericComparisonInfo = getPrimitiveNumericComparisonInfo(ktExpression);
        KtExpression left = ktExpression.getLeft();
        if (left == null) {
            throw new AssertionError("No LHS in " + ktExpression.getText());
        }
        KtExpression right = ktExpression.getRight();
        if (right == null) {
            throw new AssertionError("No RHS in " + ktExpression.getText());
        }
        if (primitiveNumericComparisonInfo != null) {
            KotlinType comparisonType = primitiveNumericComparisonInfo.getComparisonType();
            IrType irTypeKotlinTypeToIrType = kotlinTypeToIrType(comparisonType);
            if (irTypeKotlinTypeToIrType != null) {
                return PrimitivesKt.primitiveOp2(startOffsetSkippingComments, endOffset, getComparisonOperatorSymbol(origin, irTypeKotlinTypeToIrType), getContext().getIrBuiltIns().getBooleanType(), origin, generateAsPrimitiveNumericComparisonOperand(left, primitiveNumericComparisonInfo.getLeftPrimitiveType(), primitiveNumericComparisonInfo.getComparisonType()), generateAsPrimitiveNumericComparisonOperand(right, primitiveNumericComparisonInfo.getRightPrimitiveType(), primitiveNumericComparisonInfo.getComparisonType()));
            }
            throw new IllegalStateException((comparisonType + " expected to be primitive").toString());
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, ktExpression);
        if (resolvedCall == null) {
            throw new AssertionError("No resolved call for comparison operator " + ktExpression.getText());
        }
        return PrimitivesKt.primitiveOp2(startOffsetSkippingComments, endOffset, getComparisonOperatorSymbol(origin, getContext().getIrBuiltIns().getIntType()), getContext().getIrBuiltIns().getBooleanType(), origin, generateCall(resolvedCall, ktExpression, origin), IrConstImpl.INSTANCE.m7211int(startOffsetSkippingComments, endOffset, getContext().getIrBuiltIns().getIntType(), 0));
    }

    private final IrExpression generateCall(ResolvedCall<?> resolvedCall, KtExpression ktExpression, IrStatementOrigin origin) {
        return CallGeneratorKt.generateCall$default(new CallGenerator(getStatementGenerator()), ktExpression, ArgumentsGenerationUtilsKt.pregenerateCall(getStatementGenerator(), resolvedCall), origin, 0, 0, 24, null);
    }

    private final IrSimpleFunctionSymbol getComparisonOperatorSymbol(IrStatementOrigin origin, IrType primitiveNumericType) {
        Map<IrClassifierSymbol, IrSimpleFunctionSymbol> greaterOrEqualFunByOperandType;
        if (Intrinsics.areEqual(origin, IrStatementOrigin.LT.INSTANCE)) {
            greaterOrEqualFunByOperandType = getContext().getIrBuiltIns().getLessFunByOperandType();
        } else if (Intrinsics.areEqual(origin, IrStatementOrigin.LTEQ.INSTANCE)) {
            greaterOrEqualFunByOperandType = getContext().getIrBuiltIns().getLessOrEqualFunByOperandType();
        } else if (Intrinsics.areEqual(origin, IrStatementOrigin.GT.INSTANCE)) {
            greaterOrEqualFunByOperandType = getContext().getIrBuiltIns().getGreaterFunByOperandType();
        } else {
            if (!Intrinsics.areEqual(origin, IrStatementOrigin.GTEQ.INSTANCE)) {
                throw new AssertionError("Unexpected comparison operator: " + origin);
            }
            greaterOrEqualFunByOperandType = getContext().getIrBuiltIns().getGreaterOrEqualFunByOperandType();
        }
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = greaterOrEqualFunByOperandType.get(IrTypesKt.getClassifierOrFail(primitiveNumericType));
        Intrinsics.checkNotNull(irSimpleFunctionSymbol);
        return irSimpleFunctionSymbol;
    }

    private final IrExpression generateExclExclOperator(KtPostfixExpression expression, IrStatementOrigin origin) {
        KtExpression baseExpression = expression.getBaseExpression();
        Intrinsics.checkNotNull(baseExpression);
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(baseExpression);
        Intrinsics.checkNotNull(ktExpressionDeparenthesize);
        IrExpression irExpressionGenExpr = genExpr(ktExpressionDeparenthesize);
        KtSimpleNameExpression operationReference = expression.getOperationReference();
        Intrinsics.checkNotNullExpressionValue(operationReference, "getOperationReference(...)");
        KotlinType type = getContext().getBindingContext().getType(ktExpressionDeparenthesize);
        if (type == null) {
            throw new AssertionError("No type for !! argument");
        }
        KotlinType kotlinTypeStripEnhancedNullability = getContext().getExtensions().getEnhancedNullability().stripEnhancedNullability(TypeUtilsKt.makeNotNullable(type));
        FunctionDescriptor descriptor = getContext().getIrBuiltIns().getCheckNotNullSymbol().getDescriptor();
        FunctionDescriptor functionDescriptorSubstitute = descriptor.substitute(TypeSubstitutor.create((Map<TypeConstructor, TypeProjection>) MapsKt.mapOf(TuplesKt.to(descriptor.getTypeParameters().get(0).getTypeConstructor(), new TypeProjectionImpl(type)))));
        if (functionDescriptorSubstitute == null) {
            throw new AssertionError("Substitution failed for " + descriptor + ": T=" + type);
        }
        IrType irType = toIrType(kotlinTypeStripEnhancedNullability);
        IrSimpleFunctionSymbol checkNotNullSymbol = getContext().getIrBuiltIns().getCheckNotNullSymbol();
        KtSimpleNameExpression ktSimpleNameExpression = operationReference;
        IrCallImpl irCallImplFromSymbolDescriptor = IrCallImpl.INSTANCE.fromSymbolDescriptor(PsiUtilsKt.getStartOffsetSkippingComments(ktSimpleNameExpression), PsiUtilsKt.getEndOffset(ktSimpleNameExpression), irType, checkNotNullSymbol, (176 & 16) != 0 ? IrExpressionsKt.getTypeParametersCount(checkNotNullSymbol.getDescriptor()) : 0, (176 & 32) != 0 ? checkNotNullSymbol.getDescriptor().getValueParameters().size() + checkNotNullSymbol.getDescriptor().getContextReceiverParameters().size() : 0, (176 & 64) != 0 ? null : origin, (176 & 128) != 0 ? null : null);
        getContext().getCallToSubstitutedDescriptorMap$ir_psi2ir().put(irCallImplFromSymbolDescriptor, functionDescriptorSubstitute);
        irCallImplFromSymbolDescriptor.putTypeArgument(0, irType);
        irCallImplFromSymbolDescriptor.putValueArgument(0, irExpressionGenExpr);
        return irCallImplFromSymbolDescriptor;
    }

    private final IrExpression generateBinaryOperatorAsCall(KtBinaryExpression expression, IrStatementOrigin origin) {
        if (isDynamicBinaryOperator(expression)) {
            return generateDynamicBinaryExpression(expression);
        }
        StatementGenerator statementGenerator = getStatementGenerator();
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, expression);
        Intrinsics.checkNotNull(resolvedCall);
        CallBuilder callBuilderPregenerateCall = ArgumentsGenerationUtilsKt.pregenerateCall(statementGenerator, resolvedCall);
        CallGenerator callGenerator = new CallGenerator(getStatementGenerator());
        CallableDescriptor descriptor = callBuilderPregenerateCall.getDescriptor();
        FunctionDescriptor functionDescriptor = descriptor instanceof FunctionDescriptor ? (FunctionDescriptor) descriptor : null;
        if (functionDescriptor == null) {
            throw new AssertionError("Operator call resolved to a non-function: " + callBuilderPregenerateCall.getDescriptor());
        }
        KtBinaryExpression ktBinaryExpression = expression;
        return callGenerator.generateFunctionCall$ir_psi2ir(functionDescriptor, PsiUtilsKt.getStartOffsetSkippingComments(ktBinaryExpression), PsiUtilsKt.getEndOffset(ktBinaryExpression), origin, callBuilderPregenerateCall);
    }

    private final IrExpression generatePrefixOperatorAsCall(KtPrefixExpression expression, IrStatementOrigin origin) {
        KtPrefixExpression ktPrefixExpression;
        CompileTimeConstant<?> constant;
        ResolvedCall<? extends CallableDescriptor> resolvedCall = GeneratorKt.getResolvedCall(this, expression);
        Intrinsics.checkNotNull(resolvedCall);
        if ((expression.getBaseExpression() instanceof KtConstantExpression) && (constant = ConstantExpressionEvaluator.INSTANCE.getConstant((ktPrefixExpression = expression), getContext().getBindingContext())) != null) {
            ReceiverValue dispatchReceiver = resolvedCall.getDispatchReceiver();
            KotlinType type = dispatchReceiver != null ? dispatchReceiver.getType() : null;
            if (type != null && KotlinBuiltIns.isPrimitiveType(type)) {
                return getStatementGenerator().generateConstantExpression(ktPrefixExpression, constant);
            }
        }
        KtPrefixExpression ktPrefixExpression2 = expression;
        return isDynamicUnaryOperator(ktPrefixExpression2) ? generateDynamicUnaryExpression(ktPrefixExpression2) : generateCall(resolvedCall, expression, origin);
    }

    public final IrExpression generateDynamicArrayAccess(KtArrayAccessExpression ktArrayAccessExpression) {
        Intrinsics.checkNotNullParameter(ktArrayAccessExpression, "ktArrayAccessExpression");
        KtArrayAccessExpression ktArrayAccessExpression2 = ktArrayAccessExpression;
        int startOffsetSkippingComments = PsiUtilsKt.getStartOffsetSkippingComments(ktArrayAccessExpression2);
        int endOffset = PsiUtilsKt.getEndOffset(ktArrayAccessExpression2);
        KotlinType type = getContext().getBindingContext().getType(ktArrayAccessExpression);
        if (type == null) {
            throw new AssertionError("No type for " + ktArrayAccessExpression.getText());
        }
        IrDynamicOperatorExpressionImpl irDynamicOperatorExpressionImpl = new IrDynamicOperatorExpressionImpl(startOffsetSkippingComments, endOffset, toIrType(type), IrDynamicOperator.ARRAY_ACCESS);
        KtExpression arrayExpression = ktArrayAccessExpression.getArrayExpression();
        Intrinsics.checkNotNull(arrayExpression);
        irDynamicOperatorExpressionImpl.setReceiver(genExpr(arrayExpression));
        List<KtExpression> indexExpressions = ktArrayAccessExpression.getIndexExpressions();
        Intrinsics.checkNotNullExpressionValue(indexExpressions, "getIndexExpressions(...)");
        List<IrExpression> arguments = irDynamicOperatorExpressionImpl.getArguments();
        for (KtExpression ktExpression : indexExpressions) {
            Intrinsics.checkNotNull(ktExpression);
            arguments.add(genExpr(ktExpression));
        }
        return irDynamicOperatorExpressionImpl;
    }
}
