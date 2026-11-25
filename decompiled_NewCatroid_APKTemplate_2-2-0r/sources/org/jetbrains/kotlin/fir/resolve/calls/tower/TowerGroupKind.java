package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TowerGroup.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u000e\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0000H\u0096\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\b\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "", "index", "", "(B)V", "getIndex", "()B", "compareTo", "", "other", "Classifier", "Companion", "ContextReceiverGroup", "ImplicitOrNonLocal", "InvokeExtensionWithImplicitReceiver", "Last", "Local", "Member", "Qualifier", "QualifierValue", "Start", "TopPrioritized", "UnqualifiedEnum", "WithDepth", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Classifier;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$InvokeExtensionWithImplicitReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Last;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Member;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Qualifier;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$QualifierValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Start;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TowerGroupKind implements Comparable<TowerGroupKind> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final byte index;

    public /* synthetic */ TowerGroupKind(byte b, DefaultConstructorMarker defaultConstructorMarker) {
        this(b);
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0096\u0002J\b\u0010\r\u001a\u00020\u0005H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "index", "", ConfigConstants.CONFIG_KEY_DEPTH, "", "(BI)V", "getDepth", "()I", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class WithDepth extends TowerGroupKind {
        private final int depth;

        public WithDepth(byte b, int i) {
            super(b, null);
            this.depth = i;
        }

        public final int getDepth() {
            return this.depth;
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroupKind.WithDepth");
            WithDepth withDepth = (WithDepth) other;
            return getIndex() == withDepth.getIndex() && this.depth == withDepth.depth;
        }

        public int hashCode() {
            return (this.depth * 31) + getIndex();
        }
    }

    private TowerGroupKind(byte b) {
        this.index = b;
    }

    public final byte getIndex() {
        return this.index;
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Start;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Start extends TowerGroupKind {
        public static final Start INSTANCE = new Start();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Start)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 1335258056;
        }

        public String toString() {
            return "Start";
        }

        private Start() {
            super((byte) 0, null);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Qualifier;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Qualifier extends TowerGroupKind {
        public static final Qualifier INSTANCE = new Qualifier();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Qualifier)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -1206160112;
        }

        public String toString() {
            return "Qualifier";
        }

        private Qualifier() {
            super((byte) 1, null);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Classifier;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Classifier extends TowerGroupKind {
        public static final Classifier INSTANCE = new Classifier();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Classifier)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return 1013719675;
        }

        public String toString() {
            return "Classifier";
        }

        private Classifier() {
            super((byte) 2, null);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$TopPrioritized;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", ConfigConstants.CONFIG_KEY_DEPTH, "", "(I)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TopPrioritized extends WithDepth {
        public TopPrioritized(int i) {
            super((byte) 3, i);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Member;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Member extends TowerGroupKind {
        public static final Member INSTANCE = new Member();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Member)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -1741959180;
        }

        public String toString() {
            return "Member";
        }

        private Member() {
            super((byte) 4, null);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$InvokeExtensionWithImplicitReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class InvokeExtensionWithImplicitReceiver extends TowerGroupKind {
        public static final InvokeExtensionWithImplicitReceiver INSTANCE = new InvokeExtensionWithImplicitReceiver();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof InvokeExtensionWithImplicitReceiver)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -1687627257;
        }

        public String toString() {
            return "InvokeExtensionWithImplicitReceiver";
        }

        private InvokeExtensionWithImplicitReceiver() {
            super((byte) 5, null);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Local;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", ConfigConstants.CONFIG_KEY_DEPTH, "", "(I)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Local extends WithDepth {
        public Local(int i) {
            super((byte) 6, i);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$ImplicitOrNonLocal;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", ConfigConstants.CONFIG_KEY_DEPTH, "", "kindForDebugSake", "", "(ILjava/lang/String;)V", "getKindForDebugSake", "()Ljava/lang/String;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitOrNonLocal extends WithDepth {
        private final String kindForDebugSake;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ImplicitOrNonLocal(int i, String kindForDebugSake) {
            super((byte) 7, i);
            Intrinsics.checkNotNullParameter(kindForDebugSake, "kindForDebugSake");
            this.kindForDebugSake = kindForDebugSake;
        }

        public final String getKindForDebugSake() {
            return this.kindForDebugSake;
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$ContextReceiverGroup;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", ConfigConstants.CONFIG_KEY_DEPTH, "", "(I)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ContextReceiverGroup extends WithDepth {
        public ContextReceiverGroup(int i) {
            super((byte) 8, i);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$QualifierValue;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class QualifierValue extends TowerGroupKind {
        public static final QualifierValue INSTANCE = new QualifierValue();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof QualifierValue)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -402860479;
        }

        public String toString() {
            return "QualifierValue";
        }

        private QualifierValue() {
            super((byte) 9, null);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$UnqualifiedEnum;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$WithDepth;", ConfigConstants.CONFIG_KEY_DEPTH, "", "(I)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnqualifiedEnum extends WithDepth {
        public UnqualifiedEnum(int i) {
            super((byte) 9, i);
        }
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\bÆ\n\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0013\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÖ\u0003J\t\u0010\u0007\u001a\u00020\bHÖ\u0001J\t\u0010\t\u001a\u00020\nHÖ\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Last;", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "()V", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Last extends TowerGroupKind {
        public static final Last INSTANCE = new Last();

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Last)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return -788437392;
        }

        public String toString() {
            return "Last";
        }

        private Last() {
            super((byte) 15, null);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(TowerGroupKind other) {
        Intrinsics.checkNotNullParameter(other, "other");
        int iCompare = Intrinsics.compare((int) this.index, (int) other.index);
        if (iCompare != 0) {
            return iCompare;
        }
        if ((this instanceof WithDepth) && (other instanceof WithDepth)) {
            return Intrinsics.compare(((WithDepth) this).getDepth(), ((WithDepth) other).getDepth());
        }
        return 0;
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind$Companion;", "", "()V", "Implicit", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", ConfigConstants.CONFIG_KEY_DEPTH, "", "NonLocal", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TowerGroupKind Implicit(int depth) {
            return new ImplicitOrNonLocal(depth, "Implicit");
        }

        public final TowerGroupKind NonLocal(int depth) {
            return new ImplicitOrNonLocal(depth, "NonLocal");
        }
    }
}
