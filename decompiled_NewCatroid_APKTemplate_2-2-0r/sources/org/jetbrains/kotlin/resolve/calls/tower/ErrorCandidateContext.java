package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.receivers.DetailedReceiver;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: ErrorCandidateFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000fR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidateContext;", "", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "name", "Lorg/jetbrains/kotlin/name/Name;", "explicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;)V", "getExplicitReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/DetailedReceiver;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "result", "Lorg/jetbrains/kotlin/utils/SmartList;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ErrorCandidate;", "getResult", "()Lorg/jetbrains/kotlin/utils/SmartList;", "getScopeTower", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", ProductAction.ACTION_ADD, "", "errorCandidate", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class ErrorCandidateContext {
    private final DetailedReceiver explicitReceiver;
    private final Name name;
    private final SmartList<ErrorCandidate<?>> result;
    private final ImplicitScopeTower scopeTower;

    public ErrorCandidateContext(ImplicitScopeTower scopeTower, Name name2, DetailedReceiver detailedReceiver) {
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.scopeTower = scopeTower;
        this.name = name2;
        this.explicitReceiver = detailedReceiver;
        this.result = new SmartList<>();
    }

    public final ImplicitScopeTower getScopeTower() {
        return this.scopeTower;
    }

    public final Name getName() {
        return this.name;
    }

    public final DetailedReceiver getExplicitReceiver() {
        return this.explicitReceiver;
    }

    public final SmartList<ErrorCandidate<?>> getResult() {
        return this.result;
    }

    public final void add(ErrorCandidate<?> errorCandidate) {
        Intrinsics.checkNotNullParameter(errorCandidate, "errorCandidate");
        this.result.add(errorCandidate);
    }
}
