package org.jetbrains.kotlin.resolve.calls.tower;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;

/* JADX INFO: compiled from: TowerResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u0003\u0004\u0005\u0006\u0007\bB\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0006\t\n\u000b\f\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "", "()V", "BothTowerLevelAndContextReceiversGroup", "BothTowerLevelAndImplicitReceiver", "Empty", "ForLookupForNoExplicitReceiver", "OnlyImplicitReceiver", "TowerLevel", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$BothTowerLevelAndContextReceiversGroup;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$BothTowerLevelAndImplicitReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$Empty;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$ForLookupForNoExplicitReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$OnlyImplicitReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$TowerLevel;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TowerData {
    public /* synthetic */ TowerData(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$Empty;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "()V", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Empty extends TowerData {
        public static final Empty INSTANCE = new Empty();

        private Empty() {
            super(null);
        }
    }

    private TowerData() {
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$OnlyImplicitReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", "implicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OnlyImplicitReceiver extends TowerData {
        private final ReceiverValueWithSmartCastInfo implicitReceiver;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OnlyImplicitReceiver(ReceiverValueWithSmartCastInfo implicitReceiver) {
            super(null);
            Intrinsics.checkNotNullParameter(implicitReceiver, "implicitReceiver");
            this.implicitReceiver = implicitReceiver;
        }

        public final ReceiverValueWithSmartCastInfo getImplicitReceiver() {
            return this.implicitReceiver;
        }
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$TowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", FirebaseAnalytics.Param.LEVEL, "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;)V", "getLevel", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TowerLevel extends TowerData {
        private final ScopeTowerLevel level;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TowerLevel(ScopeTowerLevel level) {
            super(null);
            Intrinsics.checkNotNullParameter(level, "level");
            this.level = level;
        }

        public final ScopeTowerLevel getLevel() {
            return this.level;
        }
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$BothTowerLevelAndImplicitReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", FirebaseAnalytics.Param.LEVEL, "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "implicitReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;)V", "getImplicitReceiver", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "getLevel", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BothTowerLevelAndImplicitReceiver extends TowerData {
        private final ReceiverValueWithSmartCastInfo implicitReceiver;
        private final ScopeTowerLevel level;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BothTowerLevelAndImplicitReceiver(ScopeTowerLevel level, ReceiverValueWithSmartCastInfo implicitReceiver) {
            super(null);
            Intrinsics.checkNotNullParameter(level, "level");
            Intrinsics.checkNotNullParameter(implicitReceiver, "implicitReceiver");
            this.level = level;
            this.implicitReceiver = implicitReceiver;
        }

        public final ReceiverValueWithSmartCastInfo getImplicitReceiver() {
            return this.implicitReceiver;
        }

        public final ScopeTowerLevel getLevel() {
            return this.level;
        }
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$BothTowerLevelAndContextReceiversGroup;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", FirebaseAnalytics.Param.LEVEL, "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "contextReceiversGroup", "", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;Ljava/util/List;)V", "getContextReceiversGroup", "()Ljava/util/List;", "getLevel", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BothTowerLevelAndContextReceiversGroup extends TowerData {
        private final List<ReceiverValueWithSmartCastInfo> contextReceiversGroup;
        private final ScopeTowerLevel level;

        public final ScopeTowerLevel getLevel() {
            return this.level;
        }

        public final List<ReceiverValueWithSmartCastInfo> getContextReceiversGroup() {
            return this.contextReceiversGroup;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BothTowerLevelAndContextReceiversGroup(ScopeTowerLevel level, List<ReceiverValueWithSmartCastInfo> contextReceiversGroup) {
            super(null);
            Intrinsics.checkNotNullParameter(level, "level");
            Intrinsics.checkNotNullParameter(contextReceiversGroup, "contextReceiversGroup");
            this.level = level;
            this.contextReceiversGroup = contextReceiversGroup;
        }
    }

    /* JADX INFO: compiled from: TowerResolver.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData$ForLookupForNoExplicitReceiver;", "Lorg/jetbrains/kotlin/resolve/calls/tower/TowerData;", FirebaseAnalytics.Param.LEVEL, "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;)V", "getLevel", "()Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeTowerLevel;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ForLookupForNoExplicitReceiver extends TowerData {
        private final ScopeTowerLevel level;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ForLookupForNoExplicitReceiver(ScopeTowerLevel level) {
            super(null);
            Intrinsics.checkNotNullParameter(level, "level");
            this.level = level;
        }

        public final ScopeTowerLevel getLevel() {
            return this.level;
        }
    }
}
