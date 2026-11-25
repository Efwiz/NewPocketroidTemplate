package org.jetbrains.kotlin.backend.common.serialization.encodings;

import kotlin.Metadata;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.catrobat.catroid.web.ServerAuthenticationConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: BinaryLattice.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003¢\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u0007HÖ\u0001¢\u0006\u0004\b\u0012\u0010\tJ\u0010\u0010\u0013\u001a\u00020\u0014HÖ\u0001¢\u0006\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\t\u0088\u0001\u0002\u0092\u0001\u00020\u0003¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryLattice;", "", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "constructor-impl", "(J)J", "first", "", "getFirst-impl", "(J)I", "second", "getSecond-impl", Namer.EQUALS_METHOD_NAME, "", "other", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class BinaryLattice {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final long code;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ BinaryLattice m6181boximpl(long j) {
        return new BinaryLattice(j);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static long m6182constructorimpl(long j) {
        return j;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6183equalsimpl(long j, Object obj) {
        return (obj instanceof BinaryLattice) && j == ((BinaryLattice) obj).getCode();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6184equalsimpl0(long j, long j2) {
        return j == j2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6187hashCodeimpl(long j) {
        return Long.hashCode(j);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6188toStringimpl(long j) {
        return "BinaryLattice(code=" + j + ')';
    }

    public boolean equals(Object obj) {
        return m6183equalsimpl(this.code, obj);
    }

    public int hashCode() {
        return m6187hashCodeimpl(this.code);
    }

    public String toString() {
        return m6188toStringimpl(this.code);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ long getCode() {
        return this.code;
    }

    private /* synthetic */ BinaryLattice(long j) {
        this.code = j;
    }

    /* JADX INFO: renamed from: getFirst-impl, reason: not valid java name */
    public static final int m6185getFirstimpl(long j) {
        return INSTANCE.decodeInt(j);
    }

    /* JADX INFO: renamed from: getSecond-impl, reason: not valid java name */
    public static final int m6186getSecondimpl(long j) {
        return INSTANCE.decodeInt(j >>> 1);
    }

    /* JADX INFO: compiled from: BinaryLattice.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0002J\u0016\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nJ\u0010\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\nH\u0002\u0082\u0002\u000b\n\u0002\b!\n\u0005\b¡\u001e0\u0001¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryLattice$Companion;", "", "()V", "decode", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinaryLattice;", ServerAuthenticationConstants.SIGNIN_GOOGLE_CODE_KEY, "", "decode-9PDhHO0", "(J)J", "decodeInt", "", "xx", "encode", "f", "s", "interleaveBits", "input", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int decodeInt(long xx) {
            long j = xx & 6148914691236517205L;
            long j2 = (j ^ (j >> 1)) & 3689348814741910323L;
            long j3 = (j2 ^ (j2 >> 2)) & 1085102592571150095L;
            long j4 = (j3 ^ (j3 >> 4)) & 71777214294589695L;
            long j5 = (j4 ^ (j4 >> 8)) & 281470681808895L;
            return (int) ((j5 ^ (j5 >> 16)) & 4294967295L);
        }

        private final long interleaveBits(int input) {
            long j = ((long) input) & 4294967295L;
            long j2 = (j ^ (j << 16)) & 281470681808895L;
            long j3 = (j2 ^ (j2 << 8)) & 71777214294589695L;
            long j4 = (j3 ^ (j3 << 4)) & 1085102592571150095L;
            long j5 = (j4 ^ (j4 << 2)) & 3689348814741910323L;
            return (j5 ^ (j5 << 1)) & 6148914691236517205L;
        }

        private Companion() {
        }

        public final long encode(int f, int s) {
            return (interleaveBits(s) << 1) | interleaveBits(f);
        }

        /* JADX INFO: renamed from: decode-9PDhHO0, reason: not valid java name */
        public final long m6190decode9PDhHO0(long code) {
            return BinaryLattice.m6182constructorimpl(code);
        }
    }
}
