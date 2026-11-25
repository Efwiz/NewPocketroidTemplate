package org.jetbrains.kotlin.contracts.parsing;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.contracts.description.BooleanExpression;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.BooleanVariableReference;
import org.jetbrains.kotlin.contracts.description.expressions.ConstantReference;
import org.jetbrains.kotlin.contracts.description.expressions.ContractDescriptionValue;
import org.jetbrains.kotlin.contracts.description.expressions.IsInstancePredicate;
import org.jetbrains.kotlin.contracts.description.expressions.IsNullPredicate;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalAnd;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalNot;
import org.jetbrains.kotlin.contracts.description.expressions.LogicalOr;
import org.jetbrains.kotlin.contracts.description.expressions.VariableReference;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.descriptors.impl.AbstractTypeParameterDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedType;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.CastDiagnosticsUtil;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: PsiConditionParser.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u001d\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0012\u0010\u000b\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\rH\u0002J*\u0010\u000e\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u001f\u0010\u0016\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010\u0018J!\u0010\u0019\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\u001a2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001bJ!\u0010\u001c\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\u001d2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003H\u0016¢\u0006\u0002\u0010\u001eJ\u001f\u0010\u001f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010!J\u001f\u0010\"\u001a\u0004\u0018\u00010\u00022\u0006\u0010#\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010$J\u001f\u0010%\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020&2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010'J\u001f\u0010(\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020)2\u0006\u0010\u0017\u001a\u00020\u0003H\u0016¢\u0006\u0002\u0010*R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/contracts/parsing/PsiConditionParser;", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "", "collector", "Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;", "callContext", "Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;", "dispatcher", "Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;", "(Lorg/jetbrains/kotlin/contracts/parsing/ContractParsingDiagnosticsCollector;Lorg/jetbrains/kotlin/contracts/parsing/ContractCallContext;Lorg/jetbrains/kotlin/contracts/parsing/PsiContractParserDispatcher;)V", "parseIdentityEquals", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "processEquals", "left", "Lorg/jetbrains/kotlin/contracts/description/expressions/ContractDescriptionValue;", "right", "isNegated", "", "reportOn", "Lorg/jetbrains/kotlin/psi/KtElement;", "visitBinaryExpression", "data", "(Lorg/jetbrains/kotlin/psi/KtBinaryExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "visitCallExpression", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "(Lorg/jetbrains/kotlin/psi/KtCallExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "visitConstantExpression", "Lorg/jetbrains/kotlin/psi/KtConstantExpression;", "(Lorg/jetbrains/kotlin/psi/KtConstantExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "visitIsExpression", "Lorg/jetbrains/kotlin/psi/KtIsExpression;", "(Lorg/jetbrains/kotlin/psi/KtIsExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "visitKtElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Lorg/jetbrains/kotlin/psi/KtElement;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "visitParenthesizedExpression", "Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;", "(Lorg/jetbrains/kotlin/psi/KtParenthesizedExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "visitUnaryExpression", "Lorg/jetbrains/kotlin/psi/KtUnaryExpression;", "(Lorg/jetbrains/kotlin/psi/KtUnaryExpression;Lkotlin/Unit;)Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PsiConditionParser extends KtVisitor<BooleanExpression, Unit> {
    private final ContractCallContext callContext;
    private final ContractParsingDiagnosticsCollector collector;
    private final PsiContractParserDispatcher dispatcher;

    public PsiConditionParser(ContractParsingDiagnosticsCollector collector, ContractCallContext callContext, PsiContractParserDispatcher dispatcher) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(callContext, "callContext");
        Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
        this.collector = collector;
        this.callContext = callContext;
        this.dispatcher = dispatcher;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitIsExpression(KtIsExpression expression, Unit data) {
        KtTypeReference typeReference;
        KotlinType kotlinType;
        UnwrappedType unwrappedTypeUnwrap;
        boolean zSupportsFeature;
        String str;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        VariableReference variable = this.dispatcher.parseVariable(expression.getLeftHandSide());
        if (variable == null || (typeReference = expression.getTypeReference()) == null || (kotlinType = (KotlinType) this.callContext.getBindingContext().get(BindingContext.TYPE, typeReference)) == null || (unwrappedTypeUnwrap = kotlinType.unwrap()) == null) {
            return null;
        }
        ClassifierDescriptor this$0 = unwrappedTypeUnwrap.getConstructor().getThis$0();
        if (unwrappedTypeUnwrap instanceof CapturedType) {
            this.collector.badDescription("references to captured types are forbidden in contracts", typeReference);
            return null;
        }
        if ((this$0 instanceof AbstractTypeParameterDescriptor) && (!(zSupportsFeature = this.callContext.getLanguageVersionSettings().supportsFeature(LanguageFeature.AllowReifiedGenericsInContracts)) || !((AbstractTypeParameterDescriptor) this$0).isReified())) {
            if (zSupportsFeature) {
                str = "references to not reified type parameters are forbidden in contracts";
            } else {
                str = "references to type parameters are forbidden in contracts";
            }
            this.collector.badDescription(str, typeReference);
            return null;
        }
        KotlinType type = variable.getDescriptor().getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        UnwrappedType unwrappedType = unwrappedTypeUnwrap;
        KotlinTypeChecker DEFAULT = KotlinTypeChecker.DEFAULT;
        Intrinsics.checkNotNullExpressionValue(DEFAULT, "DEFAULT");
        if (CastDiagnosticsUtil.isCastErased(type, unwrappedType, DEFAULT)) {
            return null;
        }
        return new IsInstancePredicate(variable, unwrappedType, expression.isNegated());
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitKtElement(KtElement element, Unit data) {
        CallableDescriptor resultingDescriptor;
        ContractDescriptionValue value;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(element, this.callContext.getBindingContext());
        if (resolvedCall != null && (resultingDescriptor = resolvedCall.getResultingDescriptor()) != null) {
            if (resultingDescriptor instanceof ValueDescriptor) {
                VariableReference variable = this.dispatcher.parseVariable(element instanceof KtExpression ? (KtExpression) element : null);
                if (variable == null) {
                    return null;
                }
                return (BooleanExpression) (variable instanceof BooleanVariableReference ? (BooleanVariableReference) variable : null);
            }
            if (PsiContractsUtilsKt.isEqualsDescriptor(resultingDescriptor)) {
                PsiContractParserDispatcher psiContractParserDispatcher = this.dispatcher;
                ReceiverValue receiverValueMo7304getDispatchReceiver = resolvedCall.getDispatchReceiver();
                ExpressionReceiver expressionReceiver = receiverValueMo7304getDispatchReceiver instanceof ExpressionReceiver ? (ExpressionReceiver) receiverValueMo7304getDispatchReceiver : null;
                ContractDescriptionValue value2 = psiContractParserDispatcher.parseValue(expressionReceiver != null ? expressionReceiver.getExpression() : null);
                if (value2 == null || (value = this.dispatcher.parseValue(PsiContractsUtilsKt.firstArgumentAsExpressionOrNull(resolvedCall))) == null) {
                    return null;
                }
                KtBinaryExpression ktBinaryExpression = element instanceof KtBinaryExpression ? (KtBinaryExpression) element : null;
                Object operationToken = ktBinaryExpression != null ? ktBinaryExpression.getOperationToken() : null;
                Object obj = KtTokens.EXCLEQ;
                if (obj == null) {
                    obj = false;
                }
                return processEquals(value2, value, Intrinsics.areEqual(operationToken, obj), element);
            }
            this.collector.badDescription("unsupported construction", element);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitConstantExpression(KtConstantExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        ConstantReference constant = this.dispatcher.parseConstant(expression);
        return constant instanceof BooleanConstantReference ? (BooleanConstantReference) constant : null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitCallExpression(KtCallExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        this.collector.badDescription("call-expressions are not supported yet", expression);
        return null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.contracts.parsing.PsiConditionParser$visitBinaryExpression$1, reason: invalid class name */
    /* JADX INFO: compiled from: PsiConditionParser.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<BooleanExpression, BooleanExpression, LogicalAnd> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(LogicalAnd.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LogicalAnd invoke(BooleanExpression p0, BooleanExpression p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new LogicalAnd(p0, p1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitBinaryExpression(KtBinaryExpression expression, Unit data) {
        AnonymousClass2 anonymousClass2;
        KtExpression right;
        BooleanExpression booleanExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        IElementType operationToken = expression.getOperationToken();
        if (Intrinsics.areEqual(operationToken, KtTokens.ANDAND)) {
            anonymousClass2 = AnonymousClass1.INSTANCE;
        } else {
            if (!Intrinsics.areEqual(operationToken, KtTokens.OROR)) {
                return Intrinsics.areEqual(operationToken, KtTokens.EXCLEQEQEQ) ? true : Intrinsics.areEqual(operationToken, KtTokens.EQEQEQ) ? parseIdentityEquals(expression) : (BooleanExpression) super.visitBinaryExpression(expression, data);
            }
            anonymousClass2 = AnonymousClass2.INSTANCE;
        }
        KtExpression left = expression.getLeft();
        if (left != null) {
            PsiConditionParser psiConditionParser = this;
            BooleanExpression booleanExpression2 = (BooleanExpression) left.accept(psiConditionParser, data);
            if (booleanExpression2 != null && (right = expression.getRight()) != null && (booleanExpression = (BooleanExpression) right.accept(psiConditionParser, data)) != null) {
                return (BooleanExpression) anonymousClass2.invoke(booleanExpression2, booleanExpression);
            }
        }
        return null;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.contracts.parsing.PsiConditionParser$visitBinaryExpression$2, reason: invalid class name */
    /* JADX INFO: compiled from: PsiConditionParser.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function2<BooleanExpression, BooleanExpression, LogicalOr> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        AnonymousClass2() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;Lorg/jetbrains/kotlin/contracts/description/BooleanExpression;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(LogicalOr.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final LogicalOr invoke(BooleanExpression p0, BooleanExpression p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new LogicalOr(p0, p1);
        }
    }

    private final BooleanExpression parseIdentityEquals(KtBinaryExpression expression) {
        ContractDescriptionValue value;
        ContractDescriptionValue value2 = this.dispatcher.parseValue(expression.getLeft());
        if (value2 == null || (value = this.dispatcher.parseValue(expression.getRight())) == null) {
            return null;
        }
        return processEquals(value2, value, Intrinsics.areEqual(expression.getOperationToken(), KtTokens.EXCLEQEQEQ), expression);
    }

    private final BooleanExpression processEquals(ContractDescriptionValue left, ContractDescriptionValue right, boolean isNegated, KtElement reportOn) {
        if ((left instanceof ConstantReference) && Intrinsics.areEqual(left, ConstantReference.INSTANCE.getNULL()) && (right instanceof VariableReference)) {
            return new IsNullPredicate((VariableReference) right, isNegated);
        }
        if ((right instanceof ConstantReference) && Intrinsics.areEqual(right, ConstantReference.INSTANCE.getNULL()) && (left instanceof VariableReference)) {
            return new IsNullPredicate((VariableReference) left, isNegated);
        }
        this.collector.badDescription("only equality comparisons with 'null' allowed", reportOn);
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitUnaryExpression(KtUnaryExpression expression, Unit data) {
        BooleanExpression booleanExpression;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        if (!Intrinsics.areEqual(expression.getOperationToken(), KtTokens.EXCL)) {
            return (BooleanExpression) super.visitUnaryExpression(expression, data);
        }
        KtExpression baseExpression = expression.getBaseExpression();
        if (baseExpression == null || (booleanExpression = (BooleanExpression) baseExpression.accept(this, data)) == null) {
            return null;
        }
        if (!(booleanExpression instanceof ContractDescriptionValue)) {
            ContractParsingDiagnosticsCollector contractParsingDiagnosticsCollector = this.collector;
            KtExpression baseExpression2 = expression.getBaseExpression();
            Intrinsics.checkNotNull(baseExpression2);
            contractParsingDiagnosticsCollector.badDescription("negations in contract description can be applied only to variables/values", baseExpression2);
        }
        return new LogicalNot(booleanExpression);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public BooleanExpression visitParenthesizedExpression(KtParenthesizedExpression expression, Unit data) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(data, "data");
        KtExpression ktExpressionDeparenthesize = KtPsiUtil.deparenthesize(expression);
        if (ktExpressionDeparenthesize != null) {
            return (BooleanExpression) ktExpressionDeparenthesize.accept(this, data);
        }
        return null;
    }
}
