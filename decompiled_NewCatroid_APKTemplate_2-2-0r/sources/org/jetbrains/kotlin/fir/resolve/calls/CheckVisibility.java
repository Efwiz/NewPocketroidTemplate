package org.jetbrains.kotlin.fir.resolve.calls;

import com.badlogic.gdx.graphics.GL20;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.FirVisibilityChecker;
import org.jetbrains.kotlin.fir.FirVisibilityCheckerKt;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;

/* JADX INFO: compiled from: ResolutionStages.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/CheckVisibility;", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckVisibility extends CheckerStage {
    public static final CheckVisibility INSTANCE = new CheckVisibility();

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.resolve.calls.CheckVisibility$check$1, reason: invalid class name */
    /* JADX INFO: compiled from: ResolutionStages.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    @DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.CheckVisibility", f = "ResolutionStages.kt", i = {1, 1, 1, 1}, l = {750, 762, GL20.GL_ONE_MINUS_DST_ALPHA}, m = "check", n = {"candidate", "sink", "visibilityChecker", "declaration"}, s = {"L$0", "L$1", "L$2", "L$3"})
    static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CheckVisibility.this.check(null, null, null, null, this);
        }
    }

    private CheckVisibility() {
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:48:0x0111  */
    /* JADX WARN: Code duplicated, block: B:50:0x011e  */
    /* JADX WARN: Code duplicated, block: B:52:0x012f A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:7:0x001a  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        FirVisibilityChecker visibilityChecker;
        FirDeclaration fir;
        Candidate candidate2;
        CheckerSink checkerSink2 = checkerSink;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object obj = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = anonymousClass1.label;
        if (i != 0) {
            if (i == 1) {
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            if (i == 2) {
                FirDeclaration firDeclaration = (FirDeclaration) anonymousClass1.L$3;
                visibilityChecker = (FirVisibilityChecker) anonymousClass1.L$2;
                CheckerSink checkerSink3 = (CheckerSink) anonymousClass1.L$1;
                candidate2 = (Candidate) anonymousClass1.L$0;
                ResultKt.throwOnFailure(obj);
                fir = firDeclaration;
                checkerSink2 = checkerSink3;
                if (!VisibilityUtilsKt.isVisible$default(visibilityChecker, (FirMemberDeclaration) fir, candidate2.getCallInfo(), null, false, 8, null)) {
                    checkerSink2.reportDiagnostic(VisibilityError.INSTANCE);
                    if (checkerSink2.getNeedYielding()) {
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.L$3 = null;
                        anonymousClass1.label = 3;
                        if (checkerSink2.yield(anonymousClass1) == coroutine_suspended) {
                            return coroutine_suspended;
                        }
                    }
                }
                return Unit.INSTANCE;
            }
            if (i != 3) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ResultKt.throwOnFailure(obj);
        visibilityChecker = FirVisibilityCheckerKt.getVisibilityChecker(callInfo.getSession());
        fir = candidate.getSymbol().getFir();
        if (!(fir instanceof FirMemberDeclaration) || (fir instanceof FirConstructor) || VisibilityUtilsKt.isVisible$default(visibilityChecker, (FirMemberDeclaration) fir, candidate, false, 4, null)) {
            if (fir instanceof FirConstructor) {
                ConeKotlinType type = ((FirResolvedTypeRef) ((FirConstructor) fir).getReturnTypeRef()).getType();
                if (type != null) {
                    FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(((ConeClassLikeType) type).getLookupTag(), resolutionContext.getSession());
                    if (symbol instanceof FirRegularClassSymbol) {
                        if (((FirRegularClass) ((FirRegularClassSymbol) symbol).getFir()).getClassKind().isSingleton()) {
                            checkerSink2.reportDiagnostic(HiddenCandidate.INSTANCE);
                            if (checkerSink.getNeedYielding()) {
                                candidate2 = candidate;
                                anonymousClass1.L$0 = candidate2;
                                anonymousClass1.L$1 = checkerSink2;
                                anonymousClass1.L$2 = visibilityChecker;
                                anonymousClass1.L$3 = fir;
                                anonymousClass1.label = 2;
                                if (checkerSink2.yield(anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            } else {
                                candidate2 = candidate;
                            }
                        } else {
                            candidate2 = candidate;
                        }
                        if (!VisibilityUtilsKt.isVisible$default(visibilityChecker, (FirMemberDeclaration) fir, candidate2.getCallInfo(), null, false, 8, null)) {
                            checkerSink2.reportDiagnostic(VisibilityError.INSTANCE);
                            if (checkerSink2.getNeedYielding()) {
                                anonymousClass1.L$0 = null;
                                anonymousClass1.L$1 = null;
                                anonymousClass1.L$2 = null;
                                anonymousClass1.L$3 = null;
                                anonymousClass1.label = 3;
                                if (checkerSink2.yield(anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeClassLikeType");
                }
            }
            return Unit.INSTANCE;
        }
        checkerSink2.reportDiagnostic(VisibilityError.INSTANCE);
        if (checkerSink.getNeedYielding()) {
            anonymousClass1.label = 1;
            if (checkerSink2.yield(anonymousClass1) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
