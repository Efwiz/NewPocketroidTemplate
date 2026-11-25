package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.resolve.calls.model.KotlinCall;

/* JADX INFO: compiled from: SimpleArgumentsChecks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo;", "", "isReceiver", "", "shouldReportUnsafeCall", "reportUnsafeCallAsUnsafeImplicitInvoke", "selectorCall", "Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "(ZZZLorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;)V", "()Z", "getReportUnsafeCallAsUnsafeImplicitInvoke", "getSelectorCall", "()Lorg/jetbrains/kotlin/resolve/calls/model/KotlinCall;", "getShouldReportUnsafeCall", "Companion", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReceiverInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ReceiverInfo notReceiver = new ReceiverInfo(false, true, false, null, 8, null);
    private final boolean isReceiver;
    private final boolean reportUnsafeCallAsUnsafeImplicitInvoke;
    private final KotlinCall selectorCall;
    private final boolean shouldReportUnsafeCall;

    public ReceiverInfo(boolean z, boolean z2, boolean z3, KotlinCall kotlinCall) {
        this.isReceiver = z;
        this.shouldReportUnsafeCall = z2;
        this.reportUnsafeCallAsUnsafeImplicitInvoke = z3;
        this.selectorCall = kotlinCall;
    }

    public /* synthetic */ ReceiverInfo(boolean z, boolean z2, boolean z3, KotlinCall kotlinCall, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, z2, z3, (i & 8) != 0 ? null : kotlinCall);
    }

    /* JADX INFO: renamed from: isReceiver, reason: from getter */
    public final boolean getIsReceiver() {
        return this.isReceiver;
    }

    public final boolean getShouldReportUnsafeCall() {
        return this.shouldReportUnsafeCall;
    }

    public final boolean getReportUnsafeCallAsUnsafeImplicitInvoke() {
        return this.reportUnsafeCallAsUnsafeImplicitInvoke;
    }

    public final KotlinCall getSelectorCall() {
        return this.selectorCall;
    }

    /* JADX INFO: compiled from: SimpleArgumentsChecks.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo$Companion;", "", "()V", "notReceiver", "Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo;", "getNotReceiver", "()Lorg/jetbrains/kotlin/resolve/calls/components/ReceiverInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ReceiverInfo getNotReceiver() {
            return ReceiverInfo.notReceiver;
        }
    }
}
