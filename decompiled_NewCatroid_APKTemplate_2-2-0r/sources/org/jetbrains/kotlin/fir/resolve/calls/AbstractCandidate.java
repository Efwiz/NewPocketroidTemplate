package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.calls.inference.model.ConstraintSystemError;
import org.jetbrains.kotlin.resolve.calls.tasks.ExplicitReceiverKind;
import org.jetbrains.kotlin.resolve.calls.tower.CandidateApplicability;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: AbstractCandidate.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u0004\u0018\u00010\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u000eR\u0018\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0010X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0013R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u001d\u001a\u0006\u0012\u0002\b\u00030\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 ¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCandidate;", "", "()V", "applicability", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "getApplicability", "()Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateApplicability;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "getCallInfo", "()Lorg/jetbrains/kotlin/fir/resolve/calls/AbstractCallInfo;", "chosenExtensionReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "getChosenExtensionReceiver", "()Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "diagnostics", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionDiagnostic;", "getDiagnostics", "()Ljava/util/List;", "dispatchReceiver", "getDispatchReceiver", "errors", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/ConstraintSystemError;", "getErrors", "explicitReceiverKind", "Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", "getExplicitReceiverKind", "()Lorg/jetbrains/kotlin/resolve/calls/tasks/ExplicitReceiverKind;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractCandidate {
    /* JADX INFO: renamed from: getApplicability */
    public abstract CandidateApplicability getCurrentApplicability();

    public abstract AbstractCallInfo getCallInfo();

    public abstract FirExpression getChosenExtensionReceiver();

    public abstract List<ResolutionDiagnostic> getDiagnostics();

    public abstract FirExpression getDispatchReceiver();

    public abstract List<ConstraintSystemError> getErrors();

    public abstract ExplicitReceiverKind getExplicitReceiverKind();

    public abstract FirBasedSymbol<?> getSymbol();
}
