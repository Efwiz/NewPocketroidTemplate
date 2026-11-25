package org.jetbrains.kotlin.resolve.calls.smartcasts;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ConditionalDataFlowInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000J\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0000R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ConditionalDataFlowInfo;", "", "thenInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "elseInfo", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;)V", "getElseInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "getThenInfo", "and", "other", "or", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConditionalDataFlowInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ConditionalDataFlowInfo EMPTY = new ConditionalDataFlowInfo(DataFlowInfo.INSTANCE.getEMPTY(), 0 == true ? 1 : 0, 2, 0 == true ? 1 : 0);
    private final DataFlowInfo elseInfo;
    private final DataFlowInfo thenInfo;

    public ConditionalDataFlowInfo(DataFlowInfo thenInfo, DataFlowInfo elseInfo) {
        Intrinsics.checkNotNullParameter(thenInfo, "thenInfo");
        Intrinsics.checkNotNullParameter(elseInfo, "elseInfo");
        this.thenInfo = thenInfo;
        this.elseInfo = elseInfo;
    }

    public /* synthetic */ ConditionalDataFlowInfo(DataFlowInfo dataFlowInfo, DataFlowInfo dataFlowInfo2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(dataFlowInfo, (i & 2) != 0 ? dataFlowInfo : dataFlowInfo2);
    }

    public final DataFlowInfo getElseInfo() {
        return this.elseInfo;
    }

    public final DataFlowInfo getThenInfo() {
        return this.thenInfo;
    }

    public final ConditionalDataFlowInfo and(ConditionalDataFlowInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        ConditionalDataFlowInfo conditionalDataFlowInfo = EMPTY;
        if (Intrinsics.areEqual(this, conditionalDataFlowInfo)) {
            return other;
        }
        return Intrinsics.areEqual(other, conditionalDataFlowInfo) ? this : new ConditionalDataFlowInfo(this.thenInfo.and(other.thenInfo), this.elseInfo.and(other.elseInfo));
    }

    public final ConditionalDataFlowInfo or(ConditionalDataFlowInfo other) {
        Intrinsics.checkNotNullParameter(other, "other");
        ConditionalDataFlowInfo conditionalDataFlowInfo = EMPTY;
        if (Intrinsics.areEqual(this, conditionalDataFlowInfo)) {
            return other;
        }
        return Intrinsics.areEqual(other, conditionalDataFlowInfo) ? this : new ConditionalDataFlowInfo(this.thenInfo.or(other.thenInfo), this.elseInfo.or(other.elseInfo));
    }

    /* JADX INFO: compiled from: ConditionalDataFlowInfo.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ConditionalDataFlowInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ConditionalDataFlowInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/ConditionalDataFlowInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ConditionalDataFlowInfo getEMPTY() {
            return ConditionalDataFlowInfo.EMPTY;
        }
    }
}
