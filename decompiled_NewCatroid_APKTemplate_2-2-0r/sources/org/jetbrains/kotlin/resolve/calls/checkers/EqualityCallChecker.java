package org.jetbrains.kotlin.resolve.calls.checkers;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.inference.BuilderInferenceSession;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.Nullability;
import org.jetbrains.kotlin.types.EnumCompatibilityCheckerKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.expressions.OperatorConventions;
import org.jetbrains.kotlin.types.expressions.SenselessComparisonChecker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: EqualityCallChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J(\u0010\u000b\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\f\u001a\u00020\r2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0002J\u001a\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u000eJ\u001c\u0010\u0010\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0002J\u001c\u0010\u0011\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0002J\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0002¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/checkers/EqualityCallChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "()V", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "checkEquality", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "checkIdentityEquality", "checkIdentityOnPrimitiveOrInlineClassTypes", "ensureNonemptyIntersectionOfOperandTypes", "isIdentityComparedWithImplicitBoxing", "", "leftType", "Lorg/jetbrains/kotlin/types/KotlinType;", "rightType", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EqualityCallChecker implements CallChecker {
    public static final EqualityCallChecker INSTANCE = new EqualityCallChecker();

    private EqualityCallChecker() {
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        KtElement callElement = resolvedCall.getCall().getCallElement();
        KtBinaryExpression ktBinaryExpression = callElement instanceof KtBinaryExpression ? (KtBinaryExpression) callElement : null;
        if (ktBinaryExpression == null) {
            return;
        }
        IElementType referencedNameElementType = ktBinaryExpression.getOperationReference().getReferencedNameElementType();
        ImmutableSet<KtSingleValueToken> EQUALS_OPERATIONS = OperatorConventions.EQUALS_OPERATIONS;
        Intrinsics.checkNotNullExpressionValue(EQUALS_OPERATIONS, "EQUALS_OPERATIONS");
        if (CollectionsKt.contains(EQUALS_OPERATIONS, referencedNameElementType)) {
            checkEquality(resolvedCall, ktBinaryExpression, context.getResolutionContext());
        }
    }

    private final void checkEquality(ResolvedCall<?> resolvedCall, KtBinaryExpression expression, ResolutionContext<?> context) {
        KotlinType returnType = resolvedCall.getResultingDescriptor().getReturnType();
        if (returnType == null) {
            return;
        }
        KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(returnType);
        if (!builtIns.isBooleanOrSubtype(returnType)) {
            context.trace.report(Errors.RESULT_TYPE_MISMATCH.on(expression.getOperationReference(), FormatHelper.QUOTE + OperatorNameConventions.EQUALS + '\'', builtIns.getBooleanType(), returnType));
        } else {
            ensureNonemptyIntersectionOfOperandTypes(expression, context);
        }
    }

    public final void checkIdentityEquality(KtBinaryExpression expression, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        ensureNonemptyIntersectionOfOperandTypes(expression, context);
        checkIdentityOnPrimitiveOrInlineClassTypes(expression, context);
    }

    private final void checkIdentityOnPrimitiveOrInlineClassTypes(KtBinaryExpression expression, ResolutionContext<?> context) {
        KtExpression right;
        KotlinType type;
        KotlinType type2;
        KtExpression left = expression.getLeft();
        if (left == null || (right = expression.getRight()) == null || (type = context.trace.getType(left)) == null || (type2 = context.trace.getType(right)) == null) {
            return;
        }
        if (KotlinTypeChecker.DEFAULT.equalTypes(type, type2)) {
            if (KotlinBuiltIns.isPrimitiveType(type)) {
                context.trace.report(Errors.DEPRECATED_IDENTITY_EQUALS.on(expression, type, type2));
            }
        } else if (isIdentityComparedWithImplicitBoxing(type, type2) || isIdentityComparedWithImplicitBoxing(type2, type)) {
            context.trace.report(Errors.IMPLICIT_BOXING_IN_IDENTITY_EQUALS.on(expression, type, type2));
        }
        if (InlineClassesUtilsKt.isInlineClassType(type) || InlineClassesUtilsKt.isInlineClassType(type2)) {
            context.trace.report(Errors.FORBIDDEN_IDENTITY_EQUALS.on(expression, type, type2));
        }
    }

    private final boolean isIdentityComparedWithImplicitBoxing(KotlinType leftType, KotlinType rightType) {
        return KotlinBuiltIns.isPrimitiveType(leftType) && !KotlinBuiltIns.isPrimitiveType(rightType) && KotlinTypeChecker.DEFAULT.isSubtypeOf(leftType, rightType);
    }

    private final void ensureNonemptyIntersectionOfOperandTypes(KtBinaryExpression expression, ResolutionContext<?> context) {
        KtExpression right;
        KotlinType type;
        KotlinType type2;
        KtExpression left = expression.getLeft();
        if (left == null || (right = expression.getRight()) == null || (type = context.trace.getType(left)) == null || (type2 = context.trace.getType(right)) == null) {
            return;
        }
        if (TypeIntersector.isIntersectionEmpty(type, type2)) {
            context.trace.report(((context.languageVersionSettings.supportsFeature(LanguageFeature.ProperEqualityChecksInBuilderInferenceCalls) || !(context.inferenceSession instanceof BuilderInferenceSession) || context.trace.get(BindingContext.MARKED_EQUALIY_CALL_PROPER_IN_BUILDER_INFERENCE, expression) == null) ? Errors.EQUALITY_NOT_APPLICABLE : Errors.EQUALITY_NOT_APPLICABLE_WARNING).on(expression, expression.getOperationReference(), type, type2));
        } else {
            EnumCompatibilityCheckerKt.checkEnumsForCompatibility(context, expression, type, type2);
        }
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(trace);
        DataFlowInfo dataFlowInfo = context.dataFlowInfo;
        Intrinsics.checkNotNullExpressionValue(dataFlowInfo, "dataFlowInfo");
        SenselessComparisonChecker.checkSenselessComparisonWithNull(expression, left, right, context, anonymousClass1, new AnonymousClass2(dataFlowInfo));
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.checkers.EqualityCallChecker$ensureNonemptyIntersectionOfOperandTypes$1, reason: invalid class name */
    /* JADX INFO: compiled from: EqualityCallChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<KtExpression, KotlinType> {
        AnonymousClass1(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getType(Lorg/jetbrains/kotlin/psi/KtExpression;)Lorg/jetbrains/kotlin/types/KotlinType;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getType";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(BindingTrace.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final KotlinType invoke(KtExpression p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((BindingTrace) this.receiver).getType(p0);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.checkers.EqualityCallChecker$ensureNonemptyIntersectionOfOperandTypes$2, reason: invalid class name */
    /* JADX INFO: compiled from: EqualityCallChecker.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<DataFlowValue, Nullability> {
        AnonymousClass2(Object obj) {
            super(1, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "getStableNullability(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;)Lorg/jetbrains/kotlin/resolve/calls/smartcasts/Nullability;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "getStableNullability";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DataFlowInfo.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Nullability invoke(DataFlowValue p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return ((DataFlowInfo) this.receiver).getStableNullability(p0);
        }
    }
}
