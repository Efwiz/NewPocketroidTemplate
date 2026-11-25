package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.util.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.calls.util.ResolveArgumentsMode;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.ExpressionReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/types/expressions/DoubleColonExpressionResolver$ResolutionResultsAndTraceCommitCallback;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1", f = "DoubleColonExpressionResolver.kt", i = {0, 2}, l = {785, 792, ParserBasicInformation.NUM_RULES, 811}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "$this$sequence"}, s = {"L$0", "L$0"})
final class DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super DoubleColonExpressionResolver.ResolutionResultsAndTraceCommitCallback>, Continuation<? super Unit>, Object> {
    final /* synthetic */ ResolutionContext<?> $c;
    final /* synthetic */ KtCallableReferenceExpression $expression;
    final /* synthetic */ DoubleColonLHS $lhs;
    final /* synthetic */ KotlinType $lhsType;
    final /* synthetic */ ResolveArgumentsMode $mode;
    final /* synthetic */ KtSimpleNameExpression $reference;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DoubleColonExpressionResolver this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1(DoubleColonLHS doubleColonLHS, KotlinType kotlinType, ResolutionContext<?> resolutionContext, KtCallableReferenceExpression ktCallableReferenceExpression, DoubleColonExpressionResolver doubleColonExpressionResolver, KtSimpleNameExpression ktSimpleNameExpression, ResolveArgumentsMode resolveArgumentsMode, Continuation<? super DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1> continuation) {
        super(2, continuation);
        this.$lhs = doubleColonLHS;
        this.$lhsType = kotlinType;
        this.$c = resolutionContext;
        this.$expression = ktCallableReferenceExpression;
        this.this$0 = doubleColonExpressionResolver;
        this.$reference = ktSimpleNameExpression;
        this.$mode = resolveArgumentsMode;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1 doubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1 = new DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1(this.$lhs, this.$lhsType, this.$c, this.$expression, this.this$0, this.$reference, this.$mode, continuation);
        doubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1.L$0 = obj;
        return doubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super DoubleColonExpressionResolver.ResolutionResultsAndTraceCommitCallback> sequenceScope, Continuation<? super Unit> continuation) {
        return ((DoubleColonExpressionResolver$resolveCallableReferenceRHS$resultSequence$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:27:0x00c9 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:35:0x0117  */
    /* JADX WARN: Code duplicated, block: B:37:0x0129  */
    /* JADX WARN: Code duplicated, block: B:38:0x0130  */
    /* JADX WARN: Code duplicated, block: B:45:0x0162 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        SequenceScope sequenceScope;
        ClassifierDescriptor this$0;
        KtExpression receiverExpression;
        KtExpression calleeExpressionIfAny;
        ClassQualifier classQualifier;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            DoubleColonLHS doubleColonLHS = this.$lhs;
            if (doubleColonLHS instanceof DoubleColonLHS.Type) {
                if (!(this.$lhsType.getConstructor().getThis$0() instanceof ClassDescriptor)) {
                    this.$c.trace.report(Errors.CALLABLE_REFERENCE_LHS_NOT_A_CLASS.on(this.$expression));
                    return Unit.INSTANCE;
                }
                BindingTrace bindingTrace = this.$c.trace;
                WritableSlice<KtExpression, Qualifier> writableSlice = BindingContext.QUALIFIER;
                KtExpression receiverExpression2 = this.$expression.getReceiverExpression();
                Intrinsics.checkNotNull(receiverExpression2);
                Qualifier qualifier = (Qualifier) bindingTrace.get(writableSlice, receiverExpression2);
                if (qualifier instanceof ClassQualifier) {
                    this.L$0 = sequenceScope;
                    this.label = 1;
                    if (CollectionsKt.yieldIfNotNull(sequenceScope, this.this$0.tryResolveRHSWithReceiver("resolve unbound callable reference in static scope", qualifier, this.$reference, this.$c, this.$mode, this.$lhs), this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                }
                this.L$0 = null;
                this.label = 2;
                if (CollectionsKt.yieldIfNotNull(sequenceScope, this.this$0.tryResolveRHSWithReceiver("resolve unbound callable reference with receiver", new TransientReceiver(this.$lhsType), this.$reference, this.$c, this.$mode, this.$lhs), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else if (doubleColonLHS instanceof DoubleColonLHS.Expression) {
                ExpressionReceiver.Companion companion = ExpressionReceiver.INSTANCE;
                KtExpression receiverExpression3 = this.$expression.getReceiverExpression();
                Intrinsics.checkNotNull(receiverExpression3);
                KotlinType kotlinType = this.$lhsType;
                BindingContext context = this.$c.trace.getContext();
                Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                ExpressionReceiver expressionReceiverCreate = companion.create(receiverExpression3, kotlinType, context);
                this.L$0 = sequenceScope;
                this.label = 3;
                if (CollectionsKt.yieldIfNotNull(sequenceScope, this.this$0.tryResolveRHSWithReceiver("resolve bound callable reference", expressionReceiverCreate, this.$reference, this.$c, this.$mode, this.$lhs), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                if (((DoubleColonLHS.Expression) this.$lhs).getIsObjectQualifier()) {
                    this$0 = this.$lhsType.getConstructor().getThis$0();
                    receiverExpression = this.$expression.getReceiverExpression();
                    if (receiverExpression != null) {
                        calleeExpressionIfAny = PsiUtilsKt.getCalleeExpressionIfAny(receiverExpression);
                    } else {
                        calleeExpressionIfAny = null;
                    }
                    if (calleeExpressionIfAny instanceof KtSimpleNameExpression) {
                        classQualifier = new ClassQualifier((KtSimpleNameExpression) calleeExpressionIfAny, (ClassDescriptor) this$0);
                        this.L$0 = null;
                        this.label = 4;
                        if (CollectionsKt.yieldIfNotNull(sequenceScope, this.this$0.tryResolveRHSWithReceiver("resolve object callable reference in static scope", classQualifier, this.$reference, this.$c, this.$mode, this.$lhs), this) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                }
            }
        } else if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    sequenceScope = (SequenceScope) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    if (((DoubleColonLHS.Expression) this.$lhs).getIsObjectQualifier()) {
                        this$0 = this.$lhsType.getConstructor().getThis$0();
                        receiverExpression = this.$expression.getReceiverExpression();
                        if (receiverExpression != null) {
                            calleeExpressionIfAny = PsiUtilsKt.getCalleeExpressionIfAny(receiverExpression);
                        } else {
                            calleeExpressionIfAny = null;
                        }
                        if ((calleeExpressionIfAny instanceof KtSimpleNameExpression) && (this$0 instanceof ClassDescriptor)) {
                            classQualifier = new ClassQualifier((KtSimpleNameExpression) calleeExpressionIfAny, (ClassDescriptor) this$0);
                            this.L$0 = null;
                            this.label = 4;
                            if (CollectionsKt.yieldIfNotNull(sequenceScope, this.this$0.tryResolveRHSWithReceiver("resolve object callable reference in static scope", classQualifier, this.$reference, this.$c, this.$mode, this.$lhs), this) == coroutine_suspended) {
                                return coroutine_suspended;
                            }
                        }
                    }
                } else if (i != 4) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
            }
            ResultKt.throwOnFailure(obj);
        } else {
            sequenceScope = (SequenceScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            this.L$0 = null;
            this.label = 2;
            if (CollectionsKt.yieldIfNotNull(sequenceScope, this.this$0.tryResolveRHSWithReceiver("resolve unbound callable reference with receiver", new TransientReceiver(this.$lhsType), this.$reference, this.$c, this.$mode, this.$lhs), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
