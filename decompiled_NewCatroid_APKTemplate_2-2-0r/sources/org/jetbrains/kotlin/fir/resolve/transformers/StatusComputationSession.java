package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClass;

/* JADX INFO: compiled from: FirStatusResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005J\u0011\u0010\u000b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0005H\u0086\u0002J\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;", "", "()V", "statusMap", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession$StatusComputationStatus;", "computeOnlyClassStatus", "", "klass", "endComputing", "get", "startComputing", "StatusComputationStatus", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StatusComputationSession {
    private final Map<FirClass, StatusComputationStatus> statusMap = MapsKt.withDefaultMutable(new LinkedHashMap(), new Function1<FirClass, StatusComputationStatus>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.StatusComputationSession$statusMap$1
        @Override // kotlin.jvm.functions.Function1
        public final StatusComputationSession.StatusComputationStatus invoke(FirClass it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return StatusComputationSession.StatusComputationStatus.NotComputed;
        }
    });

    public final StatusComputationStatus get(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return (StatusComputationStatus) MapsKt.getValue(this.statusMap, klass);
    }

    public final StatusComputationStatus startComputing(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Map<FirClass, StatusComputationStatus> map = this.statusMap;
        StatusComputationStatus statusComputationStatus = map.get(klass);
        if (statusComputationStatus == null) {
            statusComputationStatus = StatusComputationStatus.Computing;
            map.put(klass, statusComputationStatus);
        }
        return statusComputationStatus;
    }

    public final void endComputing(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.statusMap.put(klass, StatusComputationStatus.Computed);
    }

    public final void computeOnlyClassStatus(FirClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (((StatusComputationStatus) MapsKt.getValue(this.statusMap, klass)).compareTo(StatusComputationStatus.ComputedOnlyClassStatus) < 0) {
            this.statusMap.put(klass, StatusComputationStatus.ComputedOnlyClassStatus);
        }
    }

    /* JADX INFO: compiled from: FirStatusResolveTransformer.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession$StatusComputationStatus;", "", "requiresComputation", "", "(Ljava/lang/String;IZ)V", "getRequiresComputation", "()Z", "NotComputed", "Computing", "ComputedOnlyClassStatus", "Computed", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum StatusComputationStatus {
        NotComputed(true),
        Computing(false),
        ComputedOnlyClassStatus(true),
        Computed(false);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean requiresComputation;

        public static EnumEntries<StatusComputationStatus> getEntries() {
            return $ENTRIES;
        }

        StatusComputationStatus(boolean z) {
            this.requiresComputation = z;
        }

        public final boolean getRequiresComputation() {
            return this.requiresComputation;
        }
    }
}
