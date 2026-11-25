package org.jetbrains.kotlin.fir.resolve.calls.tower;

import java.util.Arrays;
import java.util.Comparator;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: TowerGroup.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 %2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001%B3\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\nJ\u000e\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0015\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u0011\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0000H\u0096\u0002J\u0010\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\u0000H\u0002J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001fH\u0096\u0002J\b\u0010 \u001a\u00020\u0013H\u0016J\u0010\u0010!\u001a\u00020\u00002\u0006\u0010\"\u001a\u00020\u0006H\u0002J\b\u0010#\u001a\u00020$H\u0016R\u0011\u0010\u000b\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00008F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0010R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "debugKinds", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "invokeResolvePriority", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/InvokeResolvePriority;", "receiverGroup", "(J[Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/InvokeResolvePriority;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;)V", "InvokeExtensionWithImplicitReceiver", "getInvokeExtensionWithImplicitReceiver", "()Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "Member", "getMember", "[Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "ContextReceiverGroup", ConfigConstants.CONFIG_KEY_DEPTH, "", "Implicit", "InvokeReceiver", "InvokeResolvePriority", "Local", "NonLocal", "TopPrioritized", "compareTo", "other", "debugCompareTo", Namer.EQUALS_METHOD_NAME, "", "", "hashCode", "kindOf", Namer.METADATA_CLASS_KIND, "toString", "", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TowerGroup implements Comparable<TowerGroup> {
    private static final TowerGroup Classifier;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final Comparator<TowerGroupKind[]> DEBUG_KINDS_COMPARATOR;
    private static final int DEPTH_SIZE_BITS;
    private static final TowerGroupKind[] EMPTY_KIND_ARRAY;
    private static final TowerGroup EmptyRoot;
    private static final TowerGroup EmptyRootForInvokeReceiver;
    private static final int KIND_SIZE_BITS;
    private static final TowerGroup Last;
    private static final TowerGroup Member;
    private static final TowerGroup Qualifier;
    private static final TowerGroup QualifierValue;
    private static final TowerGroup Start;
    private static final int USABLE_BITS;
    private final long code;
    private final TowerGroupKind[] debugKinds;
    private final InvokeResolvePriority invokeResolvePriority;
    private final TowerGroup receiverGroup;

    private TowerGroup(long j, TowerGroupKind[] towerGroupKindArr, InvokeResolvePriority invokeResolvePriority, TowerGroup towerGroup) {
        this.code = j;
        this.debugKinds = towerGroupKindArr;
        this.invokeResolvePriority = invokeResolvePriority;
        this.receiverGroup = towerGroup;
    }

    /* synthetic */ TowerGroup(long j, TowerGroupKind[] towerGroupKindArr, InvokeResolvePriority invokeResolvePriority, TowerGroup towerGroup, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, towerGroupKindArr, (i & 4) != 0 ? InvokeResolvePriority.NONE : invokeResolvePriority, (i & 8) != 0 ? null : towerGroup);
    }

    /* JADX INFO: compiled from: TowerGroup.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010\t\n\u0002\b\u0015\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010)\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010+\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010,\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010-\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010.\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011J\u000e\u0010/\u001a\u00020\u00042\u0006\u0010*\u001a\u00020\u0011J)\u00100\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u00102\u001a\u00020\fH\u0002¢\u0006\u0002\u00103J)\u00104\u001a\u00020\u00112\f\u00105\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u00106\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002¢\u0006\u0002\u00107J\u001b\u00108\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u00102\u001a\u00020\fH\u0002¢\u0006\u0002\u00109J\u0010\u0010:\u001a\u00020\u00042\u0006\u00102\u001a\u00020\fH\u0002J\u0018\u0010;\u001a\u00020(2\u0006\u0010<\u001a\u00020(2\u0006\u00102\u001a\u00020\fH\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R-\u0010\t\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b`\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006R\u000e\u0010\u0019\u001a\u00020\u0011X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0006R\u0011\u0010\u001d\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0006R\u0011\u0010\u001f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0006R\u0011\u0010!\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0006R\u0011\u0010#\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0006R\u000e\u0010%\u001a\u00020\u0011X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082T¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup$Companion;", "", "()V", "Classifier", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "getClassifier", "()Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroup;", "DEBUG", "", "DEBUG_KINDS_COMPARATOR", "Ljava/util/Comparator;", "", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "Lkotlin/Comparator;", "getDEBUG_KINDS_COMPARATOR", "()Ljava/util/Comparator;", "DEPTH_MASK", "", "DEPTH_SIZE_BITS", "EMPTY_KIND_ARRAY", "[Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "EmptyRoot", "getEmptyRoot", "EmptyRootForInvokeReceiver", "getEmptyRootForInvokeReceiver", "KIND_MASK", "KIND_SIZE_BITS", "Last", "getLast", "Member", "getMember", "Qualifier", "getQualifier", "QualifierValue", "getQualifierValue", "Start", "getStart", "TOTAL_BITS", "USABLE_BITS", "USED_BITS_MASK", "", "ContextReceiverGroup", ConfigConstants.CONFIG_KEY_DEPTH, "Implicit", "Local", "NonLocal", "TopPrioritized", "UnqualifiedEnum", "appendDebugKind", "kinds", Namer.METADATA_CLASS_KIND, "([Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;)[Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "compareDebugKinds", "aDebugKinds", "bDebugKinds", "([Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;[Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;)I", "debugKindArrayOf", "(Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;)[Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerGroupKind;", "kindOf", "subscript", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TowerGroupKind[] appendDebugKind(TowerGroupKind[] kinds, TowerGroupKind kind) {
            return TowerGroup.EMPTY_KIND_ARRAY;
        }

        private final TowerGroupKind[] debugKindArrayOf(TowerGroupKind kind) {
            return TowerGroup.EMPTY_KIND_ARRAY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final long subscript(long code, TowerGroupKind kind) {
            long index;
            long j;
            int i = (int) (63 & code);
            if (kind instanceof TowerGroupKind.WithDepth) {
                int i2 = TowerGroup.KIND_SIZE_BITS + i;
                int i3 = TowerGroup.DEPTH_SIZE_BITS + i2;
                TowerGroupKind.WithDepth withDepth = (TowerGroupKind.WithDepth) kind;
                if (withDepth.getDepth() <= 65535) {
                    if (i3 > TowerGroup.USABLE_BITS) {
                        throw new IllegalArgumentException(("BitGroup overflow: newUsedBits: " + i3 + ", original: " + Long.toBinaryString(code) + ", usedBits: " + i).toString());
                    }
                    index = code | (((long) kind.getIndex()) << (64 - i2)) | (((long) withDepth.getDepth()) << (64 - i3));
                    j = i3;
                } else {
                    throw new IllegalArgumentException(("Depth overflow: requested: " + withDepth.getDepth() + ", allowed: 65535").toString());
                }
            } else {
                int i4 = i + TowerGroup.KIND_SIZE_BITS;
                if (i4 > TowerGroup.USABLE_BITS) {
                    throw new IllegalArgumentException("Failed requirement.".toString());
                }
                index = code | (((long) kind.getIndex()) << (64 - i4));
                j = i4;
            }
            return index | j;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int compareDebugKinds(TowerGroupKind[] aDebugKinds, TowerGroupKind[] bDebugKinds) {
            int i = 0;
            while (i < aDebugKinds.length) {
                if (i >= bDebugKinds.length) {
                    return 1;
                }
                if (aDebugKinds[i].compareTo(bDebugKinds[i]) < 0) {
                    return -1;
                }
                if (aDebugKinds[i].compareTo(bDebugKinds[i]) > 0) {
                    return 1;
                }
                i++;
            }
            return i < bDebugKinds.length ? -1 : 0;
        }

        public final Comparator<TowerGroupKind[]> getDEBUG_KINDS_COMPARATOR() {
            return TowerGroup.DEBUG_KINDS_COMPARATOR;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final TowerGroup kindOf(TowerGroupKind kind) {
            return new TowerGroup(subscript(0L, kind), debugKindArrayOf(kind), null, null, 12, null);
        }

        public final TowerGroup getEmptyRoot() {
            return TowerGroup.EmptyRoot;
        }

        public final TowerGroup getEmptyRootForInvokeReceiver() {
            return TowerGroup.EmptyRootForInvokeReceiver;
        }

        public final TowerGroup getStart() {
            return TowerGroup.Start;
        }

        public final TowerGroup getQualifier() {
            return TowerGroup.Qualifier;
        }

        public final TowerGroup getClassifier() {
            return TowerGroup.Classifier;
        }

        public final TowerGroup getQualifierValue() {
            return TowerGroup.QualifierValue;
        }

        public final TowerGroup getMember() {
            return TowerGroup.Member;
        }

        public final TowerGroup UnqualifiedEnum(int depth) {
            return kindOf(new TowerGroupKind.UnqualifiedEnum(depth));
        }

        public final TowerGroup Local(int depth) {
            return kindOf(new TowerGroupKind.Local(depth));
        }

        public final TowerGroup Implicit(int depth) {
            return kindOf(TowerGroupKind.INSTANCE.Implicit(depth));
        }

        public final TowerGroup NonLocal(int depth) {
            return kindOf(TowerGroupKind.INSTANCE.NonLocal(depth));
        }

        public final TowerGroup ContextReceiverGroup(int depth) {
            return kindOf(new TowerGroupKind.ContextReceiverGroup(depth));
        }

        public final TowerGroup TopPrioritized(int depth) {
            return kindOf(new TowerGroupKind.TopPrioritized(depth));
        }

        public final TowerGroup getLast() {
            return TowerGroup.Last;
        }
    }

    static {
        final Companion companion = new Companion(null);
        INSTANCE = companion;
        KIND_SIZE_BITS = Integer.bitCount(15);
        DEPTH_SIZE_BITS = Integer.bitCount(65535);
        USABLE_BITS = Long.numberOfLeadingZeros(63L);
        TowerGroupKind[] towerGroupKindArr = new TowerGroupKind[0];
        EMPTY_KIND_ARRAY = towerGroupKindArr;
        DEBUG_KINDS_COMPARATOR = new Comparator() { // from class: org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return companion.compareDebugKinds((TowerGroupKind[]) obj, (TowerGroupKind[]) obj2);
            }
        };
        DefaultConstructorMarker defaultConstructorMarker = null;
        long j = 0;
        TowerGroup towerGroup = null;
        EmptyRoot = new TowerGroup(j, towerGroupKindArr, null, towerGroup, 12, defaultConstructorMarker);
        EmptyRootForInvokeReceiver = new TowerGroup(j, towerGroupKindArr, InvokeResolvePriority.INVOKE_RECEIVER, towerGroup, 8, defaultConstructorMarker);
        Start = companion.kindOf(TowerGroupKind.Start.INSTANCE);
        Qualifier = companion.kindOf(TowerGroupKind.Qualifier.INSTANCE);
        Classifier = companion.kindOf(TowerGroupKind.Classifier.INSTANCE);
        QualifierValue = companion.kindOf(TowerGroupKind.QualifierValue.INSTANCE);
        Member = companion.kindOf(TowerGroupKind.Member.INSTANCE);
        Last = companion.kindOf(TowerGroupKind.Last.INSTANCE);
    }

    private final TowerGroup kindOf(TowerGroupKind kind) {
        Companion companion = INSTANCE;
        return new TowerGroup(companion.subscript(this.code, kind), companion.appendDebugKind(this.debugKinds, kind), null, null, 12, null);
    }

    public final TowerGroup getMember() {
        return kindOf(TowerGroupKind.Member.INSTANCE);
    }

    public final TowerGroup getInvokeExtensionWithImplicitReceiver() {
        return kindOf(TowerGroupKind.InvokeExtensionWithImplicitReceiver.INSTANCE);
    }

    public final TowerGroup Local(int depth) {
        return kindOf(new TowerGroupKind.Local(depth));
    }

    public final TowerGroup Implicit(int depth) {
        return kindOf(TowerGroupKind.INSTANCE.Implicit(depth));
    }

    public final TowerGroup NonLocal(int depth) {
        return kindOf(TowerGroupKind.INSTANCE.NonLocal(depth));
    }

    public final TowerGroup ContextReceiverGroup(int depth) {
        return kindOf(new TowerGroupKind.ContextReceiverGroup(depth));
    }

    public final TowerGroup TopPrioritized(int depth) {
        return kindOf(new TowerGroupKind.TopPrioritized(depth));
    }

    public final TowerGroup InvokeReceiver(TowerGroup receiverGroup, InvokeResolvePriority invokeResolvePriority) {
        Intrinsics.checkNotNullParameter(receiverGroup, "receiverGroup");
        Intrinsics.checkNotNullParameter(invokeResolvePriority, "invokeResolvePriority");
        if (receiverGroup.invokeResolvePriority != InvokeResolvePriority.INVOKE_RECEIVER) {
            throw new IllegalArgumentException(("Receivers for invoke should be resolved with INVOKE_RECEIVER, but " + receiverGroup.invokeResolvePriority + " found").toString());
        }
        if (invokeResolvePriority == InvokeResolvePriority.NONE || invokeResolvePriority == InvokeResolvePriority.INVOKE_RECEIVER) {
            throw new IllegalArgumentException("invokeResolvePriority should be non-trivial when receiverGroup is specified".toString());
        }
        if (receiverGroup.receiverGroup != null) {
            throw new IllegalArgumentException(("receiverGroup should be trivial, but " + receiverGroup.receiverGroup + " was found").toString());
        }
        return new TowerGroup(this.code, this.debugKinds, invokeResolvePriority, receiverGroup);
    }

    public final TowerGroup InvokeResolvePriority(InvokeResolvePriority invokeResolvePriority) {
        Intrinsics.checkNotNullParameter(invokeResolvePriority, "invokeResolvePriority");
        if (invokeResolvePriority == InvokeResolvePriority.NONE) {
            return this;
        }
        return new TowerGroup(this.code, this.debugKinds, invokeResolvePriority, null, 8, null);
    }

    @Override // java.lang.Comparable
    public int compareTo(TowerGroup other) {
        Intrinsics.checkNotNullParameter(other, "other");
        if (this.receiverGroup == null && other.receiverGroup == null && this.invokeResolvePriority == InvokeResolvePriority.NONE && other.invokeResolvePriority == InvokeResolvePriority.NONE) {
            return Long.compareUnsigned(this.code, other.code);
        }
        TowerGroup towerGroup = this.receiverGroup;
        long j = towerGroup != null ? towerGroup.code : 0L;
        TowerGroup towerGroup2 = other.receiverGroup;
        long j2 = towerGroup2 != null ? towerGroup2.code : 0L;
        long j3 = Long.compareUnsigned(this.code, j) >= 0 ? this.code : j;
        int iCompareUnsigned = Long.compareUnsigned(j3, Long.compareUnsigned(other.code, j2) >= 0 ? other.code : j2);
        if (iCompareUnsigned != 0) {
            return iCompareUnsigned;
        }
        int iCompareTo = this.invokeResolvePriority.compareTo(other.invokeResolvePriority);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        if (Long.compareUnsigned(j, j3) == 0 && Long.compareUnsigned(j2, j3) == 0) {
            return Long.compareUnsigned(this.code, other.code);
        }
        return Long.compareUnsigned(j, j2);
    }

    public boolean equals(Object other) {
        TowerGroup towerGroup;
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.resolve.calls.tower.TowerGroup");
        TowerGroup towerGroup2 = (TowerGroup) other;
        if (this.code != towerGroup2.code || this.invokeResolvePriority != towerGroup2.invokeResolvePriority) {
            return false;
        }
        TowerGroup towerGroup3 = this.receiverGroup;
        return towerGroup3 == null || (towerGroup = towerGroup2.receiverGroup) == null || Intrinsics.areEqual(towerGroup3, towerGroup);
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder("TowerGroup(code=").append(Long.toBinaryString(this.code)).append(", debugKinds=");
        String string = Arrays.toString(this.debugKinds);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return sbAppend.append(string).append(", invokeResolvePriority=").append(this.invokeResolvePriority).append(')').toString();
    }

    public int hashCode() {
        return (Long.hashCode(this.code) * 31) + this.invokeResolvePriority.hashCode();
    }
}
