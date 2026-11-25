package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BindingTraceFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 \t2\u00020\u0001:\u0001\tB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/resolve/BindingTraceFilter;", "", "ignoreDiagnostics", "", "(Z)V", "getIgnoreDiagnostics", "()Z", "includesEverythingIn", "otherFilter", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BindingTraceFilter {
    private final boolean ignoreDiagnostics;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final BindingTraceFilter ACCEPT_ALL = new BindingTraceFilter(false);
    private static final BindingTraceFilter NO_DIAGNOSTICS = new BindingTraceFilter(true);

    /* JADX INFO: compiled from: BindingTraceFilter.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/BindingTraceFilter$Companion;", "", "()V", "ACCEPT_ALL", "Lorg/jetbrains/kotlin/resolve/BindingTraceFilter;", "getACCEPT_ALL", "()Lorg/jetbrains/kotlin/resolve/BindingTraceFilter;", "NO_DIAGNOSTICS", "getNO_DIAGNOSTICS", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BindingTraceFilter getACCEPT_ALL() {
            return BindingTraceFilter.ACCEPT_ALL;
        }

        public final BindingTraceFilter getNO_DIAGNOSTICS() {
            return BindingTraceFilter.NO_DIAGNOSTICS;
        }
    }

    public BindingTraceFilter(boolean z) {
        this.ignoreDiagnostics = z;
    }

    public final boolean getIgnoreDiagnostics() {
        return this.ignoreDiagnostics;
    }

    public final boolean includesEverythingIn(BindingTraceFilter otherFilter) {
        Intrinsics.checkNotNullParameter(otherFilter, "otherFilter");
        return !this.ignoreDiagnostics || otherFilter.ignoreDiagnostics;
    }
}
