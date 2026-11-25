package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CityHash.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0000J\u0016\u0010\f\u001a\u00020\u0003HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\r\u0010\u0007J\u0016\u0010\u000e\u001a\u00020\u0003HÆ\u0003ø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0007J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001ø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\u001f\u0010\u0019\u001a\u00020\u0003*\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003H\u0082\u0004ø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u001bR\u0019\u0010\u0004\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007R\u0019\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000ø\u0001\u0001¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\t\u0010\u0007\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", "", "lowBytes", "Lkotlin/ULong;", "highBytes", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getHighBytes-s-VKNKU", "()J", "J", "getLowBytes-s-VKNKU", "combineWith", "other", "component1", "component1-s-VKNKU", "component2", "component2-s-VKNKU", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "copy-PWzV0Is", "(JJ)Lorg/jetbrains/kotlin/backend/common/serialization/Hash128Bits;", Namer.EQUALS_METHOD_NAME, "", "hashCode", "", "toString", "", "combineHash", "combineHash-oku0oEs", "(JJ)J", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class Hash128Bits {
    private final long highBytes;
    private final long lowBytes;

    public /* synthetic */ Hash128Bits(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    /* JADX INFO: renamed from: copy-PWzV0Is$default, reason: not valid java name */
    public static /* synthetic */ Hash128Bits m6134copyPWzV0Is$default(Hash128Bits hash128Bits, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            j = hash128Bits.lowBytes;
        }
        if ((i & 2) != 0) {
            j2 = hash128Bits.highBytes;
        }
        return hash128Bits.m6137copyPWzV0Is(j, j2);
    }

    /* JADX INFO: renamed from: component1-s-VKNKU, reason: not valid java name and from getter */
    public final long getLowBytes() {
        return this.lowBytes;
    }

    /* JADX INFO: renamed from: component2-s-VKNKU, reason: not valid java name and from getter */
    public final long getHighBytes() {
        return this.highBytes;
    }

    /* JADX INFO: renamed from: copy-PWzV0Is, reason: not valid java name */
    public final Hash128Bits m6137copyPWzV0Is(long lowBytes, long highBytes) {
        return new Hash128Bits(lowBytes, highBytes, null);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Hash128Bits)) {
            return false;
        }
        Hash128Bits hash128Bits = (Hash128Bits) other;
        return this.lowBytes == hash128Bits.lowBytes && this.highBytes == hash128Bits.highBytes;
    }

    public int hashCode() {
        return (ULong.m3084hashCodeimpl(this.lowBytes) * 31) + ULong.m3084hashCodeimpl(this.highBytes);
    }

    public String toString() {
        return "Hash128Bits(lowBytes=" + ((Object) ULong.m3118toStringimpl(this.lowBytes)) + ", highBytes=" + ((Object) ULong.m3118toStringimpl(this.highBytes)) + ')';
    }

    private Hash128Bits(long j, long j2) {
        this.lowBytes = j;
        this.highBytes = j2;
    }

    public /* synthetic */ Hash128Bits(long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CityHashKt.k0 : j, (i & 2) != 0 ? CityHashKt.k1 : j2, null);
    }

    /* JADX INFO: renamed from: getHighBytes-s-VKNKU, reason: not valid java name */
    public final long m6138getHighBytessVKNKU() {
        return this.highBytes;
    }

    /* JADX INFO: renamed from: getLowBytes-s-VKNKU, reason: not valid java name */
    public final long m6139getLowBytessVKNKU() {
        return this.lowBytes;
    }

    /* JADX INFO: renamed from: combineHash-oku0oEs, reason: not valid java name */
    private final long m6133combineHashoku0oEs(long j, long j2) {
        return ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(ULong.m3072constructorimpl(j + CityHashKt.kGoldenRatio) + ULong.m3072constructorimpl(j2 << 12)) + ULong.m3072constructorimpl(j2 >>> 4)) ^ j2);
    }

    public final Hash128Bits combineWith(Hash128Bits other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new Hash128Bits(m6133combineHashoku0oEs(this.highBytes, other.lowBytes), m6133combineHashoku0oEs(this.lowBytes, other.highBytes), null);
    }
}
