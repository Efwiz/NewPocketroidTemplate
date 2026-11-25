package org.jetbrains.kotlin.fir.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.calls.ImplicitReceiverValueForScript;
import org.jetbrains.kotlin.fir.scopes.FirScope;

/* JADX INFO: compiled from: ScriptScopes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/TowerElementsForScript;", "", "implicitReceivers", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/ImplicitReceiverValueForScript;", "staticScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirScope;)V", "getImplicitReceivers", "()Ljava/util/List;", "getStaticScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerElementsForScript {
    private final List<ImplicitReceiverValueForScript> implicitReceivers;
    private final FirScope staticScope;

    public TowerElementsForScript(List<ImplicitReceiverValueForScript> implicitReceivers, FirScope firScope) {
        Intrinsics.checkNotNullParameter(implicitReceivers, "implicitReceivers");
        this.implicitReceivers = implicitReceivers;
        this.staticScope = firScope;
    }

    public final List<ImplicitReceiverValueForScript> getImplicitReceivers() {
        return this.implicitReceivers;
    }

    public final FirScope getStaticScope() {
        return this.staticScope;
    }
}
